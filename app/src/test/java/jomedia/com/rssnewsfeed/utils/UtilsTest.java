package jomedia.com.rssnewsfeed.utils;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import jomedia.com.rssnewsfeed.data.models.Item;
import jomedia.com.rssnewsfeed.data.models.NewsFeedItemModel;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class UtilsTest {

    private List<Item> items = new ArrayList<>();
    private List<NewsFeedItemModel> expectedNewsFeedItemModels = new ArrayList<>();

    @Before
    public void const_inst(){
        Item newsItem = new Item(
                "Justin Trudeau visits 'reoccupation' teepee on Parliament Hill",
                "http://www.cbc.ca/news/politics/trudeau-visits-reoccupation-teepee-1.4185758?cmp=rss",
                "1.4185758",
                "30 черв. 2017 18:04",
                "John Paul Tasker",
                "Top stories",
                "<img src='https://i.cbc.ca/1.4185896.1498837536!/cpImage/httpImage/image.jpg_gen/derivatives/16x9_460/canada-day-prep-20170630-topix.jpg' alt='Canada Day Prep 20170630 TOPIX' width='460' title='Prime Minister Justin Trudeau leaves the teepee. ' height='259' />                <p>Prime Minister Justin Trudeau visited a teepee on Parliament Hill that was erected as a symbol of the unresolved grievances many Indigenous people have as the country is set to celebrate its 150th anniversary.</p>"
        );
        items.add(newsItem);
        NewsFeedItemModel expectedNewsFeedItemModel = new NewsFeedItemModel(
                "https://i.cbc.ca/1.4185896.1498837536!/cpImage/httpImage/image.jpg_gen/derivatives/16x9_460/canada-day-prep-20170630-topix.jpg",
                "Justin Trudeau visits 'reoccupation' teepee on Parliament Hill",
                "30 черв. 2017 18:04",
                "John Paul Tasker",
                "http://www.cbc.ca/news/politics/trudeau-visits-reoccupation-teepee-1.4185758?cmp=rss",
                "Prime Minister Justin Trudeau visited a teepee on Parliament Hill that was erected as a symbol of the unresolved grievances many Indigenous people have as the country is set to celebrate its 150th anniversary.",
                "Prime Minister Justin Trudeau leaves the teepee. "
        );
        expectedNewsFeedItemModels.add(expectedNewsFeedItemModel);
    }

    @Test
    public void testGetCorrectNewsFeedItems() throws Exception {
        List<NewsFeedItemModel> newsFeedItemModels = Utils.getNewsFeedItems(items);
        Assert.assertEquals(expectedNewsFeedItemModels, newsFeedItemModels);
        Assert.assertThat(expectedNewsFeedItemModels, Matchers.is(newsFeedItemModels));
    }

    @Test
    public void testShouldReturnNotNull() throws Exception {
        Assert.assertNotNull(Utils.getNewsFeedItems(null));
        Assert.assertNotNull(Utils.getNewsFeedItems(new ArrayList<Item>()));
    }


}