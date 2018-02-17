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

        changeFragment(new ImageViewerFragment().withDrawable(R.drawable.ic_perm_scan_wifi_red_700_24dp));
    }


}
