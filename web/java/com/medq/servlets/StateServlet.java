
package com.medq.servlets;

import com.google.gson.Gson;
import com.medq.services.Service;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "StateServlet", urlPatterns = {"/getstates"})
public class StateServlet extends CoreJSONServlet {

    @Override
    protected Object execServiceJSON(Service api, HttpServletRequest request, HttpSession session, HttpServletResponse response) throws Exception {

        Gson gson = new Gson();
        return gson.toJson(api.state());
    }

}
