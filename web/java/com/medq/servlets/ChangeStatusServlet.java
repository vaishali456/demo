package com.medq.servlets;

import com.google.gson.Gson;
import com.medq.services.Service;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "ChangeStatusServlet", urlPatterns = {"/changestatus"})
public class ChangeStatusServlet extends CoreJSONServlet {

    @Override
    protected Object execServiceJSON(Service api, HttpServletRequest request, HttpSession session, HttpServletResponse response) throws Exception {
        int userid = 0;
        if (request.getParameter("userid") != null) {
            userid = Integer.parseInt(request.getParameter("userid"));
        }
        

        Gson gson = new Gson();
        return gson.toJson(api.changeStatus(userid));
    }
}
