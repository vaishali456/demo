/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.medq.servlets;

import com.google.gson.Gson;
import com.medq.dto.Patient;
import com.medq.dto.ReferralComments;
import com.medq.services.Service;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author eshine
 */
@WebServlet(name = "AddCommentsOnReferral", urlPatterns = {"/addcommentsonreferral"})
public class AddCommentsOnReferral extends CoreJSONServlet {

    @Override
    protected Object execServiceJSON(Service api, HttpServletRequest request,
            HttpSession session, HttpServletResponse response) throws Exception {

        Gson gson = new Gson();
        ReferralComments referralcomments = gson.fromJson(request.getParameter("data"), ReferralComments.class);
        return gson.toJson(api.addCommentsOnReferral(referralcomments));
    }

}
