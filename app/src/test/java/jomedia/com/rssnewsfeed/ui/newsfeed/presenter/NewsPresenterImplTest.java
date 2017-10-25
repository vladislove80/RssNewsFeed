package jomedia.com.rssnewsfeed.ui.newsfeed.presenter;

import org.junit.Before;
import org.junit.Test;

import jomedia.com.rssnewsfeed.RssAplication;
import jomedia.com.rssnewsfeed.ui.newsfeed.OnNewsSelectedListener;

import static org.junit.Assert.*;

public class NewsPresenterImplTest {

    NewsPresenterImpl newsPresenterImpl;
    @Before
    public void beforTest() {
        newsPresenterImpl = new NewsPresenterImpl(new OnNewsSelectedListener() {
            @Override
            public void onNewsSelected(String link) {
                assertTrue(true);
            }
        }, RssAplication.getNewsRepository());
    }

    @Test
    public void testNotNull() {
        assertNotNull(newsPresenterImpl);
    }

    @Test
    public void loadNews() throws Exception {

    }

    @Test
    public void onNewsFeedItemClick() throws Exception {

    }

}