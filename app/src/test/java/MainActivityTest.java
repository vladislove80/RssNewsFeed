import android.support.v7.app.AppCompatActivity;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import jomedia.com.rssnewsfeed.BuildConfig;
import jomedia.com.rssnewsfeed.R;
import jomedia.com.rssnewsfeed.ui.MainActivity;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class MainActivityTest {

    private AppCompatActivity mainActivity;

    @Before
    public void setup()  {
        mainActivity = Robolectric.buildActivity(MainActivity.class)
                .create().get();
    }

    @Test
    public void shouldHaveString() throws Exception {
        String appName = mainActivity.getResources().getString(R.string.app_name);
        assertThat(appName, equalTo("RssNewsFeed"));
    }
    @Test
    public void checkActivityNotNull() throws Exception {
        assertNotNull(mainActivity);
    }
        
    @Test
    public void titleIsCorrect() throws Exception {
        assertTrue(mainActivity.getTitle().toString().equals("RssNewsFeed"));
    }
}
