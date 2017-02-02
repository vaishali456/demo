package com.medq.servlets;

import com.google.gson.Gson;
import com.medq.services.Service;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "getPatientAsPerClinicServlet", urlPatterns = {"/getpatientlistasperclinic"})
public class GetPatientListServlet extends CoreJSONServlet {

    @Override
    protected Object execServiceJSON(Service api, HttpServletRequest request, HttpSession session, HttpServletResponse response) throws Exception {

        int patientstatus = 0;

        if (request.getParameter("patientstatus") != null) {

            patientstatus = Integer.parseInt(request.getParameter("patientstatus"));
        }

        Gson gson = new Gson();
        return gson.toJson(api.getPatientListAsPerClinic(patientstatus));
    }

}
