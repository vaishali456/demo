package com.medq.servlets;

import com.google.gson.Gson;
import com.medq.services.Service;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "GetReferenceDetailsServlet", urlPatterns = {"/getreferencedetailsservlet"})
public class GetReferenceDetailsServlet extends CoreJSONServlet {

    @Override
    protected Object execServiceJSON(Service api, HttpServletRequest request, HttpSession session, HttpServletResponse response) throws Exception {
        int referralid = 0;
        if (request.getParameter("referralid") != null) {

            referralid = Integer.parseInt(request.getParameter("referralid"));
        }
        Gson gson = new Gson();
        return gson.toJson(api.getReferralDtl(referralid));
    }
}
