package com.im.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.im.entity.ApplyRecordEntity;
import com.im.entity.FriendInforEntity;
import com.im.greendao.ApplyRecordEntityDao;
import com.im.greendao.FriendInforEntityDao;
import com.im.greendao.IMGreenDaoManager;
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
public class CacheApplyRecorderHelper {

    private static CacheApplyRecorderHelper instance;


    public static CacheApplyRecorderHelper instance() {
        if (instance == null) {
            instance = new CacheApplyRecorderHelper();
        }
        return instance;
    }

    private CacheApplyRecorderHelper() {

    }


    /**
     * 查询所有的申请记录
     *
     * @param context
     * @return
     */
    public List<ApplyRecordEntity> toQueryApplyRecordList(Context context) {
        ApplyRecordEntityDao dao = IMGreenDaoManager.instance(context).getApplyRecordEntityDao();
        QueryBuilder<ApplyRecordEntity> qb = dao.queryBuilder();
        return qb.list();
    }


    public String toApplyRecordUuidList(Context context) {
        ApplyRecordEntityDao dao = IMGreenDaoManager.instance(context).getApplyRecordEntityDao();
        QueryBuilder<ApplyRecordEntity> qb = dao.queryBuilder();
        List<ApplyRecordEntity> friendInforEntityList = qb.where(ApplyRecordEntityDao.Properties.Mobile.eq("")).list();
        StringBuffer uuidSb = new StringBuffer();
        for (ApplyRecordEntity applyRecordEntity : friendInforEntityList) {
            if (TextUtils.isEmpty(applyRecordEntity.getMobile())) {
                uuidSb.append(applyRecordEntity.getUuid());
                uuidSb.append(",");
                continue;
            } else {
                break;
            }
        }
        String uuidStr = uuidSb.toString();
        int length = uuidStr.length();
        return length > 1 ? uuidStr.substring(0, length - 1) : "";
    }

    public int toQueryApplyRecordSize(Context context, String staus) {
        ApplyRecordEntityDao dao = IMGreenDaoManager.instance(context).getApplyRecordEntityDao();
        if (null != dao) {
            List<ApplyRecordEntity> applyRecordEntityList = dao.queryBuilder().where(ApplyRecordEntityDao.Properties.State.eq(staus)).list();
            return applyRecordEntityList.size();
        } else {
            return 0;
        }
    }

    /**
     * 清空当前的表记录
     */
    public static void deleteAll(Context context) {
        IMGreenDaoManager.instance(context).getApplyRecordEntityDao().deleteAll();
    }

    /**
     * 通过uuid获取单条申请记录
     *
     * @param context
     * @return
     */
    public ApplyRecordEntity toQueryApplyRecordById(Context context, String uuid) {
        ApplyRecordEntity bean = null;
        ApplyRecordEntityDao dao = IMGreenDaoManager.instance(context).getApplyRecordEntityDao();
        QueryBuilder<ApplyRecordEntity> qb = dao.queryBuilder();
        List<ApplyRecordEntity> list = qb.where(ApplyRecordEntityDao.Properties.Uuid.eq(uuid)).list();
        if (list != null && list.size() > 0) {
            bean = list.get(0);
        }
        return bean;
    }


    /**
     * 删除某个人的申请记录
     *
     * @param uuid
     * @return
     */
    public void delApplyRecord(Context context, String uuid) {
        ApplyRecordEntityDao dao = IMGreenDaoManager.instance(context).getApplyRecordEntityDao();
        QueryBuilder<ApplyRecordEntity> qb = dao.queryBuilder();
        ApplyRecordEntity applyRecordEntity = qb.where(ApplyRecordEntityDao.Properties.Uuid.eq(uuid)).unique();
        if (null != applyRecordEntity) {
            dao.deleteByKey(applyRecordEntity.getId());
        }
    }


    /**
     * 更新
     *
     * @param context
     * @param bean
     */
    public void insertOrUpdate(Context context, ApplyRecordEntity bean) {
        ApplyRecordEntityDao dao = IMGreenDaoManager.instance(context).getApplyRecordEntityDao();
        List<ApplyRecordEntity> list = dao.queryBuilder().where(ApplyRecordEntityDao.Properties.Uuid.eq(bean.getUuid())).list();
        if (list != null && list.size() > 0) {
            bean.setId(list.get(0).getId());
            dao.update(bean);
        } else {
            dao.insert(bean);
        }
        dao.insertOrReplace(bean);
    }
}
