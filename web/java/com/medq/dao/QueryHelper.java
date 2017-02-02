package com.medq.dao;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.net.InetAddress;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.*;
import java.sql.Timestamp;
import com.medq.dto.*;
import com.medq.util.DateConversion;
import com.medq.util.GetStringofArrayof;
import com.medq.util.FileOperation;
import java.net.URLDecoder;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class QueryHelper {

    DataSource dataSource;
    // Connection connection;dfsadasdasdasd
    String dbName = "refque_refque";
//    private static final SimpleDateFormat GMT_yMd = new SimpleDateFormat("yyyy-MM-dd");
//    private static final SimpleDateFormat GMT_yMd_Hms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//    static {
//        GMT_yMd.setTimeZone(TimeZone.getTimeZone("GMT"));
//        GMT_yMd_Hms.setTimeZone(TimeZone.getTimeZone("GMT"));
//    }

    @SuppressWarnings("unchecked")
    public Connection getConnection() throws Exception {
        //first change the db name and password before uploading war file..
        Connection connection;
        Context initContext = new InitialContext();
        initContext.getNameParser(dbName);
        Context envContext = (Context) initContext.lookup("java:/comp/env");
        dataSource = (DataSource) envContext.lookup("jdbc/webservice");
        return connection = dataSource.getConnection();

    }

    public User login(String emailid, String password) throws Exception {
        Connection connection1 = getConnection();
        User loginuser = new User();
        try {
            boolean isonline = true;

            PreparedStatement psForLogin = connection1.prepareStatement("Select User.*,Role.RoleName\n"
                    + "                    from Role,User where Email =? and\n"
                    + "                     Role.RoleID =\n"
                    + "                    (SELECT RoleID FROM  User where Email=? and Password=?)\n"
                    + "                    and User.RoleID=Role.RoleID;");
            psForLogin.setString(1, emailid);
            psForLogin.setString(2, emailid);
            psForLogin.setString(3, password);
            ResultSet resultSetForLogin = psForLogin.executeQuery();
            while (resultSetForLogin.next()) {

                loginuser.setUserID(resultSetForLogin.getInt("UserID"));
                loginuser.setFirstName(resultSetForLogin.getString("FirstName"));
                loginuser.setLastName(resultSetForLogin.getString("LastName"));
                loginuser.setClinicID(resultSetForLogin.getInt("ClinicID"));
                loginuser.setAddressID(resultSetForLogin.getInt("AddressID"));
                loginuser.setDeskPhone(resultSetForLogin.getString("DeskPhone"));
                loginuser.setCellPhone(resultSetForLogin.getString("CellPhone"));
                loginuser.setFaxNo(resultSetForLogin.getString("FaxNo"));
                //loginuser.setAddressID(resultSetForLogin.getInt("SpecializationID"));
                loginuser.setImageUrlThumbnail(resultSetForLogin.getString("ImageUrlThumbnail"));
                loginuser.setSpecializationName(resultSetForLogin.getString("RoleName"));

            }

            PreparedStatement psForChangeIsOnlineStatus = connection1.prepareStatement("UPDATE " + dbName + ".User SET IsOnline=? where Email=?");
            psForChangeIsOnlineStatus.setBoolean(1, isonline);
            psForChangeIsOnlineStatus.setString(2, emailid);
            int result = psForChangeIsOnlineStatus.executeUpdate();
            if (result > 0) {
                loginuser.setIsOnline(true);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection1 != null) {
                connection1.close();
            }
        }
        return loginuser;
    }

    public User logout(int uid) throws Exception {
        int result;
        User logoutuser = new User();
        Connection connection = getConnection();
        try {
            PreparedStatement psForLogoutUser = connection.prepareStatement("update " + dbName + ".User set IsOnline=0 where UserID =? ");
            psForLogoutUser.setInt(1, uid);

            result = psForLogoutUser.executeUpdate();
            if (result > 0) {
                logoutuser.setUserID(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return logoutuser;
    }

    public User User() throws Exception {
        Connection connection = getConnection();
        User loginuser = new User();
        try {
            PreparedStatement psForLogin = connection.prepareStatement("SELECT DoctorID,FirstName,Password FROM " + dbName + ".Doctor ");
            ResultSet resultSetForLogin = psForLogin.executeQuery();
            while (resultSetForLogin.next()) {
                loginuser.setUserID(resultSetForLogin.getInt("DoctorID"));
                loginuser.setPassword(resultSetForLogin.getString("Password"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return loginuser;
    }

    public ArrayList<State> state() throws Exception {
        Connection connection = getConnection();
        ArrayList statelist = new ArrayList();
        try {
            PreparedStatement psForState = connection.prepareStatement("SELECT StateID,StateName FROM " + dbName + ".State ORDER BY StateName ASC ");
            ResultSet resultSetForState = psForState.executeQuery();
            while (resultSetForState.next()) {
                State state = new State();
                state.setStateID(resultSetForState.getInt("StateID"));
                state.setStateName(resultSetForState.getString("StateName"));
                statelist.add(state);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return statelist;
    }

    public ArrayList<City> city(int stateid) throws Exception {
        Connection connection = getConnection();
        ArrayList citylist = new ArrayList();
        try {
            PreparedStatement psForCity = connection.prepareStatement("SELECT distinct(CityName),CityID FROM " + dbName + ".City where StateID=? ORDER BY CityName ASC");
            psForCity.setInt(1, stateid);
            ResultSet resultSetForCity = psForCity.executeQuery();
            while (resultSetForCity.next()) {
                City city = new City();
                city.setCityID(resultSetForCity.getInt("CityID"));
                city.setCityName(resultSetForCity.getString("CityName"));
                citylist.add(city);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return citylist;
    }

    public ArrayList<City> getZipCode(String cityname) throws Exception {
        Connection connection = getConnection();
        ArrayList zipcodelist = new ArrayList();
        try {
            PreparedStatement psForZipCode = connection.prepareStatement("select Zip from City where CityName=?");
            psForZipCode.setString(1, cityname);
            ResultSet resultSetForZipCode = psForZipCode.executeQuery();
            while (resultSetForZipCode.next()) {
                City city = new City();
                city.setZip(resultSetForZipCode.getInt("Zip"));
                zipcodelist.add(city);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return zipcodelist;
    }

    public ArrayList specialization() throws Exception {
        Connection connection = getConnection();
        ArrayList specializationlist = new ArrayList();
        try {
            PreparedStatement psForSpecialization = connection.prepareStatement("SELECT SpecializationID,SpecializationName FROM " + dbName + ".Specialization ");
            ResultSet resultSetForSpecialization = psForSpecialization.executeQuery();
            while (resultSetForSpecialization.next()) {
                Specialization specialization = new Specialization();
                specialization.setSpecializationID(resultSetForSpecialization.getInt("SpecializationID"));
                specialization.setSpecializationName(resultSetForSpecialization.getString("SpecializationName"));
                specializationlist.add(specialization);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return specializationlist;
    }

    public HashMap getClinicAndReasonForRefer(int specialid, int patientid) throws Exception {
        Connection connection = getConnection();
        HashMap<String, ArrayList> getclinicandreasonforefer = new HashMap<>();
        ArrayList cliniclist = new ArrayList();
        try {
            PreparedStatement psForClinic = connection.prepareStatement("select ClinicID,ClinicName from " + dbName + ".Clinic where ClinicID in (SELECT ClinicID FROM " + dbName + ".User \n"
                    + "where UserID in (SELECT DoctorID FROM " + dbName + ".InsuranceDoctor \n"
                    + "where InsuranceID in (select InsuranceID from " + dbName + ".InsurancePatient where PatientID = ?))\n"
                    + "and SpecializationID = ?)");

            psForClinic.setInt(1, patientid);
            psForClinic.setInt(2, specialid);
            ResultSet resultSetForClinic = psForClinic.executeQuery();
            while (resultSetForClinic.next()) {
                Clinic clinic = new Clinic();
                clinic.setClinicID(resultSetForClinic.getInt("ClinicID"));
                clinic.setClinicName(resultSetForClinic.getString("ClinicName"));
                cliniclist.add(clinic);

            }
            ArrayList resonslist = diseaseLookup(specialid);
            getclinicandreasonforefer.put("Cliniclist", cliniclist);
            getclinicandreasonforefer.put("Resonslist", resonslist);
        } catch (Exception e) {

            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return getclinicandreasonforefer;
    }

    @SuppressWarnings("unchecked")
    public String changePassword(int userid, String oldpassword, String newpassword) throws Exception {
        Connection connection = getConnection();

        String oldpass = "Old Password Did not match.";
        String changepass = "Password Changed";
        try {
            PreparedStatement psForOldPassword = connection.prepareStatement("SELECT  Email,Password FROM " + dbName + ".User where UserID=?");
            psForOldPassword.setInt(1, userid);
            PreparedStatement psForChangePassword = connection.prepareStatement("UPDATE " + dbName + ".User SET Password=? Where UserID=? ");
            psForChangePassword.setString(1, newpassword);
            psForChangePassword.setInt(2, userid);
            ResultSet resultSetForoldpassword = psForOldPassword.executeQuery();
            while (resultSetForoldpassword.next()) {
                String temp = resultSetForoldpassword.getString("Password");
                if (!oldpassword.equals(resultSetForoldpassword.getString("Password"))) {
                    return oldpass;
                } else {
                    psForChangePassword.executeUpdate();
                    return changepass;

                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return null;
    }

    public void updateAddress(Address adress) throws Exception {
        Connection connection = getConnection();
        int result = 0;
        int AddressID = 0;
        try {
            PreparedStatement psForinsertAddress = connection.prepareStatement("UPDATE " + dbName + ".Address SET"
                    + " HouseNo = ?,CityID=?,StateID=?,ZipCode=?,Street=?"
                    + " WHERE AddressID = ?");
            psForinsertAddress.setString(1, adress.getHouseNo());
            psForinsertAddress.setInt(2, adress.getCityID());
            psForinsertAddress.setInt(3, adress.getStateID());
            psForinsertAddress.setString(4, adress.getZipCode());
            psForinsertAddress.setString(5, adress.getStreet());

            psForinsertAddress.setInt(6, adress.getAddressID());

            result = psForinsertAddress.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }

    }

    public HashMap getPatientDetails(int id) throws Exception {
        Connection connection = getConnection();
        HashMap<String, Object> patientdetails = new HashMap();

        try {
            PreparedStatement psForPatientList = connection.prepareStatement("Select * from " + dbName + ".Address," + dbName + ".Patient where Address.AddressID\n"
                    + "In\n"
                    + "(Select Patient.AddressID From " + dbName + ".Patient where PatientID\n"
                    + "In \n"
                    + "(SELECT PatientID FROM " + dbName + ".Patient where PatientID=?)) and " + dbName + ".Address.AddressID=" + dbName + ".Patient.AddressID");
            psForPatientList.setInt(1, id);
            ResultSet resultSetForPatientList = psForPatientList.executeQuery();
            while (resultSetForPatientList.next()) {
                Patient patient = new Patient();
                patient.setPatientID(resultSetForPatientList.getInt("PatientID"));
                patient.setAddressID(resultSetForPatientList.getInt("AddressID"));
                patient.setFirstName(resultSetForPatientList.getString("FirstName"));
                patient.setLastName(resultSetForPatientList.getString("LastName"));
                patient.setPhoneNo(resultSetForPatientList.getString("PhoneNo"));
                patient.setEmail(resultSetForPatientList.getString("Email"));
                patient.setDOB(DateConversion.dateToUnixConversion(resultSetForPatientList.getTimestamp("DOB")));
                patient.setIDProofPicUrl(resultSetForPatientList.getString("IDProofPicUrl"));
                patient.setInsuranceIDfrontpicUrl(resultSetForPatientList.getString("InsuranceIDfrontpicUrl"));
                patient.setInsuranceIDbackpicUrl(resultSetForPatientList.getString("InsuranceIDbackpicUrl"));
                patient.setHouseNo(resultSetForPatientList.getString("HouseNo"));
                patient.setStreet(resultSetForPatientList.getString("Street"));
                patient.setCityID(resultSetForPatientList.getInt("CityID"));
                patient.setStateID(resultSetForPatientList.getInt("StateID"));
                patient.setZipCode(resultSetForPatientList.getString("ZipCode"));
                patient.setInsuranceID(GetPatientInsuranceList(id));

                patientdetails.put("PatientDetails", patient);

            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return patientdetails;
    }

    public ArrayList<Integer> GetPatientInsuranceList(int id) throws Exception {
        Connection connection = getConnection();
        ArrayList<Integer> insurancelist = new ArrayList<>();
        PreparedStatement psForPatientInsuranceList = connection.prepareStatement("SELECT InsuranceID,InsuranceType FROM " + dbName + ".Insurance where InsuranceID In (SELECT InsuranceID FROM " + dbName + ".InsurancePatient where PatientID = ?)");
        psForPatientInsuranceList.setInt(1, id);
        ResultSet resultSetForPatientInsuranceList = psForPatientInsuranceList.executeQuery();
        while (resultSetForPatientInsuranceList.next()) {
            insurancelist.add(resultSetForPatientInsuranceList.getInt(1));

        }
        return insurancelist;
    }

    public ArrayList<String> getPatientInsuranceNameList(int id) throws Exception {
        Connection connection = getConnection();

        ArrayList<String> insurancelist = new ArrayList<>();
        PreparedStatement psForPatientInsuranceList = connection.prepareStatement("SELECT InsuranceID,InsuranceType FROM " + dbName + ".Insurance where InsuranceID In (SELECT InsuranceID FROM " + dbName + ".InsurancePatient where PatientID = ?)");
        psForPatientInsuranceList.setInt(1, id);
        ResultSet resultSetForPatientInsuranceList = psForPatientInsuranceList.executeQuery();
        while (resultSetForPatientInsuranceList.next()) {

            insurancelist.add(resultSetForPatientInsuranceList.getString("InsuranceType"));
        }
        return insurancelist;
    }

    public ArrayList<Disease> diseaseLookup(int specializationid) throws Exception {
        Connection connection = getConnection();
        ArrayList diseaselist = new ArrayList();
        try {
            PreparedStatement psFordisease = connection.prepareStatement("SELECT * FROM " + dbName + ".Disease where SpecializationID=? ");
            psFordisease.setInt(1, specializationid);
            ResultSet resultSetForDisease = psFordisease.executeQuery();
            while (resultSetForDisease.next()) {
                Disease disease = new Disease();
                disease.setDiseaseID(resultSetForDisease.getInt("DiseaseID"));
                disease.setDiseaseName(resultSetForDisease.getString("DiseaseName"));
                disease.setSpecializationID(resultSetForDisease.getInt("SpecializationID"));
                diseaselist.add(disease);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return diseaselist;
    }

    public ArrayList<ChatRequest> getReceivedFriendRequest(int userid) throws Exception {
        Connection connection = getConnection();
        ArrayList requestlist = new ArrayList();
        try {
            PreparedStatement psForrequest = connection.prepareStatement("select UserID, FirstName, LastName, ImageUrlThumbnail, ImageUrlBlur, ImageUrlOriginal from " + dbName + ".User where UserID \n"
                    + "IN (SELECT SenderID FROM " + dbName + ".ChatRequest where ReceiverID = ? && ResponseStatus = 0)");
            psForrequest.setInt(1, userid);
            ResultSet resultSetForRequest = psForrequest.executeQuery();
            while (resultSetForRequest.next()) {
                User user = new User();
                user.setUserID(resultSetForRequest.getInt("UserID"));
                user.setFirstName(resultSetForRequest.getString("FirstName"));
                user.setLastName(resultSetForRequest.getString("LastName"));
                user.setImageUrlThumbnail(resultSetForRequest.getString("ImageUrlThumbnail"));
                user.setImageUrlBlur(resultSetForRequest.getString("ImageUrlBlur"));
                user.setImageUrlOriginal(resultSetForRequest.getString("ImageUrlOriginal"));

                requestlist.add(user);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return requestlist;
    }

    public ArrayList<Contactlist> getMyContactList(int userid) throws Exception {
        Connection connection = getConnection();
        ArrayList userlist = new ArrayList();

        try {
//            PreparedStatement psForcontact = connection.prepareStatement("SELECT * FROM User where UserID In (select UserID FROM Contactlist where ClinicID = (select ClinicID from User where UserID = ?)) and UserID != ?");
            PreparedStatement psForcontact = connection.prepareStatement("select * from " + dbName + ".User where UserID != ? and ClinicID \n"
                    + "in (select ClinicID from " + dbName + ".Contactlist where ClinicIDTo \n"
                    + "in (select ClinicID from " + dbName + ".User where UserID =? and IsApproved = 1 ) \n"
                    + "union \n"
                    + "select ClinicIDTo from " + dbName + ".Contactlist where ClinicID \n"
                    + "in (select ClinicID from " + dbName + ".User where UserID =? and IsApproved = 1)\n"
                    + "union\n"
                    + "select ClinicID from " + dbName + ".User where UserID = ?)");
            psForcontact.setInt(1, userid);
            psForcontact.setInt(2, userid);
            psForcontact.setInt(3, userid);
            psForcontact.setInt(4, userid);
            ResultSet resultSetForContact = psForcontact.executeQuery();
            while (resultSetForContact.next()) {
                User user = new User();
                user.setUserID(resultSetForContact.getInt("UserID"));
                user.setFirstName(resultSetForContact.getString("FirstName"));
                user.setLastName(resultSetForContact.getString("LastName"));
                user.setIsOnline(resultSetForContact.getBoolean("IsOnline"));
                userlist.add(user);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return userlist;
    }

    public ArrayList<Patient> getReferredToList(int referredbyid) throws Exception {
        Connection connection = getConnection();
        ArrayList<Patient> referredtolist = new ArrayList();
        try {
            PreparedStatement psForreferredto = connection.prepareStatement("select FirstName,LastName,PhoneNo from " + dbName + ".Patient where PatientID IN \n"
                    + "(Select PatientID from " + dbName + ".Referral where ReferralID IN \n"
                    + "(select ReferralID from " + dbName + ".ReferredTo where ReferredBy=? )) ");
            psForreferredto.setInt(1, referredbyid);
            ResultSet resultSetForReferredTo = psForreferredto.executeQuery();
            while (resultSetForReferredTo.next()) {
                Patient patient = new Patient();
                patient.setFirstName(resultSetForReferredTo.getString("FirstName"));
                patient.setLastName(resultSetForReferredTo.getString("LastName"));
                patient.setPhoneNo(resultSetForReferredTo.getString("PhoneNo"));

                referredtolist.add(patient);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return referredtolist;
    }

    public HashMap getChatHistory(int receiverid, int userid, String lastmessagetimestamp, int selectedgroupid) throws Exception {

        Connection connectionchat = getConnection();
        //initial timestamp came as parameter is currenttimestamp-24 hour time stamp
        HashMap responseChatHistory = new HashMap<>();
        ArrayList<Chatlog> chatMessages = new ArrayList<>();
        ArrayList<User> userMessageCount = new ArrayList<>();
        ArrayList<Chatlog> chatgroupMessages = new ArrayList<>();

        
        //get all new friend requests when this method called
        ArrayList<ChatRequest> receivedFriendRequestList = new ArrayList<>();
        receivedFriendRequestList = getReceivedFriendRequest(userid);
        responseChatHistory.put("UserNewFriendRequestList", receivedFriendRequestList);

        
        //get all new referral requests when this method called
        ArrayList<User> receivedReferralRequestList = new ArrayList<>();
        receivedReferralRequestList = getMyReferralRequests(userid, lastmessagetimestamp);
        responseChatHistory.put("UserNewReferralRequestList", receivedReferralRequestList);
        try {
            if (receiverid == 0 && selectedgroupid == 0) {
                //send only message count of users chat not a message with out any time stamps 

                responseChatHistory.put("Message", chatMessages);

                userMessageCount = getUsersChatMessageCount(userid);
                responseChatHistory.put("UsersMessageCount", userMessageCount);

                responseChatHistory.put("GroupMessage", chatgroupMessages);
            } else if (receiverid > 0 && selectedgroupid == 0) {

                /*this condition will be true if you click on any user for chatting and based on userid 
                new messages will come which are greater than last message time stamp */
                changeMessageStatus(receiverid, userid);

                chatMessages = getUserChatMessages(userid, receiverid, lastmessagetimestamp);
                responseChatHistory.put("Message", chatMessages);

                userMessageCount = getUsersChatMessageCount(userid);
                responseChatHistory.put("UsersMessageCount", userMessageCount);

                responseChatHistory.put("GroupMessage", chatgroupMessages);
            } else if (receiverid == 0 && selectedgroupid > 0) {

                /*this condition will be true if you click on any group based on groupid 
                new messages will come based on current timestamp-24 HR timestamp */
                responseChatHistory.put("Messages", chatMessages);

                responseChatHistory.put("UsersMessageCount", userMessageCount);

                chatgroupMessages = getGroupChatMessages(userid, selectedgroupid, lastmessagetimestamp);
                responseChatHistory.put("GroupMessage", chatgroupMessages);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connectionchat != null) {
                connectionchat.close();
            }
        }
        return responseChatHistory;
    }

    public ArrayList<User> getUsersChatMessageCount(int userid) throws Exception {
        Connection connectionchat = getConnection();
        ArrayList<User> userMessageCount = new ArrayList<>();
        try {
            PreparedStatement psForusermessagecount = connectionchat.prepareStatement("SELECT SenderID, COUNT(*) as MessageCount FROM " + dbName + ".Chatlog where ReceiverID=? and MessageStatus=0 GROUP BY SenderID ;");
            psForusermessagecount.setInt(1, userid);

            ResultSet resultSetForusermessagecount = psForusermessagecount.executeQuery();
            while (resultSetForusermessagecount.next()) {
                User user = new User();
                System.out.println(resultSetForusermessagecount.getInt(1));
                user.setUserID(resultSetForusermessagecount.getInt(1));
                user.setMessageCount(resultSetForusermessagecount.getInt(2));
                userMessageCount.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connectionchat != null) {
                connectionchat.close();
            }
        }
        return userMessageCount;
    }

    public ArrayList<GroupUserCount> getGroupsChatMessageCount(int userid) throws Exception {
        Connection connectionchat = getConnection();
        ArrayList<GroupUserCount> groupMessageCount = new ArrayList<>();
        try {

            PreparedStatement psForusermessagecount1 = connectionchat.prepareStatement("SELECT Chatgroup.ChatgroupID,count(GroupChatLogStatus.GroupChatLogID)\n"
                    + "FROM " + dbName + ".GroupChatLogStatus, " + dbName + ".Chatgroup\n"
                    + "where \n"
                    + "" + dbName + ".GroupChatLogStatus.Status = 0 and " + dbName + ".GroupChatLogStatus.ReceiverID = ?\n"
                    + "and " + dbName + ".Chatgroup.ChatgroupID = " + dbName + ".GroupChatLogStatus.GroupID\n"
                    + "and " + dbName + ".Chatgroup.IsActive = 0\n"
                    + "group by " + dbName + ".GroupChatLogStatus.GroupID;");
            psForusermessagecount1.setInt(1, userid);
            ResultSet resultSetForusermessagecount1 = psForusermessagecount1.executeQuery();
            while (resultSetForusermessagecount1.next()) {
                GroupUserCount groupusercount = new GroupUserCount();
                groupusercount.setGroupID(resultSetForusermessagecount1.getInt("ChatgroupID"));
                groupusercount.setGroupMessageCount(resultSetForusermessagecount1.getInt(2));

                groupMessageCount.add(groupusercount);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connectionchat != null) {
                connectionchat.close();
            }
        }
        return groupMessageCount;
    }

    public ArrayList<Chatlog> getUserChatMessages(int userid, int receiverid, String lastmessagetimestamp) throws Exception {
        //Give Messages with count
        Connection connectionchat = getConnection();
        ArrayList<Chatlog> chatMessages = new ArrayList<>();
        try {
            PreparedStatement psForchatlog = connectionchat.prepareStatement("SELECT * FROM " + dbName + ".Chatlog where \n"
                    + "ReceiverID=? and SenderID=? and ChatTime >= FROM_UNIXTIME(?) or\n"
                    + "ReceiverID=? and SenderID=? and ChatTime >= FROM_UNIXTIME(?);");
            psForchatlog.setInt(1, receiverid);
            psForchatlog.setInt(2, userid);
            psForchatlog.setString(3, lastmessagetimestamp);
            psForchatlog.setInt(4, userid);
            psForchatlog.setInt(5, receiverid);
            psForchatlog.setString(6, lastmessagetimestamp);
            ResultSet resultSetForcChatLog = psForchatlog.executeQuery();
            while (resultSetForcChatLog.next()) {
                Chatlog chatlog = new Chatlog();
                chatlog.setChatlogID(resultSetForcChatLog.getInt("ChatlogID"));
                chatlog.setIPAddress(resultSetForcChatLog.getString("IPAddress"));
                chatlog.setMessage(resultSetForcChatLog.getString("Message"));
                chatlog.setSenderID(resultSetForcChatLog.getInt("SenderID"));
                chatlog.setReceiverID(resultSetForcChatLog.getInt("ReceiverID"));
                chatlog.setChatType(resultSetForcChatLog.getString("ChatType"));
                chatlog.setFileName(resultSetForcChatLog.getString("FileName"));

                chatlog.setChatTime(DateConversion.dateToUnixConversion(resultSetForcChatLog.getTimestamp("ChatTime")));
                chatMessages.add(chatlog);

            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connectionchat != null) {
                connectionchat.close();
            }
        }
        return chatMessages;
    }

    public ArrayList<Chatlog> getGroupChatMessages(int userid, int selectedgroupid, String lastmessagetimestamp) throws Exception {
        //Give Messages with count
        Connection connectionchat = getConnection();
        ArrayList<Chatlog> chatgroupMessages = new ArrayList<>();
        try {
            PreparedStatement psForchatlog = connectionchat.prepareStatement("SELECT distinct(ChatTime), Message,ChatgroupID,SenderID,FileName,ChatType FROM Chatlog where\n"
                    + "ChatgroupID=? and ChatTime >= FROM_UNIXTIME(?) and MessageStatus=0");
            psForchatlog.setInt(1, selectedgroupid);
            psForchatlog.setString(2, lastmessagetimestamp);
            ResultSet resultSetForcChatLog = psForchatlog.executeQuery();
            while (resultSetForcChatLog.next()) {
                Chatlog chatlog = new Chatlog();
//                chatlog.setChatlogID(resultSetForcChatLog.getInt("ChatlogID"));
//                chatlog.setIPAddress(resultSetForcChatLog.getString("IPAddress"));
                chatlog.setMessage(resultSetForcChatLog.getString("Message"));
                chatlog.setSenderID(resultSetForcChatLog.getInt("SenderID"));
                chatlog.setChatType(resultSetForcChatLog.getString("ChatType"));
                chatlog.setFileName(resultSetForcChatLog.getString("FileName"));
//                chatlog.setReceiverID(resultSetForcChatLog.getInt("ReceiverID"));
                chatlog.setChatgroupID(resultSetForcChatLog.getInt("ChatgroupID"));
                chatlog.setChatTime(DateConversion.dateToUnixConversion(resultSetForcChatLog.getTimestamp("ChatTime")));
                chatgroupMessages.add(chatlog);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connectionchat != null) {
                connectionchat.close();
            }
        }
        return chatgroupMessages;
    }

    public String leaveGroup(int groupid, int userid) throws Exception {
        Connection connection = getConnection();
        String message = "";
        boolean isdeleted = true;
        try {

            PreparedStatement psForleavegroup = connection.prepareStatement("UPDATE " + dbName + ".UserChatGroup SET IsDeleted=? Where UserID=? and GroupID=?");
            psForleavegroup.setBoolean(1, isdeleted);
            psForleavegroup.setInt(2, userid);
            psForleavegroup.setInt(3, groupid);

            int result = psForleavegroup.executeUpdate();
            if (result == 1) {
                message = "You Leaved Group Successfully";

            } else {
                message = "Some Error in query";
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return message;
    }

    public ArrayList<RequiredReport> getRequiredReport() throws Exception {
        Connection connection = getConnection();
        ArrayList<RequiredReport> requiredReportlist = new ArrayList<>();
        try {
            PreparedStatement psForRequiredReport = connection.prepareStatement("SELECT RequirementID,RequirementName FROM " + dbName + ".RequiredReport orders LIMIT 12;");
            ResultSet resultSetForRequiredReport = psForRequiredReport.executeQuery();
            while (resultSetForRequiredReport.next()) {
                RequiredReport requiredReport = new RequiredReport();
                requiredReport.setRequirementID(resultSetForRequiredReport.getInt("RequirementID"));
                requiredReport.setRequirementName(resultSetForRequiredReport.getString("RequirementName"));
                requiredReportlist.add(requiredReport);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return requiredReportlist;
    }

    public ArrayList<Patient> getPatientListAsPerClinic(int userid, int patientstatus) throws Exception {
        Connection connection = getConnection();
        ArrayList<Patient> patientList = new ArrayList<>();
        try {

            if (patientstatus == 0) {
                //This condition gives list of patient which are active/not deleted
                PreparedStatement psForPatientList = connection.prepareStatement("SELECT PatientID,AddressID,FirstName,LastName,PhoneNo,Email,DOB,IDProofPicUrl,InsuranceIDfrontpicUrl,InsuranceIDbackpicUrl,ClinicID FROM " + dbName + ".Patient \n"
                        + "where ClinicID = (select ClinicID from " + dbName + ".User where UserID = ?) and is_deleted=0");
                psForPatientList.setInt(1, userid);
                ResultSet resultSetForPatientList = psForPatientList.executeQuery();
                while (resultSetForPatientList.next()) {
                    Patient patient = new Patient();
                    patient.setPatientID(resultSetForPatientList.getInt("PatientID"));
                    patient.setAddressID(resultSetForPatientList.getInt("AddressID"));
                    patient.setFirstName(resultSetForPatientList.getString("FirstName"));
                    patient.setLastName(resultSetForPatientList.getString("LastName"));
                    patient.setPhoneNo(resultSetForPatientList.getString("PhoneNo"));
                    patient.setEmail(resultSetForPatientList.getString("Email"));
                    patient.setDOB(DateConversion.dateToUnixConversion(resultSetForPatientList.getTimestamp("DOB")));
                    patient.setIDProofPicUrl(resultSetForPatientList.getString("IDProofPicUrl"));
                    patient.setInsuranceIDfrontpicUrl(resultSetForPatientList.getString("InsuranceIDfrontpicUrl"));
                    patient.setInsuranceIDbackpicUrl(resultSetForPatientList.getString("InsuranceIDbackpicUrl"));
                    patient.setClinicID(resultSetForPatientList.getInt("ClinicID"));
                    patientList.add(patient);
                }

            } else if (patientstatus == 1) {
                //This condition gives list of patient which are deactive/deleted
                PreparedStatement psForPatientList = connection.prepareStatement("SELECT PatientID,AddressID,FirstName,LastName,PhoneNo,Email,DOB,IDProofPicUrl,InsuranceIDfrontpicUrl,InsuranceIDbackpicUrl,ClinicID FROM " + dbName + ".Patient \n"
                        + "where ClinicID = (select ClinicID from " + dbName + ".User where UserID = ?) and is_deleted=1");
                psForPatientList.setInt(1, userid);
                ResultSet resultSetForPatientList = psForPatientList.executeQuery();
                while (resultSetForPatientList.next()) {
                    Patient patient = new Patient();
                    patient.setPatientID(resultSetForPatientList.getInt("PatientID"));
                    patient.setAddressID(resultSetForPatientList.getInt("AddressID"));
                    patient.setFirstName(resultSetForPatientList.getString("FirstName"));
                    patient.setLastName(resultSetForPatientList.getString("LastName"));
                    patient.setPhoneNo(resultSetForPatientList.getString("PhoneNo"));
                    patient.setEmail(resultSetForPatientList.getString("Email"));
                    patient.setDOB(DateConversion.dateToUnixConversion(resultSetForPatientList.getTimestamp("DOB")));
                    patient.setIDProofPicUrl(resultSetForPatientList.getString("IDProofPicUrl"));
                    patient.setInsuranceIDfrontpicUrl(resultSetForPatientList.getString("InsuranceIDfrontpicUrl"));
                    patient.setInsuranceIDbackpicUrl(resultSetForPatientList.getString("InsuranceIDbackpicUrl"));
                    patient.setClinicID(resultSetForPatientList.getInt("ClinicID"));
                    patientList.add(patient);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return patientList;
    }

    public int uploadFile(String documenturl, int uploadedby, int downloadedby, int referralid) throws Exception {
        Connection connection = getConnection();
        java.util.Date date = new java.util.Date();
        Timestamp uploadtime = new Timestamp(date.getTime());
        Timestamp downloadtime = new Timestamp(date.getTime());
        int result = 0;
        try {
            PreparedStatement psForUploadFile = connection.prepareStatement("INSERT into " + dbName + ".Document(DocumentUrl,UploadedBy,DownloadedBy,UploadTime,DownloadTime,ReferralID) values (?,?,?,?,?,?)");
            psForUploadFile.setString(1, documenturl);
            psForUploadFile.setInt(2, uploadedby);
            psForUploadFile.setInt(3, downloadedby);
            psForUploadFile.setTimestamp(4, uploadtime);
            psForUploadFile.setTimestamp(5, downloadtime);
            psForUploadFile.setInt(6, referralid);
            result = psForUploadFile.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return result;
    }

    public ArrayList<Integer> getClinicStaff(int ClinicID) throws Exception {
        Connection connection = getConnection();
        ArrayList<Integer> clinicStaffList = new ArrayList();
        try {
            PreparedStatement pstogetClinicStaff = connection.prepareStatement("select UserID from " + dbName + ".User where ClinicID=?");
            pstogetClinicStaff.setInt(1, ClinicID);
            ResultSet resultSettogetClinicStaff = pstogetClinicStaff.executeQuery();
            while (resultSettogetClinicStaff.next()) {

                clinicStaffList.add(resultSettogetClinicStaff.getInt("UserID"));

            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return clinicStaffList;
    }

    public ArrayList<ChatGroup> getGroupList(int userID) throws Exception {
        Connection connection = getConnection();
        ArrayList<ChatGroup> MyGroupList = new ArrayList();

        try {
            PreparedStatement psToGetMyGroupList = connection.prepareStatement("Select * from " + dbName + ".Chatgroup where GroupAdmin =? and IsActive=0;");
            psToGetMyGroupList.setInt(1, userID);
            ResultSet resultSetToGetMyGroupList = psToGetMyGroupList.executeQuery();
            while (resultSetToGetMyGroupList.next()) {
                ChatGroup chatgroup = new ChatGroup();
                chatgroup.setChatgroupID(resultSetToGetMyGroupList.getInt("ChatgroupID"));
                chatgroup.setGroupName(resultSetToGetMyGroupList.getString("GroupName"));
                chatgroup.setMemberCount(resultSetToGetMyGroupList.getInt("MemberCount"));

                chatgroup.setGroupAdmin(0);

                if (resultSetToGetMyGroupList.getInt("GroupAdmin") == userID) {
                    chatgroup.setGroupAdmin(resultSetToGetMyGroupList.getInt("GroupAdmin"));
                } else {

                    chatgroup.setGroupAdmin(0);
                }

                MyGroupList.add(chatgroup);

            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return MyGroupList;
    }

    public String changeStatus(int userid) throws Exception {
        Connection connection = getConnection();
        String message = "Sorry Some Error.!";
        try {
            PreparedStatement psForUploadFile = connection.prepareStatement("UPDATE " + dbName + ".User SET Status=? where UserID=?");
            psForUploadFile.setInt(1, 2);
            psForUploadFile.setInt(2, userid);
            psForUploadFile.executeUpdate();
            message = "Updated status";

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return message;
    }

    public HashMap getReferralDtl(int referralID) throws Exception {
        Connection connection = getConnection();
        HashMap<String, Object> getReferralResponse = new HashMap<>();
        Referral referral = new Referral();
        Patient patient = new Patient();
        ArrayList diseaseArrayList = getReasons(referralID);
        ArrayList referredDoctorList = getReferredDoctors(referralID);
        ArrayList referredDocumentList = getReferredDocuments(referralID);
        ArrayList referralCommentsList = getReferralComments(referralID);
        ArrayList referralDocumentsurlList = getReferralDocumentsUrlList(referralID);

        try {
            PreparedStatement psToGetReferralDtl = connection.prepareStatement("SELECT " + dbName + ".Referral.ReferralID," + dbName + ".Referral.TreatedBy," + dbName + ".Patient.*\n"
                    + "," + dbName + ".Referral.Urgency,\n"
                    + "(select SpecializationID from " + dbName + ".Specialization where SpecializationID \n"
                    + "IN (SELECT SpecializationID  FROM " + dbName + ".User where UserID\n"
                    + "IN (select SpecialistID  from " + dbName + ".ReferredTo where ReferralID=?))) as SpecializationID,\n"
                    + "(SELECT  ClinicID FROM " + dbName + ".User where userID =\n"
                    + "(select SpecialistID from " + dbName + ".ReferredTo where ReferralID=" + dbName + ".Referral.ReferralID limit 1)) as ClinicID\n"
                    + "From " + dbName + ".Referral   join Patient\n"
                    + "WHERE  " + dbName + ".Referral.PatientID = " + dbName + ".Patient.PatientID \n"
                    + "AND " + dbName + ".Referral.ReferralID=?");
            psToGetReferralDtl.setInt(1, referralID);
            psToGetReferralDtl.setInt(2, referralID);
            ResultSet resultSetToGetReferralDtl = psToGetReferralDtl.executeQuery();
            while (resultSetToGetReferralDtl.next()) {
                referral.setReferralID(resultSetToGetReferralDtl.getInt("referralID"));
                referral.setTreatedBy(resultSetToGetReferralDtl.getInt("TreatedBy"));
                referral.setClinicID(resultSetToGetReferralDtl.getInt("ClinicID"));

                referral.setUrgency(resultSetToGetReferralDtl.getString("Urgency"));
                referral.setSpecializationID(resultSetToGetReferralDtl.getInt("SpecializationID"));
                patient.setPatientID(resultSetToGetReferralDtl.getInt("PatientID"));

                patient.setDOB(DateConversion.dateToUnixConversion(resultSetToGetReferralDtl.getTimestamp("DOB")));

                patient.setFirstName(resultSetToGetReferralDtl.getString("FirstName"));
                patient.setLastName(resultSetToGetReferralDtl.getString("LastName"));
                patient.setPhoneNo(resultSetToGetReferralDtl.getString("PhoneNo"));
                patient.setIDProofPicUrl(resultSetToGetReferralDtl.getString("IDProofPicUrl"));
                patient.setInsuranceIDfrontpicUrl(resultSetToGetReferralDtl.getString("InsuranceIDfrontpicUrl"));
                patient.setInsuranceIDbackpicUrl(resultSetToGetReferralDtl.getString("InsuranceIDbackpicUrl"));
                patient.setInsuranceNames(getPatientInsuranceNameList(resultSetToGetReferralDtl.getInt("PatientID")));
                getReferralResponse.put("ReferralDetails", referral);
                getReferralResponse.put("PatientDetails", patient);

            }
            getReferralResponse.put("DiseaseList", diseaseArrayList);
            getReferralResponse.put("ReferredDoctorList", referredDoctorList);
            getReferralResponse.put("ReferredDocumentList", referredDocumentList);
            getReferralResponse.put("ReferralCommentsList", referralCommentsList);
            getReferralResponse.put("RefrralDocumentsUrlList", referralDocumentsurlList);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return getReferralResponse;
    }

    public ArrayList getReferralDocumentsUrlList(int referralID) throws Exception {
        Connection connection = getConnection();

        ArrayList doucmentUrlArrayList = new ArrayList();
        try {
            PreparedStatement psToReferraldocumentUrl = connection.prepareStatement("SELECT DocumentID,DocumentUrl FROM " + dbName + ".Document where ReferralID=?");
            psToReferraldocumentUrl.setInt(1, referralID);
            ResultSet resultSetToGetReferraldocumentUrl = psToReferraldocumentUrl.executeQuery();
            while (resultSetToGetReferraldocumentUrl.next()) {
                Document document = new Document();
                document.setDocumentID(resultSetToGetReferraldocumentUrl.getInt("DocumentID"));
                document.setDocumentUrl(resultSetToGetReferraldocumentUrl.getString("DocumentUrl"));
                doucmentUrlArrayList.add(document);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return doucmentUrlArrayList;

    }

    public ArrayList getReferralComments(int referralID) throws Exception {
        Connection connection = getConnection();

        ArrayList commentsArrayList = new ArrayList();
        try {
            PreparedStatement psToReferralcomments = connection.prepareStatement("SELECT rc.ReferralCommentID,rc.ReferralID,rc.CommentMessage,rc.CommentedMessageTime,u.FirstName,u.LastName\n"
                    + "FROM " + dbName + ".ReferralComments rc, User u \n"
                    + "where rc.UserID = u.UserID\n"
                    + "and rc.ReferralID = ?\n");
            psToReferralcomments.setInt(1, referralID);
            ResultSet resultSetToGetReferralcomments = psToReferralcomments.executeQuery();
            while (resultSetToGetReferralcomments.next()) {
                ReferralComments referralComments = new ReferralComments();
                referralComments.setReferralCommentID(resultSetToGetReferralcomments.getInt("ReferralCommentID"));
                referralComments.setReferralID(resultSetToGetReferralcomments.getInt("ReferralID"));
                referralComments.setCommentMessage(resultSetToGetReferralcomments.getString("CommentMessage"));
                referralComments.setCommentedMessageTime(DateConversion.dateToUnixConversion(resultSetToGetReferralcomments.getTimestamp("CommentedMessageTime")));
                referralComments.setUserName(resultSetToGetReferralcomments.getString("FirstName") + " " + resultSetToGetReferralcomments.getString("LastName"));
                commentsArrayList.add(referralComments);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return commentsArrayList;

    }

    public ArrayList getReasons(int referralID) throws Exception {
        Connection connection = getConnection();
        Disease disease;
        ArrayList diseaseArrayList = new ArrayList();

        try {
            PreparedStatement psToGetReasonList
                    = connection.prepareStatement("SELECT d.DiseaseID, d.DiseaseName FROM " + dbName + ".ReferralDisease r, " + dbName + ".Disease d\n"
                            + "where r.ReferralID = ? and \n"
                            + "r.DiseaseID = d.DiseaseID");
            psToGetReasonList.setInt(1, referralID);
            ResultSet resultSetToGetReasonList = psToGetReasonList.executeQuery();
            while (resultSetToGetReasonList.next()) {
                disease = new Disease();
                disease.setDiseaseID(resultSetToGetReasonList.getInt("DiseaseID"));
                disease.setDiseaseName(resultSetToGetReasonList.getString("DiseaseName"));
                diseaseArrayList.add(disease);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return diseaseArrayList;
    }

    public ArrayList getReferredDoctors(int referralID) throws Exception {
        Connection connection = getConnection();
        User user;
        ArrayList referredDoctorList = new ArrayList();

        try {
            PreparedStatement psToGetReasonList
                    = connection.prepareStatement("SELECT  " + dbName + ".User.FirstName\n"
                            + " FROM  " + dbName + ".ReferredTo, " + dbName + ".User \n"
                            + "where  " + dbName + ".ReferredTo.ReferralID=? and\n"
                            + "  " + dbName + ".ReferredTo.SpecialistID= " + dbName + ".User.UserID;");
            psToGetReasonList.setInt(1, referralID);
            ResultSet resultSetToGetReasonList = psToGetReasonList.executeQuery();
            while (resultSetToGetReasonList.next()) {
                user = new User();
                user.setFirstName(resultSetToGetReasonList.getString("FirstName"));
                referredDoctorList.add(user);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return referredDoctorList;
    }

    public ArrayList getReferredDocuments(int referralID) throws Exception {
        Connection connection = getConnection();
        RequiredReport requiredReport;
        ArrayList referredDocumentList = new ArrayList();

        try {
            PreparedStatement psToGetDocumentList = connection.prepareStatement("SELECT " + dbName + ".RequiredReport.RequirementID," + dbName + ".RequiredReport.RequirementName\n"
                    + "FROM " + dbName + ".RequiredReport where " + dbName + ".RequiredReport.RequirementID IN\n"
                    + "(Select " + dbName + ".Document.RequiredReportID from " + dbName + ".Document where " + dbName + ".Document.ReferralID=?)");
            psToGetDocumentList.setInt(1, referralID);
            ResultSet resultSetToGetDocumentList = psToGetDocumentList.executeQuery();
            while (resultSetToGetDocumentList.next()) {
                requiredReport = new RequiredReport();
                requiredReport.setRequirementID(resultSetToGetDocumentList.getInt("RequirementID"));

                requiredReport.setRequirementName(resultSetToGetDocumentList.getString("RequirementName"));
                referredDocumentList.add(requiredReport);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return referredDocumentList;
    }

    public ArrayList<ChatRequest> getSentRequestList(int SenderID) throws Exception {
        Connection connection = getConnection();
        ArrayList<ChatRequest> ChatRequestList = new ArrayList();
        try {
            PreparedStatement psToGetSentRequestList = connection.prepareStatement("SELECT SenderID,ReceiverID,ResponseStatus,CreatedTime,ResponseTime FROM " + dbName + ".ChatRequest where SenderID=?");
            psToGetSentRequestList.setInt(1, SenderID);
            ResultSet resultSetToGetSentRequestList = psToGetSentRequestList.executeQuery();
            while (resultSetToGetSentRequestList.next()) {

                ChatRequest chatrequest = new ChatRequest();
                chatrequest.setSenderID(resultSetToGetSentRequestList.getInt("SenderID"));
                chatrequest.setReceiverID(resultSetToGetSentRequestList.getInt("ReceiverID"));
                chatrequest.setStatus(resultSetToGetSentRequestList.getBoolean("ResponseStatus"));
//                chatrequest.setCreatedTime(resultSetToGetSentRequestList.getTimestamp("CreatedTime"));
//                chatrequest.setResponseTime(resultSetToGetSentRequestList.getTimestamp("ResponseTime"));
                ChatRequestList.add(chatrequest);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return ChatRequestList;
    }

    public String responseToChatRequest(int senderid, int userid, String responseflag) throws Exception {
        Connection connection = getConnection();
        int intResponseflag = Integer.parseInt(responseflag);
        String message = "Sorry Some Error.!";
        int result = 0;
        java.util.Date date = new java.util.Date();
        Timestamp responsetime = new Timestamp(date.getTime());
        try {
            PreparedStatement psForResponseChat = connection.prepareStatement("UPDATE " + dbName + ".ChatRequest SET ResponseStatus = ?,ResponseTime=? where SenderID = ? and ReceiverID = ?;");
            psForResponseChat.setInt(1, intResponseflag);
            psForResponseChat.setTimestamp(2, responsetime);
            psForResponseChat.setInt(3, senderid);
            psForResponseChat.setInt(4, userid);
            psForResponseChat.executeUpdate();

            if (responseflag.equals("1")) {
                PreparedStatement psForInsertClinicId = connection.prepareStatement("insert into " + dbName + ".Contactlist(ClinicID,ClinicIDTo) value \n"
                        + "((Select ClinicID from " + dbName + ".User where UserID=?),(Select ClinicID from " + dbName + ".User where UserID=?))");

                psForInsertClinicId.setInt(1, senderid);
                psForInsertClinicId.setInt(2, userid);
                psForInsertClinicId.executeUpdate();

            }
            message = "Updated chat status";

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return message;
    }

    public User forgotPassword(String emailid) throws Exception {
        Connection connection = getConnection();
        User loginuser = new User();
        try {

            //update randam code for user in database
            String uuid = UUID.randomUUID().toString();

            PreparedStatement objPreparedStatement = connection.prepareStatement("UPDATE " + dbName + ".User SET RandomCode=? WHERE Email=?");
            objPreparedStatement.setString(1, uuid);
            objPreparedStatement.setString(2, emailid);
            int result = objPreparedStatement.executeUpdate();

            // select user details from database
            objPreparedStatement = connection.prepareStatement("SELECT UserID,Email,RandomCode FROM " + dbName + ".User where Email=? ");
            objPreparedStatement.setString(1, emailid);
            ResultSet resultSetForLogin = objPreparedStatement.executeQuery();
            while (resultSetForLogin.next()) {
                loginuser.setUserID(resultSetForLogin.getInt("UserID"));
                loginuser.setEmail(resultSetForLogin.getString("Email"));
                loginuser.setPassword(resultSetForLogin.getString("RandomCode"));
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return loginuser;
    }

    public boolean resetPassword(String password, String token) throws Exception {
        Connection connection = getConnection();
        boolean updateflag = false;
        User loginuser = new User();
        try {
            PreparedStatement objPreparedStatement = connection.prepareStatement("UPDATE " + dbName + ".User SET Password=? WHERE RandomCode=?");
            objPreparedStatement.setString(1, password);
            objPreparedStatement.setString(2, token);
            int result = objPreparedStatement.executeUpdate();
            if (result == 1) {
                System.out.println("Password  updated successfully...");
                objPreparedStatement = connection.prepareStatement("UPDATE " + dbName + ".User SET RandomCode=null WHERE RandomCode=?");
                objPreparedStatement.setString(1, token);
                int result1 = objPreparedStatement.executeUpdate();
                if (result1 == 1) {
                    System.out.println("RandomCode update to null...");
                    updateflag = true;
                }
                return updateflag;
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return updateflag;
    }

    public int createChatGroup(ChatGroup chatgroup, int userid) throws Exception {
        Connection connection = getConnection();
        int chatGroupID = 0;
        try {
            PreparedStatement psForcreateChatGroup = connection.prepareStatement("INSERT INTO " + dbName + ".Chatgroup (GroupName,MemberCount,IsActive,GroupAdmin) VALUES (?,?,0,?)");
            psForcreateChatGroup.setString(1, chatgroup.getGroupName());
            psForcreateChatGroup.setInt(2, chatgroup.getMemberCount());
            psForcreateChatGroup.setInt(3, userid);
            int result = psForcreateChatGroup.executeUpdate();
            if (result > 0) {
                PreparedStatement psForchatGroupID = connection.prepareStatement("SELECT ChatgroupID FROM " + dbName + ".Chatgroup ORDER BY ChatgroupID DESC LIMIT 1");
                ResultSet resultSetForchatGroupID = psForchatGroupID.executeQuery();
                while (resultSetForchatGroupID.next()) {

                    chatGroupID = resultSetForchatGroupID.getInt("ChatgroupID");

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return chatGroupID;
    }

    public int addUserToGroup(String groupmember, int groupID) throws Exception {
        Connection connection = getConnection();
        int result = 0;
        try {
            PreparedStatement psForaddUserToGroup = connection.prepareStatement("INSERT INTO " + dbName + ".UserChatGroup (GroupID,UserID,IsDeleted)VALUES(?,?,0)");
            psForaddUserToGroup.setInt(1, groupID);
            psForaddUserToGroup.setInt(2, Integer.parseInt(groupmember));
            result = psForaddUserToGroup.executeUpdate();

        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return result;
    }

    public int updateChatGroup(ChatGroup chatgroup) throws Exception {
        Connection connection = getConnection();
        int chatGroupID = chatgroup.getChatgroupID();
        try {
            PreparedStatement psForcreateChatGroup = connection.prepareStatement("UPDATE " + dbName + ".Chatgroup SET GroupName=? where ChatgroupID=?");
            psForcreateChatGroup.setString(1, chatgroup.getGroupName());
            psForcreateChatGroup.setInt(2, chatgroup.getChatgroupID());
            int result = psForcreateChatGroup.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return chatGroupID;
    }

    public String deleteGroup(int groupid) throws Exception {
        Connection connection = getConnection();
        String message = "error";
        boolean groupisactive = true;
        try {
            PreparedStatement psForcreateChatGroup = connection.prepareStatement("UPDATE " + dbName + ".Chatgroup SET IsActive=? where ChatgroupID=?  ");
            psForcreateChatGroup.setBoolean(1, groupisactive);
            psForcreateChatGroup.setInt(2, groupid);
            int result = psForcreateChatGroup.executeUpdate();
            if (result == 1) {
                message = "Group Deleted";
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return message;
    }

    public String deleteGroupUser(int groupid, int useridd) throws Exception {
        Connection connection = getConnection();
        String message = "Error";
        boolean temp = true;
        try {
            PreparedStatement psForaddUserToGroup = connection.prepareStatement("UPDATE " + dbName + ".UserChatGroup SET IsDeleted=? where GroupID=? and UserID=?");
            psForaddUserToGroup.setBoolean(1, temp);
            psForaddUserToGroup.setInt(2, groupid);
            psForaddUserToGroup.setInt(3, useridd);
            int result = psForaddUserToGroup.executeUpdate();
            message = "Group User Deleted.!";

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return message;
    }

    public ArrayList<State> insuranceTypeList() throws Exception {
        Connection connection = getConnection();
        ArrayList insurancelist = new ArrayList();
        try {
            PreparedStatement psForState = connection.prepareStatement("SELECT * FROM " + dbName + ".Insurance ");
            ResultSet resultSetForState = psForState.executeQuery();
            while (resultSetForState.next()) {
                Insurance insurance = new Insurance();
                insurance.setInsuranceID(resultSetForState.getInt("InsuranceID"));
                insurance.setInsuranceType(resultSetForState.getString("InsuranceType"));
                insurancelist.add(insurance);

            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return insurancelist;
    }

    public int insertAddress(Address adress) throws Exception {
        Connection connection = getConnection();
        int AddressID = 0;
        try {

            PreparedStatement psForinsertAddress = connection.prepareStatement("INSERT INTO " + dbName + ".Address (HouseNo,Street,CityID,StateID,ZipCode) VALUES (?,?,?,?,?)");
            psForinsertAddress.setString(1, adress.getHouseNo());
            psForinsertAddress.setString(2, adress.getStreet());
            psForinsertAddress.setInt(3, adress.getCityID());
            psForinsertAddress.setInt(4, adress.getStateID());
            psForinsertAddress.setString(5, adress.getZipCode());
            int result = psForinsertAddress.executeUpdate();
            if (result > 0) {
                PreparedStatement psForAddressID = connection.prepareStatement("SELECT AddressID FROM " + dbName + ".Address ORDER BY AddressID DESC LIMIT 1");
                ResultSet resultSetForAddressID = psForAddressID.executeQuery();
                while (resultSetForAddressID.next()) {
                    AddressID = resultSetForAddressID.getInt("AddressID");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return AddressID;
    }

    public int deleteAddress(int adressID) throws Exception {
        Connection connection = getConnection();
        int result = 0;
        try {
            PreparedStatement psFordeleteAddress = connection.prepareStatement("DELETE FROM " + dbName + ".Address WHERE AddressID='" + adressID + "'");
            result = psFordeleteAddress.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return result;
    }

    public int deleteClinic(int clinicID) throws Exception {
        Connection connection = getConnection();
        int result = 0;
        try {
            PreparedStatement psFordeleteClinic = connection.prepareStatement("DELETE FROM " + dbName + ".Clinic WHERE ClinicID='" + clinicID + "'");
            result = psFordeleteClinic.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return result;
    }

    public int insertClinic(Clinic tempclinic, int addressID) throws Exception {
        Connection connection = getConnection();
        int ClinicID = 0;
        try {
            PreparedStatement psForinsertClinic = connection.prepareStatement("INSERT INTO " + dbName + ".Clinic (ClinicName,FaxNo,AdressID,PhoneNo,StaffCount,DoctorCount,Email) VALUES (?,?,?,?,?,?,?)");
            psForinsertClinic.setString(1, tempclinic.getClinicName());
            psForinsertClinic.setString(2, tempclinic.getFaxNo());
            psForinsertClinic.setInt(3, addressID);
            psForinsertClinic.setString(4, tempclinic.getPhoneNo());
            psForinsertClinic.setInt(5, tempclinic.getStaffCount());

            psForinsertClinic.setInt(6, tempclinic.getDoctorCount());

            psForinsertClinic.setString(7, tempclinic.getEmail());
            int result = psForinsertClinic.executeUpdate();
            if (result > 0) {
                PreparedStatement psForClinicID = connection.prepareStatement("SELECT ClinicID FROM " + dbName + ".Clinic ORDER BY ClinicID DESC LIMIT 1");
                ResultSet resultSetForClinicID = psForClinicID.executeQuery();
                while (resultSetForClinicID.next()) {

                    ClinicID = resultSetForClinicID.getInt("ClinicID");

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return ClinicID;
    }

    public ArrayList<User> searchUserToAddInNetwork(int userid, String fname) throws Exception {
        Connection connection = getConnection();
        ArrayList userlist = new ArrayList();
        try {
//            PreparedStatement psForUsersearchlist = connection.prepareStatement("SELECT * FROM User \n"
//                    + "where FirstName like ? and (ClinicID != (select ClinicID from User where UserID = ?) \n"
//                    + "and UserID NOT IN (select UserID FROM Contactlist\n"
//                    + "where ClinicID =(select ClinicID from User where UserID = ?))) and IsApproved = 1 ");

            PreparedStatement psForUsersearchlist = connection.prepareStatement("SELECT * FROM " + dbName + ".User where FirstName like ? and User.ClinicID Not In (select ClinicID as friendClinics\n"
                    + "from     " + dbName + ".Contactlist where ClinicIDTo = (select ClinicID from " + dbName + ".User where UserID = ?)\n"
                    + "union\n"
                    + "select  ClinicIDTo\n"
                    + "from    " + dbName + ".Contactlist where ClinicID = (select ClinicID from " + dbName + ".User where UserID = ?)) and IsApproved = 1 and User.ClinicID != (select ClinicID from " + dbName + ".User where UserID = ?)");

            psForUsersearchlist.setString(1, "%" + fname + "%");
            psForUsersearchlist.setInt(2, userid);
            psForUsersearchlist.setInt(3, userid);
            psForUsersearchlist.setInt(4, userid);
            ResultSet resultSetUsersearchlist = psForUsersearchlist.executeQuery();
            while (resultSetUsersearchlist.next()) {
                User user = new User();
                user.setUserID(resultSetUsersearchlist.getInt("UserID"));
                user.setFirstName(resultSetUsersearchlist.getString("FirstName"));
                user.setLastName(resultSetUsersearchlist.getString("LastName"));
                user.setImageUrlThumbnail(resultSetUsersearchlist.getString("ImageUrlThumbnail"));

                userlist.add(user);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return userlist;
    }

    public String sendChatRequest(int userid, int receiverid) throws Exception {
        Connection connection = getConnection();
        String message = "";
        java.util.Date date = new java.util.Date();
        Timestamp createtime = new Timestamp(date.getTime());

        try {
            PreparedStatement psForsendrequest = connection.prepareStatement("INSERT INTO " + dbName + ".ChatRequest (SenderID,ReceiverID,ResponseStatus,CreatedTime)VALUES(?,?,0,?)");
            psForsendrequest.setInt(1, userid);
            psForsendrequest.setInt(2, receiverid);
            psForsendrequest.setTimestamp(3, createtime);

            psForsendrequest.executeUpdate();
            message = "Request Sent Successfully";

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return message;
    }

    public int getClinicAsPerUser(int userid) throws Exception {

        Connection connection = getConnection();
        int clinicid = 0;

        try {
            PreparedStatement psToGetClinicId = connection.prepareStatement("SELECT ClinicID FROM " + dbName + ".User where UserID=?");
            psToGetClinicId.setInt(1, userid);
            ResultSet resultSetToGetClinicID = psToGetClinicId.executeQuery();
            while (resultSetToGetClinicID.next()) {

                clinicid = resultSetToGetClinicID.getInt("ClinicID");

            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return clinicid;

    }

    public HashMap getGroupDetails(int groupid) throws Exception {
        Connection connection = getConnection();
        ArrayList arrayOfUsers = new ArrayList();
        HashMap responceHashMap = new HashMap<>();
        try {

            PreparedStatement psForGetGroupName = connection.prepareStatement("Select GroupName,ChatgroupID from " + dbName + ".Chatgroup where ChatgroupID = ?");
            psForGetGroupName.setInt(1, groupid);
            ResultSet resultSetForGroupName = psForGetGroupName.executeQuery();
            while (resultSetForGroupName.next()) {
                String groupName = resultSetForGroupName.getString("GroupName");
                int groupID = resultSetForGroupName.getInt("ChatgroupID");
                responceHashMap.put("GroupName", groupName);
                responceHashMap.put("groupid", groupID);
            }

            PreparedStatement psForGetGroupUsers = connection.prepareStatement("select * from " + dbName + ".User where UserID in (Select UserID from " + dbName + ".UserChatGroup where GroupID = ? and IsDeleted=0) ");
            psForGetGroupUsers.setInt(1, groupid);
            ResultSet resultSetForGroupUsers = psForGetGroupUsers.executeQuery();
            while (resultSetForGroupUsers.next()) {
                User user = new User();
                user.setUserID(resultSetForGroupUsers.getInt("UserID"));
                user.setFirstName(resultSetForGroupUsers.getString("FirstName"));
                user.setLastName(resultSetForGroupUsers.getString("LastName"));
                user.setIsOnline(resultSetForGroupUsers.getBoolean("IsOnline"));
                arrayOfUsers.add(user);
            }
            responceHashMap.put("users", arrayOfUsers);
//            PreparedStatement psForGetIsAdmin = connection.prepareStatement("Select IsAdmin form UserChatGroup where UserID=?");
//            ResultSet resultSetForIsAdmin = psForGetIsAdmin.executeQuery();
//            while (resultSetForIsAdmin.next()) {
//                boolean isadminflag = resultSetForIsAdmin.getBoolean("IsAdmin");
//            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return responceHashMap;
    }

    public ArrayList<Patient> searchPatient(String searchText, int userid) throws Exception {
        Connection connection = getConnection();
        ArrayList patientlist = new ArrayList();
        try {
            PreparedStatement psForPatientsearchlist = connection.prepareStatement("SELECT * FROM " + dbName + ".Patient where (FirstName like ? or LastName like ?\n"
                    + " ) and ClinicID =\n"
                    + "(select ClinicID from " + dbName + ".User where UserID = ?) and is_deleted = 0");

            psForPatientsearchlist.setString(1, "%" + searchText + "%");
            psForPatientsearchlist.setString(2, "%" + searchText + "%");
            psForPatientsearchlist.setInt(3, userid);
            ResultSet resultSetPatientsearchlist = psForPatientsearchlist.executeQuery();
            while (resultSetPatientsearchlist.next()) {
                Patient patient = new Patient();
                patient.setPatientID(resultSetPatientsearchlist.getInt("PatientID"));
                patient.setFirstName(resultSetPatientsearchlist.getString("FirstName"));
                patient.setLastName(resultSetPatientsearchlist.getString("LastName"));
                patient.setPhoneNo(resultSetPatientsearchlist.getString("PhoneNo"));
                patient.setIDProofPicUrl(resultSetPatientsearchlist.getString("IDProofPicUrl"));
                patient.setInsuranceIDbackpicUrl(resultSetPatientsearchlist.getString("InsuranceIDbackpicUrl"));
                patient.setInsuranceIDfrontpicUrl(resultSetPatientsearchlist.getString("InsuranceIDfrontpicUrl"));
                patient.setFullName(resultSetPatientsearchlist.getString("FirstName") + " " + resultSetPatientsearchlist.getString("LastName"));
                patient.setDOB(DateConversion.dateToUnixConversion(resultSetPatientsearchlist.getTimestamp("DOB")));
                patient.setInsuranceNames(getPatientInsuranceNameList(resultSetPatientsearchlist.getInt("PatientID")));

                patientlist.add(patient);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return patientlist;
    }

    public ArrayList<Role> getRoleList() throws Exception {
        Connection connection = getConnection();
        ArrayList rolelist = new ArrayList();
        try {
            PreparedStatement psForCity = connection.prepareStatement("SELECT RoleID,RoleName FROM " + dbName + ".Role");

            ResultSet resultSetForRole = psForCity.executeQuery();
            while (resultSetForRole.next()) {
                Role role = new Role();
                if (resultSetForRole.getString("RoleName").equals("GP") || resultSetForRole.getString("RoleName").equals("Specialist")) {

                } else {
                    role.setRoleID(resultSetForRole.getInt("RoleID"));
                    role.setRoleName(resultSetForRole.getString("RoleName"));
                    rolelist.add(role);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return rolelist;
    }

    public int newSignup(Map<String, Object> signupData) throws Exception {
        Connection connection = getConnection();
        Gson gson = new Gson();
        int result = 0;
        String userIDList;

        Clinic clinic = gson.fromJson(gson.toJson(signupData.get("clinicDetails")), Clinic.class
        );
        Address address = gson.fromJson(gson.toJson(clinic), Address.class
        );
        List<User> arrayOfStaff = gson.fromJson(gson.toJson(signupData.get("staffDetails")), new TypeToken<List<User>>() {
        }.getType());

        String stringOfArrayOfStaff = GetStringofArrayof.getstringOfArrayOfStaff(arrayOfStaff);

        List<User> arrayOfDoctor = gson.fromJson(gson.toJson(signupData.get("doctorDetails")), new TypeToken<List<User>>() {
        }.getType());

        String stringOfArrayOfDoctor = GetStringofArrayof.getstringOfArrayOfDoctor(arrayOfDoctor);

        try {
            CallableStatement stmt = connection.prepareCall("{ call SignupProc(?,?,?,?,?,?,?,?,?,?,?,?,?,?) }");
            stmt.setString(1, address.getHouseNo());
            stmt.setString(2, address.getStreet());
            stmt.setInt(3, address.getCityID());
            stmt.setInt(4, address.getStateID());
            stmt.setString(5, address.getZipCode());
            stmt.setString(6, clinic.getClinicName());
            stmt.setString(7, clinic.getFaxNo());
            stmt.setString(8, clinic.getPhoneNo());
            stmt.setInt(9, clinic.getStaffCount());
            stmt.setInt(10, clinic.getDoctorCount());
            stmt.setString(11, clinic.getEmail());
            stmt.setString(12, stringOfArrayOfStaff);
            stmt.setString(13, stringOfArrayOfDoctor);
            stmt.registerOutParameter("useridList", java.sql.Types.VARCHAR);

            result = stmt.executeUpdate();
            userIDList = stmt.getString("useridList");

            ArrayList<String> UrlList = new ArrayList<>();
            for (User doc : arrayOfDoctor) {
                if (doc.getDocumentUrl() != null) {
                    UrlList.add(doc.getDocumentUrl());
                }
            }

            if (result > 0 && UrlList.size() > 0) {

                ArrayList<Integer> uidIntegerList = new ArrayList<>();
                StringTokenizer tokenizer = new StringTokenizer(userIDList, ",");
                while (tokenizer.hasMoreTokens()) {
                    uidIntegerList.add(Integer.parseInt(tokenizer.nextToken()));
                }

                uidIntegerList.remove(uidIntegerList.size() - 1);

                FileOperation mv = new FileOperation();
                ArrayList<String> finalUrlList = mv.movefile(UrlList, uidIntegerList, 1);

                for (int i = 0; i < finalUrlList.size(); i++) {
                    updateProfileImage(finalUrlList.get(i), uidIntegerList.get(i));
                }

            }

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            if (connection != null) {
                connection.close();
            }

        }
        return result;
    }

    public Chatlog sendChatMessage(int userid, Chatlog chatlog) throws Exception {
        Connection connection = getConnection();
        Chatlog chatlog1 = new Chatlog();
        try {

            // print the IP Address of your machine (inside your local network)
            System.out.println("you new IPV4 IS::" + Inet4Address.getLocalHost().getHostAddress());

            // print the IP Address of a web site
            System.out.println("Refque IP is:-" + Inet4Address.getByName("www.refque.com"));

            PreparedStatement psForsendmessage = connection.prepareStatement("INSERT INTO " + dbName + ".Chatlog (IPAddress,Message,SenderID,ReceiverID,ChatTime,ChatType)VALUES(?,?,?,?,?,?)");
            psForsendmessage.setString(1, Inet4Address.getLocalHost().getHostAddress());
            psForsendmessage.setString(2, chatlog.getMessage());
            psForsendmessage.setInt(3, userid);
            psForsendmessage.setInt(4, chatlog.getReceiverID());
            psForsendmessage.setTimestamp(5, DateConversion.stringToDateConversion(chatlog.getChatTime()));
            psForsendmessage.setString(6, chatlog.getChatType());
            int result = psForsendmessage.executeUpdate();
            PreparedStatement psForGetLastInserted = connection.prepareStatement("SELECT * FROM " + dbName + ".Chatlog ORDER BY ChatlogID DESC LIMIT 1");
            ResultSet resultSetForGetLastInserted = psForGetLastInserted.executeQuery();
            while (resultSetForGetLastInserted.next()) {

                chatlog1.setChatlogID(resultSetForGetLastInserted.getInt("ChatlogID"));
                chatlog1.setMessage(resultSetForGetLastInserted.getString("Message"));
                chatlog1.setReceiverID(resultSetForGetLastInserted.getInt("ReceiverID"));
                chatlog1.setSenderID(resultSetForGetLastInserted.getInt("SenderID"));
                chatlog1.setChatTime(DateConversion.dateToUnixConversion(resultSetForGetLastInserted.getTimestamp("ChatTime")));
                chatlog1.setChatType(resultSetForGetLastInserted.getString("ChatType"));

            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return chatlog1;
    }

    public int updateProfileImage(String imageUrl, int userid) throws Exception {
        Connection connection = getConnection();
        int result;
        try {

            PreparedStatement psForupdateProfileImage = connection.prepareStatement("UPDATE " + dbName + ".User SET ImageUrlOriginal = ?,ImageUrlThumbnail = ?,ImageUrlBlur = ? WHERE UserID = ?");
            psForupdateProfileImage.setString(1, imageUrl);
            psForupdateProfileImage.setString(2, imageUrl);
            psForupdateProfileImage.setString(3, imageUrl);
            psForupdateProfileImage.setInt(4, userid);

            result = psForupdateProfileImage.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return result;
    }

    public ArrayList getDoctorListForRefer(int specializationid, int clinicid, int patientid) throws Exception {
        Connection connection = getConnection();
        ArrayList doctorlist = new ArrayList();
        try {
            PreparedStatement psForDoctor = connection.prepareStatement("SELECT UserID,FirstName,LastName FROM " + dbName + ".User \n"
                    + "where UserID in (SELECT DoctorID FROM " + dbName + ".InsuranceDoctor \n"
                    + "where InsuranceID in (select InsuranceID from " + dbName + ".InsurancePatient where PatientID = ?))\n"
                    + "and SpecializationID = ? and ClinicID = ?");

            psForDoctor.setInt(1, patientid);
            psForDoctor.setInt(2, specializationid);
            psForDoctor.setInt(3, clinicid);

            ResultSet resultSetForDoctors = psForDoctor.executeQuery();
            while (resultSetForDoctors.next()) {
                User user = new User();
                user.setUserID(resultSetForDoctors.getInt("UserID"));
                user.setFirstName(resultSetForDoctors.getString("FirstName"));
                user.setLastName(resultSetForDoctors.getString("LastName"));
                doctorlist.add(user);
                System.out.println(doctorlist);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return doctorlist;
    }

    public User getSelectedUserDetails(int sentUserId) throws Exception {
        Connection connection = getConnection();
        User user = new User();
        try {
            PreparedStatement psForDoctor = connection.prepareStatement("Select * from " + dbName + ".User where UserID=?;");

            psForDoctor.setInt(1, sentUserId);
            ResultSet resultSetForSelectedUser = psForDoctor.executeQuery();
            while (resultSetForSelectedUser.next()) {

                user.setUserID(resultSetForSelectedUser.getInt("UserID"));
                user.setFirstName(resultSetForSelectedUser.getString("FirstName"));
                user.setLastName(resultSetForSelectedUser.getString("LastName"));
                user.setImageUrlOriginal(resultSetForSelectedUser.getString("ImageUrlOriginal"));
                user.setImageUrlThumbnail(resultSetForSelectedUser.getString("ImageUrlThumbnail"));

            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return user;
    }

    public ArrayList<Credentials> getCredentials() throws Exception {
        Connection connection = getConnection();
        ArrayList credentiallist = new ArrayList();
        try {
            PreparedStatement psForCredentials = connection.prepareStatement("SELECT CredentialID,CredentialName FROM " + dbName + ".Credential");

            ResultSet resultSetForCredentials = psForCredentials.executeQuery();
            while (resultSetForCredentials.next()) {
                Credentials credentials = new Credentials();
                credentials.setCredentialID(resultSetForCredentials.getInt("CredentialID"));
                credentials.setCredentialName(resultSetForCredentials.getString("CredentialName"));
                credentiallist.add(credentials);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return credentiallist;
    }

    public ArrayList<RequiredReport> getRequiredReportForRefer(int doctorid) throws Exception {
        Connection connection = getConnection();
        ArrayList credentiallist = new ArrayList();
        try {
            PreparedStatement psForRequiredReport = connection.prepareStatement("Select RequirementID,RequirementName from " + dbName + ".RequiredReport where RequirementID In\n"
                    + "(Select RequiredReportID from " + dbName + ".SpecialistReport where DoctorID=?)");

            psForRequiredReport.setInt(1, doctorid);
            ResultSet resultSetForRequiredReport = psForRequiredReport.executeQuery();
            while (resultSetForRequiredReport.next()) {
                RequiredReport requiredReport = new RequiredReport();
                requiredReport.setRequirementID(resultSetForRequiredReport.getInt("RequirementID"));
                requiredReport.setRequirementName(resultSetForRequiredReport.getString("RequirementName"));
                credentiallist.add(requiredReport);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return credentiallist;
    }

    public String changeMessageStatus(int selecteduserid, int userid) throws Exception {
        Connection connection = getConnection();
        String message = "Error In Query Helper";
        boolean temp = true;

        try {
            if (selecteduserid > 0) {
                PreparedStatement psForchangeStatus = connection.prepareStatement("UPDATE " + dbName + ".Chatlog SET MessageStatus=? where ReceiverID=? and SenderID=? and MessageStatus=0");
                psForchangeStatus.setBoolean(1, temp);
                psForchangeStatus.setInt(2, userid);
                psForchangeStatus.setInt(3, selecteduserid);

                int result = psForchangeStatus.executeUpdate();
                message = "User's Message Readed..!!";
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return message;
    }

    // this method is used for old group chat functionallity to count new/unread group messages
//    public String changeGroupMessageStatus(int userid, int selectedgroupid) throws Exception {
//        Connection connection = getConnection();
//        String message = "Error In Query Helper";
//        boolean temp = true;
//        
//        try {
//            if (selectedgroupid > 0) {
//                PreparedStatement psForchangegroupStatus = connection.prepareStatement("UPDATE " + dbName + ".GroupChatLogStatus SET Status=? where ReceiverID=? and GroupID=? and Status=0");
//                psForchangegroupStatus.setBoolean(1, temp);
//                psForchangegroupStatus.setInt(2, userid);
//                psForchangegroupStatus.setInt(3, selectedgroupid);
//                
//                int result = psForchangegroupStatus.executeUpdate();
//                message = "Groups's Message Readed..!!";
//                
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw e;
//        } finally {
//            if (connection != null) {
//                connection.close();
//            }
//        }
//        return message;
//    }
    public Chatlog sendGroupChatMessage(int userid, Chatlog chatlog) throws Exception {
        Connection connection = getConnection();
        Chatlog chatlog1 = new Chatlog();
        try {
            //NEW CHAT GROUP CODING

            ArrayList<Integer> userlistforgroup = new ArrayList<>();
            userlistforgroup = getGroupUsersAcceptLoginUser(chatlog.getChatgroupID(), userid);

            for (int i = 0; i < userlistforgroup.size(); i++) {
                PreparedStatement psForsendmessage = connection.prepareStatement("INSERT INTO " + dbName + ".Chatlog (Message,ChatgroupID,SenderID,ReceiverID,ChatTime,ChatType)VALUES(?,?,?,?,?,?)");
                psForsendmessage.setString(1, chatlog.getMessage());
                psForsendmessage.setInt(2, chatlog.getChatgroupID());
                psForsendmessage.setInt(3, userid);
                psForsendmessage.setInt(4, userlistforgroup.get(i));
                psForsendmessage.setTimestamp(5, DateConversion.stringToDateConversion(chatlog.getChatTime()));
                psForsendmessage.setString(6, chatlog.getChatType());
                psForsendmessage.executeUpdate();
            }

            PreparedStatement psForGetLastInserted = connection.prepareStatement("SELECT * FROM " + dbName + ".Chatlog ORDER BY ChatlogID DESC LIMIT 1");
            ResultSet resultSetForGetLastInserted = psForGetLastInserted.executeQuery();
            while (resultSetForGetLastInserted.next()) {

                chatlog1.setChatlogID(resultSetForGetLastInserted.getInt("ChatlogID"));
                chatlog1.setMessage(resultSetForGetLastInserted.getString("Message"));
                chatlog1.setReceiverID(resultSetForGetLastInserted.getInt("ReceiverID"));
                chatlog1.setSenderID(resultSetForGetLastInserted.getInt("SenderID"));
                chatlog1.setChatTime(DateConversion.dateToUnixConversion(resultSetForGetLastInserted.getTimestamp("ChatTime")));
                chatlog1.setChatType(resultSetForGetLastInserted.getString("ChatType"));
                chatlog1.setChatgroupID(resultSetForGetLastInserted.getInt("ChatgroupID"));

            }

            //OLD GROUP CHAT CODING 
//
//            PreparedStatement psForsendmessage = connection.prepareStatement("INSERT INTO " + dbName + ".Chatlog (Message,ChatgroupID,SenderID,ChatTime,ChatType)VALUES(?,?,?,?,?)");
//            psForsendmessage.setString(1, chatlog.getMessage());
//            psForsendmessage.setInt(2, chatlog.getChatgroupID());
//            psForsendmessage.setInt(3, userid);
//
//            psForsendmessage.setTimestamp(4, DateConversion.stringToDateConversion(chatlog.getChatTime()));
//            psForsendmessage.setString(5, chatlog.getChatType());
//            psForsendmessage.executeUpdate();
//            PreparedStatement psForGetLastInserted = connection.prepareStatement("SELECT * FROM " + dbName + ".Chatlog ORDER BY ChatlogID DESC LIMIT 1");
//            ResultSet resultSetForGetLastInserted = psForGetLastInserted.executeQuery();
//            while (resultSetForGetLastInserted.next()) {
//
//                chatlog1.setChatlogID(resultSetForGetLastInserted.getInt("ChatlogID"));
//                chatlog1.setMessage(resultSetForGetLastInserted.getString("Message"));
//                chatlog1.setReceiverID(resultSetForGetLastInserted.getInt("ReceiverID"));
//                chatlog1.setSenderID(resultSetForGetLastInserted.getInt("SenderID"));
//                chatlog1.setChatTime(DateConversion.dateToUnixConversion(resultSetForGetLastInserted.getTimestamp("ChatTime")));
//                chatlog1.setChatType(resultSetForGetLastInserted.getString("ChatType"));
//                chatlog1.setChatgroupID(resultSetForGetLastInserted.getInt("ChatgroupID"));
//
//            }
//            PreparedStatement psForgetgroupusers = connection.prepareStatement("SELECT UserID FROM " + dbName + ".UserChatGroup where GroupID=?");
//            psForgetgroupusers.setInt(1, chatlog1.getChatgroupID());
//            ResultSet resultSetForGetGroupUsers = psForgetgroupusers.executeQuery();
//            ArrayList<Integer> userlistforgroup = new ArrayList<>();
//            while (resultSetForGetGroupUsers.next()) {
//                userlistforgroup.add(resultSetForGetGroupUsers.getInt("UserID"));
//
//            }
            //this is for maintaining individial group chat read/unread chat message which was included in previous group chat functionallityf
//            PreparedStatement psForsetstatus = connection.prepareStatement("INSERT INTO " + dbName + ".GroupChatLogStatus(ChatLogID,GroupID,ReceiverID)VALUES(?,?,?)");
//            for (int i = 0; i < userlistforgroup.size(); i++) {
//                psForsetstatus.setInt(1, chatlog1.getChatlogID());
//                psForsetstatus.setInt(2, chatlog1.getChatgroupID());
//                psForsetstatus.setInt(3, userlistforgroup.get(i));
//
//                psForsetstatus.executeUpdate();
//            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return chatlog1;
    }

    public HashMap getNotifications(int userid, Chatlog chatlog) throws Exception {
        Connection connection = getConnection();
        HashMap<String, Integer> notificationResonseMap = new HashMap<>();
        Chatlog chatlog1 = new Chatlog();
        try {

            PreparedStatement psForsendmessage = connection.prepareStatement("INSERT INTO " + dbName + ".Chatlog (Message,ChatgroupID,SenderID,ChatTime,ChatType)VALUES(?,?,?,?,?)");
            psForsendmessage.setString(1, chatlog.getMessage());
            psForsendmessage.setInt(2, chatlog.getChatgroupID());
            psForsendmessage.setInt(3, userid);

            psForsendmessage.setTimestamp(4, DateConversion.stringToDateConversion(chatlog.getChatTime()));
            psForsendmessage.setString(5, chatlog.getChatType());
            psForsendmessage.executeUpdate();
            PreparedStatement psForGetLastInserted = connection.prepareStatement("SELECT * FROM " + dbName + ".Chatlog ORDER BY ChatlogID DESC LIMIT 1");
            ResultSet resultSetForGetLastInserted = psForGetLastInserted.executeQuery();
            while (resultSetForGetLastInserted.next()) {

                chatlog1.setChatlogID(resultSetForGetLastInserted.getInt("ChatlogID"));
                chatlog1.setMessage(resultSetForGetLastInserted.getString("Message"));
                chatlog1.setReceiverID(resultSetForGetLastInserted.getInt("ReceiverID"));
                chatlog1.setSenderID(resultSetForGetLastInserted.getInt("SenderID"));
                chatlog1.setChatTime(DateConversion.dateToUnixConversion(resultSetForGetLastInserted.getTimestamp("ChatTime")));
                chatlog1.setChatType(resultSetForGetLastInserted.getString("ChatType"));
                chatlog1.setChatgroupID(resultSetForGetLastInserted.getInt("ChatgroupID"));

            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return notificationResonseMap;
    }

    public ArrayList getRefferedToList(int userid) throws Exception {
        Connection connection = getConnection();
        ArrayList<Patient> referredtolist = new ArrayList<>();
        try {

            PreparedStatement psForGetRefferedList = connection.prepareStatement("select rt.ReferralID,pt.FirstName,pt.LastName,\n"
                    + "pt.PhoneNo,rt.CreatedTime,count(rtt.ReferralID) as referralCount,\n"
                    + "(SELECT  " + dbName + ".User.FirstName FROM " + dbName + ".User WHERE   " + dbName + ".User.UserID = rt.TreatedBy) as TreatedBy\n"
                    + "from " + dbName + ".Referral rt, " + dbName + ".Patient pt, " + dbName + ".ReferredTo rtt\n"
                    + "where rt.ReferredBy in (SELECT UserID FROM " + dbName + ".User where ClinicID = (SELECT ClinicID FROM " + dbName + ".User where UserID = ?)) and\n"
                    + "rt.PatientID = pt.PatientID and\n"
                    + "rt.ReferralID = rtt.ReferralID group by rt.ReferralID");
            psForGetRefferedList.setInt(1, userid);
            ResultSet resultSetForGetRefferedList = psForGetRefferedList.executeQuery();
            while (resultSetForGetRefferedList.next()) {
                Patient patient = new Patient();
                patient.setReferralID(resultSetForGetRefferedList.getInt("ReferralID"));
                patient.setFirstName(resultSetForGetRefferedList.getString("FirstName"));
                patient.setLastName(resultSetForGetRefferedList.getString("LastName"));
                patient.setPhoneNo(resultSetForGetRefferedList.getString("PhoneNo"));
                patient.setReferredAcceptedDoctorName(resultSetForGetRefferedList.getString("TreatedBy"));
                patient.setRequestAcceptedTime(resultSetForGetRefferedList.getTimestamp(5));
                patient.setReferralCount(resultSetForGetRefferedList.getInt(6));
                referredtolist.add(patient);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return referredtolist;
    }

    public ArrayList getReferredFromList(int userid, String filtertimestamp) throws Exception {
        Connection connection = getConnection();
        ArrayList<Patient> referredfromlist = new ArrayList<>();
        try {

            if (filtertimestamp.equals("")) {
                PreparedStatement psForGetRefferedFromList = connection.prepareStatement("Select pt.FirstName, pt.LastName,ut.FirstName, ut.LastName ,rt.*, \n"
                        + "pt.PhoneNo,pt.DOB ,rt.CreatedTime from \n"
                        + "" + dbName + ".ReferredTo rtt, " + dbName + ".Referral rt, \n"
                        + "" + dbName + ".Patient pt, " + dbName + ".User ut\n"
                        + "where rtt.SpecialistID In\n"
                        + "(SELECT UserID FROM " + dbName + ".User \n"
                        + "where ClinicID = (SELECT ClinicID FROM " + dbName + ".User where UserID = ?))\n"
                        + "and rtt.ReferralID = rt.ReferralID\n"
                        + "and rt.PatientID = pt.PatientID\n"
                        + "and rtt.SpecialistID = ut.UserID");
                psForGetRefferedFromList.setInt(1, userid);

                ResultSet resultSetForGetRefferedFromList = psForGetRefferedFromList.executeQuery();
                while (resultSetForGetRefferedFromList.next()) {
                    Patient patient = new Patient();
                    patient.setReferralID(resultSetForGetRefferedFromList.getInt("ReferralID"));
                    patient.setFirstName(resultSetForGetRefferedFromList.getString(1) + " " + resultSetForGetRefferedFromList.getString(2));
                    patient.setTreatedBy(resultSetForGetRefferedFromList.getInt("TreatedBy"));
                    patient.setPhoneNo(resultSetForGetRefferedFromList.getString("PhoneNo"));
                    patient.setReferredAcceptedDoctorName(resultSetForGetRefferedFromList.getString(3) + " " + resultSetForGetRefferedFromList.getString(4));
                    patient.setRefferedToTime(resultSetForGetRefferedFromList.getTimestamp("CreatedTime"));
                    patient.setDOB(DateConversion.dateToUnixConversion(resultSetForGetRefferedFromList.getTimestamp("DOB")));
                    referredfromlist.add(patient);
                }
            } else {

                PreparedStatement psForGetRefferedFromList = connection.prepareStatement("Select pt.FirstName, pt.LastName,ut.FirstName, ut.LastName,rt.*, \n"
                        + "pt.PhoneNo,pt.DOB, rt.CreatedTime from \n"
                        + "" + dbName + ".ReferredTo rtt, " + dbName + ".Referral rt, \n"
                        + "" + dbName + ".Patient pt, " + dbName + ".User ut\n"
                        + "where rtt.SpecialistID In\n"
                        + "(SELECT UserID FROM " + dbName + ".User \n"
                        + "where ClinicID = (SELECT ClinicID FROM " + dbName + ".User where UserID = ?))\n"
                        + "and rtt.ReferralID = rt.ReferralID\n"
                        + "and rt.PatientID = pt.PatientID\n"
                        + "and rtt.SpecialistID = ut.UserID and CreatedTime >= from_unixtime(?)");
                psForGetRefferedFromList.setInt(1, userid);
                psForGetRefferedFromList.setString(2, filtertimestamp);
                ResultSet resultSetForGetRefferedFromList = psForGetRefferedFromList.executeQuery();
                while (resultSetForGetRefferedFromList.next()) {
                    Patient patient = new Patient();
                    patient.setReferralID(resultSetForGetRefferedFromList.getInt("ReferralID"));
                    patient.setFirstName(resultSetForGetRefferedFromList.getString(1) + " " + resultSetForGetRefferedFromList.getString(2));
                    patient.setTreatedBy(resultSetForGetRefferedFromList.getInt("TreatedBy"));
                    patient.setPhoneNo(resultSetForGetRefferedFromList.getString("PhoneNo"));
                    patient.setReferredAcceptedDoctorName(resultSetForGetRefferedFromList.getString(3) + " " + resultSetForGetRefferedFromList.getString(4));
                    patient.setRefferedToTime(resultSetForGetRefferedFromList.getTimestamp("CreatedTime"));
                    patient.setDOB(DateConversion.dateToUnixConversion(resultSetForGetRefferedFromList.getTimestamp("DOB")));
                    referredfromlist.add(patient);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return referredfromlist;
    }

    public String deletePatient(int patientid, int patientstatus) throws Exception {
        Connection connection = getConnection();
        boolean temp = true;
        String message = "Sorry Some Error in query";

        try {

            //Here patientstatus=0 means activ  that patient.
            if (patientstatus == 1) {
                PreparedStatement psForchangeStatus = connection.prepareStatement("UPDATE " + dbName + ".Patient SET is_deleted=? where PatientID=?");
                psForchangeStatus.setBoolean(1, temp);
                psForchangeStatus.setInt(2, patientid);
                psForchangeStatus.executeUpdate();
                message = "Your Patient Deactivated Successfully";

            } else if (patientstatus == 0) {
                temp = false;
                PreparedStatement psForchangeStatus = connection.prepareStatement("UPDATE " + dbName + ".Patient SET is_deleted=? where PatientID=?");
                psForchangeStatus.setBoolean(1, temp);
                psForchangeStatus.setInt(2, patientid);
                psForchangeStatus.executeUpdate();
                message = "Your Patient Activated Successfully";

            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return message;
    }

    public int GetSignupDtl() throws Exception {
        Connection connection = getConnection();
        Gson gson = new Gson();
        int result = 0;

        try {
            CallableStatement stmt = connection.prepareCall("{ call getuid(?) }");

            stmt.registerOutParameter(1, java.sql.Types.INTEGER);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result = rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            if (connection != null) {
                connection.close();
            }

        }
        return result;
    }

    public ReferralComments addCommentsOnReferral(ReferralComments referralcomments, int userid) throws Exception {
        Connection connection = getConnection();
        String message = "Error In Query Helper";
        boolean temp = true;
        java.util.Date date = new java.util.Date();
        try {
            PreparedStatement psForaddcommentsonreferral = connection.prepareStatement("INSERT INTO " + dbName + ".ReferralComments (ReferralID,CommentMessage,CommentedMessageTime,UserID)VALUES(?,?,?,?)");
            psForaddcommentsonreferral.setInt(1, referralcomments.getReferralID());
            psForaddcommentsonreferral.setString(2, referralcomments.getCommentMessage());
            psForaddcommentsonreferral.setTimestamp(3, new Timestamp(date.getTime()));
            psForaddcommentsonreferral.setInt(4, userid);

            int result = psForaddcommentsonreferral.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return referralcomments;
    }

    public int newCreateReferral(Referral referral) throws Exception {
        Connection connection = getConnection();
        int result = 0;
        int referralId;
        String docIdList;

        String referralDtl = GetStringofArrayof.getstringOfArrayOfReferalDtl(referral);

        try {
            CallableStatement stmt = connection.prepareCall("{ call CreateReferralProc(?,?,?,?,?) }");
            stmt.setString(1, referralDtl);
            stmt.setInt(2, referral.getSelectedSpecialization());
            stmt.setString(3, referral.getCommentMessage());
            stmt.registerOutParameter("refID", java.sql.Types.INTEGER);
            stmt.registerOutParameter("docIdList", java.sql.Types.OTHER);

            result = stmt.executeUpdate();
            referralId = stmt.getInt("refID");
            docIdList = stmt.getString("docIdList");

            if (result > 0 && (referral.getSelectedOtherRequiredDocuments().size() > 0 || referral.getSelectedRequiredDocuments().size() > 0)) {
                FileOperation mv = new FileOperation();

                ArrayList<String> TempUrl = new ArrayList();
                ArrayList<Integer> DocumentIDList = new ArrayList();
                ArrayList<Integer> ReferralIDList = new ArrayList();

                for (int i = 0; i < referral.getSelectedOtherRequiredDocuments().size(); i++) {
                    Document doc = referral.getSelectedOtherRequiredDocuments().get(i);
                    TempUrl.add(doc.getDocumentUrl());
                    ReferralIDList.add(referralId);
                }

                for (int i = 0; i < referral.getSelectedRequiredDocuments().size(); i++) {
                    Document doc1 = referral.getSelectedRequiredDocuments().get(i);
                    TempUrl.add(doc1.getDocumentUrl());
                    ReferralIDList.add(referralId);
                }

                ArrayList<String> FinalUrlList = mv.movefile(TempUrl, ReferralIDList, 2);

                StringTokenizer tokenizer = new StringTokenizer(docIdList, ",");
                while (tokenizer.hasMoreTokens()) {
                    DocumentIDList.add(Integer.parseInt(tokenizer.nextToken()));
                }
                DocumentIDList.remove(DocumentIDList.size() - 1);

                for (int i = 0; i < FinalUrlList.size(); i++) {
                    PreparedStatement psForupdateDocumentURL = connection.prepareStatement("UPDATE Document SET DocumentUrl = ? WHERE DocumentID=?");
                    psForupdateDocumentURL.setString(1, FinalUrlList.get(i));
                    psForupdateDocumentURL.setInt(2, DocumentIDList.get(i));

                    psForupdateDocumentURL.executeUpdate();
                }

            }

        } catch (Exception e) {

            e.printStackTrace();

        } finally {
            if (connection != null) {
                connection.close();

            }
            return result;
        }

    }

    public ArrayList<User> getMyReferralRequests(int userid, String lasttimestamp) throws Exception {
        Connection connection = getConnection();
        ArrayList<User> getmyreferralrequestslist = new ArrayList<>();
        try {

            PreparedStatement psForGetReferralRequests = connection.prepareStatement("Select u.UserID,u.FirstName,u.LastName,r.ReferralID from " + dbName + ".ReferredTo r," + dbName + ".User u \n"
                    + "where r.SpecialistID = ? and \n"
                    + "r.ReferredToDate >= FROM_UNIXTIME(?) \n"
                    + "and IsReferralAccepted =0\n"
                    + "and r.ReferredBy = u.UserID ");

            psForGetReferralRequests.setInt(1, userid);
            psForGetReferralRequests.setString(2, lasttimestamp);

            ResultSet resultSetForGetReferralRequests = psForGetReferralRequests.executeQuery();
            while (resultSetForGetReferralRequests.next()) {
                User user = new User();
                user.setReferralID(resultSetForGetReferralRequests.getInt("ReferralID"));
                user.setUserID(resultSetForGetReferralRequests.getInt("UserID"));
                user.setFirstName(resultSetForGetReferralRequests.getString("FirstName"));
                user.setLastName(resultSetForGetReferralRequests.getString("LastName"));

                getmyreferralrequestslist.add(user);

            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return getmyreferralrequestslist;
    }

    public String responseToReferralRequest(int referralid, int userid, String responseflag) throws Exception {
        Connection connection = getConnection();
        int intResponseflag = Integer.parseInt(responseflag);
        String message = "Sorry Some Error.!";
        int result = 0;
        try {
            PreparedStatement psForResponseChat = connection.prepareStatement("");
            psForResponseChat.setInt(1, intResponseflag);
            psForResponseChat.setInt(2, referralid);
            psForResponseChat.setInt(3, userid);
            psForResponseChat.executeUpdate();

            if (responseflag.equals("1")) {
                PreparedStatement psForInsertClinicId = connection.prepareStatement("insert into " + dbName + ".Contactlist(ClinicID,ClinicIDTo) value \n"
                        + "((Select ClinicID from " + dbName + ".User where UserID=?),(Select ClinicID from " + dbName + ".User where UserID=?))");

                psForInsertClinicId.setInt(1, referralid);
                psForInsertClinicId.setInt(2, userid);
                psForInsertClinicId.executeUpdate();

            }
            message = "Updated chat status";

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return message;
    }

    public String responseToReferralRequest(int referralid, int userid) throws Exception {
        Connection connection = getConnection();

        String message = "Sorry Some Error.!";
        int result = 0;
        int resultFlag = 0;
        try {

            PreparedStatement psForResponseReferral = connection.prepareStatement("update " + dbName + ".Referral SET TreatedBy=?,TreatmentStatus=1 where \n"
                    + "ReferralID=? and TreatedBy IS NULL and TreatmentStatus=0;");
            psForResponseReferral.setInt(1, userid);
            psForResponseReferral.setInt(2, referralid);

            PreparedStatement psForSetReferralAcceptedFlag = connection.prepareStatement("update " + dbName + ".ReferredTo SET IsReferralAccepted=1 where \n"
                    + "ReferralID=? and SpecialistID = ?");
            psForSetReferralAcceptedFlag.setInt(1, referralid);
            psForSetReferralAcceptedFlag.setInt(2, userid);

            result = psForResponseReferral.executeUpdate();
            resultFlag = psForSetReferralAcceptedFlag.executeUpdate();
            if (result >= 1 && resultFlag >= 1) {
                message = "You Successfully Accept Referral Request..!";
            } else if (result == 0 && resultFlag == 0) {
                message = "Sorry This Referral Request Already Accepted By some other Specialist.!";

            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return message;
    }

    public int attachmentForChat(Chatlog chtlogobj, int userid) throws Exception {
        Connection connection = getConnection();
        int result = 0;
        try {

            ArrayList<Integer> userlistforgroup = new ArrayList<>();
            userlistforgroup = getGroupUsersAcceptLoginUser(chtlogobj.getChatgroupID(), userid);
            for (int temp = 0; temp < userlistforgroup.size(); temp++) {
                for (int i = 0; i < chtlogobj.getChatDocumentsUrl().size(); i++) {
                    PreparedStatement psForattachmentForChat = connection.prepareStatement("INSERT INTO " + dbName + ".Chatlog (Message,ChatgroupID,SenderID,ReceiverID,ChatTime,ReferralID,ChatType,FileName) VALUES (?,?,?,?,?,?,?,?)");

                    //get Filename from url
                    String[] values = chtlogobj.getChatDocumentsUrl().get(i).split("/");
                    String filename = values[values.length - 1];

                    // get extension of file from url
                    Matcher m = Pattern.compile(".*/.*?(\\..*)").matcher(chtlogobj.getChatDocumentsUrl().get(i));
                    String fileExtension = null;
                    if (m.matches()) {
                        fileExtension = m.group(1);
                    }

                    if (fileExtension != null) {
                        chtlogobj.setChatType(fileExtension);
                    } else {
                        chtlogobj.setChatType("file");
                    }

                    psForattachmentForChat.setString(1, chtlogobj.getChatDocumentsUrl().get(i));
                    psForattachmentForChat.setInt(2, chtlogobj.getChatgroupID());
                    psForattachmentForChat.setInt(3, chtlogobj.getSenderID());
                    psForattachmentForChat.setInt(4, userlistforgroup.get(temp));
                    psForattachmentForChat.setTimestamp(5, new java.sql.Timestamp(new java.util.Date().getTime()));
                    psForattachmentForChat.setInt(6, chtlogobj.getReferralID());
                    psForattachmentForChat.setString(7, chtlogobj.getChatType());
                    psForattachmentForChat.setString(8, filename);

                    result = psForattachmentForChat.executeUpdate();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return result;
    }

    public boolean checkDuplicateEmail(String email, int requestType) throws Exception {
        Connection connection = getConnection();
        boolean isEmailExist = false;
        try {
            PreparedStatement objPreparedStatement = null;
            switch (requestType) {
                case 1:
                    // for clinic
                    objPreparedStatement = connection.prepareStatement("SELECT ClinicID FROM " + dbName + ".Clinic where Email = ?");
                    break;
                case 2:
                case 3:
                    // for staff and doctors
                    objPreparedStatement = connection.prepareStatement("SELECT UserID FROM " + dbName + ".User where Email = ?");
                    break;
                case 4:
                    // for patinet
                    objPreparedStatement = connection.prepareStatement("SELECT PatientID FROM " + dbName + ".Patient where Email = ?");
                    break;
                default:
                    break;
            }
            objPreparedStatement.setString(1, email);
            ResultSet objResultSet = objPreparedStatement.executeQuery();
            while (objResultSet.next()) {
                if (objResultSet.getInt(1) > 0) {
                    isEmailExist = true;
                } else {
                    isEmailExist = false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return isEmailExist;
    }

    public boolean checkDuplicateMobileNumber(String mobileno, int requestType, int requestSubType) throws Exception {
        Connection connection = getConnection();
        boolean isMobileNoExist = false;
        try {
            PreparedStatement objPreparedStatement = null;
            //Checking For Clinic Only
            if (requestType == 1) {
                if (requestSubType == 1) {
                    //for clinic fax number
                    objPreparedStatement = connection.prepareStatement("SELECT ClinicID FROM " + dbName + ".Clinic where FaxNo = ?");
                    objPreparedStatement.setString(1, mobileno);
                    ResultSet objResultSet = objPreparedStatement.executeQuery();
                    while (objResultSet.next()) {
                        if (objResultSet.getInt(1) > 0) {

                            isMobileNoExist = true;
                        }
//    else {
//                            isMobileNoExist = false;
//                        }
                    }
                } else if (requestSubType == 2) {
                    //for Clinic Phone Number
                    objPreparedStatement = connection.prepareStatement("SELECT ClinicID FROM " + dbName + ".Clinic where PhoneNo = ?");
                    objPreparedStatement.setString(1, mobileno);
                    ResultSet objResultSet = objPreparedStatement.executeQuery();
                    while (objResultSet.next()) {
                        if (objResultSet.getInt(1) > 0) {

                            isMobileNoExist = true;
                        }
//                            else {
//                            isMobileNoExist = false;
//                        }
                    }
                }

            } //for Doctor/staff only
            else if (requestType == 2) {
                if (requestSubType == 1) {
                    //for staff/doctor Desk number
                    objPreparedStatement = connection.prepareStatement("SELECT UserID FROM " + dbName + ".User where DeskPhone = ?");
                    objPreparedStatement.setString(1, mobileno);
                    ResultSet objResultSet = objPreparedStatement.executeQuery();
                    while (objResultSet.next()) {
                        if (objResultSet.getInt(1) > 0) {

                            isMobileNoExist = true;
                        }
//                            else {
////                            isMobileNoExist = false;
////                        }
                    }
                } else if (requestSubType == 2) {
                    //for staff/doctor Cell number
                    objPreparedStatement = connection.prepareStatement("SELECT UserID FROM " + dbName + ".User where CellPhone = ?");
                    objPreparedStatement.setString(1, mobileno);
                    ResultSet objResultSet = objPreparedStatement.executeQuery();
                    while (objResultSet.next()) {
                        if (objResultSet.getInt(1) > 0) {

                            isMobileNoExist = true;
                        }
//                        else {
//                            isMobileNoExist = false;
//                        }
                    }
                }

            }//only for patient 
            else if (requestSubType == 3) {

                //for patient Phone  number
                objPreparedStatement = connection.prepareStatement("SELECT PatientID FROM " + dbName + ".Patient where PhoneNo = ?");
                objPreparedStatement.setString(1, mobileno);
                ResultSet objResultSet = objPreparedStatement.executeQuery();
                while (objResultSet.next()) {
                    if (objResultSet.getInt(1) > 0) {

                        isMobileNoExist = true;
                    }
//                    else {
//                        isMobileNoExist = false;
//                    }
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return isMobileNoExist;
    }

    @SuppressWarnings("unchecked")
    public String addPatient(Patient patient, int userid) throws Exception {
        Connection connection = getConnection();
        Gson gson = new Gson();
        Address objAddress;
        java.util.Date todayDate = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(todayDate.getTime());
        int patientid = 0, count = 0, isInsuranceAdded = 99;
        String ResultMsg = "Error";
        try {
            // insert address
            objAddress = gson.fromJson(gson.toJson(patient), Address.class);
            objAddress.setAddressID(insertAddress(objAddress));

            // insert patient
            PreparedStatement psForAddPatient = connection.prepareStatement("INSERT into " + dbName + ".Patient(AddressID,FirstName,Lastname,PhoneNo,Email,DOB,ClinicID) values (?,?,?,?,?,?,?)");
            psForAddPatient.setInt(1, objAddress.getAddressID());
            psForAddPatient.setString(2, patient.getFirstName());
            psForAddPatient.setString(3, patient.getLastName());
            psForAddPatient.setString(4, patient.getPhoneNo());
            psForAddPatient.setString(5, patient.getEmail());
            psForAddPatient.setTimestamp(6, DateConversion.stringToDateConversion(patient.getDOB()));
            psForAddPatient.setInt(7, getClinicAsPerUser(userid));

            psForAddPatient.executeUpdate();
            PreparedStatement psForGetPatientId = connection.prepareStatement("SELECT PatientID FROM " + dbName + ".Patient ORDER BY AddressID DESC LIMIT 1");
            ResultSet resultSetForGetPatientId = psForGetPatientId.executeQuery();
            while (resultSetForGetPatientId.next()) {
                patientid = resultSetForGetPatientId.getInt(1);
                patient.setPatientID(patientid);
                ResultMsg = "Patient Added";
            }

            for (int i = 0; i < patient.getInsuranceID().size(); i++) {
                isInsuranceAdded = addinsuranceofpatient(patient.getInsuranceID().get(i), patientid);
            }

            if (isInsuranceAdded == 0) {
                ResultMsg = "Insurace Add Unsuccessfull";
            }

            if (patientid > 0) {

                ArrayList<String> tempUrlList = new ArrayList();
                ArrayList<Integer> patientidList = new ArrayList();
                ArrayList<String> FinalUrlList = new ArrayList();
                FileOperation mv = new FileOperation();

                if (patient.getIDProofPicUrl() != null) {
                    tempUrlList.add(patient.getIDProofPicUrl());
                    patientidList.add(patientid);

                    // Move Image to particular folder and update db
                    FinalUrlList = mv.movefile(tempUrlList, patientidList, 3);
                    updateIDProofPicUrl(FinalUrlList.get(0), patientid);
                    count++;
                }

                tempUrlList.clear();
                FinalUrlList.clear();
                patientidList.clear();

                if (patient.getInsuranceIDbackpicUrl() != null) {
                    tempUrlList.add(patient.getInsuranceIDbackpicUrl());
                    patientidList.add(patientid);

                    // Move Image to particular folder and update db
                    FinalUrlList = mv.movefile(tempUrlList, patientidList, 3);
                    updateInsurnceIDbackpicUrl(FinalUrlList.get(0), patientid);
                    count++;
                }

                tempUrlList.clear();
                FinalUrlList.clear();
                patientidList.clear();

                if (patient.getInsuranceIDfrontpicUrl() != null) {
                    tempUrlList.add(patient.getInsuranceIDfrontpicUrl());
                    patientidList.add(patientid);

                    // Move Image to particular folder and update db
                    FinalUrlList = mv.movefile(tempUrlList, patientidList, 3);
                    updateInsurnceIDfrontpicUrl(FinalUrlList.get(0), patientid);
                    count++;
                }
            }
            if (patientid < 0) {
                int deleteAddress = deleteAddress(objAddress.getAddressID());
            } else if (patientid > 0
                    && (patient.getIDProofPicUrl() != null || patient.getInsuranceIDfrontpicUrl() != null || patient.getInsuranceIDbackpicUrl() != null)
                    && count == 0) {
                ResultMsg = "Upload Unsccessfull";
            }

        } catch (Exception e) {
            e.printStackTrace();

            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return ResultMsg;
    }

    public int addinsuranceofpatient(int patientinsuranceid, int patietid) throws Exception {
        Connection connection = getConnection();
        int result = 0;
        try {

            PreparedStatement psForAddInsuranceOfPatient = connection.prepareStatement("INSERT into " + dbName + ".InsurancePatient(InsuranceID,PatientID) values (?,?)");
            psForAddInsuranceOfPatient.setInt(1, patientinsuranceid);
            psForAddInsuranceOfPatient.setInt(2, patietid);
            result = psForAddInsuranceOfPatient.executeUpdate();
        } catch (Exception e) {

            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    public String editPatient(Patient patient) throws Exception {
        Connection connection = getConnection();
        Gson gson = new Gson();
        String Resultmessage = "Some Error";
        int isInsuranceadded = 0, isPatientEdited = 0, isImageUpload, isImageDel;
        try {
            Address objAddress = gson.fromJson(gson.toJson(patient), Address.class);
            updateAddress(objAddress);

            PreparedStatement psForEditPatient = connection.prepareStatement("UPDATE " + dbName + ".Patient SET"
                    + " FirstName = ?,Lastname=?,PhoneNo=?,Email=?,DOB=?"
                    + " WHERE PatientID = ?");

            psForEditPatient.setString(1, patient.getFirstName());
            psForEditPatient.setString(2, patient.getLastName());
            psForEditPatient.setString(3, patient.getPhoneNo());
            psForEditPatient.setString(4, patient.getEmail());
            psForEditPatient.setTimestamp(5, DateConversion.stringToDateConversion(patient.getDOB()));
            psForEditPatient.setInt(6, patient.getPatientID());
            isPatientEdited = psForEditPatient.executeUpdate();
            if (isPatientEdited > 0) {
                Resultmessage = "Patient Edit SuccessFull";
            }

            //Edit Insurance
            PreparedStatement psForDeleteOldInsurance = connection.prepareStatement("Delete From " + dbName + ".InsurancePatient where PatientID=?");
            psForDeleteOldInsurance.setInt(1, patient.getPatientID());
            psForDeleteOldInsurance.executeUpdate();

            for (int i = 0; i < patient.getInsuranceID().size(); i++) {
                isInsuranceadded = addinsuranceofpatient(patient.getInsuranceID().get(i), patient.getPatientID());
            }

            if (isInsuranceadded == 0) {
                Resultmessage = "Insurance Edit Unsuccessfull";
            }

            String IDProofPicUrl = null, InsurnceIDfrontpicUrl = null, InsurnceIDbackpicUrl = null;
            String path = this.getClass().getClassLoader().getResource("").getPath();
            String fullPath = URLDecoder.decode(path, "UTF-8");
            String pathArr[] = fullPath.split("/WEB-INF/classes/");
            fullPath = pathArr[0];

            PreparedStatement psForGetDocURL = connection.prepareStatement("SELECT  IDProofPicUrl,InsuranceIDfrontpicUrl, InsuranceIDbackpicUrl FROM " + dbName + ".Patient where PatientID=? ");
            psForGetDocURL.setInt(1, patient.getPatientID());
            ResultSet rsForGetDocURL = psForGetDocURL.executeQuery();
            while (rsForGetDocURL.next()) {
                IDProofPicUrl = fullPath + "/" + rsForGetDocURL.getString(1);
                InsurnceIDfrontpicUrl = fullPath + "/" + rsForGetDocURL.getString(2);
                InsurnceIDbackpicUrl = fullPath + "/" + rsForGetDocURL.getString(3);
            }

            ArrayList<String> tempUrlList = new ArrayList();
            ArrayList<Integer> patientidList = new ArrayList();
            ArrayList<String> FinalUrlList = new ArrayList();
            FileOperation fileOpr = new FileOperation();

            Matcher m;
            String pattern = "(([^0-9]|^)" + Integer.toString(patient.getPatientID()) + "([^0-9]|$))";
            // Create a Pattern object
            Pattern r = Pattern.compile(pattern);

            if (patient.getIDProofPicUrl() != null && !patient.getIDProofPicUrl().equals("del")) {

                // Now create matcher object.
                m = r.matcher(patient.getIDProofPicUrl());
                if (!m.find()) {

                    tempUrlList.add(patient.getIDProofPicUrl());
                    patientidList.add(patient.getPatientID());
                    // Move Image to particular folder and update db
                    FinalUrlList = fileOpr.movefile(tempUrlList, patientidList, 3);
                    isImageUpload = updateIDProofPicUrl(FinalUrlList.get(0), patient.getPatientID());
                    if (isImageUpload < 0) {
                        Resultmessage = "Image Upload Unsuccessfull";
                    }

                }

            } else if (patient.getIDProofPicUrl().equals("del")) {
                if (fileOpr.deletefile(IDProofPicUrl)) {
                    isImageDel = updateIDProofPicUrl("null", patient.getPatientID());
                    if (isImageDel < 0) {
                        {
                            Resultmessage = "Image Delete Unsuccessfull DataBase Error";
                        }
                    }
                } else {
                    Resultmessage = "Image Delete Unsuccessfull";
                }
            }

            tempUrlList.clear();
            FinalUrlList.clear();
            patientidList.clear();

            if (patient.getInsuranceIDbackpicUrl() != null && !patient.getInsuranceIDbackpicUrl().equals("del")) {

                // Now create matcher object.
                m = r.matcher(patient.getInsuranceIDbackpicUrl());
                if (!m.find()) {

                    tempUrlList.add(patient.getInsuranceIDbackpicUrl());
                    patientidList.add(patient.getPatientID());

                    // Move Image to particular folder and update db
                    FinalUrlList = fileOpr.movefile(tempUrlList, patientidList, 3);
                    isImageUpload = updateInsurnceIDbackpicUrl(FinalUrlList.get(0), patient.getPatientID());
                    if (isImageUpload < 0) {
                        Resultmessage = "Image Upload Unsuccessfull";
                    }
                }

            } else if (patient.getInsuranceIDbackpicUrl().equals("del")) {
                if (fileOpr.deletefile(InsurnceIDbackpicUrl)) {
                    isImageDel = updateInsurnceIDbackpicUrl("null", patient.getPatientID());

                    if (isImageDel < 0) {
                        Resultmessage = "Image Delete Unsuccessfull DataBase Error";
                    }

                } else {
                    Resultmessage = "Image Delete Unsuccessfull";
                }
            }

            tempUrlList.clear();
            FinalUrlList.clear();
            patientidList.clear();

            if (patient.getInsuranceIDfrontpicUrl() != null && !patient.getInsuranceIDfrontpicUrl().equals("del")) {

                // Now create matcher object.
                m = r.matcher(patient.getInsuranceIDfrontpicUrl());
                if (!m.find()) {

                    tempUrlList.add(patient.getInsuranceIDfrontpicUrl());
                    patientidList.add(patient.getPatientID());

                    // Move Image to particular folder and update db
                    FinalUrlList = fileOpr.movefile(tempUrlList, patientidList, 3);
                    isImageUpload = updateInsurnceIDfrontpicUrl(FinalUrlList.get(0), patient.getPatientID());
                    if (isImageUpload < 0) {
                        Resultmessage = "Image Upload Unscessfull";
                    }
                }
            } else if (patient.getInsuranceIDfrontpicUrl().equals("del")) {
                if (fileOpr.deletefile(InsurnceIDfrontpicUrl)) {
                    isImageDel = updateInsurnceIDfrontpicUrl("null", patient.getPatientID());
                    if (isImageDel < 0) {
                        Resultmessage = "Image Delete Unsuccessfull DataBase Error";
                    }

                } else {
                    Resultmessage = "Image Delete Unsuccessfull";
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }

        }
        return Resultmessage;
    }

    @SuppressWarnings("empty-statement")
    public int updateIDProofPicUrl(String IDProofPicUrl, int pid) throws Exception {
        Connection connection = getConnection();
        int result = 0;
        try {
            PreparedStatement psForupdateDocumentURL = connection.prepareStatement("UPDATE  " + dbName + ".Patient SET IDProofPicUrl = ? WHERE PatientID = ?");
            psForupdateDocumentURL.setString(1, IDProofPicUrl);
            psForupdateDocumentURL.setInt(2, pid);
            result = psForupdateDocumentURL.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return result;
    }

    public int updateInsurnceIDfrontpicUrl(String IDfrontpicUrl, int pid) throws Exception {
        Connection connection = getConnection();
        int result = 0;
        try {
            PreparedStatement psForupdateDocumentURL = connection.prepareStatement("UPDATE  " + dbName + ".Patient SET InsuranceIDfrontpicUrl = ?  WHERE PatientID = ?");
            psForupdateDocumentURL.setString(1, IDfrontpicUrl);
            psForupdateDocumentURL.setInt(2, pid);
            result = psForupdateDocumentURL.executeUpdate();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return result;
    }

    public int updateInsurnceIDbackpicUrl(String IDbackpicUrl, int pid) throws Exception {
        Connection connection = getConnection();
        int result = 0;
        try {
            PreparedStatement psForupdateDocumentURL = connection.prepareStatement("UPDATE  " + dbName + ".Patient SET InsuranceIDbackpicUrl = ? WHERE PatientID = ?");
            psForupdateDocumentURL.setString(1, IDbackpicUrl);
            psForupdateDocumentURL.setInt(2, pid);
            result = psForupdateDocumentURL.executeUpdate();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return result;
    }

    public HashMap<String, Object> getProfileDtl(int docid) throws Exception {
        Connection connection = getConnection();
        int result = 0, docDtlItr = 0;
        ArrayList<Integer> UserIDList = new ArrayList();
        ArrayList<User> StaffDetails = new ArrayList();
        ArrayList<User> DoctorDetails = new ArrayList();
        ArrayList<User> TempDoctorDetails = new ArrayList();
        HashMap<String, Object> SignupData = new HashMap();
        try {

            //get common Details of staff and doctor
            PreparedStatement psForProfileDtl = connection.prepareStatement("SELECT  refqueUser.FirstName, refqueUser.LastName,refqueUser.DeskPhone,\n"
                    + "    refqueUser.CellPhone,refqueUser.Email,refqueAddr.HouseNo,refqueAddr.ZipCode,\n"
                    + "    refqueAddr.Street,refqueState.StateName,refqueCity.CityName,\n"
                    + "    refqueUser.FaxNo, refqueUser.IsDocOnlyContact,refqueUser.IsNotificationON,\n"
                    + "    refqueUser.UserID ,refqueUser.SpecializationID,refqueSpecializaion.SpecializationName,\n"
                    + "    refqueUser.RoleID,refqueRole.RoleName FROM  " + dbName + ".User as refqueUser\n"
                    + "        RIGHT outer join " + dbName + ".Address as refqueAddr ON refqueUser.AddressID = refqueAddr.AddressID\n"
                    + "        RIGHT outer join " + dbName + ".City     as refqueCity ON refqueAddr.CityID =refqueCity.CityID\n"
                    + "        RIGHT outer join " + dbName + ".State    as refqueState ON refqueState.stateID = refqueCity.StateID\n"
                    + "        RIGHT outer join " + dbName + ".Role  as   refqueRole   ON refqueUser.RoleID=refqueRole.RoleID\n"
                    + "	 LEFT outer join  " + dbName + ".Specialization as refqueSpecializaion ON refqueUser.SpecializationID=refqueSpecializaion.SpecializationID\n"
                    + "where refqueUser.UserID in (SELECT UserID FROM " + dbName + ".User where ClinicID in (SELECT ClinicID FROM " + dbName + ".User where UserID = ?))");
            psForProfileDtl.setInt(1, docid);
            ResultSet rsForProfileDtl = psForProfileDtl.executeQuery();

            while (rsForProfileDtl.next()) {
                User user = new User();

                user.setUserID(rsForProfileDtl.getInt("refqueUser.UserID"));
                user.setFirstName(rsForProfileDtl.getString("refqueUser.FirstName"));
                user.setLastName(rsForProfileDtl.getString("refqueUser.LastName"));
                user.setDeskPhone(rsForProfileDtl.getString("refqueUser.DeskPhone"));
                user.setCellPhone(rsForProfileDtl.getString("refqueUser.CellPhone"));
                user.setEmail(rsForProfileDtl.getString("refqueUser.Email"));
                user.setRoleID(rsForProfileDtl.getInt("refqueUser.RoleID"));

                if (rsForProfileDtl.getInt("refqueUser.RoleID") < 4) {
                    user.setRoleName(rsForProfileDtl.getString("refqueRole.RoleName"));
                    StaffDetails.add(user);
                }

                if (rsForProfileDtl.getInt("refqueUser.SpecializationID") > 0) {

                    user.setHouseNo(rsForProfileDtl.getString("refqueAddr.HouseNo"));
                    user.setStreet(rsForProfileDtl.getString("refqueAddr.Street"));
                    user.setStateName(rsForProfileDtl.getString("refqueState.StateName"));
                    user.setCityName(rsForProfileDtl.getString("refqueCity.CityName"));
                    user.setZipCode(rsForProfileDtl.getString("refqueAddr.ZipCode"));
                    user.setFaxNo(rsForProfileDtl.getString("refqueUser.FaxNo"));
                    user.setSpecializationID(rsForProfileDtl.getInt("refqueUser.SpecializationID"));
                    user.setSpecializationName(rsForProfileDtl.getString("refqueSpecializaion.SpecializationName"));
                    TempDoctorDetails.add(user);

                    UserIDList.add(rsForProfileDtl.getInt("refqueUser.UserID"));
                }

            }

            SignupData.put("StaffDetails", StaffDetails);

            //get Clinic Details
            int cinicid = getClinicAsPerUser(docid);
            PreparedStatement psForClinicDtl = connection.prepareStatement("SELECT ClinicName,FaxNo,PhoneNo,StaffCount,DoctorCount,Email,"
                    + "refqueAddress.HouseNo,refqueAddress.Street,refqueAddress.ZipCode,refqueState.StateName,refqueCity.CityName FROM " + dbName + ".Clinic as refqueClinic\n"
                    + "RIGHT outer join  " + dbName + ".Address as refqueAddress     ON refqueClinic.AdressID=refqueAddress.AddressID\n"
                    + "RIGHT outer join   " + dbName + ".City as refqueCity ON refqueAddress.CityID =refqueCity.CityID RIGHT outer join\n"
                    + "" + dbName + ".State as refqueState ON refqueState.stateID = refqueCity.StateID where  ClinicID=? ");
            psForClinicDtl.setInt(1, cinicid);
            ResultSet rstoGetClinicDtl = psForClinicDtl.executeQuery();
            Clinic clinic = new Clinic();

            while (rstoGetClinicDtl.next()) {
                clinic.setClinicName(rstoGetClinicDtl.getString("ClinicName"));
                clinic.setPhoneNo(rstoGetClinicDtl.getString("PhoneNo"));
                clinic.setHouseNo(rstoGetClinicDtl.getString("refqueAddress.HouseNo"));
                clinic.setStreet(rstoGetClinicDtl.getString("refqueAddress.Street"));
                clinic.setFaxNo(rstoGetClinicDtl.getString("FaxNo"));
                clinic.setEmail(rstoGetClinicDtl.getString("Email"));
                clinic.setStateName(rstoGetClinicDtl.getString("refqueState.StateName"));
                clinic.setCityName(rstoGetClinicDtl.getString("refqueCity.CityName"));
                clinic.setZipCode(rstoGetClinicDtl.getString("refqueAddress.ZipCode"));
                clinic.setStaffCount(rstoGetClinicDtl.getInt("StaffCount"));
                clinic.setDoctorCount(rstoGetClinicDtl.getInt("DoctorCount"));

            }

            SignupData.put("ClinicDetails", clinic);

            //get all detail of Doctor  
            for (int i = 0; i < UserIDList.size(); i++) {

                PreparedStatement psForUserDtl = connection.prepareStatement("SELECT refqueUser.UserID as uid , refqueInsurance.InsuranceType as Insurancename,GROUP_CONCAT(DISTINCT refqueCred.CredentialName) as CredName,GROUP_CONCAT( DISTINCT refqueReqReport.RequirementName) as Reportlist,\n"
                        + "group_concat( distinct case when Monday in (true) then 'Monday,' else' ' end,"
                        + "case when Tuesday in (true) then 'Tuesday,'  else '' end,"
                        + "case when Wednesday in (true) then 'Wednesday,' else ''end,"
                        + "case when Thursday in (true) then 'Thursday,' else ''end ,"
                        + "case when Friday in (true) then 'Friday,' else ''end,"
                        + "			case when Saturday in (true) then 'Saturday,' else ''end ,"
                        + "case when Sunday in (true) then 'Sunday,' else ''end) as days"
                        + "                         FROM  " + dbName + ".User as refqueUser  join  " + dbName + ".InsuranceDoctor as refqueInsDoc       ON refqueUser.UserID=refqueInsDoc.DoctorID "
                        + "                         join  " + dbName + ".Insurance as refqueInsurance         ON refqueInsDoc.InsuranceID=refqueInsurance.InsuranceID "
                        + "                         join  " + dbName + ".DoctorCredential as refquedocCred    ON refqueUser.UserID=refquedocCred.DoctorID "
                        + "                         join  " + dbName + ".Credential as refqueCred 		ON refqueCred.CredentialID=refquedocCred.CredentialID "
                        + "                         left outer join   " + dbName + ".RequiredReport as refqueReqReport "
                        + "                         left outer join  " + dbName + ".SpecialistReport as refqueSpReport  ON refqueReqReport.RequirementID=refqueSpReport.RequiredReportID "
                        + "                         left outer join " + dbName + ".DoctorSchedule as refqueSched ON refqueUser.UserID=refqueSched.DoctorID	  "
                        + "                        where   UserID=? ");

                psForUserDtl.setInt(1, UserIDList.get(i));
                ResultSet rsForUserDtl = psForUserDtl.executeQuery();

                while (rsForUserDtl.next()) {
                    User doctor = new User();
                    //UserIDList.get(i)
                    for (docDtlItr = 0; docDtlItr < TempDoctorDetails.size(); docDtlItr++) {
                        if (TempDoctorDetails.get(docDtlItr).getUserID() == UserIDList.get(i)) {
                            doctor = TempDoctorDetails.get(docDtlItr);
                            break;
                        }
                    }

                    TempDoctorDetails.remove(docDtlItr);

                    doctor.setInsuranceNameList(Arrays.asList(rsForUserDtl.getString("Insurancename").split(",")));
                    doctor.setCredentialNameList(Arrays.asList(rsForUserDtl.getString("CredName").split(",")));

                    // Check if he is Specialist
                    if (doctor.getRoleID() == 4) {

                        doctor.setDays(new ArrayList(Arrays.asList(rsForUserDtl.getString("days").split(","))));
                        doctor.setSpecialistReportsNameList(Arrays.asList(rsForUserDtl.getString("Reportlist").split(",")));

                    }

                    DoctorDetails.add(doctor);
                }

            }
            SignupData.put("DoctorDetails", DoctorDetails);
            System.out.print("size" + SignupData.size());
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            if (connection != null) {
                connection.close();
            }

        }
        return SignupData;
    }

    public ArrayList<Integer> getGroupUsersAcceptLoginUser(int groupid, int userid) throws Exception {
        Connection connection = getConnection();

        PreparedStatement psForgetgroupusers = connection.prepareStatement("SELECT UserID FROM " + dbName + ".UserChatGroup where GroupID=? and UserID!=?");
        psForgetgroupusers.setInt(1, groupid);
        psForgetgroupusers.setInt(2, userid);
        ResultSet resultSetForGetGroupUsers = psForgetgroupusers.executeQuery();
        ArrayList<Integer> userslistforgroup = new ArrayList<>();
        while (resultSetForGetGroupUsers.next()) {
            userslistforgroup.add(resultSetForGetGroupUsers.getInt("UserID"));

        }
        return userslistforgroup;
    }

}
