package com.allapp.entity;

import com.chad.library.adapter.base.entity.SectionEntity;

import cn.net.cyberway.protocol.HomeLifeEntity;

/**
 * @name ${yuansk}
 * @class name：cn.net.cyberway.home.entity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2019/1/14 15:16
 * @change
 * @chang time
 * @class describe
 */
public class WholeAppSectionEntity extends SectionEntity<HomeAllLifeEntity.ContentBean.DataBean.ListBean> {


    public WholeAppSectionEntity(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public WholeAppSectionEntity(HomeAllLifeEntity.ContentBean.DataBean.ListBean t) {
        super(t);
    }
}
