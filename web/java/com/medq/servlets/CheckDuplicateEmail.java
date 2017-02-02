/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.medq.servlets;

import com.google.gson.Gson;
import com.medq.services.Service;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author eshine
 */
@WebServlet(name = "CheckDuplicateEmailServlet", urlPatterns = {"/checkduplicateemail"})
public class CheckDuplicateEmail extends CoreJSONServlet {

    @Override
    protected Object execServiceJSON(Service api, HttpServletRequest request, HttpSession session, HttpServletResponse response) throws Exception {

        String strEmail = "";
        if (request.getParameter("email") != null) {
            strEmail = request.getParameter("email");
        }
        int requestType = 0;
        //if requesttype=1 then its for Clinic,requestype=2 then its for staff/login

        if (request.getParameter("requesttype") != null) {
            requestType = Integer.parseInt(request.getParameter("requesttype"));
        }
        Gson gson = new Gson();
        return gson.toJson(api.checkDuplicateEmail(strEmail, requestType));
    }

}
