package jomedia.com.rssnewsfeed.data.models;

public class NewsFeedItemModel {
    private String imageLink;
    private String title;
    private String date;
    private String author;
    private String newsLink;
    private String imageDescription;
    private String description;

    public NewsFeedItemModel() {}

    public NewsFeedItemModel(String imageLink, String title, String date, String author, String newsLink, String description) {
        this.imageLink = imageLink;
        this.title = title;
        this.date = date;
        this.author = author;
        this.newsLink = newsLink;
        this.description = description;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getNewsLink() {
        return newsLink;
    }

    public void setNewsLink(String newsLink) {
        this.newsLink = newsLink;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageDescription() {
        return imageDescription;
    }

    public void setImageDescription(String imageDescription) {
        this.imageDescription = imageDescription;
    }
}
