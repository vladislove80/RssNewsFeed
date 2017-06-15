package jomedia.com.rssnewsfeed.ui.newsfeed.presenter;

import jomedia.com.rssnewsfeed.ui.base.Presenter;
import jomedia.com.rssnewsfeed.ui.newsfeed.view.NewsView;

public interface NewsPresenter extends Presenter<NewsView> {

    void loadNews();
    void onNewsFeedItemClick(String link);
}
