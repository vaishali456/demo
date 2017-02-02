package com.medq.services;

import java.util.ArrayList;
import com.medq.dao.QueryHelper;
import com.medq.dto.*;
import com.medq.util.SendMail;
import com.medq.util.SendMailForForgotPassword;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.TimeZone;
import com.medq.util.Util;
import java.util.Map;

public class Service {

    private static final SimpleDateFormat GMT_yMd = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat GMT_yMd_Hms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    static {
        GMT_yMd.setTimeZone(TimeZone.getTimeZone("GMT"));
        GMT_yMd_Hms.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    private int userid = 0;

    @SuppressWarnings("unchecked")
    public HashMap login(String emailid, String password) {

        HashMap responseHashMap;
        User loginuser;
        try {
            QueryHelper objQueryHelper = new QueryHelper();
            loginuser = objQueryHelper.login(emailid, password);
            if (loginuser.getUserID() > 0) {
                userid = loginuser.getUserID();
                responseHashMap = Util.setObjectResponse(loginuser);
            } else {
//                userid = 0;
                responseHashMap = Util.setInvalidUser();        // Specific for a failed user login return 401
            }
        } catch (Exception exception) {
            responseHashMap = Util.setErrorResponse(exception);
        }
        return responseHashMap;
    }

    public HashMap logout() {
        HashMap responseHashMap;
        if (userid > 0) {
            try {
                QueryHelper objQueryHelper = new QueryHelper();
                responseHashMap = Util.setObjectResponse(objQueryHelper.logout(userid));
                userid = 0;
            } catch (Exception exception) {
                responseHashMap = Util.setErrorResponse(exception);
            }
        } else {
            responseHashMap = Util.setInvalidSession();
        }
        return responseHashMap;
    }

    public HashMap user() {
        HashMap responseHashMap;
        User loginuser;

        try {
            QueryHelper objQueryHelper = new QueryHelper();
            loginuser = objQueryHelper.User();
            if (loginuser.getUserID() > 0) {

                responseHashMap = Util.setObjectResponse(loginuser);
            } else {
                userid = 0;
                responseHashMap = Util.setInvalidUser();        // Specific for a failed user login return 401
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            responseHashMap = Util.setErrorResponse(exception);
        }

        return responseHashMap;
    }

    public HashMap state() {
        HashMap responseHashMap;
        try {
            QueryHelper objQueryHelper = new QueryHelper();
            ArrayList statelist = objQueryHelper.state();
            if (!statelist.isEmpty()) {
                responseHashMap = Util.setArrayResponse(statelist);
            } else {
                responseHashMap = Util.setArrayResponseEmptyArray(statelist);        // Specific for a failed user login return 401
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            responseHashMap = Util.setErrorResponse(exception);
        }

        return responseHashMap;
    }

    public HashMap city(int stateid) {
        HashMap responseHashMap;
        try {
            QueryHelper objQueryHelper = new QueryHelper();
            ArrayList citylist = objQueryHelper.city(stateid);
            if (!citylist.isEmpty()) {
                responseHashMap = Util.setArrayResponse(citylist);
            } else {
                responseHashMap = Util.setArrayResponseEmptyArray(citylist);        // Specific for a failed user login return 401
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            responseHashMap = Util.setErrorResponse(exception);
        }

        return responseHashMap;
    }

    public HashMap getZipCode(String cityname) {
        HashMap responseHashMap;
        try {
            QueryHelper objQueryHelper = new QueryHelper();
            ArrayList zipcodelist = objQueryHelper.getZipCode(cityname);
            if (!zipcodelist.isEmpty()) {
                responseHashMap = Util.setArrayResponse(zipcodelist);
            } else {
                responseHashMap = Util.setArrayResponseEmptyArray(zipcodelist);        // Specific for a failed user login return 401
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            responseHashMap = Util.setErrorResponse(exception);
        }

        return responseHashMap;
    }

    public HashMap specialization() {
        HashMap responseHashMap;
        try {
            QueryHelper objQueryHelper = new QueryHelper();
            ArrayList specializationlist = objQueryHelper.specialization();
            if (!specializationlist.isEmpty()) {
                responseHashMap = Util.setArrayResponse(specializationlist);
            } else {
                responseHashMap = Util.setArrayResponseEmptyArray(specializationlist);
            }

        } catch (Exception exception) {
            exception.printStackTrace();
            responseHashMap = Util.setErrorResponse(exception);
        }

        return responseHashMap;
    }

    public HashMap getClinicAndReasonForRefer(int specialid, int patientid) {
        HashMap responseHashMap;
        try {
            QueryHelper objQueryHelper = new QueryHelper();
            HashMap<String, ArrayList> getclinicandreasonforefer = objQueryHelper.getClinicAndReasonForRefer(specialid, patientid);
            if (!getclinicandreasonforefer.isEmpty()) {
                responseHashMap = Util.setObjectResponse(getclinicandreasonforefer);
            } else {
                responseHashMap = Util.setObjectResponse(getclinicandreasonforefer);
            }

        } catch (Exception exception) {
            exception.printStackTrace();
            responseHashMap = Util.setErrorResponse(exception);
        }

        return responseHashMap;
    }

    public HashMap changePassword(String password, String newpassword) {
        HashMap responseHashMap = Util.getInitialHashMap();
        try {
            if (userid > 0) {
                QueryHelper objQueryHelper = new QueryHelper();
                objQueryHelper.changePassword(userid, password, newpassword);
            } else {
                responseHashMap = Util.setInvalidSession();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            responseHashMap = Util.setErrorResponse(exception);
        }
        return responseHashMap;
    }

    public HashMap addPatient(Patient patient) {
        HashMap responseHashMap;

        if (userid > 0) {
            try {
                QueryHelper objQueryHelper = new QueryHelper();
                String ResultMsg = objQueryHelper.addPatient(patient, userid);

                responseHashMap = Util.setObjectResponse(ResultMsg);

            } catch (Exception exception) {
                exception.printStackTrace();
                responseHashMap = Util.setErrorResponse(exception);
            }
        } else {
            responseHashMap = Util.setInvalidSession();
        }

        return responseHashMap;
    }

    public HashMap editPatient(Patient patient) {
        HashMap responseHashMap = null;
        if (userid > 0) {
            try {
                QueryHelper objQueryHelper = new QueryHelper();
                String message = objQueryHelper.editPatient(patient);
                if (message != null) {
                    responseHashMap = Util.setObjectResponse(message);
                }

            } catch (Exception exception) {
                exception.printStackTrace();
                responseHashMap = Util.setErrorResponse(exception);
            }
        } else {
            responseHashMap = Util.setInvalidSession();
        }

        return responseHashMap;
    }

    public HashMap getPatientDetails(int id) {
        HashMap responseHashMap;
        if (userid > 0) {
            try {
                QueryHelper objQueryHelper = new QueryHelper();
                HashMap patientlist = objQueryHelper.getPatientDetails(id);
                if (!patientlist.isEmpty()) {
                    responseHashMap = Util.setObjectResponse(patientlist);
                } else {
                    responseHashMap = Util.setArrayResponseEmptyArray(patientlist);
                }

            } catch (Exception exception) {
                exception.printStackTrace();
                responseHashMap = Util.setErrorResponse(exception);
            }
        } else {
            responseHashMap = Util.setInvalidSession();
        }

        return responseHashMap;
    }

    public HashMap diseaseLookup(int specializationid) {
        HashMap responseHashMap;
        try {
            QueryHelper objQueryHelper = new QueryHelper();
            ArrayList diseaselist = objQueryHelper.diseaseLookup(specializationid);
            if (!diseaselist.isEmpty()) {
                responseHashMap = Util.setArrayResponse(diseaselist);
            } else {

                responseHashMap = Util.setArrayResponseEmptyArray(diseaselist);        // Specific for a failed user login return 401
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            responseHashMap = Util.setErrorResponse(exception);
        }
        return responseHashMap;
    }

    public HashMap getReceivedFriendRequest() {
        HashMap responseHashMap;
        if (userid > 0) {
            try {
                QueryHelper objQueryHelper = new QueryHelper();
                ArrayList requestlist = objQueryHelper.getReceivedFriendRequest(userid);
                if (requestlist.isEmpty()) {
                    responseHashMap = Util.setArrayResponseEmptyArray(requestlist);
                } else {

                    responseHashMap = Util.setArrayResponse(requestlist);
                }
            } catch (Exception exception) {
                exception.printStackTrace();
                responseHashMap = Util.setErrorResponse(exception);
            }
        } else {
            responseHashMap = Util.setInvalidSession();
        }
        return responseHashMap;
    }

    public HashMap getMyContactList() {
        HashMap responseHashMap;
        if (userid > 0) {
            try {
                QueryHelper objQueryHelper = new QueryHelper();
                ArrayList requestlist = objQueryHelper.getMyContactList(userid);
                if (requestlist.isEmpty()) {
                    responseHashMap = Util.setArrayResponseEmptyArray(requestlist);
                } else {
                    responseHashMap = Util.setObjectResponse(requestlist);
                }
            } catch (Exception exception) {
                exception.printStackTrace();
                responseHashMap = Util.setErrorResponse(exception);
            }
        } else {

            responseHashMap = Util.setInvalidSession();
        }
        return responseHashMap;
    }

    public HashMap getReferredToList() {
        HashMap responseHashMap = Util.getInitialHashMap();

        ArrayList<Patient> referredtolist;
        if (userid > 0) {
            try {
                QueryHelper objQueryHelper = new QueryHelper();
                ArrayList<Integer> clinicStaffList = objQueryHelper.getClinicStaff(1);
                for (int i = 0; i < clinicStaffList.size(); i++) {
                    referredtolist = objQueryHelper.getReferredToList(clinicStaffList.get(i));
                    responseHashMap.put(clinicStaffList.get(i), referredtolist);
                }
            } catch (Exception exception) {
                exception.printStackTrace();
                responseHashMap = Util.setErrorResponse(exception);
            }
        } else {

            responseHashMap = Util.setInvalidSession();
        }

        return responseHashMap;
    }

    public HashMap getChatHistory(int receiverid, String lastmessagetimestamp, int selectedgroupid) {
        HashMap responseHashMap;
        HashMap responseChatHistory;
        if (userid > 0) {
            try {
                QueryHelper objQueryHelper = new QueryHelper();
                responseChatHistory = objQueryHelper.getChatHistory(receiverid, userid, lastmessagetimestamp, selectedgroupid);
                if (!responseChatHistory.isEmpty()) {
                    responseHashMap = Util.setObjectResponse(responseChatHistory);

                } else {

                    responseHashMap = Util.setInvalidUser();        // Specific for a failed user login return 401
                }
            } catch (Exception exception) {
                exception.printStackTrace();
                responseHashMap = Util.setErrorResponse(exception);
            }
        } else {

            responseHashMap = Util.setInvalidSession();
        }
        return responseHashMap;
    }

    public HashMap leaveGroup(int groupid) {
        HashMap responseHashMap;
        String message;
        if (userid > 0) {
            try {
                QueryHelper objQueryHelper = new QueryHelper();

                message = objQueryHelper.leaveGroup(groupid, userid);

//                if (message == "") {
//
//                    return null;
//                } else {
//
                responseHashMap = Util.setInvalidUser();        // Specific for a failed user login return 401
//                }
            } catch (Exception exception) {
                exception.printStackTrace();
                responseHashMap = Util.setErrorResponse(exception);
            }
        } else {

            responseHashMap = Util.setInvalidSession();
        }
        return responseHashMap;
    }

    public HashMap getRequiredReport() {
        HashMap responseHashMap;
        ArrayList<RequiredReport> requiredReportslist;
        try {
            QueryHelper objQueryHelper = new QueryHelper();
            requiredReportslist = objQueryHelper.getRequiredReport();
            if (!requiredReportslist.isEmpty()) {

                responseHashMap = Util.setObjectResponse(requiredReportslist);
            } else {

                responseHashMap = Util.setResponseForInvalidInput();        // Specific for a failed user login return 401
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            responseHashMap = Util.setErrorResponse(exception);
        }

        return responseHashMap;
    }

    public HashMap getPatientListAsPerClinic(int patientstatus) {
        HashMap responseHashMap;
        ArrayList<Patient> PatientList;
        if (userid > 0) {
            try {
                QueryHelper objQueryHelper = new QueryHelper();
                PatientList = objQueryHelper.getPatientListAsPerClinic(userid, patientstatus);
                if (!PatientList.isEmpty()) {

                    responseHashMap = Util.setArrayResponse(PatientList);
                } else {

                    responseHashMap = Util.setArrayResponseEmptyArray(PatientList);        // Specific for a failed user login return 401
                }
            } catch (Exception exception) {
                exception.printStackTrace();
                responseHashMap = Util.setErrorResponse(exception);
            }
        } else {
            responseHashMap = Util.setInvalidSession();
        }
        return responseHashMap;
    }

    public HashMap newCreateReferral(Referral referral) {

        HashMap responseHashMap;
        int result;

        if (userid > 0) {

            try {
                QueryHelper objQueryHelper = new QueryHelper();
                referral.setReferredBy(userid);
                result = objQueryHelper.newCreateReferral(referral);
                if (result > 0) {
                    responseHashMap = Util.setObjectResponse("Referral Created Successfully");
                } else {
                    responseHashMap = Util.setObjectResponse("Could Create Referral");
                }
            } catch (Exception e) {
                e.printStackTrace();
                responseHashMap = Util.setErrorResponse(e);
            }

        } else {
            responseHashMap = Util.setInvalidSession();
        }

        return responseHashMap;
    }

    public HashMap getGroupList() {
        HashMap responseHashMap;
        ArrayList<ChatGroup> MyGroupList;
        if (userid > 0) {
            try {
                QueryHelper objQueryHelper = new QueryHelper();
                MyGroupList = objQueryHelper.getGroupList(userid);
                if (MyGroupList.isEmpty()) {

                    responseHashMap = Util.setArrayResponseEmptyArray(MyGroupList);
                } else {

                    responseHashMap = Util.setArrayResponse(MyGroupList);       // Specific for a failed user login return 401
                }
            } catch (Exception exception) {
                exception.printStackTrace();
                responseHashMap = Util.setErrorResponse(exception);
            }
        } else {

            responseHashMap = Util.setInvalidSession();
        }
        return responseHashMap;
    }

    public HashMap changeStatus(int userid) {
        HashMap responseHashMap = Util.getInitialHashMap();

        if (userid > 0) {
            try {
                QueryHelper objQueryHelper = new QueryHelper();
                objQueryHelper.changeStatus(userid);

            } catch (Exception exception) {
                exception.printStackTrace();
                responseHashMap = Util.setErrorResponse(exception);
            }
        } else {

            responseHashMap = Util.setInvalidSession();
        }

        return responseHashMap;
    }

    public HashMap getReferralDtl(int ReferralID) {
        HashMap responseHashMap;
        HashMap getReferralResponse;
        Referral referral;
        if (userid > 0) {
            try {
                QueryHelper objQueryHelper = new QueryHelper();
                getReferralResponse = objQueryHelper.getReferralDtl(ReferralID);
                if (!getReferralResponse.isEmpty()) {
                    responseHashMap = Util.setObjectResponse(getReferralResponse);
                } else {
                    responseHashMap = Util.setResponseForInvalidInput();
                }
            } catch (Exception exception) {
                exception.printStackTrace();
                responseHashMap = Util.setErrorResponse(exception);
            }
        } else {
            responseHashMap = Util.setInvalidSession();
        }

        return responseHashMap;
    }

    public HashMap getSentRequestServlet() {
        HashMap responseHashMap = Util.getInitialHashMap();
        ArrayList<Integer> clinicStaffList = new ArrayList();
        ArrayList<ChatRequest> chatRequestList = new ArrayList();
        if (userid > 0) {
            try {
                QueryHelper objQueryHelper = new QueryHelper();
                clinicStaffList = objQueryHelper.getClinicStaff(userid);
                for (int i = 0; i < clinicStaffList.size(); i++) {
                    chatRequestList = objQueryHelper.getSentRequestList(clinicStaffList.get(i));
                    responseHashMap.put(clinicStaffList.get(i), chatRequestList);
                }

            } catch (Exception exception) {
                exception.printStackTrace();
                responseHashMap = Util.setErrorResponse(exception);
            }
        } else {

            responseHashMap = Util.setInvalidSession();
        }

        return responseHashMap;
    }

    public HashMap responseToChatRequest(int senderid, String responseflag) {
        HashMap responseHashMap;
        String message;
        if (userid > 0) {
            try {
                QueryHelper objQueryHelper = new QueryHelper();
                message = objQueryHelper.responseToChatRequest(senderid, userid, responseflag);
                responseHashMap = Util.setObjectResponse(message);

            } catch (Exception exception) {
                exception.printStackTrace();
                responseHashMap = Util.setErrorResponse(exception);
            }
        } else {
            responseHashMap = Util.setInvalidSession();
        }

        return responseHashMap;
    }

    public HashMap createChatGroup(ChatGroup chatgroup, ArrayList<String> users) {
        HashMap responseHashMap;
        int chatGroupID;
        int UserAddedtoGroup;
        int i;

        if (userid > 0) {
            try {
                QueryHelper objQueryHelper = new QueryHelper();
                chatGroupID = objQueryHelper.createChatGroup(chatgroup, userid);
                users.add(Integer.toString(userid));
                chatgroup.setMemberCount(users.size());
                if (chatGroupID > 0) {
                    chatgroup.setChatgroupID(chatGroupID);
                    for (i = 0; i < chatgroup.getMemberCount(); i++) {

                        objQueryHelper.addUserToGroup(chatgroup.getGroupMemberlist().get(i), chatgroup.getChatgroupID());
                    }
                    UserAddedtoGroup = i;
                    responseHashMap = Util.setObjectResponse(UserAddedtoGroup + " User Addded");

                } else {
                    responseHashMap = Util.setResponseForInvalidInput();
                }
            } catch (Exception exception) {
                exception.printStackTrace();
                responseHashMap = Util.setErrorResponse(exception);
            }
        } else {
            responseHashMap = Util.setInvalidSession();
        }
        return responseHashMap;
    }

    public HashMap forgotPassword(String emailid, String baseURL) {
        HashMap responseHashMap;
        SendMailForForgotPassword smfp = new SendMailForForgotPassword();
        User loginuser;
        try {
            QueryHelper objQueryHelper = new QueryHelper();
            loginuser = objQueryHelper.forgotPassword(emailid);
            if (loginuser.getUserID() > 0) {
                smfp.sendMail(loginuser.getEmail(), loginuser.getPassword(), baseURL);
                responseHashMap = Util.setObjectResponse(loginuser);
            } else {
                responseHashMap = Util.setInvalidUser();        // Specific for a failed user login return 401
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            responseHashMap = Util.setErrorResponse(exception);
        }

        return responseHashMap;
    }

    public HashMap resetPassword(String password, String token) {
        boolean resetFlag = false;
        System.out.println("password in service :" + password);
        System.out.println("token in service :" + token);
        HashMap responseHashMap;
        try {
            QueryHelper objQueryHelper = new QueryHelper();
            resetFlag = objQueryHelper.resetPassword(password, token);
            if (resetFlag) {
                responseHashMap = Util.setObjectResponse(resetFlag);
            } else {
                responseHashMap = Util.setInvalidUser();        // Specific for a failed user login return 401
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            responseHashMap = Util.setErrorResponse(exception);
        }
        return responseHashMap;
    }

    public HashMap updateChatGroup(ChatGroup chatgroup) {
        HashMap responseHashMap;
        int chatGroupID;
//        chatGroupID = chatgroup.getChatgroupID();
        int UserAddedtoGroup;
        int count = 0;
        if (userid > 0) {
            try {
                QueryHelper objQueryHelper = new QueryHelper();
                chatGroupID = objQueryHelper.updateChatGroup(chatgroup);
                if (chatGroupID > 0) {
                    for (int i = 0; i < chatgroup.getMemberCount(); i++) {
                        objQueryHelper.addUserToGroup(chatgroup.getGroupMemberlist().get(i), chatgroup.getChatgroupID());
                        count++;
                    }
                    UserAddedtoGroup = count;

                    responseHashMap = Util.setObjectResponse(UserAddedtoGroup + "User Added");

                } else {

                    responseHashMap = Util.setResponseForInvalidInput();

                }
            } catch (Exception exception) {
                exception.printStackTrace();
                responseHashMap = Util.setErrorResponse(exception);
            }
        } else {
            responseHashMap = Util.setInvalidSession();
        }

        return responseHashMap;

    }

    public HashMap deleteGroup(int groupid) {
        HashMap responseHashMap;

        String message;
        if (userid > 0) {
            try {
                QueryHelper objQueryHelper = new QueryHelper();
                message = objQueryHelper.deleteGroup(groupid);
                responseHashMap = Util.setObjectResponse(message);

            } catch (Exception exception) {
                exception.printStackTrace();
                responseHashMap = Util.setErrorResponse(exception);
            }
        } else {

            responseHashMap = Util.setInvalidSession();
        }
        return responseHashMap;
    }

    public HashMap deleteGroupUser(int groupid, int useridd) {
        HashMap responseHashMap;

        String message;
        if (userid > 0) {
            try {
                QueryHelper objQueryHelper = new QueryHelper();
                message = objQueryHelper.deleteGroupUser(groupid, useridd);
                responseHashMap = Util.setObjectResponse(message);
            } catch (Exception exception) {
                exception.printStackTrace();
                responseHashMap = Util.setErrorResponse(exception);
            }
        } else {

            responseHashMap = Util.setInvalidSession();
        }
        return responseHashMap;
    }

    public HashMap insuranceTypeList() {
        HashMap responseHashMap;
        ArrayList insurancelist;
        try {
            QueryHelper objQueryHelper = new QueryHelper();
            insurancelist = objQueryHelper.insuranceTypeList();
            if (!insurancelist.isEmpty()) {
                responseHashMap = Util.setArrayResponse(insurancelist);
            } else {
                responseHashMap = Util.setArrayResponseEmptyArray(insurancelist);        // Specific for a failed user login return 401
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            responseHashMap = Util.setErrorResponse(exception);
        }

        return responseHashMap;
    }

    public HashMap searchUserToAddInNetwork(String fname) {
        HashMap responseHashMap;
        ArrayList userlist;

        if (userid > 0) {
            try {
                QueryHelper objQueryHelper = new QueryHelper();
                userlist = objQueryHelper.searchUserToAddInNetwork(userid, fname);
                if (!userlist.isEmpty()) {
                    responseHashMap = Util.setArrayResponse(userlist);
                } else {
                    responseHashMap = Util.setArrayResponseEmptyArray(userlist);        // Specific for a failed user login return 401
                }
            } catch (Exception exception) {
                exception.printStackTrace();
                responseHashMap = Util.setErrorResponse(exception);
            }

        } else {

            responseHashMap = Util.setInvalidSession();
        }

        return responseHashMap;
    }

    public HashMap sendChatRequest(int receiverid) {
        HashMap responseHashMap;
        ArrayList res = new ArrayList();

        String message;

        if (userid > 0) {
            try {
                QueryHelper objQueryHelper = new QueryHelper();
                message = objQueryHelper.sendChatRequest(userid, receiverid);
                if (!message.isEmpty()) {
                    res.add(message);
                    responseHashMap = Util.setArrayResponse(res);
                } else {
                    responseHashMap = Util.setArrayResponseEmptyArray(res);        // Specific for a failed user login return 401
                }
            } catch (Exception exception) {
                exception.printStackTrace();
                responseHashMap = Util.setErrorResponse(exception);
            }

        } else {

            responseHashMap = Util.setInvalidSession();
        }

        return responseHashMap;
    }

    public HashMap getGroupDetails(int groupid) {
        HashMap responseHashMap;
        HashMap groupDetails;

        if (userid > 0) {
            try {
                QueryHelper objQueryHelper = new QueryHelper();
                groupDetails = objQueryHelper.getGroupDetails(groupid);
                if (!groupDetails.isEmpty()) {

                    responseHashMap = Util.setObjectResponse(groupDetails);
                } else {
                    responseHashMap = Util.setArrayResponseEmptyArray(groupDetails);        // Specific for a failed user login return 401
                }
            } catch (Exception exception) {
                exception.printStackTrace();
                responseHashMap = Util.setErrorResponse(exception);
            }

        } else {

            responseHashMap = Util.setInvalidSession();
        }

        return responseHashMap;
    }

    public HashMap searchPatient(String searchText) {
        HashMap responseHashMap;
        ArrayList patientlist;

        if (userid > 0) {
            try {
                QueryHelper objQueryHelper = new QueryHelper();
                patientlist = objQueryHelper.searchPatient(searchText, userid);
                if (!patientlist.isEmpty()) {
                    responseHashMap = Util.setArrayResponse(patientlist);
                } else {
                    responseHashMap = Util.setArrayResponseEmptyArray(patientlist);        // Specific for a failed user login return 401
                }
            } catch (Exception exception) {
                exception.printStackTrace();
                responseHashMap = Util.setErrorResponse(exception);
            }

        } else {

            responseHashMap = Util.setInvalidSession();
        }

        return responseHashMap;
    }

    public HashMap getRoleList() {
        HashMap responseHashMap;
        ArrayList rolelist;
        try {
            QueryHelper objQueryHelper = new QueryHelper();
            rolelist = objQueryHelper.getRoleList();
            if (!rolelist.isEmpty()) {
                responseHashMap = Util.setArrayResponse(rolelist);
            } else {
                responseHashMap = Util.setArrayResponseEmptyArray(rolelist);        // Specific for a failed user login return 401
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            responseHashMap = Util.setErrorResponse(exception);
        }

        return responseHashMap;
    }

    public HashMap newSignup(Map<String, Object> signupData) {

        HashMap responseHashMap;
        int result;

        try {
            QueryHelper objQueryHelper = new QueryHelper();
            result = objQueryHelper.newSignup(signupData);
            if (result > 0) {
                responseHashMap = Util.setObjectResponse("Signup Successfull");
            } else {
                responseHashMap = Util.setObjectResponse(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseHashMap = Util.setErrorResponse(e);
        }

        return responseHashMap;
    }

    public HashMap sendChatMessage(Chatlog chatlog) {
        HashMap responseHashMap;

        Chatlog chatlog1;

        if (userid > 0) {
            try {
                QueryHelper objQueryHelper = new QueryHelper();
                chatlog1 = objQueryHelper.sendChatMessage(userid, chatlog);
                if (chatlog1 != null) {

                    responseHashMap = Util.setObjectResponse(chatlog1);
                } else {
                    responseHashMap = Util.setObjectResponse(chatlog1);        // Specific for a failed user login return 401
                }
            } catch (Exception exception) {
                exception.printStackTrace();
                responseHashMap = Util.setErrorResponse(exception);
            }

        } else {

            responseHashMap = Util.setInvalidSession();
        }

        return responseHashMap;
    }

    public HashMap getDoctorListForRefer(int specializationid, int clinicid, int patientid) {
        HashMap responseHashMap;
        ArrayList doctorlist;
        try {
            QueryHelper objQueryHelper = new QueryHelper();
            doctorlist = objQueryHelper.getDoctorListForRefer(specializationid, clinicid, patientid);
            if (!doctorlist.isEmpty()) {
                responseHashMap = Util.setArrayResponse(doctorlist);
            } else {
                responseHashMap = Util.setArrayResponseEmptyArray(doctorlist);
            }

        } catch (Exception exception) {
            exception.printStackTrace();
            responseHashMap = Util.setErrorResponse(exception);
        }

        return responseHashMap;
    }

    public HashMap getSelectedUserDetails(int sentUserId) {
        HashMap responseHashMap;
        User user;

        if (userid > 0) {
            try {
                QueryHelper objQueryHelper = new QueryHelper();
                user = objQueryHelper.getSelectedUserDetails(sentUserId);
                responseHashMap = Util.setObjectResponse(user);

            } catch (Exception exception) {
                exception.printStackTrace();
                responseHashMap = Util.setErrorResponse(exception);
            }

        } else {

            responseHashMap = Util.setInvalidSession();
        }

        return responseHashMap;
    }

    public HashMap getCredentials() {
        HashMap responseHashMap;
        ArrayList credentiallist;
        try {
            QueryHelper objQueryHelper = new QueryHelper();
            credentiallist = objQueryHelper.getCredentials();
            if (!credentiallist.isEmpty()) {
                responseHashMap = Util.setArrayResponse(credentiallist);
            } else {
                responseHashMap = Util.setArrayResponseEmptyArray(credentiallist);        // Specific for a failed user login return 401
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            responseHashMap = Util.setErrorResponse(exception);
        }

        return responseHashMap;
    }

    public HashMap getRequiredReportForRefer(int doctorid) {
        HashMap responseHashMap;
        ArrayList<RequiredReport> requiredReportsList;

        if (userid > 0) {
            try {
                QueryHelper objQueryHelper = new QueryHelper();
                requiredReportsList = objQueryHelper.getRequiredReportForRefer(doctorid);
                if (!requiredReportsList.isEmpty()) {

                    responseHashMap = Util.setObjectResponse(requiredReportsList);
                } else {
                    responseHashMap = Util.setObjectResponse(requiredReportsList);        // Specific for a failed user login return 401
                }
            } catch (Exception exception) {
                exception.printStackTrace();
                responseHashMap = Util.setErrorResponse(exception);
            }

        } else {

            responseHashMap = Util.setInvalidSession();
        }

        return responseHashMap;
    }

    public HashMap sendGroupChatMessage(Chatlog chatlog) {
        HashMap responseHashMap;

        Chatlog chatloggroup;

        if (userid > 0) {
            try {
                QueryHelper objQueryHelper = new QueryHelper();
                chatloggroup = objQueryHelper.sendGroupChatMessage(userid, chatlog);

                responseHashMap = Util.setObjectResponse(chatloggroup);

            } catch (Exception exception) {
                exception.printStackTrace();
                responseHashMap = Util.setErrorResponse(exception);
            }

        } else {

            responseHashMap = Util.setInvalidSession();
        }
        return responseHashMap;
    }

    public HashMap getNotifications() {
        HashMap responseHashMap;
        HashMap<String, Integer> notificationResonseMap = null;
        Chatlog chatlog1 = new Chatlog();

        if (userid > 0) {
            try {
                QueryHelper objQueryHelper = new QueryHelper();
//                notificationResonseMap = objQueryHelper.getNotifications();
                if (!notificationResonseMap.isEmpty()) {

                    responseHashMap = Util.setObjectResponse(chatlog1);
                } else {
                    responseHashMap = Util.setObjectResponse(chatlog1);        // Specific for a failed user login return 401
                }
            } catch (Exception exception) {
                exception.printStackTrace();
                responseHashMap = Util.setErrorResponse(exception);
            }

        } else {

            responseHashMap = Util.setInvalidSession();
        }
        return responseHashMap;
    }

    public HashMap getRefferedToList() {
        HashMap responseHashMap;
        ArrayList referredtolist;

        if (userid > 0) {
            try {
                QueryHelper objQueryHelper = new QueryHelper();
                referredtolist = objQueryHelper.getRefferedToList(userid);
                if (!referredtolist.isEmpty()) {

                    responseHashMap = Util.setObjectResponse(referredtolist);
                } else {
                    responseHashMap = Util.setObjectResponse(referredtolist);        // Specific for a failed user login return 401
                }
            } catch (Exception exception) {
                exception.printStackTrace();
                responseHashMap = Util.setErrorResponse(exception);
            }

        } else {

            responseHashMap = Util.setInvalidSession();
        }
        return responseHashMap;
    }

    public HashMap getReferredFromList(String filtertimestamp) {
        HashMap responseHashMap;
        ArrayList referredfromlist;

        if (userid > 0) {
            try {
                QueryHelper objQueryHelper = new QueryHelper();
                referredfromlist = objQueryHelper.getReferredFromList(userid, filtertimestamp);
                if (!referredfromlist.isEmpty()) {

                    responseHashMap = Util.setObjectResponse(referredfromlist);
                } else {
                    responseHashMap = Util.setObjectResponse(referredfromlist);        // Specific for a failed user login return 401
                }
            } catch (Exception exception) {
                exception.printStackTrace();
                responseHashMap = Util.setErrorResponse(exception);
            }

        } else {

            responseHashMap = Util.setInvalidSession();
        }
        return responseHashMap;
    }

    public HashMap deletePatient(int patientid, int patientstatus) {
        HashMap responseHashMap = null;

        if (userid > 0) {
            try {
                String message;
                QueryHelper objQueryHelper = new QueryHelper();
                message = objQueryHelper.deletePatient(patientid, patientstatus);
                if (message != null) {

                    responseHashMap = Util.setObjectResponse(message);
                }
            } catch (Exception exception) {
                exception.printStackTrace();
                responseHashMap = Util.setErrorResponse(exception);
            }

        } else {

            responseHashMap = Util.setInvalidSession();
        }
        return responseHashMap;
    }

    public HashMap GetSignupDtl() {

        HashMap responseHashMap;
        int result;

        try {
            QueryHelper objQueryHelper = new QueryHelper();
            result = objQueryHelper.GetSignupDtl();
            responseHashMap = Util.setObjectResponse(result);
        } catch (Exception e) {
            e.printStackTrace();
            responseHashMap = Util.setErrorResponse(e);
        }

        return responseHashMap;
    }

    public HashMap sendMail(ContactUs contact) {
        HashMap responseHashMap;
        boolean result;
        try {
            SendMail sm = new SendMail();
            System.out.println("contact details email :" + contact.getEmailID());
            result = sm.sendMail(contact, true);
            result = sm.sendMail(contact, false);
            responseHashMap = Util.setObjectResponse(result);
        } catch (Exception e) {
            e.printStackTrace();
            responseHashMap = Util.setErrorResponse(e);
        }
        return responseHashMap;

    }

    public HashMap addCommentsOnReferral(ReferralComments referralcomments) {
        HashMap responseHashMap;
        ReferralComments referralcommentsresponse;

        if (userid > 0) {
            try {
                String message;
                QueryHelper objQueryHelper = new QueryHelper();
                referralcommentsresponse = objQueryHelper.addCommentsOnReferral(referralcomments, userid);
                if (!referralcommentsresponse.equals("")) {

                    responseHashMap = Util.setObjectResponse(referralcomments);
                } else {
                    responseHashMap = Util.setObjectResponse(referralcomments);        // Specific for a failed user login return 401
                }
            } catch (Exception exception) {
                exception.printStackTrace();
                responseHashMap = Util.setErrorResponse(exception);
            }

        } else {

            responseHashMap = Util.setInvalidSession();
        }
        return responseHashMap;
    }

    public HashMap responseToReferralRequest(int referralid, String responseflag) {
        HashMap responseHashMap;
        String message;
        if (userid > 0) {
            try {
                QueryHelper objQueryHelper = new QueryHelper();
                message = objQueryHelper.responseToReferralRequest(referralid, userid, responseflag);
                responseHashMap = Util.setObjectResponse(message);

            } catch (Exception exception) {
                exception.printStackTrace();
                responseHashMap = Util.setErrorResponse(exception);
            }
        } else {
            responseHashMap = Util.setInvalidSession();
        }

        return responseHashMap;
    }

    public HashMap responseToReferralRequest(int referralid) {
        HashMap responseHashMap;
        String message;
        if (userid > 0) {
            try {
                QueryHelper objQueryHelper = new QueryHelper();
                message = objQueryHelper.responseToReferralRequest(referralid, userid);
                responseHashMap = Util.setObjectResponse(message);

            } catch (Exception exception) {
                exception.printStackTrace();
                responseHashMap = Util.setErrorResponse(exception);
            }
        } else {
            responseHashMap = Util.setInvalidSession();
        }

        return responseHashMap;
    }

    public HashMap attachmentForChat(Chatlog chatlogobj) {
        HashMap responseHashMap;
        int result;
        if (userid > 0) {
            try {
                QueryHelper objQueryHelper = new QueryHelper();
                chatlogobj.setSenderID(userid);
                result = objQueryHelper.attachmentForChat(chatlogobj, userid);
                if (result > 0) {
                    responseHashMap = Util.setObjectResponse("Upload Success");
                } else {
                    responseHashMap = Util.setObjectResponse("Upload Failure");
                }

            } catch (Exception exception) {
                exception.printStackTrace();
                responseHashMap = Util.setErrorResponse(exception);
            }
        } else {
            responseHashMap = Util.setInvalidSession();
        }

        return responseHashMap;
    }

    public HashMap checkDuplicateEmail(String email, int requestType) {
        HashMap responseHashMap;
        try {
            QueryHelper objQueryHelper = new QueryHelper();
            boolean isEmailExist = objQueryHelper.checkDuplicateEmail(email, requestType);
            responseHashMap = Util.setObjectResponse(isEmailExist);
        } catch (Exception exception) {
            exception.printStackTrace();
            responseHashMap = Util.setErrorResponse(exception);
        }
        return responseHashMap;
    }

    public HashMap checkDuplicateMobileNumber(String mobileno, int requestType, int requestSubType) {
        HashMap responseHashMap;
        try {
            QueryHelper objQueryHelper = new QueryHelper();
            boolean isEmailExist = objQueryHelper.checkDuplicateMobileNumber(mobileno, requestType, requestSubType);
            responseHashMap = Util.setObjectResponse(isEmailExist);
        } catch (Exception exception) {
            exception.printStackTrace();
            responseHashMap = Util.setErrorResponse(exception);
        }
        return responseHashMap;
    }

    public HashMap getProfileDtl() {

        HashMap responseHashMap;
        int result;
        HashMap<String, Object> Data;
        try {
            QueryHelper objQueryHelper = new QueryHelper();
            Data = objQueryHelper.getProfileDtl(63);
            responseHashMap = Util.setObjectResponse(Data);
        } catch (Exception e) {
            e.printStackTrace();
            responseHashMap = Util.setErrorResponse(e);
        }

        return responseHashMap;
    }

}
