package com.invite.model;

/**
 * PhoneType
 * Created by chenql on 16/1/11.
 */
public class PhoneType {

    public static String getActualType(int type) {
        String actualType = "";

        switch (type) {
            case 0:
                actualType = "CUSTOM";
                break;
            case 1:
                actualType = "Home";
                break;
            case 2:
                actualType = "MOBILE";
                break;
            case 3:
                actualType = "WORK";
                break;
            case 4:
                actualType = "FAX_WORK";
                break;
            case 5:
                actualType = "FAX_HOME";
                break;
            case 6:
                actualType = "PAGER";
                break;
            case 7:
                actualType = "OTHER";
                break;
            case 8:
                actualType = "CALLBACK";
                break;
            case 9:
                actualType = "CAR";
                break;
            case 10:
                actualType = "COMPANY_MAIN";
                break;
            case 11:
                actualType = "ISDN";
                break;
            case 12:
                actualType = "MAIN";
                break;
            case 13:
                actualType = "OTHER_FAX";
                break;
            case 14:
                actualType = "RADIO";
                break;
            case 15:
                actualType = "TELEX";
                break;
            case 16:
                actualType = "TTY_TDD";
                break;
            case 17:
                actualType = "WORK_MOBILE";
                break;
            case 18:
                actualType = "WORK_PAGER";
                break;
            case 19:
                actualType = "ASSISTANT";
                break;
            case 20:
                actualType = "MMS";
                break;
            default:
                actualType = "UNKNOWN";
                break;
        }

        return actualType;
    }
}