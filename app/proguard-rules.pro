# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/howiezhang/tools/adt-bundle-mac-x86_64/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:


-dontpreverify
-flattenpackagehierarchy
-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-verbose
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*


#-libraryjars src/main/java/framework/snappydb-0.5.2.jar
#-libraryjars src/main/java/plugin/locationManager/locSDK_6.13.jar
#-libraryjars src/main/java/plugin/pay/alipay/libs/alipaySdk-20151215.jar
#-libraryjars src/main/java/plugin/pay/wxpay/libs/libammsdk.jar
#-libraryjars src/main/java/plugin/push/libs/pushservice-4.6.0.53.jar
#-libraryjars src/main/java/plugin/scanCode/libs/core.jar
#-libraryjars src/main/java/plugin/share/libs/libammsdk.jar
#-libraryjars src/main/java/plugin/share/libs/open_sdk_r4547.jar
#-libraryjars src/main/java/plugin/share/libs/weiboSDKCore_3.1.2.jar

#-libraryjars libs/universal-image-loader-1.8.6-with-sources.jar


-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.support.v4.**

-keep public class * extends framework.foundation.BaseActivity

-keep public class com.android.vending.licensing.ILicensingService

-keep class com.baidu.** { *; }

-keep class com.tencent.** { *; }
-keep class com.tencent.** { *; }


-keep class com.umeng.** { *; }
-keep class com.umeng.analytics.** { *; }
-keep class com.umeng.common.** { *; }
-keep class com.umeng.newxp.** { *; }

-dontwarn android.support.v4.**
-dontwarn org.apache.commons.net.**
-dontwarn com.tencent.**

-dontshrink
-dontoptimize
-dontwarn com.google.android.maps.**
-dontwarn android.webkit.WebView
-dontwarn com.umeng.**
-dontwarn com.tencent.weibo.sdk.**
-dontwarn com.facebook.**
-dontwarn com.esotericsoftware.**
-dontwarn org.objenesis.**


-keep enum com.facebook.**
-keepattributes Exceptions,InnerClasses,Signature
-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable

-keep public interface com.facebook.**
-keep public interface com.tencent.**
-keep public interface com.umeng.socialize.**
-keep public interface com.umeng.socialize.sensor.**
-keep public interface com.umeng.scrshot.**

-keep public class com.umeng.socialize.* {*;}
-keep public class javax.**
-keep public class android.webkit.**

-keep class com.facebook.**
-keep class com.umeng.scrshot.**
-keep public class com.tencent.** {*;}
-keep class com.umeng.socialize.sensor.**

-keep class com.tencent.mm.sdk.openapi.WXMediaMessage {*;}

-keep class com.tencent.mm.sdk.openapi.** implements com.tencent.mm.sdk.openapi.WXMediaMessage$IMediaObject {*;}

-keepclassmembers class ** {
    public void onEvent*(**);
}


-keepclassmembers class fqcn.of.javascript.interface.for.webview {
   public *;
}

-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }

-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}

-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}



# 七牛云存储
-keep class com.qiniu.**{*;}
-keep class com.qiniu.**{public <init>();}
-ignorewarnings

# 高德地图
-keep class com.amap.api.location.**{*;}
-keep class com.amap.api.fence.**{*;}
-keep class com.autonavi.aps.amapapi.model.**{*;}
-keep class com.amap.api.services.**{*;}
-keep class com.amap.api.maps2d.**{*;}
-keep class com.amap.api.mapcore2d.**{*;}
-keep class com.amap.api.navi.**{*;}
-keep class com.autonavi.**{*;}

-keepattributes Signature

# For using GSON @Expose annotation
-keepattributes *Annotation*

# Gson specific classes
-keep class sun.misc.Unsafe { *; }
#-keep class com.google.gson.stream.** { *; }

# Application classes that will be serialized/deserialized over Gson
-keep class com.google.gson.examples.android.model.** { *; }

# Prevent proguard from stripping interface information from TypeAdapterFactory,
# JsonSerializer, JsonDeserializer instances (so they can be used in @JsonAdapter)
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer
##---------------End: proguard configuration for Gson  ----------

-keep public class pl.droidsonroids.gif.GifIOException{<init>(int);}

-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

-keep class com.youmai.hxsdk.proto.**{*;}

-keepattributes Exceptions,InnerClasses

-keepclassmembers enum com.youmai.hxsdk.proto.YouMaiBasic.COMMANDID {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

#华为push
-ignorewarning
-keepattributes *Annotation*
-keepattributes Exceptions
-keepattributes InnerClasses
-keepattributes Signature
# hmscore-support: remote transport
-keep class * extends com.huawei.hms.core.aidl.IMessageEntity { *; }
# hmscore-support: remote transport
-keepclasseswithmembers class * implements com.huawei.hms.support.api.transport.DatagramTransport {
 <init>(...);
}
# manifest: provider for updates
-keep public class com.huawei.hms.update.provider.UpdateProvider { public *; protected *; }



#生成老安装包时打开，生成mapping.txt， 并将mapping.txt,移到/app下
-printmapping mapping.txt
#生成的mapping.txt在app/buidl/outputs/mapping/release路径下，移动到/app路径下

#修复后生成新安装包时打开，保证混淆结果一致
#-applymapping mapping.txt

# ProGuard configurations for NetworkBench Lens
-keep class com.networkbench.** { *; }
-dontwarn com.networkbench.**
-keepattributes Exceptions, Signature, InnerClasses
# End NetworkBench Lens

#hotfix
-keep class com.taobao.sophix.**{*;}
-keep class com.ta.utdid2.device.**{*;}
 # ProGuard configurations for Bugtags
-keepattributes LineNumberTable,SourceFile
-keep class com.bugtags.library.** {*;}
-dontwarn com.bugtags.library.**
-keep class io.bugtags.** {*;}
-dontwarn io.bugtags.**
-dontwarn org.apache.http.**
-dontwarn android.net.http.AndroidHttpClient
# End Bugtags
-keep class com.lhqpay.ewallet.**{ *; }
-keep class com.lhqpay.ewallet.adapter.** { *; }
-keep class com.lhqpay.ewallet.basil.** { *; }
-keep class com.lhqpay.ewallet.entity.** { *; }
-keep class com.lhqpay.ewallet.utils.** { *; }
-keep class com.lhqpay.ewallet.wedgit.** { *; }
-keep class com.lhqpay.ewallet.lhqpay.listener.** { *; }
-keep public class com.lhqpay.ewallet.R$*{
	public static final int *;
}
 -keep class com.android.dingtalk.share.ddsharemodule.** {*;}

# bugly
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}
-keep class android.support.**{*;}

#数位室内导航 保留
#-keep class com.szshuwei.x.** { *; }
#-keep class com.shuwei.logcollection.** { *; }

#腾讯实名认证 AuthSdk
-keep class com.tencent.authsdk.AuthSDKApi { *; }
-keep class com.tencent.authsdk.callback.IdentityCallback { *; }
-keep class com.tencent.youtufacetrack.** { *; }
-keep class com.tencent.authsdk.IDCardInfo {*;}
-keep class com.tencent.authsdk.IDCardInfo$Builder { *; }
-keep class com.tencent.mm.opensdk.** {

*;

}

-keep class com.tencent.wxop.** {

*;

}

-keep class com.tencent.mm.sdk.** {

*;

}