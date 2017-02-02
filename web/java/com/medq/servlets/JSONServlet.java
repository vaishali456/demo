package com.medq.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

 abstract  public class JSONServlet extends BaseServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean scriptTag = false;
        String cb = request.getParameter("callback");
        if (cb != null) {
            scriptTag = true;
            response.setContentType("text/javascript");
        } else {
            response.setContentType("application/x-json");
        }
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(true);
        try {
            Object objResult = execJSON(request, session, response);
            if (scriptTag) {
                String s = cb + "(" + objResult.toString() + ");";
                out.print(s);
            } else {
                out.print(objResult);
            }
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
        out.close();
    }

    abstract protected Object execJSON (HttpServletRequest request, HttpSession session, HttpServletResponse response) throws Exception;
}

