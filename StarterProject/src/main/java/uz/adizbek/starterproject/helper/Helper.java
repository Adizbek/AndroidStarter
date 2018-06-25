package uz.adizbek.starterproject.helper;

import android.graphics.Bitmap;

import com.blankj.utilcode.util.ImageUtils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import uz.adizbek.starterproject.Application;

/**
 * Created by adizbek on 2/18/18.
 */

public class Helper {
    public static String urlImage(String src) {
        return urlImage(src, Application.getHost());
    }

    public static String urlImage(String src, String host) {
        return String.format("%s/%s", host, src);
    }

    public static RequestBody stringField(String str) {
        return RequestBody.create(MediaType.parse("text/plain"), str);
    }

    public static RequestBody stringField(int str) {
        return RequestBody.create(MediaType.parse("text/plain"), String.valueOf(str));
    }


    public static String currencyFormatter(String in) {
        return currencyFormatter(Integer.parseInt(in));
    }

    public static String currencyFormatter(int in) {
        DecimalFormatSymbols sym = DecimalFormatSymbols.getInstance();
        sym.setGroupingSeparator(' ');
        DecimalFormat formatter = new DecimalFormat("#,###", sym);

        return formatter.format(in).concat(" сум");
    }
}
