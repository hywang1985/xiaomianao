package com.jianfeng.xiaomianao.domain;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnore;

@Entity
@Table(name = "mianao_state")
public class MianaoState extends BaseSerializable {

    private static final long serialVersionUID = 1587914782990104863L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToMany(mappedBy = "mianaoStates", fetch = FetchType.LAZY)
    private Set<MianaouserinfoBean> owners;

    @Transient
    private boolean owned;

    MianaoState() {

    }

    @JsonIgnore
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    public Set<MianaouserinfoBean> getOwners() {
        return owners;
    }

    public void setOwners(Set<MianaouserinfoBean> owners) {
        this.owners = owners;
    }

    public boolean isOwned() {
        return owned;
    }

    public void setOwned(boolean owned) {
        this.owned = owned;
    }
}
