package com.nhom10.broadstore.controllers;

import com.nhom10.broadstore.beans.User;
import com.nhom10.broadstore.services.UserService;
import com.nhom10.broadstore.util.Define;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

@WebServlet(urlPatterns = "new_key")
public class UserGenerateKeyController extends HttpServlet {
    UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("user/error_page.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        User user = (User) session.getAttribute(Define.userSession);
        String password = req.getParameter("password");

        if (userService.checkPassword(user.getId(), password)) {
            try {
                KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
                keyGen.initialize(2048);
                KeyPair keyPair = keyGen.generateKeyPair();
                PublicKey publicKey = keyPair.getPublic();
                PrivateKey privateKey = keyPair.getPrivate();

                File uploads = new File(System.getProperty("upload.location"));
                File file = new File(uploads, user.getId() + ".priv");

                PrintWriter writer = new PrintWriter(file, "UTF-8");
                String line = Base64.getEncoder().encodeToString(privateKey.getEncoded());
                writer.write(line);
                writer.close();

                String filePath = file.getPath();

                String publicKeyString = Base64.getEncoder().encodeToString(publicKey.getEncoded());

                userService.updatePublicKey(user.getId(), publicKeyString);

                req.setAttribute("message", "Tạo khóa thành công");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
