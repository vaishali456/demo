package com.medq.servlets;

import com.google.gson.Gson;
import com.medq.dto.Referral;
import com.medq.services.Service;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "createReferralServlet", urlPatterns = {"/createreferral"})
public class CreateReferralServlet extends CoreJSONServlet {

    @Override
    protected Object execServiceJSON(Service api, HttpServletRequest request, HttpSession session, HttpServletResponse response) throws Exception {

        Gson gson = new Gson();        
        Referral referral = gson.fromJson(request.getParameter("data"), Referral.class);
        return gson.toJson(api.newCreateReferral(referral));
        
    }

}
