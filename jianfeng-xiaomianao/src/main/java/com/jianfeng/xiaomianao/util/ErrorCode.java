package com.jianfeng.xiaomianao.util;

import java.util.HashMap;
import java.util.Map;

public enum ErrorCode {

    OK("0", "成功"),
    SystemError("9999", "服务器错误"),
    PARAMTER_ERROR("9001", "参数错误"),
    NOTMETHOD("9002", "无对应method"),
    HTTPERROR("9003", "http请求错误"),
    Token_NotValid("100001", "Token无效"),
    USER_NOT_EXIST("10001", "用户不存在"),
    USER_ALREADY_EXIST("10002","用户已经存在"),
    USER_NAME_PASSWORD_NULL("10003", "用户名或密码为空"),
    USER_NAME_INVALIDATED("10004","用户名不合法"),
    USER_PASSWORD_INVALIDATED("10005","密码不合法"),
    INCORRECT_FILE_TYPE("10006","文件类型不合法"),
    INCORRECT_FOLLOWE("10007","用户已经被关注"),
    INCORRECT_UNFOLLOW("10008","用户不在关注列表里"),
    ALREADY_LIKED_NEWS("10009","已经赞过此篇资讯"),
    NEWS_HAS_NOT_BEEN_LIKED("10010","没赞过此篇资讯"),
    ALREADY_LIKED_MAINPOST("100011","已经赞过此帖"),
    MAINPOST_HAS_NOT_BEEN_LIKED("100012","没赞过此贴"),
    ALREADY_LIKED_POST("100013","已经赞过此评论"),
    POST_HAS_NOT_BEEN_LIKED("100014","没赞过此评论"),
    ALREADY_FAVORITED_NEWS("100015","已经收藏过此资讯"),
    NEWS_HAS_NOT_BEEN_FAVORITED("100016","没收藏过此资讯"),
    ALREADY_FAVORITED_MAINPOST("100017","已经收藏过此贴"),
    MAINPOST_HAS_NOT_BEEN_FAVORITED("100018","没有收藏过此贴"),
    INTERNERL_PARAMETER_INVALID("100019","handler参数不合法"),
    FOLLOW_ILLEGAL_USER("100020","关注的用户不合法"),
    PUSH_PREFERENCE_NOT_FIND("100021","找不到推送设置选项");


    public String value;

    public String memo;

    private ErrorCode(String value, String memo) {
        this.value = value;
        this.memo = memo;
    }

    private static final Map<String, String> lookup = new HashMap<String, String>();

    private static final Map<String, ErrorCode> lookupErrorCode = new HashMap<String, ErrorCode>();

    static {
        for (ErrorCode code : values()) {
            lookup.put(code.value, code.memo);
            lookupErrorCode.put(code.value, code);
        }
    }

    public static String get(String value) {
        return lookup.get(value);
    }

    public static ErrorCode getErrorCode(String value) {
        return lookupErrorCode.get(value);
    }

    public static Map<String, String> getAllMap() {
        return lookup;
    }
}
