package com.nhom10.broadstore.controllers.customerController;

import com.nhom10.broadstore.beans.User;
import com.nhom10.broadstore.services.UserService;
import com.nhom10.broadstore.util.MailHelper;
import com.nhom10.broadstore.util.SecurityUtil;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.*;
import java.util.Base64;

@WebServlet(urlPatterns = "/active")
public class ActivePage extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        req.setAttribute("mess", "Active success");
        UserService userService = new UserService();
        userService.setActiveCustomer(id, 1);
        KeyPairGenerator keyGen = null;
        try {
            keyGen = KeyPairGenerator.getInstance("DSA");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        keyGen.initialize(2048);
        KeyPair keyPair = keyGen.generateKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        User user = userService.findById(id);
        System.out.println(req.getContextPath());
        File uploads = new File(req.getContextPath() + "/upload");
        if (!uploads.exists()) {
            System.out.println(uploads.getAbsolutePath());
            uploads.mkdirs();
        }
        File file = new File(uploads, user.getId() + ".priv");

        PrintWriter writer = new PrintWriter(file, "UTF-8");
        String privateKeyString = SecurityUtil.base64FromPrivateKey(privateKey);
        writer.write(privateKeyString);
        writer.close();

        try {
            MailHelper.sendMailWithFile("", user.getEmail(), "Private key", file);

        } catch (MessagingException e) {
            e.printStackTrace();
        }

        String publicKeyString = Base64.getEncoder().encodeToString(publicKey.getEncoded());

        userService.updatePublicKey(user.getId(), publicKeyString);

        req.setAttribute("mess", "Kích hoạt tài khoản và tạo khóa" +
                "Tạo khóa thành công" +
                "Vui lòng lưu lại khóa riêng tư và khóa sẽ được tới email bạn");
        req.getRequestDispatcher("success.jsp").forward(req, resp);
    }
}
