package com.jianfeng.xiaomianao.domain;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * 社区回帖
 */
@Entity
@Table
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "post_type", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("1")
public class Post extends BaseSerializable {

    private static final long serialVersionUID = -2965402491406273731L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    protected MianaouserinfoBean user;

    @Column(columnDefinition = "int default -1")
    protected int parentId = -1;

    @Column(columnDefinition = "int default -1")
    protected int mainpostId = -1;

    @Column(columnDefinition = "int default -1")
    protected int communityId = -1;

    @Column(columnDefinition = "varchar(140)")
    protected String content;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    protected Date createtime;

    /**
     * 当前帖子/评论的作者是否是当前用户
     */
    @Transient
    protected boolean owned;

    /**
     * 当前评论的作者是否是楼主
     */
    @Transient
    private boolean mainPostOwner;

    /**
     * 当前帖子/评论是否被当前用户赞
     */
    @Transient
    protected boolean liked;

    /**
     * 该帖被赞的数量
     */
    @Column(name = "like_count", columnDefinition = "int default 0")
    protected int likeCount;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "post_owned_images", joinColumns = { @JoinColumn(name = "post_id") }, inverseJoinColumns = { @JoinColumn(name = "image_id") })
    protected Set<ImageDescriptor> images = new LinkedHashSet<ImageDescriptor>();

    /**
     * 赞了当前评论的人
     */
    @ManyToMany(mappedBy = "likedPosts", fetch = FetchType.LAZY)
    private Set<MianaouserinfoBean> likers = new LinkedHashSet<MianaouserinfoBean>();

    /**
     * 楼层数
     */
    @Column(columnDefinition = "int default 0")
    private int floor;

    @Transient
    private Post parentPost;

    Post() {
    }

    public MianaouserinfoBean getUser() {
        return user;
    }

    public void setUser(MianaouserinfoBean user) {
        this.user = user;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getCommunityId() {
        return communityId;
    }

    public void setCommunityId(int communityId) {
        this.communityId = communityId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public int getMainpostId() {
        return mainpostId;
    }

    public void setMainpostId(int mainpostId) {
        this.mainpostId = mainpostId;
    }

    public Set<ImageDescriptor> getImages() {
        return images;
    }

    public void setImages(Set<ImageDescriptor> images) {
        this.images = images;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public boolean isOwned() {
        return owned;
    }

    public void setOwned(boolean owned) {
        this.owned = owned;
    }

    @JsonIgnore
    public Post getParentPost() {
        return parentPost;
    }

    public void setParentPost(Post parentPost) {
        this.parentPost = parentPost;
    }

    @JsonIgnore
    public Set<MianaouserinfoBean> getLikers() {
        return likers;
    }

    public void setLikers(Set<MianaouserinfoBean> likers) {
        this.likers = likers;
    }

    public boolean isMainPostOwner() {
        return mainPostOwner;
    }

    public void setMainPostOwner(boolean mainPostOwner) {
        this.mainPostOwner = mainPostOwner;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }
}
