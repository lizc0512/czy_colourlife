package com.door.entity;

/**
 * Created by Administrator on 2018/9/12.
 *
 * @Description
 */

public class DoorBaseEntity {
    public String door_id;
    public String position;
    public String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDoor_id() {
        return door_id;
    }

    public void setDoor_id(String door_id) {
        this.door_id = door_id;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
