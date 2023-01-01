package com.nhom10.broadstore.controllers;

import com.nhom10.broadstore.beans.User;
import com.nhom10.broadstore.services.UserService;
import com.nhom10.broadstore.util.Define;
import com.nhom10.broadstore.util.MailHelper;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.security.*;
import java.util.Base64;

@WebServlet(urlPatterns = "/new_key")
public class UserGenerateKeyController extends HttpServlet {
    UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("new_key.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        User user = (User) session.getAttribute(Define.userSession);
        if (user != null) {
            resp.sendRedirect("Home");
            return;
        }
        handleWithoutLogin(req, resp);

    }

    private void handleWithoutLogin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        try {
            if (!userService.isContainUserWithEmail(email)) {
                req.setAttribute("mess", "Don't have account with your email!!!");
                doGet(req, resp);
                return;
            }
            User user;
            user = userService.login(email, password);

            if (user == null) {
                req.setAttribute("mess", "Email or password is wrong!!!");
                doGet(req, resp);
                return;
            }
            generateKey(user);
            req.setAttribute("mess", "Tạo khóa thành công" +
                    "Vui lòng lưu lại khóa riêng tư và khóa sẽ được tới email bạn");
            doGet(req, resp);
        } catch (Exception e) {
            req.setAttribute("mess", "Error");
            doGet(req, resp);
        }

    }

    private void handleWithLogin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        User user = (User) session.getAttribute(Define.userSession);
        String password = req.getParameter("password");

        if (userService.checkPassword(user.getId(), password)) {

            try {
                generateKey(user);
                req.setAttribute("mess", "Tạo khóa thành công" +
                        "Vui lòng lưu lại khóa riêng tư và khóa sẽ được tới email bạn");
                doGet(req, resp);
            } catch (Exception e) {
                req.setAttribute("mess", "Error");
                doGet(req, resp);
            }
        } else {
            req.setAttribute("mess", "Password dont correct");
            doGet(req, resp);
        }
    }

    void generateKey(User user) throws NoSuchAlgorithmException, NoSuchProviderException, IOException {

        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA", "SUN");
        keyGen.initialize(2048);
        KeyPair keyPair = keyGen.generateKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();
        File file = new File(user.getId() + ".priv");

        PrintWriter writer = new PrintWriter(file, "UTF-8");
        String privateKeyString = Base64.getEncoder().encodeToString(privateKey.getEncoded());
        writer.write(privateKeyString);
        writer.close();

        try {
            MailHelper.sendMailWithFile("", user.getEmail(), "Private key", file);

        } catch (MessagingException e) {
            e.printStackTrace();
        }

        String publicKeyString = Base64.getEncoder().encodeToString(publicKey.getEncoded());

        userService.updatePublicKey(user.getId(), publicKeyString);
    }
}
