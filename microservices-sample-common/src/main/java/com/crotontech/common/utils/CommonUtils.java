package com.crotontech.common.utils;

import javax.servlet.http.HttpServletRequest;

public class CommonUtils {

    public static String getAuthorizationHeader(HttpServletRequest request){
        String header = request.getHeader("Authorization");
        String token = header.replace("Bearer", "").trim();

        return token;
    }
}
