package com.jianfeng.xiaomianao.handler.dto;

import com.jianfeng.xiaomianao.handler.dto.community.CommunityBriefDTO;
import com.jianfeng.xiaomianao.handler.dto.community.CommunityClassDTO;
import com.jianfeng.xiaomianao.handler.dto.community.CommunityListPageDTO;
import com.jianfeng.xiaomianao.handler.dto.news.FavoriteNewsDTO;
import com.jianfeng.xiaomianao.handler.dto.news.LikedNewsDTO;
import com.jianfeng.xiaomianao.handler.dto.news.NewsInfoBriefDTO;
import com.jianfeng.xiaomianao.handler.dto.news.TopBarNewsDTO;
import com.jianfeng.xiaomianao.handler.dto.post.DegestMainPostDTO;
import com.jianfeng.xiaomianao.handler.dto.post.FavoriteMainPostDTO;
import com.jianfeng.xiaomianao.handler.dto.post.LikedMainPostDTO;
import com.jianfeng.xiaomianao.handler.dto.post.MainPostDTO;
import com.jianfeng.xiaomianao.handler.dto.post.MainPostFullDTO;
import com.jianfeng.xiaomianao.handler.dto.post.ParentPostDTO;
import com.jianfeng.xiaomianao.handler.dto.post.PostDTO;
import com.jianfeng.xiaomianao.handler.dto.user.CommunityMemberDTO;
import com.jianfeng.xiaomianao.handler.dto.user.CommunityMemeberPageDTO;
import com.jianfeng.xiaomianao.handler.dto.user.UserDTO;
import com.jianfeng.xiaomianao.handler.dto.user.UserListMemberDTO;
import com.jianfeng.xiaomianao.push.PushInfo;

public enum DTOFactory {
    eInstance;

    public NewsInfoBriefDTO createNewsInfoBriefDTO() {
        return new NewsInfoBriefDTO();
    }
    
    public LikedNewsDTO createLikedNewsDTO(){
        return new LikedNewsDTO();
    }
    
    public FavoriteNewsDTO createFavoriteNewsDTO(){
        return new FavoriteNewsDTO();
    }

    public CommunityBriefDTO createCommunityBriefDTO() {
        return new CommunityBriefDTO();
    }

    public CommunityClassDTO createCommunityClassDTO() {
        return new CommunityClassDTO();
    }

    public TopBarNewsDTO createTopBarNewsDTO() {
        return new TopBarNewsDTO();
    }

    public MainPostDTO createMainPostDTO() {
        return new MainPostDTO();
    }
    
    public FavoriteMainPostDTO createFavoriteMainPostDTO(){
        return new FavoriteMainPostDTO();
    }
    
    public LikedMainPostDTO createLikedMainPostDTO(){
        return new LikedMainPostDTO();
    }

    public DegestMainPostDTO createDegestMainPostDTO() {
        return new DegestMainPostDTO();
    }

    public MainPostFullDTO createMainPostFullDTO() {
        return new MainPostFullDTO();
    }

    public ParentPostDTO createParentPostDTO() {
        return new ParentPostDTO();
    }

    public PostDTO createPostDTO() {
        return new PostDTO();
    }

    public CommunityMemberDTO createCommunityMemberDTO() {
        return new CommunityMemberDTO();
    }

    public UserDTO createUserDTO() {
        return new UserDTO();
    }

    public CommunityListPageDTO createCommunityListPageDTO() {
        return new CommunityListPageDTO();
    }

    public CommunityMemeberPageDTO createCommunityMemeberPageDTO() {
        return new CommunityMemeberPageDTO();
    }
    
    public UserListMemberDTO createUserListMemberDTO(){
        return new UserListMemberDTO();
    }
}
