package com.nhom10.broadstore.controllers.admin;

import com.nhom10.broadstore.beans.User;
import com.nhom10.broadstore.util.Define;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = "/admin_admin")
public class AdminsController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        User user = (User) session.getAttribute(Define.userSession);
        if (user == null || user.getRole() == 1) {
            resp.sendRedirect("Login");
            return;
        }
        RequestDispatcher rd = req.getRequestDispatcher("admin/admins.jsp");
        rd.forward(req, resp);
    }
}
