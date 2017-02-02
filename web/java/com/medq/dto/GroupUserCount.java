/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.medq.dto;

/**
 * this dto used for old group chat functionallity
 *
 * @author eshine
 */
public class GroupUserCount {

    private int GroupMessageCount;
    private int GroupID;
    private int ReceiverID;

    public int getGroupMessageCount() {
        return GroupMessageCount;
    }

    public void setGroupMessageCount(int GroupMessageCount) {
        this.GroupMessageCount = GroupMessageCount;
    }

    public int getGroupID() {
        return GroupID;
    }

    public void setGroupID(int GroupID) {
        this.GroupID = GroupID;
    }

    public int getReceiverID() {
        return ReceiverID;
    }

    public void setReceiverID(int ReceiverID) {
        this.ReceiverID = ReceiverID;
    }

}
