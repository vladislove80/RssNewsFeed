package jomedia.com.rssnewsfeed.utils;

import android.widget.ImageView;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import jomedia.com.rssnewsfeed.BuildConfig;

@Ignore
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class PicassoUtilTest {

    private PicassoUtil mPicassoManager;
    private ImageView imageView;

    @Before
    public void setup() {
        mPicassoManager = new PicassoUtil(RuntimeEnvironment.application);
        imageView = new ImageView(RuntimeEnvironment.application);
    }

    @Test
    public void testPreConditions() throws Exception {
        Assert.assertNotNull(mPicassoManager);
    }

    @Test
    public void loadImage() throws Exception {
        String link = "https://i.cbc.ca/1.4128486.1495576405!/fileImage/httpImage/image.jpg_gen/derivatives/16x9_460/94567042.jpg";
        Assert.assertNull(imageView.getDrawable());
        mPicassoManager.loadImage(imageView, link);
        Assert.assertNotNull(imageView.getDrawable());
    }

}