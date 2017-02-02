package com.medq.servlets;

import com.google.gson.Gson;
import com.medq.dto.Patient;
import com.medq.services.Service;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "AddPatientServlet", urlPatterns = {"/addpatient"})
public class AddPatientServlet extends CoreJSONServlet {

    @Override
    protected Object execServiceJSON(Service api, HttpServletRequest request,
            HttpSession session, HttpServletResponse response) throws Exception {

        Gson gson = new Gson();
        Patient patient = gson.fromJson(request.getParameter("data"), Patient.class);
        return gson.toJson(api.addPatient(patient));
    }
}
