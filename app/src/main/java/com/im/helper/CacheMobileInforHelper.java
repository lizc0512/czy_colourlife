package com.im.helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.im.entity.FriendInforEntity;
import com.im.entity.MobileInforEntity;
import com.im.greendao.FriendInforEntityDao;
import com.im.greendao.IMGreenDaoManager;
import com.im.greendao.MobileInforEntityDao;
import com.user.UserAppConst;

import org.greenrobot.greendao.query.DeleteQuery;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;


/**
 * Author:  colin
 * Date:    2018-4-20 14:35
 * Description:
 */
public class CacheMobileInforHelper {

    private static CacheMobileInforHelper instance;


    public static CacheMobileInforHelper instance() {
        if (instance == null) {
            instance = new CacheMobileInforHelper();
        }
        return instance;
    }

    private CacheMobileInforHelper() {

    }

    /**
     * 查询通讯录列表
     *
     * @param context
     * @return
     */
    public List<MobileInforEntity> toQueryMobileList(Context context) {
        MobileInforEntityDao dao = IMGreenDaoManager.instance(context).getMobileInforEntityDao();
        QueryBuilder<MobileInforEntity> qb = dao.queryBuilder();
        return qb.list();
    }

    /**
     * 清空当前的表记录
     */
    public void deleteAll(Context context) {
        IMGreenDaoManager.instance(context).getFriendInforEntityDao().deleteAll();
    }

    /**
     * 通过mobile获取用户信息
     *
     * @param context
     * @return
     */
    public MobileInforEntity toQueryFriendnforById(Context context, String mobile) {
        MobileInforEntity bean = null;
        MobileInforEntityDao dao = IMGreenDaoManager.instance(context).getMobileInforEntityDao();
        QueryBuilder<MobileInforEntity> qb = dao.queryBuilder();
        List<MobileInforEntity> list = qb.where(MobileInforEntityDao.Properties.Mobile.eq(mobile)).list();
        if (list != null && list.size() > 0) {
            bean = list.get(0);
        }
        return bean;
    }

    /**
     * 添加或者更新
     *
     * @param context
     * @param bean
     */
    public void insertOrUpdate(Context context, MobileInforEntity bean) {
        MobileInforEntityDao dao = IMGreenDaoManager.instance(context).getMobileInforEntityDao();
        List<MobileInforEntity> list = dao.queryBuilder().where(MobileInforEntityDao.Properties.Uuid.eq(bean.getUuid())).list();
        if (list != null && list.size() > 0) {
            bean.setId(list.get(0).getId());
            dao.update(bean);
        } else {
            dao.insert(bean);
        }
        dao.insertOrReplace(bean);
    }

    public void insertOrUpdate(Context context, List<MobileInforEntity> list) {
        if (list != null && list.size() > 0) {
            MobileInforEntityDao dao = IMGreenDaoManager.instance(context).getMobileInforEntityDao();
            dao.insertOrReplaceInTx(list);
        }
    }
}
