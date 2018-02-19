package com.github.adizbek.starter;

import android.app.Activity;
import android.os.Bundle;

import uz.adizbek.starterproject.BaseActivity;
import uz.adizbek.starterproject.fragment.ImageViewerContainerFragment;
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

//        addF(new ImageViewerContainerFragment()
//                .addImages("https://4pda.uz/uploads/fotos/news/01-02-18/010218023757.jpg")
//                .addImages("http://img.4pda.uz/go/725fec8e11fafc6f0bd0609c25a999d3"));

        addFragmentToStack(new PlusOneFragment());
        addFragmentToStack(new PlusOneFragment2());
    }


}
