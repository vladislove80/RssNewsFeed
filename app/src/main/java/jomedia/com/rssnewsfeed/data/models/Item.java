package jomedia.com.rssnewsfeed.data.models;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.convert.Convert;

import jomedia.com.rssnewsfeed.data.PubDateConverter;

@Root(name = "item", strict = false)
public class Item {

    @Element(name = "title", required = false)
    public String title;
    @Element(name = "link", required = false)
    public String link;
    @Element(name = "guid", required = false)
    public String guid;
    @Element(name = "pubDate", required = false)
    @Convert(PubDateConverter.class)
    public String pubDate;
    @Element(name = "author", required = false)
    public String author;
    @Element(name = "category", required = false)
    public String category;
    @Element(name = "description", required = false)
    public String description;

    public Item() {
    }

    public Item(String title,
                String link,
                String guid,
                String pubDate,
                String author,
                String category,
                String description) {
        this.title = title;
        this.link = link;
        this.guid = guid;
        this.pubDate = pubDate;
        this.author = author;
        this.category = category;
        this.description = description;
    }

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;

        if (!title.equals(item.title)) return false;
        if (!link.equals(item.link)) return false;
        if (!guid.equals(item.guid)) return false;
        if (!pubDate.equals(item.pubDate)) return false;
        if (!author.equals(item.author)) return false;
        if (!category.equals(item.category)) return false;
        return description.equals(item.description);

    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + link.hashCode();
        result = 31 * result + guid.hashCode();
        result = 31 * result + pubDate.hashCode();
        result = 31 * result + author.hashCode();
        result = 31 * result + category.hashCode();
        result = 31 * result + description.hashCode();
        return result;
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
