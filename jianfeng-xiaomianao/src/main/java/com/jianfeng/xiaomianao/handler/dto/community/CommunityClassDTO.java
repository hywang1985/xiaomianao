package com.jianfeng.xiaomianao.handler.dto.community;

import com.jianfeng.xiaomianao.domain.CommunityClassification;
import com.jianfeng.xiaomianao.handler.dto.DTOFactory;

public class CommunityClassDTO {

    private int id;

    private String name;

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

    public static CommunityClassDTO ConvertCommunityClass(CommunityClassification communityClass) {
        CommunityClassDTO communityClassDto = DTOFactory.eInstance.createCommunityClassDTO();
        communityClassDto.id = communityClass.getId();
        communityClassDto.name = communityClass.getName();
        return communityClassDto;
    }
}
