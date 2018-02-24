package uz.adizbek.starterproject;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.blankj.utilcode.util.Utils;
import com.google.firebase.FirebaseApp;
import com.squareup.picasso.Picasso;

import uz.adizbek.starterproject.helper.Favorite;
import uz.adizbek.starterproject.helper.PrefHelper;

/**
 * Created by adizbek on 1/14/18.
 */

public class Application extends MultiDexApplication {
    public static PrefHelper prefs;
    public static String apiHost = "https://api.github.com/";
    public static Picasso pic;
    public static Context c;

    public static String getHost() {
        return apiHost;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        MultiDex.install(this);
        c = getApplicationContext();

        init();
        initFirebase();
        initRetro();
        initPicasso();
    }

    protected void initFirebase() {
        FirebaseApp.initializeApp(this);
    }

    protected void init() {
        Utils.init(this);
        prefs = PrefHelper.init(this);

        Favorite.load();
    }

    protected void initPicasso() {
        pic = Picasso.with(this);
    }

    protected void initRetro() {
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(this.apiHost)
//                .build();

//        api = retrofit.create(ApiService.class);
    }
}
