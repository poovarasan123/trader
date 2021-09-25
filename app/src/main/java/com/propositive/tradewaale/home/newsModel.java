package com.propositive.tradewaale.home;

public class newsModel {

    String market, news_title, post_date, description;

    public newsModel(){}

    public newsModel(String market, String news_title, String post_date, String description) {
        this.market = market;
        this.news_title = news_title;
        this.post_date = post_date;
        this.description = description;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
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
