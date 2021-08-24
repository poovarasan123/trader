package com.propositive.traderwaale.advisory.tabs.derivative;

public class DerivativeModel {

    String name, rate_status, stock_status, tc_price_txt, ap_return_txt;
    float stock_loss, reco_price, target_value_start, target_value_end, ap_return;
    String terms,post_date,post_time;

    public DerivativeModel(String name, String rate_status, String stock_status, String tc_price_txt, String ap_return_txt, float stock_loss, float reco_price, float target_value_start, float target_value_end, float ap_return, String terms, String post_date, String post_time) {
        this.name = name;
        this.rate_status = rate_status;
        this.stock_status = stock_status;
        this.tc_price_txt = tc_price_txt;
        this.ap_return_txt = ap_return_txt;
        this.stock_loss = stock_loss;
        this.reco_price = reco_price;
        this.target_value_start = target_value_start;
        this.target_value_end = target_value_end;
        this.ap_return = ap_return;
        this.terms = terms;
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

    public float getStock_loss() {
        return stock_loss;
    }

    public void setStock_loss(float stock_loss) {
        this.stock_loss = stock_loss;
    }

    public float getReco_price() {
        return reco_price;
    }

    public void setReco_price(float reco_price) {
        this.reco_price = reco_price;
    }

    public float getTarget_value_start() {
        return target_value_start;
    }

    public void setTarget_value_start(float target_value_start) {
        this.target_value_start = target_value_start;
    }

    public float getTarget_value_end() {
        return target_value_end;
    }

    public void setTarget_value_end(float target_value_end) {
        target_value_end = target_value_end;
    }

    public float getAp_return() {
        return ap_return;
    }

    public void setAp_return(float ap_return) {
        this.ap_return = ap_return;
    }

    public String getTerms() {
        return terms;
    }

    public void setTerms(String terms) {
        this.terms = terms;
    }

    public String getPost_date() {
        return post_date;
    }

    public void setPost_date(String post_date) {
        this.post_date = post_date;
    }

    public String getPost_time() {
        return post_time;
    }

    public void setPost_time(String post_time) {
        this.post_time = post_time;
    }
}
