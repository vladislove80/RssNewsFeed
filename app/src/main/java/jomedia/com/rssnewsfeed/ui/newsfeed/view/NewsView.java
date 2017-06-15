package jomedia.com.rssnewsfeed.ui.newsfeed.view;

import android.support.annotation.NonNull;

import java.util.List;

import jomedia.com.rssnewsfeed.data.models.NewsFeedItemModel;
import jomedia.com.rssnewsfeed.ui.base.ProgressView;
import jomedia.com.rssnewsfeed.ui.base.View;
import jomedia.com.rssnewsfeed.ui.newsfeed.presenter.NewsPresenter;

public interface NewsView extends View<NewsPresenter>, ProgressView {
    void showNews(@NonNull List<NewsFeedItemModel> models);
}
