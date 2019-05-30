package com.jpush.helper;



import com.jpush.entity.CheckMsgResp;

import java.util.HashMap;
import java.util.Map;

public class BadgeHelper {

    private static Map<String, CheckMsgResp> badgeMap;

    private static BadgeHelper instance;

    private BadgeHelper() {
    }

    public static BadgeHelper getBadgeHelper() {
        if (instance == null) {
            instance = new BadgeHelper();
        }
        return instance;
    }

    public boolean hasBadge() {
        if (badgeMap != null && badgeMap.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public Map<String, CheckMsgResp> getBadgeMap() {
        return badgeMap;
    }

    /**
     * 获取标识
     *
     * @param entityid
     * @return
     */
    public CheckMsgResp getBadge(String entityid) {

        if (entityid != null && entityid.length() > 0) {

            if (badgeMap == null) {
                badgeMap = new HashMap<String, CheckMsgResp>();
            }

            if (badgeMap.containsKey(entityid)) {
                return badgeMap.get(entityid);
            }
        }

        return null;
    }

    /**
     * 清空指定标识
     *
     * @param entityid
     */
    public void clearBadge(String entityid) {

        if (badgeMap.containsKey(entityid)) {

            badgeMap.remove(entityid);
        }
    }

    /**
     * 清空所有标识
     *
     * @param
     */
    public void clearBadge() {

        if (badgeMap != null) {

            badgeMap.clear();
        }
    }

    /**
     * 更新标识
     *
     * @param entityid
     * @param
     */
    public void updateBadge(String entityid, CheckMsgResp msgResp) {

        if (badgeMap == null) {
            badgeMap = new HashMap<String, CheckMsgResp>();
        }

        badgeMap.put(entityid, msgResp);
    }

}
