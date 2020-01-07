package cn.net.cyberway.home.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.BeeFramework.activity.WebViewActivity;
import com.tmall.ultraviewpager.UltraViewPager;

import cn.net.cyberway.R;
import cn.net.cyberway.home.adapter.NoLoginPagerAdapter;
import cn.net.cyberway.utils.LinkParseUtil;


/**
 * 2017/10/18
 * 新版彩之云5.0新首页
 */
public class NologinHomeFragment extends Fragment implements View.OnClickListener {

    private View mView;
    private UltraViewPager no_login_viewpager;
    private Button btn_register_login;
    private TextView tv_user_agreement;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home_nologin, container, false);
        no_login_viewpager = mView.findViewById(R.id.no_login_viewpager);
        btn_register_login = mView.findViewById(R.id.btn_register_login);
        tv_user_agreement = mView.findViewById(R.id.tv_user_agreement);
        btn_register_login.setOnClickListener(this::onClick);
        setNoticeSpannString();
        no_login_viewpager.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL);
        NoLoginPagerAdapter homeDoorAdapter = new NoLoginPagerAdapter();
        no_login_viewpager.setAdapter(homeDoorAdapter);
        no_login_viewpager.initIndicator();
        no_login_viewpager.getIndicator().setFocusResId(R.drawable.no_login_banner_select);
        no_login_viewpager.getIndicator().setNormalResId( R.drawable.no_login_banner_default);
        no_login_viewpager.getIndicator().setOrientation(UltraViewPager.Orientation.HORIZONTAL);
        no_login_viewpager.getIndicator().setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
        no_login_viewpager.getIndicator().build();
        no_login_viewpager.setInfiniteLoop(true);
        no_login_viewpager.setAutoScroll(2500);
        return mView;
    }

    private void setNoticeSpannString() {
        StringBuffer stringBuffer = new StringBuffer();
        String door_notice_one = "继续即表示你同意";
        String door_notice_two = "<<隐私条款>>";
        String door_notice_three = "和";
        String door_notice_four = "<<用户协议>>";
        stringBuffer.append(door_notice_one);
        stringBuffer.append(door_notice_two);
        stringBuffer.append(door_notice_three);
        stringBuffer.append(door_notice_four);
        SpannableString spannableString = new SpannableString(stringBuffer.toString());
        int middleLength = door_notice_one.length() + door_notice_two.length();
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#25282E")), 0, door_notice_one.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), WebViewActivity.class);
                String url = "https://m.colourlife.com/xieyiApp/protocol";
                intent.putExtra(WebViewActivity.WEBTITLE, "隐私条款");
                intent.putExtra(WebViewActivity.WEBURL, url);
                startActivity(intent);
            }

            public void updateDrawState(@NonNull TextPaint ds) {
                ds.setColor(Color.parseColor("#0567FA"));
                ds.setUnderlineText(false);
            }
        }, door_notice_one.length(), middleLength, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#25282E")), middleLength, middleLength + 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), WebViewActivity.class);
                String url = "https://m.colourlife.com/xieyiApp";
                intent.putExtra(WebViewActivity.WEBTITLE, "用户协议");
                intent.putExtra(WebViewActivity.WEBURL, url);
                startActivity(intent);
            }

            public void updateDrawState(@NonNull TextPaint ds) {
                ds.setColor(Color.parseColor("#0567FA"));
                ds.setUnderlineText(false);
            }
        }, middleLength + 1, stringBuffer.toString().length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        tv_user_agreement.setMovementMethod(LinkMovementMethod.getInstance());
        tv_user_agreement.setText(spannableString);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register_login:
                LinkParseUtil.parse(getActivity(), "", "");
                break;
        }

    }

}
