package cn.net.cyberway.utils;

/**
 * 百度定位监听
 */

public interface LocationListener {
    public void onReceiveLocation();//定位成功之后的回调
    public void onError();
}
