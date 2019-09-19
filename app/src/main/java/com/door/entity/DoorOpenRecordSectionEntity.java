package com.door.entity;

import com.chad.library.adapter.base.entity.SectionEntity;

public class DoorOpenRecordSectionEntity extends SectionEntity<DoorOpenRecordEntity.ContentBean.DataBeanX.DataBean> {
    public DoorOpenRecordSectionEntity(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public DoorOpenRecordSectionEntity(DoorOpenRecordEntity.ContentBean.DataBeanX.DataBean dataBean) {
        super(dataBean);
    }
}
