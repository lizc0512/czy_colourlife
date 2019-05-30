package com.invite.model;

/**
 * EventType
 * Created by chenql on 16/1/11.
 */
public class EventType {

    public static String BIRTHDAY = "BIRTHDAY";

    public static String getActualType(int type) {
        String actualType = "";

        switch (type) {
            case 0:
                actualType = "CUSTOM";
                break;
            case 1:
                actualType = "ANNIVERSARY";
                break;
            case 2:
                actualType = "OTHER";
                break;
            case 3:
                actualType = "BIRTHDAY";
                break;
            default:
                actualType = "UNKNOWN";
                break;
        }

        return actualType;
    }
}