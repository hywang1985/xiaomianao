package com.jianfeng.xiaomianao.handler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.jianfeng.xiaomianao.handler.dto.EventsContained;
import com.jianfeng.xiaomianao.push.event.MianaoEvent;

/**
 * This class is used to bring back the events added by current handler.
 * The handlers and the HandlerDispatcher is in singleton scope under the MianaoController(which also in singleton), 
 * so need a uniformed result to bring back the events after the business transaction has been committed. 
 * 
 * @See HandlerDispather to know the event dispatching mechanism.
 * 
 * @author hywang
 */
public class EventsContainedHandlerResult implements EventsContained, DataContained {

    private Object data;

    private List<MianaoEvent> events = new ArrayList<MianaoEvent>();

    @Override
    public void addEvent(MianaoEvent event) {
        
        if (event != null && !events.contains(event)) {
            events.add(event);
        }
    }

    public void addEvents(List<MianaoEvent> events) {
        if (events != null && !events.isEmpty()) {
            for (MianaoEvent event : events) {
                addEvent(event);
            }
        }
    }

    @Override
    public List<MianaoEvent> getEvents() {
        return Collections.unmodifiableList(events);
    }

    @Override
    public void clearEvents() {
        events.clear();
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
