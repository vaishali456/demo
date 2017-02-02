package com.medq.servlets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.medq.services.Service;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpSession;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends CoreJSONServlet {

    @Override
    protected Object execServiceJSON(Service api, HttpServletRequest request, HttpSession session, HttpServletResponse response) throws Exception {
        String strEmailID = "";
        
        if (request.getParameter("emailid") != null) {
            strEmailID = request.getParameter("emailid");
        }
        String strPassword = "";
        if (request.getParameter("password") != null) {
            strPassword = request.getParameter("password");
        }
        Gson gson = new Gson();
        return gson.toJson(api.login(strEmailID, strPassword));
    }
    

}
