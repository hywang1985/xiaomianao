package com.jianfeng.xiaomianao.service;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jianfeng.xiaomianao.dao.CommunityDao;
import com.jianfeng.xiaomianao.dao.CommunityTagDao;
import com.jianfeng.xiaomianao.dao.ImageDescriptorDao;
import com.jianfeng.xiaomianao.dao.MainPostDao;
import com.jianfeng.xiaomianao.dao.PostDao;
import com.jianfeng.xiaomianao.domain.Community;
import com.jianfeng.xiaomianao.domain.CommunityTag;
import com.jianfeng.xiaomianao.domain.DomainEntityFactory;
import com.jianfeng.xiaomianao.domain.ImageDescriptor;
import com.jianfeng.xiaomianao.domain.MainPost;
import com.jianfeng.xiaomianao.domain.MianaouserinfoBean;
import com.jianfeng.xiaomianao.domain.Post;
import com.jianfeng.xiaomianao.util.StringUtil;

@Service
public class PostService extends AbstractUserNeededService {

    private static final int BRIEF_CONTENT_MAX_SIZE = 60;
    
    @Autowired
    private PostDao postDao;

    @Autowired
    private MainPostDao mainpostDao;

    @Autowired
    private CommunityDao communityDao;

    @Autowired
    private CommunityTagDao communityTagDao;

    @Autowired
    private ImageDescriptorDao imageDao;

    @Autowired
    private FileService fileService;

    public Post addPost(String userId, Integer mainpostId, Integer parentId, String content, List<String> images) {

        if (mainpostId == null || mainpostId < 0) {
            throw new IllegalArgumentException("The parameter mainpostId is required.");
        }

        MianaouserinfoBean user = findUser(userId);
        Post post = DomainEntityFactory.eInstance.createPost();
        user.setPostCount(user.getPostCount() + 1);
        post.setUser(user);
        if (parentId != null && parentId > 0) {
            Post parentPost = postDao.read(parentId);
            if (parentPost != null) {
                post.setParentId(parentId);
            }
        }
        post.setContent(content);
        post.setCreatetime(new Date());
        // find last post and set floor,this is enable to cover the delete floor
        Post lastPost = postDao.findLastMainPost(mainpostId);
        if (lastPost != null) {
            post.setFloor(lastPost.getFloor() + 1);
        } else {
            post.setFloor(post.getFloor() + 1);
        }

        MainPost mainPost = mainpostDao.read(mainpostId);
        if (mainPost != null) {
            post.setCommunityId(mainPost.getCommunityId());
            mainPost.setReplyCount(mainPost.getReplyCount() + 1);
            mainpostDao.update(mainPost);
            post.setMainpostId(mainpostId);
        }
        if (images != null && !images.isEmpty()) {
            for (String saveKey : images) {
                ImageDescriptor image = DomainEntityFactory.eInstance.createImageDescriptor();
                image.setUrl(FileService.generateImageUrl(saveKey));
                image.setSaveKey(saveKey);
                // imageDao.create(image);
                post.getImages().add(image);
            }
        }
        return postDao.create(post);
    }

    public MainPost addMainPost(String userId, Integer communityId, String title, String content, List<String> tags,
            List<String> images) {

        if (communityId == null || communityId < 0) {
            throw new IllegalArgumentException("The parameter communityId is required.");
        }
        MianaouserinfoBean user = findUser(userId);
        MainPost mainPost = DomainEntityFactory.eInstance.createMainPost();
        mainPost.setUser(user);
        user.setMainpostCount(user.getMainpostCount() + 1);
        if (tags != null && !tags.isEmpty()) {
            List<CommunityTag> ctags = communityTagDao.findTagsByName(tags);
            if (ctags != null && !ctags.isEmpty()) {
                mainPost.getTags().addAll(ctags);
            }
        }
        mainPost.setCommunityId(communityId);
        mainPost.setMainPostContent(content);
        mainPost.setBriefContent(StringUtil.judgeChina(content, BRIEF_CONTENT_MAX_SIZE, ""));
        mainPost.setTitle(title);
        mainPost.setCreatetime(new Date());
        if (images != null && !images.isEmpty()) {
            for (String saveKey : images) {
                ImageDescriptor image = DomainEntityFactory.eInstance.createImageDescriptor();
                image.setSaveKey(saveKey);
                image.setUrl(FileService.generateImageUrl(saveKey));
                // imageDao.create(image);
                mainPost.getImages().add(image);
            }
        }
        Community community = communityDao.read(communityId);
        community.setMainPostCount(community.getMainPostCount() + 1);
        mainPost.setSource(community.getName());
        mainpostDao.create(mainPost);
        community.setLastMainpostId(mainPost.getId());
        communityDao.update(community);
        return mainPost;
    }

    public boolean deletePost(String userId, Integer postId) throws Exception {
        findUser(userId); // ensure the request is sent with token.
        boolean result = true;
        try {
            Post post = postDao.read(postId);
            MianaouserinfoBean user = post.getUser();
            user.setPostCount(user.getPostCount() - 1);
            post.setUser(null);
            deleteImages(post);
            int mainpostId = post.getMainpostId();
            if (mainpostId > 0) {
                MainPost mp = mainpostDao.read(mainpostId);
                mp.setReplyCount(mp.getReplyCount() - 1);
                mainpostDao.update(mp);
            }
            postDao.delete(post);
        } catch (Exception e) {
            result = false;
            throw e;
        }
        return result;
    }

    public boolean deleteMainPost(String userId, Integer mainPostId) throws Exception {
        findUser(userId); // ensure the request is sent with token.
        boolean result = true;
        try {
            MainPost mainPost = mainpostDao.read(mainPostId);
            MianaouserinfoBean user = mainPost.getUser();
            List<Post> posts = postDao.findPostForMainPost(mainPost.getId(), -1, -1);
            if (posts != null && !posts.isEmpty()) {
                for (Post post : posts) {
                    deletePost(userId, post.getId());
                }
            }
            user.setMainpostCount(user.getMainpostCount() - 1);
            mainPost.getTags().clear();
            mainPost.setUser(null);
            deleteImages(mainPost);
            mainpostDao.delete(mainPost);
            Community community = communityDao.read(mainPost.getCommunityId());
            MainPost lastMainPost = mainpostDao.findLastMainPost(community.getId());
            if (lastMainPost != null) {
                community.setLastMainpostId(lastMainPost.getId());
            }
            community.setMainPostCount(community.getMainPostCount() - 1);
            communityDao.update(community);
        } catch (Exception e) {
            result = false;
            throw e;
        }
        return result;
    }

    private void deleteImages(Post post) {
        if (post != null) {
            Set<ImageDescriptor> images = post.getImages();
            if (images != null && !images.isEmpty()) {
                Iterator<ImageDescriptor> it = images.iterator();
                while (it.hasNext()) {
                    fileService.deleteFile(it.next().getSaveKey());
                }
            }
        }
    }
}
