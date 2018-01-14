package com.github.adizbek.starter;

import android.support.multidex.MultiDexApplication;

import com.github.adizbek.starter.api.ApiService;
import com.squareup.picasso.Picasso;

import retrofit2.Retrofit;

/**
 * Created by adizbek on 1/14/18.
 */

public class Application extends MultiDexApplication {
    ApiService api;
    String apiHost = "https://api.github.com/";
    Picasso pic;

    @Override
    public void onCreate() {
        super.onCreate();

        initRetro();
        initPicasso();
    }

    private void initPicasso() {
        pic = Picasso.with(this);
    }

    private void initRetro() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(this.apiHost)
                .build();

        api = retrofit.create(ApiService.class);
    }
}
