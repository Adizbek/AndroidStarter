package uz.adizbek.starterproject.helper;

import android.graphics.Bitmap;

import com.blankj.utilcode.util.ImageUtils;

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


    public static Bitmap getResizedBitmap(Bitmap bm) {
        int width = bm.getWidth();
        int height = bm.getHeight();


        if (Math.max(width, height) > 800) {
            if (width > height) {
                float scale = ((float) 800) / width;
                width = 800;
                height *= scale;
            } else {
                float scale = ((float) 800) / height;
                height = 800;
                width *= scale;
            }
        }

        // Recreate the new Bitmap
        return ImageUtils.scale(bm, width, height);

    }
}
