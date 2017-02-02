
package com.medq.servlets;

import com.google.gson.Gson;
import com.medq.services.Service;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet(name = "ResponseToChatRequestServlet", urlPatterns = {"/responsetochatrequest"})
public class ResponseToChatRequestServlet extends CoreJSONServlet {

    @Override
    protected Object execServiceJSON(Service api, HttpServletRequest request, HttpSession session, HttpServletResponse response) throws Exception {
        int senderid = 0;
        String responseflag = "";
        if (request.getParameter("senderid") != null) {
      
            senderid = Integer.parseInt(request.getParameter("senderid"));

        }
        if (request.getParameter("responseflag") != null) {
            responseflag = request.getParameter("responseflag");
        }

        Gson gson = new Gson();
        return gson.toJson(api.responseToChatRequest(senderid, responseflag));
    }

}