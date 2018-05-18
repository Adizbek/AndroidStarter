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

public class BaseActivity extends AppCompatActivity implements FragmentStackListener {
    public FragmentManager manager;
    public BaseFragment currentFragment = null;

    public ArrayList<FragmentStack> stacks = new ArrayList<>();


    public int getFrame() {
        return 0;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        manager = getSupportFragmentManager();
    }

    public void setTitle(String text) {
        getSupportActionBar().setTitle(text);
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


    public void setToolbarCustomView(View v) {
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(v);
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
        add(false, f, backstack, tag, true);
    }

    public void replaceFragmentToStack(BaseFragment fragment) {
        replaceFragmentToStack(fragment, null);
    }

    public void replaceFragmentToStack(BaseFragment f, String backstack) {
        replaceFragmentToStack(f, backstack, null);
    }

    public void replaceFragmentToStack(BaseFragment f, String backstack, String tag) {
        add(true, f, backstack, tag, true);
    }

    public void replaceFragmentToStack(BaseFragment f, String backstack, String tag, boolean save) {
        add(true, f, backstack, tag, save);
    }

    // TODO last back is wrong
    public void add(boolean replace, BaseFragment f, String backstack, String tag, boolean save) {
        if (currentFragment == f)
            return;

        boolean alreadyAdded = false;

        if (tag != null) {
            BaseFragment tagFragment = (BaseFragment) manager.findFragmentByTag(tag);

            if (tagFragment != null && currentFragment != tagFragment) {
                f = tagFragment;
                alreadyAdded = true;
            } else if (tagFragment == currentFragment && tagFragment != null) {
                // Return if current tagFragment is present
                return;
            }
        }

        BaseFragment out = null;

        // look up for fragment. if exists just show
        if (!alreadyAdded) {
            for (FragmentStack fragment : stacks) {
                if (fragment.in == f) {
                    alreadyAdded = true;
                    save = false;
                }
            }
        }

        FragmentTransaction t = manager.beginTransaction();
        t.setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out, R.anim.slide_left_in, R.anim.slide_right_out);


        if (stacks.size() > 0) {
            BaseFragment fragment = stacks.get(stacks.size() - 1).in;

            fragment.onFragmentExit();
            out = fragment;
            t.hide(fragment);
        }

//        if (save)
//            fragments.add(f);
//        onStackChanged();

        if (alreadyAdded) {
            t.show(f);
        } else if (replace) {
            t.replace(getFrame(), f, tag);
        } else {
            t.add(getFrame(), f, tag);
        }

        f.stack = backstack;

        t.addToBackStack(backstack);
        t.commitAllowingStateLoss();
        manager.executePendingTransactions();

        stacks.add(new FragmentStack(f, out));

        currentFragment = f;
        f.onFragmentEnter();
    }

    public BaseFragment popFragmentInStack(String stack) {
        for (int i = stacks.size() - 1; i >= 0; i--) {
            if (stacks.get(i).in.stack != null && stacks.get(i).in.stack.equals(stack)) {
                return stacks.get(i).in;
            }
        }

        return null;
    }

    public boolean popBackStack() {
        if (stacks.size() > 1) {
            int index = stacks.size() - 1;
            FragmentStack f = stacks.get(index);

            removeFragment(f.in, f.out);
            stacks.remove(index);

            return true;
        }

        return false;
    }

    public void removeFragment(BaseFragment remove, BaseFragment show) {
        remove.onFragmentExit();

        FragmentTransaction t = manager.beginTransaction();
        t.setCustomAnimations(R.anim.slide_left_in, R.anim.slide_right_out);
        t.hide(remove);
        manager.popBackStack();


        if (show != null) {
            t.show(show);
            show.onFragmentEnter();
        }

        t.commit();

        currentFragment = show;
    }

    public void changeFragment(BaseFragment fragment) {
        FragmentTransaction t = manager.beginTransaction();
        t.setCustomAnimations(R.anim.slide_left_out, R.anim.slide_right_in, R.anim.slide_right_out, R.anim.slide_left_in);

        if (currentFragment != null) {
            currentFragment.onFragmentExit();
        }

        t.replace(getFrame(), fragment)
                .commitAllowingStateLoss();
        manager.executePendingTransactions();

        fragment.onFragmentEnter();

        currentFragment = fragment;
    }

    public String getCurrentFragmentStackName() {
        return currentFragment.stack;
    }

    @Override
    public void onBackPressed() {
        if (currentFragment != null && currentFragment.onBackPressed())
            return;

        if (stacks.size() == 1) {
            finish();
            return;
        }

        if (!popBackStack()) {
            super.onBackPressed();
        }
    }
//            BaseFragment f = popFragmentInStack(stack);

    @Override
    public void onStackChanged() {

    }

}
