package uz.adizbek.starterproject;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by adizbek on 2/9/18.
 */

public class BaseActivity extends AppCompatActivity {
    public FragmentManager manager;
    public BaseFragment lastFragment = null;

    public ArrayList<BaseFragment> fragments = new ArrayList<>();
    public int backstack = 0;


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

    public void addFragmentToStack(BaseFragment fragment) {
        addFragmentToStack(fragment, null);
    }

    public void addFragmentToStack(BaseFragment f, String backstack) {
        addFragmentToStack(f, backstack, null);
    }

    public void addFragmentToStack(BaseFragment f, String backstack, String tag) {
        if (tag == null || manager.findFragmentByTag(tag) == null)
            add(false, f, backstack, tag);
    }

    public void replaceFragmentToStack(BaseFragment fragment) {
        replaceFragmentToStack(fragment, null);
    }

    public void replaceFragmentToStack(BaseFragment f, String backstack) {
        replaceFragmentToStack(f, backstack, null);
    }

    public void replaceFragmentToStack(BaseFragment f, String backstack, String tag) {
        if (tag == null || manager.findFragmentByTag(tag) == null)
            add(true, f, backstack, tag);
    }

    // TODO last back is wrong
    public void add(boolean replace, BaseFragment f, String backstack, String tag) {
        FragmentTransaction t = manager.beginTransaction();
        t.setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out, R.anim.slide_left_in, R.anim.slide_right_out);


        if (fragments.size() > 0) {
            BaseFragment fragment = fragments.get(fragments.size() - 1);
            fragment.onFragmentExit();

            t.hide(fragment);
        }

        fragments.add(f);
        f.onFragmentEnter();

        if (replace)
            t.replace(getFrame(), f, tag);
        else {
            t.add(getFrame(), f, tag);
        }

        t.addToBackStack(backstack)
                .commit();
    }

    public boolean popBackStack() {
        if (fragments.size() > 0) {
            int index = fragments.size() - 1;
            BaseFragment f = fragments.get(index);

            if (index - 1 >= 0) {
                removeFragment(f, fragments.get(index - 1));
            } else {
                removeFragment(f, null);
            }

            fragments.remove(index);


            return true;
        }

        return false;
    }

    public void removeFragment(BaseFragment remove, BaseFragment show) {
        FragmentTransaction t = manager.beginTransaction();
        t.setCustomAnimations(R.anim.slide_left_in, R.anim.slide_right_out);

        remove.onFragmentExit();

        if (show != null) {
            t.show(show);
            show.onFragmentEnter();
        }

        t.remove(remove)
                .commit();
    }

    public void changeFragment(BaseFragment fragment) {
        FragmentTransaction t = manager.beginTransaction();
        t.setCustomAnimations(R.anim.slide_left_out, R.anim.slide_right_in, R.anim.slide_right_out, R.anim.slide_left_in);

        t.replace(getFrame(), fragment)
                .commit();

    }

    @Override
    public void onBackPressed() {
        if (!popBackStack()) {
            super.onBackPressed();
        }
    }
}
