package com.nhom10.broadstore.controllers;

import com.nhom10.broadstore.beans.User;
import com.nhom10.broadstore.services.PasswordHash;
import com.nhom10.broadstore.services.UserService;
import com.nhom10.broadstore.util.Define;
import com.nhom10.broadstore.util.SecurityUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

@WebServlet(urlPatterns = "/confirm_payment")
public class ConfirmPayment extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher rd = req.getRequestDispatcher("order_sign.jsp");
        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String signature = req.getParameter("signature");
        HttpSession session = req.getSession(true);
        User user = (User) session.getAttribute(Define.userSession);
        try {
            PublicKey publicKey = SecurityUtil.publicKeyFromBase64(user.getPubKey());
            String token = Base64.getEncoder().encodeToString(signature.getBytes());
            boolean isSignatureValid = SecurityUtil.verifySignature(token.getBytes(), Base64.getDecoder().decode(signature), publicKey
            );
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | NoSuchProviderException | InvalidKeyException | SignatureException e) {
            e.printStackTrace();
        }
//        if (!password.equals(repassword)) {
//            req.setAttribute("mess", "Password not same");
//        } else {
//            UserService userService = new UserService();
//            try {
//                userService.changePassword(id, PasswordHash.createHash(password));
//            } catch (NoSuchAlgorithmException e) {
//                throw new RuntimeException(e);
//            } catch (InvalidKeySpecException e) {
//                throw new RuntimeException(e);
//            }
//            req.setAttribute("mess", "Password was change");
//        }


        RequestDispatcher rd = req.getRequestDispatcher("change_password.jsp");
        rd.forward(req, resp);
    }
}
