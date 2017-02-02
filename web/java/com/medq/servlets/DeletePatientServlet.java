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
@WebServlet(name = "DeletePatientServlet", urlPatterns = {"/deletepatient"})
public class DeletePatientServlet extends CoreJSONServlet {

    @Override
    protected Object execServiceJSON(Service api, HttpServletRequest request, HttpSession session, HttpServletResponse response) throws Exception {
        int patientid = 0;
        int patientstatus = 0;

        if (request.getParameter("patientid") != null) {

            patientid = Integer.parseInt(request.getParameter("patientid"));
        }
        if (request.getParameter("patientstatus") != null) {

            patientstatus = Integer.parseInt(request.getParameter("patientstatus"));
        }
        //Here patientstatus=0 means activ  that patient.

        Gson gson = new Gson();
        return gson.toJson(api.deletePatient(patientid, patientstatus));
    }

}
