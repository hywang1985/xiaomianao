package com.jianfeng.xiaomianao.consts;

import java.util.HashMap;
import java.util.Map;

public enum ActionCode {

    LOGIN("101", "用户登陆"),
    LOGOUT("102", "用户登出"),
    BOOTINITIALIZATION("103", "用户开机"),
    REGISTER("104", "用户注册"),
    UPDATE_USER_BIRTH("105","更新用户生日"),
    UPDATE_USER_LOCATION("106","更新用户城市"),
    UPDATE_USER_NICKNAME("107","更新用户昵称"),
    UPDATE_USER_SIGNATURE("108","更新用户个性签名"),
    FOLLOW_USER("109","关注用户"),
    UNFOLLOW_USER("110","取消关注用户"),
    UPDATE_PUSH_PREFERENCE("111","更新用户推送设置项"),
    FIND_NEWS_BY_ID("201", "通过id获取资讯"),
    FIND_NEWS("202", "获取指定数量的任意资讯"),
    FIND_NEWS_BY_TAGS("203","通过指定的标签名称获取资讯"),
    FIND_MIANAO_STATES("204","查找所有棉袄状态"),
    FIND_TOP_BAR_NEWS("205","根据类别查找置顶资讯"),
    FIND_COMMUNITY_BY_ID("206","通过id查找社区"),
    FIND_SUBSCRIBED_COMMUNITY("207","查找用户订阅的社区"),
    FIND_MAINPOSTS_COMMUNITY("208","查找社区主贴"),
    FIND_POSTS("209","查找主贴的评论"),
    FIND_DIGEST_MAINPOSTS("210","查找置顶主贴"),
    FIND_COMMUNITY_TAGS("211","查找社区标签列表"),
    FIND_MAINPOST_BY_ID("212","根据ID查找主贴"),
    FIND_COMMUNITY_INTRO_PAGE("213","查询社区简介页"),
    FIND_COMMUNITY_MEMBER_PAGE("214","查询社区个人资料页"),
    FIND_USER_CONCERNED_MAINPOSTS("215","查询用户感兴趣的帖子(发过的/回复过的)"),
    FIND_LIKED_RESOURCE("216","查找用户赞过的资源"),
    SEARCH_FAVORITE_RESOURCE("217","根据关键字搜索用户收藏的资源，包括自查，资讯和主贴"),
    FIND_USER_LIST("218","查找用户列表"),
    FIND_CLASSES("301","获取所有分类"),
    FIND_COMMUNITY_CLASSES("302","获取所有社区分类"),
    FIND_TAGS_BY_CLASSNAME("401","通过类别名称获取标签"),
    FIND_COMMUNITY_BY_CLASSNAME("402","通过类别名称获取社区"),
    SUBSCRIBE_TAGS("501","订阅标签"),
    UNSUBSCRIBE_TAGS("502","取消订阅标签"),
    SUBSCRIBE_MIANAO_STATE("503","订阅棉袄状态"),
    UNSUBSCRIBE_MIANAO_STATE("504","取消订阅棉袄状态"),
    SUBSCRIBE_COMMUNITY("505","订阅社区"),
    UNSUBSCRIBE_COMMUNITY("506","取消订阅社区"),
    FAVORITE_NEWS("601","收藏指定资讯"),
    UNFAVORITE_NEWS("602","取消收藏指定的资讯"),
    FIND_FAVORITE_RESOURCE("603","查询用户收藏的资源"),
    FAVORITE_MAINPOST("605","收藏主贴"),
    UNFAVORITE_MAINPOST("606","取消收藏的主贴"),
    LIKE_NEWS("701","对指定资讯点赞"),
    UNLIKE_NEWS("702","取消对指定资讯的赞"),
    LIKE_POST("703","对指定主贴/评论点赞"),
    UNLIKE_POST("704","取消对指主贴/评论的赞"),
    ADD_COMMENT("801","添加一个评论"),
    FIND_COMMENTS_BY_NEWS_ID("802","根据资讯ID获取该资讯下的评论"),
    UPDATE_COMMENT("803","更新某条评论"),
    DELETE_COMMENT("804","删除某条评论"),
    ADD_POST("805","添加社区评论"),
    ADD_MAIN_POST("806","添加社区主贴"),
    DELETE_POST("807","删除社区评论"),
    UPLOAD_USER_SNAPSHOT("901","上传用户头像"),
    GENERATE_QINIU_UPLOAD_TOKEN("902","生成七牛的上传凭证"),
    ADD_HEALTH_EVALUATOIN_ITEM_RECORDS("1001","记录健康测评选项"),
    BIND_PUSH_CHANNEL_INFO("1101","绑定用户的消息推送通道信息");
    private String code;

    private String memo;

    private static Map<String, String> map = new HashMap<String, String>();

    static {
        ActionCode[] codes = values();
        for (ActionCode code : codes) {
            map.put(code.getCode(), code.getMemo());
        }
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    private ActionCode(String code, String memo) {
        this.code = code;
        this.memo = memo;
    }

    public static String getMethodMemo(String method) {
        if (!map.containsKey(method)) {
            return "Unknow, No Method";
        }
        return map.get(method);
    }
}
