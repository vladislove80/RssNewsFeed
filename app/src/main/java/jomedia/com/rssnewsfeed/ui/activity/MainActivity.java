package jomedia.com.rssnewsfeed.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import jomedia.com.rssnewsfeed.data.model.NewsFeedItem;
import jomedia.com.rssnewsfeed.R;
import jomedia.com.rssnewsfeed.utils.Utils;
import jomedia.com.rssnewsfeed.data.api.ApiManager;
import jomedia.com.rssnewsfeed.data.model.Item;
import jomedia.com.rssnewsfeed.data.model.RssModel;
import jomedia.com.rssnewsfeed.ui.adapter.NewsFeedAdapter;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class MainActivity extends AppCompatActivity {
    private final CompositeSubscription mSubscriptions = new CompositeSubscription();
    private List<Item> mRssItemList;
    List<NewsFeedItem> mNewsFeedItemList;

    private RecyclerView mRecyclerView;
    private NewsFeedAdapter mNewsFeedAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRssItemList = new ArrayList<>();
        mNewsFeedItemList = new ArrayList<>();

        loadData();

        mRecyclerView = (RecyclerView) findViewById(R.id.news_feed);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        mNewsFeedAdapter = new NewsFeedAdapter(this, mNewsFeedItemList);
        mRecyclerView.setAdapter(mNewsFeedAdapter);
    }

    private void loadData() {
        Subscription subscription = ApiManager.getRssModel()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onDataSuccess, this::onDataError);
        mSubscriptions.add(subscription);
    }

    private void onDataError(Throwable throwable) {
        Log.d(Utils.LOG, "onDataError " + throwable.toString());
    }

    private void onDataSuccess(RssModel rssModel) {
        if (rssModel != null) {
            mRssItemList = rssModel.getChannel().getItems();
            Log.d(Utils.LOG, "onDataSuccess ");
            mNewsFeedItemList = Utils.getNewsFeedItems(mRssItemList);
            mNewsFeedAdapter.notifyNewsFeedAdapter(mNewsFeedItemList);
        }
    }

    @Override
    public void onDestroy() {
        mSubscriptions.clear();
        super.onDestroy();
    }

}
