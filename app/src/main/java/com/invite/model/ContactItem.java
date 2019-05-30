package com.invite.model;

import java.io.Serializable;

/**
 * ContactItem
 * Created by chenql on 16/1/11.
 */
public class ContactItem implements Serializable{

    protected String contactId;
    protected String type;
    protected String subtype;// = "";
    protected String value;
    protected String value2;
    protected String country;
    protected String region;

    public String getContactId() {
        return contactId;
    }

    public void setName(String contactId) {
        this.contactId = contactId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue2() {
        return value2;
    }

    public void setValue2(String value2) {
        this.value2 = value2;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
