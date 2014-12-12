package com.jianfeng.xiaomianao.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

@Entity
@Table(name = "image")
public class ImageDescriptor extends BaseSerializable {

    private static final long serialVersionUID = 8968530654410241886L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String url;
    
    private String saveKey;

    ImageDescriptor() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @JsonIgnore
    public String getSaveKey() {
        return saveKey;
    }

    
    public void setSaveKey(String saveKey) {
        this.saveKey = saveKey;
    }
}
