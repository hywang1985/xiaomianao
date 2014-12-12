package com.jianfeng.xiaomianao.processor;

import com.jianfeng.xiaomianao.handler.dto.ClientRequest;

/**
 * The IPostProcessor is used to handle after the execution of executeInPractice of handler,say,to retrieve
 * additional data for specific domain object(s) and build/reform the data transform object(s).
 *
 * @author hywang
 */
public interface IPostProcessor<R, T> {

    public R postProcess(ClientRequest clientRequest, T processedResult, String userId) throws Exception;
}
