package com.example.www.rssreader.Services;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.net.URL;

public class GetImageFromUrlAsync extends AsyncTask<String, Integer, Bitmap> {

    ImageView _imageView;

    public GetImageFromUrlAsync(ImageView imageView) {

        _imageView = imageView;
        imageView.setImageDrawable(null);
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        try {
            if(!params[0].isEmpty()) {
                URL url = new URL(params[0]);
                Bitmap bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                return bitmap;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        super.onPostExecute(result);
        if (result != null) {
            _imageView.setImageBitmap(result);
        }
    }
}
