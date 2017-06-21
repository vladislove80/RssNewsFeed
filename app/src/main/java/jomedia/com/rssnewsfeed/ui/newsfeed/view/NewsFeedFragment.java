package jomedia.com.rssnewsfeed.ui.newsfeed.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import jomedia.com.rssnewsfeed.data.models.NewsFeedItemModel;
import jomedia.com.rssnewsfeed.ui.BaseFragment;
import jomedia.com.rssnewsfeed.ui.newsfeed.adapters.NewsFeedAdapter;
import jomedia.com.rssnewsfeed.ui.newsfeed.presenter.NewsPresenter;
import jomedia.com.rssnewsfeed.utils.Utils;

public class NewsFeedFragment extends BaseFragment implements NewsView, NewsFeedInteractor{

    private List<NewsFeedItemModel> mNewsFeedItemModelList;
    private NewsFeedAdapter mNewsFeedAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private NewsPresenter presenter;
    String link;

    private boolean mAlreadyLoaded = false;

    public NewsFeedFragment() {}

    public static NewsFeedFragment getInstance() {
        return new NewsFeedFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        link = getArguments().getString("link to news");
        mNewsFeedItemModelList = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.v(Utils.LOG, "NewsFeedFragment -> onViewCreated: savedInstanceState == "
                + ((savedInstanceState == null)? "null" : "not null"));
        if (!mAlreadyLoaded) {
            getPresenter().loadNews(link);
            mAlreadyLoaded = true;
        }
        swipeRefreshLayout = new SwipeRefreshLayout(getContext());
        SwipeRefreshLayout.LayoutParams swipeRefreshLayoutParams = new SwipeRefreshLayout.LayoutParams(
                SwipeRefreshLayout.LayoutParams.MATCH_PARENT,
                SwipeRefreshLayout.LayoutParams.WRAP_CONTENT
        );
        swipeRefreshLayout.setLayoutParams(swipeRefreshLayoutParams);
        swipeRefreshLayout.addView(setRecyclerView());
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPresenter().loadNews(link);
            }});

        addViewInContainer(swipeRefreshLayout);
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

    @Override
    public void OnNewsClick(String link) {
        getPresenter().onNewsFeedItemClick(link);
    }

    @Override
    public void showProgress() {
        showProgressBar();
    }

    @Override
    public void hideProgress() {
        hideProgressBar();
    }

    @Override
    public void showNoDataMessage(String message) {
        showNoDataTextView(message);
    }

    @Override
    public void showNewsStatus(String status) {
        Toast.makeText(getContext(), status, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError(String errorMessage) {
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void bindPresenter(NewsPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public NewsPresenter getPresenter() {
        return presenter;
    }

    @Override
    public void showNews(@NonNull List<NewsFeedItemModel> models) {
        mNewsFeedAdapter.notifyNewsFeedAdapter(models);
        swipeRefreshLayout.setRefreshing(false);
    }
}
