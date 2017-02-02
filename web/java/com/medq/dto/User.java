/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.medq.dto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author eshine
 */
public class User {

    private int UserID;
    private String FirstName;
    private String LastName;
    private int ClinicID;
    private int AddressID;
    private String DeskPhone;
    private String CellPhone;
    private String FaxNo;
    private int SpecializationID;
    private String Password;
    private String ImageUrlOriginal;
    private String Email;
    private String ImageUrlThumbnail;
    private String ImageUrlBlur;
    private boolean DoctorAvailability;
    private boolean IsDocOnlyContact;
    private int RoleID;
    private boolean IsNotificationON;
    private boolean IsApproved;
    private int Status;
    private int MessageCount;
    private int SenderID;
    private boolean IsOnline;
    private String SpecializationName;

    //Get Referral details in referral notification
    private int ReferralID;

    private int CityID;

    private int StateID;
    private String HouseNo;
    private String Street;
    private String ZipCode;
    private String StateName;
    private String CityName;

    private String doctorType;

    public String getDoctorType() {
        return doctorType;
    }

    public void setDoctorType(String doctorType) {
        this.doctorType = doctorType;
    }

    //Field for File Upload and newReport for Signup
    private String DocumentUrl;
    private ArrayList<String> newAddedReports;

    ArrayList<Integer> CredentialList = new ArrayList();
    ArrayList<Integer> InsuranceType = new ArrayList();
    ArrayList<String> days = new ArrayList();
    ArrayList<Integer> specialistReports = new ArrayList();

    List<String> CredentialNameList = new ArrayList();
    List<String> InsuranceNameList = new ArrayList();
    List<String> specialistReportsNameList = new ArrayList();

    private String RoleName;

    public List<String> getCredentialNameList() {
        return CredentialNameList;
    }

    public void setCredentialNameList(List<String> CredentialNameList) {
        this.CredentialNameList = CredentialNameList;
    }

    public List<String> getInsuranceNameList() {
        return InsuranceNameList;
    }

    public void setInsuranceNameList(List<String> InsuranceNameList) {
        this.InsuranceNameList = InsuranceNameList;
    }

    public List<String> getSpecialistReportsNameList() {
        return specialistReportsNameList;
    }

    public void setSpecialistReportsNameList(List<String> specialistReportsNameList) {
        this.specialistReportsNameList = specialistReportsNameList;
    }

    public String getStateName() {
        return StateName;
    }

    public void setStateName(String StateName) {
        this.StateName = StateName;
    }

    public String getCityName() {
        return CityName;
    }

    public void setCityName(String CityName) {
        this.CityName = CityName;
    }

    public String getRoleName() {
        return RoleName;
    }

    public void setRoleName(String RoleName) {
        this.RoleName = RoleName;
    }

    public int getReferralID() {
        return ReferralID;
    }

    public void setReferralID(int ReferralID) {
        this.ReferralID = ReferralID;
    }

    public ArrayList<String> getNewAddedReports() {
        return newAddedReports;
    }

    public void setNewAddedReports(ArrayList<String> newAddedReports) {
        this.newAddedReports = newAddedReports;
    }

    public String getDocumentUrl() {
        return DocumentUrl;
    }

    public void setDocumentUrl(String DocumentUrl) {
        this.DocumentUrl = DocumentUrl;
    }

    public boolean isIsOnline() {
        return IsOnline;
    }

    public void setIsOnline(boolean IsOnline) {
        this.IsOnline = IsOnline;
    }

    public String getSpecializationName() {
        return SpecializationName;
    }

    public void setSpecializationName(String SpecializationName) {
        this.SpecializationName = SpecializationName;
    }

    public int getSenderID() {
        return SenderID;
    }

    public void setSenderID(int SenderID) {
        this.SenderID = SenderID;
    }

    public int getMessageCount() {
        return MessageCount;
    }

    public void setMessageCount(int MessageCount) {
        this.MessageCount = MessageCount;
    }

    public ArrayList<Integer> getInsuranceType() {
        return InsuranceType;
    }

    public void setInsuranceType(ArrayList<Integer> InsuranceType) {
        this.InsuranceType = InsuranceType;

    }

    public ArrayList<String> getDays() {
        return days;
    }

    public void setDays(ArrayList<String> days) {
        this.days = days;
    }

    public ArrayList<Integer> getSpecialistReports() {
        return specialistReports;
    }

    public void setSpecialistReports(ArrayList<Integer> specialistReports) {
        this.specialistReports = specialistReports;
    }

    public ArrayList<Integer> getCredentialList() {
        return CredentialList;
    }

    public void setCredentialList(ArrayList<Integer> CredentialList) {
        this.CredentialList = CredentialList;
    }

    public boolean isDoctorAvailability() {
        return DoctorAvailability;
    }

    public void setDoctorAvailability(boolean DoctorAvailability) {
        this.DoctorAvailability = DoctorAvailability;
    }

    public boolean isIsDocOnlyContact() {
        return IsDocOnlyContact;
    }

    public void setIsDocOnlyContact(boolean IsDocOnlyContact) {
        this.IsDocOnlyContact = IsDocOnlyContact;
    }

    public boolean isIsApproved() {
        return IsApproved;
    }

    public void setIsApproved(boolean IsApproved) {
        this.IsApproved = IsApproved;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int UserID) {
        this.UserID = UserID;
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

    public int getClinicID() {
        return ClinicID;
    }

    public void setClinicID(int ClinicID) {
        this.ClinicID = ClinicID;
    }

    public int getAddressID() {
        return AddressID;
    }

    public void setAddressID(int AddressID) {
        this.AddressID = AddressID;
    }

    public String getDeskPhone() {
        return DeskPhone;
    }

    public void setDeskPhone(String DeskPhone) {
        this.DeskPhone = DeskPhone;
    }

    public String getCellPhone() {
        return CellPhone;
    }

    public void setCellPhone(String CellPhone) {
        this.CellPhone = CellPhone;
    }

    public String getFaxNo() {
        return FaxNo;
    }

    public void setFaxNo(String FaxNo) {
        this.FaxNo = FaxNo;
    }

    public int getSpecializationID() {
        return SpecializationID;
    }

    public void setSpecializationID(int SpecializationID) {
        this.SpecializationID = SpecializationID;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getImageUrlOriginal() {
        return ImageUrlOriginal;
    }

    public void setImageUrlOriginal(String ImageUrlOriginal) {
        this.ImageUrlOriginal = ImageUrlOriginal;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getImageUrlThumbnail() {
        return ImageUrlThumbnail;
    }

    public void setImageUrlThumbnail(String ImageUrlThumbnail) {
        this.ImageUrlThumbnail = ImageUrlThumbnail;
    }

    public String getImageUrlBlur() {
        return ImageUrlBlur;
    }

    public void setImageUrlBlur(String ImageUrlBlur) {
        this.ImageUrlBlur = ImageUrlBlur;
    }

    public int getRoleID() {
        return RoleID;
    }

    public void setRoleID(int RoleID) {
        this.RoleID = RoleID;
    }

    public boolean isIsNotificationON() {
        return IsNotificationON;
    }

    public void setIsNotificationON(boolean IsNotificationON) {
        this.IsNotificationON = IsNotificationON;
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

    public void setCityID(int cityID) {
        this.CityID = cityID;
    }

    public int getStateID() {
        return StateID;
    }

    public void setStateID(int stateID) {
        this.StateID = stateID;
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

    public void setStreet(String Street) {
        this.Street = Street;
    }

    public String getZipCode() {
        return ZipCode;
    }

    public void setZipCode(String ZipCode) {
        this.ZipCode = ZipCode;
    }

}
