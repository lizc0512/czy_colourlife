package cn.net.cyberway.activity;

import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.BeeFramework.BeeFrameworkApp;
import com.BeeFramework.Utils.ThemeStyleHelper;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.HttpApi;
import com.BeeFramework.model.HttpApiResponse;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.notification.protocol.NotifyGetApi;
import com.user.UserAppConst;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.net.cyberway.R;
import cn.net.cyberway.adpter.FindPropertyBottomRVAdapter;
import cn.net.cyberway.adpter.FindPropertyTopRVAdapter;
import cn.net.cyberway.home.view.CircleImageView;
import cn.net.cyberway.model.FindPropertyModel;
import cn.net.cyberway.protocol.AppHomePropertyListGetApi;
import cn.net.cyberway.protocol.FANPIAOCONTENTDATA;
import cn.net.cyberway.utils.LinkParseUtil;

/**
 * 找物业页面
 * data 2017/11/06
 * oauthor lizc
 */
public class FindPropertyActivity extends BaseActivity implements HttpApiResponse, OnClickListener {
    private SharedPreferences shared;
    public SharedPreferences.Editor editor;
    private AppHomePropertyListGetApi appHomePropertyListGetApi;
    private NotifyGetApi notifyGetApi;
    public ArrayList<FANPIAOCONTENTDATA> top = new ArrayList<FANPIAOCONTENTDATA>();
    public ArrayList<FANPIAOCONTENTDATA> middle = new ArrayList<FANPIAOCONTENTDATA>();
    public ArrayList<FANPIAOCONTENTDATA> bottom = new ArrayList<FANPIAOCONTENTDATA>();
    public ArrayList<String> notifyTitle = new ArrayList<String>(); //保存消息标题通知
    private ImageView mBack;
    private ImageView iv_findpro_news;
    private TextView mTitle;
    private TextView tv_findpro_notice;
    private FindPropertyModel findPropertyModel;
    private RecyclerView sv_findpro_show;
    private RelativeLayout rl_findpro_notice;
    private RelativeLayout rl_findpro_midright;
    private RelativeLayout rl_findpro_midbottom;
    private LinearLayout ll_findpro_addservice;
    private CircleImageView iv_findpro_midlogo;
    private TextView tv_findpro_mindname;
    private TextView tv_findpro_minddesr;
    private TextView tv_findpro_name;
    private CircleImageView iv_findpro_midright;
    private TextView tv_findpro_midrightname;
    private TextView tv_findpro_midrightdesc;
    private CircleImageView iv_findpro_midbottom;
    private TextView tv_findpro_midbottomname;
    private TextView tv_findpro_midbottomdesc;
    private RecyclerView sv_findpro_addservice;
    private FindPropertyBottomRVAdapter findPropertyAdapter;
    private FindPropertyTopRVAdapter findPropertyTopAdapter;
    private String midurlone, midurlonename;
    private String midurltwo, midurltwoname;
    private String midurlthree, midurlthreename;
    private Boolean isloading = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_property);
        shared = getSharedPreferences(UserAppConst.USERINFO, 0);
        editor = shared.edit();
        initView();
        initData();
    }

    private void initData() {
        findPropertyModel = new FindPropertyModel(this);
        int userid = shared.getInt(UserAppConst.Colour_User_id, 0);
        String findproCache = shared.getString(UserAppConst.FINDPROPERTY + userid, "");
        String findnotifyCache = shared.getString(UserAppConst.FINDNOTIFY + userid, "");
        appHomePropertyListGetApi = new AppHomePropertyListGetApi();
        notifyGetApi = new NotifyGetApi();
        if (!findproCache.isEmpty()) {
            try {
                appHomePropertyListGetApi.response.fromJson(new JSONObject(findproCache));
                adapterdata(appHomePropertyListGetApi);
                isloading = false;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (!findnotifyCache.isEmpty()) {
            try {
                notifyGetApi.response.fromJson(new JSONObject(findnotifyCache));
                //notifydata(notifyGetApi);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        findPropertyModel.getproperty(this, isloading);
        isloading = true;
//        String mCommunityId = shared.getString(UserAppConst.Colour_login_community_uuid, "1");
//        findPropertyModel.getfindnotity(this, mCommunityId, "2", 1);
    }


    private void initView() {
        sv_findpro_addservice = (RecyclerView) findViewById(R.id.sv_findpro_addservice);
        sv_findpro_show = (RecyclerView) findViewById(R.id.sv_findpro_show);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        GridLayoutManager gridBotLayoutManager = new GridLayoutManager(this, 4);
        sv_findpro_show.setLayoutManager(gridLayoutManager);
        sv_findpro_show.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.set(0, 0, 1, 1);
            }
        });
        sv_findpro_show.setNestedScrollingEnabled(false);
        sv_findpro_addservice.setLayoutManager(gridBotLayoutManager);
        sv_findpro_addservice.setNestedScrollingEnabled(false);
        mBack = (ImageView) findViewById(R.id.user_top_view_back);
        mTitle = (TextView) findViewById(R.id.user_top_view_title);
        mTitle.setText(getResources().getString(R.string.find_property));
        mBack.setOnClickListener(this);
        ThemeStyleHelper.onlyFrameTitileBar(getApplicationContext(), (FrameLayout) findViewById(R.id.czy_title_layout), mBack, mTitle);
        ll_findpro_addservice = (LinearLayout) findViewById(R.id.ll_findpro_addservice);
        iv_findpro_news = (ImageView) findViewById(R.id.iv_findpro_news);
        tv_findpro_notice = (TextView) findViewById(R.id.tv_findpro_notice);
        iv_findpro_midlogo = (CircleImageView) findViewById(R.id.iv_findpro_midlogo);
        tv_findpro_mindname = (TextView) findViewById(R.id.tv_findpro_mindname);
        tv_findpro_minddesr = (TextView) findViewById(R.id.tv_findpro_minddesr);
        iv_findpro_midright = (CircleImageView) findViewById(R.id.iv_findpro_midright);
        tv_findpro_midrightname = (TextView) findViewById(R.id.tv_findpro_midrightname);
        tv_findpro_midrightdesc = (TextView) findViewById(R.id.tv_findpro_midrightdesc);
        iv_findpro_midbottom = (CircleImageView) findViewById(R.id.iv_findpro_midbottom);
        tv_findpro_midbottomname = (TextView) findViewById(R.id.tv_findpro_midbottomname);
        tv_findpro_midbottomdesc = (TextView) findViewById(R.id.tv_findpro_midbottomdesc);
        tv_findpro_name = (TextView) findViewById(R.id.tv_findpro_name);
        rl_findpro_notice = (RelativeLayout) findViewById(R.id.rl_findpro_notice);
        rl_findpro_notice.setOnClickListener(this);
        rl_findpro_midright = (RelativeLayout) findViewById(R.id.rl_findpro_midright);
        rl_findpro_midright.setOnClickListener(this);
        rl_findpro_midbottom = (RelativeLayout) findViewById(R.id.rl_findpro_midbottom);
        rl_findpro_midbottom.setOnClickListener(this);


    }

    @Override
    public void OnHttpResponse(HttpApi api) {
        if (api.getClass().equals(AppHomePropertyListGetApi.class)) {
            AppHomePropertyListGetApi appHomePropertyListGetApi = (AppHomePropertyListGetApi) api;
            adapterdata(appHomePropertyListGetApi);
        } else if (api.getClass().equals(NotifyGetApi.class)) {
            NotifyGetApi notifyGetApi = (NotifyGetApi) api;
            notifydata(notifyGetApi);
        }
    }

    /**
     * 顶部和底部数据适配
     *
     * @param appHomePropertyListGetApi
     */
    private void adapterdata(AppHomePropertyListGetApi appHomePropertyListGetApi) {
        top.clear();
        middle.clear();
        bottom.clear();
        top = appHomePropertyListGetApi.response.content.top.list;
        middle = appHomePropertyListGetApi.response.content.middle.list;
        bottom = appHomePropertyListGetApi.response.content.bottom.list;
        tv_findpro_name.setText(appHomePropertyListGetApi.response.content.bottom.name);
        midset(middle);
        if (null == findPropertyTopAdapter) {
            findPropertyTopAdapter = new FindPropertyTopRVAdapter(this, top);
            sv_findpro_show.setAdapter(findPropertyTopAdapter);
        } else {
            findPropertyTopAdapter.setData(top);
        }
        if (null != bottom && bottom.size() > 0) {
            ll_findpro_addservice.setVisibility(View.VISIBLE);
        } else {
            ll_findpro_addservice.setVisibility(View.GONE);
        }
        if (null == findPropertyAdapter) {
            findPropertyAdapter = new FindPropertyBottomRVAdapter(this, bottom);
            sv_findpro_addservice.setAdapter(findPropertyAdapter);
        } else {
            findPropertyAdapter.setData(bottom);
        }
    }

    /**
     * 消息滚动适配
     *
     * @param notifyGetApi
     */
    private void notifydata(NotifyGetApi notifyGetApi) {
        notifyTitle.clear();
        if (notifyGetApi.response.notifies.size() > 0) {
            iv_findpro_news.setVisibility(View.GONE);
            tv_findpro_notice.setVisibility(View.GONE);
        } else {
            iv_findpro_news.setVisibility(View.VISIBLE);
            tv_findpro_notice.setVisibility(View.VISIBLE);
        }
        for (int i = 0; i < notifyGetApi.response.notifies.size(); i++) {
            notifyTitle.add(notifyGetApi.response.notifies.get(i).title);
        }
    }

    /**
     * 中间数据适配
     */
    private void midset(ArrayList<FANPIAOCONTENTDATA> middle) {
        if (middle.size() > 0) {
            for (int i = 0; i < middle.size(); i++) {
                if (i == 0) {
                    midurlone = middle.get(i).url;
                    midurlonename = middle.get(i).name;
                    tv_findpro_mindname.setText(middle.get(i).name);
                    tv_findpro_minddesr.setText(middle.get(i).desc);
                    ImageLoader.getInstance().displayImage(middle.get(i).img, iv_findpro_midlogo, BeeFrameworkApp.optionsImage);
                }
                if (i == 1) {
                    midurltwo = middle.get(i).url;
                    midurltwoname = middle.get(i).name;
                    tv_findpro_midrightname.setText(middle.get(i).name);
                    tv_findpro_midrightdesc.setText(middle.get(i).desc);
                    ImageLoader.getInstance().displayImage(middle.get(i).img, iv_findpro_midright, BeeFrameworkApp.optionsImage);
                }
                if (i == 2) {
                    midurlthree = middle.get(i).url;
                    midurlthreename = middle.get(i).name;
                    tv_findpro_midbottomname.setText(middle.get(i).name);
                    tv_findpro_midbottomdesc.setText(middle.get(i).desc);
                    ImageLoader.getInstance().displayImage(middle.get(i).img, iv_findpro_midbottom, BeeFrameworkApp.optionsImage);
                }
            }

        }
    }


    @Override
    public void onClick(View v) {
        Boolean islogin = shared.getBoolean(UserAppConst.IS_LOGIN, false);
        switch (v.getId()) {
            case R.id.user_top_view_back:
                this.finish();
                break;
            case R.id.rl_findpro_notice:
                if (islogin && !TextUtils.isEmpty(midurlone)) {
                    LinkParseUtil.parse(this, midurlone, midurlonename);
                }
                break;
            case R.id.rl_findpro_midright:
                if (islogin && !TextUtils.isEmpty(midurltwo)) {
                    LinkParseUtil.parse(this, midurltwo, midurltwoname);
                }
                break;
            case R.id.rl_findpro_midbottom:
                if (islogin && !TextUtils.isEmpty(midurlthree)) {
                    LinkParseUtil.parse(this, midurlthree, midurlthreename);
                }
                break;
        }
    }
}
