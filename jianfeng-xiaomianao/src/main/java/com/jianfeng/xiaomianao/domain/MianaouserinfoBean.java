package com.jianfeng.xiaomianao.domain;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnore;

@Entity
@Table(name = "mianaouserinfo")
public class MianaouserinfoBean extends BaseSerializable {

    private static final long serialVersionUID = -6787376058329684318L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    /**
     * 用户唯一标示
     */
    @Column(name = "userid", unique = true)
    private String userid;

    /**
     * 用户名
     */
    @Column(name = "username")
    private String username;

    /**
     * 用户密码
     */
    @Column(name = "checkcode")
    private String checkcode;

    /**
     * 用户来源 渠道
     */
    @Column(name = "channel")
    private String channel;

    /**
     * statue 状态 0成功 1 代表token过期 2其他原因
     */
    @Column(name = "statue")
    private Integer statue;

    @Column(name = "createtime")
    private Date createtime;

    /**
     * 上次登录时的imei，不允许同一账号在多台移动设备上同时登录
     */
    @Column(name = "last_imei")
    private String lastImei;

    /**
     * 订阅的标签
     */
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "user_subsribed_tags", joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "tag_id", referencedColumnName = "id") })
    private Set<Tag> subscribedTags = new LinkedHashSet<Tag>();

    /**
     * 收藏的资讯
     */
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "user_favorite_news", joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "news_id", referencedColumnName = "id") })
    private Set<NewsInfoBean> favoriteNews = new LinkedHashSet<NewsInfoBean>();

    /**
     * 赞过的资讯
     */
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "user_liked_news", joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "news_id", referencedColumnName = "id") })
    private Set<NewsInfoBean> likedNews = new LinkedHashSet<NewsInfoBean>();

    /**
     * 赞过的评论
     */
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "user_liked_posts", joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "post_id", referencedColumnName = "id") })
    private Set<Post> likedPosts = new LinkedHashSet<Post>();

    /**
     * 赞过的主贴
     */
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "user_liked_main_posts", joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "mainpost_id", referencedColumnName = "id") })
    private Set<MainPost> likedMainPosts = new LinkedHashSet<MainPost>();

    /**
     * 用户在资讯的评论
     */
    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    private Set<Comment> comments = new LinkedHashSet<Comment>();

    /**
     * 用户选择的棉袄状态
     */
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_mianao_states", joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "mstate_id", referencedColumnName = "id") })
    private Set<MianaoState> mianaoStates = new LinkedHashSet<MianaoState>();

    /**
     * 用户关注的社区
     */
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "user_community", joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "community_id", referencedColumnName = "id") })
    private Set<Community> communities = new LinkedHashSet<Community>();

    /**
     * 收藏的主贴
     */
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "user_favorite_mainpost", joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "mainpost_id", referencedColumnName = "id") })
    private Set<MainPost> favoriteMainpost = new LinkedHashSet<MainPost>();

    /**
     * 用户头像
     */
    @Column
    private String snapshot;

    /**
     * 棉袄昵称
     */
    @Column
    private String nickName;

    /**
     * 地区
     */
    @Column
    private String location;

    /**
     * 生日
     */
    @Column(name = "birth")
    @Temporal(TemporalType.TIMESTAMP)
    private Date birth;

    /**
     * 个性签名
     */
    @Column(columnDefinition = "varchar(30)")
    private String signature;

    /**
     * 关注的社区数量
     */
    @Column(name = "communities_count", columnDefinition = "int default 0")
    private int communitiesCount;

    /**
     * 关注的人的数量
     */
    @Column(name = "friends_count", columnDefinition = "int default 0")
    private int friendsCount;

    /**
     * 粉丝的数量
     */
    @Column(name = "followers_count", columnDefinition = "int default 0")
    private int followersCount;

    /**
     * 收藏的主贴的数量
     */
    @Column(name = "favoriate_mainpost_count", columnDefinition = "int default 0")
    private int favPostCount;

    /**
     * 收藏的资讯的数量
     */
    @Column(name = "favoriate_news_count", columnDefinition = "int default 0")
    private int favNewsCount;

    /**
     * 发表过的帖子数量
     */
    @Column(name = "mainpost_count", columnDefinition = "int default 0")
    private int mainpostCount;

    /**
     * 回帖数量
     */
    @Column(name = "post_count", columnDefinition = "int default 0")
    private int postCount;

    /**
     * 赞过的主帖数量
     */
    @Column(name = "liked_post_count", columnDefinition = "int default 0")
    private int likedPostCount;

    /**
     * 赞资讯数量
     */
    @Column(name = "liked_news_count", columnDefinition = "int default 0")
    private int likedNewsCount;

    /**
     * 用户的粉丝
     */
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "user_followers", joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "fans_id", referencedColumnName = "id") })
    private Set<MianaouserinfoBean> followers = new LinkedHashSet<MianaouserinfoBean>();

    /**
     * 用户的好友
     */
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "user_friends", joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "friend_id", referencedColumnName = "id") })
    private Set<MianaouserinfoBean> friends = new LinkedHashSet<MianaouserinfoBean>();

    /**
     * 是否被当前用户follow
     */
    @Transient
    private boolean followed;

    MianaouserinfoBean() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public Integer getStatue() {
        return statue;
    }

    public void setStatue(Integer statue) {
        this.statue = statue;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getCheckcode() {
        return checkcode;
    }

    public void setCheckcode(String checkcode) {
        this.checkcode = checkcode;
    }

    public Set<Tag> getSubscribedTags() {
        return subscribedTags;
    }

    public void setSubscribedTags(Set<Tag> subscribedTags) {
        this.subscribedTags = subscribedTags;
    }

    @JsonIgnore
    public Set<NewsInfoBean> getFavoriteNews() {
        return favoriteNews;
    }

    public void setFavoriteNews(Set<NewsInfoBean> favoriteNews) {
        this.favoriteNews = favoriteNews;
    }

    @JsonIgnore
    public Set<NewsInfoBean> getLikedNews() {
        return likedNews;
    }

    public void setLikedNews(Set<NewsInfoBean> likedNews) {
        this.likedNews = likedNews;
    }

    @JsonIgnore
    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getSnapshot() {
        return snapshot;
    }

    public void setSnapshot(String snapshot) {
        this.snapshot = snapshot;
    }

    public Set<MianaoState> getMianaoStates() {
        return mianaoStates;
    }

    public void setMianaoStates(Set<MianaoState> mianaoStates) {
        this.mianaoStates = mianaoStates;
    }

    @JsonIgnore
    public Set<Community> getCommunities() {
        return communities;
    }

    public void setCommunities(Set<Community> communities) {
        this.communities = communities;
    }

    public int getCommunitiesCount() {
        return communitiesCount;
    }

    public void setCommunitiesCount(int communitiesCount) {
        this.communitiesCount = communitiesCount;
    }

    public int getFriendsCount() {
        return friendsCount;
    }

    public void setFriendsCount(int friendsCount) {
        this.friendsCount = friendsCount;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(int followersCount) {
        this.followersCount = followersCount;
    }

    public int getFavPostCount() {
        return favPostCount;
    }

    public void setFavPostCount(int favPostCount) {
        this.favPostCount = favPostCount;
    }

    public int getMainpostCount() {
        return mainpostCount;
    }

    public void setMainpostCount(int mainpostCount) {
        this.mainpostCount = mainpostCount;
    }

    public int getPostCount() {
        return postCount;
    }

    public void setPostCount(int postCount) {
        this.postCount = postCount;
    }

    public int getLikedPostCount() {
        return likedPostCount;
    }

    public void setLikedPostCount(int likedPostCount) {
        this.likedPostCount = likedPostCount;
    }

    public int getLikedNewsCount() {
        return likedNewsCount;
    }

    public void setLikedNewsCount(int likedNewsCount) {
        this.likedNewsCount = likedNewsCount;
    }

    @JsonIgnore
    public Set<MainPost> getFavoriteMainpost() {
        return favoriteMainpost;
    }

    public void setFavoriteMainpost(Set<MainPost> favoriteMainpost) {
        this.favoriteMainpost = favoriteMainpost;
    }

    @JsonIgnore
    public Set<Post> getLikedPosts() {
        return likedPosts;
    }

    public void setLikedPosts(Set<Post> likedPosts) {
        this.likedPosts = likedPosts;
    }

    public Set<MainPost> getLikedMainPosts() {
        return likedMainPosts;
    }

    public void setLikedMainPosts(Set<MainPost> likedMainPosts) {
        this.likedMainPosts = likedMainPosts;
    }

    public int getFavNewsCount() {
        return favNewsCount;
    }

    public void setFavNewsCount(int favNewsCount) {
        this.favNewsCount = favNewsCount;
    }

    @JsonIgnore
    public Set<MianaouserinfoBean> getFollowers() {
        return followers;
    }

    public void setFollowers(Set<MianaouserinfoBean> followers) {
        this.followers = followers;
    }

    @JsonIgnore
    public Set<MianaouserinfoBean> getFriends() {
        return friends;
    }

    public void setFriends(Set<MianaouserinfoBean> friends) {
        this.friends = friends;
    }

    @JsonIgnore
    public boolean isFollowed() {
        return followed;
    }

    public void setFollowed(boolean followed) {
        this.followed = followed;
    }

    @JsonIgnore
    public String getLastImei() {
        return lastImei;
    }

    public void setLastImei(String lastImei) {
        this.lastImei = lastImei;
    }

}
