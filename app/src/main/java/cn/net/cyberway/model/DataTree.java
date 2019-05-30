package cn.net.cyberway.model;

import java.util.List;

/**
 * @name ${yuansk}
 * @class name：cn.net.cyberway.model
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2017/10/19 17:05
 * @change
 * @chang time
 * @class describe
 */
public class DataTree<K, V> {

    private K groupItem;
    private List<V> subItems;

    public DataTree(K groupItem, List<V> subItems) {
        this.groupItem = groupItem;
        this.subItems = subItems;
    }

    public K getGroupItem() {
        return groupItem;
    }

    public List<V> getSubItems() {
        return subItems;
    }
}
