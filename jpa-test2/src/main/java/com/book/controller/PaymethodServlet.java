package com.book.controller;

import com.book.business.Customer;
import com.book.business.Employee;
import com.book.business.Paymethod;
import com.book.dao.PayDAO;
import com.book.dao.UserDAO;
import com.book.entity.PaymethodEntity;
import com.book.entity.UserEntity;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "PaymethodServlet", value = "/admin/paymethod")
public class PaymethodServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        Thêm tiếng việt
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        List<PaymethodEntity> paymethodList = PayDAO.getAll();
        request.setAttribute("paymethodList", paymethodList);

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
                request.getRequestDispatcher("/admin/paymethod.jsp").forward(request, response);
                break;
            default:
                request.getRequestDispatcher("/admin/paymethod.jsp").forward(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        Thêm tiếng việt
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        List<PaymethodEntity> paymethodList = PayDAO.getAll();
        request.setAttribute("paymethodList", paymethodList);

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
                request.getRequestDispatcher("/admin/paymethod.jsp").forward(request, response);
                break;
            default:
                request.getRequestDispatcher("/admin/paymethod.jsp").forward(request, response);
                break;
        }
    }

    protected void actionInsert(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        request.setAttribute("action", "insert");
        request.getRequestDispatcher("/admin/paymethod-form.jsp").forward(request, response);
    }

    protected void actionEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        request.setAttribute("action", "edit");
        String pID = request.getParameter("paymethodID");
//        Lấy employee có id tương ứng ra
        PaymethodEntity selectedPaymethod = PayDAO.find(Integer.parseInt(pID));
        request.setAttribute("paymethod", selectedPaymethod);
        request.getRequestDispatcher("/admin/paymethod-form.jsp").forward(request, response);
    }

    protected void actionSave(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        String pID = request.getParameter("pID");
        String pName = request.getParameter("paymethodName");

//        Kiểm tra người dùng có nhập dữ liệu vào hay chưa
        if (!pName.trim().equals("")) {
//            Thực hiện kiểm tra dữ liệu vào

//            Tạo một đối tượng Product để lưu dữ liệu
            PaymethodEntity paymethod = new PaymethodEntity();
            if (!pID.equals("")) {
                paymethod.setId(Integer.parseInt(pID));
            }
            paymethod.setName(pName);

//          Kiểm tra xem có ID chưa, nếu chưa là thêm mới, nếu có là cập nhật
//          Đang thêm nhân viên mới
            if (paymethod.getId() == 0) {
                request.setAttribute("action", "insert");
                request.setAttribute("paymethod", paymethod);
//                  Kiểm tra xem một số thông tin phải là unique
//              Nếu số tên đã tồn tại
                if (!"OK".equals(Paymethod.CheckNameAvailable(paymethod))) {
                    request.setAttribute("message", Paymethod.CheckNameAvailable(paymethod));
                    request.getRequestDispatcher("/admin/paymethod-form.jsp").forward(request, response);
                }
//              Nếu là tên mới
                else {
                    PayDAO.save(paymethod);
                    String message = new String("Vừa thêm phương thức thanh toán <b>" + paymethod.getName() + "</b>");
                    request.setAttribute("message", message);
                    List<PaymethodEntity> paymethodList = PayDAO.getAll();
                    request.setAttribute("paymethodList", paymethodList);
                    request.getRequestDispatcher("/admin/paymethod.jsp").forward(request, response);
                }
            }
//          Đang cập nhật sách
            else {
                String message = new String("Vừa xem thông tin khách hàng <b>" + paymethod.getName() + "</b>");
                request.setAttribute("message", message);
                List<PaymethodEntity> paymethodList = PayDAO.getAll();
                request.setAttribute("paymethodList", paymethodList);
                request.getRequestDispatcher("/admin/customer.jsp").forward(request, response);
            }
        }
    }
}
