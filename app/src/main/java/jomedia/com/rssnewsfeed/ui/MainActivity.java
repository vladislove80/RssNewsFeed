package jomedia.com.rssnewsfeed.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import jomedia.com.rssnewsfeed.R;
import jomedia.com.rssnewsfeed.RssAplication;
import jomedia.com.rssnewsfeed.ui.customtab.CustomTabActivityHelper;
import jomedia.com.rssnewsfeed.ui.navigationdrawer.DrawerItemCustomAdapter;
import jomedia.com.rssnewsfeed.ui.newsfeed.OnNewsSelectedListener;
import jomedia.com.rssnewsfeed.ui.newsfeed.presenter.NewsPresenter;
import jomedia.com.rssnewsfeed.ui.newsfeed.presenter.NewsPresenterImpl;
import jomedia.com.rssnewsfeed.ui.newsfeed.view.NewsFeedFragment;
import jomedia.com.rssnewsfeed.utils.Utils;

public class MainActivity extends AppCompatActivity implements OnNewsSelectedListener {

    private String[] mNavigationDrawerItemTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    Toolbar toolbar;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    android.support.v7.app.ActionBarDrawerToggle mDrawerToggle;
    private String[] links;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTitle = mDrawerTitle = getTitle();
        mNavigationDrawerItemTitles= getResources().getStringArray(R.array.navigation_drawer_items_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setStatusBarBackground(R.color.colorPrimaryDark);
        mDrawerList = (ListView) findViewById(R.id.nav_view);
        setupToolbar();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        DrawerItemCustomAdapter adapter = new DrawerItemCustomAdapter(this, R.layout.list_view_item_row, mNavigationDrawerItemTitles);
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        setupDrawerToggle();
        if (savedInstanceState == null) startNewsFeedFragment(mNavigationDrawerItemTitles[0]);
        mDrawerList.setItemChecked(0, true);
        setTitle(mNavigationDrawerItemTitles[0]);
        links = Utils.rssLinks;
    }
    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }

        private void selectItem(int position) {
            mDrawerList.setItemChecked(position, true);
            Toast.makeText(MainActivity.this, mNavigationDrawerItemTitles[position], Toast.LENGTH_SHORT).show();
            startNewsFeedFragment(links[position]);
            setTitle(mNavigationDrawerItemTitles[position]);
            mDrawerLayout.closeDrawer(Gravity.LEFT);
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    void setupToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    void setupDrawerToggle(){
        mDrawerToggle = new android.support.v7.app.ActionBarDrawerToggle(this,mDrawerLayout,toolbar,R.string.app_name, R.string.app_name);
        //This is necessary to change the icon of the Drawer Toggle upon state change.
        mDrawerToggle.syncState();
    }
    private int startNewsFeedFragment(String link) {
        NewsPresenter presenter = new NewsPresenterImpl(this, RssAplication.getNewsRepository());
        NewsFeedFragment fragment = NewsFeedFragment.getInstance();
        Bundle bundle = new Bundle();
        bundle.putString("link to news", link);
        fragment.setArguments(bundle);
        presenter.bindView(fragment);
        fragment.bindPresenter(presenter);
        return getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_container, fragment)
                .commit();
    }

    @Override
    public void onNewsSelected(String link) {
        openNewsInCustomTab(link);
    }

    public void openNewsInCustomTab(String link) {
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder
                .setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .build();
        CustomTabActivityHelper.openCustomTab(this, customTabsIntent,  Uri.parse(link),
                new CustomTabActivityHelper.CustomTabFallback() {
                    @Override
                    public void openUri(Activity activity, Uri uri) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        activity.startActivity(intent);
                    }
                });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
