package com.door.helper;

import android.content.Context;

import com.im.entity.IntelligenceDoorEntity;
import com.im.greendao.IMGreenDaoManager;
import com.im.greendao.IntelligenceDoorEntityDao;

import java.util.List;


/**
 * 门禁重命名数据库helper
 * hxg 2019.09.19
 */
public class CacheDoorRenameHelper {

    private static CacheDoorRenameHelper instance;


    public static CacheDoorRenameHelper instance() {
        if (instance == null) {
            instance = new CacheDoorRenameHelper();
        }
        return instance;
    }

    private CacheDoorRenameHelper() {

    }

    /**
     * 查询重命名
     */
    public String toQueryMobileList(Context context, int user_id, String qr_code) {
        String rename = "";

        IntelligenceDoorEntityDao dao = IMGreenDaoManager.instance(context).getIntelligenceDoorEntityDao();
        List<IntelligenceDoorEntity> list = dao.queryBuilder()
                .where(IntelligenceDoorEntityDao.Properties.User_id.eq(user_id),
                        IntelligenceDoorEntityDao.Properties.Qr_code.eq(qr_code))
                .list();
        if (list != null && list.size() > 0) {
            rename = list.get(0).getRename();
        }

        return rename;
    }

//    public String toQueryApplyRecordSize(Context context, String staus) {
//        ApplyRecordEntityDao dao = IMGreenDaoManager.instance(context).getApplyRecordEntityDao();
//        List<ApplyRecordEntity> applyRecordEntityList = dao.queryBuilder().where(ApplyRecordEntityDao.Properties.State.eq(staus)).list();
//        return applyRecordEntityList.size();
//    }


//    /**
//     * 清空当前的表记录
//     */
//    public void deleteAll(Context context) {
//        IMGreenDaoManager.instance(context).getFriendInforEntityDao().deleteAll();
//    }

//    /**
//     * 通过mobile获取用户信息
//     *
//     * @param context
//     * @return
//     */
//    public IntelligenceDoorEntity toQueryFriendnforById(Context context, String mobile) {
//        IntelligenceDoorEntity bean = null;
//        IntelligenceDoorEntityDao dao = IMGreenDaoManager.instance(context).getIntelligenceDoorEntityDao();
//        QueryBuilder<IntelligenceDoorEntity> qb = dao.queryBuilder();
//        List<IntelligenceDoorEntity> list = qb.where(IntelligenceDoorEntityDao.Properties.Mobile.eq(mobile)).list();
//        if (list != null && list.size() > 0) {
//            bean = list.get(0);
//        }
//        return bean;
//    }

    /**
     * 添加或者更新
     *
     * @param context
     * @param bean
     */
    public void insertOrUpdate(Context context, IntelligenceDoorEntity bean) {
        IntelligenceDoorEntityDao dao = IMGreenDaoManager.instance(context).getIntelligenceDoorEntityDao();
        List<IntelligenceDoorEntity> list = dao.queryBuilder()
                .where(IntelligenceDoorEntityDao.Properties.User_id.eq(bean.getUser_id()),
                        IntelligenceDoorEntityDao.Properties.Qr_code.eq(bean.getQr_code()))
                .list();
        if (list != null && list.size() > 0) {
            bean.setId(list.get(0).getId());
            dao.update(bean);
        } else {
            dao.insert(bean);
        }
        dao.insertOrReplace(bean);
    }

//    public void insertOrUpdate(Context context, List<IntelligenceDoorEntity> list) {
//        if (list != null && list.size() > 0) {
//            IntelligenceDoorEntityDao dao = IMGreenDaoManager.instance(context).getIntelligenceDoorEntityDao();
//            dao.insertOrReplaceInTx(list);
//        }
//    }
}
