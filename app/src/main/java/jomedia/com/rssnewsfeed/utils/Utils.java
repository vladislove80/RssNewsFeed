package jomedia.com.rssnewsfeed.utils;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jomedia.com.rssnewsfeed.data.models.NewsFeedItem;
import jomedia.com.rssnewsfeed.data.models.Item;

public class Utils {
    public static final String LOG = "RssFeed";
    public static final String BASE_URL = "http://www.cbc.ca/";

    public static List<NewsFeedItem> getNewsFeedItems(List<Item> items){
        List<NewsFeedItem> newsFeedItems = new ArrayList<>();
        String description = "";
        for (int i = 0; i < items.size(); i++){
            NewsFeedItem item = new NewsFeedItem();
            description = items.get(i).getDescription();
            boolean descNotNull = !TextUtils.isEmpty(description);
            String title = items.get(i).getTitle();
            String date = items.get(i).getPubDate();
            String author = items.get(i).getAuthor();
            String newsLink = items.get(i).getLink();
            item.setImageLink(descNotNull ? getImageLinkFrom(description) : "");
            item.setTitle(!TextUtils.isEmpty(title) ? title : "");
            item.setDescription(descNotNull ? getNewsDescription(description) : "");
            item.setDate(!TextUtils.isEmpty(date) ? date : "");
            item.setAuthor(!TextUtils.isEmpty(author) ? author : "");
            item.setNewsLink(!TextUtils.isEmpty(newsLink) ? newsLink : "");
            newsFeedItems.add(item);
        }
        return newsFeedItems;
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

    private static String getNewsDescription(String htmlDescription){
        String desc = "";
        Pattern p = Pattern.compile("<img[^>]*title=[\\\"']([^\\\"^']*)");
        Matcher m = p.matcher(htmlDescription);
        if (m.find()) {
            String src = m.group();
            int startIndex = src.indexOf("title=") + 7;
            desc = src.substring(startIndex, src.length());
        }
        desc = desc + "\n" + getSecondDescription(htmlDescription);
        return desc;
    }

    private static String getSecondDescription(String htmlDesc){
        return htmlDesc.substring(htmlDesc.indexOf("<p>") + 3, htmlDesc.indexOf("</p>"));
    }
}
