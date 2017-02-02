/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.medq.servlets;

import com.google.gson.Gson;
import com.medq.dto.ContactUs;
import com.medq.dto.Patient;
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
 * @author eshine-104
 */
@WebServlet(name = "SendMailServlet", urlPatterns = {"/sendmail"})
public class SendMailServlet extends CoreJSONServlet {

    @Override
    protected Object execServiceJSON(Service api, HttpServletRequest request, HttpSession session, HttpServletResponse response) throws Exception {

        boolean flag;
        Gson gson = new Gson();
        ContactUs contact = gson.fromJson(request.getParameter("data"), ContactUs.class);
        System.out.println("request para :" + request.getParameter("data"));
//        flag=api.sendMail(contact);
        return gson.toJson(api.sendMail(contact));

    }

}
