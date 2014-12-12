package com.jianfeng.xiaomianao.handler.dto;

import java.util.List;

import com.jianfeng.xiaomianao.push.event.MianaoEvent;


public interface EventsContained {

    public void addEvent(MianaoEvent event);
    
    public void addEvents(List<MianaoEvent> events);

    public List<MianaoEvent> getEvents();
    
    public void clearEvents();
}
