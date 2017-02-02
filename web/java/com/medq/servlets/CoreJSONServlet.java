package com.medq.servlets;

import com.medq.services.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public abstract class CoreJSONServlet extends JSONServlet {

    @Override
    protected Object execJSON(HttpServletRequest request, HttpSession session, HttpServletResponse response)
            throws Exception {
        Service api = (Service) session.getAttribute("api");
        if (api == null) {

            api = new Service();
            session.setAttribute("api", api);
        }

        return execServiceJSON(api, request, session, response);
    }

    abstract protected Object execServiceJSON(Service api, HttpServletRequest request, HttpSession session, HttpServletResponse response)
            throws Exception;

}
