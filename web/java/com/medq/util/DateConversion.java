/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.medq.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 *
 * @author eshine
 */
public class DateConversion {
    
    @SuppressWarnings("unchecked")
     public static Timestamp stringToDateConversion(String dateInString) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMM dd, yyyy HH:mm:ss a");
//        Date date = dateFormat.parse(Long.parseLong(dateInString));
        Timestamp stamp = new Timestamp(Long.parseLong(dateInString) * 1000);
        java.sql.Date date = new java.sql.Date(stamp.getTime());
        // java.sql.Date date = new java.sql.Date(Long.parseLong(dateInString) * 1000);
        // System.out.println(date);
        return stamp;
    }

    @SuppressWarnings("unchecked") 
    public static String dateToUnixConversion(java.sql.Timestamp date) {

        long unixTime = (long) date.getTime() / 1000;
        return Long.toString(unixTime);
    }
    
       
    
}
