/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.medq.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;

/**
 *
 * @author eshine
 */
public class FileOperation {

    public ArrayList<String> movefile(ArrayList<String> TempUrlList, ArrayList<Integer> UseridList, int requestType) throws UnsupportedEncodingException {

        InputStream inStream;
        OutputStream outStream;
        File dir;
        String folderName;

        String destinationPath = null;
        int userid = 0;
        String directoryPath = null;
        ArrayList<String> fileDesPathList = new ArrayList();
        String finalpath = null;
        String path = this.getClass().getClassLoader().getResource("").getPath();
        String fullPath = URLDecoder.decode(path, "UTF-8");
        String pathArr[] = fullPath.split("/WEB-INF/classes/");
        fullPath = pathArr[0];

        String rootfoldername = "assets";
        directoryPath = fullPath + "/" + rootfoldername;
        File fileAssets = new File(directoryPath);
        if (!fileAssets.exists()) {
            fileAssets.mkdir();
        }

        String childfoldername = "images";
        directoryPath = directoryPath + "/" + childfoldername;
        File fileimages = new File(directoryPath);
        if (!fileimages.exists()) {
            fileimages.mkdir();
        }

        switch (requestType) {

            case 1:
                folderName = "ProfileImage";
                break;
            case 2:
                folderName = "RequiredReport";
                break;
            case 3:
                folderName = "PatientImage";
                break;
            default:
                folderName = "UploadedDoc";

        }

        directoryPath = directoryPath + "/" + folderName;

        File file = new File(directoryPath);
        if (!file.exists()) {
            file.mkdir();
        }

        try {

            for (int i = 0; i < TempUrlList.size(); i++) {
                File afile = new File(TempUrlList.get(i));
                if (UseridList.size() > 1) {
                    userid = UseridList.get(i);
                    destinationPath = directoryPath + "/" + userid;
                } else if (UseridList.size() == 1) {
                    userid = UseridList.get(0);
                    destinationPath = directoryPath + "/" + userid;
                }

                // attempt to create the directory as per ID
                dir = new File(destinationPath);
                dir.mkdir();

                // Get File name from Temporary URL
                String url1 = TempUrlList.get(i);
                String[] values = url1.split("/");
                String filename = values[values.length - 1];

                destinationPath = destinationPath + "/" + filename;

                File bfile = new File(destinationPath);
                inStream = new FileInputStream(afile);
                outStream = new FileOutputStream(bfile);
                byte[] buffer = new byte[1024];
                int length;

                long before_move = bfile.getFreeSpace();

                //copy the file content in bytes
                while ((length = inStream.read(buffer)) > 0) {

                    outStream.write(buffer, 0, length);

                }
                inStream.close();
                outStream.close();
                //Check if free space is reduced after moving file             
                if (before_move > bfile.getFreeSpace()) {

                    finalpath = rootfoldername + "/" + childfoldername + "/" + folderName + "/" + userid + "/" + filename;

                }

                //delete the original file
                afile.delete();
                fileDesPathList.add(finalpath);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileDesPathList;

    }

    public boolean deletefile(String url) {
        boolean isdeleted = false;
        File filetodelete = new File(url);
        isdeleted = filetodelete.delete();
        return isdeleted;
    }

//   public static void main(String args[]){
//     ArrayList<String> UrlList = new ArrayList();
//     UrlList.add("/home/eshine/apache-tomcat-8.0.15/bin/Temp/female.jpg");
//     UrlList.add("/home/eshine/apache-tomcat-8.0.15/bin/Temp/male.jpg");
//     
//    
//     ArrayList<Integer> UseridList=new ArrayList<Integer>();
//    
//     UseridList.add(21);
//     UseridList.add(22);
//     
//    
//    moveFile mv=new moveFile();
//    mv.movefile(UrlList, UseridList, 1);
//    }
}
