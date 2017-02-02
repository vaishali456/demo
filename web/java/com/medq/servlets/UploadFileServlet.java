package com.medq.servlets;

import com.google.gson.Gson;
import com.medq.services.Service;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import java.io.File;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "UploadFileServlet", urlPatterns = {"/UploadImages"})
public class UploadFileServlet extends CoreJSONServlet {

    @Override
    protected Object execServiceJSON(Service api, HttpServletRequest request,
            HttpSession session, HttpServletResponse response) throws Exception {
        HashMap responseObj = new HashMap();
        ArrayList<String> TempUrlList = new ArrayList();
        Gson gson = new Gson();
        int requestType;
        String path;

        requestType = Integer.parseInt(request.getHeader("requesttype"));

        path = this.getClass().getClassLoader().getResource("").getPath();
        String fullPath = URLDecoder.decode(path, "UTF-8");
        String pathArr[] = fullPath.split("/WEB-INF/classes/");
        fullPath = pathArr[0];

        String rootfoldername = "assets";
        fullPath = fullPath + "/" + rootfoldername;

        File fileAssets = new File(fullPath);
        if (!fileAssets.exists()) {
            fileAssets.mkdir();
        }

        switch (requestType) {

            case 1:
                path = "ProfileImageTemp";
                break;
            case 2:
                path = "ReportTemp";
                break;
            case 3:
                path = "PatientTemp";
                break;
            case 4:
                path = "Chat";
                break;

            default:
                path = "UploadedDoc";

        }

        fullPath = fullPath + "/" + path;

        File uploads = new File(fullPath);
        boolean sucees = uploads.mkdir();
        
              uploads.setExecutable(true);
	      uploads.setReadable(true);
	      uploads.setWritable(true);

        
        MultipartRequest multipartRequest = new MultipartRequest(request, fullPath, 5000000,new DefaultFileRenamePolicy());
        Enumeration<String> files = multipartRequest.getFileNames();
        boolean uploadStatus = false;

        while (files.hasMoreElements()) {
            String tempUrl = new String();
            String nextElement = files.nextElement();
            String fileName = multipartRequest.getFilesystemName(nextElement);
            if(path.equals("Chat"))
                  tempUrl=rootfoldername + "/" + path +  "/" + fileName  ;
            else
                tempUrl = tempUrl.concat(fullPath + "/" + fileName);
            uploadStatus=true;
            if (uploadStatus) {
                TempUrlList.add(tempUrl);
            }

        }
        if (uploadStatus) {
            responseObj.put("responseData", TempUrlList);
            responseObj.put("responseMessage", "Upload Success");
            responseObj.put("responseCode", 200);
        } else {
            responseObj.put("responseData", TempUrlList);
            responseObj.put("responseMessage", "Upload Error");
            responseObj.put("responseCode", 400);
        }

        return gson.toJson(responseObj);
    }
    
    
    
    
}
