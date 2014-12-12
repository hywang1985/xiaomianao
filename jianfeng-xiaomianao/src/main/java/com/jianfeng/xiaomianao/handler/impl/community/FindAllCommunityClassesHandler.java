package com.jianfeng.xiaomianao.handler.impl.community;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.jianfeng.xiaomianao.domain.CommunityClassification;
import com.jianfeng.xiaomianao.handler.AbstractHandler;
import com.jianfeng.xiaomianao.handler.dto.ClientRequest;
import com.jianfeng.xiaomianao.handler.dto.community.CommunityClassDTO;

@Service("handler_302")
public class FindAllCommunityClassesHandler extends AbstractHandler {

    public Object processInPractice(ClientRequest clientRequest,Object... params) throws Exception {
        logger.info("handler_302 查询社区分类");
        List<CommunityClassification> communityClasses = queryService.findCommunityClasses(getFirstResult(clientRequest), getMaxResult(clientRequest));
        List<CommunityClassDTO> toReturn = null;
        if (communityClasses != null && !communityClasses.isEmpty()) {
            toReturn = new ArrayList<CommunityClassDTO>();
            for (CommunityClassification classification : communityClasses) {
                toReturn.add(CommunityClassDTO.ConvertCommunityClass(classification));
            }
        }
        return toReturn;
    }
}
