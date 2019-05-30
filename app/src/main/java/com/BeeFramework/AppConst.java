package com.BeeFramework;

import android.os.Environment;

import java.text.SimpleDateFormat;
import java.util.Date;

/*
 *	 ______    ______    ______
 *	/\  __ \  /\  ___\  /\  ___\
 *	\ \  __<  \ \  __\_ \ \  __\_
 *	 \ \_____\ \ \_____\ \ \_____\
 *	  \/_____/  \/_____/  \/_____/
 *
 *
 *	Copyright (c) 2013-2014, {Bee} open source community
 *	http://www.bee-framework.com
 *
 *
 *	Permission is hereby granted, free of charge, to any person obtaining a
 *	copy of this software and associated documentation files (the "Software"),
 *	to deal in the Software without restriction, including without limitation
 *	the rights to use, copy, modify, merge, publish, distribute, sublicense,
 *	and/or sell copies of the Software, and to permit persons to whom the
 *	Software is furnished to do so, subject to the following conditions:
 *
 *	The above copyright notice and this permission notice shall be included in
 *	all copies or substantial portions of the Software.
 *
 *	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *	IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *	FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *	AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *	LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 *	FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
 *	IN THE SOFTWARE.
 */
public class AppConst {
    public static String FILEPATH = Environment.getExternalStorageDirectory() + "/.ColourLife/.cache/";

    public static final String TAG = "ColourLife";

    /**
     * 程序运行期间产生的文件，缓存根目录
     */
    public static final String ROOT_DIR_PATH = "/ColourLife/cache";

    public static final String LOG_DIR_PATH = ROOT_DIR_PATH + "log";

    /**
     * 保存的图片存放位置
     */
    public static final String CASH = "ColourLife";
    /**
     * 存放用户信息
     */
    public static final String USERINFO = "user_info";

    public static String imageName() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd_HHmmss");
        Date date = new Date();
        String time = format.format(date);
        String imageName = "IMG_" + time + ".jpg";
        return imageName;
    }

    public static final String APP_KEY = "wxjzzl5rsoli76rpx75nyb17c";//测试demo、七星大门
//        public static final String TEST_USER_ACCID = "xxxx8848";//测试demo
//    public static final String TEST_USER_ACCID = "3f2d5d49-a344-4e68-ab69-afcaa76c83d5";//七星大门
//        public static final String TEST_USER_TOKEN = "xxxxxxxx";//测试demo
//    public static final String TEST_USER_TOKEN = "1fc3j9pge26cg6ixsgp4dyv4k5z8m5n5ddldkgs7n7";//七星大门

    public static final long CONNECT_TIME_OUT = 5000;

}