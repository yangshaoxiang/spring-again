package com.springstudy.springstudy.entry;

import lombok.Data;

/**
 *  家类
 */
@Data
public class Home {
    private Integer homeNum;
    private String room;

    public Home() {
    }

    public Home(Integer homeNum, String room) {
        this.homeNum = homeNum;
        this.room = room;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public Integer getHomeNum() {
        return homeNum;
    }

    public void setHomeNum(Integer homeNum) {
        this.homeNum = homeNum;
    }
}
