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
public class Patient {

    private int PatientID;
    private int AddressID;
    private String FirstName;
    private String LastName;
    private String PhoneNo;
    private String Email;
    private String DOB;
    private ArrayList<Integer> InsuranceID;
    private ArrayList<String> InsuranceNames;
    private Timestamp DOBtimestamp;

    //for IOS App
    private int TreatedBy;
    private String FullName;

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String FullName) {
        this.FullName = FullName;
    }

    public int getTreatedBy() {
        return TreatedBy;
    }

    public void setTreatedBy(int TreatedBy) {
        this.TreatedBy = TreatedBy;
    }

    //For ReferralList Response
    private String ReferredAcceptedDoctorName;
    private Timestamp RequestAcceptedTime;
    private int ReferralCount;
    private int ReferralID;

    public int getReferralID() {
        return ReferralID;
    }

    public void setReferralID(int ReferralID) {
        this.ReferralID = ReferralID;
    }

    //For ReferredFrom List Response
    private Timestamp RefferedToTime;

    public Timestamp getRefferedToTime() {
        return RefferedToTime;
    }

    public void setRefferedToTime(Timestamp RefferedToTime) {
        this.RefferedToTime = RefferedToTime;
    }

    public int getReferralCount() {
        return ReferralCount;
    }

    public void setReferralCount(int ReferralCount) {
        this.ReferralCount = ReferralCount;
    }

    public Timestamp getRequestAcceptedTime() {
        return RequestAcceptedTime;
    }

    public void setRequestAcceptedTime(Timestamp RequestAcceptedTime) {
        this.RequestAcceptedTime = RequestAcceptedTime;
    }

    public String getReferredAcceptedDoctorName() {
        return ReferredAcceptedDoctorName;
    }

    public void setReferredAcceptedDoctorName(String ReferredAcceptedDoctorName) {
        this.ReferredAcceptedDoctorName = ReferredAcceptedDoctorName;
    }

    public Timestamp getDOBtimestamp() {
        return DOBtimestamp;
    }

    public void setDOBtimestamp(Timestamp DOBtimestamp) {
        this.DOBtimestamp = DOBtimestamp;
    }

    public ArrayList<String> getInsuranceNames() {
        return InsuranceNames;
    }

    public void setInsuranceNames(ArrayList<String> InsuranceNames) {
        this.InsuranceNames = InsuranceNames;
    }

    public ArrayList<Integer> getInsuranceID() {
        return InsuranceID;
    }

    public void setInsuranceID(ArrayList<Integer> InsurranceID) {
        this.InsuranceID = InsurranceID;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }
    private String IDProofPicUrl;
    private String InsuranceIDfrontpicUrl;
    private String InsuranceIDbackpicUrl;
    private int ClinicID;

    private String HouseNo;

    public String getHouseNo() {
        return HouseNo;
    }

    public void setHouseNo(String HouseNo) {
        this.HouseNo = HouseNo;
    }
    private String Street;
    private int CityID;
    private int StateID;
    private String ZipCode;

    public String getStreet() {
        return Street;
    }

    public void setStreet(String Street) {
        this.Street = Street;
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

    public String getZipCode() {
        return ZipCode;
    }

    public void setZipCode(String ZipCode) {
        this.ZipCode = ZipCode;
    }

    public String getInsuranceIDfrontpicUrl() {
        return InsuranceIDfrontpicUrl;
    }

    public void setInsuranceIDfrontpicUrl(String InsuranceIDfrontpicUrl) {
        this.InsuranceIDfrontpicUrl = InsuranceIDfrontpicUrl;
    }

    public String getInsuranceIDbackpicUrl() {
        return InsuranceIDbackpicUrl;
    }

    public void setInsuranceIDbackpicUrl(String InsuranceIDbackpicUrl) {
        this.InsuranceIDbackpicUrl = InsuranceIDbackpicUrl;
    }

    public int getClinicID() {
        return ClinicID;
    }

    public void setClinicID(int ClinicID) {
        this.ClinicID = ClinicID;
    }

    public int getPatientID() {
        return PatientID;
    }

    public void setPatientID(int PatientID) {
        this.PatientID = PatientID;
    }

    public int getAddressID() {
        return AddressID;
    }

    public void setAddressID(int AddressID) {
        this.AddressID = AddressID;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String FirstName) {
        this.FirstName = FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String LastName) {
        this.LastName = LastName;
    }

    public String getPhoneNo() {
        return PhoneNo;
    }

    public void setPhoneNo(String PhoneNo) {
        this.PhoneNo = PhoneNo;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getIDProofPicUrl() {
        return IDProofPicUrl;
    }

    public void setIDProofPicUrl(String IDProofPicUrl) {
        this.IDProofPicUrl = IDProofPicUrl;
    }
}
