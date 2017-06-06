package jomedia.com.rssnewsfeed.ui.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import jomedia.com.rssnewsfeed.R;
import jomedia.com.rssnewsfeed.ui.fragments.NewsFeedFragment;

public class MainActivity extends AppCompatActivity implements NewsFeedFragment.OnNewsSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) startPostsFragment();
    }

    private int startPostsFragment() {
        NewsFeedFragment fragment = NewsFeedFragment.getInstance();
        fragment.setOnNewsSelectedListener(this);
        return getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_container, fragment)
                .commit();
    }

    @Override
    public void onNewsSelected(String link) {
        Intent intent = new Intent(MainActivity.this, OpenItemActivity.class);
        intent.putExtra(OpenItemActivity.URL, link);
        startActivity(intent);
    }
}
