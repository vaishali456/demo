
package com.medq.servlets;

import com.google.gson.Gson;
import com.medq.services.Service;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet(name = "CityServlet", urlPatterns = {"/getcity"})
public class CityServlet extends CoreJSONServlet {

    @Override
    protected Object execServiceJSON(Service api, HttpServletRequest request, HttpSession session, HttpServletResponse response) throws Exception {
        int stateid = 0;
   
        if (request.getParameter("stateid") != null) {
          
            stateid = Integer.parseInt(request.getParameter("stateid"));
        }

        Gson gson = new Gson();
        return gson.toJson(api.city(stateid));
    }

}
