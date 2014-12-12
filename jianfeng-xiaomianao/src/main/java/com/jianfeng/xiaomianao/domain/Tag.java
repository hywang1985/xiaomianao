package com.jianfeng.xiaomianao.domain;

import java.util.Set;

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
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * 资讯的标签
 */
@Entity
@Table(name = "tags")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tag_type", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("1")
public class Tag extends BaseSerializable {

    private static final long serialVersionUID = -6518061694774182910L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Integer id;

    @Column(nullable = false)
    protected String name;

    @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY)
    private Set<NewsInfoBean> News;

    @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY)
    private Set<Classification> classes;

    @ManyToMany(mappedBy = "subscribedTags", fetch = FetchType.LAZY)
    private Set<MianaouserinfoBean> subscribers;

    /**
     * 标签是否被当前用户订阅
     */
    @Transient
    private boolean subscribed;

    Tag() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    public Set<NewsInfoBean> getNews() {
        return News;
    }

    public void setNews(Set<NewsInfoBean> news) {
        News = news;
    }

    public boolean isSubscribed() {
        return subscribed;
    }

    public void setSubscribed(boolean subscribed) {
        this.subscribed = subscribed;
    }

    @JsonIgnore
    public Set<MianaouserinfoBean> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(Set<MianaouserinfoBean> subscribers) {
        this.subscribers = subscribers;
    }

    @JsonIgnore
    public Set<Classification> getClasses() {
        return classes;
    }

    public void setClasses(Set<Classification> classes) {
        this.classes = classes;
    }

}
