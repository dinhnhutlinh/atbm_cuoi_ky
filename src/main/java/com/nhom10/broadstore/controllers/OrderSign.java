package com.nhom10.broadstore.controllers;

import com.nhom10.broadstore.beans.Order;
import com.nhom10.broadstore.beans.OrderSignature;
import com.nhom10.broadstore.beans.User;
import com.nhom10.broadstore.services.OrderServices;
import com.nhom10.broadstore.util.Define;
import com.nhom10.broadstore.util.Invoice;
import com.nhom10.broadstore.util.SecurityUtil;
import org.apache.commons.io.FileUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;

import java.util.Base64;

@WebServlet(urlPatterns = "/order_sign")
public class OrderSign extends HttpServlet {
    OrderServices orderServices = new OrderServices();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        Order order = orderServices.findById(id);

        File tomcatFolder = new File("/pdf");
        String filePdf = tomcatFolder + "/" + order.getId() + ".pdf";
        System.out.println(filePdf);
        if (!checkFileContain(filePdf)) {
            createFileInvoice(order, req);
        }
        req.setAttribute("file", filePdf);
        RequestDispatcher rd = req.getRequestDispatcher("order_sign.jsp");
        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");

        String signature = req.getParameter("signature").trim();
        if (signature == null || signature.equals("")) {
            req.setAttribute("mess", "signature is not empty");
//            doGet(req, resp);
            RequestDispatcher rd = req.getRequestDispatcher("order_sign.jsp");
            rd.forward(req, resp);
        }

        Order order = orderServices.findById(id);
        String application = req.getServletContext().getRealPath("");
//        application + File.separator + "pdf"
        File tomcatFolder = new File(application + "pdf");
        String filePdf = tomcatFolder + "/" + order.getId() + ".pdf";

        byte[] fileByte = FileUtils.readFileToByteArray(new File(filePdf));

        HttpSession session = req.getSession(true);
        User user = (User) session.getAttribute(Define.userSession);
        try {
            PublicKey publicKey = SecurityUtil.publicKeyFromBase64(user.getPubKey());
            try {
                boolean isVerify = SecurityUtil.verifySignature(fileByte,
                        Base64.getDecoder().decode(signature),
                        publicKey);

                if (isVerify) {
                    OrderSignature orderSignature = new OrderSignature();
                    orderSignature.setOrderId(id);
                    orderSignature.setSignature(signature);
                    orderSignature.setFile(filePdf);
                    orderServices.signOrder(orderSignature);

                    req.setAttribute("mess", "order Success");
                    RequestDispatcher rd = req.getRequestDispatcher("success.jsp");
                    rd.forward(req, resp);
                } else {
                    req.setAttribute("mess", "Signature is invalid");
                    doGet(req, resp);
                }
            } catch (IllegalArgumentException e) {
                req.setAttribute("mess", "Signature is invalid");
                doGet(req, resp);
            }

        } catch (NoSuchAlgorithmException | InvalidKeySpecException | NoSuchProviderException | InvalidKeyException | SignatureException e) {
            e.printStackTrace();
        }


    }

    private boolean checkFileContain(String path) {
        File file = new File(path);
        return file.exists();
    }

    private void createFileInvoice(Order order, HttpServletRequest req) throws IOException {
        String pathInfo = req.getServletContext().getRealPath("");
        System.out.println("path Info: " + pathInfo);
        Invoice invoice = new Invoice(pathInfo + "/" + "times.ttf");
        invoice.getdata(order);
        String application = req.getServletContext().getRealPath("");
        System.out.println("tomcatFolder: " + application + File.separator + "pdf");
        File tomcatFolder = new File(application + "pdf");
        if (!tomcatFolder.exists()) {
            tomcatFolder.mkdirs();
        }
//        tomcatFolder + File.separator + order.getId() + ".pdf"
        invoice.writeInvoice(tomcatFolder + File.separator + order.getId() + ".pdf");
    }

}
