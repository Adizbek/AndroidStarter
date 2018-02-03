package uz.adizbek.starterproject.helper;

import uz.adizbek.starterproject.Application;
import com.google.firebase.iid.FirebaseInstanceId;

/**
 * Created by adizbek on 1/15/18.
 */

public class LoginHelper {
    public static final String TOKEN = "token";
    public static final String USER_ID = "user_id";
    public static final String FIREBASE_TOKEN = "firebase_token";

    public static boolean hasToken() {
        return Application.prefs.readString(TOKEN, null) != null;
    }

    public static void saveToken(String token) {
        Application.prefs.saveString(TOKEN, token);
    }

    public static String getToken() {
        return Application.prefs.readString(TOKEN, null);
    }

    public static void saveFirebaseToken(String token) {
        Application.prefs.saveString(FIREBASE_TOKEN, token);

        if (isUser()) {
            updateRemoteUserToken();
        }
    }

    public static String getFirebaseToken() {
        if (Application.prefs.readString(FIREBASE_TOKEN, null) != null) {
            return Application.prefs.readString(FIREBASE_TOKEN, null);
        }

        return FirebaseInstanceId.getInstance().getToken();
    }

    private static void updateRemoteUserToken() {

    }

    public static int getUserId() {
        return Application.prefs.readInt(USER_ID, -1);
    }

    public static void saveUserId(String id) {
        saveUserId(Integer.parseInt(id));
    }

    public static void saveUserId(int id){
        Application.prefs.saveInt(USER_ID, id);
    }


    public static boolean isUser() {
        return getUserId() != -1;
    }

    public static void removeUser() {
        saveUserId("-1");
        saveToken(null);
    }
}
