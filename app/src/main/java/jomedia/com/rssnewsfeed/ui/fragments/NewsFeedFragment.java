package jomedia.com.rssnewsfeed.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import jomedia.com.rssnewsfeed.data.api.ApiManager;
import jomedia.com.rssnewsfeed.data.models.Item;
import jomedia.com.rssnewsfeed.data.models.NewsFeedItem;
import jomedia.com.rssnewsfeed.data.models.RssModel;
import jomedia.com.rssnewsfeed.ui.adapters.NewsFeedAdapter;
import jomedia.com.rssnewsfeed.ui.adapters.NewsFeedInteractor;
import jomedia.com.rssnewsfeed.utils.Utils;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class NewsFeedFragment extends BaseFragment implements NewsFeedInteractor{

    private final CompositeSubscription mSubscriptions = new CompositeSubscription();
    private List<Item> mRssItemList;
    private List<NewsFeedItem> mNewsFeedItemList;
    private RecyclerView mRecyclerView;
    private NewsFeedAdapter mNewsFeedAdapter;
    private OnNewsSelectedListener onNewsSelectedListener;

    public NewsFeedFragment() {
    }

    public static NewsFeedFragment getInstance() {
        return new NewsFeedFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRssItemList = new ArrayList<>();
        mNewsFeedItemList = new ArrayList<>();
        loadData();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mRssItemList.size() != 0) {
            hideProgressBar();
        }
        addViewInContainer(setRecyclerView());
    }

    private void loadData() {
        Subscription subscription = ApiManager.getRssModel()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onDataSuccess, this::onDataError);
        mSubscriptions.add(subscription);
    }

    private RecyclerView setRecyclerView() {
        mRecyclerView = new RecyclerView(getContext());
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mRecyclerView.setLayoutParams(layoutParams);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        mNewsFeedAdapter = new NewsFeedAdapter(getContext(), mNewsFeedItemList, this);
        mRecyclerView.setAdapter(mNewsFeedAdapter);
        return mRecyclerView;
    }

    private void onDataError(Throwable throwable) {
        hideProgressBar();
        showNoDataTextView("Check connections !!");
        Log.d(Utils.LOG, "onDataError " + throwable.toString());
    }

    private void onDataSuccess(RssModel rssModel) {
        hideProgressBar();
        if (rssModel != null) {
            mRssItemList = rssModel.getChannel().getItems();
            Log.d(Utils.LOG, "onDataSuccess ");
            mNewsFeedItemList = Utils.getNewsFeedItems(mRssItemList);
            mNewsFeedAdapter.notifyNewsFeedAdapter(mNewsFeedItemList);
        }
    }

    @Override
    public void OnNewsClick(String link) {
        if (onNewsSelectedListener != null) {
            onNewsSelectedListener.onNewsSelected(link);
        }
    }

    public void setOnNewsSelectedListener(OnNewsSelectedListener onNewsSelectedListener) {
        this.onNewsSelectedListener = onNewsSelectedListener;
    }

    @Override
    public void onDestroy() {
        mSubscriptions.clear();
        super.onDestroy();
    }

    public interface OnNewsSelectedListener {
        void onNewsSelected(String link);
    }
}
