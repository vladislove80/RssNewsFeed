package jomedia.com.rssnewsfeed.data.db;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import jomedia.com.rssnewsfeed.data.models.Item;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class DatabaseSourceImplTest {

    private List<Item> recivedItems = new ArrayList<>();
    private List<Item> expectedCategoryItems = new ArrayList<>();
    private List<Item> expectedAllItems = new ArrayList<>();
    private DatabaseSourceImpl databaseSourceImpl;
    private String category;

    @Before
    public void setup(){
        Item testNewsItem;
        databaseSourceImpl = new DatabaseSourceImpl(RuntimeEnvironment.application);

        category = "Top stories";
        testNewsItem = new Item(
                "Justin Trudeau visits 'reoccupation' teepee on Parliament Hill",
                "http://www.cbc.ca/news/politics/trudeau-visits-reoccupation-teepee-1.4185758?cmp=rss",
                "1.4185758",
                "30 черв. 2017 18:04",
                "John Paul Tasker",
                "Top stories",
                "<img src='https://i.cbc.ca/1.4185896.1498837536!/cpImage/httpImage/image.jpg_gen/derivatives/16x9_460/canada-day-prep-20170630-topix.jpg' alt='Canada Day Prep 20170630 TOPIX' width='460' title='Prime Minister Justin Trudeau leaves the teepee. ' height='259' />                <p>Prime Minister Justin Trudeau visited a teepee on Parliament Hill that was erected as a symbol of the unresolved grievances many Indigenous people have as the country is set to celebrate its 150th anniversary.</p>"
        );
        expectedCategoryItems.add(testNewsItem);
        expectedAllItems.add(testNewsItem);

        category = "Politics";
        testNewsItem = new Item(
                "<![CDATA[\n" +
                        "Federal websites geared to youth bland, boring: focus groups\n" +
                        "]]>",
                "http://www.cbc.ca/news/politics/websites-youth-trudeau-employment-skills-focus-groups-education-digital-1.4190076?cmp=rss",
                "1.4190076",
                "5 лип. 2017 05:00",
                "Dean Beeby",
                "Politics",
                "<![CDATA[\n" +
                        "<img src='https://i.cbc.ca/1.4187677.1498940580!/fileImage/httpImage/image.jpg_gen/derivatives/16x9_460/canada-day-trudeau.jpg' alt='canada day trudeau' width='460' title='Canada&#39;s Prime Minister Justin Trudeau poses for a selfie during Canada Day celebrations as the cou\n" +
                        "]]>\n" +
                        "<![CDATA[\n" +
                        "ntry marks its 150th anniversary since confederation, on Parliament Hill in Ottawa on July 1, 2017. (Blair Gable/Reuters)' height='259' /> <p>Prime Minister Justin Trudeau is also youth minister, promising to help struggling young Canadians find jobs and affordable education. But a new report suggests youth are largely ignorant of Liberal programs because government social media are so badly designed.</p>\n" +
                        "]]>"
        );
        expectedAllItems.add(testNewsItem);
        databaseSourceImpl.saveNews(expectedAllItems, category);
    }

    @Test
    public void testPreConditions() throws Exception {
        Assert.assertNotNull(databaseSourceImpl);
    }

    @Test
    public void testGetCategoryItems() throws Exception {
        category = "Top stories";
        recivedItems = databaseSourceImpl.getCategoryItems(category);
        Assert.assertEquals(expectedCategoryItems, recivedItems);
    }

    @Test
    public void testGetAllItems() {
        recivedItems = databaseSourceImpl.getAllItems();
        Assert.assertEquals(expectedAllItems, recivedItems);
    }
}