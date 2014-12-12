package com.jianfeng.xiaomianao.dao;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jianfeng.xiaomianao.domain.NewsInfoBean;

public class NewsInfoDaoTests extends AbstractDaoTests {

    private static final Integer RETRIEVED_ID = 21;

    private static final int CATEGORY = 1;

    private static final int START_POSITION = 1; //Start from 2nd record

    private static final int MAX_RESULT_COUNT = 5;
    
    private static final String EXPECTED_BRIEF_CONTENT = "BLABLABLA";
    
    private static final List<String> TAGS= new ArrayList<String>();
    
    static {
        TAGS.add("私处");
        TAGS.add("经期");
        TAGS.add("妇科疾病");
    }

    @Autowired
    protected NewsInfoDao newsDao;

    @Test
    @Ignore
    public void testAddNews(){
        NewsInfoBean newOne = new NewsInfoBean();
        newOne.setId(23);
        JSONArray array = new JSONArray();
        JSONObject content1 = new JSONObject();
        content1.put("text","文文你好");
        content1.put("image", "http://182.92.216.40/xiaomianaoImg/images/53631412912529331.jpg");
        JSONObject content2 = new JSONObject();
        content2.put("text","小悠你好");
        content2.put("image", "http://182.92.216.40/xiaomianaoImg/images/53631412912529331.jpg");
        JSONObject content3 = new JSONObject();
        content3.put("text","小锐你好");
        content3.put("image", "http://182.92.216.40/xiaomianaoImg/images/53631412912529331.jpg");
        array.add(content1);
        array.add(content2);
        array.add(content3);
        newOne.setContent(array.toString());
        newsDao.create(newOne);
    }
    
    @Test
    public void testFindNewsById() {
        NewsInfoBean retrievedNews = newsDao.findNewsById(RETRIEVED_ID);
        String briefContent = retrievedNews.getBriefContent();
        Assert.assertNotNull(briefContent);
        briefContent.equals(EXPECTED_BRIEF_CONTENT);
        Assert.assertNotNull(retrievedNews);
    }

    @Test
    public void testFindNewsByCategory() {
        List<NewsInfoBean> retrievedNews = newsDao.findNews(CATEGORY, START_POSITION, MAX_RESULT_COUNT);
        Assert.assertNotNull(retrievedNews);
        Assert.assertEquals(MAX_RESULT_COUNT, retrievedNews.size());
    }
    
    @Test 
    public void testFindNewsByTags(){
        List<NewsInfoBean> retrievedNews = newsDao.findNewsByTags(TAGS,START_POSITION,MAX_RESULT_COUNT);
        Assert.assertNotNull(retrievedNews);
        Assert.assertEquals(4, retrievedNews.size());
        int id1 = retrievedNews.get(0).getId();
        Assert.assertEquals(4,id1);
        int id2 = retrievedNews.get(1).getId();
        Assert.assertEquals(3,id2);
        int id3 = retrievedNews.get(2).getId();
        Assert.assertEquals(2,id3);
        int id4 = retrievedNews.get(3).getId();
        Assert.assertEquals(1,id4);
    }
    
    /**
     * The tag-matched results was 3 but there should 
     * be only 2 records returned due to set the limit of maxResults to 2.
     * */
    @Test 
    public void testFindNewsBySingleTag(){
        TAGS.clear();
        TAGS.add("私处");
        List<NewsInfoBean> retrievedNews = newsDao.findNewsByTags(TAGS, START_POSITION, MAX_RESULT_COUNT);
        Assert.assertNotNull(retrievedNews);
        Assert.assertEquals(2, retrievedNews.size());
        int id1 = retrievedNews.get(0).getId();
        Assert.assertEquals(4,id1);
        int id2 = retrievedNews.get(1).getId();
        Assert.assertEquals(1,id2);
    }
    
    @Test
    public void testSearchFavoriteNews(){
        List<NewsInfoBean> retrievedNews = newsDao.searchFavoriteNews("100008", "小超", 0, 5);
        Assert.assertNotNull(retrievedNews);
        Assert.assertEquals(2,retrievedNews.size());
    }
}
