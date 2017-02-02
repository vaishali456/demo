/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.medq.servlets;

import com.google.gson.Gson;
import com.medq.dto.Chatlog;
import com.medq.services.Service;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author eshine
 */
@WebServlet(name = "attachmentForChatServlet", urlPatterns = {"/attachmentForChat"})
public class AttachmentForChatServlet extends HttpServlet {

    protected Object execServiceJSON(Service api, HttpServletRequest request, HttpSession session, HttpServletResponse response) throws Exception {

        Gson gson = new Gson();
        Chatlog chatlogobj = gson.fromJson(request.getParameter("data"), Chatlog.class);

        return gson.toJson(api.attachmentForChat(chatlogobj));
    }

}
