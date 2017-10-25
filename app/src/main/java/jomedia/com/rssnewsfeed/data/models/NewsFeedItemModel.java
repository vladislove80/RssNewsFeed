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

    public NewsFeedItemModel(
            String imageLink,
            String title,
            String date,
            String author,
            String newsLink,
            String description,
            String imageDescription
    ) {
        this.imageLink = imageLink;
        this.title = title;
        this.date = date;
        this.author = author;
        this.newsLink = newsLink;
        this.description = description;
        this.imageDescription = imageDescription;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NewsFeedItemModel that = (NewsFeedItemModel) o;

        if (!imageLink.equals(that.imageLink)) return false;
        if (!title.equals(that.title)) return false;
        if (!date.equals(that.date)) return false;
        if (!author.equals(that.author)) return false;
        if (!newsLink.equals(that.newsLink)) return false;
        return imageDescription.equals(that.imageDescription) && description.equals(that.description);

    }

    @Override
    public int hashCode() {
        int result = imageLink.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + date.hashCode();
        result = 31 * result + author.hashCode();
        result = 31 * result + newsLink.hashCode();
        result = 31 * result + imageDescription.hashCode();
        result = 31 * result + description.hashCode();
        return result;
    }
}
