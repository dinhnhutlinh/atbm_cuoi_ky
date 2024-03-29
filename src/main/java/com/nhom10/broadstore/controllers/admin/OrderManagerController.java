package com.nhom10.broadstore.controllers.admin;
import com.nhom10.broadstore.beans.OrderStatus;
import com.nhom10.broadstore.beans.User;
import com.nhom10.broadstore.emun.Role;
import com.nhom10.broadstore.util.Define;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = "/admin_orders")
public class OrderManagerController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        req.setAttribute("mapStatus", OrderStatus.mapOrderStatus());
        RequestDispatcher rd = req.getRequestDispatcher("admin/orders.jsp");
        rd.forward(req, resp);
    }
}
