package jomedia.com.rssnewsfeed.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import jomedia.com.rssnewsfeed.RssAplication;
import jomedia.com.rssnewsfeed.data.callback.NewsCallback;
import jomedia.com.rssnewsfeed.data.models.NewsFeedItemModel;
import jomedia.com.rssnewsfeed.ui.adapters.NewsFeedAdapter;
import jomedia.com.rssnewsfeed.ui.adapters.NewsFeedInteractor;
import jomedia.com.rssnewsfeed.utils.Utils;

public class NewsFeedFragment extends BaseFragment implements NewsFeedInteractor{

    private List<NewsFeedItemModel> mNewsFeedItemModelList;
    private NewsFeedAdapter mNewsFeedAdapter;
    private OnNewsSelectedListener onNewsSelectedListener;
    private SwipeRefreshLayout swipeRefreshLayout;
    private boolean isSwipeRefresh = false;

    public NewsFeedFragment() {}

    public static NewsFeedFragment getInstance() {
        return new NewsFeedFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNewsFeedItemModelList = new ArrayList<>();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (mNewsFeedItemModelList.size() != 0) {
            hideProgressBar();
        } else {
            initModel();
        }

        swipeRefreshLayout = new SwipeRefreshLayout(getContext());
        SwipeRefreshLayout.LayoutParams swipeRefreshLayoutParams = new SwipeRefreshLayout.LayoutParams(
                SwipeRefreshLayout.LayoutParams.MATCH_PARENT,
                SwipeRefreshLayout.LayoutParams.WRAP_CONTENT
        );
        swipeRefreshLayout.setLayoutParams(swipeRefreshLayoutParams);
        swipeRefreshLayout.addView(setRecyclerView());
        swipeRefreshLayout.setOnRefreshListener(() -> {
            isSwipeRefresh = true;
            initModel();
        });
        addViewInContainer(swipeRefreshLayout);
    }

    private void initModel() {
        Log.d(Utils.LOG, "NewsFeedFragment -> initModel()");
        RssAplication.getNewsRepository().getNewsItems(new NewsCallback<List<NewsFeedItemModel>>() {
            @Override
            public void onEmit(List<NewsFeedItemModel> data) {
                Log.d(Utils.LOG, "NewsFeedFragment -> initModel -> onEmit: data.size = " + data.size());
                mNewsFeedAdapter.notifyNewsFeedAdapter(data);
            }

            @Override
            public void onCompleted(boolean isOffLine) {
                Toast.makeText(getContext(), "News loaded " + ((isOffLine)? "offline":"successfully"), Toast.LENGTH_SHORT).show();
                stopProgressViewOrSwipeRefresh(isSwipeRefresh);
                isSwipeRefresh = false;
            }

            @Override
            public void onError(Throwable throwable) {
                Toast.makeText(getContext(), "getPosts error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private RecyclerView setRecyclerView() {
        RecyclerView mRecyclerView = new RecyclerView(getContext());
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mRecyclerView.setLayoutParams(layoutParams);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mNewsFeedAdapter = new NewsFeedAdapter(getContext(), mNewsFeedItemModelList, this);
        mRecyclerView.setAdapter(mNewsFeedAdapter);
        return mRecyclerView;
    }

    private void stopProgressViewOrSwipeRefresh(boolean swipeRefresh){
        if (swipeRefresh) {
            swipeRefreshLayout.setRefreshing(false);
        } else {
            hideProgressBar();
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

    public interface OnNewsSelectedListener {
        void onNewsSelected(String link);
    }
}
