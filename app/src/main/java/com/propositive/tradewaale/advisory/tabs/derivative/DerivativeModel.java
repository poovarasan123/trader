package com.propositive.tradewaale.advisory.tabs.derivative;

public class DerivativeModel {

    String symbol, buy_value, calls_method, stop_loss, reco_pr, target, target2, exp_term, returns, sel_date, sel_time;


    public DerivativeModel(String symbol, String buy_value, String calls_method, String stop_loss, String reco_pr, String target, String target2, String exp_term, String returns, String sel_date, String sel_time) {
        this.symbol = symbol;
        this.buy_value = buy_value;
        this.calls_method = calls_method;
        this.stop_loss = stop_loss;
        this.reco_pr = reco_pr;
        this.target = target;
        this.target2 = target2;
        this.exp_term = exp_term;
        this.returns = returns;
        this.sel_date = sel_date;
        this.sel_time = sel_time;
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


    public String getStop_loss() {
        return stop_loss;
    }

    public void setStop_loss(String stop_loss) {
        this.stop_loss = stop_loss;
    }

    public String getReco_pr() {
        return reco_pr;
    }

    public void setReco_pr(String reco_pr) {
        this.reco_pr = reco_pr;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getTarget2() {
        return target2;
    }

    public void setTarget2(String target2) {
        this.target2 = target2;
    }

    public String getExp_term() {
        return exp_term;
    }

    public void setExp_term(String exp_term) {
        this.exp_term = exp_term;
    }

    public String getReturns() {
        return returns;
    }

    public void setReturns(String returns) {
        this.returns = returns;
    }

    public String getSel_date() {
        return sel_date;
    }

    public void setSel_date(String sel_date) {
        this.sel_date = sel_date;
    }

    public String getSel_time() {
        return sel_time;
    }

    public void setSel_time(String sel_time) {
        this.sel_time = sel_time;
    }
}
