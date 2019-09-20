package com.im.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * hxg 2019.09.19
 */
@Entity
public class IntelligenceDoorEntity {
    @Id
    private Long id;  //主键ID
    
    private int user_id;
    private String qr_code;
    private String rename;
    @Generated(hash = 324649597)
    public IntelligenceDoorEntity(Long id, int user_id, String qr_code,
            String rename) {
        this.id = id;
        this.user_id = user_id;
        this.qr_code = qr_code;
        this.rename = rename;
    }
    @Generated(hash = 492932738)
    public IntelligenceDoorEntity() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getUser_id() {
        return this.user_id;
    }
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
    public String getQr_code() {
        return this.qr_code;
    }
    public void setQr_code(String qr_code) {
        this.qr_code = qr_code;
    }
    public String getRename() {
        return this.rename;
    }
    public void setRename(String rename) {
        this.rename = rename;
    }


}
