package com.feed.view;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import cn.net.cyberway.R;

/**
 * feed的评论和赞的popupWindow
 */
public class FeedPopup extends PopupWindow implements PopupWindow.OnDismissListener {

    //	private TextView priase;
//	private TextView comment;
    private LinearLayout priase;
    private LinearLayout comment;
    private ImageView priaseImg;
    private ImageView commentImg;
    private TextView tv_praise_status;

    private Context mContext;

    // 列表弹窗的间隔
    protected final int LIST_PADDING = 10;

    // 实例化一个矩形
    private Rect mRect = new Rect();

    // 坐标的位置（x、y）
    private final int[] mLocation = new int[2];

    // 屏幕的宽度和高度
    private int mScreenWidth, mScreenHeight;

    // 位置不在中心
    private int popupGravity = Gravity.NO_GRAVITY;

    // 弹窗子类项选中时的监听
    private OnItemOnClickListener mItemOnClickListener;

    private int mPosition;

    private int mStatus;

    public FeedPopup(Context context) {
        // 设置布局的参数
        this(context, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    public FeedPopup(Context context, int width, int height) {
        this.mContext = context;

        // 设置可以获得焦点
        setFocusable(true);
        // 设置弹窗内可点击
        setTouchable(true);
        // 设置弹窗外可点击
        setOutsideTouchable(true);

        // 设置弹窗的宽度和高度
        setWidth(width);
        setHeight(height);

        setBackgroundDrawable(new BitmapDrawable());

        // 设置弹窗的布局界面
        View view = LayoutInflater.from(mContext).inflate(R.layout.comment_popu, null);
        setContentView(view);
        priase = (LinearLayout) view.findViewById(R.id.popu_praise);
        comment = (LinearLayout) view.findViewById(R.id.popu_comment);
        priaseImg = (ImageView) view.findViewById(R.id.popu_praise_img);
        tv_praise_status = view.findViewById(R.id.tv_praise_status);
        commentImg = (ImageView) view.findViewById(R.id.popu_comment_img);
        priase.setOnClickListener(onclick);
        comment.setOnClickListener(onclick);

        setOnDismissListener(this);

    }

    /**
     * 显示弹窗列表界面
     */
    public void show(final View c, int position, int status) {
        mPosition = position;
        mStatus = status;
        if (mStatus == 1) {
            tv_praise_status.setText("取消");
        } else {
            tv_praise_status.setText("赞");
        }
        // 获得点击屏幕的位置坐标
        c.getLocationOnScreen(mLocation);
        // 设置矩形的大小
        mRect.set(mLocation[0], mLocation[1], mLocation[0] + c.getWidth(), mLocation[1] + c.getHeight());
        // 显示弹窗的位置
        showAtLocation(c, Gravity.NO_GRAVITY, mLocation[0] - this.getWidth() - 10, mLocation[1] - ((this.getHeight() - c.getHeight()) / 2));
    }

    OnClickListener onclick = new OnClickListener() {
        @Override
        public void onClick(View v) {
            dismiss();
            Handler mHandler;
            switch (v.getId()) {
                case R.id.popu_comment:
                    mHandler = new Handler() {
                        @Override
                        public void handleMessage(Message msg) {
                            super.handleMessage(msg);
                            mItemOnClickListener.onItemClick(1, mPosition, mStatus);
                        }
                    };
                    mHandler.sendEmptyMessageDelayed(0, 200);

                    break;
                case R.id.popu_praise://
                    mHandler = new Handler() {
                        @Override
                        public void handleMessage(Message msg) {
                            super.handleMessage(msg);
                            mItemOnClickListener.onItemClick(0, mPosition, mStatus);
                        }
                    };
                    mHandler.sendEmptyMessageDelayed(0, 200);

                    break;
            }
        }
    };

    /**
     * 设置监听事件
     */
    public void setItemOnClickListener(OnItemOnClickListener onItemOnClickListener) {
        this.mItemOnClickListener = onItemOnClickListener;
    }

    @Override
    public void onDismiss() {
        mItemOnClickListener.onDismiss();
    }

    /**
     * 功能描述：弹窗子类项按钮监听事件
     */
    public static interface OnItemOnClickListener {
        public void onItemClick(int index, int position, int status);

        public void onDismiss();
    }
}
