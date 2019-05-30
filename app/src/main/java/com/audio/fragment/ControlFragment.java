package com.audio.fragment;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.model.NewHttpResponse;
import com.audio.activity.RoomActivity;
import com.audio.model.AudioHangupModel;
import com.door.entity.OpenDoorResultEntity;
import com.door.model.NewDoorModel;
import com.nohttp.utils.GsonUtils;

import cn.net.cyberway.R;

import static com.audio.activity.RoomActivity.COMMUNITY_NAME;
import static com.audio.activity.RoomActivity.COMMUNITY_UUID;
import static com.audio.activity.RoomActivity.DOOR_ID;
import static com.audio.activity.RoomActivity.ROOM_NAME;


/**
 * Fragment for call control.
 */
public class ControlFragment extends Fragment implements View.OnClickListener {
    private View mControlView;
    private TextView tv_room_name;
    private Chronometer mTimer;
    private LinearLayout audio_wait_layout;
    private TextView tv_wait_handup;
    private TextView tv_wait_answer;
    private LinearLayout audio_answer_layout;
    private TextView tv_answer_mute;
    private TextView tv_answer_handfree;
    private TextView tv_answer_handup;
    private TextView tv_audio_opendoor;

    private StringBuffer mRemoteLogText;

    private OnCallEvents mCallEvents;

    private boolean mIsScreenCaptureEnabled = false;
    private boolean mIsAudioOnly = false;
    private SoundPool mSoundPool;//摇一摇音效
    private int mAudio;

    private MyTimeCount myTimeCount;

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallEvents = (OnCallEvents) activity;
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mControlView = inflater.inflate(R.layout.fragment_room, container, false);

        tv_room_name = mControlView.findViewById(R.id.tv_room_name);
        mTimer = mControlView.findViewById(R.id.timer);
        audio_wait_layout = mControlView.findViewById(R.id.audio_wait_layout);
        tv_wait_handup = mControlView.findViewById(R.id.tv_wait_handup);
        tv_wait_answer = mControlView.findViewById(R.id.tv_wait_answer);
        audio_answer_layout = mControlView.findViewById(R.id.audio_answer_layout);

        tv_answer_mute = mControlView.findViewById(R.id.tv_answer_mute);
        tv_answer_handfree = mControlView.findViewById(R.id.tv_answer_handfree);
        tv_answer_handup = mControlView.findViewById(R.id.tv_answer_handup);
        tv_audio_opendoor = mControlView.findViewById(R.id.tv_audio_opendoor);

        tv_wait_handup.setOnClickListener(this);
        tv_wait_answer.setOnClickListener(this);
        tv_answer_mute.setOnClickListener(this);
        tv_answer_handfree.setOnClickListener(this);
        tv_answer_handup.setOnClickListener(this);
        tv_audio_opendoor.setOnClickListener(this);
        mSoundPool = new SoundPool(1, AudioManager.STREAM_SYSTEM, 5);
        mAudio = mSoundPool.load(getActivity(), R.raw.colourlifeopendoor, 1);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSoundPool.play(mAudio, 1, 1, 0, -1, 1);
            }
        }, 500);
        Bundle bundle = getArguments();
        String communityName = bundle.getString(COMMUNITY_NAME);
        String roomName = bundle.getString(ROOM_NAME);
        communityUUid = bundle.getString(COMMUNITY_UUID);
        doorId = bundle.getString(DOOR_ID);
        tv_room_name.setText(communityName + roomName);
        if (null == myTimeCount) {
            myTimeCount = new MyTimeCount(20000, 1000);
        }
        myTimeCount.start();
        return mControlView;
    }

    private int hungUp = 0;

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_wait_handup://直接挂断 未进去房间
                AudioHangupModel audioHangupModel = new AudioHangupModel(getActivity());
                audioHangupModel.uploadHangUpMethod(0, doorId, new NewHttpResponse() {
                    @Override
                    public void OnHttpResponse(int what, String result) {

                    }
                });
                mCallEvents.onCallHangUp();
                mSoundPool.release();
                break;
            case R.id.tv_answer_handup:
                mCallEvents.onCallHangUp();
                mSoundPool.release();
                break;
            case R.id.tv_wait_answer: //进去房间
                hungUp = 1;
                myTimeCount.cancel();
                mSoundPool.release();
                audio_wait_layout.setVisibility(View.GONE);
                audio_answer_layout.setVisibility(View.VISIBLE);
                ((RoomActivity) getActivity()).startAnswerPhone();
                break;
            case R.id.tv_answer_mute:
                boolean micEnabled = mCallEvents.onToggleMic();
                int micDrawableId = micEnabled ? R.mipmap.audio_no_mute : R.mipmap.audio_mute;
                tv_answer_mute.setCompoundDrawablesWithIntrinsicBounds(0, micDrawableId, 0, 0);
                break;
            case R.id.tv_answer_handfree:
                boolean speakerEnabled = mCallEvents.onToggleSpeaker();
                int speakerDrawableId = speakerEnabled ? R.mipmap.audio_hands_free : R.mipmap.audio_hands_normal;
                tv_answer_handfree.setCompoundDrawablesWithIntrinsicBounds(0, speakerDrawableId, 0, 0);
                break;
            case R.id.tv_audio_opendoor://开门
                if (hungUp == 0) {   //未接听直接开门 需要进行事件上报
                    AudioHangupModel hangupModel = new AudioHangupModel(getActivity());
                    hangupModel.uploadHangUpMethod(0, doorId, new NewHttpResponse() {
                        @Override
                        public void OnHttpResponse(int what, String result) {

                        }
                    });
                }
                NewDoorModel newDoorModel = new NewDoorModel(getActivity());
                newDoorModel.openQuickDoor(1, doorId, communityUUid, new NewHttpResponse() {
                    @Override
                    public void OnHttpResponse(int what, String result) {
                        try {
                            OpenDoorResultEntity openDoorResultEntity = GsonUtils.gsonToBean(result, OpenDoorResultEntity.class);
                            OpenDoorResultEntity.ContentBean contentBean = openDoorResultEntity.getContent();
                            if (contentBean.getOpen_result() == 1) {
                                ToastUtil.toastShow(getActivity(), "开门成功");
                            } else {
                                ToastUtil.toastShow(getActivity(), contentBean.getTitle());
                            }
                        } catch (Exception e) {
                            ToastUtil.toastShow(getActivity(), "开门失败");
                        }
                        mCallEvents.onCallHangUp();
                        mSoundPool.release();
                        getActivity().finish();
                    }
                });
                break;
        }
    }

    /**
     * Call control interface for container activity.
     */
    public interface OnCallEvents {
        void onCallHangUp();

        void onCameraSwitch();

        boolean onToggleMic();

        boolean onToggleVideo();

        boolean onToggleSpeaker();

        boolean onToggleBeauty();

    }

    public void setScreenCaptureEnabled(boolean isScreenCaptureEnabled) {
        mIsScreenCaptureEnabled = isScreenCaptureEnabled;
    }

    public void setAudioOnly(boolean isAudioOnly) {
        mIsAudioOnly = isAudioOnly;
    }

    private String communityUUid;
    private String doorId;


    public void defaultCallMediaPlayer(Context mContext) {
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        Ringtone r = RingtoneManager.getRingtone(mContext, notification);
        r.play();
    }

    public void startTimer() {
        mTimer.setBase(SystemClock.elapsedRealtime());
        mTimer.start();
    }

    public void stopTimer() {
        mTimer.stop();
    }

    public void updateLocalVideoLogText(String logText) {

    }

    public void updateLocalAudioLogText(String logText) {

    }

    public void updateRemoteLogText(String logText) {
        if (mRemoteLogText == null) {
            mRemoteLogText = new StringBuffer();
        }

    }

    class MyTimeCount extends CountDownTimer {
        public MyTimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {// 计时完毕时触发
            if (mSoundPool != null) {
                mSoundPool.release();
            }
            if (getActivity() != null) {
                getActivity().finish();
            }
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示

        }
    }
}
