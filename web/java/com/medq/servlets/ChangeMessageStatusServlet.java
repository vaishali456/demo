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
 * @author eshine
 */
@WebServlet(name = "ChangeMessageStatusServlet", urlPatterns = {"/changemessagestatus"})
public class ChangeMessageStatusServlet extends CoreJSONServlet {

    @Override
    protected Object execServiceJSON(Service api, HttpServletRequest request, HttpSession session, HttpServletResponse response) throws Exception {
        int selecteduserid = 0;
        int selectedgroupid = 0;

        if (request.getParameter("selecteduserid") != null) {

            selecteduserid = Integer.parseInt(request.getParameter("selecteduserid"));
        }
        if (request.getParameter("selectedgroupid") != null) {

            selecteduserid = Integer.parseInt(request.getParameter("selectedgroupid"));
        }

        Gson gson = new Gson();
//        return gson.toJson(api.changeMessageStatus(selecteduserid, selectedgroupid));
        return gson.toJson("changemessagestatus is not in use! service deleted");
    }

}
