package com.invite.model;

import java.io.Serializable;

/**
 * 通讯录实体类
 * Created by chenql on 16/1/11.
 */
public class ContactsEntity implements Serializable{

    private String name;
    private String textCompany;
    private String phoneNumber;
    private String contactId;
    private Boolean checked;
    private String sort_key;
    private String groupName;

    public ContactsEntity() {

    }

    public ContactsEntity(String name, String textCompany, String phoneNumber,
                        String contactId, Boolean checked, String sort_key) {
        this.name = name;
        this.textCompany = textCompany;
        this.phoneNumber = phoneNumber;
        this.contactId = contactId;
        this.checked = checked;
        this.sort_key = sort_key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getSort_key() {
        return sort_key;
    }

    public void setSort_key(String sort_key) {
        this.sort_key = sort_key;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getTextCompany() {
        return textCompany;
    }

    public void setTextCompany(String textCompany) {
        this.textCompany = textCompany;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }
}
