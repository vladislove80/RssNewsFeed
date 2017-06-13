package jomedia.com.rssnewsfeed.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import jomedia.com.rssnewsfeed.R;
import jomedia.com.rssnewsfeed.ui.newsfeed.view.NewsFeedFragment;
import jomedia.com.rssnewsfeed.ui.newsview.OpenNewsFragment;

public class MainActivity extends AppCompatActivity implements NewsFeedFragment.OnNewsSelectedListener{

    private OpenNewsFragment openNewsFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) startNewsFeedFragment();
    }

    private int startNewsFeedFragment() {
        NewsFeedFragment fragment = NewsFeedFragment.getInstance();
        fragment.setOnNewsSelectedListener(this);
        return getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.main_container, fragment)
                .commit();
    }

    @Override
    public void onNewsSelected(String link) {
        openNewsFragment(link);
    }

    private void openNewsFragment(String link) {
        openNewsFragment = OpenNewsFragment.getInstance(link);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_container, openNewsFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && openNewsFragment != null && openNewsFragment.canGoBack()) {
            openNewsFragment.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}