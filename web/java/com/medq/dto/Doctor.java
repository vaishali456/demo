/*
 * To change this license header; choose License Headers in Project Properties.
 * To change this template file; choose Tools | Templates
 * and open the template in the editor.
 */
package com.medq.dto;

/**
 *
 * @author eshine
 */
public class Doctor {

    private int DoctorID;
    private String FirstName;
    private String LastName;
    private int ClinicID;
    private int AddressID;
    private String PhonePrimary;
    private String PhoneSecondary;
    private String FaxNo;
    private int SpecializationID;
    private String Password;
    private String ImageUrlOriginal;
    private String Email;
    private String ImageUrlThumbnail;
    private String ImageUrlBlur;
    private int Status;
    private int RoleID;
    private int cityID;
    private int stateID;
    private String HouseNo;

    public String getHouseNo() {
        return HouseNo;
    }

    public void setHouseNo(String HouseNo) {
        this.HouseNo = HouseNo;
    }
    private String Street;
    private String ZipCode;

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

    public int getCityID() {
        return cityID;
    }

    public void setCityID(int cityID) {
        this.cityID = cityID;
    }

    public int getStateID() {
        return stateID;
    }

    public void setStateID(int stateID) {
        this.stateID = stateID;
    }

    public int getDoctorID() {
        return DoctorID;
    }

    public void setDoctorID(int DoctorID) {
        this.DoctorID = DoctorID;
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

    public String getPhonePrimary() {
        return PhonePrimary;
    }

    public void setPhonePrimary(String PhonePrimary) {
        this.PhonePrimary = PhonePrimary;
    }

    public String getPhoneSecondary() {
        return PhoneSecondary;
    }

    public void setPhoneSecondary(String PhoneSecondary) {
        this.PhoneSecondary = PhoneSecondary;
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

    public int getStatus() {
        return Status;
    }

    public void setStatus(int Status) {
        this.Status = Status;
    }

    public int getRoleID() {
        return RoleID;
    }

    public void setRoleID(int RoleID) {
        this.RoleID = RoleID;
    }

}
