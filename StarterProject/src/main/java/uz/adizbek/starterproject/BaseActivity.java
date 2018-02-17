package uz.adizbek.starterproject;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by adizbek on 2/9/18.
 */

public class BaseActivity extends AppCompatActivity {
    public FragmentManager manager;


    public int getFrame() {
        return 0;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        manager = getSupportFragmentManager();
    }

    public static void setToolbar(Activity activity, String title, boolean upEnabled, boolean showHome, boolean customView, View view) {
        if (activity instanceof AppCompatActivity) {
            ((AppCompatActivity) activity).getSupportActionBar().setHomeButtonEnabled(showHome);
            ((AppCompatActivity) activity).getSupportActionBar().setDisplayHomeAsUpEnabled(upEnabled);


            ((AppCompatActivity) activity).getSupportActionBar().setDisplayShowCustomEnabled(customView);
            ((AppCompatActivity) activity).getSupportActionBar().setCustomView(customView ? view : null);

            ((AppCompatActivity) activity).getSupportActionBar().setTitle(title);
        }
    }

    public static void setToolbar(Activity activity, String title, boolean upEnabled, boolean showHome) {
        setToolbar(activity, title, upEnabled, showHome, false, null);
    }

    public void addFragmentToStack(Fragment fragment) {
        addFragmentToStack(fragment, null);
    }

    public void changeFragment(Fragment fragment) {
        FragmentTransaction t = manager.beginTransaction();

        t.setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out, R.anim.slide_left_in, R.anim.slide_right_out);
        t
                .replace(getFrame(), fragment)
                .commit();

    }

    public void addFragmentToStack(Fragment f, String backstack) {
        addFragmentToStack(f, backstack, null);
    }

    public void addFragmentToStack(Fragment f, String backstack, String tag) {
        if (tag != null) {
            Fragment tf = manager.findFragmentByTag(tag);

            if (tf != null) return;
        }

        FragmentTransaction t = manager.beginTransaction();
        t.setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out, R.anim.slide_left_in, R.anim.slide_right_out);

        t
                .replace(getFrame(), f, tag)
                .addToBackStack(backstack)
                .commit();
    }

    public void removeFragment(Fragment f) {
        manager.beginTransaction().remove(f).commit();
    }

}