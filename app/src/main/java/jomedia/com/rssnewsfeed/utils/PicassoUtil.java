package jomedia.com.rssnewsfeed.utils;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import static jomedia.com.rssnewsfeed.R.drawable.no_image;

public class PicassoUtil {
    private Context context;

    public PicassoUtil(Context context) {
        this.context = context;
    }

    public void loadImage(ImageView view, String url) {
        Picasso.with(context)
                .load(url)
                .placeholder(no_image)
                .error(no_image)
                .fit()
                .centerCrop()
                .into(view);
    }
}
