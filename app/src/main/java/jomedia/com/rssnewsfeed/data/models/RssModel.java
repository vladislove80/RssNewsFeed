package jomedia.com.rssnewsfeed.data.models;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "rss", strict = false)
public class RssModel {

    @Attribute
    private String version;
    @Element(name = "channel")
    private Channel channel;

    public Channel getChannel ()
    {
        return channel;
    }
}
