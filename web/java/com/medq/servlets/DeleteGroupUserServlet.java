package com.medq.servlets;

import com.google.gson.Gson;
import com.medq.services.Service;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "DeleteGroupUserServlet", urlPatterns = {"/deleteuserfromgroup"})
public class DeleteGroupUserServlet extends CoreJSONServlet {

    @Override
    protected Object execServiceJSON(Service api, HttpServletRequest request, HttpSession session, HttpServletResponse response) throws Exception {
        int groupid = 0;
        int useridd = 0;
        if (request.getParameter("groupid") != null) {
            
            groupid = Integer.parseInt(request.getParameter("groupid"));
        }
        if (request.getParameter("userid") != null) {

            useridd = Integer.parseInt(request.getParameter("userid"));
        }

        Gson gson = new Gson();
        return gson.toJson(api.deleteGroupUser(groupid, useridd));
    }

}
