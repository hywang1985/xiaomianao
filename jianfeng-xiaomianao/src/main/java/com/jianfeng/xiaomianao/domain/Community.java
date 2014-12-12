package com.jianfeng.xiaomianao.domain;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
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
import javax.persistence.Table;
import javax.persistence.Transient;

import net.sf.json.JSONArray;

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

import com.jianfeng.xiaomianao.handler.dto.DTOFactory;
import com.jianfeng.xiaomianao.handler.dto.post.MainPostDTO;
import com.jianfeng.xiaomianao.handler.dto.user.CommunityMemberDTO;

/**
 * 社区
 */
@Entity
@Table(name = "community")
public class Community extends BaseSerializable {

    private static final long serialVersionUID = 6608012692504494716L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    /**
     * 社区类别
     */
    @Column
    private String className;

    /**
     * 社区包含的主贴数
     */
    @Column(name = "mainpost_count", columnDefinition = "int default 0")
    private int mainPostCount;

    /**
     * 社区的描述
     */
    @Column
    private String description;

    /**
     * 社区的简介
     */
    @Column
    private String introduction;

    /**
     * 社区的规则
     */
    @Column
    private String rules;

    /**
     * 社区关注者的数量
     */
    @Column(name = "fans_count", columnDefinition = "int default 0")
    private int fansCount;

    /**
     * 社区的名称
     */
    @Column
    private String name;

    /**
     * 最新一条主贴的id
     */
    @Column(name = "last_mainpost_id", columnDefinition = "int default -1")
    private int lastMainpostId;

    /**
     * 最新的主题帖,这个对象只需要在列表页显示，不需要完整的主贴数据
     */
    @Transient
    private MainPostDTO lastMainPost;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "community_tags", joinColumns = @JoinColumn(name = "community_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"))
    private Set<CommunityTag> tags;

    @ManyToMany(mappedBy = "communities", fetch = FetchType.LAZY)
    private Set<MianaouserinfoBean> subscribers;

    /**
     * 社区的管理员
     */
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "community_admins", joinColumns = @JoinColumn(name = "community_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "admin_id", referencedColumnName = "id"))
    private Set<MianaouserinfoBean> admins = new LinkedHashSet<MianaouserinfoBean>();

    /**
     * 群达人的DTO,@see handler-213
     */
    @Transient
    private List<CommunityMemberDTO> advanceFans = new ArrayList<CommunityMemberDTO>();

    /**
     * 管理员DTO,@see handler-213
     */
    @Transient
    private List<CommunityMemberDTO> administrators = new ArrayList<CommunityMemberDTO>();

    @Transient
    private boolean subscribed;

    /**
     * 社区的照片
     */
    private String snapshot;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Set<CommunityTag> getTags() {
        return tags;
    }

    public void setTags(Set<CommunityTag> tags) {
        this.tags = tags;
    }

    @JsonIgnore
    public Set<MianaouserinfoBean> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(Set<MianaouserinfoBean> subscribers) {
        this.subscribers = subscribers;
    }

    public boolean isSubscribed() {
        return subscribed;
    }

    public void setSubscribed(boolean subscribed) {
        this.subscribed = subscribed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMainPostCount() {
        return mainPostCount;
    }

    public void setMainPostCount(int mainPostCount) {
        this.mainPostCount = mainPostCount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRules() {
        return rules;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }

    public int getFansCount() {
        return fansCount;
    }

    public void setFansCount(int fansCount) {
        this.fansCount = fansCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSnapshot() {
        return snapshot;
    }

    public void setSnapshot(String snapshot) {
        this.snapshot = snapshot;
    }

    public List<CommunityMemberDTO> getAdvanceFans() {
        return advanceFans;
    }

    public void setAdvanceFans(List<CommunityMemberDTO> advanceFans) {
        this.advanceFans = advanceFans;
    }

    public int getLastMainpostId() {
        return lastMainpostId;
    }

    public void setLastMainpostId(int lastMainpostId) {
        this.lastMainpostId = lastMainpostId;
    }

    @JsonIgnore
    public MainPostDTO getLastMainPost() {
        return lastMainPost;
    }

    public void setLastMainPost(MainPostDTO lastMainPost) {
        this.lastMainPost = lastMainPost;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
    
    @JsonIgnore
    public Set<MianaouserinfoBean> getAdmins() {
        return admins;
    }

    public void setAdmins(Set<MianaouserinfoBean> admins) {
        this.admins = admins;
    }

    public List<CommunityMemberDTO> getAdministrators() {
        return administrators;
    }

    public void setAdministrators(List<CommunityMemberDTO> administrators) {
        this.administrators = administrators;
    }

}
