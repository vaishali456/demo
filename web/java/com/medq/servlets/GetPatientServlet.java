package com.medq.servlets;

import com.google.gson.Gson;
import com.medq.services.Service;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "GetPatientServlet", urlPatterns = {"/getpatientdetails"})
public class GetPatientServlet extends CoreJSONServlet {

    @Override
    protected Object execServiceJSON(Service api, HttpServletRequest request, HttpSession session, HttpServletResponse response) throws Exception {
        int patientid = 0;
        if (request.getParameter("patientid") != null) {

            patientid = Integer.parseInt(request.getParameter("patientid"));
        }
        
        
        Gson gson = new Gson();
        return gson.toJson(api.getPatientDetails(patientid));
    }

}
