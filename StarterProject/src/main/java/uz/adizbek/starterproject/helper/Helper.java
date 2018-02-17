package uz.adizbek.starterproject.helper;

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
}
