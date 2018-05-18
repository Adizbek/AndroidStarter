package uz.adizbek.starterproject.helper;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Collections;

import uz.adizbek.starterproject.Application;

/**
 * Created by adizbek on 2/21/18.
 */

public class Favorite {
    private static String key = "favorites";
    private static boolean load = false;
    private static ArrayList<String> favorites;

    public static String getAllFromPrefs() {
        return Application.prefs.readString(key, null);
    }

    public static String getAllCurrent() {
        return TextUtils.join(",", favorites);
    }

    public static void addFav(String string) {
        load();

        if (!favorites.contains(string)) {
            favorites.add(string);
        }

    }

    public static void toggleFav(String string) {
        if (isFav(string)) {
            removeFav(string);
        } else {
            addFav(string);
        }
    }

    public static void removeFav(String string) {
        load();

        favorites.remove(string);
    }

    public static boolean isFav(String string) {
        load();

        return favorites.contains(string);
    }

    public static void load() {
        if (!load) {
            String list = getAllFromPrefs();

            favorites = new ArrayList<>();

            if (list != null) {
                Collections.addAll(favorites, list.split(","));
            }

            load = true;
        }
    }

    public static void save() {
        String list[] = new String[favorites.size()];

        for (int i = 0; i < favorites.size(); i++) {
            list[i] = favorites.get(i);
        }

        String result = TextUtils.join(",", list);
        Application.prefs.saveString(key, result);
    }
}
