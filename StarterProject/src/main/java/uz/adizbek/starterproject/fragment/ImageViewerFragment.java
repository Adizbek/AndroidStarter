package uz.adizbek.starterproject.fragment;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.RequestCreator;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

import uz.adizbek.starterproject.Application;
import uz.adizbek.starterproject.BaseFragment;
import uz.adizbek.starterproject.R;


/**
 * Created by adizbek on 11/17/17.
 */

public class ImageViewerFragment extends BaseFragment {

    private String url;
    private RelativeLayout root;
    private ImageSource type;

    @DrawableRes
    private int drawable;

    private Bitmap bitmap;
    private File file;
    private Uri uri;
    private Thread tr;


    private boolean isSingle = false;

    private boolean forceHide = false;

    @Override
    public void onFragmentEnter() {
        super.onFragmentEnter();

        if (activity.getSupportActionBar() != null && activity.getSupportActionBar().isShowing()) {
            activity.getSupportActionBar().hide();
            forceHide = true;
        }
    }

    @Override
    public void onFragmentExit() {
        super.onFragmentExit();

        if (forceHide && activity.getSupportActionBar() != null && !activity.getSupportActionBar().isShowing()) {
            activity.getSupportActionBar().show();
        }
    }

    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (isSingle) {
            view.findViewById(uz.adizbek.starterproject.R.id.closePreview).setOnClickListener(v -> activity.popBackStack());
        } else {
            view.findViewById(uz.adizbek.starterproject.R.id.closePreview).setVisibility(View.GONE);
        }

        RequestCreator creator = null;
        if (type == ImageSource.URL) {
            creator = Application
                    .pic
                    .load(url);
        } else if (type == ImageSource.DRAWABLE) {
            creator = Application.pic.load(drawable);
        } else if (type == ImageSource.FILE) {
            creator = Application.pic.load(file);
        } else if (type == ImageSource.URI) {
            creator = Application.pic.load(uri);
        }

        if (creator == null && type != ImageSource.BITMAP) return;

        RequestCreator finalCreator = creator;

        tr = new Thread(() -> {
            try {
                Bitmap src = (type == ImageSource.BITMAP) ? bitmap : finalCreator.get();

                float dt = 600 / src.getWidth();
                int height = (int) (src.getHeight() * dt);

                if (activity != null) {
                    activity.runOnUiThread(() -> finalCreator.resize(1000, (int) (height * dt))
                            .onlyScaleDown()
                            .into((PhotoView) root.findViewById(R.id.photo)));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        tr.start();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        tr.interrupt();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = (RelativeLayout) inflater.inflate(R.layout.fragment_image_viewer, null);
        return root;
    }

    public ImageViewerFragment withUrl(String url) {
        this.url = url;
        type = ImageSource.URL;

        return this;
    }


    public ImageViewerFragment withDrawable(@DrawableRes int draw) {
        drawable = draw;
        type = ImageSource.DRAWABLE;

        return this;
    }

    public ImageViewerFragment withBitmap(Bitmap draw) {
        bitmap = draw;
        type = ImageSource.BITMAP;

        return this;
    }


    public ImageViewerFragment withFile(File f) {
        file = f;
        type = ImageSource.FILE;

        return this;
    }

    public ImageViewerFragment withUri(Uri f) {
        uri = f;
        type = ImageSource.URL;

        return this;
    }


    public ImageViewerFragment setSingle(boolean single) {
        isSingle = single;
        return this;
    }


    enum ImageSource {
        URL, DRAWABLE, BITMAP, FILE, URI
    }

}
