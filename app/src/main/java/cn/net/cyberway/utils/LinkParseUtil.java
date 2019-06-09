package cn.net.cyberway.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.BeeFramework.Utils.Utils;
import com.BeeFramework.activity.WebViewActivity;
import com.BeeFramework.model.Constants;
import com.about.activity.FeedBackActivity;
import com.allapp.activity.WholeApplicationActivity;
import com.cardcoupons.activity.CardCouponsActivity;
import com.cashier.activity.OrderListActivity;
import com.customerInfo.activity.CustomerColourBeanActivity;
import com.customerInfo.activity.CustomerInfoActivity;
import com.customerInfo.activity.DeliveryAddressListActivity;
import com.dashuview.library.keep.Cqb_PayUtil;
import com.door.activity.NewDoorActivity;
import com.eparking.activity.AppointmentParkingActivity;
import com.eparking.activity.EParkingCardHolderActivity;
import com.eparking.activity.EParkingHistoryRecordActivity;
import com.eparking.activity.EParkingHomeActivity;
import com.eparking.activity.EmergencySluiceActivity;
import com.eparking.activity.EparkingBusinessSectionActivity;
import com.eparking.activity.EparkingLockStatusActivity;
import com.eparking.activity.FastmovingCarActivity;
import com.eparking.activity.FindParkingSpaceActivity;
import com.eparking.activity.MonthCardApplyActivity;
import com.feed.activity.CreateNormalFeedActivity;
import com.feed.activity.FeedOrActivityActivity;
import com.feed.activity.LinLiActivity;
import com.invite.activity.ContactsActivity;
import com.invite.activity.InviteActivity;
import com.mycarinfo.activity.MyCarInfoActivity;
import com.myproperty.activity.MyPropertyActivity;
import com.notification.activity.ENotificationActivity;
import com.notification.activity.NotificationActivity;
import com.scanCode.activity.CaptureActivity;
import com.setting.activity.SettingActivity;
import com.user.UserAppConst;
import com.user.activity.UserRegisterAndLoginActivity;

import cn.net.cyberway.R;
import cn.net.cyberway.activity.FindPropertyActivity;

/**
 * 所有link解析 解析文档 见 svn 彩之云linkprotocol
 * 此类的名称不能改双乾跳转彩之云有通过反射用到
 */

public class LinkParseUtil {
    public static void parse(Context context, String link, String title) {

        SharedPreferences mShared = context.getSharedPreferences(UserAppConst.USERINFO, 0);
        if (!mShared.getBoolean(UserAppConst.IS_LOGIN, false)) {
            Intent intent = new Intent(context, UserRegisterAndLoginActivity.class);
            context.startActivity(intent);
        } else {
            if (!TextUtils.isEmpty(link)) {
                if (link.trim().startsWith("http://") || link.trim().startsWith("https://")) {
//                    link="http://h5test.elab-plus.com/H5/index.html";

//                 link = "file:///android_asset/index.html";
                    Intent intent = new Intent(context, WebViewActivity.class);
                    intent.putExtra(WebViewActivity.WEBURL, link);
                    intent.putExtra(WebViewActivity.WEBTITLE, title);
                    intent.putExtra(WebViewActivity.THRIDSOURCE, false);
                    context.startActivity(intent);
                    if (context instanceof Activity) {
                        ((Activity) context).overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
                    }
                } else {
                    link = link.trim();
                    int pos = link.indexOf("type=") + "type=".length();
                    String value = "";
                    int len = link.length();
                    if (len > pos) {
                        value = link.substring(pos, len);
                    }
                    Intent intent = null;
                    switch (value) {
                        case "EntranceGuard":  //门禁colourlife://proto?type=notificationList
                        case "Guard":
                            intent = new Intent(context, NewDoorActivity.class);
                            ((Activity) context).startActivityForResult(intent, 2000);
                            ((Activity) context).overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
                            break;
                        case "AreaNotive": //旧的消息通知
                            intent = new Intent(context, ENotificationActivity.class);
                            context.startActivity(intent);
                            ((Activity) context).overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
                            break;
                        case "Card"://卡券
                            intent = new Intent(context, CardCouponsActivity.class);
                            context.startActivity(intent);
                            ((Activity) context).overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
                            break;
                        case "Ticket":
                        case "NewTicket"://彩钱包
                            Cqb_PayUtil.getInstance((Activity) context).createPay(Utils.getPublicParams(context), Constants.CAIWALLET_ENVIRONMENT);//彩钱包
                            break;
                        case "OpenColourlifeWallet": // 开通彩钱包
                            Cqb_PayUtil.getInstance((Activity) context).openActivityUI(Utils.getPublicParams(context), Constants.CAIWALLET_ENVIRONMENT);//开通彩钱包
                            break;
                        case "openZCB"://打开招财宝
                            Cqb_PayUtil.getInstance((Activity) context).openZCB(Utils.getPublicParams(context), Constants.CAIWALLET_ENVIRONMENT, "zhaocaibao");//进入招财宝
                            break;
                        case "Invite"://邀请好友
                            intent = new Intent(context, InviteActivity.class);
//                            intent = new Intent(context, InviteFriendActivity.class);//邀请好友 保留
                            context.startActivity(intent);
                            ((Activity) context).overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
                            break;
                        case "QRCode"://扫一扫
                            intent = new Intent(context, CaptureActivity.class);
                            intent.putExtra(CaptureActivity.QRCODE_SOURCE, "default");
                            context.startActivity(intent);
                            ((Activity) context).overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
                            break;
                        case "Neighborhood"://邻里首页  colourlife://proto?type=orderList
                            intent = new Intent(context, LinLiActivity.class);
                            context.startActivity(intent);
                            break;
                        case "Dynamic"://发动态
                            intent = new Intent(context, CreateNormalFeedActivity.class);
                            context.startActivity(intent);
                            break;
                        case "LaunchEvent"://发活动
                            intent = new Intent(context, FeedOrActivityActivity.class);
                            context.startActivity(intent);
                            break;
                        case "Information"://个人信息
                            intent = new Intent(context, CustomerInfoActivity.class);
                            context.startActivity(intent);
                            break;
                        case "FeedBack"://意见反馈
                            intent = new Intent(context, FeedBackActivity.class);
                            context.startActivity(intent);
                            break;
                        case "myCar"://我的车辆
                            intent = new Intent(context, MyCarInfoActivity.class);
                            context.startActivity(intent);
                            break;
                        case "myHouse"://我的房产
                            intent = new Intent(context, MyPropertyActivity.class);
                            context.startActivity(intent);
                            break;
                        case "Setting"://设置页面
                            intent = new Intent(context, SettingActivity.class);
                            if (title.equals("FromColourBean")) {
                                intent.putExtra(SettingActivity.FROM_COLOUR_BEAN, true);
                            }
                            context.startActivity(intent);
                            break;
                        case "FindProperty"://找物业页面
                            intent = new Intent(context, FindPropertyActivity.class);
                            context.startActivity(intent);
                            break;
                        case "orderList"://我的订单
                            intent = new Intent(context, OrderListActivity.class);
                            context.startActivity(intent);
                            break;
                        case "phoneAddressBook"://彩钱包sdk调用彩之云的通讯录
                            intent = new Intent(context, ContactsActivity.class);
                            ((Activity) context).startActivityForResult(intent, 1001);
                            break;
                        case "notificationList"://消息通知列表
                            intent = new Intent(context, NotificationActivity.class);
                            intent.putExtra(BuryingPointUtils.UPLOAD_DETAILS, title);
                            context.startActivity(intent);
                            break;
                        case "colourlifeDeliveryAddress":////收货地址
                            intent = new Intent(context, DeliveryAddressListActivity.class);
                            context.startActivity(intent);
                            ((Activity) context).overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
                            break;
                        case "emergency-sluice"://紧急开闸
                            intent = new Intent(context, EmergencySluiceActivity.class);
                            context.startActivity(intent);
                            break;
                        case "reserved-parking"://预约停车
                            intent = new Intent(context, AppointmentParkingActivity.class);
                            context.startActivity(intent);
                            break;
                        case "parking-business-block"://商家版块
                            intent = new Intent(context, EparkingBusinessSectionActivity.class);
                            context.startActivity(intent);
                            break;
                        case "apply-month-parking"://申请月卡
                            intent = new Intent(context, MonthCardApplyActivity.class);
                            context.startActivity(intent);
                            break;
                        case "no-feel-pay"://无感支付
                            break;
                        case "quick-move-car"://一键挪车
                            intent = new Intent(context, FastmovingCarActivity.class);
                            context.startActivity(intent);
                            break;
                        case "find-parking-lot"://找车位
                            intent = new Intent(context, FindParkingSpaceActivity.class);
                            context.startActivity(intent);
                            break;
                        case "parking-history"://历史记录
                            intent = new Intent(context, EParkingHistoryRecordActivity.class);
                            context.startActivity(intent);
                            break;
                        case "parking-lock-status"://当前车位状态
                            intent = new Intent(context, EparkingLockStatusActivity.class);
                            context.startActivity(intent);
                            break;
                        case "parking-home"://e停主页
                            intent = new Intent(context, EParkingHomeActivity.class);
                            context.startActivity(intent);
                            break;
                        case "parking-cardholder"://我的卡包
                            intent = new Intent(context, EParkingCardHolderActivity.class);
                            context.startActivity(intent);
                            break;
                        case "colourlifeAppMall"://全部应用
                            intent = new Intent(context, WholeApplicationActivity.class);
                            context.startActivity(intent);
                            break;
                        case "huxinCalling":
                            break;
                        case "Colorbean":
                            intent = new Intent(context, CustomerColourBeanActivity.class);
                            ((Activity) context).startActivityForResult(intent, 1);
                            break;
                    }
                }
            }
        }
    }

    /***双乾光彩支付用到**/
    public static void jumpHtmlPay(Context context, String link, String domainName) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(WebViewActivity.WEBURL, link);
        intent.putExtra(WebViewActivity.WEBDOMAIN, domainName);
        intent.putExtra(WebViewActivity.THRIDSOURCE, false);
        context.startActivity(intent);
        ((Activity) context).overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }

    /***双乾光彩支付用到**/
    public static void jumpFromThrid(Context context, String link, String title) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(WebViewActivity.WEBURL, link);
        intent.putExtra(WebViewActivity.WEBTITLE, title);
        intent.putExtra(WebViewActivity.THRIDSOURCE, true);
        context.startActivity(intent);
        ((Activity) context).overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }
}

