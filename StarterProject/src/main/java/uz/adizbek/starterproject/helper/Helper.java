package uz.adizbek.starterproject.helper;

import android.text.format.DateFormat;

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
}
