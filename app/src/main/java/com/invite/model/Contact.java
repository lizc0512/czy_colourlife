package com.invite.model;

import java.util.Collection;

/**
 * Contact
 * Created by chenql on 16/1/11.
 */
public class Contact {

    protected String contactId;
    protected String title;
    protected String first;// = "";
    protected String middle;
    protected String last;
    protected String initial;
    protected String prefix;
    protected String suffix;
    protected String birthday = "1/1/0001 12:00:00 AM";
    protected String deviceId;
    protected String phoneContactId;
    protected String cardUrl;
    protected String cardUrl2;
    protected String idType;

    public Boolean success = false;
    protected Collection<ContactItem> contactItem;

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getMiddle() {
        return middle;
    }

    public void setMiddle(String middle) {
        this.middle = middle;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getInitial() {
        return initial;
    }

    public void setInitial(String initial) {
        this.initial = initial;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getPhoneContactId() {
        return phoneContactId;
    }

    public void setPhoneContactId(String phoneContactId) {
        this.phoneContactId = phoneContactId;
    }

    public String getCardUrl() {
        return cardUrl;
    }

    public void setCardUrl(String cardUrl) {
        this.cardUrl = cardUrl;
    }

    public String getCardUrl2() {
        return cardUrl2;
    }

    public void setCardUrl2(String cardUrl2) {
        this.cardUrl2 = cardUrl2;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Collection<ContactItem> getContactItem() {
        return contactItem;
    }

    public void setContactItem(Collection<ContactItem> contactItem) {
        this.contactItem = contactItem;
    }
}
