package com.pay.alipay;

import android.content.Context;

public class PartnerConfig {
    // 合作商户ID。用签约支付宝账号登录ms.alipay.com后，在账户信息页面获取。
//    public static final String PARTNER = "2088011063375879";
//    // 商户收款的支付宝账号
//   public static final String SELLER = "2088011063375879";
//    // 商户（RSA）私钥
//    public static final String RSA_PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJoiYMl12bNTiRXrAGliVvOKzOmRyXPKaSFRwX+xCBXJ/f58HqdPomgztFkEdsnG+JW3q8O6tPzNmybB2hGlgdIT/rLQ/eq5x1OYy4seu7Ino+SeIoGaGh38sjDi5tbIsxSndh0yLpBAZCLVdR5sEFSKbLhiwwl5lU++hIkjYXV9AgMBAAECgYEAiphzE3QnJ3rr/4tquVg1+5RJoZT34miVk+Jh7iIPtRgGjjipj6Sp0qz7dDfxYIrLqESZ7MwMRm3TH0yce9WpHwqZ8D7VqXj/UgqErfdRQ+aR3LH9G2HXXULTsXSdPpdW3WQYC9BR8plMR6Q2TVQ5XUXtKDhP3EiOGUXrAEBq1MECQQDJeKCIEBunr3azuppxBtZkIx8k4QhKT7DQlapj9lLRW3Ile764Pd0xvMpOdr5mGWzIpJ6OFqvhNca3BBnMhu7ZAkEAw9no2xeZH1A147Up7y0L94nCHKgwe4HC+hSH6127BW/jqCyR/wc7mv3XmZHGDlzvE1hlJtaEviXsj+XRUcidRQJAWMHTtx2hkVYzrSpgL7sbaDIw3kZlKJfDBaFp13AFPEZVGz5Q30oh0G+jkL2vU7uPuTUMxPwn7KeMS8R6uSgYwQJAVP2DZ1BeSpBsUlyTzg8mWk2VxwnVwEMXcZ7nPOR3/GwJxzlQQfPJkgEGRsZTxHff5+08OBZvlHSwq+F3bJ46YQJAdKU9iBUUT9zhx30McrWyyUzDJoqFvULU3bhTZFPGphoDu58+qzUN6hWCiBH0Iy2Ukqf6CSjKEYAnY7GiymmBDQ==";
//    // 支付宝（RSA）公钥 用签约支付宝账号登录ms.alipay.com后，在密钥管理页面获取。
    //public static final String RSA_ALIPAY_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCPTNAth2DBR5LxEQnzm3Y/kfSKGkJCnAxvZIUqmhybb3atgGV835fX58WGan7gswVk6vkJTXQp/G8d0g358muwt1c6NpFX90vP5WIk97dbGmBjqQREqmvX7x/UuCAKg5dG+hhQngjParDDkjWI9m8xXiLctoJ6z72JRh5qMZ15VwIDAQAB";
//    // 支付宝回调地址
//    public static final String ALIPAY_CALLBACK = "http://shop.ecmobile.me/ecmobile/respond.php";
    // 支付宝安全支付服务apk的名称，必须与assets目录下的apk名称一致
    public static final String ALIPAY_PLUGIN_NAME = "alipay_msp.apk";

    public String PARTNER;//商户id
    public String SELLER;//收款的支付宝账号
    public String RSA_PRIVATE;
    public String RSA_ALIPAY_PUBLIC;
    public String ALIPAY_CALLBACK;
    public Context mContext;

    public PartnerConfig(Context context) {
        PARTNER = "2088011180065329";
        SELLER = "2088011180065329";
        RSA_PRIVATE = "MIICXQIBAAKBgQCtfXgFO8w+Ytx+2+thNfI3O4sKKBO+rjmaP3O1f/ZTjRRr0hJk8jvadDu+IC/aVBd2SU74cKG1isyOC2zEa//CbhCFnt9Deafo4vSGXoQFNRXn1yOl3W05lPUOw9cdVSCxI7AjJb9yktEju/u0vTy5/FrB75bnmBBFY2FJCYjT6QIDAQABAoGBAIggne8FV6JP8hE8QDm7mX2LI/LpNWz1yc7lIMZ2THKMjUx4veQlSZ+EemgZW1Lljq9KoENCJTCMYyrFfie1Ui0TQnHCGb33vWvJCKFgkJmgblpJQXZoaWIHNrQh+sh/UwL8gTx3CJdl0eEe652rmqrIx+zKyCzw+Tlj8GLdJ/v9AkEA2h7SL/OI0FJTycEGzrRDeQ64N13jGcK6gH1Goz8ANKZHg0Q+RI68BbpTh6tH9pZd1YmXfwcWEAeBLETP8vt7ywJBAMueenGbS0yRAEEj39EJnmnFfRQm4SXKKAQj7iNq1WdaGMf7qHYhnJjciE2zLUV+5RkRgfcGu2CLn+wrMyjyoJsCQQCLFQ4Xj2WU2Hoe8tthYKaJga5Ld62A1p9PoRIys9BwwfplpFVEJ/OSf24V20zG2ri4mcSlNiKGVBK05Kfomx0jAkAEgha96y1InbE+v/eVnjvmpZu2VYIInygxp0X/fL8K0cDYtKavLuFTtRZDeiMXdc7Gtk+FU53UbmL1DKPOVUjfAkBzm3Rcg0cNtjhbPs33oKhiJfK8q+EMtpFY8Bx0MRa/gthi0h7gEOx3LyLavyrFuBgdKy62UWVHDwmGBtXXf3HM";
        RSA_ALIPAY_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCEN/F3Ll7MQLuxe7ma6+6jOsz+L69P9TvcuMBrRnhBnCkTbObT7I6ElnKY5OsTHKpQ7PnFasNiKPI2xbaa3hOECjbWE3JjW+1Qt0K5tZvK0OYv4FRAYBF8doKNJgwW1UhS/3lRbDY3DuogNaICCJnk1hOd1bHowOZTE0c4jHLmAwIDAQAB";
        ALIPAY_CALLBACK = "http://dev.o2omobile.cn/alipaynotify";
    }


}


