package dk.frmi.android.hangman;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.apache.http.util.ByteArrayBuffer;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.GenericArrayType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Frederik on 01-11-13.
 */
public class Helper {

    public static String stringArrayToString(String[] array){
        String str = "";
        for (int i = 0; i < array.length; i++){
            str += array[i];
        }
        return str;
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public static String[] stringToArray(String str){
        String[] strArray = new String[str.length()];
        for(int i = 0; i < str.length(); i++){
            strArray[i] = String.valueOf(str.charAt(i));
        }
        return strArray;
    }

}
