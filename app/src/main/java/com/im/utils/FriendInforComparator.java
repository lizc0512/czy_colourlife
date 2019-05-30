package com.im.utils;

import com.im.entity.FriendInforEntity;
import com.im.entity.MobileBookEntity;

import java.util.Comparator;

/**
 * @author xiaanming
 */
public class FriendInforComparator implements Comparator<FriendInforEntity> {

    public int compare(FriendInforEntity o1, FriendInforEntity o2) {
        if (o1.getSortLetters().equals("@")
                || o2.getSortLetters().equals("#")) {
            return -1;
        } else if (o1.getSortLetters().equals("#")
                || o2.getSortLetters().equals("@")) {
            return 1;
        } else {
            return o1.getSortLetters().compareTo(o2.getSortLetters());
        }
    }
}
