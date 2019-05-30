package cn.net.cyberway.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.feed.activity.CreateNormalFeedActivity;

import java.util.ArrayList;
import java.util.List;

import cn.csh.colourful.life.utils.GlideImageLoader;
import cn.csh.colourful.life.view.imagepicker.ImagePicker;
import cn.csh.colourful.life.view.imagepicker.ui.ImageGridActivity;
import cn.net.cyberway.R;
import cn.net.cyberway.protocol.ShareEntity;
import cn.net.cyberway.view.adapter.BaseRecyclerAdapter;
import cn.net.cyberway.view.adapter.BaseRecyclerHolder;

/**
 * 创建时间 : 2017/8/14.
 * 编写人 :  ${yuansk}
 * 功能描述: 底部分享的对话框
 * 版本:
 */

public class BottomShareDialogFragment extends PopupWindow {

    public BaseRecyclerAdapter<ShareEntity> adapter;
    private String[] textArr = {"从相册中选取二维码"};
    private int[] imgArr = {R.drawable.icon_wechat_share};
    private List<ShareEntity> shareEntityList = new ArrayList<ShareEntity>();

    private Context mContext;

    public BottomShareDialogFragment(Context context) {
        super();
        this.mContext = context;
        initView();
        initImagePicker();
    }

    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(false);  //显示拍照按钮
        imagePicker.setCrop(false);
        imagePicker.setMultiMode(false);
        imagePicker.setSelectLimit(1);    //选中数量限制
        imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);//保存文件的高度。单位像素
    }

    private void initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.bottom_share_layout, null);
        setContentView(view);
        setFocusable(true);
        setOutsideTouchable(true);
        setBackgroundDrawable(new BitmapDrawable());
        for (int i = 0; i < textArr.length; i++) {
            ShareEntity shareEntity = new ShareEntity();
            shareEntity.setShareText(textArr[i]);
            shareEntity.setShareDrawableId(imgArr[i]);
            shareEntityList.add(shareEntity);
        }
        adapter = new BaseRecyclerAdapter<ShareEntity>(mContext, shareEntityList, R.layout.share_item_layout) {
            @Override
            public void convert(BaseRecyclerHolder holder, ShareEntity item, int position, boolean isScrolling) {
                holder.setText(R.id.share_item_text, item.getShareText());
                holder.setImageResource(R.id.share_item_image, item.getShareDrawableId());
            }
        };
        RecyclerView shareRV = (RecyclerView) view.findViewById(R.id.share_recycleView);
        shareRV.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        shareRV.setAdapter(adapter);
        TextView tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        slideToUp(view);
    }

    public static void slideToUp(View view) {
        Animation slide = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        slide.setDuration(400);
        slide.setFillAfter(true);
        slide.setFillEnabled(true);
        view.startAnimation(slide);

        slide.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
    public  void  dismissDialog(){
        dismiss();
    }

}
