/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.medq.util;

import com.medq.dto.Document;
import com.medq.dto.Referral;
import com.medq.dto.User;
import java.util.List;

/**
 *
 * @author eshine
 */
public class GetStringofArrayof {

    public static String getstringOfArrayOfStaff(List<User> arrayOfStaff) {
        String stringOfArrayOfStaff = "";
        for (User arrayOfStaff1 : arrayOfStaff) {
            User user = (User) arrayOfStaff1;
            stringOfArrayOfStaff = stringOfArrayOfStaff.concat(user.getFirstName() + ",");
            stringOfArrayOfStaff = stringOfArrayOfStaff.concat(user.getLastName() + ",");
            stringOfArrayOfStaff = stringOfArrayOfStaff.concat(user.getDeskPhone() + ",");
            stringOfArrayOfStaff = stringOfArrayOfStaff.concat(user.getCellPhone() + ",");
            stringOfArrayOfStaff = stringOfArrayOfStaff.concat(user.getPassword() + ",");
            stringOfArrayOfStaff = stringOfArrayOfStaff.concat(user.getEmail() + ",");
            stringOfArrayOfStaff = stringOfArrayOfStaff.concat(user.isIsNotificationON() + ",");
            stringOfArrayOfStaff = stringOfArrayOfStaff.concat(user.getRoleID() + "|");
        }

        return stringOfArrayOfStaff;
    }

    public static String getstringOfArrayOfDoctor(List<User> arrayOfDoctor) {
        String stringOfArrayOfDoctor = "";
        int intItr;
        for (User arrayOfDoctor1 : arrayOfDoctor) {
            User user = (User) arrayOfDoctor1;

            stringOfArrayOfDoctor = stringOfArrayOfDoctor.concat(user.getHouseNo() + ",");
            stringOfArrayOfDoctor = stringOfArrayOfDoctor.concat(user.getStreet() + ",");
            stringOfArrayOfDoctor = stringOfArrayOfDoctor.concat(user.getCityID() + ",");
            stringOfArrayOfDoctor = stringOfArrayOfDoctor.concat(user.getStateID() + ",");
            stringOfArrayOfDoctor = stringOfArrayOfDoctor.concat(user.getZipCode() + "|#refque!|");

            //Credentials
            for (intItr = 0; intItr < user.getCredentialList().size(); intItr++) {
                if (intItr == 0) {
                    stringOfArrayOfDoctor = stringOfArrayOfDoctor.concat(user.getCredentialList().get(intItr) + "");
                } else {
                    stringOfArrayOfDoctor = stringOfArrayOfDoctor.concat("," + user.getCredentialList().get(intItr));
                }
            }
            stringOfArrayOfDoctor = stringOfArrayOfDoctor.concat(" |#refque!|");

            //Insurance
            for (intItr = 0; intItr < user.getInsuranceType().size(); intItr++) {
                if (intItr == 0) {
                    stringOfArrayOfDoctor = stringOfArrayOfDoctor.concat(user.getInsuranceType().get(intItr) + "");
                } else {
                    stringOfArrayOfDoctor = stringOfArrayOfDoctor.concat("," + user.getInsuranceType().get(intItr));
                }
            }
            stringOfArrayOfDoctor = stringOfArrayOfDoctor.concat(" |#refque!|");

            //Working days
            for (intItr = 0; intItr < user.getDays().size(); intItr++) {
                if (intItr == 0) {
                    stringOfArrayOfDoctor = stringOfArrayOfDoctor.concat(user.getDays().get(intItr) + "");
                } else {
                    stringOfArrayOfDoctor = stringOfArrayOfDoctor.concat("," + user.getDays().get(intItr));

                }
            }
            stringOfArrayOfDoctor = stringOfArrayOfDoctor.concat(" |#refque!|");

            
            // SpecialistReports           
            for (intItr = 0; intItr < user.getSpecialistReports().size(); intItr++) {
                if (intItr == 0) {
                    stringOfArrayOfDoctor = stringOfArrayOfDoctor.concat(user.getSpecialistReports().get(intItr) + "");
                } else {
                    stringOfArrayOfDoctor = stringOfArrayOfDoctor.concat("," + user.getSpecialistReports().get(intItr));
                }

            }
            if (user.getSpecialistReports().isEmpty()) {
                stringOfArrayOfDoctor = stringOfArrayOfDoctor.concat("blank");
            }
            stringOfArrayOfDoctor = stringOfArrayOfDoctor.concat("|#refque!|");
           
            //Other Reports          
            for (intItr = 0; intItr < user.getNewAddedReports().size(); intItr++) {
                if (intItr == 0) {
                    stringOfArrayOfDoctor = stringOfArrayOfDoctor.concat(user.getNewAddedReports().get(intItr) + "");
                } else {
                    stringOfArrayOfDoctor = stringOfArrayOfDoctor.concat("," + user.getNewAddedReports().get(intItr));
                }

            }
            if (user.getNewAddedReports().isEmpty()) {
                stringOfArrayOfDoctor = stringOfArrayOfDoctor.concat("blank");
            }
            stringOfArrayOfDoctor = stringOfArrayOfDoctor.concat(" |#refque!|");

            // User Details 
            stringOfArrayOfDoctor = stringOfArrayOfDoctor.concat(user.getFirstName() + ",");
            stringOfArrayOfDoctor = stringOfArrayOfDoctor.concat(user.getLastName() + ",");
            stringOfArrayOfDoctor = stringOfArrayOfDoctor.concat(user.getDeskPhone() + ",");
            stringOfArrayOfDoctor = stringOfArrayOfDoctor.concat(user.getCellPhone() + ",");
            stringOfArrayOfDoctor = stringOfArrayOfDoctor.concat(user.getFaxNo() + ",");
            stringOfArrayOfDoctor = stringOfArrayOfDoctor.concat(user.getSpecializationID() + ",");
            stringOfArrayOfDoctor = stringOfArrayOfDoctor.concat(user.getPassword() + ",");
            stringOfArrayOfDoctor = stringOfArrayOfDoctor.concat(user.getEmail() + ",");
            stringOfArrayOfDoctor = stringOfArrayOfDoctor.concat(user.getDoctorType() + ",");
            stringOfArrayOfDoctor = stringOfArrayOfDoctor.concat(user.isIsDocOnlyContact() + "|#refque!||");
        }

        return stringOfArrayOfDoctor;
    }

    public static String getstringOfArrayOfReferalDtl(Referral referral) {
        String referralDtl = "";
        int intItr;

        //set current Time 
        referral.setCreatedTime(new java.sql.Timestamp(new java.util.Date().getTime()));

        referralDtl = referralDtl.concat(referral.getReferredBy() + ",");
        referralDtl = referralDtl.concat(referral.getUrgency() + ",");
        referralDtl = referralDtl.concat(referral.getPatientID() + ",");
        referralDtl = referralDtl.concat(referral.getCreatedTime() + "|#refque!|");

        //Referral Diseaes
        for (intItr = 0; intItr < referral.getSelectedReasons().size(); intItr++) {
            if (intItr == 0) {
                referralDtl = referralDtl.concat(referral.getSelectedReasons().get(intItr) + "");
            } else {
                referralDtl = referralDtl.concat("," + referral.getSelectedReasons().get(intItr));
            }

        }
        if (referral.getSelectedReasons().isEmpty()) {
            referralDtl = referralDtl.concat("blank");
        }
        referralDtl = referralDtl.concat(" |#refque!|");

        //Reffered To
        for (intItr = 0; intItr < referral.getSelectedDoctors().size(); intItr++) {
            if (intItr == 0) {
                referralDtl = referralDtl.concat(referral.getSelectedDoctors().get(intItr) + "");
            } else {
                referralDtl = referralDtl.concat("," + referral.getSelectedDoctors().get(intItr));
            }

        }
        referralDtl = referralDtl.concat(" |#refque!|");

        //Document
        for (intItr = 0; intItr < referral.getSelectedRequiredDocuments().size(); intItr++) {
            Document doc = referral.getSelectedRequiredDocuments().get(intItr);
            if (intItr == 0) {
                referralDtl = referralDtl.concat(doc.getDocumentUrl() + "," + doc.getRequirementID());
            } else {
                referralDtl = referralDtl.concat("#" + doc.getDocumentUrl() + "," + doc.getRequirementID());
            }

        }
        if (referral.getSelectedRequiredDocuments().isEmpty()) {
            referralDtl = referralDtl.concat("blank");
        }
        referralDtl = referralDtl.concat(" |#refque!|");

        //Other Diseaes
        for (intItr = 0; intItr < referral.getSelectedOtherReasons().size(); intItr++) {
            if (intItr == 0) {
                referralDtl = referralDtl.concat(referral.getSelectedOtherReasons().get(intItr) + "");
            } else {
                referralDtl = referralDtl.concat("," + referral.getSelectedOtherReasons().get(intItr));
            }

        }
        if (referral.getSelectedOtherReasons().isEmpty()) {
            referralDtl = referralDtl.concat("blank");
        }
        referralDtl = referralDtl.concat(" |#refque!|");

        //Other Reoprt 
        for (intItr = 0; intItr < referral.getSelectedOtherRequiredDocuments().size(); intItr++) {
            Document doc = referral.getSelectedOtherRequiredDocuments().get(intItr);
            if (intItr == 0) {
                referralDtl = referralDtl.concat(doc.getDocumentUrl() + "," + doc.getRequirementName());
            } else {
                referralDtl = referralDtl.concat("#" + doc.getDocumentUrl() + "," + doc.getRequirementName());
            }

        }
        if (referral.getSelectedOtherRequiredDocuments().isEmpty()) {
            referralDtl = referralDtl.concat("blank");
        }
        referralDtl = referralDtl.concat(" |#refque!|");

        return referralDtl;
    }

}
