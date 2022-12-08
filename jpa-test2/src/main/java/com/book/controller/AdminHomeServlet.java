package com.book.controller;

import com.book.business.Customer;
import com.book.business.Employee;
import com.book.business.Product;
import com.book.dao.OrderDAO;
import com.book.dao.ProductDAO;
import com.book.entity.OrderEntity;
import com.book.entity.ProductEntity;
import com.book.entity.UserEntity;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "AdminHomeServlet", value = "/admin")
public class AdminHomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //      Thêm tiếng việt
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        List<ProductEntity> bookList = ProductDAO.getAll();
        request.setAttribute("bookList", bookList);

        List<UserEntity> userList = Customer.getAll();
        request.setAttribute("customerList", userList);

        List<UserEntity> employeeList = Employee.getAll();
        request.setAttribute("employeeList", employeeList);

        List<OrderEntity> orderList = OrderDAO.get5LastestOrder();
        request.setAttribute("orderList", orderList);

        List<ProductEntity> book5List = ProductDAO.get5LastestProduct();
        request.setAttribute("book5List", book5List);

        ProductEntity bestSellerBook= Product.BestSellerBook();
        request.setAttribute("bestSellerBook", bestSellerBook);
        // Tổng số bán trong ngày
        // Thu nhập trong tháng
        // Khách hàng trong năm
        // Top selling
        request.getRequestDispatcher("/admin/index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
