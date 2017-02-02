/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.medq.servlets;

import com.google.gson.Gson;
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
@WebServlet(name = "ResetPasswordServlet", urlPatterns = {"/resetpassword"})
public class ResetPasswordServlet extends CoreJSONServlet {

    @Override
    protected Object execServiceJSON(Service api, HttpServletRequest request, HttpSession session, HttpServletResponse response) throws Exception {

        String password = "";
        String token = "";
        if (request.getParameter("password") != null && request.getParameter("token") != null) {
            System.out.println("password :" + request.getParameter("password"));
            password = request.getParameter("password");
            token = request.getParameter("token");
            System.out.println("password variable :" + password);
            System.out.println("token variable :" + token);
        }

        Gson gson = new Gson();
        return gson.toJson(api.resetPassword(password, token));
    }
}
