package com.jianfeng.xiaomianao.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("2")
public class CommunityClassification extends Classification {

    private static final long serialVersionUID = 9197183876513838158L;

    CommunityClassification() {

    }
}
