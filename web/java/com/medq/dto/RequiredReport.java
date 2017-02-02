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
public class RequiredReport {
    
    private int RequirementID;
   private String RequirementName;
   private int SpecializationID;

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

    public int getSpecializationID() {
        return SpecializationID;
    }

    public void setSpecializationID(int SpecializationID) {
        this.SpecializationID = SpecializationID;
    }
    
}
