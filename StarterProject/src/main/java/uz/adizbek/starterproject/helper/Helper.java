package uz.adizbek.starterproject.helper;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.StringRes;
import android.text.Html;
import android.text.format.DateFormat;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;

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

/**
 * Created by adizbek on 2/18/18.
 */

public class Helper {
    public static final String defaultLang = "ru";
    private static Locale locale;

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
        try {
            return currencyFormatter(Integer.parseInt(in));
        } catch (Exception e) {
            return currencyFormatter(0);
        }
    }

    public static String currencyFormatter(int in) {
        DecimalFormatSymbols sym = DecimalFormatSymbols.getInstance();
        sym.setGroupingSeparator(' ');
        DecimalFormat formatter = new DecimalFormat("#,###", sym);

        return String.format(Application.c.getString(R.string.currency_sum), formatter.format(in));
    }

    public static String dateFormatter(Calendar myCalendar) {
        return java.text.DateFormat.getDateInstance(java.text.DateFormat.LONG).format(myCalendar.getTime());
    }

    public static Date str2Date(String date) {
        return str2Date(date, "yyyy-MM-dd HH:mm:ss");
    }

    public static Date str2Date(String date, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern, getLocale());

        try {
            Date d = format.parse(date);
            System.out.println(date);

            return d;
        } catch (Exception e) {
            System.out.println("Parse error ");
            return new Date();
        }
    }

    public static String date2MonthAndDay(Date date) {
        return (String) DateFormat.format("dd-MMM", date);
    }


    public static String date2MonthAndDayAndHour(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM, HH:mm", Helper.getLocale());
        return sdf.format(date);
    }

    public static String dateToFullStringFormat(Calendar calendar) {
        return dateToFullStringFormat(calendar.getTime());
    }


    public static String date2Custom(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Helper.getLocale());
        return sdf.format(date);
    }


    public static String dateToFullStringFormat(Date calendar) {
        return DateFormat.format("yyyy-MM-dd HH:mm:ss", calendar.getTime()).toString();
    }

    public static void loadSavedLang() {
        changeLocale(getLang());
    }

    public static void changeLocale(String lang) {
        locale = new Locale(lang);
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

    public static void bindHtml(TextView tv, @StringRes int res) {
        bindHtml(tv, tv.getContext().getResources().getString(res));
    }

    public static Locale getLocale() {
        if (locale == null)
            loadSavedLang();

        return locale;
    }


    public static class Bundle {
        private android.os.Bundle _bundle;

        public Bundle() {
            _bundle = new android.os.Bundle();
        }

        public Bundle putInt(String id, int uid) {
            _bundle.putInt(id, uid);
            return this;
        }

        public Bundle putParcelable(String id, Parcelable uid) {
            _bundle.putParcelable(id, uid);
            return this;
        }

        public Bundle putString(String id, String uid) {
            _bundle.putString(id, uid);
            return this;
        }

        public android.os.Bundle get() {
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

    private static class Docs {
        /**
         * @url http://www.java2s.com/Tutorial/Java/0120__Development/0190__SimpleDateFormat.htm
         * 6.12.SimpleDateFormat
        6.12.1.	Get Today's Date
        6.12.2.	new SimpleDateFormat('hh')
        6.12.3.	new SimpleDateFormat('H') // The hour (0-23)
        6.12.4.	new SimpleDateFormat('m'):9 The minutes
        6.12.5.	new SimpleDateFormat('mm')
        6.12.6.	new SimpleDateFormat('s'): The seconds
        6.12.7.	new SimpleDateFormat('ss')
        6.12.8.	new SimpleDateFormat('a'): The am/pm marker
        6.12.9.	new SimpleDateFormat('z'): The time zone
        6.12.10.	new SimpleDateFormat('zzzz')
        6.12.11.	new SimpleDateFormat('Z')
        6.12.12.	new SimpleDateFormat('hh:mm:ss a')
        6.12.13.	new SimpleDateFormat('HH.mm.ss')
        6.12.14.	new SimpleDateFormat('HH:mm:ss Z')
        6.12.15.	The day number: SimpleDateFormat('d')
        6.12.16.	Two digits day number: SimpleDateFormat('dd')
        6.12.17.	The day in week: SimpleDateFormat('E')
        6.12.18.	Full day name: SimpleDateFormat('EEEE')
        6.12.19.	SimpleDateFormat('MM'): number based month value
        6.12.20.	SimpleDateFormat('MM/dd/yy')
        6.12.21.	SimpleDateFormat('dd-MMM-yy')
        6.12.22.	The month: SimpleDateFormat('M')
        6.12.23.	SimpleDateFormat('E, dd MMM yyyy HH:mm:ss Z')
        6.12.24.	SimpleDateFormat('yyyy')
        6.12.25.	Three letter-month value: SimpleDateFormat('MMM')
        6.12.26.	Full length of month name: SimpleDateFormat('MMMM')
        6.12.27.	Formatting a Date Using a Custom Format
        6.12.28.	Formatting date with full day and month name and show time up to milliseconds with AM/PM
        6.12.29.	Format date in dd/mm/yyyy format
        6.12.30.	Format date in mm-dd-yyyy hh:mm:ss format
        6.12.31.	Formatting day of week using SimpleDateFormat
        6.12.32.	Formatting day of week in EEEE format like Sunday, Monday etc.
        6.12.33.	Formatting day in d format like 1,2 etc
        6.12.34.	Formatting day in dd format like 01, 02 etc.
        6.12.35.	Format hour in h (1-12 in AM/PM) format like 1, 2..12.
        6.12.36.	Format hour in hh (01-12 in AM/PM) format like 01, 02..12.
        6.12.37.	Format hour in H (0-23) format like 0, 1...23.
        6.12.38.	Format hour in HH (00-23) format like 00, 01..23.
        6.12.39.	Format hour in k (1-24) format like 1, 2..24.
        6.12.40.	Format hour in kk (01-24) format like 01, 02..24.
        6.12.41.	Format hour in K (0-11 in AM/PM) format like 0, 1..11.
        6.12.42.	Format hour in KK (00-11) format like 00, 01,..11.
        6.12.43.	Formatting minute in m format like 1,2 etc.
        6.12.44.	Format minutes in mm format like 01, 02 etc.
        6.12.45.	Format month in M format like 1,2 etc
        6.12.46.	Format Month in MM format like 01, 02 etc.
        6.12.47.	Format Month in MMM format like Jan, Feb etc.
        6.12.48.	Format Month in MMMM format like January, February etc.
        6.12.49.	Format seconds in s format like 1,2 etc.
        6.12.50.	Format seconds in ss format like 01, 02 etc.
        6.12.51.	Format TimeZone in z (General time zone) format like EST.
        6.12.52.	Format TimeZone in zzzz format Eastern Standard Time.
        6.12.53.	Format TimeZone in Z (RFC 822) format like -8000.
        6.12.54.	Format year in yy format like 07, 08 etc
        6.12.55.	Format year in yyyy format like 2007, 2008 etc.
        6.12.56.	Parsing custom formatted date string into Date object using SimpleDateFormat
        6.12.57.	Date Formatting and Localization
        6.12.58.	Add AM PM to time using SimpleDateFormat
        6.12.59.	Check if a String is a valid date
         */
    }
}
