package jomedia.com.rssnewsfeed.data.models;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;

@Root(name = "channel", strict = false)
public class Channel {

    @Element(required = false)
    private String title;
    @Element(required = false)
    private String link;
    @Element(required = false)
    private String description;
    @Element(required = false)
    private String language;
    @Element(required = false)
    private String lastBuildDate;
    @Element(required = false)
    private String copyright;
    @Element(required = false)
    private String docs;
    @ElementList(required = false, inline = true)
    public ArrayList<Item> mItems;

    public ArrayList<Item> getItems(){
        return mItems;
    }
}
