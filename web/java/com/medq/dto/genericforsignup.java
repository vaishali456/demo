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
public class genericforsignup {

    public int getUserCount() {
        return UserCount;
    }

    public void setUserCount(int UserCount) {
        this.UserCount = UserCount;
    }

    private String ClinicName;
    private String PhoneNo;
    private int HouseNo;
    private String Street;
    private int UserCount;
    private String FaxNo;
    private int CityID;
    private int StateID;
    private String EmailId;
    private String ZipCode;
    
    private ArrayList<User> StaffList= new ArrayList();
    private ArrayList<User> Doctorist= new ArrayList();
    

    public String getClinicName() {
        return ClinicName;
    }

    public void setClinicName(String ClinicName) {
        this.ClinicName = ClinicName;
    }

    public String getPhoneNo() {
        return PhoneNo;
    }

    public void setPhoneNo(String PhoneNo) {
        this.PhoneNo = PhoneNo;
    }

    public int getHouseNo() {
        return HouseNo;
    }

    public void setHouseNo(int HouseNo) {
        this.HouseNo = HouseNo;
    }

    public String getStreet() {
        return Street;
    }

    public void setStreet(String Street) {
        this.Street = Street;
    }

    public String getFaxNo() {
        return FaxNo;
    }

    public void setFaxNo(String FaxNo) {
        this.FaxNo = FaxNo;
    }

    public int getCityID() {
        return CityID;
    }

    public void setCityID(int CityID) {
        this.CityID = CityID;
    }

    public int getStateID() {
        return StateID;
    }

    public void setStateID(int StateID) {
        this.StateID = StateID;
    }

    public String getEmailId() {
        return EmailId;
    }

    public void setEmailId(String EmailId) {
        this.EmailId = EmailId;
    }

    public String getZipCode() {
        return ZipCode;
    }

    public void setZipCode(String ZipCode) {
        this.ZipCode = ZipCode;
    }

    public ArrayList<User> getStaffList() {
        return StaffList;
    }

    public void setStaffList(ArrayList<User> StaffList) {
        this.StaffList = StaffList;
    }

    public ArrayList<User> getDoctorist() {
        return Doctorist;
    }

    public void setDoctorist(ArrayList<User> Doctorist) {
        this.Doctorist = Doctorist;
    }
   
    
    
            
    
    

}
