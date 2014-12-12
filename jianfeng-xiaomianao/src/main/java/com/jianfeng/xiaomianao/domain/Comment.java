package com.jianfeng.xiaomianao.domain;

import java.io.IOException;
import java.lang.reflect.Type;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

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

@Entity
@Table(name = "comment")
public class Comment extends BaseSerializable {

    private static final long serialVersionUID = -4177534163612471764L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    /**
     * 所属的资讯
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "news_id")
    private NewsInfoBean news;

    @Column
    private String content;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatetime;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date createtime;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_id")
    private MianaouserinfoBean owner;

    @ManyToOne(fetch = FetchType.EAGER)
    private Comment parent;

    @OneToMany(mappedBy = "parent")
    private Set<Comment> children = new LinkedHashSet<Comment>();

    /**
     * 判断当前评论是否被当前用户拥有
     */
    @Transient
    private boolean owned;

    Comment() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    @JsonSerialize(using = ParentSerializer.class)
    @JsonIgnore
    public Comment getParent() {
        return parent;
    }

    public void setParent(Comment parent) {
        this.parent = parent;
    }

    @JsonSerialize(using = OwnerSerializer.class)
    public MianaouserinfoBean getOwner() {
        return owner;
    }

    public void setOwner(MianaouserinfoBean owner) {
        this.owner = owner;
    }

    @JsonIgnore
    public NewsInfoBean getNews() {
        return news;
    }

    public void setNews(NewsInfoBean news) {
        this.news = news;
    }

    @JsonIgnore
    public Set<Comment> getChildren() {
        return children;
    }

    public void setChildren(Set<Comment> children) {
        this.children = children;
    }

    public boolean isOwned() {
        return owned;
    }

    public void setOwned(boolean owned) {
        this.owned = owned;
    }

    private static final class OwnerSerializer extends SerializerBase<MianaouserinfoBean> {

        Logger logger = LoggerFactory.getLogger(OwnerSerializer.class);

        public OwnerSerializer() {
            super(MianaouserinfoBean.class, true);
        }

        public void serialize(MianaouserinfoBean owner, JsonGenerator jgen, SerializerProvider provider) throws IOException,
                JsonGenerationException {
            String userId = owner.getUserid();
            String userName = owner.getUsername();
            String snapshot = owner.getSnapshot();
            String nickName = owner.getNickName();
            JSONObject ownerObject = new JSONObject();
            ownerObject.put("userId", userId);
            ownerObject.put("userName", userName);
            ownerObject.put("snapshot", snapshot);
            ownerObject.put("nickName", nickName);
            jgen.writeObject(ownerObject);
        }

        @Override
        public JsonNode getSchema(SerializerProvider provider, Type typeHint) throws JsonMappingException {
            return null;
        }
    }
    
    private static final class ParentSerializer extends SerializerBase<Comment> {

        Logger logger = LoggerFactory.getLogger(OwnerSerializer.class);

        public ParentSerializer() {
            super(Comment.class, true);
        }

        public void serialize(Comment parent, JsonGenerator jgen, SerializerProvider provider) throws IOException,
                JsonGenerationException {
            JSONObject parentObject = new JSONObject();
            String content = parent.getContent();
            String userId = parent.getOwner().getUserid();
            Integer parentId = parent.getId();
            String userName = parent.getOwner().getUsername();
            String snapshot = parent.getOwner().getSnapshot();
            String nickName = parent.getOwner().getNickName();
            parentObject.put("userId", userId);
            parentObject.put("userName", userName);
            parentObject.put("snapshot",snapshot);
            parentObject.put("parentId", parentId);
            parentObject.put("content", content);
            parentObject.put("nickName", nickName);
            jgen.writeObject(parentObject);
        }

        @Override
        public JsonNode getSchema(SerializerProvider provider, Type typeHint) throws JsonMappingException {
            return null;
        }
    }
}
