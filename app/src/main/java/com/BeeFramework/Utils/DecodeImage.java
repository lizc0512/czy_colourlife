package com.BeeFramework.Utils;

import android.graphics.Bitmap;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;

import java.util.Hashtable;

public class DecodeImage {

    public static Result handleQRCodeFormBitmap(Bitmap bitmap) {
        Hashtable<DecodeHintType, String> hints = new Hashtable<DecodeHintType, String>();
        hints.put(DecodeHintType.CHARACTER_SET, "utf-8");
        RGBLuminanceSource source = null;
        Result result = null;
        try {
            source = new RGBLuminanceSource(bitmap);
            BinaryBitmap bitmap1 = new BinaryBitmap(new HybridBinarizer(source));
            MultiFormatReader reader2 = new MultiFormatReader();
            result = reader2.decode(bitmap1, hints);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }catch (Exception e){

        }
        return result;
    }

}
