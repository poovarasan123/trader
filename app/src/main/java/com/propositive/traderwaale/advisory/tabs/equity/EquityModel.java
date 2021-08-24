package com.propositive.traderwaale.advisory.tabs.equity;

public class EquityModel {
    String name, rate_status, stock_status, tc_price_txt, ap_return_txt;
    float reco_value, tc_price, ap_return;
    String filter,post_date,post_time;

    public EquityModel(){}

    public EquityModel(String name, String rate_status, String stock_status, String tc_price_txt, String ap_return_txt, float reco_value, float tc_price, float ap_return, String filter, String post_date, String post_time) {
        this.name = name;
        this.rate_status = rate_status;
        this.stock_status = stock_status;
        this.tc_price_txt = tc_price_txt;
        this.ap_return_txt = ap_return_txt;
        this.reco_value = reco_value;
        this.tc_price = tc_price;
        this.ap_return = ap_return;
        this.filter = filter;
        this.post_date = post_date;
        this.post_time = post_time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRate_status() {
        return rate_status;
    }

    public void setRate_status(String rate_status) {
        this.rate_status = rate_status;
    }

    public String getStock_status() {
        return stock_status;
    }

    public void setStock_status(String stock_status) {
        this.stock_status = stock_status;
    }

    public String getTc_price_txt() {
        return tc_price_txt;
    }

    public void setTc_price_txt(String tc_price_txt) {
        this.tc_price_txt = tc_price_txt;
    }

    public String getAp_return_txt() {
        return ap_return_txt;
    }

    public void setAp_return_txt(String ap_return_txt) {
        this.ap_return_txt = ap_return_txt;
    }

    public float getReco_value() {
        return reco_value;
    }

    public void setReco_value(float reco_value) {
        this.reco_value = reco_value;
    }

    public float getTc_price() {
        return tc_price;
    }

    public void setTc_price(float tc_price) {
        this.tc_price = tc_price;
    }

    public float getAp_return() {
        return ap_return;
    }

    public void setAp_return(float ap_return) {
        this.ap_return = ap_return;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getPost_time() {
        return post_time;
    }

    public String getPost_date() {
        return post_date;
    }

    public void setPost_date(String post_date) {
        this.post_date = post_date;
    }

    public void setPost_time(String post_time) {
        this.post_time = post_time;
    }
}
