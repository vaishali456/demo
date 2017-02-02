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
public class ChatRequest {

    private  int SenderID;
    private  int ReceiverID;
    private boolean Status;
    private Timestamp CreatedTime;
      private  Timestamp ResponseTime;

    public Timestamp getCreatedTime() {
        return CreatedTime;
    }

    public void setCreatedTime(Timestamp CreatedTime) {
        this.CreatedTime = CreatedTime;
    }

    public Timestamp getResponseTime() {
        return ResponseTime;
    }

    public void setResponseTime(Timestamp ResponseTime) {
        this.ResponseTime = ResponseTime;
    }
   
    
    public boolean isStatus() {
        return Status;
    }

    public void setStatus(boolean Status) {
        this.Status = Status;
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
    
}
