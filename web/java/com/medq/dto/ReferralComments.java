/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.medq.dto;

import java.sql.Timestamp;

/**
 *
 * @author eshine
 */
public class ReferralComments {

    private int ReferralCommentID;
    private int ReferralID;
    private String CommentMessage;
    private String CommentedMessageTime;
    private Timestamp CommentedMessageTimeStamp;

    public Timestamp getCommentedMessageTimeStamp() {
        return CommentedMessageTimeStamp;
    }

    public void setCommentedMessageTimeStamp(Timestamp CommentedMessageTimeStamp) {
        this.CommentedMessageTimeStamp = CommentedMessageTimeStamp;
    }
    private int UserID;
    private String UserName;

    public int getReferralCommentID() {
        return ReferralCommentID;
    }

    public void setReferralCommentID(int ReferralCommentID) {
        this.ReferralCommentID = ReferralCommentID;
    }

    public String getCommentedMessageTime() {
        return CommentedMessageTime;
    }

    public void setCommentedMessageTime(String CommentedMessageTime) {
        this.CommentedMessageTime = CommentedMessageTime;
    }

    public int getReferralID() {
        return ReferralID;
    }

    public void setReferralID(int ReferralID) {
        this.ReferralID = ReferralID;
    }

    public String getCommentMessage() {
        return CommentMessage;
    }

    public void setCommentMessage(String CommentMessage) {
        this.CommentMessage = CommentMessage;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int UserID) {
        this.UserID = UserID;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

}
