package com.jianfeng.xiaomianao.domain;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.jianfeng.xiaomianao.handler.dto.Favoritable;

/**
 * 社区主贴
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("2")
public class MainPost extends Post implements Favoritable {

    private static final long serialVersionUID = -3420558365868464437L;

    /**
     * 普通的主题帖子
     */
    public static final int NOT_DIGEST_TYPE = 0;

    /**
     * 标记为精华的主题帖子
     */
    public static final int DIGEST_MAIN_POST_TYPE = 1;

    /**
     * 标记为公告的主题帖子
     */
    public static final int DIGEST_ADV_TYPE = 2;

    /**
     * 标记为新的帖子
     */
    public static final int DIGEST_NEW_TYPE = 3;

    /**
     * 标记为置顶的帖子
     */
    public static final int DIGEST_LIST_IN_TOP_TYPE = 4;

    /**
     * 帖子是否置顶
     */
    @Column(columnDefinition = "boolean default false")
    private boolean degest;

    /**
     * 主题帖的题目
     */
    private String title;

    /**
     * 该主题贴的回复数
     */
    @Column(name = "reply_count", columnDefinition = "int default 0")
    private int replyCount;

    /**
     * 该主题贴的浏览次数
     */
    @Column(columnDefinition = "int default 0")
    private int visits;

    @Column(columnDefinition = "int default 0")
    private int digestType = NOT_DIGEST_TYPE;

    /**
     * 主贴简要内容
     */
    @Column(name = "brief_content")
    private String briefContent;

    /**
     * 主贴内容，大小和回复内容不同
     */
    @Column(name = "mainpost_content", columnDefinition = "TEXT")
    private String mainPostContent;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "mainpost_tags", joinColumns = @JoinColumn(name = "mainpost_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"))
    private Set<CommunityTag> tags = new LinkedHashSet<CommunityTag>();

    /**
     * 收藏了当前主帖的人
     */
    @ManyToMany(mappedBy = "favoriteMainpost", fetch = FetchType.LAZY)
    private Set<MianaouserinfoBean> favoritors = new LinkedHashSet<MianaouserinfoBean>();

    /**
     * 赞了当前主贴的人
     */
    @ManyToMany(mappedBy = "likedMainPosts", fetch = FetchType.LAZY)
    private Set<MianaouserinfoBean> mpLikers = new LinkedHashSet<MianaouserinfoBean>();

    /**
     * 主贴的来源
     */
    @Column
    private String source;

    /**
     * 标记主贴是否为当前用户所收藏
     */
    @Transient
    private boolean favorited;

    @Transient
    private Date favTime;

    @Transient
    private Date likeTime;

    MainPost() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    public Set<CommunityTag> getTags() {
        return tags;
    }

    public void setTags(Set<CommunityTag> tags) {
        this.tags = tags;
    }

    public int getDigestType() {
        return digestType;
    }

    public void setDigestType(int digestType) {
        this.digestType = digestType;
    }

    @JsonIgnore
    public Set<MianaouserinfoBean> getFavoritors() {
        return favoritors;
    }

    public void setFavoritors(Set<MianaouserinfoBean> favoritors) {
        this.favoritors = favoritors;
    }

    public String getMainPostContent() {
        return mainPostContent;
    }

    public void setMainPostContent(String mainPostContent) {
        this.mainPostContent = mainPostContent;
    }

    public String getBriefContent() {
        return briefContent;
    }

    public void setBriefContent(String briefContent) {
        this.briefContent = briefContent;
    }

    public int getVisits() {
        return visits;
    }

    public void setVisits(int visits) {
        this.visits = visits;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public boolean isDegest() {
        return degest;
    }

    public void setDegest(boolean degest) {
        this.degest = degest;
    }

    @JsonIgnore
    public Set<MianaouserinfoBean> getMpLikers() {
        return mpLikers;
    }

    public void setMpLikers(Set<MianaouserinfoBean> mpLikers) {
        this.mpLikers = mpLikers;
    }

    public boolean isFavorited() {
        return favorited;
    }

    public void setFavorited(boolean favorited) {
        this.favorited = favorited;
    }

    public Date getFavTime() {
        return favTime;
    }

    public void setFavTime(Date favTime) {
        this.favTime = favTime;
    }

    public Date getLikeTime() {
        return likeTime;
    }

    public void setLikeTime(Date likeTime) {
        this.likeTime = likeTime;
    }
    
    public int compareTo(Favoritable o) {
        int order = 0;
        if( this.getFavTime().compareTo(o.getFavTime()) > 0){
            order = -1;
        } else if( this.getFavTime().compareTo(o.getFavTime()) < 0){
            order = 1;
        }
        return order;
    }

}
