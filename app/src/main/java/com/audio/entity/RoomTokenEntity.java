package com.audio.entity;

/**
 * @name ${yuansk}
 * @class name：com.audio.entity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2019/3/8 15:21
 * @change
 * @chang time
 * @class describe
 */
public class RoomTokenEntity {


    /**
     * doorName : 12楼集团总部大门[七星商业广场]
     * communityName : 七星商业广场
     * communityID : bcfe0f35-37b0-49cf-a73d-ca96914a46a5
     * doorID : 1537401129129
     * roomName : 09dfff64-8a0f-4663-9ec2-17f2a1ec5b74
     * roomToken : hLnn7j1_5_Gwg0mOKATS
     */

    private String doorName;
    private String communityName;
    private String communityID;
    private String doorID;

    public long getSendTime() {
        return sendTime;
    }

    public void setSendTime(long sendTime) {
        this.sendTime = sendTime;
    }

    private long sendTime;
    private String roomName;
    private String roomToken;

    public String getDoorName() {
        return doorName;
    }

    public void setDoorName(String doorName) {
        this.doorName = doorName;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public String getCommunityID() {
        return communityID;
    }

    public void setCommunityID(String communityID) {
        this.communityID = communityID;
    }

    public String getDoorID() {
        return doorID;
    }

    public void setDoorID(String doorID) {
        this.doorID = doorID;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomToken() {
        return roomToken;
    }

    public void setRoomToken(String roomToken) {
        this.roomToken = roomToken;
    }
}
