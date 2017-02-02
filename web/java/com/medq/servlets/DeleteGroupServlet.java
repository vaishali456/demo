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

@WebServlet(name = "DeleteGroupServlet", urlPatterns = {"/deletegroup"})
public class DeleteGroupServlet extends CoreJSONServlet {

    @Override
    protected Object execServiceJSON(Service api, HttpServletRequest request, HttpSession session, HttpServletResponse response) throws Exception {
        int groupid = 0;
        if (request.getParameter("ChatgroupID") != null) {
        
            groupid = Integer.parseInt( request.getParameter("ChatgroupID"));
        }

        Gson gson = new Gson();
        return gson.toJson(api.deleteGroup(groupid));
    }

}
