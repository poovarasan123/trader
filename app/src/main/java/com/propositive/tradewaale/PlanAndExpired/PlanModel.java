package com.propositive.tradewaale.PlanAndExpired;

public class PlanModel {

    String plan_id, plan_name, plan_slogan, plan_price,  plan_validity, plan_details;

    public PlanModel(String plan_id, String plan_name, String plan_slogan, String plan_price, String plan_validity, String plan_details) {
        this.plan_id = plan_id;
        this.plan_name = plan_name;
        this.plan_slogan = plan_slogan;
        this.plan_price = plan_price;
        this.plan_validity = plan_validity;
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

    public String getPlan_validity() {
        return plan_validity;
    }

    public void setPlan_validity(String plan_validity) {
        this.plan_validity = plan_validity;
    }

    public String getPlan_details() {
        return plan_details;
    }

    public void setPlan_details(String plan_details) {
        this.plan_details = plan_details;
    }
}
