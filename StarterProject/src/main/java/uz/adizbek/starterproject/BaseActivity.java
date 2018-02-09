package uz.adizbek.starterproject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by adizbek on 2/9/18.
 */

public class BaseActivity extends AppCompatActivity {
    public FragmentManager manager;

    public static int FRAME;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        manager = getSupportFragmentManager();
    }

    public void addFragmentToStack(Fragment fragment) {
        addFragmentToStack(fragment, null);
    }

    public void changeFragment(Fragment fragment) {
        FragmentTransaction t = manager.beginTransaction();

        t.setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out, R.anim.slide_left_in, R.anim.slide_right_out);
        t
                .replace(FRAME, fragment)
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
                .replace(FRAME, f, tag)
                .addToBackStack(backstack)
                .commit();
    }

    public void removeFragment(Fragment f) {
        manager.beginTransaction().remove(f).commit();
    }

}
