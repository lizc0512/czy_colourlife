package com.door.entity;

import com.chad.library.adapter.base.entity.SectionEntity;

public class DoorOpenRecordSectionEntity extends SectionEntity<DoorOpenRecordEntity.ContentBean.DataBean> {
    public DoorOpenRecordSectionEntity(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public DoorOpenRecordSectionEntity(DoorOpenRecordEntity.ContentBean.DataBean dataBean) {
        super(dataBean);
    }
}
