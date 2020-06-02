package uz.adizbek.starterproject;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
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

    public boolean bootstrapped = false;

    private boolean isLoading = withLoading();

    public ReqQueue reqQueue;
    private View errorLayout;
    private View errorNoResultLayout = null;
    private boolean errorLayoutShown = false;
    private boolean errorNoResultShown = false;

    private boolean mEntered = false;

    public String stack;

    public String TAG = getClass().getCanonicalName();
    protected View baseView;
    private View loading;

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
        BaseActivity.setToolbar(activity, title, true, false);
        BaseActivity.setToolbar(activity, title, false, false);
    }

    public void setTitle(@StringRes int title) {
        activity.setTitle(getString(title));
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    @Override
    public void onFragmentEnter() {
        if (activity != null && isAdded())
            activity.cleanToolbar();

        Log.d(TAG, "onStart() onFragmentEnter: ");

        if (baseView != null && loading != null && loading.getVisibility() != View.VISIBLE) {
            showLoading();
        }

//        showErrorLayout();
    }


    @Override
    public void onFragmentExit() {
        Log.d(TAG, "onStart() onFragmentExit: ");
//        hideErrorLayout();

        if (baseView != null && loading != null && loading.getVisibility() == View.VISIBLE) {
            hideLoading();
        }
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        baseView = view;

        if (withLoading()) {
            showLoading();
        }
    }


    /**
     * Show loading view for BaseFragment instance
     */
    public void showLoading() {
        if (baseView == null) {
            throw new NullPointerException("You should override onViewCreated and call super to use this method");
        }

        baseView.setVisibility(View.GONE);

        loading = getLayoutInflater().inflate(R.layout.loading_view, null);

        ((ViewGroup) baseView.getParent()).addView(loading);
    }

    /**
     * Hide loading view for BaseFragment instance
     */
    public void hideLoading() {
        try {

            baseView.setVisibility(View.VISIBLE);
            ((ViewGroup) baseView.getParent()).removeView(loading);
            loading = null;
        } catch (Exception e) {

        }
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

    @LayoutRes
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
        reqQueue.cancelAll();
        super.onDestroyView();
//        removeErrorLayout();
    }


    protected boolean withLoading() {
        return false;
    }

    /**
     * @return returns true if handled by fragment
     */
    public boolean onBackPressed() {
        return false;
    }


    public void showHomeTitle(String title) {
        BaseActivity.setToolbar(activity, title, true, true);
    }

    public void showHomeTitle(@StringRes int title) {
        showHomeTitle(getString(title));
    }

}
