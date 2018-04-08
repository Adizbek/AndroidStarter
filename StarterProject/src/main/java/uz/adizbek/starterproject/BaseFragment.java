package uz.adizbek.starterproject;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by adizbek on 2/18/18.
 */

public abstract class BaseFragment extends Fragment implements BaseFragmentListener {
    public BaseActivity activity;

    private View errorLayout;
    private View errorNoResultLayout = null;
    private boolean errorLayoutShown = false;
    private boolean errorNoResultShown = false;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        this.activity = (BaseActivity) activity;
    }

    public void setTitle(String title) {
        activity.setTitle(title);
    }

    @Override
    public void onFragmentEnter() {
//        showErrorLayout();
    }


    @Override
    public void onFragmentExit() {
//        hideErrorLayout();
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

        errorLayout = getLayoutInflater().inflate(R.layout.layout_network_error, null);
        errorLayout.findViewById(R.id.reconnect)
                .setOnClickListener(v -> onNetworkRetry(code));

        getErrorContainerView().addView(errorLayout, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        errorLayoutShown = true;
    }

    @Override
    public void onNetworkRetry(int code) {

    }

    @Override
    public void onNetworkRetrySuccess(int code) {
        removeErrorLayout();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        removeErrorLayout();
    }

    private void removeErrorLayout() {
        if (!errorLayoutShown) return;

        getErrorContainerView()
                .removeView(errorLayout);

        errorLayoutShown = false;
    }

    private void showErrorLayout() {
        if (!errorLayoutShown) return;

        errorLayout.setVisibility(View.VISIBLE);
    }

    private void hideErrorLayout() {
        if (!errorLayoutShown) return;

        errorLayout.setVisibility(View.GONE);
    }


    /**
     * @return returns true if handled by fragment
     */
    public boolean onBackPressed() {
        return false;
    }
}
