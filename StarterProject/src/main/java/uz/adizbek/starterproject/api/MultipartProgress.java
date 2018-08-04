package uz.adizbek.starterproject.api;

import android.app.Activity;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;
import uz.adizbek.starterproject.R;

/**
 * Created by adizbek on 27.05.2018.
 */

public class MultipartProgress extends RequestBody {
    private File mFile;
    private String mPath;
    private Progress mListener;
    private int per = 0;

    private static final int DEFAULT_BUFFER_SIZE = 2048;
    private Activity context;


    public MultipartProgress(final File file, final Progress listener) {
        mFile = file;
        mListener = listener;
    }

    public MultipartProgress(final String file, final Progress listener) {
        mFile = new File(file);
        mListener = listener;
    }

    @Override
    public MediaType contentType() {
        // i want to upload only images
        return MediaType.parse("image/png");
    }

    @Override
    public long contentLength() throws IOException {
        return mFile.length();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        // TODO move it out of.

        long fileLength = mFile.length();
        byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
        FileInputStream in = new FileInputStream(mFile);
        long uploaded = 0;

        try {
            int read;


            while ((read = in.read(buffer)) != -1) {
                int tp = (int) (uploaded * 100 / fileLength);

                if (tp != per) {
                    per = tp;
                    mListener.update(per, uploaded, fileLength);
                }

                uploaded += read;
                sink.write(buffer, 0, read);
            }
        } finally {
            in.close();

            if (uploaded == fileLength) {
                mListener.update(100, uploaded, fileLength);
            }
        }
    }

    public interface Progress {
        void update(int percent, long uploaded, long fileLength);
    }

}
