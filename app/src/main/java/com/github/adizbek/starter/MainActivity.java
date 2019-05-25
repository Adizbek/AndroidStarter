package com.github.adizbek.starter;

import android.os.Bundle;

import uz.adizbek.starterproject.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    public int getFrame() {
        return R.id.frame;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


}
