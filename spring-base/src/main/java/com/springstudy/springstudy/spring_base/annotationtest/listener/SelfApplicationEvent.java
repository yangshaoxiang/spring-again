package com.springstudy.springstudy.spring_base.annotationtest.listener;

import com.springstudy.springstudy.entry.EventModel;
import org.springframework.context.ApplicationEvent;

public class SelfApplicationEvent extends ApplicationEvent {

    private EventModel eventModel;

    public SelfApplicationEvent(EventModel eventModel) {
        super(eventModel);
        this.eventModel = eventModel;
    }

    public  EventModel getEventModel(){
        return eventModel;
    }




}
