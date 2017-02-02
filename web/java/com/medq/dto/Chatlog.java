/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.medq.dto;

import java.sql.Timestamp;
import java.util.ArrayList;

/**
 *
 * @author eshine
 */
public class Chatlog {

    private int ChatlogID;
    private String IPAddress;
    private String Message;
    private int ChatgroupID;
    private int SenderID;
    private int ReceiverID;
    private String ChatTime;
    private String ChatType;
    private Timestamp LastMessageTimeStamp;
    private String FileName;

    
    private ArrayList<String> ChatDocumentsUrl;

     public String getFileName() {
        return FileName;
    }

    public void setFileName(String FileName) {
        this.FileName = FileName;
    }
    
    public ArrayList<String> getChatDocumentsUrl() {
        return ChatDocumentsUrl;
    }

    public void setChatDocumentsUrl(ArrayList<String> ChatDocumentsUrl) {
        this.ChatDocumentsUrl = ChatDocumentsUrl;
    }
    

    public Timestamp getLastMessageTimeStamp() {
        return LastMessageTimeStamp;
    }

    public void setLastMessageTimeStamp(Timestamp LastMessageTimeStamp) {
        this.LastMessageTimeStamp = LastMessageTimeStamp;
    }

    public String getChatTime() {
        return ChatTime;
    }

    public void setChatTime(String ChatTime) {
        this.ChatTime = ChatTime;
    }


    public String getChatType() {
        return ChatType;
    }

    public void setChatType(String ChatType) {
        this.ChatType = ChatType;
    }

    private Boolean ChatStatus;
    private int ReferralID;

    public int getChatlogID() {
        return ChatlogID;
    }

    public void setChatlogID(int ChatlogID) {
        this.ChatlogID = ChatlogID;
    }

    public String getIPAddress() {
        return IPAddress;
    }

    public void setIPAddress(String IPAddress) {
        this.IPAddress = IPAddress;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public int getChatgroupID() {
        return ChatgroupID;
    }

    public void setChatgroupID(int ChatgroupID) {
        this.ChatgroupID = ChatgroupID;
    }

    public int getSenderID() {
        return SenderID;
    }

    public void setSenderID(int SenderID) {
        this.SenderID = SenderID;
    }

    public int getReceiverID() {
        return ReceiverID;
    }

    public void setReceiverID(int ReceiverID) {
        this.ReceiverID = ReceiverID;
    }

    public Boolean getChatStatus() {
        return ChatStatus;
    }

    public void setChatStatus(Boolean ChatStatus) {
        this.ChatStatus = ChatStatus;
    }

    public int getReferralID() {
        return ReferralID;
    }

    public void setReferralID(int ReferralID) {
        this.ReferralID = ReferralID;
    }

    @Override
    public String toString() {
        return "Chatlog{" + "ChatlogID=" + ChatlogID + ", IPAddress=" + IPAddress + ", Message=" + Message + ", ChatgroupID=" + ChatgroupID + ", SenderID=" + SenderID + ", ReceiverID=" + ReceiverID + ", ChatTime=" + ChatTime + ", ChatType=" + ChatType + ", ChatStatus=" + ChatStatus + ", ReferralID=" + ReferralID + '}';
    }

}
