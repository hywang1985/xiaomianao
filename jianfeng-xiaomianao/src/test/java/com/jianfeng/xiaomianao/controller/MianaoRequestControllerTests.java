package com.jianfeng.xiaomianao.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import com.jianfeng.xiaomianao.consts.Constants;
import com.jianfeng.xiaomianao.util.ErrorCode;

public class MianaoRequestControllerTests extends AbstractContextControllerTests {

    public static final String TOKEN = "02586AD0DB504B39B447777D32E62785";

    private static final String BODY_PARAM_KEY = "body";

    private static final String TAG_OF_NO1 = "私处";

    private static final String TAG_OF_NO2 = "经期";

    private static final String TAG_OF_NO3 = "妇科疾病";

    private static final String USER_ID = "100008";

    private static JSONArray tagArray = new JSONArray();

    private static JSONArray newsIds = new JSONArray();
    
    private static JSONArray communityIds = new JSONArray();

    private static JSONObject bodyObj = new JSONObject();

    private static JSONObject paramsObj = new JSONObject();

    private static final String CLASS_NAME = "女性保养";

    private static final String ERROR_CODE_OK = "0";

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(this.wac).alwaysExpect(status().isOk()).build();
        resetParameters();
    }
    
    private void resetParameters() {
        bodyObj.clear();
        paramsObj.clear();
        bodyObj.put(Constants.UDID_PARAMETER_KEY, "43-545-83-00");
        tagArray.clear();
        newsIds.clear();
        communityIds.clear();
    }

    // 101-user login and retrieve token
    @Test
    public void testLogin() throws Exception {
        bodyObj.put(Constants.IMEI_PARAMETER_KEY, "thisisimei2");
        bodyObj.put(Constants.METHOD_PARAMETER_KEY, "101");
//        paramsObj.put(Constants.USER_NAME_PARAMETER_KEY, "absfangbaosidd");
//        paramsObj.put(Constants.PASS_WORD_PARAMETER_KEY, "Wd654321");
        paramsObj.put(Constants.USER_NAME_PARAMETER_KEY, "xiaomianao");
        paramsObj.put(Constants.PASS_WORD_PARAMETER_KEY, "123456");
        bodyObj.put(Constants.PARAMS_PARAMETER_KEY, paramsObj);
        performRequest()
        .andDo(print())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.errorcode").value(ERROR_CODE_OK))
        .andExpect(status().isOk());
    }
    
    @Test
    public void testBindPushChannel1101() throws Exception {
        bodyObj.put(Constants.TOKEN_PARAMETER_KEY, TOKEN);
        bodyObj.put(Constants.METHOD_PARAMETER_KEY, "1101");
        paramsObj.put(Constants.PUSH_CHANNEL_ID_PARAMETER_KEY, 4331001548479828112L);
        paramsObj.put(Constants.PUSH_USER_ID_PARAMETER_KEY, 912582605301658043L);
        paramsObj.put(Constants.PUSH_APP_ID_PARAMETER_KEY, 4930895);
        paramsObj.put(Constants.PUSH_DEVICE_TYPE_PARAMETER_KEY, 3);
        bodyObj.put(Constants.PARAMS_PARAMETER_KEY, paramsObj);
        performRequest()
        .andDo(print())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.errorcode").value(ERROR_CODE_OK))
        .andExpect(jsonPath("$.entity").value(USER_ID))
        .andExpect(status().isOk());
    }
    
    // 103-user using token retrieve user
    @Test
    public void testBootinitlization() throws Exception {
        bodyObj.put(Constants.METHOD_PARAMETER_KEY, "103");
        bodyObj.put(Constants.TOKEN_PARAMETER_KEY, TOKEN);
        bodyObj.put(Constants.PARAMS_PARAMETER_KEY, paramsObj);
        performRequest()
        .andDo(print())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.entity.userName").value("xiaomianao"))
        .andExpect(jsonPath("$.entity.userId").value(USER_ID))
        .andExpect(jsonPath("$.entity.state").value(0))
        .andExpect(jsonPath("$.errorcode").value(ERROR_CODE_OK))
        .andExpect(status().isOk());
    }

    // 201-find news by id
    @Test
    public void testGetNewsById201() throws Exception {
        bodyObj.put(Constants.METHOD_PARAMETER_KEY, "201");
        paramsObj.put(Constants.NEWSID_PARAMETER_KEY, "1");
        bodyObj.put(Constants.PARAMS_PARAMETER_KEY, paramsObj);
        performRequest()
        .andDo(print())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.entity.id").value(1))
        .andExpect(jsonPath("$.entity.favorited").value(false))
        .andExpect(jsonPath("$.entity.liked").value(false))
        .andExpect(jsonPath("$.errorcode").value(ERROR_CODE_OK))
        .andExpect(status().isOk());
    }

    // 202-find news by category
    @Test
    public void testGetNewsByCategory202() throws Exception {
        bodyObj.put(Constants.METHOD_PARAMETER_KEY, "202");
//        paramsObj.put(Constants.FIRST_RESULT_PARAMETER_KEY, 21);
//        paramsObj.put(Constants.MAX_RESULTS_PARAMETER_KEY, 7);
        paramsObj.put("category", "3");
        bodyObj.put(Constants.PARAMS_PARAMETER_KEY, paramsObj);
        performRequest()
        .andDo(print())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.errorcode").value(ERROR_CODE_OK))
        .andExpect(status().isOk());
    }

    // 203-find news by tags
    @Test
    public void testGetNewsByTags203() throws Exception {
        tagArray.add(TAG_OF_NO1);
        tagArray.add(TAG_OF_NO2);
        tagArray.add(TAG_OF_NO3);
        bodyObj.put(Constants.METHOD_PARAMETER_KEY, "203");
        bodyObj.put(Constants.TOKEN_PARAMETER_KEY, TOKEN);
        paramsObj.put(Constants.FIRST_RESULT_PARAMETER_KEY, "0");
        paramsObj.put(Constants.MAX_RESULTS_PARAMETER_KEY, "1");
        paramsObj.put(Constants.TAGS_PARAMETER_KEY, tagArray);
        bodyObj.put(Constants.PARAMS_PARAMETER_KEY, paramsObj);
        performRequest()
        .andDo(print())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.errorcode").value(ERROR_CODE_OK))
        .andExpect(jsonPath("$.entity.totalCount").value(5))
        .andExpect(jsonPath("$.entity.records[0].news[0].id").value(4))
        .andExpect(jsonPath("$.entity.records[1].news[0].id").value(2))
        .andExpect(jsonPath("$.entity.records[2].news[0].id").value(3))
        .andExpect(status().isOk());
    }
    
 // 205-find top bar news
    @Test
    public void testGetTopBarNews205() throws Exception {
        bodyObj.put(Constants.METHOD_PARAMETER_KEY, "205");
        paramsObj.put(Constants.FIRST_RESULT_PARAMETER_KEY, "0");
        paramsObj.put(Constants.MAX_RESULTS_PARAMETER_KEY, "5");
        JSONArray categoryArray = new JSONArray();
        categoryArray.add("1");
        categoryArray.add("2");
        paramsObj.put(Constants.CATEGORIES_KEY_PARAMETER, categoryArray);
        bodyObj.put(Constants.PARAMS_PARAMETER_KEY, paramsObj);
        performRequest()
        .andDo(print())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.errorcode").value(ERROR_CODE_OK))
//        .andExpect(jsonPath("$.entity[0].id").value(true))
//        .andExpect(jsonPath("$.entity[0].image").value(true))
        .andExpect(status().isOk());
    }

    // 301-find all classifications
    @Test
    public void testGetAllClasses301() throws Exception {
        bodyObj.put(Constants.METHOD_PARAMETER_KEY, "301");
        paramsObj.put(Constants.FIRST_RESULT_PARAMETER_KEY, "0");
        paramsObj.put(Constants.MAX_RESULTS_PARAMETER_KEY, "5");
        bodyObj.put(Constants.PARAMS_PARAMETER_KEY, paramsObj);
        performRequest()
        .andDo(print())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.errorcode").value(ERROR_CODE_OK))
        .andExpect(jsonPath("$.entity[0].tags[0].subscribed").value(false))
        .andExpect(status().isOk());
    }

    // 401-find tags by class name
    @Test
    public void testGetTagsByClassName401() throws Exception {
        bodyObj.put(Constants.METHOD_PARAMETER_KEY, "401");
//        bodyObj.put(Constants.TOKEN_PARAMETER_KEY, TOKEN);
        paramsObj.put(Constants.FIRST_RESULT_PARAMETER_KEY, "0");
        paramsObj.put(Constants.MAX_RESULTS_PARAMETER_KEY, "5");
        paramsObj.put(Constants.CLASS_NAME_PARAMETER_KEY, CLASS_NAME);
        bodyObj.put(Constants.PARAMS_PARAMETER_KEY, paramsObj);
        performRequest()
        .andDo(print())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.errorcode").value(ERROR_CODE_OK))
        .andExpect(jsonPath("$.entity[1].subscribed").value(false))
        .andExpect(status().isOk());
    }
    

    // 501-subscribe tags
    @Test
    public void testSubscribeTags501() throws Exception {
        bodyObj.put(Constants.METHOD_PARAMETER_KEY, "501");
        bodyObj.put(Constants.TOKEN_PARAMETER_KEY, TOKEN);
        tagArray.add(TAG_OF_NO1);
        tagArray.add(TAG_OF_NO2);
        paramsObj.put(Constants.TAGS_PARAMETER_KEY, tagArray);
        bodyObj.put(Constants.PARAMS_PARAMETER_KEY, paramsObj);
        performRequest()
        .andDo(print())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.entity").value(USER_ID)).andExpect(jsonPath("$.errorcode").value(ERROR_CODE_OK))
        .andExpect(status().isOk());
    }
    
    @Test
    public void testSubscribeCommunity505() throws Exception {
        bodyObj.put(Constants.METHOD_PARAMETER_KEY, "505");
        bodyObj.put(Constants.TOKEN_PARAMETER_KEY, TOKEN);
        communityIds.add(1);
        communityIds.add(2);
        paramsObj.put(Constants.COMMUNITY_IDS_PARAMETER_KEY, communityIds);
        bodyObj.put(Constants.PARAMS_PARAMETER_KEY, paramsObj);
        performRequest()
        .andDo(print())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.entity").value(USER_ID))
        .andExpect(jsonPath("$.errorcode").value(ERROR_CODE_OK))
        .andExpect(status().isOk());
    }
    
    @Test
    public void testFindSubscribeCommunity207After505() throws Exception{
        bodyObj.put(Constants.TOKEN_PARAMETER_KEY, TOKEN);
        bodyObj.put(Constants.METHOD_PARAMETER_KEY, "207");
        paramsObj.put(Constants.FIRST_RESULT_PARAMETER_KEY, 0);
        paramsObj.put(Constants.MAX_RESULTS_PARAMETER_KEY, 5);
        paramsObj.put(Constants.USERID_PARAMETER_KEY, USER_ID);
        bodyObj.put(Constants.PARAMS_PARAMETER_KEY, paramsObj);
        performRequest()
        .andDo(print())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.errorcode").value(ERROR_CODE_OK))
        .andExpect(jsonPath("$.entity[0].id").value(1))
        .andExpect(jsonPath("$.entity[0].subscribed").value(true))
        .andExpect(jsonPath("$.entity[1].id").value(2))
        .andExpect(jsonPath("$.entity[1].subscribed").value(true))
//        .andExpect(jsonPath("$.entity[0].lastMainpost.title").value("主题帖开始啦"))
        .andExpect(status().isOk());
    }
    
    @Test
    public void testFindCommunityIntroPage213() throws Exception{
        bodyObj.put(Constants.TOKEN_PARAMETER_KEY, TOKEN);
        bodyObj.put(Constants.METHOD_PARAMETER_KEY, "213");
        paramsObj.put(Constants.COMMUNITY_ID_PARAMETER_KEY, 1);
        bodyObj.put(Constants.PARAMS_PARAMETER_KEY, paramsObj);
        performRequest()
        .andDo(print())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.errorcode").value(ERROR_CODE_OK))
        .andExpect(jsonPath("$.entity.subscribed").value(true))
        .andExpect(status().isOk());
    }
    
    @Test
    public void testFindCommunityById206() throws Exception {
        bodyObj.put(Constants.METHOD_PARAMETER_KEY, "206");
        bodyObj.put(Constants.TOKEN_PARAMETER_KEY, TOKEN);
        paramsObj.put(Constants.COMMUNITY_ID_PARAMETER_KEY, 1);
        bodyObj.put(Constants.PARAMS_PARAMETER_KEY, paramsObj);
        performRequest()
        .andDo(print())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.entity.subscribed").value(true))
        .andExpect(jsonPath("$.errorcode").value(ERROR_CODE_OK))
        .andExpect(status().isOk());
        }
    
 // 402-find community by class name
    @Test
    public void testCommunityByClassName402After505() throws Exception {
        bodyObj.put(Constants.METHOD_PARAMETER_KEY, "402");
        bodyObj.put(Constants.TOKEN_PARAMETER_KEY, TOKEN);
        paramsObj.put(Constants.FIRST_RESULT_PARAMETER_KEY, "0");
        paramsObj.put(Constants.MAX_RESULTS_PARAMETER_KEY, "5");
        paramsObj.put(Constants.CLASS_NAME_PARAMETER_KEY, "同城");
        bodyObj.put(Constants.PARAMS_PARAMETER_KEY, paramsObj);
        performRequest()
        .andDo(print())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.errorcode").value(ERROR_CODE_OK))
        .andExpect(jsonPath("$.entity[1].subscribed").value(true))
        .andExpect(jsonPath("$.entity[1].fansCount").value(1))
        .andExpect(status().isOk());
    }
    
    
    @Test
    public void testUnSubscribeCommunity506() throws Exception {
        bodyObj.put(Constants.METHOD_PARAMETER_KEY, "506");
        bodyObj.put(Constants.TOKEN_PARAMETER_KEY, TOKEN);
        communityIds.add(1);
        communityIds.add(2);
        paramsObj.put(Constants.COMMUNITY_IDS_PARAMETER_KEY, communityIds);
        bodyObj.put(Constants.PARAMS_PARAMETER_KEY, paramsObj);
        performRequest()
        .andDo(print())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.entity").value(USER_ID))
        .andExpect(jsonPath("$.errorcode").value(ERROR_CODE_OK))
        .andExpect(status().isOk());
    }

    // 301-find all classifications after subscribed,to see whether tags got correct subscribed field.
    @Test
    @Ignore
    public void testGetAllClasses2() throws Exception {
        bodyObj.put(Constants.METHOD_PARAMETER_KEY, "301");
        paramsObj.put(Constants.FIRST_RESULT_PARAMETER_KEY, "0");
        paramsObj.put(Constants.MAX_RESULTS_PARAMETER_KEY, "5");
        bodyObj.put(Constants.PARAMS_PARAMETER_KEY, paramsObj);
        performRequest().andDo(print()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errorcode").value(ERROR_CODE_OK))
                // due to set, the returned tags is disorderd,but sure there is correct subscribed field.
                // .andExpect(jsonPath("$.entity[0].tags[0].subscribed").value(true))
                .andExpect(status().isOk());
    }

    // 401-find tags by class name after subscribed
    @Test
    public void testGetTagsByClassName2() throws Exception {
        bodyObj.put(Constants.TOKEN_PARAMETER_KEY, TOKEN);
        bodyObj.put(Constants.METHOD_PARAMETER_KEY, "401");
        paramsObj.put(Constants.FIRST_RESULT_PARAMETER_KEY, "0");
        paramsObj.put(Constants.MAX_RESULTS_PARAMETER_KEY, "5");
        paramsObj.put(Constants.CLASS_NAME_PARAMETER_KEY, CLASS_NAME);
        bodyObj.put(Constants.PARAMS_PARAMETER_KEY, paramsObj);
        performRequest().andDo(print()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errorcode").value(ERROR_CODE_OK))
                .andExpect(jsonPath("$.entity[0].subscribed").value(true))
                .andExpect(jsonPath("$.entity[1].subscribed").value(true)).andExpect(status().isOk());
    }

    // 502-unsubscribe tags
    @Test
    public void testUnsubscribeTags502() throws Exception {
        bodyObj.put(Constants.TOKEN_PARAMETER_KEY, TOKEN);
        bodyObj.put(Constants.METHOD_PARAMETER_KEY, "502");
        tagArray.add(TAG_OF_NO1);
        tagArray.add(TAG_OF_NO2);
        paramsObj.put(Constants.TAGS_PARAMETER_KEY, tagArray);
        bodyObj.put(Constants.PARAMS_PARAMETER_KEY, paramsObj);
        performRequest().andDo(print()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errorcode").value(ERROR_CODE_OK)).andExpect(jsonPath("$.entity").value(USER_ID))
                .andExpect(status().isOk());
    }

    // 601-favorite news
    @Test
    public void testFavoriteNews601() throws Exception {
        newsIds.add(1);
        bodyObj.put(Constants.TOKEN_PARAMETER_KEY, TOKEN);
        bodyObj.put(Constants.METHOD_PARAMETER_KEY, "601");
        paramsObj.put(Constants.NEWS_IDS_PARAMETER_KEY, newsIds);
        bodyObj.put(Constants.PARAMS_PARAMETER_KEY, paramsObj);
        performRequest()
        .andDo(print())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.errorcode").value(ERROR_CODE_OK))
        .andExpect(jsonPath("$.entity").value(USER_ID))
        .andExpect(status().isOk());
    }
    

    // 601-favorite news
    @Test
    public void testFavoriteNews601_2() throws Exception {
        Thread.sleep(1000);
        newsIds.add(2);
        bodyObj.put(Constants.TOKEN_PARAMETER_KEY, TOKEN);
        bodyObj.put(Constants.METHOD_PARAMETER_KEY, "601");
        paramsObj.put(Constants.NEWS_IDS_PARAMETER_KEY, newsIds);
        bodyObj.put(Constants.PARAMS_PARAMETER_KEY, paramsObj);
        performRequest()
        .andDo(print())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.errorcode").value(ERROR_CODE_OK))
        .andExpect(jsonPath("$.entity").value(USER_ID))
        .andExpect(status().isOk());
    }


    // 701-like news
    @Test
    public void testLikeNews701() throws Exception {
        newsIds.add(1);
        bodyObj.put(Constants.TOKEN_PARAMETER_KEY, TOKEN);
        bodyObj.put(Constants.METHOD_PARAMETER_KEY, "701");
        paramsObj.put(Constants.NEWS_IDS_PARAMETER_KEY, newsIds);
        bodyObj.put(Constants.PARAMS_PARAMETER_KEY, paramsObj);
        performRequest().andDo(print())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.errorcode").value(ERROR_CODE_OK))
        .andExpect(jsonPath("$.entity").value(USER_ID))
        .andExpect(status().isOk());
    }
    
    // 701-like news
    @Test
    public void testLikeNews701_2() throws Exception {
        Thread.sleep(1000);
        newsIds.add(2);
        bodyObj.put(Constants.TOKEN_PARAMETER_KEY, TOKEN);
        bodyObj.put(Constants.METHOD_PARAMETER_KEY, "701");
        paramsObj.put(Constants.NEWS_IDS_PARAMETER_KEY, newsIds);
        bodyObj.put(Constants.PARAMS_PARAMETER_KEY, paramsObj);
        performRequest().andDo(print())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.errorcode").value(ERROR_CODE_OK))
        .andExpect(jsonPath("$.entity").value(USER_ID))
        .andExpect(status().isOk());
    }
    
    @Test
    public void testFindLikeNews216after701() throws Exception {
        bodyObj.put(Constants.METHOD_PARAMETER_KEY, "216");
        paramsObj.put(Constants.USERID_PARAMETER_KEY, USER_ID);
        JSONArray array = new JSONArray();
        array.add(Constants.LIKED_NEWS_TYPE);
        paramsObj.put(Constants.TYPES_PARAMETER_KEY, array);
        bodyObj.put(Constants.PARAMS_PARAMETER_KEY, paramsObj);
        performRequest().andDo(print())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.errorcode").value(ERROR_CODE_OK))
        .andExpect(jsonPath("$.entity[0].id").value(2))
        .andExpect(jsonPath("$.entity[1].id").value(1))
        .andExpect(status().isOk());
    }
    
    // 201-exam whether the liked field is true after likeNews.
    @Test
    public void testGetNewsByIdAfterLiked() throws Exception {
        bodyObj.put(Constants.TOKEN_PARAMETER_KEY, TOKEN);
        bodyObj.put(Constants.METHOD_PARAMETER_KEY, "201");
        paramsObj.put(Constants.NEWSID_PARAMETER_KEY, "1");
        bodyObj.put(Constants.PARAMS_PARAMETER_KEY, paramsObj);
        performRequest()
        .andDo(print())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.entity.id").value(1))
        .andExpect(jsonPath("$.entity.liked").value(true))
        .andExpect(jsonPath("$.errorcode").value(ERROR_CODE_OK))
        .andExpect(status().isOk());
    }

    // 702-unlike news
    @Test
    public void testUnlikeNews702() throws Exception {
        newsIds.add(1);
        newsIds.add(2);
        bodyObj.put(Constants.TOKEN_PARAMETER_KEY, TOKEN);
        bodyObj.put(Constants.METHOD_PARAMETER_KEY, "702");
        paramsObj.put(Constants.NEWS_IDS_PARAMETER_KEY, newsIds);
        bodyObj.put(Constants.PARAMS_PARAMETER_KEY, paramsObj);
        performRequest().andDo(print())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.errorcode").value(ERROR_CODE_OK))
        .andExpect(jsonPath("$.entity").value(USER_ID))
        .andExpect(status().isOk());
    }

    // 201-exam whether the liked field is true after unlikeNews.
    @Test
    public void testGetNewsByIdAfterUnLiked() throws Exception {
        bodyObj.put(Constants.METHOD_PARAMETER_KEY, "201");
        paramsObj.put(Constants.NEWSID_PARAMETER_KEY, "1");
        bodyObj.put(Constants.PARAMS_PARAMETER_KEY, paramsObj);
        performRequest().andDo(print()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.entity.id").value(1)).andExpect(jsonPath("$.entity.liked").value(false))
                .andExpect(jsonPath("$.errorcode").value(ERROR_CODE_OK)).andExpect(status().isOk());
    }
    //104-register user
    @Test
    @Ignore
    public void testRegisterUser() throws Exception{
        bodyObj.put(Constants.METHOD_PARAMETER_KEY, "104");
        bodyObj.put(Constants.IMEI_PARAMETER_KEY, "thisisimei1");
        paramsObj.put(Constants.USER_NAME_PARAMETER_KEY, "woaibeijing123");
        paramsObj.put(Constants.PASS_WORD_PARAMETER_KEY, "Wd654321");
        bodyObj.put(Constants.PARAMS_PARAMETER_KEY, paramsObj);
        performRequest()
        .andDo(print())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.errorcode").value(ERROR_CODE_OK))
        .andExpect(status().isOk());
    }
    
    @Test
    public void testUpdatePushPreference111() throws Exception{
        bodyObj.put(Constants.TOKEN_PARAMETER_KEY, TOKEN);
        bodyObj.put(Constants.METHOD_PARAMETER_KEY, "111");
        paramsObj.put(Constants.MIANAO_EVENT_TYPE_PARAMETER_KEY, 1);
        paramsObj.put(Constants.IS_PUSH_NOTIFICATION_PARAMETER_KEY, false);
        bodyObj.put(Constants.PARAMS_PARAMETER_KEY, paramsObj);
        performRequest()
        .andDo(print())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.errorcode").value(ERROR_CODE_OK))
        .andExpect(jsonPath("$.entity").value(USER_ID))
        .andExpect(status().isOk());
    }
    
    //104-validate userName on registering
    @Test
    public void testValidationRegisteredUsername() throws Exception{
        bodyObj.put(Constants.IMEI_PARAMETER_KEY, "thisisimei1");
        bodyObj.put(Constants.METHOD_PARAMETER_KEY, "104");
        paramsObj.put(Constants.USER_NAME_PARAMETER_KEY, "Sif");
        paramsObj.put(Constants.PASS_WORD_PARAMETER_KEY, "Wd654321");
        bodyObj.put(Constants.PARAMS_PARAMETER_KEY, paramsObj);
        performRequest()
        .andDo(print())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.errorcode").value(ErrorCode.USER_NAME_INVALIDATED.value))
        .andExpect(status().isOk());
    }
    
  //104-validate passWord on registering
    @Test
    public void testValidationRegisteredPassword() throws Exception{
        bodyObj.put(Constants.IMEI_PARAMETER_KEY, "thisisimei1");
        bodyObj.put(Constants.METHOD_PARAMETER_KEY, "104");
        paramsObj.put(Constants.USER_NAME_PARAMETER_KEY, "hywang-dada");
        paramsObj.put(Constants.PASS_WORD_PARAMETER_KEY, "fdW1");
        bodyObj.put(Constants.PARAMS_PARAMETER_KEY, paramsObj);
        performRequest()
        .andDo(print())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.errorcode").value(ErrorCode.USER_PASSWORD_INVALIDATED.value))
        .andExpect(status().isOk());
    }
    
    @Test
    //801-add comment
    public void testAddComment801() throws Exception{
        bodyObj.put(Constants.TOKEN_PARAMETER_KEY, TOKEN);
        bodyObj.put(Constants.METHOD_PARAMETER_KEY, "801");
        paramsObj.put(Constants.NEWSID_PARAMETER_KEY, 1);
        paramsObj.put("parentId", -1);
        paramsObj.put(Constants.CONTENT_PARAMETER_KEY, "blablabla");
        bodyObj.put(Constants.PARAMS_PARAMETER_KEY, paramsObj);
        performRequest()
        .andDo(print())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.errorcode").value(ERROR_CODE_OK))
//        .andExpect(jsonPath("$.entity").value(5))
        .andExpect(status().isOk());
    }
    
    @Test
    //802-find comments of specific news
    public void testFindCommentsByNewsId802() throws Exception{
        bodyObj.put(Constants.TOKEN_PARAMETER_KEY, TOKEN);
        bodyObj.put(Constants.METHOD_PARAMETER_KEY, "802");
        paramsObj.put(Constants.NEWSID_PARAMETER_KEY, 2);
        paramsObj.put(Constants.FIRST_RESULT_PARAMETER_KEY, 0);
        paramsObj.put(Constants.MAX_RESULTS_PARAMETER_KEY, 5);
        bodyObj.put(Constants.PARAMS_PARAMETER_KEY, paramsObj);
        performRequest()
        .andDo(print())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.errorcode").value(ERROR_CODE_OK))
        .andExpect(jsonPath("$.entity.records[0].owned").value(true))
        .andExpect(jsonPath("$.entity.records[1].owned").value(true))
        .andExpect(jsonPath("$.entity.totalCount").value(2))
        .andExpect(status().isOk());
    }
    
    @Test
    //803-update specific comment
    public void testUpdateComment803() throws Exception{
        bodyObj.put(Constants.TOKEN_PARAMETER_KEY, TOKEN);
        bodyObj.put(Constants.METHOD_PARAMETER_KEY, "803");
        paramsObj.put(Constants.COMMENT_ID_PARAMETER_KEY, 2);
        paramsObj.put(Constants.CONTENT_PARAMETER_KEY, "更新更新更新啊");
        bodyObj.put(Constants.PARAMS_PARAMETER_KEY, paramsObj);
        performRequest()
        .andDo(print())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.errorcode").value(ERROR_CODE_OK))
        .andExpect(jsonPath("$.entity").value(2))
        .andExpect(status().isOk());
    }
    
    @Test
    @Ignore
    //804-delete specific comment,ignore by default,cause rollback=false
    public void testDeleteComment804() throws Exception{
        bodyObj.put(Constants.TOKEN_PARAMETER_KEY, TOKEN);
        bodyObj.put(Constants.METHOD_PARAMETER_KEY, "804");
        paramsObj.put(Constants.COMMENT_ID_PARAMETER_KEY,11);
        bodyObj.put(Constants.PARAMS_PARAMETER_KEY, paramsObj);
        performRequest()
        .andDo(print())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.errorcode").value(ERROR_CODE_OK))
        .andExpect(jsonPath("$.entity").value(11))
        .andExpect(status().isOk());
    }
    
    //105-update birth.
    @Test
    public void testUpdateBirth105() throws Exception{
        bodyObj.put(Constants.TOKEN_PARAMETER_KEY, TOKEN);
        bodyObj.put(Constants.METHOD_PARAMETER_KEY, "105");
        paramsObj.put(Constants.BIRTH_PARAMETER_KEY,"1985-09-17");
        bodyObj.put(Constants.PARAMS_PARAMETER_KEY, paramsObj);
        performRequest()
        .andDo(print())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.errorcode").value(ERROR_CODE_OK))
        .andExpect(jsonPath("$.entity").value(USER_ID))
        .andExpect(status().isOk());
    }
    
    //106-update location.
    @Test
    public void testUpdateLocation106() throws Exception{
        bodyObj.put(Constants.TOKEN_PARAMETER_KEY, TOKEN);
        bodyObj.put(Constants.METHOD_PARAMETER_KEY, "106");
        paramsObj.put(Constants.LOCATION_PARAMETER_KEY,"北京");
        bodyObj.put(Constants.PARAMS_PARAMETER_KEY, paramsObj);
        performRequest()
        .andDo(print())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.errorcode").value(ERROR_CODE_OK))
        .andExpect(jsonPath("$.entity").value(USER_ID))
        .andExpect(status().isOk());
    }
    
    //503-subscribe mianao state.
    @Test
    public void testSubscribeMianaoState503() throws Exception{
        bodyObj.put(Constants.TOKEN_PARAMETER_KEY, TOKEN);
        bodyObj.put(Constants.METHOD_PARAMETER_KEY, "503");
        JSONArray states = new JSONArray();
        states.add("备孕中");
        states.add("哺乳期");
        paramsObj.put(Constants.SUB_STATES_PARAMETER_KEY,states);
        bodyObj.put(Constants.PARAMS_PARAMETER_KEY, paramsObj);
        performRequest()
        .andDo(print())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.errorcode").value(ERROR_CODE_OK))
        .andExpect(jsonPath("$.entity").value(USER_ID))
        .andExpect(status().isOk());
    }
    
    // 103-user using token retrieve user
    @Test
    public void testBootinitlizationaAfter503() throws Exception {
        bodyObj.put(Constants.METHOD_PARAMETER_KEY, "103");
        bodyObj.put(Constants.TOKEN_PARAMETER_KEY, TOKEN);
        bodyObj.put(Constants.PARAMS_PARAMETER_KEY, paramsObj);
        performRequest()
        .andDo(print())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.entity.userName").value("xiaomianao"))
        .andExpect(jsonPath("$.entity.state").value(0))
        .andExpect(jsonPath("$.errorcode").value(ERROR_CODE_OK))
        .andExpect(status().isOk());
    }
    
    //204-fetch all mianaostates
    @Test
    public void testGetMianaoStates204() throws Exception{
        bodyObj.put(Constants.TOKEN_PARAMETER_KEY, TOKEN);
        bodyObj.put(Constants.METHOD_PARAMETER_KEY, "204");
        bodyObj.put(Constants.PARAMS_PARAMETER_KEY, paramsObj);
        performRequest()
        .andDo(print())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.errorcode").value(ERROR_CODE_OK))
        .andExpect(jsonPath("$.entity[4].name").value("哺乳期"))
        .andExpect(jsonPath("$.entity[4].owned").value(true))
        .andExpect(jsonPath("$.entity[5].name").value("备孕中"))
        .andExpect(jsonPath("$.entity[5].owned").value(true))
        .andExpect(status().isOk());
    }
    
    //504-unsubscribe mianao state.
    @Test
    public void testUnsubscribeMianaoState504() throws Exception{
        bodyObj.put(Constants.TOKEN_PARAMETER_KEY, TOKEN);
        bodyObj.put(Constants.METHOD_PARAMETER_KEY, "504");
        JSONArray states = new JSONArray();
        states.add("备孕中");
        states.add("哺乳期");
        paramsObj.put(Constants.UNSUB_STATES_PARAMETER_KEY,states);
        bodyObj.put(Constants.PARAMS_PARAMETER_KEY, paramsObj);
        performRequest()
        .andDo(print())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.errorcode").value(ERROR_CODE_OK))
        .andExpect(jsonPath("$.entity").value(USER_ID))
        .andExpect(status().isOk());
    }
    
    //107-update nickname.
    @Test
    public void testUpdateNickName107() throws Exception{
        bodyObj.put(Constants.TOKEN_PARAMETER_KEY, TOKEN);
        bodyObj.put(Constants.METHOD_PARAMETER_KEY, "107");
        paramsObj.put(Constants.NICKNAME_PARAMETER_KEY,"阿喀琉斯");
        bodyObj.put(Constants.PARAMS_PARAMETER_KEY, paramsObj);
        performRequest()
        .andDo(print())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.errorcode").value(ERROR_CODE_OK))
        .andExpect(jsonPath("$.entity").value(USER_ID))
        .andExpect(status().isOk());
    }
    
    
    //108-update signature.
    @Test
    public void testUpdateSignature108() throws Exception{
        bodyObj.put(Constants.TOKEN_PARAMETER_KEY, TOKEN);
        bodyObj.put(Constants.METHOD_PARAMETER_KEY, "108");
        paramsObj.put(Constants.SIGNATURE_PARAMETER_KEY,"特别蛋疼特别矫情的签名");
        bodyObj.put(Constants.PARAMS_PARAMETER_KEY, paramsObj);
        performRequest()
        .andDo(print())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.errorcode").value(ERROR_CODE_OK))
        .andExpect(jsonPath("$.entity").value(USER_ID))
        .andExpect(status().isOk());
    }
   
    @Test
    public void test103AfterUpdateUser() throws Exception{
        bodyObj.put(Constants.METHOD_PARAMETER_KEY, "103");
        bodyObj.put(Constants.TOKEN_PARAMETER_KEY, TOKEN);
        bodyObj.put(Constants.PARAMS_PARAMETER_KEY, paramsObj);
        Calendar time = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        time.setTime(sdf.parse("1985-09-17"));
        performRequest()
        .andDo(print())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.entity.userName").value("xiaomianao"))
        .andExpect(jsonPath("$.entity.state").value(0))
        .andExpect(jsonPath("$.errorcode").value(ERROR_CODE_OK))
        .andExpect(jsonPath("$.entity.location").value("北京"))
        .andExpect(jsonPath("$.entity.nickName").value("阿喀琉斯"))
//        .andExpect(jsonPath("$.entity.mianaoState").value("备孕中"))
        .andExpect(jsonPath("$.entity.signature").value("特别蛋疼特别矫情的签名"))
        .andExpect(jsonPath("$.entity.birth").value(time.getTimeInMillis()))
        .andExpect(status().isOk());
    }
    
    //901-snapshot upload
    @Test
    public void testSnapshotUpload() throws Exception{
        bodyObj.put(Constants.TOKEN_PARAMETER_KEY, TOKEN);
        bodyObj.put(Constants.METHOD_PARAMETER_KEY, "901");
        paramsObj.put(Constants.UPLOAD_KEY_PARAMETER_KEY,"user/3/snapshot/hello.png");
        bodyObj.put(Constants.PARAMS_PARAMETER_KEY, paramsObj);
        performRequest()
        .andDo(print())
//        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.errorcode").value(ERROR_CODE_OK))
        .andExpect(jsonPath("$.entity").value(USER_ID))
        .andExpect(status().isOk());
    }
    
    //102-logout system,remove token,after logout the token will be changed,so ignore
    @Test
    @Ignore
    public void testLogout102() throws Exception{
        bodyObj.put(Constants.TOKEN_PARAMETER_KEY, TOKEN);
        bodyObj.put(Constants.METHOD_PARAMETER_KEY, "102");
        bodyObj.put(Constants.PARAMS_PARAMETER_KEY, paramsObj);
        performRequest()
        .andDo(print())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.errorcode").value(ERROR_CODE_OK))
        .andExpect(jsonPath("$.entity").value(USER_ID))
        .andExpect(status().isOk());
    }
    
    @Test
    public void testGenerateQiniuToken902() throws Exception{
        bodyObj.put(Constants.TOKEN_PARAMETER_KEY, TOKEN);
        bodyObj.put(Constants.METHOD_PARAMETER_KEY, "902");
        paramsObj.put(Constants.UPLOAD_KEY_PARAMETER_KEY, "user/A9DFAE2C9EE948C6AEE08CBF8A52F810/snapshot/icon.png");
        bodyObj.put(Constants.PARAMS_PARAMETER_KEY, paramsObj);
        performRequest()
        .andDo(print())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.errorcode").value(ERROR_CODE_OK))
//        .andExpect(jsonPath("$.entity").value(USER_ID))
        .andExpect(status().isOk());
    }
    
    @Test
    @Ignore
    public void testAddMainPost806() throws Exception{
        bodyObj.put(Constants.TOKEN_PARAMETER_KEY, TOKEN);
        bodyObj.put(Constants.METHOD_PARAMETER_KEY, "806");
        JSONArray keys = new JSONArray();
        keys.add("user/0402A41D9C994D5B8EE87A83EE17E6D1/mainpost/1416639696978.png");
//        keys.add("user/3/post/2/bbb.jpg");
        tagArray.clear();
//        tagArray.add(TAG_OF_NO1);
//        tagArray.add(TAG_OF_NO2);
//        tagArray.add(TAG_OF_NO3);
        paramsObj.put(Constants.COMMUNITY_ID_PARAMETER_KEY, 1);
        paramsObj.put(Constants.TITLE_PARAMETER_KEY,"主题帖开始啦");
        paramsObj.put(Constants.CONTENT_PARAMETER_KEY, "啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦BOBBOBOBOB");
        paramsObj.put(Constants.TAGS_PARAMETER_KEY, tagArray);
        paramsObj.put(Constants.IMAGES_PARAMEER_KEY, keys);
        bodyObj.put(Constants.PARAMS_PARAMETER_KEY, paramsObj);
        performRequest()
        .andDo(print())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.errorcode").value(ERROR_CODE_OK))
//        .andExpect(jsonPath("$.entity").value(USER_ID))
        .andExpect(status().isOk());
    }
    
    @Test
//    @Ignore
    public void testAddPost805() throws Exception{
//        bodyObj.put(Constants.TOKEN_PARAMETER_KEY, TOKEN);
        bodyObj.put(Constants.TOKEN_PARAMETER_KEY, "AF8CF9AB932F43488B883281FBF6A6C7");
        bodyObj.put(Constants.METHOD_PARAMETER_KEY, "805");
        JSONArray keys = new JSONArray();
        keys.add("user/3/post/1/aaa.jpg");
        keys.add("user/3/post/2/bbb.jpg");
        paramsObj.put(Constants.MAIN_POST_ID_PARAM_KEY, 2);
//        paramsObj.put(Constants.PARENT_ID_PARAM_KEY,-1);
        paramsObj.put(Constants.CONTENT_PARAMETER_KEY, "啦啦啦");
        paramsObj.put(Constants.IMAGES_PARAMEER_KEY, keys);
        bodyObj.put(Constants.PARAMS_PARAMETER_KEY, paramsObj);
        performRequest()
        .andDo(print())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.errorcode").value(ERROR_CODE_OK))
//        .andExpect(jsonPath("$.entity").value(USER_ID))
        .andExpect(status().isOk());
    }
    
    
    @Test
    public void testGetCommunityById206after806() throws Exception {
        bodyObj.put(Constants.METHOD_PARAMETER_KEY, "206");
        bodyObj.put(Constants.TOKEN_PARAMETER_KEY, TOKEN);
        paramsObj.put(Constants.COMMUNITY_ID_PARAMETER_KEY, 1);
        bodyObj.put(Constants.PARAMS_PARAMETER_KEY, paramsObj);
        performRequest()
        .andDo(print())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.entity.subscribed").value(false))
        .andExpect(jsonPath("$.errorcode").value(ERROR_CODE_OK))
        .andExpect(status().isOk());
        }
    
    @Test
    public void testFindCommunityClasses302() throws Exception{
        bodyObj.put(Constants.TOKEN_PARAMETER_KEY, TOKEN);
        bodyObj.put(Constants.METHOD_PARAMETER_KEY, "302");
//        paramsObj.put(Constants.FIRST_RESULT_PARAMETER_KEY, 0);
//        paramsObj.put(Constants.MAX_RESULTS_PARAMETER_KEY, 5);
        bodyObj.put(Constants.PARAMS_PARAMETER_KEY, paramsObj);
        performRequest()
        .andDo(print())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.errorcode").value(ERROR_CODE_OK))
        .andExpect(jsonPath("$.entity[0].name").value("同城"))
        .andExpect(status().isOk());
    }
    
    @Test
    public void testFindMainPost208() throws Exception{
//        bodyObj.put(Constants.TOKEN_PARAMETER_KEY, TOKEN);
        bodyObj.put(Constants.METHOD_PARAMETER_KEY, "208");
        paramsObj.put(Constants.COMMUNITY_ID_PARAMETER_KEY, 1);
        paramsObj.put(Constants.FIRST_RESULT_PARAMETER_KEY, 0);
        paramsObj.put(Constants.MAX_RESULTS_PARAMETER_KEY, 5);
        bodyObj.put(Constants.PARAMS_PARAMETER_KEY, paramsObj);
        performRequest()
        .andDo(print())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.errorcode").value(ERROR_CODE_OK))
        .andExpect(jsonPath("$.entity[0].liked").value(false))
        .andExpect(status().isOk());
    }
    
    @Test
    @Ignore
    public void testAddPost805ForMP2() throws Exception{
        bodyObj.put(Constants.TOKEN_PARAMETER_KEY, TOKEN);
        bodyObj.put(Constants.METHOD_PARAMETER_KEY, "805");
        JSONArray keys = new JSONArray();
        keys.add("user/3/post/1/aaa.jpg");
        keys.add("user/3/post/2/bbb.jpg");
        paramsObj.put(Constants.MAIN_POST_ID_PARAM_KEY, 2);
        paramsObj.put(Constants.COMMUNITY_ID_PARAMETER_KEY, 1);
        paramsObj.put(Constants.PARENT_ID_PARAM_KEY,-1);
        paramsObj.put(Constants.CONTENT_PARAMETER_KEY, "评论在第一篇主贴的评论");
        paramsObj.put(Constants.IMAGES_PARAMEER_KEY, keys);
        bodyObj.put(Constants.PARAMS_PARAMETER_KEY, paramsObj);
        performRequest()
        .andDo(print())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.errorcode").value(ERROR_CODE_OK))
//        .andExpect(jsonPath("$.entity").value(USER_ID))
        .andExpect(status().isOk());
    }
    
    @Test
    public void testFindPosts209() throws Exception{
        bodyObj.put(Constants.TOKEN_PARAMETER_KEY, TOKEN);
        bodyObj.put(Constants.METHOD_PARAMETER_KEY, "209");
        paramsObj.put(Constants.MAIN_POST_ID_PARAM_KEY, 2);
        paramsObj.put(Constants.FIRST_RESULT_PARAMETER_KEY, 0);
        paramsObj.put(Constants.MAX_RESULTS_PARAMETER_KEY,3);
        bodyObj.put(Constants.PARAMS_PARAMETER_KEY, paramsObj);
        performRequest()
        .andDo(print())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.errorcode").value(ERROR_CODE_OK))
        .andExpect(jsonPath("$.entity[0].owned").value(true))
        .andExpect(jsonPath("$.entity[0].mainPostOwner").value(true))
        .andExpect(jsonPath("$.entity[0].liked").value(false))
        .andExpect(status().isOk());
    }
    
    @Test
    public void testFindDigestMainposts210() throws Exception{
//        bodyObj.put(Constants.TOKEN_PARAMETER_KEY, TOKEN);
        bodyObj.put(Constants.METHOD_PARAMETER_KEY, "210");
        paramsObj.put(Constants.COMMUNITY_ID_PARAMETER_KEY, 1);
        bodyObj.put(Constants.PARAMS_PARAMETER_KEY, paramsObj);
        performRequest()
        .andDo(print())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.errorcode").value(ERROR_CODE_OK))
//        .andExpect(jsonPath("$.entity[0].owned").value(true))
        .andExpect(status().isOk());
    }
    
    @Test
    public void testFindCommunityTags211() throws Exception{
        bodyObj.put(Constants.METHOD_PARAMETER_KEY, "211");
        bodyObj.put(Constants.PARAMS_PARAMETER_KEY, paramsObj);
        performRequest()
        .andDo(print())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.errorcode").value(ERROR_CODE_OK))
//        .andExpect(jsonPath("$.entity[0].owned").value(true))
        .andExpect(status().isOk());
    }
    
    @Test
    @Ignore
    public void testDeletePost807() throws Exception{
        bodyObj.put(Constants.TOKEN_PARAMETER_KEY, TOKEN);
        bodyObj.put(Constants.METHOD_PARAMETER_KEY, "807");
        paramsObj.put(Constants.POST_ID_PARAM_KEY, 122);
        bodyObj.put(Constants.PARAMS_PARAMETER_KEY, paramsObj);
        performRequest()
        .andDo(print())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.errorcode").value(ERROR_CODE_OK))
        .andExpect(jsonPath("$.entity").value(true))
        .andExpect(status().isOk());
    }
    
    @Test
    @Ignore
    public void testDeleteMainPost808() throws Exception{
        bodyObj.put(Constants.TOKEN_PARAMETER_KEY, TOKEN);
        bodyObj.put(Constants.METHOD_PARAMETER_KEY, "808");
        paramsObj.put(Constants.MAIN_POST_ID_PARAM_KEY, 2);
        bodyObj.put(Constants.PARAMS_PARAMETER_KEY, paramsObj);
        performRequest()
        .andDo(print())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.errorcode").value(ERROR_CODE_OK))
        .andExpect(jsonPath("$.entity").value(true))
        .andExpect(status().isOk());
    }
    
    
    @Test
    public void testLikeMainPost703() throws Exception{
        bodyObj.put(Constants.TOKEN_PARAMETER_KEY, TOKEN);
        bodyObj.put(Constants.METHOD_PARAMETER_KEY, "703");
        JSONArray ids = new JSONArray();
        ids.add(2);
        paramsObj.put(Constants.IDS_PARAMETER_KEY, ids);
        paramsObj.put(Constants.TYPE_CODE_PARAMETER_KEY, 2);
        bodyObj.put(Constants.PARAMS_PARAMETER_KEY, paramsObj);
        performRequest()
        .andDo(print())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.errorcode").value(ERROR_CODE_OK))
        .andExpect(jsonPath("$.entity").value(USER_ID))
        .andExpect(status().isOk());
    } 
    
    @Test
    public void testPushLikeMainPost703() throws Exception{
        bodyObj.put(Constants.TOKEN_PARAMETER_KEY, "AF8CF9AB932F43488B883281FBF6A6C7");
        bodyObj.put(Constants.METHOD_PARAMETER_KEY, "703");
        JSONArray ids = new JSONArray();
        ids.add(2);
        paramsObj.put(Constants.IDS_PARAMETER_KEY, ids);
        paramsObj.put(Constants.TYPE_CODE_PARAMETER_KEY, 2);
        bodyObj.put(Constants.PARAMS_PARAMETER_KEY, paramsObj);
        performRequest()
        .andDo(print())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.errorcode").value(ERROR_CODE_OK))
        .andExpect(jsonPath("$.entity").value("B813E7B7F8FD47899FF54091A843A836"))
        .andExpect(status().isOk());
    } 
    
    @Test
    public void testLikeMainPost703_2() throws Exception{
        Thread.sleep(1000);
        bodyObj.put(Constants.TOKEN_PARAMETER_KEY, TOKEN);
        bodyObj.put(Constants.METHOD_PARAMETER_KEY, "703");
        JSONArray ids = new JSONArray();
        ids.add(4);
        paramsObj.put(Constants.IDS_PARAMETER_KEY, ids);
        paramsObj.put(Constants.TYPE_CODE_PARAMETER_KEY, 2);
        bodyObj.put(Constants.PARAMS_PARAMETER_KEY, paramsObj);
        performRequest()
        .andDo(print())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.errorcode").value(ERROR_CODE_OK))
        .andExpect(jsonPath("$.entity").value(USER_ID))
        .andExpect(status().isOk());
    } 
    
    @Test
    public void testFindLikeMainPost216after703() throws Exception {
        bodyObj.put(Constants.METHOD_PARAMETER_KEY, "216");
        paramsObj.put(Constants.USERID_PARAMETER_KEY, USER_ID);
        JSONArray array = new JSONArray();
        array.add(Constants.LIKED_MAINPOSTS_TYPE);
        paramsObj.put(Constants.TYPES_PARAMETER_KEY, array);
        bodyObj.put(Constants.PARAMS_PARAMETER_KEY, paramsObj);
        performRequest().andDo(print())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.errorcode").value(ERROR_CODE_OK))
        .andExpect(jsonPath("$.entity[0].id").value(4))
        .andExpect(jsonPath("$.entity[1].id").value(2))
        .andExpect(status().isOk());
    }
    
    @Test
    public void testFindMainpostById212After703() throws Exception{
        bodyObj.put(Constants.TOKEN_PARAMETER_KEY, TOKEN);
        bodyObj.put(Constants.METHOD_PARAMETER_KEY, "212");
        paramsObj.put(Constants.MAIN_POST_ID_PARAM_KEY, 2);
        bodyObj.put(Constants.PARAMS_PARAMETER_KEY, paramsObj);
        performRequest()
        .andDo(print())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.errorcode").value(ERROR_CODE_OK))
        .andExpect(jsonPath("$.entity.liked").value(true))
//        .andExpect(jsonPath("$.entity").value(USER_ID))
        .andExpect(status().isOk());
    }
    
    @Test
    public void testUnLikeMainPost704() throws Exception{
        bodyObj.put(Constants.TOKEN_PARAMETER_KEY, TOKEN);
        bodyObj.put(Constants.METHOD_PARAMETER_KEY, "704");
        JSONArray ids = new JSONArray();
        ids.add(2);
        ids.add(4);
        paramsObj.put(Constants.TYPE_CODE_PARAMETER_KEY, 2);
        paramsObj.put(Constants.IDS_PARAMETER_KEY, ids);
        bodyObj.put(Constants.PARAMS_PARAMETER_KEY, paramsObj);
        performRequest()
        .andDo(print())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.errorcode").value(ERROR_CODE_OK))
        .andExpect(jsonPath("$.entity").value(USER_ID))
        .andExpect(status().isOk());
    } 
    
    @Test
    public void testUnLikeMainPost704_2() throws Exception{
        bodyObj.put(Constants.TOKEN_PARAMETER_KEY, "AF8CF9AB932F43488B883281FBF6A6C7");
        bodyObj.put(Constants.METHOD_PARAMETER_KEY, "704");
        JSONArray ids = new JSONArray();
        ids.add(2);
        paramsObj.put(Constants.TYPE_CODE_PARAMETER_KEY, 2);
        paramsObj.put(Constants.IDS_PARAMETER_KEY, ids);
        bodyObj.put(Constants.PARAMS_PARAMETER_KEY, paramsObj);
        performRequest()
        .andDo(print())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.errorcode").value(ERROR_CODE_OK))
        .andExpect(jsonPath("$.entity").value("B813E7B7F8FD47899FF54091A843A836"))
        .andExpect(status().isOk());
    } 
    
    
    @Test
    public void testFindMainpostById212After704() throws Exception{
        bodyObj.put(Constants.TOKEN_PARAMETER_KEY, TOKEN);
        bodyObj.put(Constants.METHOD_PARAMETER_KEY, "212");
        paramsObj.put(Constants.MAIN_POST_ID_PARAM_KEY, 2);
        bodyObj.put(Constants.PARAMS_PARAMETER_KEY, paramsObj);
        performRequest()
        .andDo(print())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.errorcode").value(ERROR_CODE_OK))
        .andExpect(jsonPath("$.entity.liked").value(false))
//        .andExpect(jsonPath("$.entity").value(USER_ID))
        .andExpect(status().isOk());
    }
    
    @Test
    public void testLikePost703() throws Exception{
        bodyObj.put(Constants.TOKEN_PARAMETER_KEY, TOKEN);
        bodyObj.put(Constants.METHOD_PARAMETER_KEY, "703");
        JSONArray ids = new JSONArray();
        ids.add(5);
        ids.add(6);
        paramsObj.put(Constants.IDS_PARAMETER_KEY, ids);
        paramsObj.put(Constants.TYPE_CODE_PARAMETER_KEY, 1);
        bodyObj.put(Constants.PARAMS_PARAMETER_KEY, paramsObj);
        performRequest()
        .andDo(print())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.errorcode").value(ERROR_CODE_OK))
        .andExpect(jsonPath("$.entity").value(USER_ID))
        .andExpect(status().isOk());
    } 
    
    @Test
    public void testFindPosts209after703() throws Exception{
        bodyObj.put(Constants.TOKEN_PARAMETER_KEY, TOKEN);
        bodyObj.put(Constants.METHOD_PARAMETER_KEY, "209");
        paramsObj.put(Constants.MAIN_POST_ID_PARAM_KEY, 2);
        paramsObj.put(Constants.FIRST_RESULT_PARAMETER_KEY, 0);
        paramsObj.put(Constants.MAX_RESULTS_PARAMETER_KEY,3);
        bodyObj.put(Constants.PARAMS_PARAMETER_KEY, paramsObj);
        performRequest()
        .andDo(print())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.errorcode").value(ERROR_CODE_OK))
        .andExpect(jsonPath("$.entity[0].owned").value(true))
        .andExpect(jsonPath("$.entity[0].mainPostOwner").value(true))
        .andExpect(jsonPath("$.entity[0].liked").value(true))
        .andExpect(status().isOk());
    }
    
    
    
    @Test
    public void testUnLikePost704() throws Exception{
        bodyObj.put(Constants.TOKEN_PARAMETER_KEY, TOKEN);
        bodyObj.put(Constants.METHOD_PARAMETER_KEY, "704");
        JSONArray ids = new JSONArray();
        ids.add(5);
        ids.add(6);
        paramsObj.put(Constants.IDS_PARAMETER_KEY, ids);
        paramsObj.put(Constants.TYPE_CODE_PARAMETER_KEY, 1);
        bodyObj.put(Constants.PARAMS_PARAMETER_KEY, paramsObj);
        performRequest()
        .andDo(print())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.errorcode").value(ERROR_CODE_OK))
        .andExpect(jsonPath("$.entity").value(USER_ID))
        .andExpect(status().isOk());
    } 
    
    @Test
    public void testFindPosts209after704() throws Exception{
        bodyObj.put(Constants.TOKEN_PARAMETER_KEY, TOKEN);
        bodyObj.put(Constants.METHOD_PARAMETER_KEY, "209");
        paramsObj.put(Constants.MAIN_POST_ID_PARAM_KEY, 2);
        paramsObj.put(Constants.FIRST_RESULT_PARAMETER_KEY, 0);
        paramsObj.put(Constants.MAX_RESULTS_PARAMETER_KEY,3);
        bodyObj.put(Constants.PARAMS_PARAMETER_KEY, paramsObj);
        performRequest()
        .andDo(print())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.errorcode").value(ERROR_CODE_OK))
        .andExpect(jsonPath("$.entity[0].owned").value(true))
        .andExpect(jsonPath("$.entity[0].mainPostOwner").value(true))
        .andExpect(jsonPath("$.entity[0].liked").value(false))
        .andExpect(status().isOk());
    }
    
    
    @Test
    public void testFindCommunityMemberPage214() throws Exception{
        bodyObj.put(Constants.TOKEN_PARAMETER_KEY, TOKEN);
        bodyObj.put(Constants.METHOD_PARAMETER_KEY, "214");
        paramsObj.put(Constants.USERID_PARAMETER_KEY, "282D6E8A0E70450883B4EE3D75FD7C54");
        bodyObj.put(Constants.PARAMS_PARAMETER_KEY, paramsObj);
        performRequest()
        .andDo(print())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.errorcode").value(ERROR_CODE_OK))
        .andExpect(jsonPath("$.entity.userId").value("282D6E8A0E70450883B4EE3D75FD7C54"))
        .andExpect(jsonPath("$.entity.followed").value(false))
        .andExpect(status().isOk());
    }
    
    @Test
//    @Ignore
    public void testFindRepliedMainPosts215() throws Exception{
        bodyObj.put(Constants.METHOD_PARAMETER_KEY, "215");
        paramsObj.put(Constants.USERID_PARAMETER_KEY, USER_ID);
        paramsObj.put(Constants.TYPE_CODE_PARAMETER_KEY, Constants.REPLIED_MAINPOSTS_TYPE);
        bodyObj.put(Constants.PARAMS_PARAMETER_KEY, paramsObj);
        performRequest()
        .andDo(print())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.errorcode").value(ERROR_CODE_OK))
        .andExpect(jsonPath("$.entity[0].id").value(2))
        .andExpect(status().isOk());
    }
    
    
    @Test
//    @Ignore
    public void testFindCreatedMainPosts215() throws Exception{
        bodyObj.put(Constants.METHOD_PARAMETER_KEY, "215");
        paramsObj.put(Constants.USERID_PARAMETER_KEY, USER_ID);
        paramsObj.put(Constants.TYPE_CODE_PARAMETER_KEY, Constants.CREATED_MAINPOSTS_TYPE);
        paramsObj.put(Constants.MAX_RESULTS_PARAMETER_KEY, 3);
        bodyObj.put(Constants.PARAMS_PARAMETER_KEY, paramsObj);
        performRequest()
        .andDo(print())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.errorcode").value(ERROR_CODE_OK))
        .andExpect(status().isOk());
    }
    
    @Test
    public void testFavoriteMainPost605() throws Exception{
        bodyObj.put(Constants.TOKEN_PARAMETER_KEY, TOKEN);
        bodyObj.put(Constants.METHOD_PARAMETER_KEY, "605");
        JSONArray ids = new JSONArray();
        ids.add(2);
        paramsObj.put(Constants.MAIN_POST_IDS_PARAM_KEY, ids);
        bodyObj.put(Constants.PARAMS_PARAMETER_KEY, paramsObj);
        performRequest()
        .andDo(print())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.errorcode").value(ERROR_CODE_OK))
        .andExpect(jsonPath("$.entity").value(USER_ID))
        .andExpect(status().isOk());
    } 
    
    @Test
    public void testFavoriteMainPost605_2() throws Exception{
        Thread.sleep(1000);
        bodyObj.put(Constants.TOKEN_PARAMETER_KEY, TOKEN);
        bodyObj.put(Constants.METHOD_PARAMETER_KEY, "605");
        JSONArray ids = new JSONArray();
        ids.add(4);
        paramsObj.put(Constants.MAIN_POST_IDS_PARAM_KEY, ids);
        bodyObj.put(Constants.PARAMS_PARAMETER_KEY, paramsObj);
        performRequest()
        .andDo(print())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.errorcode").value(ERROR_CODE_OK))
        .andExpect(jsonPath("$.entity").value(USER_ID))
        .andExpect(status().isOk());
    } 
    
    // 603-find favorite resource
    @Test
    public void testFindfavoriteResource603After605() throws Exception {
        bodyObj.put(Constants.METHOD_PARAMETER_KEY, "603");
        paramsObj.put(Constants.FIRST_RESULT_PARAMETER_KEY, "0");
        paramsObj.put(Constants.USERID_PARAMETER_KEY, USER_ID);
        paramsObj.put(Constants.MAX_RESULTS_PARAMETER_KEY, "5");
//        JSONArray array = new JSONArray();
//        array.add(1);
//        paramsObj.put(Constants.TYPES_PARAMETER_KEY, array);
        bodyObj.put(Constants.PARAMS_PARAMETER_KEY, paramsObj);
        performRequest()
        .andDo(print())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.errorcode").value(ERROR_CODE_OK))
        .andExpect(jsonPath("$.entity[0].type").value(2))
        .andExpect(jsonPath("$.entity[0].id").value(4))
        .andExpect(jsonPath("$.entity[1].type").value(2))
        .andExpect(jsonPath("$.entity[1].id").value(2))
        .andExpect(jsonPath("$.entity[2].type").value(1))     
        .andExpect(jsonPath("$.entity[2].id").value(2))
        .andExpect(jsonPath("$.entity[3].type").value(1))     
        .andExpect(jsonPath("$.entity[3].id").value(1))
        .andExpect(status().isOk());
    }
    
    @Test
    public void testFindMainpostById212After605() throws Exception{
        bodyObj.put(Constants.TOKEN_PARAMETER_KEY, TOKEN);
        bodyObj.put(Constants.METHOD_PARAMETER_KEY, "212");
        paramsObj.put(Constants.MAIN_POST_ID_PARAM_KEY, 2);
        bodyObj.put(Constants.PARAMS_PARAMETER_KEY, paramsObj);
        performRequest()
        .andDo(print())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.errorcode").value(ERROR_CODE_OK))
        .andExpect(jsonPath("$.entity.favorited").value(true))
//        .andExpect(jsonPath("$.entity").value(USER_ID))
        .andExpect(status().isOk());
    }
    
    @Test
  public void testSearchFavoriteResource217() throws Exception{
      bodyObj.put(Constants.TOKEN_PARAMETER_KEY, TOKEN);
      bodyObj.put(Constants.METHOD_PARAMETER_KEY, "217");
      paramsObj.put(Constants.KEYWORD_PARAMETER_KEY,"小超");
//      JSONArray array = new JSONArray();
//      array.add(2);
//      paramsObj.put(Constants.TYPES_PARAMETER_KEY, array);
      bodyObj.put(Constants.PARAMS_PARAMETER_KEY, paramsObj);
      performRequest()
      .andDo(print())
      .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.errorcode").value(ERROR_CODE_OK))
      .andExpect(jsonPath("$.entity[0].type").value(2))
      .andExpect(jsonPath("$.entity[0].id").value(4))
      .andExpect(jsonPath("$.entity[1].type").value(2))
      .andExpect(jsonPath("$.entity[1].id").value(2))
      .andExpect(jsonPath("$.entity[2].type").value(1))
      .andExpect(jsonPath("$.entity[2].id").value(2))
      .andExpect(status().isOk());
  }
    
    @Test
    public void testUnFavoriteMainPost606() throws Exception{
        bodyObj.put(Constants.TOKEN_PARAMETER_KEY, TOKEN);
        bodyObj.put(Constants.METHOD_PARAMETER_KEY, "606");
        JSONArray ids = new JSONArray();
        ids.add(2);
        ids.add(4);
        paramsObj.put(Constants.MAIN_POST_IDS_PARAM_KEY, ids);
        bodyObj.put(Constants.PARAMS_PARAMETER_KEY, paramsObj);
        performRequest()
        .andDo(print())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.errorcode").value(ERROR_CODE_OK))
        .andExpect(jsonPath("$.entity").value(USER_ID))
        .andExpect(status().isOk());
    }   
    
    // 602-unfavorite news
    @Test
    public void testUnfavoriteNews602() throws Exception {
        newsIds.add(1);
        newsIds.add(2);
        bodyObj.put(Constants.TOKEN_PARAMETER_KEY, TOKEN);
        bodyObj.put(Constants.METHOD_PARAMETER_KEY, "602");
        paramsObj.put(Constants.NEWS_IDS_PARAMETER_KEY, newsIds);
        bodyObj.put(Constants.PARAMS_PARAMETER_KEY, paramsObj);
        performRequest()
        .andDo(print())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.errorcode").value(ERROR_CODE_OK))
        .andExpect(jsonPath("$.entity").value(USER_ID))
        .andExpect(status().isOk());
    }
    
    // 109-follow user
    @Test
    public void testFollowUser109() throws Exception {
        bodyObj.put(Constants.TOKEN_PARAMETER_KEY, TOKEN);
        bodyObj.put(Constants.METHOD_PARAMETER_KEY, "109");
        paramsObj.put(Constants.USERID_PARAMETER_KEY, "282D6E8A0E70450883B4EE3D75FD7C54");
        bodyObj.put(Constants.PARAMS_PARAMETER_KEY, paramsObj);
        performRequest()
        .andDo(print())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.errorcode").value(ERROR_CODE_OK))
        .andExpect(jsonPath("$.entity").value(USER_ID))
        .andExpect(status().isOk());
    }
    
    @Test
    public void testPushFollowUser109() throws Exception {
        bodyObj.put(Constants.TOKEN_PARAMETER_KEY, "AF8CF9AB932F43488B883281FBF6A6C7");
        bodyObj.put(Constants.METHOD_PARAMETER_KEY, "109");
        paramsObj.put(Constants.USERID_PARAMETER_KEY, USER_ID);
        bodyObj.put(Constants.PARAMS_PARAMETER_KEY, paramsObj);
        performRequest()
        .andDo(print())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.errorcode").value(ERROR_CODE_OK))
        .andExpect(jsonPath("$.entity").value("B813E7B7F8FD47899FF54091A843A836"))
        .andExpect(status().isOk());
    }
    
    @Test
    public void testUnFollowUser110AfterPush() throws Exception {
        bodyObj.put(Constants.TOKEN_PARAMETER_KEY, "AF8CF9AB932F43488B883281FBF6A6C7");
        bodyObj.put(Constants.METHOD_PARAMETER_KEY, "110");
        paramsObj.put(Constants.USERID_PARAMETER_KEY, USER_ID);
        bodyObj.put(Constants.PARAMS_PARAMETER_KEY, paramsObj);
        performRequest()
        .andDo(print())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.errorcode").value(ERROR_CODE_OK))
        .andExpect(jsonPath("$.entity").value("B813E7B7F8FD47899FF54091A843A836"))
        .andExpect(status().isOk());
    }
    
    @Test
    public void testFindCommunityMemberPage214After109() throws Exception{
        bodyObj.put(Constants.TOKEN_PARAMETER_KEY, TOKEN);
        bodyObj.put(Constants.METHOD_PARAMETER_KEY, "214");
        paramsObj.put(Constants.USERID_PARAMETER_KEY, "282D6E8A0E70450883B4EE3D75FD7C54");
        bodyObj.put(Constants.PARAMS_PARAMETER_KEY, paramsObj);
        performRequest()
        .andDo(print())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.errorcode").value(ERROR_CODE_OK))
        .andExpect(jsonPath("$.entity.userId").value("282D6E8A0E70450883B4EE3D75FD7C54"))
        .andExpect(jsonPath("$.entity.followed").value(true))
        .andExpect(status().isOk());
    }
    
    // 109-follow user
    @Test
    public void testFollowUser109_2() throws Exception {
        bodyObj.put(Constants.TOKEN_PARAMETER_KEY, TOKEN);
        bodyObj.put(Constants.METHOD_PARAMETER_KEY, "109");
        paramsObj.put(Constants.USERID_PARAMETER_KEY, "19D44CF056FF4A608D257BF6C4538255");
        bodyObj.put(Constants.PARAMS_PARAMETER_KEY, paramsObj);
        performRequest()
        .andDo(print())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.errorcode").value(ERROR_CODE_OK))
        .andExpect(jsonPath("$.entity").value(USER_ID))
        .andExpect(status().isOk());
    }
    
    @Test
    public void testFindFollowers218After109() throws Exception {
//        bodyObj.put(Constants.TOKEN_PARAMETER_KEY, TOKEN);
        bodyObj.put(Constants.METHOD_PARAMETER_KEY, "218");
        paramsObj.put(Constants.USERID_PARAMETER_KEY, "282D6E8A0E70450883B4EE3D75FD7C54");
        paramsObj.put(Constants.TYPE_CODE_PARAMETER_KEY, 1);
        bodyObj.put(Constants.PARAMS_PARAMETER_KEY, paramsObj);
        performRequest()
        .andDo(print())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.errorcode").value(ERROR_CODE_OK))
        .andExpect(jsonPath("$.entity[0].userId").value(USER_ID))
        .andExpect(status().isOk());
    }
    
    @Test
    public void testFindFriends218After109() throws Exception {
//        bodyObj.put(Constants.TOKEN_PARAMETER_KEY, TOKEN);
        bodyObj.put(Constants.METHOD_PARAMETER_KEY, "218");
        paramsObj.put(Constants.USERID_PARAMETER_KEY, USER_ID);
        paramsObj.put(Constants.TYPE_CODE_PARAMETER_KEY, 2);
        bodyObj.put(Constants.PARAMS_PARAMETER_KEY, paramsObj);
        performRequest()
        .andDo(print())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.errorcode").value(ERROR_CODE_OK))
        .andExpect(jsonPath("$.entity[0].userId").value("19D44CF056FF4A608D257BF6C4538255"))
        .andExpect(jsonPath("$.entity[1].userId").value("282D6E8A0E70450883B4EE3D75FD7C54"))
        .andExpect(status().isOk());
    }
    
    // 110-unfollow user
    @Test
    public void testUnFollowUser110() throws Exception {
        bodyObj.put(Constants.TOKEN_PARAMETER_KEY, TOKEN);
        bodyObj.put(Constants.METHOD_PARAMETER_KEY, "110");
        paramsObj.put(Constants.USERID_PARAMETER_KEY, "282D6E8A0E70450883B4EE3D75FD7C54");
        bodyObj.put(Constants.PARAMS_PARAMETER_KEY, paramsObj);
        performRequest()
        .andDo(print())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.errorcode").value(ERROR_CODE_OK))
        .andExpect(jsonPath("$.entity").value(USER_ID))
        .andExpect(status().isOk());
    }
    
    // 110-unfollow user
    @Test
    public void testUnFollowUser110_2() throws Exception {
        bodyObj.put(Constants.TOKEN_PARAMETER_KEY, TOKEN);
        bodyObj.put(Constants.METHOD_PARAMETER_KEY, "110");
        paramsObj.put(Constants.USERID_PARAMETER_KEY, "19D44CF056FF4A608D257BF6C4538255");
        bodyObj.put(Constants.PARAMS_PARAMETER_KEY, paramsObj);
        performRequest()
        .andDo(print())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.errorcode").value(ERROR_CODE_OK))
        .andExpect(jsonPath("$.entity").value(USER_ID))
        .andExpect(status().isOk());
    }
    
    @Test
    public void test1001() throws Exception {
        bodyObj.put(Constants.TOKEN_PARAMETER_KEY, TOKEN);
        bodyObj.put(Constants.METHOD_PARAMETER_KEY, "1001");
        
        JSONArray itemRecords = new JSONArray();
        //timestamp
        JSONObject timestamp = new JSONObject();
        timestamp.put("timestamp", Calendar.getInstance().getTimeInMillis());
        itemRecords.add(timestamp);
        JSONObject itemValue =  new JSONObject();
        //vegetable
        JSONObject vegetable  = new JSONObject();
        vegetable.put("id", 1);
        itemValue.put("time",21);
        vegetable.put("value", itemValue);
        itemRecords.add(vegetable);
        //bottled water
        itemValue.clear();
        JSONObject bottledWarter = new JSONObject();
        bottledWarter.put("id", 2);
        itemValue.put("count", 5);
        bottledWarter.put("value", itemValue);
        itemRecords.add(bottledWarter);
        //swimming 
        itemValue.clear();
        JSONObject swimming = new JSONObject();
        swimming.put("id", 3);
        itemValue.put("count", 4);
        swimming.put("value", itemValue);
        itemRecords.add(swimming);
        //wake up
        itemValue.clear();
        JSONObject wakeUp = new JSONObject();
        wakeUp.put("id", 4);
        itemValue.put("time", 10);
        wakeUp.put("value", itemValue);
        itemRecords.add(wakeUp);
        //sleep
        itemValue.clear();
        JSONObject sleep = new JSONObject();
        sleep.put("id", 5);
        itemValue.put("time", 22);
        sleep.put("value", itemValue);
        itemRecords.add(sleep);
        //insomnia 
        itemValue.clear();
        JSONObject insomnia = new JSONObject();
        insomnia.put("id", 6);
        itemValue.put("checked", true);
        insomnia.put("value", itemValue);
        itemRecords.add(insomnia);
        paramsObj.put(Constants.HEALTH_EVALUATION_ITEM_RECORDS, itemRecords);
        bodyObj.put(Constants.PARAMS_PARAMETER_KEY, paramsObj);
        performRequest()
        .andDo(print())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.errorcode").value(ERROR_CODE_OK))
        .andExpect(jsonPath("$.entity").value(true))
        .andExpect(status().isOk());
    }
    
    private ResultActions performRequest() throws Exception {
        return this.mockMvc.perform(post("/request").param(BODY_PARAM_KEY, bodyObj.toString()));
    }
}
