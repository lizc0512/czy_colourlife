
package com.user.protocol;
public enum ENUM_USER_GENDER
{

    MALE(1),
    FEMALE(2),
    NONE(0);

    private int value = 0;
    private ENUM_USER_GENDER(int initValue)
    {
        this.value = initValue;
    }

    public int value()
    {
        return this.value;
    }
}