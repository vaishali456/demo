/*
 * To change this license header; choose License Headers in Project Properties.
 * To change this template file; choose Tools | Templates
 * and open the template in the editor.
 */
package com.medq.dto;

import java.util.ArrayList;

/**
 *
 * @author eshine
 */
public class Clinic {

    private int ClinicID;
    private String ClinicName;
    private int AdressID;
    private String PhoneNo;
    private int Status;
    private int CityID;
    private int StateID;
   

    private String HouseNo;
    private String Street;
    private String FaxNo;
    private String ZipCode;
    private String Email;
    private int StaffCount;
    private int DoctorCount;
    private String CityName;
    private String StateName;
  
    
    
    public String getCityName() {
        return CityName;
    }

    public void setCityName(String CityName) {
        this.CityName = CityName;
    }

    public String getStateName() {
        return StateName;
    }

    public void setStateName(String StateName) {
        this.StateName = StateName;
    }
    
    public String getHouseNo() {
        return HouseNo;
    }

    public void setHouseNo(String HouseNo) {
        this.HouseNo = HouseNo;
    }
  

    public String getStreet() {
        return Street;
    }

    public int getStateID() {
        return StateID;
    }

    public void setStateID(int StateID) {
        this.StateID = StateID;
    }

    public void setStreet(String Street) {
        this.Street = Street;
    }

    public String getZipCode() {
        return ZipCode;
    }

    public void setZipCode(String ZipCode) {
        this.ZipCode = ZipCode;
    }

    public int getClinicID() {
        return ClinicID;
    }

    public void setClinicID(int ClinicID) {
        this.ClinicID = ClinicID;
    }

    public String getClinicName() {
        return ClinicName;
    }

    public void setClinicName(String ClinicName) {
        this.ClinicName = ClinicName;
    }

    public String getFaxNo() {
        return FaxNo;
    }

    public void setFaxNo(String FaxNo) {
        this.FaxNo = FaxNo;
    }

    public int getAdressID() {
        return AdressID;
    }

    public void setAdressID(int AdressID) {
        this.AdressID = AdressID;
    }

    public String getPhoneNo() {
        return PhoneNo;
    }

    public void setPhoneNo(String PhoneNo) {
        this.PhoneNo = PhoneNo;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int Status) {
        this.Status = Status;
    }

    public int getCityID() {
        return CityID;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public int getStaffCount() {
        return StaffCount;
    }

    public void setStaffCount(int StaffCount) {
        this.StaffCount = StaffCount;
    }

    public int getDoctorCount() {
        return DoctorCount;
    }

    public void setDoctorCount(int DoctorCount) {
        this.DoctorCount = DoctorCount;
    }

    public void setCityID(int CityID) {
        this.CityID = CityID;
    }

    public int getUserCount() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
