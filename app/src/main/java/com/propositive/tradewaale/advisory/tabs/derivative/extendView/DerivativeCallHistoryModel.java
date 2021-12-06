package com.propositive.tradewaale.advisory.tabs.derivative.extendView;

public class DerivativeCallHistoryModel {
    String h_symbol, h_buy_value, h_calls_method, h_reco_pr, h_target, h_updated, h_updated_time;

    public DerivativeCallHistoryModel(String h_symbol, String h_buy_value, String h_calls_method, String h_reco_pr, String h_target, String h_updated, String h_updated_time) {
        this.h_symbol = h_symbol;
        this.h_buy_value = h_buy_value;
        this.h_calls_method = h_calls_method;
        this.h_reco_pr = h_reco_pr;
        this.h_target = h_target;
        this.h_updated = h_updated;
        this.h_updated_time = h_updated_time;
    }

    public String getH_symbol() {
        return h_symbol;
    }

    public void setH_symbol(String h_symbol) {
        this.h_symbol = h_symbol;
    }

    public String getH_buy_value() {
        return h_buy_value;
    }

    public void setH_buy_value(String h_buy_value) {
        this.h_buy_value = h_buy_value;
    }

    public String getH_calls_method() {
        return h_calls_method;
    }

    public void setH_calls_method(String h_calls_method) {
        this.h_calls_method = h_calls_method;
    }

    public String getH_reco_pr() {
        return h_reco_pr;
    }

    public void setH_reco_pr(String h_reco_pr) {
        this.h_reco_pr = h_reco_pr;
    }

    public String getH_target() {
        return h_target;
    }

    public void setH_target(String h_target) {
        this.h_target = h_target;
    }

    public String getH_updated() {
        return h_updated;
    }

    public void setH_updated(String h_updated) {
        this.h_updated = h_updated;
    }

    public String getH_updated_time() {
        return h_updated_time;
    }

    public void setH_updated_time(String h_updated_time) {
        this.h_updated_time = h_updated_time;
    }
}
