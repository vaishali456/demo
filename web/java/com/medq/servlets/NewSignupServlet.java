/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.medq.servlets;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.medq.services.Service;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author eshine
 */
@WebServlet(name = "newSignupServlet", urlPatterns = {"/newSignup"})
public class NewSignupServlet extends CoreJSONServlet {

    @Override
    protected Object execServiceJSON(Service api, HttpServletRequest request, HttpSession session, HttpServletResponse response) throws Exception {

        Gson gson = new Gson();
        
          String SignupData = request.getParameter("data");
          Map<String, Object> signupData = new Gson().fromJson(SignupData, new TypeToken<HashMap<String, Object>>() {}.getType());
          
          return  gson.toJson(api.newSignup(signupData));

    }

}
