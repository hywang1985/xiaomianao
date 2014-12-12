package com.jianfeng.xiaomianao.handler;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.jianfeng.xiaomianao.consts.Constants;
import com.jianfeng.xiaomianao.domain.AuthenticationToken;
import com.jianfeng.xiaomianao.exception.XiaoMianAoException;
import com.jianfeng.xiaomianao.handler.dto.ClientRequest;
import com.jianfeng.xiaomianao.processor.IPostProcessor;
import com.jianfeng.xiaomianao.service.AuthenticationTokenService;
import com.jianfeng.xiaomianao.service.QueryService;
import com.jianfeng.xiaomianao.util.ErrorCode;

public abstract class AbstractHandler implements Handler {

    protected Logger logger = LoggerFactory.getLogger(AbstractHandler.class);

    @Autowired(required = false)
    protected Map<String, IPostProcessor> postProcessors;

    @Autowired
    protected AuthenticationTokenService tokenService;

    @Autowired
    protected QueryService queryService;

    protected String method;
    
    @Override
    public boolean checkToken() {
        return true;
    }

    protected String getUserid(ClientRequest clientRequest) throws Exception {
        String userid = "";
        if (StringUtils.isNotBlank(clientRequest.getUserid())) {
            userid = clientRequest.getUserid();
        }
        if (StringUtils.isBlank(userid)) {
            AuthenticationToken token = tokenService.getToken(clientRequest.getToken());
            if (null != token) {
                userid = token.getUserid();
            }
        }
        return userid;
    }

    protected String getUseridByToken(String token) throws Exception {
        AuthenticationToken authenticationToken = tokenService.getToken(token);
        if (null != authenticationToken) {
            return authenticationToken.getUserid();
        }
        return null;
    }

    
    protected abstract Object processInPractice(ClientRequest clientRequest, Object... params) throws Exception;

    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected EventsContainedHandlerResult postProcess(ClientRequest clientRequest, Object initialResult) throws Exception {
        EventsContainedHandlerResult toReturn = null;
        if(initialResult instanceof EventsContainedHandlerResult){
            toReturn = (EventsContainedHandlerResult) initialResult;
        }else{
            toReturn = new EventsContainedHandlerResult();
            toReturn.setData(initialResult);
        }
        if (postProcessors != null && !postProcessors.isEmpty()) {
            IPostProcessor processer = postProcessors.get(StringUtils.join(new String[] { "processor", method }, "_"));
            if (processer != null) {
                String userid = getUserid(clientRequest);
                Object processedData = processer.postProcess(clientRequest, toReturn.getData(), userid);
                toReturn.setData(processedData);
            }
        }
        return toReturn;
    }

    @Override
    public EventsContainedHandlerResult process(ClientRequest clientRequest, Object... params) throws Exception {
        Object initialResult = processInPractice(clientRequest, params);
        return postProcess(clientRequest, initialResult);
    }
    

    protected Integer getFirstResult(ClientRequest clientRequest) {
        return clientRequest.getIntParameter(Constants.FIRST_RESULT_PARAMETER_KEY);
    }

    protected Integer getMaxResult(ClientRequest clientRequest) {
        return clientRequest.getIntParameter(Constants.MAX_RESULTS_PARAMETER_KEY);
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
    
    protected void validateBodyParameter(String parameterName, String parameter) {
        if (parameter == null || StringUtils.isBlank(parameter)) {
            XiaoMianAoException xiaoMianAoException = new XiaoMianAoException(ErrorCode.PARAMTER_ERROR);
            xiaoMianAoException.setErrorValue("Body parameter " + parameterName + " is invalid, make sure it's not empty or null.");
            throw xiaoMianAoException;
        }
    }
}
