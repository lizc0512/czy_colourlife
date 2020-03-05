package com.im.helper;

import android.content.Context;
import android.text.TextUtils;

import com.im.entity.FriendInforEntity;
import com.im.greendao.FriendInforEntityDao;
import com.im.greendao.IMGreenDaoManager;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;


/**
 * Author:  colin
 * Date:    2018-4-20 14:35
 * Description:
 */
public class CacheFriendInforHelper {

    private static CacheFriendInforHelper instance;


    public static CacheFriendInforHelper instance() {
        if (instance == null) {
            instance = new CacheFriendInforHelper();
        }
        return instance;
    }

    private CacheFriendInforHelper() {

    }

    /**
     * 查询好友列表
     *
     * @param context
     * @return
     */
    public List<FriendInforEntity> toQueryFriendList(Context context) {
        FriendInforEntityDao dao = IMGreenDaoManager.instance(context).getFriendInforEntityDao();
        QueryBuilder<FriendInforEntity> qb = dao.queryBuilder();
        return qb.list();
    }

    public List<String> toQueryFriendUUIdList(Context context) {
        List<FriendInforEntity> friendInforEntityList = toQueryFriendList(context);
        List<String> uuidList = new ArrayList<>();
        for (FriendInforEntity friendInforEntity : friendInforEntityList) {
            uuidList.add(friendInforEntity.getUuid());
        }
        return uuidList;
    }

    public List<String> toQueryFriendUserIdList(Context context) {
        List<FriendInforEntity> friendInforEntityList = toQueryFriendList(context);
        List<String> uuidList = new ArrayList<>();
        for (FriendInforEntity friendInforEntity : friendInforEntityList) {
            uuidList.add(String.valueOf(friendInforEntity.getId()));
        }
        return uuidList;
    }

    /**
     * 清空当前的表记录
     */
    public void deleteAll(Context context) {
        IMGreenDaoManager.instance(context).getFriendInforEntityDao().deleteAll();
    }

    /**
     * 通过uuid获取单个用户的信息
     *
     * @param context
     * @return
     */
    public FriendInforEntity toQueryFriendnforById(Context context, String uuid) {
        FriendInforEntity bean = null;
        if (!TextUtils.isEmpty(uuid)) {
            FriendInforEntityDao dao = IMGreenDaoManager.instance(context).getFriendInforEntityDao();
            QueryBuilder<FriendInforEntity> qb = dao.queryBuilder();
            List<FriendInforEntity> list = qb.where(FriendInforEntityDao.Properties.Uuid.eq(uuid)).list();
            if (list != null && list.size() > 0) {
                bean = list.get(0);
            }
        } else {
            bean = new FriendInforEntity();
        }
        return bean;
    }


    /**
     * 删除某个好友
     *
     * @param uuid
     * @return
     */
    public void delFriendInfor(Context context, String uuid) {
        FriendInforEntityDao dao = IMGreenDaoManager.instance(context).getFriendInforEntityDao();
        QueryBuilder<FriendInforEntity> qb = dao.queryBuilder();
        FriendInforEntity friendInforEntity = qb.where(FriendInforEntityDao.Properties.Uuid.eq(uuid)).build().unique();
        if (friendInforEntity != null) {
            dao.deleteByKey(friendInforEntity.getId());
        }
    }


    /**
     * 添加
     *
     * @param context
     * @param list
     */
    public void insertOrUpdate(Context context, List<FriendInforEntity> list) {
        if (list != null && list.size() > 0) {
            FriendInforEntityDao dao = IMGreenDaoManager.instance(context).getFriendInforEntityDao();
            dao.insertOrReplaceInTx(list);
        }
    }


    /**
     * 添加或者更新
     *
     * @param context
     * @param bean
     */
    public void insertOrUpdate(Context context, FriendInforEntity bean) {
        FriendInforEntityDao dao = IMGreenDaoManager.instance(context).getFriendInforEntityDao();
        List<FriendInforEntity> list = dao.queryBuilder().where(FriendInforEntityDao.Properties.Uuid.eq(bean.getUuid())).list();
        if (list != null && list.size() > 0) {
            bean.setId(list.get(0).getId());
            dao.update(bean);
        } else {
            dao.insert(bean);
        }
        dao.insertOrReplace(bean);
    }
}
