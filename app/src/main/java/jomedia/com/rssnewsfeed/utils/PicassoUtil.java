package jomedia.com.rssnewsfeed.utils;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class PicassoUtil {
    private Context context;

    public PicassoUtil(Context context) {
        this.context = context;
    }

    public void loadImage(ImageView view, String url) {
        Picasso.with(context)
                .load(url)
                .resize(150, 100)
                .into(view);
    }
}
