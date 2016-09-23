package jomedia.com.rssnewsfeed.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jomedia.com.rssnewsfeed.ui.NewsFeedItem;
import jomedia.com.rssnewsfeed.rest.model.Item;

public class Utils {
    public static final String LOG = "log_tag";

    public static List<NewsFeedItem> getNewsFeedItems(List<Item> items){
        List<NewsFeedItem> newsFeedItems = new ArrayList<>();
        String description = "";
        for (int i = 0; i < items.size(); i++){
            NewsFeedItem item = new NewsFeedItem();
            description = items.get(i).getDescription();
            item.setImageLink(getSrcFromTag(description));
            item.setTitle(items.get(i).getTitle());
            item.setDate(items.get(i).getPubDate());
            item.setAuthor(items.get(i).getAuthor());
            item.setNewsLink(items.get(i).getLink());
            newsFeedItems.add(item);
        }
        return newsFeedItems;
    }

    private static String getSrcFromTag(String description) {
        String srcTag = "";
        Pattern p = Pattern.compile("<img[^>]*src=[\\\"']([^\\\"^']*)");
        Matcher m = p.matcher(description);
        if (m.find()) {
            String src = m.group();
            int startIndex = src.indexOf("src=") + 5;
            srcTag = src.substring(startIndex, src.length());
        }
        return srcTag;
    }
}
