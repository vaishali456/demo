/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.medq.dto;

import java.util.ArrayList;

/**
 *
 * @author eshine
 */
public class ChatGroup {

    private int ChatgroupID;
    private String GroupName;
    private int MemberCount;
    private boolean IsActive;
    private ArrayList<String> GroupMemberlist = new ArrayList();
    private ArrayList<User> GroupMemberlist1 = new ArrayList();
    private int GroupAdmin;

    public int getGroupAdmin() {
        return GroupAdmin;
    }

    public void setGroupAdmin(int GroupAdmin) {
        this.GroupAdmin = GroupAdmin;
    }

    //For Group Chat History
    private int GroupMessageCount;
    private int GroupID;
    private int UserID;

    public int getGroupID() {
        return GroupID;
    }

    public void setGroupID(int GroupID) {
        this.GroupID = GroupID;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int UserID) {
        this.UserID = UserID;
    }

    public int getGroupMessageCount() {
        return GroupMessageCount;
    }

    public void setGroupMessageCount(int GroupMessageCount) {
        this.GroupMessageCount = GroupMessageCount;
    }

    public ArrayList<String> getGroupMemberlist() {
        return GroupMemberlist;
    }

    public void setGroupMemberlist(ArrayList<String> GroupMemberlist) {
        this.GroupMemberlist = GroupMemberlist;
    }

    public ArrayList<User> getGroupMemberlist1() {
        return GroupMemberlist1;
    }

    public void setGroupMemberlist1(ArrayList<User> GroupMemberlist1) {
        this.GroupMemberlist1 = GroupMemberlist1;
    }

    public int getChatgroupID() {
        return ChatgroupID;
    }

    public void setChatgroupID(int ChatgroupID) {
        this.ChatgroupID = ChatgroupID;
    }

    public String getGroupName() {
        return GroupName;
    }

    public void setGroupName(String GroupName) {
        this.GroupName = GroupName;
    }

    public int getMemberCount() {
        return MemberCount;
    }

    public void setMemberCount(int MemberCount) {
        this.MemberCount = MemberCount;
    }

    public boolean isIsActive() {
        return IsActive;
    }

    public void setIsActive(boolean IsActive) {
        this.IsActive = IsActive;
    }

    @Override
    public String toString() {
        return "ChatGroup{" + "ChatgroupID=" + ChatgroupID + ", GroupName=" + GroupName + ", MemberCount=" + MemberCount + ", IsActive=" + IsActive + ", GroupMemberlist=" + GroupMemberlist + ", GroupMemberlist1=" + GroupMemberlist1 + '}';
    }

}
