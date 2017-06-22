package jomedia.com.rssnewsfeed.ui.navigationdrawer;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import jomedia.com.rssnewsfeed.R;

public class DrawerItemCustomAdapter extends ArrayAdapter<String> {
    private Context mContext;
    private int resource;
    private String objects[] = null;

    public DrawerItemCustomAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull String[] objects) {
        super(context, resource, objects);
        this.resource = resource;
        this.mContext = context;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;

        LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
        listItem = inflater.inflate(resource, parent, false);

        //ImageView imageViewIcon = (ImageView) listItem.findViewById(R.id.imageViewIcon);
        TextView textViewName = (TextView) listItem.findViewById(R.id.textViewName);

        String item = objects[position];
        textViewName.setText(item);

        return listItem;
    }
}
