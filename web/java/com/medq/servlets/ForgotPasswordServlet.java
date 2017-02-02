package com.medq.servlets;

import com.google.gson.Gson;
import com.medq.services.Service;
import com.medq.util.Util;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "ForgotPasswordServlet", urlPatterns = {"/forgotpassword"})
public class ForgotPasswordServlet extends CoreJSONServlet {

    @Override
    protected Object execServiceJSON(Service api, HttpServletRequest request, HttpSession session, HttpServletResponse response) throws Exception {
        String baseURL =Util.getBaseURLFromRequest(request);
        String strEmailID = "";
        if (request.getParameter("emailid") != null) {
            strEmailID = request.getParameter("emailid");
        }
        
        Gson gson = new Gson();
        return gson.toJson(api.forgotPassword(strEmailID, baseURL));
    }

}
