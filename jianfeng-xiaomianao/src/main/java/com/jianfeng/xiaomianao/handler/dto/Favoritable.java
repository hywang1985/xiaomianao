package com.jianfeng.xiaomianao.handler.dto;

import java.util.Date;

public interface Favoritable extends Comparable<Favoritable>{

    public void setFavTime(Date favTime);

    public Date getFavTime();
}
