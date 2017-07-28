package com.hailer.news.util.bean;


public class FeedBackMessage {

    private String userEmail;
    private String messageContent;

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public FeedBackMessage(String userEmail, String messageContent) {
        this.userEmail = userEmail;
        this.messageContent = messageContent;
    }
}
