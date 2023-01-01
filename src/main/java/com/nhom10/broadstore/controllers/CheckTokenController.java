package com.nhom10.broadstore.controllers;

import com.nhom10.broadstore.beans.User;
import com.nhom10.broadstore.services.UserService;
import com.nhom10.broadstore.util.Define;
import com.nhom10.broadstore.util.SecurityUtil;

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

@WebServlet(urlPatterns = "/check_token")
public class CheckTokenController extends HttpServlet {
    UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        User user = userService.findById(id);

        String userId = user.getId();
        String email = user.getEmail();

        String token = Base64.getEncoder().encodeToString((userId + email).getBytes());
        System.out.println(token);
        req.setAttribute("id", id);
        req.setAttribute("token", token);
        req.getRequestDispatcher("check_token.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String signature = req.getParameter("signature");
        User user = userService.findById(req.getParameter("id"));
        String userId = user.getId();
        String email = user.getEmail();
        if (signature == null || signature.equals("")) {
            req.setAttribute("mess", "Signature is invalid!!!");
            doGet(req, resp);
            return;
        }

        try {
            PublicKey publicKey = SecurityUtil.publicKeyFromBase64(user.getPubKey());
            String token = Base64.getEncoder().encodeToString((userId + email).getBytes());
            boolean isSignatureValid = SecurityUtil.verifySignature(token.getBytes(), Base64.getDecoder().decode(signature), publicKey
            );
            HttpSession session = req.getSession(true);

            if (isSignatureValid) {
                session.setAttribute(Define.userSession, user);
                if (user.getRole() == 0) {
                    resp.sendRedirect("dashboard");
                } else {
                    resp.sendRedirect("Home");
                }

            } else {
                req.setAttribute("mess", "Signature is invalid!!!");
                doGet(req, resp);
            }
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | SignatureException | NoSuchProviderException |
                InvalidKeyException e) {
            req.getRequestDispatcher("check_token.jsp").forward(req, resp);
        }
    }
}
