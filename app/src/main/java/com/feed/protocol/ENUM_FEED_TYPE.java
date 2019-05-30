
package com.feed.protocol;
public enum ENUM_FEED_TYPE
{
    NORMAL(1),
    ACTIVITY(2),
    SHARE(3);

    private int value = 1;
    private ENUM_FEED_TYPE(int initValue)
    {
        this.value = initValue;
    }

    public int value()
    {
        return this.value;
    }
}