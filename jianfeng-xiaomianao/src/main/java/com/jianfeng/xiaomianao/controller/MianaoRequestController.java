package com.jianfeng.xiaomianao.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jianfeng.xiaomianao.handler.dto.Success;

@Controller
public class MianaoRequestController {

    private Logger logger = LoggerFactory.getLogger(MianaoRequestController.class);

    @Autowired
    private HandlerDispatcher handlerDispatcher;

    @RequestMapping(value = "/request", method = RequestMethod.POST)
    public void request(HttpServletRequest request, HttpServletResponse response) throws Exception {
        long start = System.currentTimeMillis();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        Success success = handlerDispatcher.process(request);
        String json = success.toJson();
        long end = System.currentTimeMillis();
        logger.info("客户端请求返回,executeTime:{}", new Object[] { end - start});
        response.getWriter().write(json);
        response.getWriter().flush();
        response.getWriter().close();
    }
}
