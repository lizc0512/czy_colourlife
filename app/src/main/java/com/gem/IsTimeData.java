package com.gem;

/**
 * Created by liusw on 2016/12/15.
 */
public class IsTimeData
{
    private static IsTimeData isTimeData = null;

    private String is_time;

    private IsTimeData() {

    }
    public static IsTimeData getInstance(){
        if (null == isTimeData) {
            isTimeData = new IsTimeData();
        }
        return isTimeData;
    }

    public void setLevel(String level){
        this.is_time = level;
    }

    public String getLevel(){
        return is_time;
    }
}
