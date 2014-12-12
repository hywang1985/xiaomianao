package com.jianfeng.xiaomianao.handler.dto.post;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.jianfeng.xiaomianao.domain.ImageDescriptor;
import com.jianfeng.xiaomianao.domain.MianaouserinfoBean;
import com.jianfeng.xiaomianao.domain.Post;
import com.jianfeng.xiaomianao.handler.dto.DTOFactory;
import com.jianfeng.xiaomianao.handler.dto.user.CommunityMemberDTO;

public class PostDTO extends ParentPostDTO {

    protected ParentPostDTO parent;

    protected List<String> images;

    protected boolean liked;

    public PostDTO convertPost(Post post) {
        if (post != null) {
            this.setId(post.getId());
            this.setLikeCount(post.getLikeCount());
            this.setContent(post.getContent());
            this.setOwned(post.isOwned());
            this.setMainPostOwner(post.isMainPostOwner());
            this.setFloor(post.getFloor());
            this.setCreateDate(post.getCreatetime());
            this.setLiked(post.isLiked());
            setImages(post);
            this.setOwner((CommunityMemberDTO) DTOFactory.eInstance.createCommunityMemberDTO().convert(post.getUser()));
            Post parentPost = post.getParentPost();
            if (parentPost != null) {
                ParentPostDTO parentPostDTO = DTOFactory.eInstance.createParentPostDTO();
                parentPostDTO.setId(parentPost.getId());
                parentPostDTO.setContent(parentPost.getContent());
                parentPostDTO.setCreateDate(parentPost.getCreatetime());
                parentPostDTO.setOwned(parentPost.isOwned());
                parentPostDTO.setLikeCount(parentPost.getLikeCount());
                parentPostDTO.setFloor(parentPost.getFloor());
                parentPostDTO.setMainPostOwner(parentPost.isMainPostOwner());
                this.setParent(parentPostDTO);
                MianaouserinfoBean parentOwner = parentPost.getUser();
                if (parentOwner != null) {
                    CommunityMemberDTO parentOwnerDto = DTOFactory.eInstance.createCommunityMemberDTO();
                    parentOwnerDto.setUserId(parentOwner.getUserid());
                    parentOwnerDto.setUserName(parentOwner.getUsername());
                    parentOwnerDto.setNickName(parentOwner.getNickName());
                    parentOwnerDto.setSnapshot(parentOwner.getSnapshot());
                    this.getParent().setOwner(parentOwnerDto);
                }
            }
        }
        return this;
    }

    protected void setImages(Post post) {
        Set<ImageDescriptor> imageDescriptors = post.getImages();
        if (imageDescriptors != null && !imageDescriptors.isEmpty()) {
            List<String> imgList = new ArrayList<String>();
            Iterator<ImageDescriptor> it = imageDescriptors.iterator();
            while (it.hasNext()) {
                imgList.add(it.next().getUrl());
            }
            this.setImages(imgList);
        }
    }

    public ParentPostDTO getParent() {
        return parent;
    }

    public void setParent(ParentPostDTO parent) {
        this.parent = parent;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }
}
