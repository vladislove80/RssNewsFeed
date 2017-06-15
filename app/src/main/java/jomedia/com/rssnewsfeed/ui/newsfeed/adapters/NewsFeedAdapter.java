package jomedia.com.rssnewsfeed.ui.newsfeed.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import jomedia.com.rssnewsfeed.R;
import jomedia.com.rssnewsfeed.data.models.NewsFeedItemModel;
import jomedia.com.rssnewsfeed.ui.newsfeed.view.NewsFeedInteractor;
import jomedia.com.rssnewsfeed.utils.PicassoUtil;

public class NewsFeedAdapter
        extends RecyclerView.Adapter<NewsFeedAdapter.ItemViewHolder>
        implements View.OnClickListener {

    @NonNull
    private Context mContext;
    @NonNull
    private List<NewsFeedItemModel> mItemList;
    @NonNull
    private PicassoUtil mPicassoManager;
    @NonNull
    private NewsFeedInteractor mInteractor;

    public NewsFeedAdapter(Context context, List<NewsFeedItemModel> itemList, NewsFeedInteractor interactor) {
        mContext = context;
        mItemList = itemList;
        mInteractor = interactor;
        mPicassoManager = new PicassoUtil(mContext);
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
        NewsFeedItemModel item = mItemList.get(position);
        mPicassoManager.loadImage(holder.itemImage, item.getImageLink());
        holder.itemTitle.setText(item.getTitle());
        holder.itemDescription.setText(item.getDescription());
        holder.itemDate.setText(item.getDate());
        holder.itemAuthor.setText(item.getAuthor());
        holder.setOnClickListener(position, this);
    }

    @Override
    public int getItemCount() {
        return mItemList == null ? 0 : mItemList.size();
    }

    public void notifyNewsFeedAdapter(List<NewsFeedItemModel> itemList){
        mItemList.clear();
        mItemList.addAll(itemList);
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();
        String link = mItemList.get(position).getNewsLink();
        mInteractor.OnNewsClick(link);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView itemImage;
        private TextView itemTitle;
        private TextView itemDescription;
        private TextView itemDate;
        private TextView itemAuthor;
        private CardView cardView;

        public ItemViewHolder(View view) {
            super(view);
            itemImage = (ImageView) view.findViewById(R.id.image);
            itemTitle = (TextView) view.findViewById(R.id.title);
            itemDescription = (TextView) view.findViewById(R.id.description);
            itemDate = (TextView) view.findViewById(R.id.pub_date);
            itemAuthor = (TextView) view.findViewById(R.id.author);
            cardView = (CardView) view.findViewById(R.id.card_view);
        }

        void setOnClickListener(int position, View.OnClickListener onClickListener) {
            cardView.setTag(position);
            cardView.setOnClickListener(onClickListener);
        }
    }

}
