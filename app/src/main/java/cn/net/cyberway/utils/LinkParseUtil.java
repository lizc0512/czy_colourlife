package cn.net.cyberway.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.BeeFramework.Utils.Utils;
import com.BeeFramework.activity.WebViewActivity;
import com.BeeFramework.model.Constants;
import com.about.activity.AboutActivity;
import com.about.activity.FeedBackActivity;
import com.allapp.activity.WholeApplicationActivity;
import com.cashier.activity.OrderListActivity;
import com.customerInfo.activity.CustomerColourBeanActivity;
import com.customerInfo.activity.CustomerInfoActivity;
import com.customerInfo.activity.DeliveryAddressListActivity;
import com.dashuview.library.keep.Cqb_PayUtil;
import com.door.activity.IntelligenceDoorActivity;
import com.door.activity.NewDoorAuthorizeActivity;
import com.door.activity.NewDoorRenewalActivity;
import com.invite.activity.ContactsActivity;
import com.invite.activity.InviteActivity;
import com.mycarinfo.activity.MyCarInfoActivity;
import com.myproperty.activity.MyPropertyActivity;
import com.notification.activity.NotificationActivity;
import com.point.activity.MyPointActivity;
import com.scanCode.activity.CaptureActivity;
import com.setting.activity.SettingActivity;
import com.sobot.chat.SobotApi;
import com.sobot.chat.api.model.ConsultingContent;
import com.sobot.chat.api.model.Information;
import com.user.UserAppConst;
import com.user.activity.UserRegisterAndLoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;

import cn.net.cyberway.R;
import cn.net.cyberway.activity.MainActivity;

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
            if (context instanceof Activity) {
                ((Activity) context).overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
            }
        } else {
            if (!TextUtils.isEmpty(link)) {
                if (link.trim().startsWith("http://") || link.trim().startsWith("https://")) {
//                  link="http://h5test.elab-plus.com/H5/index.html";
//                     link = "file:///android_asset/demo.html";
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
                        case "testWeb":
                            intent = new Intent(context, WebViewActivity.class);
                            link = "file:///android_asset/demo.html";
                            intent.putExtra(WebViewActivity.WEBURL, link);
                            intent.putExtra(WebViewActivity.WEBTITLE, title);
                            intent.putExtra(WebViewActivity.THRIDSOURCE, false);
                            context.startActivity(intent);
                            if (context instanceof Activity) {
                                ((Activity) context).overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
                            }
                            break;
                        case "EntranceGuard":  //门禁colourlife://proto?type=EntranceGuard
                        case "BloothKeyList"://蓝牙钥匙  colourlife://proto?type=BloothKeyList
                        case "Guard":
                            intent = new Intent(context, IntelligenceDoorActivity.class);
                            ((Activity) context).startActivityForResult(intent, 2000);
                            ((Activity) context).overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
                            break;
                        case "AuthorizedGuard":  //用户申请后接到消息通知 然后进行授权colourlife://proto?type=AuthorizedGuard
                            intent = new Intent(context, NewDoorAuthorizeActivity.class);
                            context.startActivity(intent);
                            ((Activity) context).overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
                            break;
                        case "Ticket":
                        case "NewTicket"://新彩钱包
                            intent = new Intent(context, MyPointActivity.class);
                            context.startActivity(intent);
                            break;
                        case "OldColourlifeWallet"://我的积分
                            Cqb_PayUtil.getInstance((Activity) context).createPay(Utils.getPublicParams(context), Constants.CAIWALLET_ENVIRONMENT);
                            break;
                        case "OpenColourlifeWallet": // 开通彩钱包
                            Cqb_PayUtil.getInstance((Activity) context).openActivityUI(Utils.getPublicParams(context), Constants.CAIWALLET_ENVIRONMENT);//开通彩钱包
                            break;
                        case "openZCB"://打开招财宝
                            Cqb_PayUtil.getInstance((Activity) context).openZCB(Utils.getPublicParams(context), Constants.CAIWALLET_ENVIRONMENT, "zhaocaibao");//进入招财宝
                            break;
                        case "Invite"://邀请好友
                            intent = new Intent(context, InviteActivity.class);
                            context.startActivity(intent);
                            ((Activity) context).overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
                            break;
                        case "QRCode"://扫一扫
                            intent = new Intent(context, CaptureActivity.class);
                            intent.putExtra(CaptureActivity.QRCODE_SOURCE, "default");
                            context.startActivity(intent);
                            ((Activity) context).overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
                            break;
                        case "Information"://个人信息
                            intent = new Intent(context, CustomerInfoActivity.class);
                            boolean fromWeb = context instanceof WebViewActivity;
                            intent.putExtra(CustomerInfoActivity.FROM_WEB, fromWeb);
                            if (fromWeb) {
                                ((Activity) context).startActivityForResult(intent, WebViewActivity.REFRESH);
                            } else {
                                context.startActivity(intent);
                            }
                            break;
                        case "FeedBack"://意见反馈
                            intent = new Intent(context, FeedBackActivity.class);
                            boolean isWeb = context instanceof WebViewActivity;
                            intent.putExtra(FeedBackActivity.FROM_WEB, isWeb);
                            if (isWeb) {
                                ((Activity) context).startActivityForResult(intent, WebViewActivity.REFRESH);
                            } else {
                                context.startActivity(intent);
                            }
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
                        case "colourlifeAppMall"://全部应用
                            intent = new Intent(context, WholeApplicationActivity.class);
                            context.startActivity(intent);
                            break;
                        case "updateVersion":
                            intent = new Intent(context, AboutActivity.class);
                            context.startActivity(intent);
                            break;
                        case "smartService":
                            Information info = new Information();
                            info.setAppkey(Constants.SMART_SERVICE_KEY);  //分配给App的的密钥
                            info.setUid(mShared.getString(UserAppConst.Colour_User_uuid, ""));
                            info.setUname(mShared.getString(UserAppConst.Colour_NAME, ""));
                            info.setRealname(mShared.getString(UserAppConst.Colour_Real_name, ""));
                            info.setTel(mShared.getString(UserAppConst.Colour_login_mobile, ""));
                            info.setFace(mShared.getString(UserAppConst.Colour_head_img, ""));
                            info.setEmail(mShared.getString(UserAppConst.COLOUR_EMAIL, ""));
                            info.setArtificialIntelligence(false);////默认false：显示转人工按钮。true：智能转人工
                            info.setArtificialIntelligenceNum(2);//为true时生效
                            info.setInitModeType(-1);
                            HashSet<String> tmpSet = new HashSet<>();
                            tmpSet.add("转人工");
                            tmpSet.add("人工");
                            info.setTransferKeyWord(tmpSet);
                            SobotApi.startSobotChat(context, info);
                            break;
                        case "Colorbean":
                            intent = new Intent(context, CustomerColourBeanActivity.class);
                            ((Activity) context).startActivityForResult(intent, 1);
                            break;
                        case "colourlifeCaiHui"://彩惠  colourlife://proto?type=colourlifeCaiHui
                            intent = new Intent(context, MainActivity.class);
                            intent.putExtra(MainActivity.JUMPOTHERURL, "colourlifeCaiHui");
                            context.startActivity(intent);
                            break;
                        case "ColourlifeBackHome":
                            intent = new Intent(context, MainActivity.class);
                            intent.putExtra(MainActivity.JUMPOTHERURL, "ColourlifeBackHome");
                            context.startActivity(intent);
                            break;
                        case "apply":
                            intent = new Intent(context, NewDoorRenewalActivity.class);
                            context.startActivity(intent);
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

    public static void sendGoodsInfor(Context context, String goodsInfor) {
        SharedPreferences mShared = context.getSharedPreferences(UserAppConst.USERINFO, 0);
        Information info = new Information();
        info.setAppkey(Constants.SMART_SERVICE_KEY);  //分配给App的的密钥
        info.setUid(mShared.getString(UserAppConst.Colour_User_uuid, ""));
        info.setUname(mShared.getString(UserAppConst.Colour_NAME, ""));
        info.setRealname(mShared.getString(UserAppConst.Colour_Real_name, ""));
        info.setTel(mShared.getString(UserAppConst.Colour_login_mobile, ""));
        info.setFace(mShared.getString(UserAppConst.Colour_head_img, ""));
        info.setArtificialIntelligence(false);////默认false：显示转人工按钮。true：智能转人工
        info.setArtificialIntelligenceNum(2);//为true时生效
        //客服模式控制 -1不控制 按照服务器后台设置的模式运行
        //1仅机器人 2仅人工 3机器人优先 4人工优先
        info.setInitModeType(-1);
        HashSet<String> tmpSet = new HashSet<>();
        tmpSet.add("转人工");
        tmpSet.add("人工");
        info.setTransferKeyWord(tmpSet);
        if (TextUtils.isEmpty(goodsInfor)) {
            SobotApi.startSobotChat(context, info);
        } else {
            JSONObject goodsJson = null;
            try {
                goodsJson = new JSONObject(goodsInfor);
                ConsultingContent consultingContent = new ConsultingContent();
                //咨询内容标题，必填
                //CardMessageHolder
                consultingContent.setSobotGoodsTitle(goodsJson.optString("goodsTitle"));
                //咨询内容图片，选填 但必须是图片地址
                //咨询来源页，必填
                consultingContent.setSobotGoodsFromUrl(goodsJson.optString("goodsFromUrl"));
                if (!goodsJson.isNull("goodsImgUrl")) {
                    consultingContent.setSobotGoodsImgUrl(goodsJson.optString("goodsImgUrl"));
                }
                //描述，选填
                if (!goodsJson.isNull("goodsDescribe")) {
                    consultingContent.setSobotGoodsDescribe(goodsJson.optString("goodsDescribe"));
                }
                //标签，选填
                if (!goodsJson.isNull("goodsPrice")) {
                    consultingContent.setSobotGoodsLable(goodsJson.optString("goodsPrice"));
                }
                info.setConsultingContent(consultingContent);
                consultingContent.setAutoSend(true);
                //发送商品卡片消息接口
                SobotApi.startSobotChat(context, info);
                SobotApi.sendCardMsg(context, consultingContent);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

