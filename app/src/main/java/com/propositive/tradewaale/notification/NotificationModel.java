package com.propositive.tradewaale.notification;

public class NotificationModel {

    String notification_title, body_message, date;

    public NotificationModel(String notification_title, String body_message, String date) {
        this.notification_title = notification_title;
        this.body_message = body_message;
        this.date = date;
    }

    public String getNotification_title() {
        return notification_title;
    }

    public void setNotification_title(String notification_title) {
        this.notification_title = notification_title;
    }

    public String getBody_message() {
        return body_message;
    }

    public void setBody_message(String body_message) {
        this.body_message = body_message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
