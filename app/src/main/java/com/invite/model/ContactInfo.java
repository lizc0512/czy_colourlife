package com.invite.model;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Environment;
import android.provider.ContactsContract;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * ContactInfo
 * Created by chenql on 16/1/11.
 */
public class ContactInfo extends android.content.ContextWrapper {

    private ContentResolver cr = getContentResolver();

    public ContactInfo(Context base) {
        super(base);
    }

    public List<ContactsEntity> GetContactList(Boolean isWithGroupInfo) {
        List<ContactsEntity> list_ShowData = new ArrayList<>();

        ContactsEntity showContacts = new ContactsEntity();

        Cursor cursor = cr
                .query(ContactsContract.Contacts.CONTENT_URI,
                        new String[]{ContactsContract.Contacts._ID,
                                ContactsContract.Contacts.DISPLAY_NAME,
                                ContactsContract.Contacts.HAS_PHONE_NUMBER,
                                "sort_key"}, null, null,
                        "sort_key COLLATE LOCALIZED asc");

        String contactId = "";
        String name = "";
        String sort_key = "";
        String phoneNumber = "";
        String has_phoneNumber = "0";

        String company = "";
        if (cursor != null) {
            while (cursor.moveToNext()) {

                has_phoneNumber = cursor
                        .getString(cursor
                                .getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                if (has_phoneNumber.equals("1")) {

                    name = cursor
                            .getString(cursor
                                    .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                    if (name != null) {

                        contactId = cursor.getString(cursor
                                .getColumnIndex(ContactsContract.Contacts._ID));

                        sort_key = cursor.getString(cursor
                                .getColumnIndex("sort_key"));
                        has_phoneNumber = cursor
                                .getString(cursor
                                        .getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                        showContacts = new ContactsEntity(name, company, phoneNumber,
                                contactId, false, sort_key);
                        list_ShowData.add(showContacts);
                    }
              }
            }
        }

        if (cursor!=null){
            cursor.close();
        }


        Pad_FirstPhone(list_ShowData);
//        Pad_Company(list_ShowData);

//        if (isWithGroupInfo) {
//            Pad_Group(list_ShowData);
//        }

        return list_ShowData;
    }

    private void Pad_FirstPhone(List<ContactsEntity> list_ShowData) {

        Cursor phones = cr
                .query(ContactsContract.Data.CONTENT_URI,
                        new String[]{ContactsContract.Data.CONTACT_ID,
                                ContactsContract.CommonDataKinds.Phone.NUMBER},
                        ContactsContract.Data.MIMETYPE
                                + "='"
                                + ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE
                                + "'", null, null);

        String contactId = "";
        String phoneNumber = "";

        while (phones!=null&&phones.moveToNext()) {

            contactId = phones.getString(phones
                    .getColumnIndex(ContactsContract.Data.CONTACT_ID));

            for (ContactsEntity showContacts : list_ShowData) {

                if (showContacts.getPhoneNumber().length() == 0) {

                    if (showContacts.getContactId().equals(contactId)) {

                        phoneNumber = phones
                                .getString(phones
                                        .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        phoneNumber = phoneNumber == null ? "" : phoneNumber;
                        phoneNumber = phoneNumber.replace("-", "");
                        phoneNumber = phoneNumber.replace(" ", "");
                        showContacts.setPhoneNumber(phoneNumber);
                        break;
                    }
                }
            }
        }
        if (phones!=null){
            phones.close();
        }
    }

//    private void Pad_Company(List<ContactsEntity> list_ShowData) {
//
//        Cursor organizations = cr
//                .query(ContactsContract.Data.CONTENT_URI,
//
//                        new String[]{
//                                ContactsContract.Data.CONTACT_ID,
//                                ContactsContract.CommonDataKinds.Organization.COMPANY,
//                                "sort_key"},
//
//                        ContactsContract.Data.MIMETYPE
//                                + "='"
//                                + ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE
//                                + "'", null, null);
//
//        String contactId = "";
//        String company = "";
//
//        while (organizations.moveToNext()) {
//
//            contactId = organizations.getString(organizations
//                    .getColumnIndex(ContactsContract.Data.CONTACT_ID));
//
//            for (ContactsEntity showContacts : list_ShowData) {
//
//                if (showContacts.getTextCompany().length() == 0) {
//
//                    if (showContacts.getContactId().equals(contactId)) {
//
//                        company = organizations
//                                .getString(organizations
//                                        .getColumnIndex(ContactsContract.CommonDataKinds.Organization.COMPANY));
//
//                        company = company == null ? "" : company;
//                        showContacts.setTextCompany(company);
//                        break;
//                    }
//                }
//            }
//
//        }
//
//        organizations.close();
//    }

//    private void Pad_Group(List<ContactsEntity> list_ShowData) {
//
//        ContactGroup contactGroup = new ContactGroup(this);
//        HashMap<String, String> groupReference = contactGroup
//                .GetGroupReference();
//
//        Cursor GroupMembershipCursor = cr
//                .query(ContactsContract.Data.CONTENT_URI,
//
//                        new String[]{
//                                ContactsContract.Data.CONTACT_ID,
//                                ContactsContract.CommonDataKinds.GroupMembership.GROUP_ROW_ID},
//
//                        ContactsContract.Data.MIMETYPE
//                                + "='"
//                                + ContactsContract.CommonDataKinds.GroupMembership.CONTENT_ITEM_TYPE
//                                + "'", null, "contact_id COLLATE LOCALIZED asc");
//
//        String contactId = "";
//        String group_ROW_ID = "";
//        String groupTitle_Temp;
//        String groupTitle_Temp2;
//
//        while (GroupMembershipCursor.moveToNext()) {
//
//            contactId = GroupMembershipCursor.getString(GroupMembershipCursor
//                    .getColumnIndex(ContactsContract.Data.CONTACT_ID));
//
//            for (ContactsEntity showContacts : list_ShowData) {
//
//                if (showContacts.getContactId().equals(contactId)) {
//
//                    group_ROW_ID = GroupMembershipCursor
//                            .getString(GroupMembershipCursor
//                                    .getColumnIndex(ContactsContract.CommonDataKinds.GroupMembership.GROUP_ROW_ID));
//
//                    groupTitle_Temp = groupReference.get(group_ROW_ID);
//                    groupTitle_Temp2 = groupTitle_Temp == null
//                            ? ""
//                            : groupTitle_Temp;
//
//                    if (groupTitle_Temp2.length() > 0) {
//
//                        if (showContacts.getGroupName() == null) {
//                            showContacts.setGroupName(groupTitle_Temp2 + ",");
//                        } else {
//                            showContacts.setGroupName(showContacts.getGroupName()
//                                    + groupTitle_Temp2 + ",");
//                        }
//                    } else {
//                        if (showContacts.getGroupName() == null) {
//                            showContacts.setGroupName("");
//                        }
//                    }
//
//                    break;
//                }
//            }
//        }
//        GroupMembershipCursor.close();
//
//        for (ContactsEntity showContacts : list_ShowData) {
//            if (showContacts.getGroupName() == null) {
//                showContacts.setGroupName("");
//            }
//        }
//
//    }

    private String getContactId_Combine(List<String> contactIds) {
        String contactId_Combine = "";
        for (String contactId : contactIds) {
            contactId_Combine += "'" + contactId + "',";
        }

        if (contactId_Combine.length() > 0) {
            contactId_Combine = contactId_Combine.substring(0,
                    contactId_Combine.length() - 1);
        }

        return contactId_Combine;
    }

    private Collection<Contact> Generate_Contact_Submit_Form(
            List<String> contactIds) {
        Collection<Contact> contactHeader = new ArrayList<>();

        DeviceId deviceId = new DeviceId(this);
        for (String contactId : contactIds) {

            Contact contact = new Contact();

            contact.setPhoneContactId(contactId);
            contact.setDeviceId(deviceId.getDeviceId());
            contact.setIdType(deviceId.getIdType());

            Collection<ContactItem> contactItemHeader = new ArrayList<>();
            contact.setContactItem(contactItemHeader);

            contactHeader.add(contact);

        }

        return contactHeader;

    }

    private void Pad_names(Collection<Contact> contactHeader,
                           String contactId_Combine) {

        Cursor names = cr
                .query(ContactsContract.Data.CONTENT_URI,
                        new String[]{
                                ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME,
                                ContactsContract.CommonDataKinds.StructuredName.MIDDLE_NAME,
                                ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME,
                                ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
                                ContactsContract.CommonDataKinds.StructuredName.PREFIX,
                                ContactsContract.CommonDataKinds.StructuredName.SUFFIX,
                                ContactsContract.CommonDataKinds.StructuredName.CONTACT_ID},
                        ContactsContract.CommonDataKinds.StructuredName.CONTACT_ID
                                + " in("
                                + contactId_Combine
                                + ") AND "
                                + ContactsContract.Data.MIMETYPE + " =  ?",
                        new String[]{ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE},
                        null);

        String contactId;
        String first_judge;
        String middle_judge;
        String last_judge;
        String prefix_judge;
        String suffix_judge;

        while (names.moveToNext()) {

            contactId = names
                    .getString(names
                            .getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.CONTACT_ID));

            for (Contact contact : contactHeader) {
                if (contact.getPhoneContactId().equals(contactId)) {
                    first_judge = names
                            .getString(names
                                    .getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME));
                    if (first_judge != null) {
                        contact.setFirst(first_judge);
                    }

                    middle_judge = names
                            .getString(names
                                    .getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.MIDDLE_NAME));
                    if (middle_judge != null) {
                        contact.setMiddle(middle_judge);
                    }

                    last_judge = names
                            .getString(names
                                    .getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME));
                    if (last_judge != null) {
                        contact.setLast(last_judge);
                    }

                    prefix_judge = names
                            .getString(names
                                    .getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.PREFIX));
                    if (prefix_judge != null) {
                        contact.setPrefix(prefix_judge);
                    }

                    suffix_judge = names
                            .getString(names
                                    .getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.SUFFIX));
                    if (suffix_judge != null) {
                        contact.setSuffix(suffix_judge);
                    }
                    break;
                }
            }

        }
        names.close();

    }

    private void Pad_Event(Collection<Contact> contactHeader,
                           String contactId_Combine) {

        Cursor EventCursor = cr
                .query(ContactsContract.Data.CONTENT_URI,
                        new String[]{
                                ContactsContract.CommonDataKinds.Event.DATA,
                                ContactsContract.CommonDataKinds.Event.TYPE,
                                ContactsContract.CommonDataKinds.Event.CONTACT_ID},
                        ContactsContract.CommonDataKinds.Event.CONTACT_ID
                                + " in("
                                + contactId_Combine
                                + ") AND "
                                + ContactsContract.Data.MIMETYPE
                                + "='"
                                + ContactsContract.CommonDataKinds.Event.CONTENT_ITEM_TYPE
                                + "'", null, null);

        if (EventCursor.moveToFirst()) {
            String contactId = "";
            String event;
            String eventType;

            do {

                contactId = EventCursor
                        .getString(EventCursor
                                .getColumnIndex(ContactsContract.CommonDataKinds.Event.CONTACT_ID));

                for (Contact contact : contactHeader) {
                    if (contact.getPhoneContactId().equals(contactId)) {
                        event = EventCursor
                                .getString(EventCursor
                                        .getColumnIndex(ContactsContract.CommonDataKinds.Event.DATA));
                        eventType = EventType
                                .getActualType(Integer.parseInt(EventCursor.getString(EventCursor
                                        .getColumnIndex(ContactsContract.CommonDataKinds.Event.TYPE))));
                        if (eventType == EventType.BIRTHDAY) {
                            contact.setBirthday(event);
                        }

                        break;
                    }
                }

            } while (EventCursor.moveToNext());
        }

        EventCursor.close();

    }

    private void Pad_Phones(Collection<Contact> contactHeader,
                            String contactId_Combine) {

        Cursor phonesCursor = cr.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER,
                        ContactsContract.CommonDataKinds.Phone.TYPE,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID},
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " in("
                        + contactId_Combine + ")", null, null);

        if (phonesCursor.moveToFirst()) {

            String contactId = "";
            String phone;
            String phoneType;
            do {

                contactId = phonesCursor
                        .getString(phonesCursor
                                .getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));

                for (Contact contact : contactHeader) {
                    if (contact.getPhoneContactId().equals(contactId)) {
                        phone = phonesCursor
                                .getString(phonesCursor
                                        .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        phoneType = PhoneType
                                .getActualType(Integer.parseInt(phonesCursor.getString(phonesCursor
                                        .getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE))));

                        ContactItem contactItem = new ContactItem();
                        contactItem.setType("Phone");
                        contactItem.setSubtype(phoneType);
                        contactItem.setValue(phone);

                        contact.getContactItem().add(contactItem);

                        break;
                    }
                }

            } while (phonesCursor.moveToNext());
        }
        phonesCursor.close();

    }


    private void Pad_note(Collection<Contact> contactHeader,
                          String contactId_Combine) {

        Cursor noteCursor = cr
                .query(ContactsContract.Data.CONTENT_URI,
                        new String[]{
                                ContactsContract.CommonDataKinds.Note.NOTE,
                                ContactsContract.CommonDataKinds.Note.CONTACT_ID},
                        ContactsContract.CommonDataKinds.Note.CONTACT_ID
                                + " in("
                                + contactId_Combine
                                + ") AND "
                                + ContactsContract.Data.MIMETYPE
                                + "='"
                                + ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE
                                + "'", null, null);

        if (noteCursor.moveToFirst()) {

            String contactId = "";
            String note;
            do {

                contactId = noteCursor
                        .getString(noteCursor
                                .getColumnIndex(ContactsContract.CommonDataKinds.Note.CONTACT_ID));

                for (Contact contact : contactHeader) {
                    if (contact.getPhoneContactId().endsWith(contactId)) {
                        note = noteCursor
                                .getString(noteCursor
                                        .getColumnIndex(ContactsContract.CommonDataKinds.Note.NOTE));

                        ContactItem contactItem = new ContactItem();
                        contactItem.setType("Note");
                        contactItem.setValue(note);
                        contact.getContactItem().add(contactItem);

                        break;
                    }
                }

            } while (noteCursor.moveToNext());

        }
        noteCursor.close();

    }

    private void Pad_nickname(Collection<Contact> contactHeader,
                              String contactId_Combine) {

        Cursor nicknameCursor = cr
                .query(ContactsContract.Data.CONTENT_URI,
                        new String[]{
                                ContactsContract.CommonDataKinds.Nickname.NAME,
                                ContactsContract.CommonDataKinds.Nickname.CONTACT_ID},
                        ContactsContract.CommonDataKinds.Nickname.CONTACT_ID
                                + " in("
                                + contactId_Combine
                                + ") AND "
                                + ContactsContract.Data.MIMETYPE
                                + "='"
                                + ContactsContract.CommonDataKinds.Nickname.CONTENT_ITEM_TYPE
                                + "'", null, null);

        if (nicknameCursor.moveToFirst()) {

            String contactId = "";
            String nickname;
            do {

                contactId = nicknameCursor
                        .getString(nicknameCursor
                                .getColumnIndex(ContactsContract.CommonDataKinds.Nickname.CONTACT_ID));

                for (Contact contact : contactHeader) {
                    if (contact.getPhoneContactId().endsWith(contactId)) {

                        nickname = nicknameCursor
                                .getString(nicknameCursor
                                        .getColumnIndex(ContactsContract.CommonDataKinds.Nickname.NAME));
                        // Log.i("ContentProvider", "nickname: "+nickname);

                        ContactItem contactItem = new ContactItem();
                        contactItem.setType("Nickname");
                        contactItem.setValue(nickname);
                        contact.getContactItem().add(contactItem);

                        break;
                    }
                }

            } while (nicknameCursor.moveToNext());

        }

        nicknameCursor.close();

    }

    private Collection<Contact> getContactHeader(List<String> contactIds) {
        Collection<Contact> contactHeader = new ArrayList<Contact>();
        contactHeader = Generate_Contact_Submit_Form(contactIds);
        String contactId_Combine = getContactId_Combine(contactIds);
        Pad_names(contactHeader, contactId_Combine);
        Pad_Event(contactHeader, contactId_Combine);
        Pad_Phones(contactHeader, contactId_Combine);
        Pad_note(contactHeader, contactId_Combine);
        Pad_nickname(contactHeader, contactId_Combine);

        return contactHeader;
    }

    public void WriteFile(String jsonString) {
        try {
            File f = new File(Environment.getExternalStorageDirectory(), "EC");

            if (!f.exists()) {
                f.mkdir();
            }

            File gcmfile = new File(f, "registrationId.txt");
            FileWriter writer = new FileWriter(gcmfile);

            writer.append(jsonString);
            writer.flush();
            writer.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}