package cn.net.cyberway.home.activity;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.activity.BaseActivity;
import com.intelspace.library.module.LocalKey;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.Objects;

import cn.net.cyberway.R;
import cn.net.cyberway.home.adapter.LekaiAdapter;
import cn.net.cyberway.utils.LekaiHelper;

/**
 * 蓝牙门禁列表
 * hxg 2019.06.14
 */
public class LekaiListActivity extends BaseActivity implements View.OnClickListener {

    private ImageView user_top_view_back;
    private TextView user_top_view_title;
    private TextView tv_bluetooth;
    private SwipeMenuRecyclerView smrv_lekai;

    private LekaiAdapter mAdapter;

    private ArrayList<LocalKey> keysList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lekai_list);
        getWindow().setBackgroundDrawable(null);

        initView();
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if (null != bluetoothListener) {
                bluetoothListener = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        user_top_view_back = findViewById(R.id.user_top_view_back);
        user_top_view_title = findViewById(R.id.user_top_view_title);
        smrv_lekai = findViewById(R.id.smrv_lekai);
        tv_bluetooth = findViewById(R.id.tv_bluetooth);
        user_top_view_back.setOnClickListener(this);

        smrv_lekai.setLayoutManager(new LinearLayoutManager(this));
        smrv_lekai.useDefaultLoadMore();

        BluetoothAdapter blueAdapter = BluetoothAdapter.getDefaultAdapter();
        //支持蓝牙模块
        if (null != blueAdapter) {
            tv_bluetooth.setVisibility(blueAdapter.isEnabled() ? View.GONE : View.VISIBLE);
        }
    }

    private void initData() {
        try {
            IntentFilter statusFilter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
            this.registerReceiver(bluetoothListener, statusFilter);

            user_top_view_title.setText("蓝牙钥匙");
            keysList = LekaiHelper.getLocalKey();

            if (null != keysList) {
                if (0 != keysList.size()) {
                    try {
                        mAdapter = new LekaiAdapter(this, keysList);
                        smrv_lekai.setAdapter(mAdapter);
                        smrv_lekai.loadMoreFinish(false, false);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
        }
    }

    private BroadcastReceiver bluetoothListener = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(Objects.requireNonNull(intent.getAction()))) {
                int blueState = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, 0);
                switch (blueState) {
                    case BluetoothAdapter.STATE_ON:
                        tv_bluetooth.setVisibility(View.GONE);
                        break;
                    case BluetoothAdapter.STATE_OFF:
                        tv_bluetooth.setVisibility(View.VISIBLE);
                        break;
                }
            }
        }
    };
}


