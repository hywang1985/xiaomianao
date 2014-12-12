package com.jianfeng.xiaomianao.service;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jianfeng.xiaomianao.dao.CommentDao;
import com.jianfeng.xiaomianao.dao.NewsInfoDao;
import com.jianfeng.xiaomianao.domain.Comment;
import com.jianfeng.xiaomianao.domain.DomainEntityFactory;
import com.jianfeng.xiaomianao.domain.MianaouserinfoBean;
import com.jianfeng.xiaomianao.domain.NewsInfoBean;
import com.jianfeng.xiaomianao.exception.XiaoMianAoException;
import com.jianfeng.xiaomianao.util.DateUtil;
import com.jianfeng.xiaomianao.util.ErrorCode;

@Service
public class CommentService extends AbstractUserNeededService {

    private Logger logger = LoggerFactory.getLogger(CommentService.class);

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private NewsInfoDao newsDao;

    public Comment addComment(Integer newsId, Integer parentId, String content, String userId) {

        MianaouserinfoBean user = findUser(userId);
        Comment comment = DomainEntityFactory.eInstance.createComment();
        if (parentId != null && parentId.intValue() > 0) {
            Comment parentComment = commentDao.read(parentId);
            if (parentComment != null) {
                comment.setParent(parentComment);
            }
        }
        NewsInfoBean news = newsDao.read(newsId);
        if (news != null) {
            comment.setNews(news);
        }
        comment.setContent(content);
        comment.setOwner(user);
        comment.setCreatetime(DateUtil.getCurrentDate());
        comment.setUpdatetime(DateUtil.getCurrentDate());
        commentDao.create(comment);
        return comment;
    }

    public Comment updateComment(String userId, Integer commentId, String content) {

        if (userId == null || StringUtils.isBlank(userId)) {
            throw new XiaoMianAoException(ErrorCode.USER_NOT_EXIST);
        }
        Comment comment = commentDao.read(commentId);
        if (comment != null) {
            comment.setContent(content);
            comment.setUpdatetime(DateUtil.getCurrentDate());
        }
        return commentDao.update(comment);
    }

    public void deleteComment(String userId, Integer id) {

        if (userId == null || StringUtils.isBlank(userId)) {
            throw new XiaoMianAoException(ErrorCode.USER_NOT_EXIST);
        }
        Comment comment = commentDao.read(id);
        comment.setOwner(null);
        comment.setParent(null);
        commentDao.delete(comment);
    }

}
