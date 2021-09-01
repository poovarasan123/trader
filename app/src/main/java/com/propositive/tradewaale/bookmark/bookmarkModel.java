package com.propositive.tradewaale.bookmark;

public class bookmarkModel {

    int imageNews;
    String title_text, date_text, comment_text;

    public bookmarkModel(int imageNews, String title_text, String date_text, String comment_text) {
        this.imageNews = imageNews;
        this.title_text = title_text;
        this.date_text = date_text;
        this.comment_text = comment_text;
    }

    public int getImageNews() {
        return imageNews;
    }

    public void setImageNews(int imageNews) {
        this.imageNews = imageNews;
    }

    public String getTitle_text() {
        return title_text;
    }

    public void setTitle_text(String title_text) {
        this.title_text = title_text;
    }

    public String getDate_text() {
        return date_text;
    }

    public void setDate_text(String date_text) {
        this.date_text = date_text;
    }

    public String getComment_text() {
        return comment_text;
    }

    public void setComment_text(String comment_text) {
        this.comment_text = comment_text;
    }
}
