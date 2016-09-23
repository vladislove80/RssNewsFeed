package jomedia.com.rssnewsfeed.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import jomedia.com.rssnewsfeed.NewsFeedItem;

public class NewsFeedAdapter extends RecyclerView.Adapter<NewsFeedAdapter.ViewHolder>{
    private Context mContext;
    private List<NewsFeedItem> mItemList;

    public NewsFeedAdapter(Context context, List<NewsFeedItem> itemList) {
        mContext = context;
        mItemList = itemList;
    }

    @Override
    public NewsFeedAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(NewsFeedAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View view) {
            super(view);
        }
    }
}
