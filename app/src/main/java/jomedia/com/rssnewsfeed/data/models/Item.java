package jomedia.com.rssnewsfeed.data.models;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "item", strict = false)
public class Item {

    @Element(name = "title")
    public String title;

    @Element(name = "link")
    public String link;

    @Element(name = "guid")
    public String guid;

    @Element(name = "pubDate")
    public String pubDate;

    @Element(name = "author", required = false)
    public String author;

    @Element(name = "category")
    public String category;

    @Element(name = "description")
    public String description;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    @Override
    public String toString() {
        return "NewsItem{" + "\n" +
                "title" + title + "\n" +
                "link" + link + "\n" +
                "guid" + guid + "\n" +
                "pubDate" + pubDate + "\n" +
                "author" + author + "\n" +
                "category" + category + "\n" +
                "description" + description +
                "}";
    }
}
