package uz.adizbek.starterproject.fragment;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RelativeLayout;

import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.RequestCreator;

import java.io.IOException;

import uz.adizbek.starterproject.Application;
import uz.adizbek.starterproject.BaseActivity;
import uz.adizbek.starterproject.R;
import uz.adizbek.starterproject.helper.Helper;


/**
 * Created by adizbek on 11/17/17.
 */

public class ImageViewerFragment extends Fragment {

    private String url;
    private RelativeLayout root;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RequestCreator creator = Application
                .pic
                .load(url);


        new Thread(() -> {
            try {
                Bitmap bitmap = creator.get();

                float dt = 600 / bitmap.getWidth();
                int height = (int) (bitmap.getHeight() * dt);

                getActivity()
                        .runOnUiThread(() -> creator.resize(1000, (int) (height * dt))
                                .onlyScaleDown()
                                .into((PhotoView) root.findViewById(R.id.photo)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).getSupportActionBar().hide();
        }

        root = (RelativeLayout) inflater.inflate(R.layout.fragment_image_viewer, null);
        return root;
    }

    @Override
    public void onDestroyView() {
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).getSupportActionBar().show();
        }

        super.onDestroyView();
    }

    public Fragment withUrl(String url) {
        this.url = url;

        return this;
    }


}
