package com.feed.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.format.DateUtils;

import java.util.Calendar;

/**
 * Created by DELL on 2016/1/4.
 */
public class DateTimeDialog extends AlertDialog implements DialogInterface.OnClickListener {
    private DateTimeView mDateTimeView;
    private Calendar mDate = Calendar.getInstance();
    private OnDateTimeSetListener mOnDateTimeSetListener;

    @SuppressWarnings("deprecation")
    public DateTimeDialog(Context context, long date)
    {
        super(context);
        mDateTimeView = new DateTimeView(context);
        setView(mDateTimeView);
        mDateTimeView.setOnDateTimeChangedListener(new DateTimeView.OnDateTimeChangedListener()
        {
            @Override
            public void onDateTimeChanged(DateTimeView view, int year, int month, int day, int hour, int minute)
            {
                mDate.set(Calendar.YEAR, year);
                mDate.set(Calendar.MONTH, month);
                mDate.set(Calendar.DAY_OF_MONTH, day);
                mDate.set(Calendar.HOUR_OF_DAY, hour);
                mDate.set(Calendar.MINUTE, minute);
                mDate.set(Calendar.SECOND, 0);
                updateTitle(mDate.getTimeInMillis());
            }
        });

        setButton("选择", this);
        setButton2("取消", (OnClickListener)null);
        mDate.setTimeInMillis(date);
        updateTitle(mDate.getTimeInMillis());
    }

    public interface OnDateTimeSetListener
    {
        void OnDateTimeSet(AlertDialog dialog, long date);
    }

    private void updateTitle(long date)
    {
        int flag = DateUtils.FORMAT_SHOW_YEAR | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_WEEKDAY| DateUtils.FORMAT_SHOW_TIME;
        setTitle(DateUtils.formatDateTime(this.getContext(), date, flag));
    }

    public void setOnDateTimeSetListener(OnDateTimeSetListener callBack)
    {
        mOnDateTimeSetListener = callBack;
    }

    public void onClick(DialogInterface arg0, int arg1)
    {
        if (mOnDateTimeSetListener != null)
        {
            mOnDateTimeSetListener.OnDateTimeSet(this, mDate.getTimeInMillis());
        }
    }
}
