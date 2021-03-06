package uz.adizbek.starterproject;

/**
 * Created by adizbek on 2/21/18.
 */

public interface BaseFragmentListener {
    void onFragmentEnter();

    void onFragmentExit();

    void showNoResult();

    void hideNoResult();

    void onNetworkError(int code);

    void onNetworkRetry(int code);

    void onNetworkRetrySuccess(int code);

}
