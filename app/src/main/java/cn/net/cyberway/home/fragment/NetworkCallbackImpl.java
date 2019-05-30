package cn.net.cyberway.home.fragment;

import android.annotation.TargetApi;
import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Build;
import android.os.Message;

import com.external.eventbus.EventBus;
import com.user.UserMessageConstant;

/**
 * @name ${yuansk}
 * @class name：cn.net.cyberway.home.fragment
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/12/17 16:13
 * @change
 * @chang time
 * @class describe
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class NetworkCallbackImpl extends ConnectivityManager.NetworkCallback {
    @Override
    public void onAvailable(Network network) {
        super.onAvailable(network);
        Message netMessage = new Message();
        netMessage.what = UserMessageConstant.NET_CONN_CHANGE;
        netMessage.arg1=1;
        EventBus.getDefault().post(netMessage);
    }

    @Override
    public void onLosing(Network network, int maxMsToLive) {
        super.onLosing(network, maxMsToLive);
    }

    @Override
    public void onLost(Network network) {
        super.onLost(network);
        Message netMessage = new Message();
        netMessage.what = UserMessageConstant.NET_CONN_CHANGE;
        netMessage.arg1=0;
        EventBus.getDefault().post(netMessage);
    }

    @Override
    public void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
        super.onCapabilitiesChanged(network, networkCapabilities);
    }

    @Override
    public void onLinkPropertiesChanged(Network network, LinkProperties linkProperties) {
        super.onLinkPropertiesChanged(network, linkProperties);
    }
}
