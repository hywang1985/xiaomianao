package com.jianfeng.xiaomianao.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jianfeng.xiaomianao.consts.Constants;
import com.jianfeng.xiaomianao.domain.AuthenticationToken;
import com.jianfeng.xiaomianao.domain.DomainEntityFactory;
import com.jianfeng.xiaomianao.domain.MianaouserinfoBean;
import com.jianfeng.xiaomianao.domain.PushPreference;
import com.jianfeng.xiaomianao.exception.XiaoMianAoException;
import com.jianfeng.xiaomianao.util.CipherUtil;
import com.jianfeng.xiaomianao.util.DateUtil;
import com.jianfeng.xiaomianao.util.ErrorCode;
import com.jianfeng.xiaomianao.util.RandomUtil;

@Service
public class UserService extends AbstractUserNeededService {

    //Need to change this enum once while push event changed.
    private static enum EVENT_TYPE {
        LIKE_MAINPOST(1),
        ADD_POST(2),
        FOLLOW_USER(3);

        private int type;

        private EVENT_TYPE(int type) {
            this.type = type;
        }
    }
    
    private static final String USERNAME_PATTERN_EXPRESSION = "^[a-z0-9_-]{3,15}$";

    /*
     * 6 to 20 characters Must contain at least one digit; Must contain at least one letter (case insensitive); Can
     * contain the following characters: !@#$%&*
     */
    private static final String PASSWORD_PATTERN_EXPRESSION = "^((?=.*\\d)(?=.*[a-zA-Z])[a-zA-Z0-9!@#$%&*]{6,20})$";

    private static final Pattern USERNAME_PATTERN = Pattern.compile(USERNAME_PATTERN_EXPRESSION);

    private static final Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_PATTERN_EXPRESSION);

    @Autowired
    private AuthenticationTokenService tokenService;

    public String register(String userName, String passWord, String channel, String imei) {
        validateInternalParameter(Constants.IMEI_PARAMETER_KEY, imei);
        validateUserName(userName);
        validatePassword(passWord);
        judgeUserExist(userName);
        AuthenticationToken generatedToken = registerUser(userName, passWord, channel, imei);
        return generatedToken.getToken();
    }

    private AuthenticationToken registerUser(String userName, String passWord, String channel, String imei) {
        validateInternalParameter(Constants.IMEI_PARAMETER_KEY, imei);
        MianaouserinfoBean newUser = DomainEntityFactory.eInstance.createMianaoUser();
        newUser.setUsername(userName);
        newUser.setUserid(RandomUtil.getUUID());
        newUser.setCheckcode(CipherUtil.generateMD5Password(passWord));
        newUser.setChannel(channel);
        newUser.setCreatetime(DateUtil.getCurrentDate());
        newUser.setStatue(0);
        newUser.setLastImei(imei);
        userDao.create(newUser);
        setDefaultPushPreferences(newUser.getUserid());
        String userId = newUser.getUserid();
        AuthenticationToken generatedToken = tokenService.createToken(userId);
        return generatedToken;
    }
    
    private void setDefaultPushPreferences(String userId){
        
        for(EVENT_TYPE eventType:EVENT_TYPE.values()){
            PushPreference preference = DomainEntityFactory.eInstance.createPushPreference();
            preference.setUserId(userId);
            preference.setEventType(eventType.type);
            preferenceDao.create(preference);
        }
    }
    
    public MianaouserinfoBean updatePushPreference(String userId,Integer eventType,Boolean isSendNotification){
        MianaouserinfoBean user = findUser(userId);
        PushPreference preference = preferenceDao.findPushPreference(userId, eventType);
        if (preference != null) {
            if(isSendNotification!=null && isSendNotification.compareTo(Boolean.valueOf(preference.isSendNotification()))!=0){
                preference.setSendNotification(isSendNotification);
                preferenceDao.update(preference);
            }
        }else{
            throw new XiaoMianAoException(ErrorCode.PUSH_PREFERENCE_NOT_FIND);
        }
        return user;
    }

    private void validateUserName(final String userName) {
        if (userName == null || StringUtils.isBlank(userName)) {
            throw new XiaoMianAoException(ErrorCode.USER_NAME_PASSWORD_NULL);
        }
        if (!USERNAME_PATTERN.matcher(userName).matches()) {
            throw new XiaoMianAoException(ErrorCode.USER_NAME_INVALIDATED);
        }
    }

    private void validatePassword(String passWord) {
        if (passWord == null || StringUtils.isBlank(passWord)) {
            throw new XiaoMianAoException(ErrorCode.USER_NAME_PASSWORD_NULL);
        }
        if (!PASSWORD_PATTERN.matcher(passWord).matches()) {
            throw new XiaoMianAoException(ErrorCode.USER_PASSWORD_INVALIDATED);
        }
    }

    private void judgeUserExist(String userName) {
        MianaouserinfoBean existUser = userDao.findUserByUserName(userName);
        if (existUser != null) {
            throw new XiaoMianAoException(ErrorCode.USER_ALREADY_EXIST);
        }
    }

    public String login(String userName, String passWord, String imei) {

        validateInternalParameter(Constants.IMEI_PARAMETER_KEY, imei);
        validateInternalParameter(Constants.USER_NAME_PARAMETER_KEY, userName);
        validateInternalParameter(Constants.PASS_WORD_PARAMETER_KEY, passWord);

        MianaouserinfoBean user = userDao.findUserByUserName(userName);
        if (user == null) {
            throw new XiaoMianAoException(ErrorCode.USER_NOT_EXIST);
        }
        if (!CipherUtil.validatePassword(user.getCheckcode(), passWord)) {
            throw new XiaoMianAoException(ErrorCode.USER_NOT_EXIST);
        }
        String userId = user.getUserid();
        AuthenticationToken oldToken = tokenService.getTokenByUserid(userId);
        // validated user but has no token,regenerate a token for the user.
        String tokenToReturn = null;
        if (oldToken == null) { // If first time login,need to setup last imei.
            AuthenticationToken generatedToken = tokenService.createToken(userId);
            tokenToReturn = generatedToken.getToken();
        } else {
            // Should not allow same user login in via different clients.
            // If login in to another device the token should be regenerated.
            if (user.getLastImei() != null && !user.getLastImei().equals(imei)) {
                logger.info("User login on different client: from: {},to: {}, userId: {}", new String[] { user.getLastImei(),
                        imei, userId });
                tokenService.delToken(oldToken.getToken());
                tokenToReturn = tokenService.createToken(userId).getToken();
                user.setLastImei(imei);
                userDao.update(user);
            } else {
                tokenToReturn = oldToken.getToken();
            }
        }
        return tokenToReturn;
    }

    public String logout(String token) {
        validateInternalParameter(Constants.TOKEN_PARAMETER_KEY, token);
        AuthenticationToken retrevedToken = tokenService.getToken(token);
        if (retrevedToken == null) {
            throw new XiaoMianAoException(ErrorCode.Token_NotValid);
        }
        tokenService.delToken(token);
        return retrevedToken.getUserid();
    }

    public MianaouserinfoBean updateNickName(String userId, String nickName) {
        MianaouserinfoBean user = findUser(userId);
        user.setNickName(nickName);
        return userDao.update(user);
    }

    public MianaouserinfoBean updateUserLocation(String userId, String location) {
        MianaouserinfoBean user = findUser(userId);
        user.setLocation(location);
        return userDao.update(user);
    }

    public MianaouserinfoBean UpdateBirth(String userId, String birth) throws ParseException {
        MianaouserinfoBean user = findUser(userId);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(birth);
        user.setBirth(date);
        return userDao.update(user);
    }

    public MianaouserinfoBean UpdateSignature(String userId, String signature) {
        MianaouserinfoBean user = findUser(userId);
        user.setSignature(signature);
        return userDao.update(user);
    }

    public MianaouserinfoBean findUserByToken(String token) {
        AuthenticationToken retrevedToken = tokenService.getToken(token);
        if (retrevedToken == null) {
            throw new XiaoMianAoException(ErrorCode.Token_NotValid);
        }
        String userId = retrevedToken.getUserid();
        return findUser(userId);
    }

    public MianaouserinfoBean follow(String userId, String targetUserId) {

        if (userId != null && targetUserId != null && userId.equals(targetUserId)) {
            throw new XiaoMianAoException(ErrorCode.FOLLOW_ILLEGAL_USER);
        }
        MianaouserinfoBean user = findUser(userId);
        MianaouserinfoBean targetUser = findUser(targetUserId);
        Set<MianaouserinfoBean> existFollowers = targetUser.getFollowers();
        Set<MianaouserinfoBean> friends = user.getFriends();
        if (!existFollowers.contains(user) && !friends.contains(targetUser)) {
            existFollowers.add(user);
            targetUser.setFollowersCount(targetUser.getFollowersCount() + 1);
            friends.add(targetUser);
            user.setFriendsCount(user.getFriendsCount() + 1);
            userDao.update(targetUser);
            userDao.update(user);
        } else {
            throw new XiaoMianAoException(ErrorCode.INCORRECT_FOLLOWE);
        }
        return user;
    }

    public MianaouserinfoBean unfollow(String userId, String targetUserId) {
        MianaouserinfoBean user = findUser(userId);
        MianaouserinfoBean targetUser = findUser(targetUserId);
        Set<MianaouserinfoBean> existFollowers = targetUser.getFollowers();
        Set<MianaouserinfoBean> friends = user.getFriends();
        if (existFollowers.contains(user) && friends.contains(targetUser)) {
            existFollowers.remove(user);
            targetUser.setFollowersCount(targetUser.getFollowersCount() - 1);
            friends.remove(targetUser);
            user.setFriendsCount(user.getFriendsCount() - 1);
            userDao.update(targetUser);
            userDao.update(user);
        } else {
            throw new XiaoMianAoException(ErrorCode.INCORRECT_UNFOLLOW);
        }
        return user;
    }

}
