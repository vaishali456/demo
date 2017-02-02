package com.medq.servlets;

import com.google.gson.Gson;
import com.medq.services.Service;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "ChatlogServlet", urlPatterns = {"/getchathistory"})
public class ChatlogServlet extends CoreJSONServlet {

    @Override
    protected Object execServiceJSON(Service api, HttpServletRequest request, HttpSession session, HttpServletResponse response) throws Exception {
        int receiverid = 0;

        if (request.getParameter("receiverid") != null) {

            receiverid = Integer.parseInt(request.getParameter("receiverid"));
        }
        String lastmessagetimestamp = "";
        if (request.getParameter("lastmessagetimestamp") != null) {

            lastmessagetimestamp = request.getParameter("lastmessagetimestamp");
        }
        int selectedgroupid = 0;
        if (request.getParameter("selectedgroupid") != null) {

            selectedgroupid = Integer.parseInt(request.getParameter("selectedgroupid"));
        }

        Gson gson = new Gson();
        return gson.toJson(api.getChatHistory(receiverid, lastmessagetimestamp, selectedgroupid));
    }
}
