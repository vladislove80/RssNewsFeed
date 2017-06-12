package jomedia.com.rssnewsfeed.ui.fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
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

import jomedia.com.rssnewsfeed.data.api.RestManager;
import jomedia.com.rssnewsfeed.data.models.Item;
import jomedia.com.rssnewsfeed.data.models.NewsFeedItemModel;
import jomedia.com.rssnewsfeed.data.models.RssModel;
import jomedia.com.rssnewsfeed.db.DatabaseConst;
import jomedia.com.rssnewsfeed.db.DatabaseHelper;
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
    private List<NewsFeedItemModel> mNewsFeedItemModelList;
    private RecyclerView mRecyclerView;
    private NewsFeedAdapter mNewsFeedAdapter;
    private OnNewsSelectedListener onNewsSelectedListener;
    private SQLiteDatabase database;
    private SwipeRefreshLayout swipeRefreshLayout;
    private boolean isSwipeRefresh = false;

    public NewsFeedFragment() {
    }

    public static NewsFeedFragment getInstance() {
        return new NewsFeedFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRssItemList = new ArrayList<>();
        mNewsFeedItemModelList = new ArrayList<>();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mRssItemList.size() != 0) {
            hideProgressBar();
        }
        swipeRefreshLayout = new SwipeRefreshLayout(getContext());
        SwipeRefreshLayout.LayoutParams swipeRefreshLayoutParams = new SwipeRefreshLayout.LayoutParams(
                SwipeRefreshLayout.LayoutParams.MATCH_PARENT,
                SwipeRefreshLayout.LayoutParams.WRAP_CONTENT
        );
        swipeRefreshLayout.setLayoutParams(swipeRefreshLayoutParams);
        swipeRefreshLayout.addView(setRecyclerView());
        addViewInContainer(swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            isSwipeRefresh = true;
            loadData();
            Toast.makeText(getContext(), "Обновление..", Toast.LENGTH_SHORT).show();
        });
        loadData();
    }

    private void loadData() {
        Log.d(Utils.LOG, "loadData");
        Subscription subscription = RestManager.getRssModel()
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

    private void onDataError(Throwable throwable) {
        stopProgressViewOrSwipeRefresh(isSwipeRefresh);
        Log.d(Utils.LOG, "onDataError " + throwable.toString());
        loadNewsFromBD();
    }

    private void onDataSuccess(RssModel rssModel) {
        Log.d(Utils.LOG, "onDataSuccess: rssModel not null = " + (rssModel != null));
        stopProgressViewOrSwipeRefresh(isSwipeRefresh);
        if (rssModel != null) {
            mRssItemList = rssModel.getChannel().getItems();
            mNewsFeedItemModelList = Utils.getNewsFeedItems(mRssItemList);
            saveNewsToBD(mNewsFeedItemModelList);
            mNewsFeedAdapter.notifyNewsFeedAdapter(mNewsFeedItemModelList);
            Toast.makeText(getContext(), "News loaded", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadNewsFromBD(){
        openDBForLoadData();
        Cursor cursor = database.query(DatabaseConst.TABLE.ITEMS, null, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            Log.d(Utils.LOG, "cursor != null. load from DB..");
            while (!cursor.isAfterLast()) {
                int id = cursor.getInt(cursor.getColumnIndex(DatabaseConst.ITEM_FIELDS.ID));
                String imageLink = cursor.getString(cursor.getColumnIndex(DatabaseConst.ITEM_FIELDS.IMAGE_LINK));
                String title = cursor.getString(cursor.getColumnIndex(DatabaseConst.ITEM_FIELDS.TITLE));
                String description = cursor.getString(cursor.getColumnIndex(DatabaseConst.ITEM_FIELDS.NEWS_DESCRIPTION));
                Log.d(Utils.LOG, title);
                String date = cursor.getString(cursor.getColumnIndex(DatabaseConst.ITEM_FIELDS.DATE));
                String author = cursor.getString(cursor.getColumnIndex(DatabaseConst.ITEM_FIELDS.AUTHOR));
                String newsLink = cursor.getString(cursor.getColumnIndex(DatabaseConst.ITEM_FIELDS.NEWS_LINK));
                NewsFeedItemModel newsFeedItemModel = new NewsFeedItemModel(imageLink, title, date, author, newsLink, description);
                mNewsFeedItemModelList.add(newsFeedItemModel);
                cursor.moveToNext();
            }
            cursor.close();
            hideProgressBar();
            Toast.makeText(getContext(), "Offline mode!", Toast.LENGTH_SHORT).show();
        } else {
            Log.d(Utils.LOG, "Database is empty!");
            showNoDataTextView("Check connections !!");
        }
        closeDB();
    }

    private void saveNewsToBD(List<NewsFeedItemModel> newsFeedItemModelList){
        Log.d(Utils.LOG, "saveNewsToBD");
        openDBForSaveData();
        String sql = String.format("INSERT INTO %s ('%s', '%s', '%s', '%s', '%s', '%s') VALUES(?1, ?2, ?3, ?4, ?5, ?6)",
                DatabaseConst.TABLE.ITEMS,
                DatabaseConst.ITEM_FIELDS.IMAGE_LINK,
                DatabaseConst.ITEM_FIELDS.TITLE,
                DatabaseConst.ITEM_FIELDS.NEWS_DESCRIPTION,
                DatabaseConst.ITEM_FIELDS.DATE,
                DatabaseConst.ITEM_FIELDS.AUTHOR,
                DatabaseConst.ITEM_FIELDS.NEWS_LINK
        );
        Log.d(Utils.LOG, "Open BD. sql = " + sql);
        SQLiteStatement stmt = database.compileStatement(sql);
        Log.d(Utils.LOG, "stmt = " + stmt.toString());
        database.beginTransaction();
        for (NewsFeedItemModel item: newsFeedItemModelList) {
            stmt.clearBindings();
            stmt.bindString(1, item.getImageLink());
            stmt.bindString(2, item.getTitle());
            stmt.bindString(3, item.getDescription());
            stmt.bindString(4, item.getDate());
            stmt.bindString(5, item.getAuthor());
            stmt.bindString(6, item.getNewsLink());
            stmt.executeInsert();
            Log.d(Utils.LOG, "News title: " + item.getTitle());
        }
        database.setTransactionSuccessful();
        database.endTransaction();
        closeDB();
        Log.d(Utils.LOG, "Data has been saved successfully!");
    }

    @Override
    public void OnNewsClick(String link) {
        if (onNewsSelectedListener != null) {
            onNewsSelectedListener.onNewsSelected(link);
        }
    }

    private void openDBForLoadData() {
        DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
        database = databaseHelper.getWritableDatabase();
    }

    private void openDBForSaveData() {
        DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
        database = databaseHelper.getWritableDatabase();
        databaseHelper.clearTable(database);
    }

    private void closeDB() {
        database.close();
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
