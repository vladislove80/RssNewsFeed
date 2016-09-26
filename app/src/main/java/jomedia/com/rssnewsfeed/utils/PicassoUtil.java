package jomedia.com.rssnewsfeed.utils;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import jomedia.com.rssnewsfeed.R;

public class PicassoUtil {
    private Context context;

    public PicassoUtil(Context context) {
        this.context = context;
    }

    public void loadImage(ImageView view, String url) {
        Picasso.with(context)
                .load(url)
                .resize(150, 100)
                .placeholder(R.drawable.placeholder_150)
                .into(view);
    }
}
