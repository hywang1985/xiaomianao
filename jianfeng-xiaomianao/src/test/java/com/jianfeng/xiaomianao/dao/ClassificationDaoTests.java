package com.jianfeng.xiaomianao.dao;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jianfeng.xiaomianao.domain.Classification;
import com.jianfeng.xiaomianao.domain.Tag;
import com.jianfeng.xiaomianao.util.JsonUtil;

public class ClassificationDaoTests extends AbstractDaoTests {

    private static final int FIRST_RESULT = 0;
    
    private static final int MAX_RESULT = 5;
   
    @Autowired
    private ClassificationDao classDao;

    @Test
    public void testGetClasses() {
        List<Classification> classes = classDao.getClasses(FIRST_RESULT,MAX_RESULT);
        Assert.assertNotNull(classes);
        Classification womenHealth = classes.get(0);
        System.out.println(JsonUtil.toJson(womenHealth));
        List<Tag> containedTags = womenHealth.getTags();
        Assert.assertNotNull(containedTags);
        Iterator<Tag> tagItor = containedTags.iterator();
        while (tagItor.hasNext()) {
            Tag tag = tagItor.next();
            Assert.assertNotNull(tag);
            System.out.println(tag.getName());
        }
    }
}
