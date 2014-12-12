package com.jianfeng.xiaomianao.handler.dto.post;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.jianfeng.xiaomianao.domain.CommunityTag;
import com.jianfeng.xiaomianao.domain.MainPost;
import com.jianfeng.xiaomianao.handler.dto.DTOFactory;
import com.jianfeng.xiaomianao.handler.dto.user.CommunityMemberDTO;

public class MainPostDTO extends PostDTO {

    protected String title;

    protected int replyCount;

    protected List<String> tags;

    protected int digestType;

    protected String briefContent;

    protected boolean degest;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public MainPostDTO converMainPost(MainPost mainPost) {
        if (mainPost != null) {
            this.id = mainPost.getId();
            this.title = mainPost.getTitle();
            this.owned = mainPost.isOwned();
            this.likeCount = mainPost.getLikeCount();
            this.replyCount = mainPost.getReplyCount();
            this.createDate = mainPost.getCreatetime();
            this.digestType = mainPost.getDigestType();
            this.degest = mainPost.isDegest();
            this.briefContent = mainPost.getBriefContent();
            this.liked = mainPost.isLiked();
            setImages(mainPost);
            setTags(mainPost);
            this.setOwner((CommunityMemberDTO) DTOFactory.eInstance.createCommunityMemberDTO().convert(mainPost.getUser()));

        }
        return this;
    }

    protected void setTags(MainPost mainPost) {
        Set<CommunityTag> tagsSet = mainPost.getTags();
        if (tagsSet != null) {
            List<String> tags = new ArrayList<String>();
            Iterator<CommunityTag> it = tagsSet.iterator();
            while (it.hasNext()) {
                tags.add(it.next().getName());
            }
            this.setTags(tags);
        }
    }

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    @JsonIgnore
    public String getContent() {
        return super.getContent();
    }

    @JsonIgnore
    public ParentPostDTO getParent() {
        return super.getParent();
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public int getDigestType() {
        return digestType;
    }

    public void setDigestType(int digestType) {
        this.digestType = digestType;
    }

    @JsonIgnore
    public int getFloor() {
        return super.getFloor();
    }

    public String getBriefContent() {
        return briefContent;
    }

    public void setBriefContent(String briefContent) {
        this.briefContent = briefContent;
    }

    @JsonIgnore
    public boolean isDegest() {
        return degest;
    }

    public void setDegest(boolean degest) {
        this.degest = degest;
    }

}
