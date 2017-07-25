package com.moma.app.news.util.bean;

/**
 * Created by moma on 17-7-25.
 */

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
