package webit.bthereapp.General;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;

import webit.bthereapp.Connection.ConnectionUtils;

/**
 * Created by User on 29/09/2016.
 */

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

    public DownloadImageTask() {
    }

    protected Bitmap doInBackground(String... urls) {
        String url = urls[0];
        if (ConnectionUtils.isLive) Log.d("SeServer", "DownloadImageTask url: " + url);
        Bitmap mIcon11 = null;
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPurgeable = true;
            options.inInputShareable = true;
            options.inJustDecodeBounds = false;
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            InputStream in = new java.net.URL(url).openStream();
            mIcon11 = BitmapFactory.decodeStream(in, null, options);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
    }
}
