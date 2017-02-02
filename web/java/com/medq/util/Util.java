package com.medq.util;

import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;

/**
 */
public class Util {

    public enum Errors {
        errorMessage {
            public String description() {
                return "errorMessage";
            }
        },
    }

    public enum Response {
        responseData,
        responseCount,
        responseCode;
    }

    static HashMap responseHashMap;

    public static HashMap getInitialHashMap() {
        return new HashMap<>();
    }

    @SuppressWarnings("unchecked")
    public static HashMap setObjectResponse(Object object) {
        responseHashMap = Util.getInitialHashMap();
        if (object != null) {
            responseHashMap.put(Response.responseData, object);
            responseHashMap.put(Response.responseCode, 200);
        } else {
            HashMap hashMapErrorMessage = new HashMap();
            hashMapErrorMessage.put(Errors.errorMessage, "No result found for given input data in DB. Please check your input data");
            responseHashMap.put(Response.responseData, hashMapErrorMessage);
            responseHashMap.put(Response.responseCode, 400);
        }
        return responseHashMap;
    }

    @SuppressWarnings("unchecked")
    public static HashMap setArrayResponse(Object object) {
        responseHashMap = Util.getInitialHashMap();
        ArrayList<Object> arrayList = (ArrayList) object;
        if (arrayList.size() > 0) {
            responseHashMap.put(Response.responseData, arrayList);
            responseHashMap.put(Response.responseCode, 200);
        } else {
            HashMap hashMapErrorMessage = new HashMap();
            hashMapErrorMessage.put(Errors.errorMessage, "No result found for given input data in DB. Please check your input data");
            responseHashMap.put(Response.responseData, hashMapErrorMessage);
            responseHashMap.put(Response.responseCode, 400);
        }
        return responseHashMap;
    }

    // Return an empty array with zero elements rather then exception
    @SuppressWarnings("unchecked")
    public static HashMap setArrayResponseEmptyArray(Object object) {
        responseHashMap = Util.getInitialHashMap();
        ArrayList<Object> arrayList = (ArrayList) object;
        if (arrayList != null) {
            responseHashMap.put(Response.responseData, arrayList);
            responseHashMap.put(Response.responseCode, 200);
        }
        return responseHashMap;
    }

    @SuppressWarnings("unchecked")
    public static HashMap setErrorResponse(Exception exception) {
        HashMap hashMapErrorMessage = new HashMap();
        hashMapErrorMessage.put("errorMessage", exception.getMessage() + "--------  error  ");
        responseHashMap = Util.getInitialHashMap();
        responseHashMap.put(Response.responseData, hashMapErrorMessage);
        responseHashMap.put(Response.responseCode, 400);
        return responseHashMap;
    }

    public static HashMap setInvalidSession() {
        HashMap hashMapErrorMessage = new HashMap();
        hashMapErrorMessage.put("errorMessage", "Session not found");
        responseHashMap = Util.getInitialHashMap();
        responseHashMap.put(Response.responseData, hashMapErrorMessage);
        responseHashMap.put(Response.responseCode, 454);
        return responseHashMap;
    }

    public static HashMap setInvalidUser() {
        HashMap hashMapErrorMessage = new HashMap();
        hashMapErrorMessage.put("errorMessage", "Invalid username or password");
        responseHashMap = Util.getInitialHashMap();
        responseHashMap.put(Response.responseData, hashMapErrorMessage);
        responseHashMap.put(Response.responseCode, 401);
        return responseHashMap;
    }

    @SuppressWarnings("unchecked")
    public static HashMap setResponseForInvalidInput() {
        HashMap hashMapErrorMessage = new HashMap();
        hashMapErrorMessage.put("errorMessage", " Invalid input  ");
        responseHashMap = Util.getInitialHashMap();
        responseHashMap.put(Response.responseData, hashMapErrorMessage);
        responseHashMap.put(Response.responseCode, 400);
        return responseHashMap;
    }

    public static HashMap setDataBaseDownError() {
        responseHashMap = Util.getInitialHashMap();
        responseHashMap.put(Response.responseCode, 500);
        HashMap hashMapErrorMessage = new HashMap();
        hashMapErrorMessage.put(Errors.errorMessage, "Currently DB is down");
        responseHashMap.put(Response.responseData, hashMapErrorMessage);
        return responseHashMap;
    }
    public static String getBaseURLFromRequest(HttpServletRequest request) {
        String url = request.getRequestURL().toString();
        String baseURL = url.substring(0, url.length() - request.getRequestURI().length()) + request.getContextPath() + "/";
        return baseURL;
    }

//    public static HashMap setRespsoseMessage() {
//        responseHashMap = Util.getInitialHashMap();
//        responseHashMap.put(Response.responseCode, 101);
//        HashMap hashMapErrorMessage = new HashMap();
//        hashMapErrorMessage.put(Errors.errorMessage, "There nothing to Update..!");
//        responseHashMap.put(Response.responseData, hashMapErrorMessage);
//        return responseHashMap;
//    }

}
