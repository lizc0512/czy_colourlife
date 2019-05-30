package com.feed.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.BeeFramework.activity.BaseFragmentActivity;

import cn.net.cyberway.R;
import cn.net.cyberway.fagment.CommunityFragment;

/**
 * Created by HX_CHEN on 2016/7/25.
 */
public class LinLiActivity extends BaseFragmentActivity
{
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private CommunityFragment communityFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linli);
        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        if (communityFragment == null) {
            communityFragment = new CommunityFragment();
            transaction.add(R.id.main_fragment_container, communityFragment);
        } else {
            transaction.show(communityFragment);
        }
        transaction.commitAllowingStateLoss();
    }
}
