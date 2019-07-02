package cn.net.cyberway.utils;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.model.NewHttpResponse;
import com.BeeFramework.view.Util;
import com.chuanglan.shanyan_sdk.OneKeyLoginManager;
import com.chuanglan.shanyan_sdk.listener.OneKeyLoginListener;
import com.chuanglan.shanyan_sdk.listener.OpenLoginAuthListener;
import com.chuanglan.shanyan_sdk.tool.ShanYanUIConfig;
import com.external.eventbus.EventBus;
import com.jpush.Constant;
import com.user.UserAppConst;
import com.user.UserMessageConstant;
import com.user.model.NewUserModel;
import com.user.model.TokenModel;

import cn.net.cyberway.R;

import static cn.net.cyberway.utils.IMFriendDataUtils.userInitImData;


public class ConfigUtils implements NewHttpResponse {
    private Activity mActivity;
    private NewUserModel newUserModel;
    private SharedPreferences shared;
    private SharedPreferences.Editor editor;

    public ConfigUtils(Activity mActivity) {
        this.mActivity = mActivity;
        newUserModel = new NewUserModel(mActivity);
        shared = mActivity.getSharedPreferences(UserAppConst.USERINFO, 0);
        editor = shared.edit();
    }

    public static ShanYanUIConfig getCJSConfig(final Activity context) {
        //标题栏下划线
     /*   View view = new View(context);
        view.setBackgroundColor(0xffe8e8e8);
        RelativeLayout.LayoutParams mLayoutParams3 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, AbScreenUtils.dp2px(context, 1));
        mLayoutParams3.setMargins(0, AbScreenUtils.dp2px(context, 0), 0, 0);
        view.setLayoutParams(mLayoutParams3);*/

        TextView rightText = new TextView(context);
        rightText.setText("切换账号");
        rightText.setTextColor(0xff329dfa);
        rightText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
        RelativeLayout.LayoutParams rightTextParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        rightTextParams.setMargins(0, Util.DensityUtil.dip2px(context, 15), Util.DensityUtil.dip2px(context, 15), 0);
        rightTextParams.width = Util.DensityUtil.dip2px(context, 55);
        rightTextParams.height = Util.DensityUtil.dip2px(context, 15);
        rightTextParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        rightText.setLayoutParams(rightTextParams);


        LayoutInflater inflater = LayoutInflater.from(context);
        RelativeLayout relativeLayout = (RelativeLayout) inflater.inflate(R.layout.relative_item_view, null);
        RelativeLayout.LayoutParams layoutParamsOther = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParamsOther.setMargins(0, Util.DensityUtil.dip2px(context, 380), 0, 0);
        layoutParamsOther.addRule(RelativeLayout.CENTER_HORIZONTAL);
        relativeLayout.setLayoutParams(layoutParamsOther);
        LinearLayout wechat_layout = relativeLayout.findViewById(R.id.wechat_layout);
        LinearLayout qq_layout = relativeLayout.findViewById(R.id.qq_layout);
        wechat_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        qq_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        /****************************************************设置授权页*********************************************************/
        ShanYanUIConfig uiConfig = new ShanYanUIConfig.Builder()
                //授权页导航栏：
                .setNavColor(0xffffffff)  //设置导航栏颜色
                .setNavText("免密登录")  //设置导航栏标题文字
                .setNavTextColor(0xff252A2E) //设置标题栏文字颜色
                .setNavReturnImgPath("new_close")  //设置导航栏返回按钮图标
                .setAuthBGImgPath("sysdk_login_bg")
                .setAuthNavTransparent(true)
                .setAuthNavHidden(false)

                //授权页logo（logo的层级在次底层，仅次于自定义控件）
                .setLogoImgPath("new_czy_logo")  //设置logo图片
                .setLogoWidth(70)   //设置logo宽度
                .setLogoHeight(70)   //设置logo高度
                .setLogoHidden(false)   //是否隐藏logo

                //授权页号码栏：
                .setNumberColor(0xff252A2E)  //设置手机号码字体颜色
                .setNumFieldOffsetY(200)    //设置号码栏相对于标题栏下边缘y偏移
                .setNumFieldWidth(110)
                .setNumberSize(18)


                //授权页登录按钮：
                .setLogBtnText("本机号码一键登录")  //设置登录按钮文字
                .setLogBtnTextColor(0xffffffff)   //设置登录按钮文字颜色
                .setLogBtnImgPath("onekey_login_bg")   //设置登录按钮图片
                .setLogBtnOffsetY(260)   //设置登录按钮相对于标题栏下边缘y偏移
                .setLogBtnTextSize(15)
                .setLogBtnHeight(45)
                .setLogBtnWidth(Util.DensityUtil.getScreenWidth(context, true) - 40)
                //授权页隐私栏：


                .setAppPrivacyOne("用户自定义协议条款", "https://m.colourlife.com/xieyiApp/protocol")  //设置开发者隐私条款1名称和URL(名称，url)
                .setAppPrivacyTwo("用户服务条款", "https://m.colourlife.com/xieyiApp")  //设置开发者隐私条款2名称和URL(名称，url)
                .setAppPrivacyColor(0xff25282E, 0xff0085d0)   //	设置隐私条款名称颜色(基础文字颜色，协议文字颜色)
                .setPrivacyOffsetBottomY(30)//设置隐私条款相对于屏幕下边缘y偏
                .setCheckBoxHidden(true)
                //授权页slogan：
                .setSloganTextColor(0xff329DFA)  //设置slogan文字颜色
                .setSloganOffsetY(160)  //设置slogan相对于标题栏下边缘y偏移
                .setSloganHidden(true)
//                .addCustomView(rightText, false, true, new ShanYanCustomInterface() {
//                    @Override
//                    public void onClick(Context context, View view) {
//                        Intent intent = new Intent(context, UserIdentityLoginActivity.class);
//                        context.startActivity(intent);
//
//                    }
//                })
//                .addCustomView(relativeLayout, false, false, null)
                //标题栏下划线，可以不写
                .build();
        return uiConfig;

    }

    public void jumpOneKeyLogin() {
        OneKeyLoginManager.getInstance().openLoginAuth(false, 10, new OpenLoginAuthListener() {
            @Override
            public void getOpenLoginAuthStatus(int code, String result) {
                //拉起授权页监听
                if (code != 1000) {
                    OneKeyLoginManager.getInstance().finishAuthActivity();
                }
            }
        }, new OneKeyLoginListener() {
            @Override
            public void getOneKeyLoginStatus(int code, String result) {
                //一键登录监听
                if (code != 1000) {
                    ToastUtil.toastTime(mActivity, result + "(" + code + ")", 3000);
                    OneKeyLoginManager.getInstance().finishAuthActivity();
                } else {
                    oneKeyLogin(result);
                }
            }
        });
    }

    private void oneKeyLogin(String result) {
        newUserModel.oneKeyLoginByBlue(0, result, ConfigUtils.this);
    }

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:
                if (!TextUtils.isEmpty(result)) {
                    newUserModel.getUserInformation(1, false, this);
                }
                break;
            case 1:
                if (!TextUtils.isEmpty(result)) {
                    userInitImData(mActivity, shared);
                    TokenModel tokenModel = new TokenModel(mActivity);
                    tokenModel.getToken(2, 2, false, this);
                }
                break;
            case 2:
                ToastUtil.toastShow(mActivity, mActivity.getResources().getString(R.string.user_login_success));
                editor.putBoolean(UserAppConst.Colour_user_login, true);
                editor.commit();
                Message msg = new Message();
                msg.what = UserMessageConstant.SIGN_IN_SUCCESS;//登录成功之后，刷新各种数据
                EventBus.getDefault().post(msg);
                sendNotification();
                mActivity.finish();
                OneKeyLoginManager.getInstance().finishAuthActivity();
                break;
        }
    }

    private void sendNotification() {
        LocalBroadcastManager mLocalBroadcastManager = LocalBroadcastManager
                .getInstance(mActivity);
        Intent data = new Intent();
        data.setAction(Constant.ACTION_LOGIN_FINISH_COMPLETED);
        mLocalBroadcastManager.sendBroadcast(data);
    }
}
