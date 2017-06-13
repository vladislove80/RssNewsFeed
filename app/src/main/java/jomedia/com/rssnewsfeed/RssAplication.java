package jomedia.com.rssnewsfeed;

import android.app.Application;

import jomedia.com.rssnewsfeed.data.repository.NewsRepository;
import jomedia.com.rssnewsfeed.data.repository.NewsRepositoryImpl;

public class RssAplication extends Application {

    private static NewsRepository newsRepository;

    @Override
    public void onCreate() {
        super.onCreate();
        newsRepository = new NewsRepositoryImpl(this);
    }

    public static NewsRepository getNewsRepository() {
        return newsRepository;
    }
}
