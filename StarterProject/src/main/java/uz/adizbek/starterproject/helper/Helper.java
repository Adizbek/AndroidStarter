package uz.adizbek.starterproject.helper;

import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.app.Activity;
import android.content.Context;
import android.app.Activity;
import android.content.res.TypedArray;
import android.os.Build;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


import com.tapadoo.alerter.Alerter;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import uz.adizbek.starterproject.Application;
import uz.adizbek.starterproject.R;
import uz.adizbek.starterproject.R;

/**
 * Created by adizbek on 2/18/18.
 */

public class Helper {
    public static final String defaultLang = "uz";

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

    public static String dateFormatter(Calendar myCalendar) {
        return java.text.DateFormat.getDateInstance(java.text.DateFormat.LONG).format(myCalendar.getTime());
    }

    public static Date str2Date(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);

        try {
            Date d = format.parse(date);
            System.out.println(date);

            return d;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return new Date();
    }

    public static String date2MonthAndDay(Date date) {
        return (String) DateFormat.format("dd-MMM", date);
    }

    public static String dateToFullStringFormat(Calendar calendar) {
        return dateToFullStringFormat(calendar.getTime());
    }


    public static String dateToFullStringFormat(Date calendar) {
        return DateFormat.format("yyyy-MM-dd HH:mm:ss", calendar.getTime()).toString();
    }

    public static void changeLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);

        Configuration config = new Configuration();
        config.locale = locale;

        Application.c.getResources().updateConfiguration(config, Application.c.getResources().getDisplayMetrics());
        Application.prefs.saveString("lang", lang);
    }

    public static String getLang() {
        return Application.prefs.readString("lang", defaultLang);
    }

    public static void bindHtml(TextView tv, String string) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tv.setText(Html.fromHtml(string, Html.FROM_HTML_MODE_LEGACY));
        } else {
            tv.setText(Html.fromHtml(string));
        }

        tv.setMovementMethod(LinkMovementMethod.getInstance());
    }


    public static class Bundle {
        private android.os.Bundle _bundle;

        public Bundle() {
            _bundle = new android.os.Bundle();
        }

        public android.os.Bundle putInt(String id, int uid) {
            _bundle.putInt(id, uid);
            return _bundle;
        }
    }

    public static class UI {
        public static final String TAG = "StarterProject:UIHelper";

        public static void rippleEffect(Activity activity, View view) {
            try {
                int[] attrs = new int[]{R.attr.selectableItemBackground};
                TypedArray typedArray = activity.obtainStyledAttributes(attrs);
                int backgroundResource = typedArray.getResourceId(0, 0);
                view.setBackgroundResource(backgroundResource);
                typedArray.recycle();
            } catch (NullPointerException e) {
                Log.w(TAG, "rippleEffect: error");
            }
        }
    }

    public static void showErrorAlert(Activity activity, String text) {
        showAlert(activity,
                text,
                R.drawable.ic_info_outline_white_24dp,
                android.R.color.holo_red_light);
    }

    public static void showAlert(Activity c, String text, int icon, int color) {
        Alerter.create(c)
                .setText(text)
                .setIcon(icon)
                .setBackgroundColorRes(color)
                .show();

    }
}
