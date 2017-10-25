package jomedia.com.rssnewsfeed.data.api;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import jomedia.com.rssnewsfeed.utils.Utils;

import static org.junit.Assert.*;

public class RestManagerTest {

    private RestManager restManager;

    @Before
    public void beforTest() {
        restManager = new RestManager();
    }

    @Test
    public void testNotNull() throws Exception {
        Assert.assertNotNull(restManager);
    }

    @Test
    public void provideRestApi() throws Exception {
        RestApi restApi = restManager.provideRestApi(Utils.BASE_URL);
        Assert.assertNotNull(restApi);
    }

    @After
    public void afterTest() {
        restManager = null;
    }
}