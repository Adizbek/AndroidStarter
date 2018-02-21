package uz.adizbek.starterproject.fragment;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.RequestCreator;

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

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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

        new Thread(() -> {
            try {
                Bitmap src = (type == ImageSource.BITMAP) ? bitmap : finalCreator.get();


                float dt = 600 / src.getWidth();
                int height = (int) (src.getHeight() * dt);

                getActivity()
                        .runOnUiThread(() -> finalCreator.resize(1000, (int) (height * dt))
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
        activity.getSupportActionBar().hide();

        root = (RelativeLayout) inflater.inflate(R.layout.fragment_image_viewer, null);
        return root;
    }

    @Override
    public void onDestroyView() {
        activity.getSupportActionBar().show();

        super.onDestroyView();
    }

    public BaseFragment withUrl(String url) {
        this.url = url;
        type = ImageSource.URL;

        return this;
    }


    public BaseFragment withDrawable(@DrawableRes int draw) {
        drawable = draw;
        type = ImageSource.DRAWABLE;

        return this;
    }

    public BaseFragment withBitmap(Bitmap draw) {
        bitmap = draw;
        type = ImageSource.BITMAP;

        return this;
    }


    public BaseFragment withFile(File f) {
        file = f;
        type = ImageSource.FILE;

        return this;
    }

    public BaseFragment withUri(Uri f) {
        uri = f;
        type = ImageSource.URL;

        return this;
    }


    enum ImageSource {
        URL, DRAWABLE, BITMAP, FILE, URI
    }

}
