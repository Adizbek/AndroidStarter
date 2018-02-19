package uz.adizbek.starterproject.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import uz.adizbek.starterproject.BaseFragment;
import uz.adizbek.starterproject.R;

/**
 * Created by adizbek on 8/7/17.
 */

public class ImageViewerContainerFragment extends BaseFragment {

    ImageViewPager pager;
    PagerAdapter adapter;

    ArrayList<String> urls = new ArrayList<>();
    private int start = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity.getSupportActionBar().hide();

        RelativeLayout root = (RelativeLayout) inflater.inflate(R.layout.fragment_image_viewer_container, null);
        adapter = new ScreenSlidePagerAdapter(getChildFragmentManager());
        pager = root.findViewById(R.id.slider_pager);
        pager.setAdapter(adapter);
        pager.setCurrentItem(start);

        root.findViewById(R.id.btnFinish).setOnClickListener(view -> activity.removeFragment(this));

        return root;
    }

    public ImageViewerContainerFragment setStart(int start) {
        this.start = start;

        return this;
    }

    public ImageViewerContainerFragment addImages(String url) {
        urls.add(url);

        return this;
    }


    public ImageViewerContainerFragment addImages(ArrayList<String> urls) {
        this.urls.addAll(urls);

        return this;
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            return new ImageViewerFragment().withUrl(urls.get(position));
        }

        @Override
        public int getCount() {
            return urls.size();
        }
    }

    @Override
    public void onDestroyView() {
        activity.getSupportActionBar().show();

        super.onDestroyView();
    }

}
