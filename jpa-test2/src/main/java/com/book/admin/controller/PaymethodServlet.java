package com.book.admin.controller;

import com.book.admin.business.DeliveryBS;
import com.book.admin.business.PaymethodBS;
import com.book.dao.DeliveryDAO;
import com.book.dao.PaymentDAO;
import com.book.entity.Delivery;
import com.book.entity.Paymethod;

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
        List<Paymethod> paymethodList = PaymentDAO.getAll();
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
        List<Paymethod> paymethodList = PaymentDAO.getAll();
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
            case "delete":
                actionDelete(request, response);
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
        Paymethod selectedPaymethod = PaymentDAO.find(Integer.parseInt(pID));
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
            Paymethod paymethod = new Paymethod();
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
                if (!"OK".equals(PaymethodBS.CheckNameAvailable(paymethod))) {
                    request.setAttribute("message", PaymethodBS.CheckNameAvailable(paymethod));
                    request.getRequestDispatcher("/admin/paymethod-form.jsp").forward(request, response);
                }
//              Nếu là tên mới
                else {
                    PaymentDAO.save(paymethod);
                    String message = new String("Vừa thêm phương thức thanh toán <b>" + paymethod.getName() + "</b>");
                    request.setAttribute("message", message);
                    List<Paymethod> paymethodList = PaymentDAO.getAll();
                    request.setAttribute("paymethodList", paymethodList);
                    request.getRequestDispatcher("/admin/paymethod.jsp").forward(request, response);
                }
            }
//          Đang cập nhật sách
            else {
                String message = new String("Vừa xem thông tin khách hàng <b>" + paymethod.getName() + "</b>");
                request.setAttribute("message", message);
                List<Paymethod> paymethodList = PaymentDAO.getAll();
                request.setAttribute("paymethodList", paymethodList);
                request.getRequestDispatcher("/admin/customer.jsp").forward(request, response);
            }
        }
    }

    protected void actionDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        Thêm tiếng việt
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
//        Lấy id của delivery được truyền xuống nè
        String pID = request.getParameter("paymethodID");
//        Lấy product có id tương ứng ra
        Paymethod selectedMethod = PaymentDAO.find(Integer.parseInt(pID));
        String message;

        if (PaymethodBS.totalOrder(selectedMethod) == 0) {
            message = new String("Vừa xóa phương thức <b>" + selectedMethod.getName() + "</b>");
            PaymentDAO.delete(selectedMethod);
        } else {
            message = new String("Phương thức <b>" + selectedMethod.getName() + "</b> hong thể xóa do có dữ liệu cần dùng!");
        }
        request.setAttribute("message", message);

        List<Paymethod> paymethodList = PaymentDAO.getAll();
        request.setAttribute("paymethodList", paymethodList);

        request.getRequestDispatcher("/admin/paymethod.jsp").forward(request, response);
    }
}
