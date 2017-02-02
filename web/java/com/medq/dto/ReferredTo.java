/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.medq.dto;

/**
 *
 * @author eshine
 */
public class ReferredTo {

    private int ReferralID;
    private int SpecialistID;
    private int ReferredBy;
    private Boolean IsReferralAccepted;

    public int getReferralID() {
        return ReferralID;
    }

    public void setReferralID(int ReferralID) {
        this.ReferralID = ReferralID;
    }

    public int getSpecialistID() {
        return SpecialistID;
    }

    public void setSpecialistID(int SpecialistID) {
        this.SpecialistID = SpecialistID;
    }

    public int getReferredBy() {
        return ReferredBy;
    }

    public void setReferredBy(int ReferredBy) {
        this.ReferredBy = ReferredBy;
    }

    public Boolean getIsReferralAccepted() {
        return IsReferralAccepted;
    }

    public void setIsReferralAccepted(Boolean IsReferralAccepted) {
        this.IsReferralAccepted = IsReferralAccepted;
    }

}
