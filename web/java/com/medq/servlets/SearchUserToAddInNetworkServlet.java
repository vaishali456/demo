package com.medq.servlets;

import com.google.gson.Gson;
import com.medq.services.Service;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "SearchUserToAddInNetworkServlet", urlPatterns = {"/searchusertoaddinnetwork"})
public class SearchUserToAddInNetworkServlet extends CoreJSONServlet {

    @Override
    protected Object execServiceJSON(Service api, HttpServletRequest request, HttpSession session, HttpServletResponse response) throws Exception {
        String searchText = null;

        if (request.getParameter("fname") != null) {
            searchText = request.getParameter("fname");

        }

        Gson gson = new Gson();
        return gson.toJson(api.searchUserToAddInNetwork(searchText));
    }

}
