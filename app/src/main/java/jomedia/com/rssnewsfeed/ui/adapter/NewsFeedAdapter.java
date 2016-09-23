package jomedia.com.rssnewsfeed.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import jomedia.com.rssnewsfeed.NewsFeedItem;
import jomedia.com.rssnewsfeed.PicassoUtil;
import jomedia.com.rssnewsfeed.R;

public class NewsFeedAdapter extends RecyclerView.Adapter<NewsFeedAdapter.ItemViewHolder>{
    private Context mContext;
    private List<NewsFeedItem> mItemList;
    private PicassoUtil picasso;

    public NewsFeedAdapter(Context context, List<NewsFeedItem> itemList) {
        mContext = context;
        mItemList = itemList;
        picasso = new PicassoUtil(mContext);
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_feed_item, parent, false);
        ItemViewHolder vh = new ItemViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        NewsFeedItem item = mItemList.get(position);
        picasso.loadImage(holder.itemImage, item.getImageLink());
        holder.itemTitle.setText(item.getTitle());
        holder.itemDate.setText(item.getDate());
        holder.itemAuthor.setText(item.getAuthor());
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    public void notifyNewsFeedAdapter(List<NewsFeedItem> itemList){
        mItemList.clear();
        mItemList.addAll(itemList);
        notifyDataSetChanged();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView itemImage;
        private TextView itemTitle;
        private TextView itemDate;
        private TextView itemAuthor;

        public ItemViewHolder(View view) {
            super(view);
            itemImage = (ImageView) view.findViewById(R.id.image);
            itemTitle = (TextView) view.findViewById(R.id.title);
            itemDate = (TextView) view.findViewById(R.id.pub_date);
            itemAuthor = (TextView) view.findViewById(R.id.author);
        }
    }
}