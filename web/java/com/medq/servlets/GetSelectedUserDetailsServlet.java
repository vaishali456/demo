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
 * @author eshine
 */
@WebServlet(name = "GetSelectedUserDetailsServlet", urlPatterns = {"/getselecteduserdetails"})
public class GetSelectedUserDetailsServlet extends CoreJSONServlet {

    @Override
    protected Object execServiceJSON(Service api, HttpServletRequest request, HttpSession session, HttpServletResponse response) throws Exception {
        int sentUserId = 0;

        if (request.getParameter("sentuserid") != null) {

            sentUserId = Integer.parseInt(request.getParameter("sentuserid"));
        }

        Gson gson = new Gson();
        return gson.toJson(api.getSelectedUserDetails(sentUserId));
    }
}
