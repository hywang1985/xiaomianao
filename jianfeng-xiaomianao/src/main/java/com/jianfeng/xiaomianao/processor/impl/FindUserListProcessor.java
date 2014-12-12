package com.jianfeng.xiaomianao.processor.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.jianfeng.xiaomianao.domain.MianaouserinfoBean;
import com.jianfeng.xiaomianao.handler.dto.ClientRequest;
import com.jianfeng.xiaomianao.handler.dto.DTOFactory;
import com.jianfeng.xiaomianao.handler.dto.user.UserListMemberDTO;
import com.jianfeng.xiaomianao.processor.IPostProcessor;

@Service("processor_218")
public class FindUserListProcessor implements IPostProcessor<List<UserListMemberDTO>, List<MianaouserinfoBean>> {

    public List<UserListMemberDTO> postProcess(ClientRequest clientRequest, List<MianaouserinfoBean> processedResult,
            String userId) throws Exception {
        List<UserListMemberDTO> toReturn = null;
        if (processedResult != null && !processedResult.isEmpty()) {
            toReturn = new ArrayList<UserListMemberDTO>();
            for (MianaouserinfoBean user : processedResult) {
                toReturn.add(DTOFactory.eInstance.createUserListMemberDTO().convert(user));
            }
        }
        return toReturn;
    }

}
