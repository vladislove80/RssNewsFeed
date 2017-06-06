package jomedia.com.rssnewsfeed.data.models;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "rss", strict = false)
public class RssModel {
    @Element(name = "channel")
    private Channel channel;
    @Attribute
    private String version;

    public Channel getChannel ()
    {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}
