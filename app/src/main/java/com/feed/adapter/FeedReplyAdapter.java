package com.feed.adapter;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.external.eventbus.EventBus;
import com.feed.FeedConstant;
import com.feed.protocol.REPLY;
import com.user.UserAppConst;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.net.cyberway.R;

/**
 * feed回复的adapter
 */
public class FeedReplyAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    public List<REPLY> mList;
    private String mFeedId;
    private Matcher matcher1;
    private Pattern pattern1;

    //	private REPLY 				reply;
    public FeedReplyAdapter(Context context, List<REPLY> list, String feedId) {
        this.mContext = context;
        this.mList = list;
        mFeedId = feedId;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.feed_reply_item, null);
            holder.mLayout = (LinearLayout) convertView.findViewById(R.id.feed_reply_content_layout);
            holder.mContent = (TextView) convertView.findViewById(R.id.feed_reply_content);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final REPLY reply = mList.get(position);

        String content;
        if (reply.to_user.id != 0) {
            String to_nickname = reply.to_user.nickname;
            if (to_nickname.equals("")) {
                to_nickname = "彩多多";
            }
            content = reply.from_user.nickname + " 回复 " + to_nickname + ": " + reply.content;
        } else {
            content = reply.from_user.nickname + ": " + reply.content;
        }
        SpannableStringBuilder ssb = new SpannableStringBuilder(content);
        String fromUser = "";
        if (reply.from_user.nickname != null) {
            fromUser = reply.from_user.nickname;
        } else {
            fromUser = "";
        }
        try {
            pattern1 = Pattern.compile(fromUser);
        } catch (Exception e) {

        }
        if (null != pattern1) {
            matcher1 = pattern1.matcher(content);
            while (matcher1.find()) {
                ClickableSpan what1 = new ClickableSpan() {

                    @Override
                    public void onClick(View widget) {
                        Message msg = Message.obtain();
                        msg.what = FeedConstant.FEED_USER_INFOR;
                        msg.obj=reply.from_user.id;
                        EventBus.getDefault().post(msg);
                    }

                    @Override
                    public void updateDrawState(TextPaint ds) {
                        ds.setColor(mContext.getResources().getColor(R.color.blue_text_color_feed));
                        ds.setFakeBoldText(true);
                        ds.setUnderlineText(false); //去掉下划线
                    }
                };
                ssb.setSpan(what1, matcher1.start(), matcher1.end(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                break;
            }
        }

        ClickableSpan what3 = new ClickableSpan() {

            @Override
            public void onClick(View widget) {
                if (reply.from_user.nickname != null) {
                    if (reply.from_user.id == UserAppConst.getUser(mContext).id) {
                        doDelete(reply);
                    } else {
                        Message msg = new Message();
                        msg.what = FeedConstant.COMMENT_REPLY_USER;
                        Bundle b = new Bundle();
                        b.putInt("userId", reply.from_user.id);
                        b.putString("userName", reply.from_user.nickname);
                        b.putString("mFeedId", mFeedId);
                        b.putSerializable("user", reply.from_user);
                        msg.setData(b);
                        EventBus.getDefault().post(msg);
                    }
                }
            }

            @Override
            public void updateDrawState(TextPaint ds) {
//				ds.setColor(Color.parseColor("#38343a"));
                ds.setUnderlineText(false); //去掉下划线
            }
        };

        if (reply.to_user.id != 0 && reply.to_user.nickname != null) {
            String to_nickname = reply.to_user.nickname;
            if (to_nickname.equals("")) {
                to_nickname = "彩多多";
            }
            try {
                Pattern pattern2 = Pattern.compile(to_nickname);
                Matcher matcher2 = pattern2.matcher(content);
                while (matcher2.find()) {
                    ClickableSpan what2 = new ClickableSpan() {

                        @Override
                        public void onClick(View widget) {
                            Message msg = Message.obtain();
                            msg.what = FeedConstant.FEED_USER_INFOR;
                            msg.obj=reply.to_user.id;
                            EventBus.getDefault().post(msg);
                        }

                        @Override
                        public void updateDrawState(TextPaint ds) {
//						ds.setColor(Color.parseColor("#000000"));
                            ds.setColor(mContext.getResources().getColor(R.color.blue_text_color_feed));
                            ds.setFakeBoldText(true);
                            ds.setUnderlineText(false); //去掉下划线
                        }
                    };
                    ssb.setSpan(what2, matcher1.end() + 4, matcher1.end() + 4 + to_nickname.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                    break;
                }
                if (matcher1.matches()) {
                    if (!TextUtils.isEmpty(content) && !TextUtils.isEmpty(to_nickname)) {
                        if (content.length() >= matcher1.end() + 4 + to_nickname.length()) {
                            ssb.setSpan(what3, matcher1.end() + 4 + to_nickname.length(), content.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                        }
                    }
                } else {
                    if (!TextUtils.isEmpty(content) && !TextUtils.isEmpty(to_nickname)) {
                        if (content.length() >= 4 + to_nickname.length()) {
                            ssb.setSpan(what3, 4 + to_nickname.length(), content.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                        }
                    }

                }
            } catch (IllegalStateException state) {


            } catch (Exception e) {
                //正则匹配错误
            }
        }
        holder.mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (reply.from_user.nickname != null) {
                    if (reply.from_user.id == UserAppConst.getUser(mContext).id) {
                        doDelete(reply);
                    } else {
                        Message msg = new Message();
                        msg.what = FeedConstant.COMMENT_REPLY_USER;
                        Bundle b = new Bundle();
                        b.putInt("userId", reply.from_user.id);
                        b.putString("userName", reply.from_user.nickname);
                        b.putString("mFeedId", mFeedId);
                        b.putSerializable("user", reply.from_user);
                        msg.setData(b);
                        EventBus.getDefault().post(msg);
                    }
                }
            }
        });

        holder.mContent.setText(ssb);
        holder.mContent.setMovementMethod(LinkMovementMethod.getInstance());
        return convertView;

    }

    /**
     * 删除
     *
     * @param reply
     */
    private void doDelete(final REPLY reply) {
        final Dialog dialog = new Dialog(mContext, R.style.dialog);
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.delete_dialog, null);
        dialog.setContentView(contentView);

        android.view.ViewGroup.LayoutParams cursorParams = contentView.getLayoutParams();
        cursorParams.width = mContext.getResources().getDisplayMetrics().widthPixels;
        contentView.setLayoutParams(cursorParams);
        dialog.getWindow().setWindowAnimations(R.style.dialogWindowAnim); // 设置窗口弹出动画
        dialog.show();

        TextView delete = (TextView) dialog.findViewById(R.id.dialog_delete);
        TextView cancel = (TextView) dialog.findViewById(R.id.dialog_cancel);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Message msg = new Message();
                msg.arg1 = Integer.parseInt(reply.id);
                msg.obj = mFeedId;
                if (FeedConstant.FEE_REPLYD_DELETED_FORM == 0) {
                    msg.what = FeedConstant.FEED_DELETE_COMMENT;//邻里主要
                } else if (FeedConstant.FEE_REPLYD_DELETED_FORM == 1) {
                    msg.what = FeedConstant.ACTIVE_FEED_DELETE_COMMENTD;// 邻里活动列表
                }
                EventBus.getDefault().post(msg);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    class ViewHolder {
        private LinearLayout mLayout;
        private TextView mContent;
    }
}
