/*
 * To change this license header; choose License Headers in Project Properties.
 * To change this template file; choose Tools | Templates
 * and open the template in the editor.
 */
package com.medq.dto;

import java.sql.Timestamp;
import java.util.ArrayList;

/**
 *
 * @author eshine
 */
public class Referral {

    private int ReferralID;
    private int ReferredBy;
    private String Urgency;
    private Timestamp CreatedTime;
    private Timestamp UpdatedTime;
    private int PatientID;
    private int UpdatedBy;
    private int CreatedBy;
    private int TreatedBy;
    private int TreatmentStatus;
    private String CommentMessage;

    

    public String getCommentMessage() {
        return CommentMessage;
    }

    public void setCommentMessage(String CommentMessage) {
        this.CommentMessage = CommentMessage;
    }

    // Extra field for createReferral Webservice
    private ArrayList<Integer> selectedReasons = new ArrayList();
    private ArrayList<Integer> selectedDoctors = new ArrayList();
    private ArrayList<Document> selectedRequiredDocuments = new ArrayList();

    //Field to add Othet Reson and report
    private ArrayList<String> selectedOtherReasons = new ArrayList();
    private ArrayList<Document> selectedOtherRequiredDocuments = new ArrayList();

    public int getSelectedSpecialization() {
        return selectedSpecialization;
    }

    public void setSelectedSpecialization(int selectedSpecialization) {
        this.selectedSpecialization = selectedSpecialization;
    }
    private int selectedSpecialization;

    public ArrayList<String> getSelectedOtherReasons() {
        return selectedOtherReasons;
    }

    public void setSelectedOtherReasons(ArrayList<String> selectedOtherReasons) {
        this.selectedOtherReasons = selectedOtherReasons;
    }

    public ArrayList<Document> getSelectedOtherRequiredDocuments() {
        return selectedOtherRequiredDocuments;
    }

    public void setSelectedOtherRequiredDocuments(ArrayList<Document> selectedOtherRequiredDocuments) {
        this.selectedOtherRequiredDocuments = selectedOtherRequiredDocuments;
    }

    public ArrayList<Integer> getSelectedReasons() {
        return selectedReasons;
    }

    public void setSelectedReasons(ArrayList<Integer> selectedReasons) {
        this.selectedReasons = selectedReasons;
    }

    public ArrayList<Document> getSelectedRequiredDocuments() {
        return selectedRequiredDocuments;
    }

    public void setSelectedRequiredDocuments(ArrayList<Document> selectedRequiredDocuments) {
        this.selectedRequiredDocuments = selectedRequiredDocuments;
    }

    public int getReferralID() {
        return ReferralID;
    }

    public ArrayList<Integer> getSelectedDoctors() {
        return selectedDoctors;
    }

    public void setSelectedDoctors(ArrayList<Integer> selectedDoctors) {
        this.selectedDoctors = selectedDoctors;
    }

    public void setReferralID(int ReferralID) {
        this.ReferralID = ReferralID;
    }

    public int getReferredBy() {
        return ReferredBy;
    }

    public void setReferredBy(int ReferredBy) {
        this.ReferredBy = ReferredBy;
    }

    public String getUrgency() {
        return Urgency;
    }

    public void setUrgency(String Urgency) {
        this.Urgency = Urgency;
    }

    public Timestamp getCreatedTime() {
        return CreatedTime;
    }

    public void setCreatedTime(Timestamp CreatedTime) {
        this.CreatedTime = CreatedTime;
    }

    public Timestamp getUpdatedTime() {
        return UpdatedTime;
    }

    public void setUpdatedTime(Timestamp UpdatedTime) {
        this.UpdatedTime = UpdatedTime;
    }

    public int getPatientID() {
        return PatientID;
    }

    public void setPatientID(int PatientID) {
        this.PatientID = PatientID;
    }

    public int getUpdatedBy() {
        return UpdatedBy;
    }

    public void setUpdatedBy(int UpdatedBy) {
        this.UpdatedBy = UpdatedBy;
    }

    public int getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(int CreatedBy) {
        this.CreatedBy = CreatedBy;
    }

    public int getTreatedBy() {
        return TreatedBy;
    }

    public void setTreatedBy(int TreatedBy) {
        this.TreatedBy = TreatedBy;
    }

    public int getTreatmentStatus() {
        return TreatmentStatus;
    }

    public void setTreatmentStatus(int TreatmentStatus) {
        this.TreatmentStatus = TreatmentStatus;
    }

//    Added extra fields to use to bind the specializatinID In
//    getReferralDtl service
    private int SpecializationID;
    private int ClinicID;

    public int getClinicID() {
        return ClinicID;
    }

    public void setClinicID(int ClinicID) {
        this.ClinicID = ClinicID;
    }

    public int getSpecializationID() {
        return SpecializationID;
    }

    public void setSpecializationID(int SpecializationID) {
        this.SpecializationID = SpecializationID;
    }

}
