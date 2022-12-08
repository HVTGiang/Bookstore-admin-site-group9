package com.book.controller;

import com.book.business.Customer;
import com.book.business.Employee;
import com.book.dao.UserDAO;
import com.book.entity.UserEntity;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "CustomerServlet", value = {"/admin/customer"})
public class CustomerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//      Thêm tiếng việt
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        List<UserEntity> userList = Customer.getAll();
        request.setAttribute("customerList", userList);

        UserEntity bestCustomerByPrice=Customer.BestCustomerByPrice();
        request.setAttribute("bestCustomerByPrice", bestCustomerByPrice);

        UserEntity BestCustomerByBooks=Customer.BestCustomerByBooks();
        request.setAttribute("BestCustomerByBooks", BestCustomerByBooks);
        // Lấy action của người dùng
        String action = request.getParameter("action");
        if (action == null) {
            action = new String("home");
        }
        switch (action) {
            case "insert":
                actionInsert(request, response);
                break;
            case "edit":
                actionEdit(request, response);
                break;
            case "save":
                actionSave(request, response);
                break;
            case "home":
//                actionHome(request, response);
                request.getRequestDispatcher("/admin/customer.jsp").forward(request, response);
                break;
            default:
                request.getRequestDispatcher("/admin/customer.jsp").forward(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<UserEntity> userList = Customer.getAll();
        request.setAttribute("customerList", userList);
//      Thêm tiếng việt
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        // Lấy action của người dùng
        String action = request.getParameter("action");
        if (action == null) {
            action = new String("home");
        }
        switch (action) {
            case "insert":
                actionInsert(request, response);
                break;
            case "edit":
                actionEdit(request, response);
                break;
            case "save":
                actionSave(request, response);
                break;
            case "home":
//                actionHome(request, response);
                request.getRequestDispatcher("/admin/customer.jsp").forward(request, response);
                break;
            default:
                request.getRequestDispatcher("/admin/customer.jsp").forward(request, response);
                break;
        }
    }

    protected void actionInsert(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        request.setAttribute("action", "insert");
        request.getRequestDispatcher("/admin/customer-form.jsp").forward(request, response);
    }

    protected void actionEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        request.setAttribute("action", "edit");
        String eID = request.getParameter("customerID");
//        Lấy employee có id tương ứng ra
        UserEntity selectedCustomer = UserDAO.find(Integer.parseInt(eID));
        request.setAttribute("customer", selectedCustomer);
        request.getRequestDispatcher("/admin/customer-form.jsp").forward(request, response);
    }

    protected void actionSave(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        String cID = request.getParameter("cID");
        String cName = request.getParameter("customerName");
        String cAddress = request.getParameter("customerAddress");
        String cEmail = request.getParameter("customerEmail");
        String cPhone = request.getParameter("customerPhone");
        String cPassword = request.getParameter("customerPassword");
        String cImageLink = request.getParameter("customerImageLink");


//        Kiểm tra người dùng có nhập dữ liệu vào hay chưa
        if (!cName.trim().equals("") && !cAddress.trim().equals("") && !cEmail.trim().equals("") && !cPhone.trim().equals("")) {
//            Thực hiện kiểm tra dữ liệu vào

//            Tạo một đối tượng Product để lưu dữ liệu
            UserEntity user = new UserEntity();
            if (!cID.equals("")) {
                user.setId(Integer.parseInt(cID));
            }
            user.setName(cName);
            user.setAddress(cAddress);
            user.setEmail(cEmail);
            user.setPhone(cPhone);
            user.setPassword(cPassword);
            user.setImage(cImageLink);
            user.setIsRole(3);

//            Kiểm tra dữ liệu đầu vào đang được lưu trong book
//            Nêu dữ liệu chưa hợp lệ
            if (!"OK".equals(Customer.CheckInputData(user))) {
                request.setAttribute("customer", user);
                request.setAttribute("action", "insert");
                request.setAttribute("message", Customer.CheckInputData(user));
                request.getRequestDispatcher("/admin/customer-form.jsp").forward(request, response);
            }
//            Nếu dữ liệu hợp lệ
            else {
//              Kiểm tra xem có ID chưa, nếu chưa là thêm mới, nếu có là cập nhật
//              Đang thêm nhân viên mới
                if (user.getId() == 0) {
                    request.setAttribute("action", "insert");
                    request.setAttribute("customer", user);
//                  Kiểm tra xem một số thông tin phải là unique
//                  Nếu số điện thoại đã tồn tại
                    if (!"OK".equals(Customer.CheckPhoneAvailable(user))) {
                        request.setAttribute("message", Customer.CheckPhoneAvailable(user));
                        request.getRequestDispatcher("/admin/customer-form.jsp").forward(request, response);
                    }
//                    Nếu email đã tồn tại
                    else if(!"OK".equals(Customer.CheckEmailAvailable(user))){
                        request.setAttribute("message", Customer.CheckPhoneAvailable(user));
                        request.getRequestDispatcher("/admin/customer-form.jsp").forward(request, response);
                    }
//                  Nếu email và điện thoại là mới
                    else {
                        UserDAO.save(user);
                        String message = new String("Vừa thêm khách hàng <b>" + user.getName() + "</b>");
                        request.setAttribute("message", message);
                        List<UserEntity> customerList = Customer.getAll();
                        request.setAttribute("customerList", customerList);
                        request.getRequestDispatcher("/admin/customer.jsp").forward(request, response);
                    }
                }
//                Đang cập nhật sách
                else {
                    String message = new String("Vừa xem thông tin khách hàng <b>" + user.getName() + "</b>");
                    request.setAttribute("message", message);
                    List<UserEntity> customerList = Customer.getAll();
                    request.setAttribute("customerList", customerList);
                    request.getRequestDispatcher("/admin/customer.jsp").forward(request, response);
                }
            }
        }
    }
}
