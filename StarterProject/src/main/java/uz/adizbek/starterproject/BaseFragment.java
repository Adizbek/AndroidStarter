package uz.adizbek.starterproject;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import uz.adizbek.starterproject.api.ReqQueue;
import uz.adizbek.starterproject.event.NetworkStateEvent;

/**
 * Created by adizbek on 2/18/18.
 */

public abstract class BaseFragment extends Fragment implements BaseFragmentListener {
    public BaseActivity activity;

    public ReqQueue reqQueue;
    private View errorLayout;
    private View errorNoResultLayout = null;
    private boolean errorLayoutShown = false;
    private boolean errorNoResultShown = false;

    public String stack;

    public String TAG = getClass().getCanonicalName();

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        this.reqQueue = new ReqQueue();
        this.activity = (BaseActivity) activity;

        Log.d("FRAGMENTS", "onAttach() returned: " + getClass().getCanonicalName());
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDetach() {
        EventBus.getDefault().unregister(this);

        Log.d(TAG, "detached() returned: ");

        super.onDetach();
    }

    public void setTitle(String title) {
        activity.setTitle(title);
    }

    @Override
    public void onFragmentEnter() {
        activity.cleanToolbar();
        Log.d(TAG, "onStart() onFragmentEnter: ");
//        showErrorLayout();
    }


    @Override
    public void onFragmentExit() {
        Log.d(TAG, "onStart() onFragmentExit: ");
//        hideErrorLayout();
    }


    @Override
    public void onStart() {
        super.onStart();

        Log.d(TAG, "onStart() returned: ");
    }

    @Override
    public void onStop() {
        super.onStop();

        Log.d(TAG, "onStop() returned: ");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNetworkStateEvent(NetworkStateEvent event) {
        Toast.makeText(activity, "Network: ".concat(event.connected ? "Yes" : "No"), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showNoResult() {
        if (errorNoResultLayout != null) {
            errorNoResultLayout.setVisibility(View.VISIBLE);
        } else {
            errorNoResultLayout = getLayoutInflater().inflate(getNoResultLayout(), null);

            getErrorContainerView().addView(errorNoResultLayout, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }

        errorNoResultShown = true;
    }

    @Override
    public void hideNoResult() {
        if (errorNoResultShown) {
            errorNoResultLayout.setVisibility(View.GONE);
            errorLayoutShown = false;
        }
    }

    public boolean useNoResult() {
        return false;
    }

    public int getNoResultLayout() {
        return 0;
    }

    public boolean useNetworkError() {
        return false;
    }

    public ViewGroup getErrorContainerView() {
        return null;
    }

    @Override
    public void onNetworkError(int code) {
        if (errorLayoutShown) return;

        if (errorLayout == null) {
            errorLayout = getLayoutInflater().inflate(R.layout.layout_network_error, null);
            errorLayout.findViewById(R.id.reconnect)
                    .setOnClickListener(v -> onNetworkRetry(code));

            getErrorContainerView().addView(errorLayout, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        } else {
            showErrorLayout();
        }

        errorLayoutShown = true;
    }

//    private void removeErrorLayout() {
//        if (!errorLayoutShown) return;
//
//        getErrorContainerView()
//                .removeView(errorLayout);
//
//        errorLayoutShown = false;
//    }

    private void showErrorLayout() {
        if (!errorLayoutShown) return;

        errorLayout.setVisibility(View.VISIBLE);
    }

    private void hideErrorLayout() {
        if (!errorLayoutShown) return;

        errorLayout.setVisibility(View.GONE);
    }

    @Override
    public void onNetworkRetry(int code) {

    }

    @Override
    public void onNetworkRetrySuccess(int code) {
        hideErrorLayout();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        removeErrorLayout();
    }


    /**
     * @return returns true if handled by fragment
     */
    public boolean onBackPressed() {
        return false;
    }
}
