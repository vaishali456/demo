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
public class City {

    private int CityID;
    private String CityName;
    private int StateID;
    private int Zip;

    public int getZip() {
        return Zip;
    }

    public void setZip(int Zip) {
        this.Zip = Zip;
    }

    public int getCityID() {
        return CityID;
    }

    public void setCityID(int CityID) {
        this.CityID = CityID;
    }

    public String getCityName() {
        return CityName;
    }

    public void setCityName(String CityName) {
        this.CityName = CityName;
    }

    public int getStateID() {
        return StateID;
    }

    public void setStateID(int StateID) {
        this.StateID = StateID;
    }

}
