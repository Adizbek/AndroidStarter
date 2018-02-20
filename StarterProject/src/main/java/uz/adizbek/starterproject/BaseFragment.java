package uz.adizbek.starterproject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;

/**
 * Created by adizbek on 2/18/18.
 */

public class BaseFragment extends Fragment implements BaseFragmentListener {
    public BaseActivity activity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!(getActivity() instanceof BaseActivity))
            Log.e("Adizbek/AndroidStarter", "Activity must extend BaseActivity");
        else activity = (BaseActivity) getActivity();
    }

    @Override
    public void onFragmentEnter() {

    }

    @Override
    public void onFragmentExit() {

    }
}
