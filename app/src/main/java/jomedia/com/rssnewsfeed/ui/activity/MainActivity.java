package jomedia.com.rssnewsfeed.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;

import jomedia.com.rssnewsfeed.R;
import jomedia.com.rssnewsfeed.Utils;
import jomedia.com.rssnewsfeed.rest.api.ApiManager;
import jomedia.com.rssnewsfeed.rest.model.Item;
import jomedia.com.rssnewsfeed.rest.model.RssModel;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class MainActivity extends AppCompatActivity {
    private final CompositeSubscription mSubscriptions = new CompositeSubscription();
    private ArrayList<Item> rssItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadData();
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
            rssItems = rssModel.getChannel().getItems();
        }
        Log.d(Utils.LOG, "onDataSuccess ");
        for (int i = 0; i < rssItems.size(); i++){
            Log.d(Utils.LOG, (i+1) + " " + rssItems.get(i).getTitle());
        }
    }

    @Override
    public void onDestroy() {
        mSubscriptions.clear();
        super.onDestroy();
    }

}
