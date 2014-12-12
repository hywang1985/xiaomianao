package com.jianfeng.xiaomianao.dao;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jianfeng.xiaomianao.domain.Tag;

public class TagDaoTests extends AbstractDaoTests {

    private static final String CLASS_NAME = "女性保养";

    private static final int START_POSITION = 0;

    private static final int MAX_RESUILT = 4;

    @Autowired
    private TagDao tagDao;

    @Test
    public void testGetTagsByClass() {
        List<Tag> tags = tagDao.findTagsByClass(CLASS_NAME, START_POSITION, MAX_RESUILT);
        Assert.assertNotNull(tags);
        Assert.assertEquals(4, tags.size());
        for (int i = 0; i <= MAX_RESUILT - 1; i++) {
            Tag tag = tags.get(i);
            Assert.assertNotNull(tag);
            int id = tag.getId();
            Assert.assertEquals(++i, id);
        }
    }

    @Test
    public void testGetTagsByName() {
        List<String> tagNames = new ArrayList<String>();
        tagNames.add("私处");
        tagNames.add("经期");
        List<Tag> tags = tagDao.findTagsByName(tagNames);
        Assert.assertNotNull(tags);
        Assert.assertEquals(2, tags.size());
    }
}
