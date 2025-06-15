package com.aleksey.servlet_app.utils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Scanner;

public class HttpUtils {

    public static String getIdFromRequest(HttpServletRequest request) {
        String id = request.getPathInfo();
        return id == null ? null : id.replace("/", "");
    }

    public static String getBodyFromRequest(HttpServletRequest request) throws IOException {
        Scanner scanner = new Scanner(request.getInputStream(), "UTF-8");
        return scanner.useDelimiter("\\A").next();
    }
}
