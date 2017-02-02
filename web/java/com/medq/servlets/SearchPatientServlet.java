package com.medq.servlets;

import com.google.gson.Gson;
import com.medq.services.Service;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "SearchPatientServlet", urlPatterns = {"/searchpatient"})
public class SearchPatientServlet extends CoreJSONServlet {

    @Override
    protected Object execServiceJSON(Service api, HttpServletRequest request, HttpSession session, HttpServletResponse response) throws Exception {

        String searchText = null;
        if (request.getParameter("searchtext") != null) {
            searchText = request.getParameter("searchtext");
        }
        Gson gson = new Gson();
        return gson.toJson(api.searchPatient(searchText));
    }

}
