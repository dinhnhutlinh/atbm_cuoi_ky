package com.nhom10.broadstore.api;

import com.nhom10.broadstore.beans.User;
import com.nhom10.broadstore.beans.UserPubKey;
import com.nhom10.broadstore.services.KeyService;
import com.nhom10.broadstore.services.UserService;
import com.nhom10.broadstore.util.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
@WebServlet(urlPatterns = "/KeysController")
public class KeysController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        KeyService keyService = new KeyService();
        List<UserPubKey> userPubKeys = keyService.getAllKeys();
        PrintWriter printWriter = resp.getWriter();
        printWriter.println(new JsonUtil().toJSon(userPubKeys));
        printWriter.flush();
        printWriter.close();
    }
}
