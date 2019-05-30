
package com.user.protocol;

public enum ENUM_PLATFORM
{
    ANDROID(1),
    IOS(2);

    private int value = 0;
    private ENUM_PLATFORM(int initValue)
    {
        this.value = initValue;
    }

    public int value()
    {
        return this.value;
    }
}