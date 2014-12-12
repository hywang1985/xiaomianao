package com.jianfeng.xiaomianao.domain;

public enum DomainEntityFactory {
    eInstance;

    public AuthenticationToken createAutenticationToken() {
        return new AuthenticationToken();
    }
    
    public PushChannelInfo createPushChannelInfo(){
        return new PushChannelInfo();
    }
    
    public PushPreference createPushPreference(){
        return new PushPreference();
    }

    public Classification createClassification() {
        return new Classification();
    }

    public Comment createComment() {
        return new Comment();
    }

    public MianaouserinfoBean createMianaoUser() {
        return new MianaouserinfoBean();
    }

    public NewsInfoBean createNews() {
        return new NewsInfoBean();
    }

    public Tag createTag() {
        return new Tag();
    }

    public MianaoState createMianaoState() {
        return new MianaoState();
    }

    public Post createPost() {
        return new Post();
    }

    public MainPost createMainPost() {
        return new MainPost();
    }

    public ImageDescriptor createImageDescriptor() {
        return new ImageDescriptor();
    }

    public CommunityClassification createCommunityClass() {
        return new CommunityClassification();
    }

    public CommunityTag createCommunityTag() {
        return new CommunityTag();
    }

    public HealthEvaluationItemRecord createHealthEvaluationItemRecord() {
        return new HealthEvaluationItemRecord();
    }

    public FavoriteRelation createFavoriteRelation() {
        return new FavoriteRelation();
    }

    public LikeRelation createLikeRelation() {
        return new LikeRelation();
    }
}
