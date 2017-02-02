
package com.medq.servlets;

import com.google.gson.Gson;
import com.medq.services.Service;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet(name = "ChangePassword", urlPatterns = {"/changepassword"})
public class ChangePassword extends CoreJSONServlet {

    @Override
    protected Object execServiceJSON(Service api, HttpServletRequest request, HttpSession session, HttpServletResponse response) throws Exception {
    
        String strPassword = "";
        if (request.getParameter("oldpassword") != null) {
            strPassword = request.getParameter("oldpassword");
        }
        String newPassword = "";
        if (request.getParameter("newpassword") != null) {
            newPassword = request.getParameter("newpassword");
        }
        Gson gson = new Gson();
        return gson.toJson(api.changePassword(strPassword, newPassword));
    }

}
