package com.myproperty.activity;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.activity.BaseActivity;

import cn.net.cyberway.R;

/**
 * 我的房产列表
 * Created by hxg on 2017/4/29.
 */
public class PropertyQuestionActivity extends BaseActivity implements View.OnClickListener {

    private ImageView user_top_view_back;
    private TextView user_top_view_title;
    private TextView tv_1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_question);
        getWindow().setBackgroundDrawable(null);//减少GPU绘制 布局要设为match_parent
        user_top_view_back = findViewById(R.id.user_top_view_back);
        user_top_view_back.setOnClickListener(this);

        user_top_view_title = findViewById(R.id.user_top_view_title);
        user_top_view_title.setText(getResources().getString(R.string.property_property));

        tv_1 = findViewById(R.id.tv_1);
        tv_1.append(getResources().getString(R.string.property_question1_1));

        SpannableString spannableString = setImage(this, R.drawable.ic_auth);
        tv_1.append(spannableString);

        tv_1.append(getResources().getString(R.string.property_question1_2));
    }

    //图片夹在文字里面
    private SpannableString setImage(Context c, int dra) {
        Drawable drawable = c.getResources().getDrawable(dra);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        SpannableString spannableString = new SpannableString("pics");
        ImageSpan imageSpan = new ImageSpan(drawable, ImageSpan.ALIGN_BASELINE);
        spannableString.setSpan(imageSpan, 0, spannableString.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
        }
    }

}