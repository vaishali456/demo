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
public class Document {

    private int DocumentID;
    private String DocumentUrl;
    private int UploadBy;
    private int DownloadedBy;
    private Timestamp UploadTime;
    private Timestamp DownloadTime;

    
    private int RequirementID;

    // Field to add Othr Report
    private String RequirementName;

    public int getRequirementID() {
        return RequirementID;
    }

    public void setRequirementID(int RequirementID) {
        this.RequirementID = RequirementID;
    }

    public String getRequirementName() {
        return RequirementName;
    }

    public void setRequirementName(String RequirementName) {
        this.RequirementName = RequirementName;
    }
    
   
    public Timestamp getUploadTime() {
        return UploadTime;
    }

    public void setUploadTime(Timestamp UploadTime) {
        this.UploadTime = UploadTime;
    }

    public Timestamp getDownloadTime() {
        return DownloadTime;
    }

    public void setDownloadTime(Timestamp DownloadTime) {
        this.DownloadTime = DownloadTime;
    }

    private int ReferralId;

    public int getDocumentID() {
        return DocumentID;
    }

    public void setDocumentID(int DocumentID) {
        this.DocumentID = DocumentID;
    }

    public String getDocumentUrl() {
        return DocumentUrl;
    }

    public void setDocumentUrl(String DocumentUrl) {
        this.DocumentUrl = DocumentUrl;
    }

    public int getUploadBy() {
        return UploadBy;
    }

    public void setUploadBy(int UploaBy) {
        this.UploadBy = UploadBy;
    }

    public int getDownloadedBy() {
        return DownloadedBy;
    }

    public void setDownloadedBy(int DownloadedBy) {
        this.DownloadedBy = DownloadedBy;
    }

    public int getReferralId() {
        return ReferralId;
    }

    public void setReferralId(int ReferralId) {
        this.ReferralId = ReferralId;
    }

}
