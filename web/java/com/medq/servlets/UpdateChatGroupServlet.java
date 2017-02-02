package com.medq.servlets;

import com.google.gson.Gson;
import com.medq.dto.ChatGroup;
import com.medq.services.Service;
import java.util.ArrayList;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "UpdateChatGroupServlet", urlPatterns = {"/updatechatgroup"})
public class UpdateChatGroupServlet extends CoreJSONServlet {

    @Override
    protected Object execServiceJSON(Service api, HttpServletRequest request, HttpSession session, HttpServletResponse response) throws Exception {

        Gson gson = new Gson();
        ChatGroup chatgroup = gson.fromJson(request.getParameter("updategroup"), ChatGroup.class);
        ArrayList<String> users = chatgroup.getGroupMemberlist();
        chatgroup.setMemberCount(users.size());
        System.err.println(chatgroup.toString());

        return gson.toJson(api.updateChatGroup(chatgroup));
    }
}
