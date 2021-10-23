package com.propositive.tradewaale.advisory.tabs.equity;

public class EquityModel {
    String symbol, buy_value, calls_method, target, reco_pr, exp_term, sel_date, sel_time,returns;

    public EquityModel(){}

    public EquityModel(String symbol, String buy_value, String calls_method, String target, String reco_pr, String exp_term, String sel_date, String sel_time, String returns) {
        this.symbol = symbol;
        this.buy_value = buy_value;
        this.calls_method = calls_method;
        this.target = target;
        this.reco_pr = reco_pr;
        this.exp_term = exp_term;
        this.sel_date = sel_date;
        this.sel_time = sel_time;
        this.returns = returns;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getBuy_value() {
        return buy_value;
    }

    public void setBuy_value(String buy_value) {
        this.buy_value = buy_value;
    }

    public String getCalls_method() {
        return calls_method;
    }

    public void setCalls_method(String calls_method) {
        this.calls_method = calls_method;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getReco_pr() {
        return reco_pr;
    }

    public void setReco_pr(String reco_pr) {
        this.reco_pr = reco_pr;
    }

    public String getExp_term() {
        return exp_term;
    }

    public void setExp_term(String exp_term) {
        this.exp_term = exp_term;
    }

    public String getSel_time() {
        return sel_time;
    }

    public String getSel_date() {
        return sel_date;
    }

    public void setSel_date(String sel_date) {
        this.sel_date = sel_date;
    }

    public void setSel_time(String sel_time) {
        this.sel_time = sel_time;
    }

    public String getReturns() {
        return returns;
    }

    public void setReturns(String returns) {
        this.returns = returns;
    }
}
