package com.jianfeng.xiaomianao.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.jianfeng.xiaomianao.domain.MianaouserinfoBean;
import com.jianfeng.xiaomianao.qiniu.QiniuTokenBuilder;
import com.qiniu.api.net.CallRet;
import com.qiniu.api.rs.RSClient;

@Service
public class FileService extends AbstractUserNeededService{

    private static final String RETURN_BODY_EXPRESSION = "{\"key\": $(key)";
    
    private static final String MIANAO_IMG_BUCKET_PREFIX = "http://xiaomianao-img.qiniudn.com/";
    
    private static final String MIANAO_IMG_BUCKET = "xiaomianao-img";

    private Logger logger = LoggerFactory.getLogger(FileService.class);

    private static long TOKEN_EXPIRE_SECONDS = 1200;
    
//    private static String UPLOAD_CALLBACK_URL = null;
    
//    static {
//        Properties prop = new Properties();
//        try {
//            prop.load(FileUploadService.class.getResourceAsStream("/META-INF/spring/fileupload.properties"));
//            UPLOAD_CALLBACK_URL = prop.getProperty("fileupload.callbackurl");
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }

    /**
     * @param userId user's identifier,required
     * @param saveKey the path to store in Qiniu.
     * @return the generated token of Qiniu.
     */
    public String generateUploadToken(String userId,String saveKey) throws Exception {
            findUser(userId);
        QiniuTokenBuilder tokenBuilder = QiniuTokenBuilder.create()
                .setSaveKey(saveKey)
                .setScope(MIANAO_IMG_BUCKET+":"+saveKey)
                .setInsertOnly(0)
//                .setReturnBody(RETURN_BODY_EXPRESSION)
                .setExpire(TOKEN_EXPIRE_SECONDS);
        return tokenBuilder.generateUploadToken();
    }
    
    public void deleteFile(String saveKey){
        RSClient client = new RSClient(QiniuTokenBuilder.getMac());
        CallRet result = client.delete(MIANAO_IMG_BUCKET, saveKey);
        if(result.ok()){
            logger.info("Image: "+saveKey+" has been removed.");
        }
    }
    
    public MianaouserinfoBean uploadSnapshot(String userId,String saveKey){
        MianaouserinfoBean user = findUser(userId);
        String url = generateImageUrl(saveKey);
        user.setSnapshot(url);
        return userDao.update(user);
    }

    public static String generateImageUrl(String saveKey) {
        return MIANAO_IMG_BUCKET_PREFIX+saveKey;
    }
}
