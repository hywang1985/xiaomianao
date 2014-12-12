package com.jianfeng.xiaomianao.handler.impl.community;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jianfeng.xiaomianao.consts.Constants;
import com.jianfeng.xiaomianao.handler.AbstractHandler;
import com.jianfeng.xiaomianao.handler.dto.ClientRequest;
import com.jianfeng.xiaomianao.service.PostService;
import com.jianfeng.xiaomianao.util.JsonUtil;

@Service("handler_806")
public class AddMainPostHandler extends AbstractHandler {

    @Autowired
    private PostService postService;
    
    public Object processInPractice(ClientRequest clientRequest,Object... params) throws Exception {
        Integer communityId = clientRequest.getIntParameter(Constants.COMMUNITY_ID_PARAMETER_KEY);
        String tagsJSON = clientRequest.getParameter(Constants.TAGS_PARAMETER_KEY);
        List<String> tags = JsonUtil.fromJsonArrayToObjects(tagsJSON, String.class);
        String title = clientRequest.getParameter(Constants.TITLE_PARAMETER_KEY);
        String content = clientRequest.getParameter(Constants.CONTENT_PARAMETER_KEY);
        List images = (List)clientRequest.getObjectParameter(Constants.IMAGES_PARAMEER_KEY);
        logger.info("handler_806 创建主贴: communityId:{},title:{},",new String[]{communityId.toString(),title});
        return postService.addMainPost(getUserid(clientRequest), communityId, title,content, tags,images).getId();
    }
}
