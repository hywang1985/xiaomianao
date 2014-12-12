package com.jianfeng.xiaomianao.consts;

/**
 * 
 * @author zhaokailong
 * @date 2014年6月7日 下午3:57:19
 */
public interface CacheConsts {

    /**
     * 列族名，存储 用户 TOKEN
     */
    public static final String CF_TOKEN = "xiaomianao.cf.token";

    public static final String CF_TOKEN_USERID_INDEX = "xiaomianao.cf.token_userid_index";

    public static final String OFFLINE_CF_TOKEN = "xiaomianao.offline.cf.token";

    public static final String OFFLINE_CF_TOKEN_USERID_INDEX = "xiaomianao.offline.cf.token_userid_index";
    
    public static final String MIANAO_PUSH_CHANNEL = "xiaomianao.pushchannel";
    
    public static final String MIANAO_USER_PUSH_PREFERENCE = "xiaomianao.user_push_preference";
    
    /**
     * 随机码 xcode 存储，后面加上 phoneNumber 每个值有效期为 10分钟
     */
    public static final String CF_XCODE = "xiaomianao.xcode:";

    public static final String LOL_AUTH_XCODE = "xiaomianao.lol_auth.xcode:";

    public static final String CF_XCODE_INTERVAL = "xiaomianao.xcode.interval:";

    /**
     * 用户坐标，这里会存储坐标轨迹 LIST_USER_LOCATION + userid
     */
    public static final String LIST_USER_LOCATION = "xiaomianao.list.user_location:";

    public static final String USER_LOCATION = "xiaomianao.user_location";

    /**
     * 用户的坐标 geoHash 前缀， USER_LOCATION_GEOHASH + userid + : + hashcode
     */
    public static final String USER_LOCATION_GEOHASH = "xiaomianao.user_location.geohash:";

    /**
     * 附近的人的缓冲区前缀，每个用户都会有一个缓冲区，例如 LIST_USER_NEAR_BUFFER + userid 缓冲区主要为了完成翻页操作，当前人如果查看附近时，传递了 pageIndex=1 那么就要重建这个人的缓冲区
     */
    public static final String LIST_USER_NEAR_BUFFER = "xiaomianao.list.nearuser.buffer:";

    public static final String LIST_USER_SAMEREALM_BUFFER = "xiaomianao.list.samerealmuser.buffer:";

    public static final String LIST_USER_PATTERNNICKNAME_BUFFER = "xiaomianao.list.patternnickname.buffer:";

    /**
     * 用于存储用户的好友信息，格式为 FRIEND_KEY + friendShipId + ":" + (userA+userB) 查找时，匹配 *userA* 或者 *userB* 来找到好友 值 存储
     * {"aid":"","bid":""}
     */
    public static final String FRIEND_KEY = "xiaomianao.friend";

    public static final String RANKING_KEY = "xiaomianao.ranking";

    public static final String MY_DIYNAMICMSG_KEY = "xiaomianao.my.diynamicmsg:";

    public static final String OTHER_DIYNAMICMSG_KEY = "xiaomianao.other.diynamicmsg:";

    public static final String FRIEND_DIYNAMICMSG_KEY = "xiaomianao.friend.diynamicmsg:";

    public static final String RECOMMENDFRIEND_KEY = "xiaomianao.recommendfriend:";

    public static final String FANSLIST_KEY = "xiaomianao.fans.list:";

    public static final String FRIENDS_FANS_LIST_KEY = "xiaomianao.friendsandfans.list:";

    public static final String IMEILIST_KEY = "xiaomianao.imei.list";

    public static final String IMEIUSERID_KEY = "xiaomianao.imei.userid";

    public static final String CHARACTER_RANKING_KEY = "xiaomianao.character.ranking";

    public static final String CHARACTER_RANKING_OPERATOR_KEY = "xiaomianao.character.ranking.operator";

    /**
     * 用户记录邂逅每天玩儿的次数
     */
    public static final String ENCOUNTER_PLAYTIME_KEY = "xiaomianao.encounter.playtime";

    /**
     * 用户记录邂逅每天邂逅的人
     */
    public static final String ENCOUNTER_ENCOUNTEREDUSER_KEY = "xiaomianao.encounter.encountereduser";

    /**
     * 按用户名查找用户的索引
     */
    public static final String xiaomianaoUSER_USERNAME_INDEX_KEY = "xiaomianaouser_username_index";

    public static final String xiaomianaoUSER_DEVICETOKEN_INDEX_KEY = "xiaomianaouser_devicetoken_index";

    public static final String PUSHMSGID_KEY = "xiaomianao.push.msgid";

    public static final String WOW_RANKING_CACHE_KEY = "xiaomianao.wow.ranking.cache";

    public static final String WOW_RANKING_INDEX_KEY = "xiaomianao.wow.ranking.index";

    /**
     * 魔女今日最热
     * */
    public static final String MAGICGIRL_EVERYDAY = "xiaomianao.magicgirl.everyday";

    public static final String MAGICGIRL_EVERYDAY_LIST = "xiaomianao.magicgirl.everyday.list";

    public static final String GAMEUSERSHIPCACHE_KEY = "xiaomianao.gameusership.cache";

    public static final String LOL_GameUserShipCache_Key = "xiaomianao.lol.gameusership.cache";

    public static final String CHARACTER_AUTH_TIEM_KEY = "xiaomianao.characterprofile.auth.items";

    public static final String WOW_PVESCORE_LIST = "xiaomianao.pvescore.list";

    /**
     * 世界频道
     * */
    public static final String WORLDMESSAGE_POST_COOLDOWN = "worldmessage.post.cooldown";

    public static final String WORLDMESSAGE_POST_INTERVAL = "worldmessage.post.interval";

    public static final String WORLDMESSAGE_HEAL_INTERVAL = "worldmessage.heal.interval";

    public static final String WORLDMESSAGE_USER_CATALOG = "worldmessage.user.catalog";

    public static final String WORLDMESSAGE_USER_CATALOG_LIST = "worldmessage.user.catalog.list";

    public static final String LOL_REALM_SEARCH_KEY = "xiaomianao:lol:realm";

    public static final String DAOTA_REALM_SEARCH_KEY = "xiaomianao:daota:realm";

    /**
     * 群坐标的 geoHash 前缀， GROUP_LOCATION_GEOHASH + : + gameid + : + groupId + : + hashcode
     */
    public static final String GROUP_LOCATION_GEOHASH = "xiaomianao.group_location.geohash";

    /**
     * 附近的群的缓冲区前缀，每个用户都会有一个缓冲区，例如 LIST_GROUP_NEAR_BUFFER + userid
     */
    public static final String LIST_GROUP_NEAR_BUFFER = "xiaomianao.list.neargroup.buffer:";

    /**
     * 创建群间隔
     * **/
    public static final String GROUP_POST_INTERVAL = "group.post.interval";

    /**
     * 审核加入群间隔
     */
    public static final String GROUP_JOINGROUP_APPLICATION_AUTID_INTERVAL = "group.join_group_application_audit.interval";

    /**
     * 统计每个群当日活跃度
     */
    public static final String HOT_GROUP_EVERYDAY = "group.hotgroup.everyday";

    /**
     * 当日活跃群列表
     */
    public static final String HOT_GROUP_EVERYDAY_LIST = "group.hotgroup.everyday.list";

    /**
     * 昨日活跃群的cache，用于用户查询
     */
    public static final String HOT_GROUP_EVERYDAY_CACHE = "group.hotgroup.everyday.cache";
}
