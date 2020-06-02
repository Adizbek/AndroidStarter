package uz.adizbek.starterproject;

import android.content.Context;
import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.blankj.utilcode.util.Utils;
import com.squareup.picasso.LruCache;
import com.squareup.picasso.Picasso;

import uz.adizbek.starterproject.helper.Favorite;
import uz.adizbek.starterproject.helper.Helper;
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
        initRetro();
        initPicasso();
    }


    protected void init() {
        Utils.init(this);
        prefs = PrefHelper.init(this);

        Favorite.load();
        Helper.loadSavedLang();
    }

    protected void initPicasso() {
        pic = new Picasso.Builder(this)
                .memoryCache(new LruCache(1024)) // Maybe something fishy here?
                .build();

    }

    protected void initRetro() {
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(this.apiHost)
//                .build();

//        api = retrofit.create(ApiService.class);
    }
}
