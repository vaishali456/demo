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
public class Disease {

    private int DiseaseID;
    private String DiseaseName;
    private int SpecializationID;

    public int getDiseaseID() {
        return DiseaseID;
    }

    public void setDiseaseID(int DiseaseID) {
        this.DiseaseID = DiseaseID;
    }

    public String getDiseaseName() {
        return DiseaseName;
    }

    public void setDiseaseName(String DiseaseName) {
        this.DiseaseName = DiseaseName;
    }

    public int getSpecializationID() {
        return SpecializationID;
    }

    public void setSpecializationID(int SpecializationID) {
        this.SpecializationID = SpecializationID;
    }

}
