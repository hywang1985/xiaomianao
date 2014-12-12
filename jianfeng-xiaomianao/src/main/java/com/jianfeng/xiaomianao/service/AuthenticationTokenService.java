package com.jianfeng.xiaomianao.service;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jianfeng.xiaomianao.consts.CacheConsts;
import com.jianfeng.xiaomianao.domain.AuthenticationToken;
import com.jianfeng.xiaomianao.domain.DomainEntityFactory;
import com.jianfeng.xiaomianao.exception.XiaoMianAoException;
import com.jianfeng.xiaomianao.util.ErrorCode;
import com.jianfeng.xiaomianao.util.JsonUtil;
import com.jianfeng.xiaomianao.util.RandomUtil;
import com.jianfeng.xiaomianao.util.StringUtil;

@Service
public class AuthenticationTokenService {

    private Logger logger = LoggerFactory.getLogger(AuthenticationTokenService.class);

    @Autowired
    private JedisPoolService poolService;

    private Map<String, String> tokenCache = new ConcurrentHashMap<String, String>();

    private Map<String, AuthenticationToken> authenticationTokenCache = new ConcurrentHashMap<String, AuthenticationToken>();

    public AuthenticationToken createOfflineToken(String userid, String token, Integer type) {
        AuthenticationToken authenticationToken = DomainEntityFactory.eInstance.createAutenticationToken();
        authenticationToken.setExpire(-1L);
        authenticationToken.setToken(token);
        authenticationToken.setUserid(userid);
        authenticationToken.setCreateDate(new Date());
        authenticationToken.setInvalidType(type);
        poolService.hset(CacheConsts.OFFLINE_CF_TOKEN, authenticationToken.getToken(),
                JsonUtil.toJson(authenticationToken), JedisPoolService.WEEK);
        logger.info("创建离线token,userid:{},token:{}", new Object[] { userid, authenticationToken.toJson() });
        return authenticationToken;
    }

    public AuthenticationToken getOfflineToken(String token) {
        if (!StringUtil.isEmpty(token)) {
            String json = poolService.hget(CacheConsts.OFFLINE_CF_TOKEN, token);
            if (!StringUtil.isEmpty(json)) {
                return AuthenticationToken.fromJsonToAuthenticationToken(json);
            }
        }
        return null;
    }

    public AuthenticationToken createToken(String userid) {
        AuthenticationToken authenticationToken = DomainEntityFactory.eInstance.createAutenticationToken();
        authenticationToken.setExpire(-1L);
        authenticationToken.setToken(RandomUtil.getUUID());
        authenticationToken.setUserid(userid);
        authenticationToken.setCreateDate(new Date());
        poolService.hset(CacheConsts.CF_TOKEN, authenticationToken.getToken(), JsonUtil.toJson(authenticationToken));
        poolService.hset(CacheConsts.CF_TOKEN_USERID_INDEX, userid, authenticationToken.getToken());
        putAuthenticationToken(authenticationToken);
        logger.info("创建登陆token,userid:{},token:{}", new Object[] { userid, authenticationToken.toJson() });
        return authenticationToken;
    }

    private void putAuthenticationToken(AuthenticationToken authenticationToken) {
        if (authenticationTokenCache.size() > 5000) {
            Map<String, AuthenticationToken> oldAuthenticationTokenCache = authenticationTokenCache;
            authenticationTokenCache = new ConcurrentHashMap<String, AuthenticationToken>();
            oldAuthenticationTokenCache.clear();
            logger.info("Clear authenticationTokenCache , size > 5000");
        }
        authenticationTokenCache.put(authenticationToken.getToken(), authenticationToken);
    }

    public AuthenticationToken getToken(String token) {
        if (!StringUtil.isEmpty(token)) {
            if (authenticationTokenCache.containsKey(token)) {
                return authenticationTokenCache.get(token);
            }
            String json = poolService.hget(CacheConsts.CF_TOKEN, token);
            if (!StringUtil.isEmpty(json)) {
                AuthenticationToken _token = AuthenticationToken.fromJsonToAuthenticationToken(json);
                putAuthenticationToken(_token);
                return _token;
            }
        }
        return null;
    }

    public AuthenticationToken getTokenByUserid(String userid) {
        if (!StringUtil.isEmpty(userid)) {
            String token = poolService.hget(CacheConsts.CF_TOKEN_USERID_INDEX, userid);
            if (StringUtils.isNotBlank(token)) {
                String json = poolService.hget(CacheConsts.CF_TOKEN, token);
                if (!StringUtil.isEmpty(json)) {
                    return AuthenticationToken.fromJsonToAuthenticationToken(json);
                }
            }
        }
        return null;
    }

    public void delToken(String token) {
        if (!StringUtil.isEmpty(token)) {
            AuthenticationToken _token = getToken(token);
            authenticationTokenCache.remove(token);
            tokenCache.remove(token);
            poolService.hdel(CacheConsts.CF_TOKEN, token);
            if (_token != null) {
                poolService.hdel(CacheConsts.CF_TOKEN_USERID_INDEX, _token.getUserid());
            }
            logger.info("删除登陆token, token:{}", token);
        }
    }

    public String checkToken(String token) {
        if (tokenCache.containsKey(token)) {
            if (tokenCache.size() > 200000) {
                Map<String, String> oldTokenCache = tokenCache;
                tokenCache = new ConcurrentHashMap<String, String>();
                oldTokenCache.clear();
                logger.info("Clear tokenCache , size > 200000");
            }
            return tokenCache.get(token);
        }
        AuthenticationToken _token = getToken(token);
        if (null == _token) {
            throw new XiaoMianAoException(ErrorCode.Token_NotValid);
        }
        tokenCache.put(token, _token.getUserid());
        return _token.getUserid();
    }

    public void checkOfflineToken(String sn, String token) {
        AuthenticationToken _token = getOfflineToken(token);
        if (null != _token) {
            logger.info("检查到存在离线token,删除登陆token, sn:{}, token:{}", new Object[] { sn, _token.toJson() });
            delToken(token);
            throw new XiaoMianAoException(ErrorCode.Token_NotValid);
        }
    }
}
