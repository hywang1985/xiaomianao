package com.jianfeng.xiaomianao.processor.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jianfeng.xiaomianao.dao.MainPostDao;
import com.jianfeng.xiaomianao.domain.MainPost;
import com.jianfeng.xiaomianao.handler.dto.ClientRequest;
import com.jianfeng.xiaomianao.handler.dto.DTOFactory;
import com.jianfeng.xiaomianao.handler.dto.post.MainPostDTO;
import com.jianfeng.xiaomianao.processor.AbstractPostProcessor;

@Service("processor_212")
public class FindMainPostByIdProcessor extends AbstractPostProcessor<MainPostDTO, MainPost> {

    @Autowired
    private MainPostDao mainpostDao;
    
    public MainPostDTO postProcess(ClientRequest clientRequest, MainPost processedResult, String userId) throws Exception {
       if(processedResult!=null){
           setLiked(userId, processedResult);
           setFavorited(userId, processedResult);
           increaseVisits(processedResult);
       }
        return DTOFactory.eInstance.createMainPostFullDTO().converMainPost(processedResult);
    }

    protected void increaseVisits(MainPost processedResult) {
        processedResult.setVisits(processedResult.getVisits()+1);
        mainpostDao.update(processedResult);
    }

}
