package com.propositive.tradewaale.home;

public class newsModel {

    String news_image;
    String news_title, post_date, description;

    public newsModel(){}

    public newsModel(String news_image, String news_title, String post_date, String description) {
        this.news_image = news_image;
        this.news_title = news_title;
        this.post_date = post_date;
        this.description = description;
    }

    public String getNews_image() {
        return news_image;
    }

    public void setNews_image(String news_image) {
        this.news_image = news_image;
    }

    public String getNews_title() {
        return news_title;
    }

    public void setNews_title(String news_title) {
        this.news_title = news_title;
    }

    public String getPost_date() {
        return post_date;
    }

    public void setPost_date(String post_date) {
        this.post_date = post_date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
