package jomedia.com.rssnewsfeed.ui.newsfeed.presenter;

import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;

import jomedia.com.rssnewsfeed.data.callback.NewsCallback;
import jomedia.com.rssnewsfeed.data.models.DataResponse;
import jomedia.com.rssnewsfeed.data.models.NewsFeedItemModel;
import jomedia.com.rssnewsfeed.data.repository.NewsRepository;
import jomedia.com.rssnewsfeed.ui.base.BasePresenter;
import jomedia.com.rssnewsfeed.ui.newsfeed.OnNewsSelectedListener;
import jomedia.com.rssnewsfeed.ui.newsfeed.view.NewsView;
import jomedia.com.rssnewsfeed.utils.Utils;

public class NewsPresenterImpl extends BasePresenter<NewsView> implements NewsPresenter{

    @Nullable
    private List<NewsFeedItemModel> newsFeedItemModels;

    private final OnNewsSelectedListener onNewsSelectedListener;
    private final NewsRepository newsRepository;

    public NewsPresenterImpl(@Nullable OnNewsSelectedListener onNewsSelectedListener, @Nullable NewsRepository newsRepository) {
        this.onNewsSelectedListener = onNewsSelectedListener;
        this.newsRepository = newsRepository;
    }

    @Override
    public void loadNews() {
        showProgress();
        newsRepository.getNewsItems(new NewsCallback<DataResponse>() {
            @Override
            public void onEmit(DataResponse data) {
                String newsStatus = (data.isOffline())? "Offline" : "Online";
                Log.i(Utils.LOG, "NewsPresentorImpl -> loadNews -> onEmit -> data is " + newsStatus);
                handleData(data);
            }
            @Override
            public void onCompleted() {
                    hideProgress();
                }
            @Override
            public void onError(Throwable throwable) {
                showError();
            }
        });
    }

    @Override
    public void onNewsFeedItemClick(String link) {
        if (onNewsSelectedListener != null) {
            onNewsSelectedListener.onNewsSelected(link);
        }
    }

    private void showProgress() {
        if (getView() != null)
            getView().showProgress();
    }

    private void showError() {
        if (getView() != null) {
            getView().hideProgress();
            getView().showError("Check connections");
        }
    }

    private void hideProgress() {
        if (getView() != null)
            getView().hideProgress();
    }

    private void handleData(DataResponse data) {
        if (data != null && getView() != null) {
            if (data.getNewsFeedItemModels().size() != 0) {
                newsFeedItemModels = data.getNewsFeedItemModels();
                String newsStatus = (data.isOffline())? "Offline" : "Online";
                getView().showNewsStatus(newsStatus);
                getView().showNews(newsFeedItemModels);
            } else {
                getView().showNoDataMessage("No data");
            }
        }
    }
}
