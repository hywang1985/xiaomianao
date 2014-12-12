package com.jianfeng.xiaomianao.domain;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.Iterator;
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

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.ser.SerializerBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jianfeng.xiaomianao.handler.dto.Favoritable;

@Entity
@Table(name = "Newsinfo")
public class NewsInfoBean extends BaseSerializable implements Favoritable{

    private static final long serialVersionUID = 1241791325364752298L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column
    private int category;

    @Column
    private String title;

    @Column
    private String briefContent;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatetime;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date createtime;

    @Column
    private String state;

    @Column
    private String author;

    @Column
    private String image;

    @Column(columnDefinition = "VARCHAR(255) default '小棉袄'")
    private String source;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "news_tags", joinColumns = @JoinColumn(name = "news_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"))
    private Set<Tag> tags;

    /**
     * 收藏了当前资讯的人
     */
    @ManyToMany(mappedBy = "favoriteNews", fetch = FetchType.LAZY)
    private Set<MianaouserinfoBean> favoritors = new LinkedHashSet<MianaouserinfoBean>();

    /**
     * 赞了当前资讯的人
     */
    @ManyToMany(mappedBy = "likedNews", fetch = FetchType.LAZY)
    private Set<MianaouserinfoBean> likers = new LinkedHashSet<MianaouserinfoBean>();

    /**
     * 当前咨询下用户的评论
     */
    @OneToMany(mappedBy = "news", fetch = FetchType.LAZY)
    private Set<Comment> comments = new LinkedHashSet<Comment>();

    /**
     * 标记文章是否为当前用户所收藏
     */
    @Transient
    private boolean favorited;

    /**
     * 标记文章是否为当前用户赞
     */
    @Transient
    private boolean liked;

    /**
     * 是否是首页置顶文章
     */
    @Column(columnDefinition = "BOOLEAN DEFAULT 0")
    private boolean onTop;

    /**
     * 该资讯的回复数
     */
    @Column(name = "reply_count", columnDefinition = "int default 0")
    private int replyCount;

    /**
     * 该资讯被赞的数量
     */
    @Column(name = "like_count", columnDefinition = "int default 0")
    private int likeCount;

    @Transient
    private Date favTime;

    @Transient
    private Date likeTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @JsonSerialize(using = NewsContentSerializer.class, as = String.class)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @JsonSerialize(using = TagSerializer.class)
    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public String getBriefContent() {
        return briefContent;
    }

    public void setBriefContent(String briefContent) {
        this.briefContent = briefContent;
    }

    @JsonIgnore
    public Set<MianaouserinfoBean> getFavoritors() {
        return favoritors;
    }

    public void setFavoritors(Set<MianaouserinfoBean> favoritors) {
        this.favoritors = favoritors;
    }

    public boolean isFavorited() {
        return favorited;
    }

    public void setFavorited(boolean favorited) {
        this.favorited = favorited;
    }

    @JsonIgnore
    public Set<MianaouserinfoBean> getLikers() {
        return likers;
    }

    public void setLikers(Set<MianaouserinfoBean> likers) {
        this.likers = likers;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    @JsonIgnore
    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public boolean isOnTop() {
        return onTop;
    }

    public void setOnTop(boolean onTop) {
        this.onTop = onTop;
    }

    private static final class NewsContentSerializer extends SerializerBase<String> {

        Logger logger = LoggerFactory.getLogger(NewsContentSerializer.class);

        public NewsContentSerializer() {
            super(String.class, true);
        }

        public void serialize(String content, JsonGenerator jgen, SerializerProvider provider) throws IOException,
                JsonGenerationException {
            JSONArray segments = new JSONArray();
            try {
                segments = JSONArray.fromObject(content);
            } catch (Exception e) {
                JSONObject textObject = new JSONObject();
                textObject.put("text", content);
                JSONObject imgObject = new JSONObject();
                imgObject.put("img", "");
                segments.add(textObject);
                segments.add(imgObject);
                logger.info("Content has incorret format");
            } finally {
                jgen.writeObject(segments);
            }
        }

        @Override
        public JsonNode getSchema(SerializerProvider provider, Type typeHint) throws JsonMappingException {
            return null;
        }

    }

    private static final class TagSerializer extends SerializerBase<Set<Tag>> {

        Logger logger = LoggerFactory.getLogger(NewsContentSerializer.class);

        public TagSerializer() {
            super(String.class, true);
        }

        public void serialize(Set<Tag> tags, JsonGenerator jgen, SerializerProvider provider) throws IOException,
                JsonGenerationException {
            StringBuilder sb = new StringBuilder();
            try {
                Iterator<Tag> it = tags.iterator();
                int index = 0;
                while (it.hasNext()) {
                    Tag tag = it.next();
                    sb.append(tag.getName());
                    if (index != tags.size() - 1) {
                        sb.append(",");
                    }
                    index++;
                }
                jgen.writeString(sb.toString());
            } catch (Exception e) {
                logger.info("Content has incorret format");
                jgen.writeString("");
            } finally {
                sb = null;
            }
        }

        @Override
        public JsonNode getSchema(SerializerProvider provider, Type typeHint) throws JsonMappingException {
            return null;
        }

    }

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
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
        if (this.getFavTime().compareTo(o.getFavTime()) > 0) {
            order = -1;
        } else if (this.getFavTime().compareTo(o.getFavTime()) < 0) {
            order = 1;
        }
        return order;
    }

}
