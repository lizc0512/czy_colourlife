package com.audio.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.BeeFramework.Utils.ToastUtil;
import com.audio.fragment.ControlFragment;
import com.audio.utils.Config;
import com.audio.utils.TrackWindowMgr;
import com.audio.view.UserTrackView;
import com.eparking.helper.CustomDialog;
import com.permission.AndPermission;
import com.qiniu.droid.rtc.QNBeautySetting;
import com.qiniu.droid.rtc.QNCameraSwitchResultCallback;
import com.qiniu.droid.rtc.QNErrorCode;
import com.qiniu.droid.rtc.QNRTCEngine;
import com.qiniu.droid.rtc.QNRTCEngineEventListener;
import com.qiniu.droid.rtc.QNRTCSetting;
import com.qiniu.droid.rtc.QNRoomState;
import com.qiniu.droid.rtc.QNSourceType;
import com.qiniu.droid.rtc.QNStatisticsReport;
import com.qiniu.droid.rtc.QNTrackInfo;
import com.qiniu.droid.rtc.QNTrackKind;
import com.qiniu.droid.rtc.QNVideoFormat;
import com.qiniu.droid.rtc.model.QNAudioDevice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import cn.csh.colourful.life.utils.ToastUtils;
import cn.net.cyberway.R;

import static com.audio.utils.Config.DEFAULT_BITRATE;
import static com.audio.utils.Config.DEFAULT_FPS;
import static com.audio.utils.Config.DEFAULT_RESOLUTION;


public class RoomActivity extends AppCompatActivity implements QNRTCEngineEventListener, ControlFragment.OnCallEvents {
    private static final String TAG = "RoomActivity";
    private static final int BITRATE_FOR_SCREEN_VIDEO = (int) (1.5 * 1000 * 1000);
    public static final String ROOM_NAME = "ROOM_NAME";//房间号
    public static final String COMMUNITY_NAME = "COMMUNITY_NAME";//小区名称
    public static final String DOOR_ID = "DOOR_ID";//门禁的qrcode
    public static final String COMMUNITY_UUID = "COMMUNITY_UUID";//小区uuid
    public static final String EXTRA_USER_ID = "USER_ID";//用户的id
    public static final String EXTRA_ROOM_TOKEN = "ROOM_TOKEN";//房间的room_token
    public static final String EXTRA_ROOM_ID = "ROOM_ID";//房间的id

    private Toast mLogToast;
    private List<String> mHWBlackList = new ArrayList<>();

    private UserTrackView mTrackWindowFullScreen;
    private List<UserTrackView> mTrackWindowsList;
    private AlertDialog mKickOutDialog;

    private QNRTCEngine mEngine;
    private String mRoomToken;
    private String mUserId;
    private String mRoomId;
    private boolean mMicEnabled = false;
    private boolean mBeautyEnabled = false;
    private boolean mVideoEnabled = true;
    private boolean mSpeakerEnabled = true;
    private boolean mIsError = false;
    private boolean mIsAdmin = false;
    private boolean mIsJoinedRoom = false;
    private ControlFragment mControlFragment;
    private List<QNTrackInfo> mLocalTrackList;

    private QNTrackInfo mLocalVideoTrack;
    private QNTrackInfo mLocalAudioTrack;
    private QNTrackInfo mLocalScreenTrack;

    private int mScreenWidth = 0;
    private int mScreenHeight = 0;
    private int mCaptureMode = Config.CAMERA_CAPTURE;

    private TrackWindowMgr mTrackWindowMgr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        getWindow().getDecorView().setSystemUiVisibility(getSystemUiVisibility());

        final WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(outMetrics);
        mScreenWidth = outMetrics.widthPixels;
        mScreenHeight = outMetrics.heightPixels;
        setContentView(R.layout.activity_muti_track_room);
        List<String> permissionList = new ArrayList<>();
        permissionList.add(Manifest.permission.CAMERA);
        permissionList.add(Manifest.permission.RECORD_AUDIO);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (AndPermission.hasPermission(RoomActivity.this, permissionList)) {

            } else {
                if (AndPermission.hasAlwaysDeniedPermission(RoomActivity.this, permissionList)) {
                    ToastUtil.toastShow(getApplicationContext(), "为了保障正常使用,请将相机权限和麦克风权限开启");
                } else {
                    AndPermission.with(this)
                            .permission(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO)
                            .start();
                }
            }
        }
        Intent intent = getIntent();
        mRoomToken = intent.getStringExtra(EXTRA_ROOM_TOKEN);
        mUserId = intent.getStringExtra(EXTRA_USER_ID);
        mRoomId = intent.getStringExtra(EXTRA_ROOM_ID);
        mTrackWindowFullScreen = (UserTrackView) findViewById(R.id.track_window_full_screen);
        mTrackWindowsList = new LinkedList<UserTrackView>(Arrays.asList(
                (UserTrackView) findViewById(R.id.track_window_a),
                (UserTrackView) findViewById(R.id.track_window_b),
                (UserTrackView) findViewById(R.id.track_window_c),
                (UserTrackView) findViewById(R.id.track_window_d),
                (UserTrackView) findViewById(R.id.track_window_e),
                (UserTrackView) findViewById(R.id.track_window_f),
                (UserTrackView) findViewById(R.id.track_window_g),
                (UserTrackView) findViewById(R.id.track_window_h),
                (UserTrackView) findViewById(R.id.track_window_i)
        ));

        for (final UserTrackView view : mTrackWindowsList) {
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (mIsAdmin) {
                        showKickoutDialog(view.getUserId());
                    }
                    return false;
                }
            });
        }

        // init Control fragment
        mControlFragment = new ControlFragment();
        mControlFragment.setArguments(intent.getExtras());
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.control_fragment_container, mControlFragment);
        ft.commitAllowingStateLoss();
        // permission check


        // init rtcEngine and local track info list.
        initQNRTCEngine();
        initLocalTrackInfoList();

        // init decorate and set default to p2p mode
        mTrackWindowMgr = new TrackWindowMgr(mUserId, mScreenWidth, mScreenHeight, outMetrics.density
                , mEngine, mTrackWindowFullScreen, mTrackWindowsList);

        List<QNTrackInfo> localTrackListExcludeScreenTrack = new ArrayList<>(mLocalTrackList);
        localTrackListExcludeScreenTrack.remove(mLocalScreenTrack);
        mTrackWindowMgr.addTrackInfo(mUserId, localTrackListExcludeScreenTrack);

    }

    private void initQNRTCEngine() {
        SharedPreferences preferences = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        int videoWidth = preferences.getInt(Config.WIDTH, DEFAULT_RESOLUTION[1][0]);
        int videoHeight = preferences.getInt(Config.HEIGHT, DEFAULT_RESOLUTION[1][1]);
        int fps = preferences.getInt(Config.FPS, DEFAULT_FPS[1]);
        boolean isHwCodec = preferences.getInt(Config.CODEC_MODE, Config.HW) == Config.HW;
        int videoBitrate = preferences.getInt(Config.BITRATE, DEFAULT_BITRATE[1]);
        boolean isMaintainRes = preferences.getBoolean(Config.MAINTAIN_RES, false);
        mCaptureMode = preferences.getInt(Config.CAPTURE_MODE, Config.CAMERA_CAPTURE);

        // get the items in hw black list, and set isHwCodec false forcibly
        String[] hwBlackList = getResources().getStringArray(R.array.hw_black_list);
        mHWBlackList.addAll(Arrays.asList(hwBlackList));
        if (mHWBlackList.contains(Build.MODEL)) {
            isHwCodec = false;
        }

        QNVideoFormat format = new QNVideoFormat(videoWidth, videoHeight, fps);
        QNRTCSetting setting = new QNRTCSetting();
        setting.setCameraID(QNRTCSetting.CAMERA_FACING_ID.FRONT)
                .setHWCodecEnabled(isHwCodec)
                .setMaintainResolution(isMaintainRes)
                .setVideoBitrate(videoBitrate)
                .setVideoEncodeFormat(format)
                .setVideoPreviewFormat(format);
        mEngine = QNRTCEngine.createEngine(getApplicationContext(), setting, this);
    }

    private void initLocalTrackInfoList() {
        mLocalTrackList = new ArrayList<>();
        mLocalAudioTrack = mEngine.createTrackInfoBuilder()
                .setSourceType(QNSourceType.AUDIO)
                .setMaster(true)
                .create();
        mLocalTrackList.add(mLocalAudioTrack);

        QNVideoFormat screenEncodeFormat = new QNVideoFormat(mScreenWidth / 2, mScreenHeight / 2, 15);
        switch (mCaptureMode) {
            case Config.CAMERA_CAPTURE:
                mLocalVideoTrack = mEngine.createTrackInfoBuilder()
                        .setSourceType(QNSourceType.VIDEO_CAMERA)
                        .setMaster(true)
                        .setTag(UserTrackView.TAG_CAMERA).create();
                mLocalTrackList.add(mLocalVideoTrack);
                break;
            case Config.ONLY_AUDIO_CAPTURE:
                mControlFragment.setAudioOnly(true);
                break;
            case Config.SCREEN_CAPTURE:
                mLocalScreenTrack = mEngine.createTrackInfoBuilder()
                        .setVideoPreviewFormat(screenEncodeFormat)
                        .setBitrate(BITRATE_FOR_SCREEN_VIDEO)
                        .setSourceType(QNSourceType.VIDEO_SCREEN)
                        .setMaster(true)
                        .setTag(UserTrackView.TAG_SCREEN).create();
                mLocalTrackList.add(mLocalScreenTrack);
                mControlFragment.setAudioOnly(true);
                break;
            case Config.MUTI_TRACK_CAPTURE:
                mLocalScreenTrack = mEngine.createTrackInfoBuilder()
                        .setSourceType(QNSourceType.VIDEO_SCREEN)
                        .setVideoPreviewFormat(screenEncodeFormat)
                        .setBitrate(BITRATE_FOR_SCREEN_VIDEO)
                        .setMaster(true)
                        .setTag(UserTrackView.TAG_SCREEN).create();
                mLocalVideoTrack = mEngine.createTrackInfoBuilder()
                        .setSourceType(QNSourceType.VIDEO_CAMERA)
                        .setTag(UserTrackView.TAG_CAMERA).create();
                mLocalTrackList.add(mLocalScreenTrack);
                mLocalTrackList.add(mLocalVideoTrack);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mEngine != null) {
            mEngine.destroy();
            mEngine = null;
        }
        if (mTrackWindowFullScreen != null) {
            mTrackWindowFullScreen.dispose();
        }
        for (UserTrackView item : mTrackWindowsList) {
            item.dispose();
        }
        mTrackWindowsList.clear();
    }

    private void logAndToast(final String msg) {
        Log.d(TAG, msg);
        if (mLogToast != null) {
            mLogToast.cancel();
        }
        mLogToast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        mLogToast.show();
    }


    private void showCustomDialog(String tips) {
        try {
            final CustomDialog customDialog = new CustomDialog(RoomActivity.this, R.style.custom_dialog_theme);
            customDialog.show();
            customDialog.setCancelable(false);
            customDialog.setCanceledOnTouchOutside(false);
            customDialog.dialog_content.setText(tips);
            customDialog.dialog_button_ok.setGravity(Gravity.CENTER_HORIZONTAL);
            customDialog.dialog_button_cancel.setVisibility(View.GONE);
            customDialog.dialog_button_ok.setText(getResources().getString(R.string.message_define));
            customDialog.dialog_button_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    customDialog.dismiss();
                    finish();
                }
            });
        } catch (Exception e) {

        }
    }

    private void disconnectWithErrorMessage(final String errorMessage) {
        showCustomDialog(getResources().getString(R.string.channel_error_title) + "\r\n" + errorMessage);
    }

    private void reportError(final String description) {
        // TODO: handle error.
        if (!mIsError) {
            mIsError = true;
            disconnectWithErrorMessage(description);
        }
    }

    private void showKickoutDialog(final String userId) {
        if (mKickOutDialog == null) {
            mKickOutDialog = new AlertDialog.Builder(this)
                    .setNegativeButton(R.string.negative_dialog_tips, null)
                    .create();
        }
        mKickOutDialog.setMessage(getString(R.string.kickout_tips, userId));
        mKickOutDialog.setButton(DialogInterface.BUTTON_POSITIVE, getResources().getString(R.string.positive_dialog_tips),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mEngine.kickOutUser(userId);
                    }
                });
        mKickOutDialog.show();
    }

    private void updateRemoteLogText(final String logText) {
        Log.i(TAG, logText);
        mControlFragment.updateRemoteLogText(logText);
    }

    @TargetApi(19)
    private static int getSystemUiVisibility() {
        int flags = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            flags |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        }
        return flags;
    }

    @Override
    public void onRoomStateChanged(QNRoomState state) {
        Log.i(TAG, "onRoomStateChanged:" + state.name());
        switch (state) {
            case RECONNECTING:
                logAndToast(getString(R.string.reconnecting_to_room));
                mControlFragment.stopTimer();
                break;
            case CONNECTED:
                int userSize = mEngine.getUserList().size();
                if (userSize > 1) {
                    mEngine.publishTracks(mLocalTrackList);
                    logAndToast(getString(R.string.connected_to_room));
                    mIsJoinedRoom = true;
                    mControlFragment.startTimer();
                } else {
                    if (mEngine != null) {
                        mEngine.leaveRoom();
                    }
                    finish();
                }
                break;
            case RECONNECTED:
                logAndToast(getString(R.string.connected_to_room));
                mControlFragment.startTimer();
                break;
            case CONNECTING:
                logAndToast(getString(R.string.connecting_to, mRoomId));
                break;
        }
    }

    @Override
    public void onRemoteUserJoined(String remoteUserId, String userData) {
        updateRemoteLogText("onRemoteUserJoined:remoteUserId = " + remoteUserId + " ,userData = " + userData);
    }

    @Override
    public void onRemoteUserLeft(final String remoteUserId) {
        updateRemoteLogText("onRemoteUserLeft:remoteUserId = " + remoteUserId);
        if (mEngine != null) {
            mEngine.leaveRoom();
        }
        finish();
    }

    @Override
    public void onLocalPublished(List<QNTrackInfo> trackInfoList) {
        updateRemoteLogText("onLocalPublished");
        mEngine.enableStatistics();
    }

    @Override
    public void onRemotePublished(String remoteUserId, List<QNTrackInfo> trackInfoList) {
        updateRemoteLogText("onRemotePublished:remoteUserId = " + remoteUserId);
    }

    @Override
    public void onRemoteUnpublished(final String remoteUserId, List<QNTrackInfo> trackInfoList) {
        updateRemoteLogText("onRemoteUnpublished:remoteUserId = " + remoteUserId);
        if (mTrackWindowMgr != null) {
            mTrackWindowMgr.removeTrackInfo(remoteUserId, trackInfoList);
        }
    }

    @Override
    public void onRemoteUserMuted(String remoteUserId, List<QNTrackInfo> trackInfoList) {
        updateRemoteLogText("onRemoteUserMuted:remoteUserId = " + remoteUserId);
        if (mTrackWindowMgr != null) {
            mTrackWindowMgr.onTrackInfoMuted(remoteUserId);
        }
    }

    @Override
    public void onSubscribed(String remoteUserId, List<QNTrackInfo> trackInfoList) {
        updateRemoteLogText("onSubscribed:remoteUserId = " + remoteUserId);
        if (mTrackWindowMgr != null) {
            mTrackWindowMgr.addTrackInfo(remoteUserId, trackInfoList);
        }
    }

    @Override
    public void onKickedOut(String userId) {
        ToastUtils.showMessage(RoomActivity.this, getString(R.string.kicked_by_admin));
        finish();
    }

    @Override
    public void onStatisticsUpdated(final QNStatisticsReport report) {
        if (report.userId == null || report.userId.equals(mUserId)) {
            if (QNTrackKind.AUDIO.equals(report.trackKind)) {
                final String log = "音频码率:" + report.audioBitrate / 1000 + "kbps \n" +
                        "音频丢包率:" + report.audioPacketLostRate;
                mControlFragment.updateLocalAudioLogText(log);
            } else if (QNTrackKind.VIDEO.equals(report.trackKind)) {
                final String log = "视频码率:" + report.videoBitrate / 1000 + "kbps \n" +
                        "视频丢包率:" + report.videoPacketLostRate + " \n" +
                        "视频的宽:" + report.width + " \n" +
                        "视频的高:" + report.height + " \n" +
                        "视频的帧率:" + report.frameRate;
                mControlFragment.updateLocalVideoLogText(log);
            }
        }
    }

    @Override
    public void onAudioRouteChanged(QNAudioDevice routing) {
        updateRemoteLogText("onAudioRouteChanged: " + routing.name());
    }

    @Override
    public void onCreateMergeJobSuccess(String mergeJobId) {

    }

    @Override
    public void onError(int errorCode, String description) {
        if (errorCode == QNErrorCode.ERROR_TOKEN_ERROR
                || errorCode == QNErrorCode.ERROR_TOKEN_EXPIRED
                || errorCode == QNErrorCode.ERROR_AUTH_FAIL
                || errorCode == QNErrorCode.ERROR_RECONNECT_TOKEN_ERROR
                || errorCode == QNErrorCode.ERROR_SIGNAL_IO_EXCEPTION
                || errorCode == QNErrorCode.ERROR_TOKEN_INVALID) {
            reportError("roomToken 错误，请重新加入房间");
        } else if (errorCode == QNErrorCode.ERROR_PUBLISH_FAIL) {
            reportError("发布失败，请重新加入房间发布");
        } else if (errorCode == QNErrorCode.ERROR_ACCESSTOKEN_INVALID) {
            reportError("服务端发生了一些问题，加入房间失败，请重试");
        } else {
            logAndToast("errorCode:" + errorCode + " description:" + description);
        }
    }

    // Demo control
    @Override
    public void onCallHangUp() {
        if (mEngine != null) {
            mEngine.leaveRoom();
        }
        finish();
    }

    @Override
    public void onCameraSwitch() {
        if (mEngine != null) {
            mEngine.switchCamera(new QNCameraSwitchResultCallback() {
                @Override
                public void onCameraSwitchDone(boolean isFrontCamera) {
                }

                @Override
                public void onCameraSwitchError(String errorMessage) {
                }
            });
        }
    }

    @Override
    public boolean onToggleMic() {
        if (mEngine != null && mLocalAudioTrack != null) {
            mMicEnabled = !mMicEnabled;
            mLocalAudioTrack.setMuted(!mMicEnabled);
            mEngine.muteTracks(Collections.singletonList(mLocalAudioTrack));
            if (mTrackWindowMgr != null) {
                mTrackWindowMgr.onTrackInfoMuted(mUserId);
            }
        }
        return mMicEnabled;
    }

    @Override
    public boolean onToggleVideo() {
        if (mEngine != null && mLocalVideoTrack != null) {
            mVideoEnabled = !mVideoEnabled;
            mLocalVideoTrack.setMuted(!mVideoEnabled);
            if (mLocalScreenTrack != null) {
                mLocalScreenTrack.setMuted(!mVideoEnabled);
                mEngine.muteTracks(Arrays.asList(mLocalScreenTrack, mLocalVideoTrack));
            } else {
                mEngine.muteTracks(Collections.singletonList(mLocalVideoTrack));
            }
            if (mTrackWindowMgr != null) {
                mTrackWindowMgr.onTrackInfoMuted(mUserId);
            }
        }
        return mVideoEnabled;
    }

    @Override
    public boolean onToggleSpeaker() {
        if (mEngine != null) {
            mSpeakerEnabled = !mSpeakerEnabled;
            mEngine.muteRemoteAudio(!mSpeakerEnabled);
        }
        return mSpeakerEnabled;
    }

    @Override
    public boolean onToggleBeauty() {
        if (mEngine != null) {
            mBeautyEnabled = !mBeautyEnabled;
            QNBeautySetting beautySetting = new QNBeautySetting(0.5f, 0.5f, 0.5f);
            beautySetting.setEnable(mBeautyEnabled);
            mEngine.setBeauty(beautySetting);
        }
        return mBeautyEnabled;
    }

    public void startAnswerPhone() {
        if (!mIsJoinedRoom) {
            mEngine.joinRoom(mRoomToken);
        }
    }
}
