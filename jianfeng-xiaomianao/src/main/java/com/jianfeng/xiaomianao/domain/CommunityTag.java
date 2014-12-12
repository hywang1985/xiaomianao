package com.jianfeng.xiaomianao.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import org.codehaus.jackson.annotate.JsonIgnore;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("2")
public class CommunityTag extends Tag {

    private static final long serialVersionUID = -4187913730836368042L;

    CommunityTag() {

    }

    @JsonIgnore
    public Integer getId() {
        return super.getId();
    }

    @JsonIgnore
    public boolean isSubscribed() {
        // TODO Auto-generated method stub
        return super.isSubscribed();
    }
}
