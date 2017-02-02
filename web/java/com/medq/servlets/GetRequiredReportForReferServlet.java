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
@WebServlet(name = "GetRequiredReportForReferServlet", urlPatterns = {"/getrequiredreportforrefer"})
public class GetRequiredReportForReferServlet extends CoreJSONServlet {

    @Override
    protected Object execServiceJSON(Service api, HttpServletRequest request, HttpSession session, HttpServletResponse response) throws Exception {

        int doctorid = 0;
        if (request.getParameter("doctorid") != null) {

            doctorid = Integer.parseInt(request.getParameter("doctorid"));
        }
        Gson gson = new Gson();
        return gson.toJson(api.getRequiredReportForRefer(doctorid));
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
