package cn.net.cyberway.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.activity.WebViewActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.external.eventbus.EventBus;
import com.nohttp.utils.GsonUtils;
import com.user.UserAppConst;
import com.user.UserMessageConstant;
import com.user.activity.UserRegisterAndLoginActivity;
import com.user.activity.UserThridRegisterActivity;
import com.user.entity.CheckRegisterEntity;
import com.user.model.NewUserModel;

import org.json.JSONException;
import org.json.JSONObject;

import cn.net.cyberway.R;
import cn.net.cyberway.utils.LinkParseUtil;

import static com.user.activity.UserThridRegisterActivity.GENDER;
import static com.user.activity.UserThridRegisterActivity.NICKNAME;
import static com.user.activity.UserThridRegisterActivity.OPENCODE;
import static com.user.activity.UserThridRegisterActivity.PORTRAIT;
import static com.user.activity.UserThridRegisterActivity.SOURCE;

/**
 * @name ${yuansk}
 * @class name：cn.net.cyberway.activity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/4/11 13:57
 * @change
 * @chang time
 * @class describe   从浏览器跳转到彩之云的过度页面
 */
@Route(path = UserAppConst.COLOURLIFE_JUMP_EXCESSIVE)
public class BrowerJumpExcessiveActivity extends BaseActivity implements NewHttpResponse {
    public static final String THRIDFROMSOURCE = "thridfromsource"; //网页的来源
    public static final String THRIDLINKURL = "thridlinkurl"; //网页的链接
    public static final String THRIDTITLE = "thridtitle";  //网页的标题
    public static final String THRIDDOMAIN = "thriddomain";  //彩白条支付
    public static final String THRIDSOURCE = "thridsource";  //彩白条来源

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Uri schemeUri = intent.getData();
        int source = intent.getIntExtra(THRIDFROMSOURCE, 0);
        boolean loginOut = intent.getBooleanExtra("login_out", false);
        if (loginOut) {
            Message message = new Message();
            message.what = UserMessageConstant.SQUEEZE_OUT;
            message.obj = getResources().getString(R.string.account_extrude_login);
            EventBus.getDefault().post(message);
        } else {
            if (source == 0) {
                if (null != schemeUri && "colourlifeauth".equals(schemeUri.getScheme()) && "web".equals(schemeUri.getHost())) {
                    final String linkUrl = schemeUri.getQueryParameter("linkURL");
                    if (!TextUtils.isEmpty(linkUrl)) {
                        Intent jumpIntent = new Intent(BrowerJumpExcessiveActivity.this, MainActivity.class);
                        jumpIntent.putExtra(MainActivity.JUMPOTHERURL, linkUrl);
                        startActivity(jumpIntent);
                    } else {
                        //通过设备id获取  钉钉的id
                        NewUserModel newUserModel = new NewUserModel(BrowerJumpExcessiveActivity.this);
                        newUserModel.getDingTalk(0, this);
                    }
                }
            } else {
                String linkUrl = intent.getStringExtra(THRIDLINKURL);
                String title = intent.getStringExtra(THRIDTITLE);
                String domain = intent.getStringExtra(THRIDDOMAIN);
                if (source == 100) {
                    Intent webIntent = new Intent(BrowerJumpExcessiveActivity.this, WebViewActivity.class);
                    webIntent.putExtra(WebViewActivity.WEBURL, linkUrl);
                    webIntent.putExtra(WebViewActivity.WEBTITLE, title);
                    webIntent.putExtra(WebViewActivity.THRIDSOURCE, false);
                    startActivity(webIntent);
                    overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
                } else {
                    boolean thridSource = intent.getBooleanExtra(THRIDSOURCE, false);
                    if (thridSource) {
                        LinkParseUtil.jumpFromThrid(BrowerJumpExcessiveActivity.this, linkUrl, title);
                    } else {
                        if (!TextUtils.isEmpty(domain)) {
                            LinkParseUtil.jumpHtmlPay(BrowerJumpExcessiveActivity.this, linkUrl, domain);
                        } else {
                            LinkParseUtil.parse(BrowerJumpExcessiveActivity.this, linkUrl, title);
                        }
                    }
                }
            }
        }
        finish();
    }

    private String userId;
    private String userName;

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:
                if (!TextUtils.isEmpty(result)) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        JSONObject contentJsonObject = jsonObject.optJSONObject("data");
                        userId = contentJsonObject.optString("userId");
                        userName = contentJsonObject.optString("userName");
                        NewUserModel newUserModel = new NewUserModel(this);
                        newUserModel.authRegister(1, "dingding", userId, this);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case 1:

                //是否登录

                //登录直接打开首页

                //未登录

                //未注册去第三方注册页面

                //去登录页面  同步保存获取access_token的类型为6
                int is_register = 0;
                String mobile = "";
                try {
                    if (!TextUtils.isEmpty(result)) {
                        CheckRegisterEntity checkRegisterEntity = GsonUtils.gsonToBean(result, CheckRegisterEntity.class);
                        CheckRegisterEntity.ContentBean contentBean = checkRegisterEntity.getContent();
                        is_register = contentBean.getIs_register();
                        mobile = contentBean.getMobile();
                    }
                    Intent intent = null;
                    if (is_register == 1) {
                        //判断用户是否登录未登录 去登录页面
                        //已登录去首页 更换为最新的手机号码去登录
                        boolean isLogin = shared.getBoolean(UserAppConst.IS_LOGIN, false);
                        editor.putString(UserAppConst.Colour_login_mobile, mobile).commit();
                        if (isLogin) {
                            intent = new Intent(BrowerJumpExcessiveActivity.this, MainActivity.class);
                        } else {
                            intent = new Intent(BrowerJumpExcessiveActivity.this, UserRegisterAndLoginActivity.class);
                        }
                    } else {
                        //去第三方注册页面
                        intent = new Intent(BrowerJumpExcessiveActivity.this, UserThridRegisterActivity.class);
                        intent.putExtra(OPENCODE, userId);
                        intent.putExtra(NICKNAME, userName);
                        intent.putExtra(SOURCE, "dingding");
                        intent.putExtra(GENDER, "");
                        intent.putExtra(PORTRAIT, "");
                    }
                    startActivity(intent);
                    finish();
                } catch (Exception e) {

                }
                break;
        }
    }
}
