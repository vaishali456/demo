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
@WebServlet(name = "CheckDuplicateMobileNumberServlet", urlPatterns = {"/checkduplicatemobilenumber"})
public class CheckDuplicateMobileNumberServlet extends CoreJSONServlet {

    @Override
    protected Object execServiceJSON(Service api, HttpServletRequest request, HttpSession session, HttpServletResponse response) throws Exception {

        String mobileno = "";
        if (request.getParameter("mobileno") != null) {
            mobileno = request.getParameter("mobileno");
        }
        int requestType = 0;
        //if requesttype=1 then its for Clinic,requestype=2 then its for staff/doctor

        if (request.getParameter("requesttype") != null) {
            requestType = Integer.parseInt(request.getParameter("requesttype"));
        }
        int requestsubtype = 0;

        if (request.getParameter("requestsubtype") != null) {
            requestsubtype = Integer.parseInt(request.getParameter("requestsubtype"));
        }
        Gson gson = new Gson();
        return gson.toJson(api.checkDuplicateMobileNumber(mobileno, requestType, requestsubtype));
    }
}
