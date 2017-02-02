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
@WebServlet(name = "GetReferredFromListServlet", urlPatterns = {"/getreferredfromlist"})
public class GetReceivedReferralListServlet extends CoreJSONServlet {

    @Override
    protected Object execServiceJSON(Service api, HttpServletRequest request, HttpSession session, HttpServletResponse response) throws Exception {

        //Here we took a timestamp as paramater just for IOS application development
        String filtertimestamp = "";

        if (request.getParameter("filtertimestamp") != null) {

            filtertimestamp = request.getParameter("filtertimestamp");
        }

        Gson gson = new Gson();
        return gson.toJson(api.getReferredFromList(filtertimestamp));
    }
}
