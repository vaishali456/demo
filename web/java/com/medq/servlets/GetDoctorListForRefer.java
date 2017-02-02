/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.medq.servlets;

import com.google.gson.Gson;
import com.medq.services.Service;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author eshine-104
 */
@WebServlet(name = "GetDoctorListForRefer", urlPatterns = {"/getdoctorlistforrefer"})
public class GetDoctorListForRefer extends CoreJSONServlet {

    @Override
    protected Object execServiceJSON(Service api, HttpServletRequest request, HttpSession session, HttpServletResponse response) throws Exception {

        int clinicid = 0;
        if (request.getParameter("clinicid") != null) {

            clinicid = Integer.parseInt(request.getParameter("clinicid"));
        }
        int specializationid = 0;
        if (request.getParameter("specializationid") != null) {

            specializationid = Integer.parseInt(request.getParameter("specializationid"));
        }
        int patientid = 0;
        if (request.getParameter("patientid") != null) {

            patientid = Integer.parseInt(request.getParameter("patientid"));
        }
        Gson gson = new Gson();
        return gson.toJson(api.getDoctorListForRefer(specializationid, clinicid, patientid));
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
