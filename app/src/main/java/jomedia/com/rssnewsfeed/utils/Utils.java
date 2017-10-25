package jomedia.com.rssnewsfeed.utils;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jomedia.com.rssnewsfeed.data.models.NewsFeedItemModel;
import jomedia.com.rssnewsfeed.data.models.Item;

public class Utils {
    public static final String LOG = "RssFeed";
    public static final String BASE_URL = "https://www.cbc.ca";
    public static final String[] rssLinks = {
            BASE_URL + "/cmlink/rss-topstories",
            BASE_URL +"/cmlink/rss-world",
            BASE_URL + "/cmlink/rss-canada",
            BASE_URL + "/cmlink/rss-politics",
            BASE_URL + "/cmlink/rss-business",
            BASE_URL + "/cmlink/rss-health",
            BASE_URL + "/cmlink/rss-arts",
            BASE_URL + "/cmlink/rss-technology",
            BASE_URL + "/cmlink/rss-offbeat",
            BASE_URL + "/cmlink/rss-cbcaboriginal",
            BASE_URL + "/cmlink/rss-sports",
            BASE_URL + "/cmlink/rss-sports-mlb",
            BASE_URL + "/cmlink/rss-sports-nba",
            BASE_URL + "/cmlink/rss-sports-curling",
            BASE_URL + "/cmlink/rss-sports-cfl",
            BASE_URL + "/cmlink/rss-sports-nfl",
            BASE_URL + "/cmlink/rss-sports-nhl",
            BASE_URL + "/cmlink/rss-sports-soccer",
            BASE_URL + "/cmlink/rss-sports-figureskating"
    };


    public static List<NewsFeedItemModel> getNewsFeedItems(List<Item> items){
        List<NewsFeedItemModel> newsFeedItemModels = new ArrayList<>();
        if (items != null && !items.isEmpty()) {
            String description;
            for (int i = 0; i < items.size(); i++){
                NewsFeedItemModel item = new NewsFeedItemModel();
                description = items.get(i).getDescription();
                boolean descNotNull = !TextUtils.isEmpty(description);
                String title = items.get(i).getTitle();
                String date = items.get(i).getPubDate();
                String author = items.get(i).getAuthor();
                String newsLink = items.get(i).getLink();
                item.setImageLink(descNotNull ? getImageLinkFrom(description) : "");
                item.setTitle(!TextUtils.isEmpty(title) ? title : "");
                item.setImageDescription(descNotNull ? getImageDescription(description) : "");
                item.setDescription(descNotNull ? getNewsDescription(description) : "");
                item.setDate(!TextUtils.isEmpty(date) ? date : "");
                item.setAuthor(!TextUtils.isEmpty(author) ? author : "");
                item.setNewsLink(!TextUtils.isEmpty(newsLink) ? newsLink : "");
                newsFeedItemModels.add(item);
            }
        }
        return newsFeedItemModels;
    }

    private static String getImageLinkFrom(String htmlDescription) {
        String srcTag = "";
        Pattern p = Pattern.compile("<img[^>]*src=[\\\"']([^\\\"^']*)");
        Matcher m = p.matcher(htmlDescription);
        if (m.find()) {
            String src = m.group();
            int startIndex = src.indexOf("src=") + 5;
            srcTag = src.substring(startIndex, src.length());
        }
        return srcTag;
    }

    private static String getImageDescription(String htmlDescription){
        String desc = "";
        Pattern p = Pattern.compile("<img[^>]*title=[\\\"']([^\\\"^']*)");
        Matcher m = p.matcher(htmlDescription);
        if (m.find()) {
            String src = m.group();
            int startIndex = src.indexOf("title=") + 7;
            desc = src.substring(startIndex, src.length());
        }
        return desc;
    }

    private static String getNewsDescription(String htmlDesc){
        return htmlDesc.substring(htmlDesc.indexOf("<p>") + 3, htmlDesc.indexOf("</p>"));
    }
}
