package com.jianfeng.xiaomianao.service;

import java.util.List;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jianfeng.xiaomianao.dao.NewsInfoDao;
import com.jianfeng.xiaomianao.dao.UserDao;
import com.jianfeng.xiaomianao.domain.Comment;
import com.jianfeng.xiaomianao.domain.MianaouserinfoBean;
import com.jianfeng.xiaomianao.domain.NewsInfoBean;

public class CommentServiceTests extends AbstractServiceTests {

    @Autowired
    private CommentService commentService;

    @Autowired
    private QueryService queryService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private NewsInfoDao newsDao;

    @Test
    public void testAddComment() {
        // 给第一篇文章加两条评论
        commentService.addComment(1, null, "这是评论在第一篇文章的评论1", USER_ID);
        commentService.addComment(1, null, "这是评论在第一篇文章的评论2", USER_ID);
        // 给第二篇文章加一条评论
        Comment commentOfnews2 = commentService.addComment(2, null, "这是评论在第二篇文章的评论1", USER_ID);
        // 给第二篇文章的第一条评论加一条子评论
        commentService.addComment(2, commentOfnews2.getId(), "这是评论在第二篇文章的评论1上的评论1", USER_ID);
        MianaouserinfoBean user = userDao.findUserByUserId(USER_ID);
        Set<Comment> commentsOfUser = user.getComments();
        Assert.assertNotNull(commentsOfUser);
        Assert.assertEquals(4, commentsOfUser.size());
        NewsInfoBean news1 = newsDao.read(1);
        NewsInfoBean news2 = newsDao.read(2);
        Set<Comment> commentsOfNews1 = news1.getComments();
        Assert.assertNotNull(commentsOfNews1);
        Assert.assertEquals(2, commentsOfNews1.size());
        Set<Comment> commentsOfNews2 = news2.getComments();
        Assert.assertNotNull(commentsOfNews2);
        Assert.assertEquals(2, commentsOfNews2.size());
    }

    @Test
    public void testFindCommentByNewsInfoId() {
        // 第一篇资讯的所有评论
        List<Comment> commentsInNews1 = (List<Comment>) queryService.findCommentsByNewsId(USER_ID,1, 0, 5).get("records");
        Assert.assertNotNull(commentsInNews1);
        Assert.assertEquals(2, commentsInNews1.size());
        Comment comment1 = commentsInNews1.get(0);
        Assert.assertNotNull(comment1);
        Assert.assertEquals(1, comment1.getNews().getId());
        Assert.assertEquals(USER_ID, comment1.getOwner().getUserid());
        Comment comment2 = commentsInNews1.get(1);
        Assert.assertNotNull(comment2);
        Assert.assertEquals(1, comment2.getNews().getId());
        Assert.assertEquals(USER_ID, comment2.getOwner().getUserid());
        // 第二篇资讯的所有评论
        List<Comment> commentsInNews2 = (List<Comment>) queryService.findCommentsByNewsId(USER_ID,2, 0, 5).get("records");
        Assert.assertNotNull(commentsInNews2);
        Assert.assertEquals(2, commentsInNews2.size());
        Comment comment1Ofnews2 = commentsInNews2.get(0);
        Comment comment2Ofnews2 = commentsInNews2.get(1);
        // 断言第二篇资讯的第一条评论
        Assert.assertNotNull(comment1Ofnews2);
        Assert.assertEquals(2, comment1Ofnews2.getNews().getId());
        Assert.assertEquals(USER_ID, comment1Ofnews2.getOwner().getUserid());
        Assert.assertEquals(1, comment1Ofnews2.getChildren().size());
        // 断言第二篇资讯的第二条评论
        Assert.assertNotNull(comment2Ofnews2);
        Assert.assertEquals(2, comment2Ofnews2.getNews().getId());
        Assert.assertEquals(USER_ID, comment2Ofnews2.getOwner().getUserid());
        Assert.assertEquals(comment1Ofnews2.getId(), comment2Ofnews2.getParent().getId());
    }

    @Test
    public void testUpdateComment() {
        Comment comment1 = queryService.findCommentById(USER_ID,1);
        String originalCommentContent = comment1.getContent();
        String newContent = "这是更改后的Comment";
        comment1 = commentService.updateComment(USER_ID, 1, newContent);
        Assert.assertNotSame(originalCommentContent, comment1.getContent());
    }

    @Test
    @Ignore
    public void testDeleteComment() {
        commentService.deleteComment(USER_ID, 3);
        commentService.deleteComment(USER_ID, 4);
        List<Comment> commentsInNews2 = (List<Comment>) queryService.findCommentsByNewsId(USER_ID,2, 0, 5).get("records");
        Assert.assertEquals(0, commentsInNews2.size());
        Set<Comment> restComments = queryService.findCommentById(USER_ID,1).getOwner().getComments();
        Assert.assertEquals(2, restComments.size());
    }
}
