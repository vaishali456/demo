package com.medq.servlets;

import com.google.gson.Gson;
import com.medq.services.Service;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "DiseaseLookUpServlet", urlPatterns = {"/diseaselookup"})
public class DiseaseLookUpServlet extends CoreJSONServlet {

    @Override
    protected Object execServiceJSON(Service api, HttpServletRequest request, HttpSession session, HttpServletResponse response) throws Exception {
        int specializationid = 0;

        if (request.getParameter("specializationid") != null) {
            
            specializationid = Integer.parseInt(request.getParameter("specializationid"));
        }

        Gson gson = new Gson();
        return gson.toJson(api.diseaseLookup(specializationid));
    }

}
