package com.propositive.tradewaale.subscription;

public class SubscriptionModel {
    String plan_id;
    String plan_name;
    String plan_validity;
    String plan_status;
    String plan_slogan;
    String plan_price;
    String paymentId;
    String payment_status;
    String start_date;
    String end_date;
    String plan_details;

    public SubscriptionModel(){}

    public SubscriptionModel(String plan_id, String plan_name, String plan_validity, String plan_status, String plan_slogan, String plan_price, String paymentId, String payment_status, String start_date, String end_date, String plan_details) {
        this.plan_id = plan_id;
        this.plan_name = plan_name;
        this.plan_validity = plan_validity;
        this.plan_status = plan_status;
        this.plan_slogan = plan_slogan;
        this.plan_price = plan_price;
        this.paymentId = paymentId;
        this.payment_status = payment_status;
        this.start_date = start_date;
        this.end_date = end_date;
        this.plan_details = plan_details;
    }

    public String getPlan_id() {
        return plan_id;
    }

    public void setPlan_id(String plan_id) {
        this.plan_id = plan_id;
    }

    public String getPlan_name() {
        return plan_name;
    }

    public void setPlan_name(String plan_name) {
        this.plan_name = plan_name;
    }

    public String getPlan_validity() {
        return plan_validity;
    }

    public void setPlan_validity(String plan_validity) {
        this.plan_validity = plan_validity;
    }

    public String getPlan_status() {
        return plan_status;
    }

    public void setPlan_status(String plan_status) {
        this.plan_status = plan_status;
    }

    public String getPlan_slogan() {
        return plan_slogan;
    }

    public void setPlan_slogan(String plan_slogan) {
        this.plan_slogan = plan_slogan;
    }

    public String getPlan_price() {
        return plan_price;
    }

    public void setPlan_price(String plan_price) {
        this.plan_price = plan_price;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getPayment_status() {
        return payment_status;
    }

    public void setPayment_status(String payment_status) {
        this.payment_status = payment_status;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getPlan_details() {
        return plan_details;
    }

    public void setPlan_details(String plan_details) {
        this.plan_details = plan_details;
    }
}

