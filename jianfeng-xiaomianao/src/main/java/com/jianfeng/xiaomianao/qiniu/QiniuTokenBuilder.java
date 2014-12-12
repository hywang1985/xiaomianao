package com.jianfeng.xiaomianao.qiniu;

import java.util.HashMap;
import java.util.Map;

import com.qiniu.api.auth.digest.Mac;
import com.qiniu.api.config.Config;
import com.qiniu.api.rs.PutPolicy;

/**
 * Class to generate Qiniu upload token.
 * 
 * @author hywang
 */
public class QiniuTokenBuilder {

    private static final String MIANAO_IMAGE_BUCKET = "xiaomianao-img";

    private static Mac mac = null;
    
    //upload policy parameters
    private static String SCOPE_PARAMETER_KEY = "scope";
    
    private static String EXPIRE_PARAMETER_KEY = "expire";
    
    private static String INSERTONLY_PARAMETER_KEY = "insertOnly";
    
    private static String SAVEKEY_PARAMETER_KEY = "saveKey";
    
    private static String ENDUSER_PARAMETER_KEY = "endUser";
    
    private static String RETURNURL_PARAMETER_KEY = "returnUrl";
    
    private static String RETURNBODY_PARAMETER_KEY = "returnBody";
    
    private static String CALLBACKURL_PARAMETER_KEY = "callbackUrl";
    
    private static String CALLBACKHOST_PARAMETER_KEY = "callbackHost";
    
    private static String CALLBACKBODY_PARAMETER_KEY = "callbackBody";
    
    private static String PERSISTENTOPS_PARAMETER_KEY = "persistentOps";
    
    private static String PERSISTENTNOTIFYURL_PARAMETER_KEY = "persistentNotifyUrl";
    
    private static String PERSISTENTPIPELINE_PARAMETER_KEY = "persistentPipeline";
    
    private static String FSIZELIMIT_PARAMETER_KEY = "fsizeLimit";
    
    private static String DETECTMIME_PARAMETER_KEY = "detectMime";
    
    private static String MIMELIMIT_PARAMETER_KEY = "mimeLimit";
    
    private static final String UPLOAD_CALLBACK_URL = System.getProperty("uploadCallbackUrl"); 
    
    private Map<String,Object> parameters = new HashMap<String,Object>();
    
    static {
        Config.ACCESS_KEY = "TZSCUuEw9nsHxYSu-qSETZu9ZQoW9v242bIBLeDa";
        Config.SECRET_KEY = "VMjUsWVaLdlFomkb0Nk4K66l2cmb1wfW7Mv8gX79";
        mac = new Mac(Config.ACCESS_KEY, Config.SECRET_KEY);
    }
    
    private QiniuTokenBuilder() {
    }
    
    public QiniuTokenBuilder setScope(String scope){
        parameters.put(SCOPE_PARAMETER_KEY, scope);
        return this;
    }
    
    public QiniuTokenBuilder setExpire(long expire){
        parameters.put(EXPIRE_PARAMETER_KEY, expire);
        return this;
    }
    
    public QiniuTokenBuilder setInsertOnly(int insertOnly){
        parameters.put(INSERTONLY_PARAMETER_KEY, insertOnly);
        return this;
    }
    
    public QiniuTokenBuilder setSaveKey(String saveKey){
        parameters.put(SAVEKEY_PARAMETER_KEY, saveKey);
        return this;
    }
    
    public QiniuTokenBuilder setEndUser(String endUser){
        parameters.put(ENDUSER_PARAMETER_KEY, endUser);
        return this;
    }
    
    public QiniuTokenBuilder setReturnUrl(String returnUrl){
        parameters.put(RETURNURL_PARAMETER_KEY, returnUrl);
        return this;
    }
    
    public QiniuTokenBuilder setReturnBody(String returnBody){
        parameters.put(RETURNBODY_PARAMETER_KEY, returnBody);
        return this;
    }
    
    public QiniuTokenBuilder setCallbackUrl(String callbackUrl){
        parameters.put(CALLBACKURL_PARAMETER_KEY, callbackUrl);
        return this;
    }
    
    public QiniuTokenBuilder setCallbackHost(String callbackHost){
        parameters.put(CALLBACKHOST_PARAMETER_KEY, callbackHost);
        return this;
    }
    
    public QiniuTokenBuilder setCallbackBody(String callbackBody){
        parameters.put(CALLBACKBODY_PARAMETER_KEY, callbackBody);
        return this;
    }
    
    public QiniuTokenBuilder setPersistentOps(String persistentOps){
        parameters.put(PERSISTENTOPS_PARAMETER_KEY, persistentOps);
        return this;
    }
    
    public QiniuTokenBuilder setPersistentNotifyUrl(String persistentNotifyUrl){
        parameters.put(PERSISTENTNOTIFYURL_PARAMETER_KEY, persistentNotifyUrl);
        return this;
    }
    
    public QiniuTokenBuilder setPersistentPipeline(String persistentPipeline){
        parameters.put(PERSISTENTPIPELINE_PARAMETER_KEY, persistentPipeline);
        return this;
    }
    
    public QiniuTokenBuilder setFsizeLimit(long fsizeLimit){
        parameters.put(FSIZELIMIT_PARAMETER_KEY, fsizeLimit);
        return this;
    }
    
    public QiniuTokenBuilder setDetectMime(int detectMime){
        parameters.put(DETECTMIME_PARAMETER_KEY, detectMime);
        return this;
    }
    
    public QiniuTokenBuilder setMimeLimit(String mimeLimit){
        parameters.put(MIMELIMIT_PARAMETER_KEY, mimeLimit);
        return this;
    }

    public String generateUploadToken() throws Exception {
        PutPolicy putPolicy = new PutPolicy(MIANAO_IMAGE_BUCKET);
        putPolicy.callbackUrl = (String)parameters.get(CALLBACKURL_PARAMETER_KEY);
        putPolicy.callbackBody = (String)parameters.get(CALLBACKBODY_PARAMETER_KEY);
        putPolicy.saveKey = (String)parameters.get(SAVEKEY_PARAMETER_KEY);
        putPolicy.expires = (Long)parameters.get(EXPIRE_PARAMETER_KEY);
        putPolicy.scope = (String)parameters.get(SCOPE_PARAMETER_KEY);
        Object insertOnly = parameters.get(INSERTONLY_PARAMETER_KEY);
        if(insertOnly!=null){
            putPolicy.insertOnly = (Integer)insertOnly;
        }
        putPolicy.returnBody = (String)parameters.get(RETURNBODY_PARAMETER_KEY);
        return putPolicy.token(mac);
    }
    
    public static QiniuTokenBuilder create(){
        return new QiniuTokenBuilder();
    }

    public static Mac getMac() {
        return mac;
    }
}
