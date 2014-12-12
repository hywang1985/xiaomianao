package com.jianfeng.xiaomianao.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jianfeng.xiaomianao.consts.ActionCode;
import com.jianfeng.xiaomianao.exception.XiaoMianAoException;
import com.jianfeng.xiaomianao.handler.EventsContainedHandlerResult;
import com.jianfeng.xiaomianao.handler.Handler;
import com.jianfeng.xiaomianao.handler.dto.ClientRequest;
import com.jianfeng.xiaomianao.handler.dto.Success;
import com.jianfeng.xiaomianao.push.DynamicEventRouter;
import com.jianfeng.xiaomianao.push.MessageHandler;
import com.jianfeng.xiaomianao.push.event.AddPostEvent;
import com.jianfeng.xiaomianao.push.event.FollowUserEvent;
import com.jianfeng.xiaomianao.push.event.LikeMainPostEvent;
import com.jianfeng.xiaomianao.push.event.MianaoEvent;
import com.jianfeng.xiaomianao.push.handler.AddPostEventHandler;
import com.jianfeng.xiaomianao.push.handler.FollowUserEventHandler;
import com.jianfeng.xiaomianao.push.handler.LikeMainPostEventHandler;
import com.jianfeng.xiaomianao.push.handler.MianaoEventHandler;
import com.jianfeng.xiaomianao.service.AuthenticationTokenService;
import com.jianfeng.xiaomianao.util.ErrorCode;
import com.jianfeng.xiaomianao.util.StringUtil;

/**
 * This class dispatched the http requests from client to specific handler. It must's be included in package
 * com.jianfeng.xiaomianao.handler, cause aop point-cuts were added on all the handler class of that package, it will
 * cause an issue client can't receive the errorcode of uncaught exceptions which would be forward to the specific page
 * via Spring's exception handler.
 * 
 * @update hywang
 */
@Service
public class HandlerDispatcher implements DynamicEventRouter<MianaoEvent>,BeanFactoryAware {

    private Logger logger = LoggerFactory.getLogger(HandlerDispatcher.class);

    @Autowired
    private Map<String, Handler> handlers;
    
    private Map<Class<? extends MianaoEvent>, MianaoEventHandler> eventHandlers = new HashMap<Class<? extends MianaoEvent>, MianaoEventHandler>();

    @Autowired
    private AuthenticationTokenService tokenService;
    
    private BeanFactory beanFactory;
    
    @PostConstruct
    private void injectEventHandlers() {
        registerEventHandler(LikeMainPostEvent.class, new LikeMainPostEventHandler());
        registerEventHandler(AddPostEvent.class,new AddPostEventHandler());
        registerEventHandler(FollowUserEvent.class, new FollowUserEventHandler());
    }

    public Success process(HttpServletRequest request) {
        long start = System.currentTimeMillis();
        String udid = "unknow";
        ErrorCode errorcode = ErrorCode.OK;
        String errorDetail = errorcode.value;
        String body = request.getParameter("body");
        try {
            if (StringUtil.isEmpty(body)) {
                return new Success(udid, ErrorCode.PARAMTER_ERROR.value, "body is null");
            }
            ClientRequest clientRequest = ClientRequest.fromJsonToClientRequest(body);
            udid = clientRequest.getUdid();
            String method = clientRequest.getMethod();
            String methodMemo = ActionCode.getMethodMemo(method);
            logger.info("handler 请求udid:{}, method:{}, memo:{}", new String[] { udid, method, methodMemo });
            Handler handler = handlers.get(StringUtil.join("_", "handler", method));
            if (null != handler) {
                try {
                    // if (handler.checkToken()) {
                    // String userid = tokenService.checkToken(clientRequest.getToken());
                    // logger.info("udid:{}, userid:{}, token:{}", new Object[] { udid, userid, clientRequest.getToken()
                    // });
                    // } else {
                    // tokenService.checkOfflineToken(udid, clientRequest.getToken());
                    // }
                    handler.setMethod(method);
                    EventsContainedHandlerResult result = handler.process(clientRequest,request);
                    //events should be fired after the handler is processed because the data should be commit before fire events.
                    List<MianaoEvent> eventsToFire = result.getEvents();
                    if (eventsToFire != null && !eventsToFire.isEmpty()) {
                        for (MianaoEvent event : eventsToFire) {
                            fireEvent(event);
                        }
                    }
                    return new Success(udid, errorcode.value, result.getData());
                } catch (XiaoMianAoException e) {
                    errorcode = null == e.getErrorCode() ? ErrorCode.SystemError : e.getErrorCode();
                    errorDetail = errorcode.value + (e.getErrorValue() == null ? "" : ": "+e.getErrorValue());
                    logger.error("handler请求出错,udid:" + udid + ", errorcode:" + errorcode.value + ", errormsg:" + e.getMessage(),
                            e);
                } catch (Exception e) {
                    errorcode = ErrorCode.SystemError;
                    logger.error("handler请求出错,error udid:" + udid, e);
                } finally {
                    logger.debug("udid:{}, method:{}, memo:{}, executeTime:{}",
                            new Object[] { udid, method, methodMemo, System.currentTimeMillis() - start });
                }
            } else {
                errorcode = ErrorCode.NOTMETHOD;
            }
        } catch (Throwable e) {
            logger.error("process error, body" + body, e);
            errorcode = ErrorCode.SystemError;
        }
        return new Success(udid, errorDetail, errorcode.memo);
    }

    public Map<String, Object> xmppCallback(String body) {
        String udid = "unknow";
        try {
            if (StringUtil.isEmpty(body)) {
                return getMap(udid, false, "body is null");
            }
            ClientRequest clientRequest = ClientRequest.fromJsonToClientRequest(body);
            udid = clientRequest.getUdid();
            String method = clientRequest.getMethod();
            logger.debug("udid:{}, method:{}", new String[] { udid, method });
            Handler handler = handlers.get(StringUtil.join("_", "handler", method));
            if (null != handler) {
                try {
                    return getMap(udid, true, handler.process(clientRequest));
                } catch (Throwable e) {
                    logger.error("handler请求出错,udid:" + udid, e);
                    return getMap(udid, false, e.getMessage());
                }
            }
            return getMap(udid, false, "no method");
        } catch (Throwable e) {
            logger.error("process error, body" + body, e);
            return getMap(udid, false, e.getMessage());
        }
    }

    private Map<String, Object> getMap(String udid, boolean success, Object entity) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("udid", udid);
        map.put("success", success);
        map.put("entity", entity);
        return map;
    }

    @Override
    public void registerEventHandler(Class<? extends MianaoEvent> event,
            MessageHandler<? extends MianaoEvent> eventHandler) {
        eventHandlers.put(event, (MianaoEventHandler)eventHandler);
    }
    

    @Override
    public void fireEvent(MianaoEvent event) throws Exception {
        
        if (eventHandlers != null && !eventHandlers.isEmpty()) {
            eventHandlers.get(event.getClass()).handle(event, beanFactory);
        }
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
            this.beanFactory = beanFactory;
    }


}
