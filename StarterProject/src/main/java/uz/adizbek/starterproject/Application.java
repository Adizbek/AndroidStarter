package uz.adizbek.starterproject;

import android.support.multidex.MultiDexApplication;

import com.blankj.utilcode.util.Utils;

import uz.adizbek.starterproject.helper.PrefHelper;

import com.google.firebase.FirebaseApp;
import com.squareup.picasso.Picasso;

import retrofit2.Retrofit;

/**
 * Created by adizbek on 1/14/18.
 */

public class Application extends MultiDexApplication {
    public static PrefHelper prefs;
    public static String apiHost = "https://api.github.com/";
    public static Picasso pic;

    @Override
    public void onCreate() {
        super.onCreate();

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
