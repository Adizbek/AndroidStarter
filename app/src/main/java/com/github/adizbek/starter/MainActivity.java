package com.github.adizbek.starter;

import android.app.Activity;
import android.os.Bundle;

import uz.adizbek.starterproject.BaseActivity;
import uz.adizbek.starterproject.fragment.ImageViewerFragment;

public class MainActivity extends BaseActivity {

    @Override
    public int getFrame() {
        return R.id.frame;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        changeFragment(new ImageViewerFragment().withUrl("https://4pda.uz/uploads/fotos/screen/23-10-17/7ab450fa46dc10caf2c60caa93b83fb5.jpg"));
    }


}
