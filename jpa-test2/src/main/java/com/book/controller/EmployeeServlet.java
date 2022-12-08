package com.book.controller;

import com.book.business.Customer;
import com.book.business.Employee;
import com.book.business.Product;
import com.book.dao.CategoryDAO;
import com.book.dao.ProductDAO;
import com.book.dao.UserDAO;
import com.book.entity.CategoryEntity;
import com.book.entity.ProductEntity;
import com.book.entity.UserEntity;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "EmployeeServlet", value = "/admin/employee")
public class EmployeeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        Thêm tiếng việt
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        List<UserEntity> employeeList = Employee.getAll();
        request.setAttribute("employeeList", employeeList);

        UserEntity BestEmployeeByPrice = Employee.BestEmployeeByPrice();
        request.setAttribute("BestEmployeeByPrice", BestEmployeeByPrice);

        UserEntity BestEmployeeByBooks = Employee.BestEmployeeByBooks();
        request.setAttribute("BestEmployeeByBooks", BestEmployeeByBooks);

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
                request.getRequestDispatcher("/admin/employee.jsp").forward(request, response);
                break;
            default:
                request.getRequestDispatcher("/admin/employee.jsp").forward(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        List<UserEntity> employeeList = Employee.getAll();
        request.setAttribute("employeeList", employeeList);

        UserEntity BestEmployeeByPrice = Employee.BestEmployeeByPrice();
        request.setAttribute("BestEmployeeByPrice", BestEmployeeByPrice);

        UserEntity BestEmployeeByBooks = Employee.BestEmployeeByBooks();
        request.setAttribute("BestEmployeeByBooks", BestEmployeeByBooks);
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
                request.getRequestDispatcher("/admin/employee.jsp").forward(request, response);
                break;
            default:
                request.getRequestDispatcher("/admin/employee.jsp").forward(request, response);
                break;
        }
    }

    protected void actionInsert(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        request.setAttribute("action", "insert");
        request.getRequestDispatcher("/admin/employee-form.jsp").forward(request, response);
    }

    protected void actionEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        request.setAttribute("action", "edit");
        String eID = request.getParameter("employeeID");
//        Lấy employee có id tương ứng ra
        UserEntity selectedEmployee = UserDAO.find(Integer.parseInt(eID));
        request.setAttribute("employee", selectedEmployee);
        request.getRequestDispatcher("/admin/employee-form.jsp").forward(request, response);
    }

    protected void actionSave(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        String eID = request.getParameter("eID");
        String eName = request.getParameter("employeeName");
        String eAddress = request.getParameter("employeeAddress");
        String eEmail = request.getParameter("employeeEmail");
        String ePhone = request.getParameter("employeePhone");
        String ePassword = request.getParameter("employeePassword");
        String eImageLink = request.getParameter("employeeImageLink");


//        Kiểm tra người dùng có nhập dữ liệu vào hay chưa
        if (!eName.trim().equals("") && !eAddress.trim().equals("") && !eEmail.trim().equals("") && !ePhone.trim().equals("")) {
//            Thực hiện kiểm tra dữ liệu vào

//            Tạo một đối tượng Product để lưu dữ liệu
            UserEntity user = new UserEntity();
            if (!eID.equals("")) {
                user.setId(Integer.parseInt(eID));
            }
            user.setName(eName);
            user.setAddress(eAddress);
            user.setEmail(eEmail);
            user.setPhone(ePhone);
            user.setPassword(ePassword);
            user.setImage(eImageLink);
            user.setIsRole(2);

//            Kiểm tra dữ liệu đầu vào đang được lưu trong book
//            Nêu dữ liệu chưa hợp lệ
            if (!"OK".equals(Employee.CheckInputData(user))) {
                request.setAttribute("employee", user);
                request.setAttribute("message", Employee.CheckInputData(user));
                request.getRequestDispatcher("/admin/employee-form.jsp").forward(request, response);
            }
//            Nếu dữ liệu hợp lệ
            else {
//              Kiểm tra xem có ID chưa, nếu chưa là thêm mới, nếu có là cập nhật
//              Đang thêm nhân viên mới
                if (user.getId() == 0) {
//                  Kiểm tra xem một số thông tin phải là unique
//                  Nếu số điện thoại đã tồn tại
                    if (!"OK".equals(Employee.CheckPhoneAvailable(user))) {
                        request.setAttribute("employee", user);
                        request.setAttribute("message", Employee.CheckPhoneAvailable(user));
                        request.getRequestDispatcher("/admin/employee-form.jsp").forward(request, response);
                    }
//                    Nếu email đã tồn tại
                    else if (!"OK".equals(Employee.CheckEmailAvailable(user))) {
                        request.setAttribute("employee", user);
                        request.setAttribute("message", Employee.CheckPhoneAvailable(user));
                        request.getRequestDispatcher("/admin/employee-form.jsp").forward(request, response);
                    }
//                  Nếu email và điện thoại là mới
                    else {
                        UserDAO.save(user);
                        String message = new String("Vừa thêm nhân viên <b>" + user.getName() + "</b>");
                        request.setAttribute("message", message);
                        List<UserEntity> employeeList = Employee.getAll();
                        request.setAttribute("employeeList", employeeList);
                        request.getRequestDispatcher("/admin/employee.jsp").forward(request, response);
                    }
                }
//                Đang cập nhật sách
                else {
                    UserDAO.update(user);
                    String message = new String("Vừa cập nhật nhân viên <b>" + user.getName() + "</b>");
                    request.setAttribute("message", message);
                    List<UserEntity> employeeList = Employee.getAll();
                    request.setAttribute("employeeList", employeeList);
                    request.getRequestDispatcher("/admin/employee.jsp").forward(request, response);
                }
            }
        }
    }

    protected void actionDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        Thêm tiếng việt
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
//        Lấy id của product được truyền xuống nè
        String eID = request.getParameter("employeeID");
//        Lấy product có id tương ứng ra
        UserEntity selectedUser = UserDAO.find(Integer.parseInt(eID));
        String message;
        if (selectedUser != null && Employee.totalOrder(selectedUser) == 0) {
            message = new String("Vừa xóa nhân viên <b>" + selectedUser.getName() + "</b>");
            selectedUser.setActive(true);
            UserDAO.update(selectedUser);
        } else {
            message = new String("Nhân viên <b>" + selectedUser.getName() + "</b> hong thể xóa do có dữ liệu cần dùng!");
        }
        request.setAttribute("message", message);

        List<UserEntity> employeeList = Employee.getAll();
        request.setAttribute("employeeList", employeeList);

        UserEntity BestEmployeeByPrice = Employee.BestEmployeeByPrice();
        request.setAttribute("BestEmployeeByPrice", BestEmployeeByPrice);

        UserEntity BestEmployeeByBooks = Employee.BestEmployeeByBooks();
        request.setAttribute("BestEmployeeByBooks", BestEmployeeByBooks);

        request.getRequestDispatcher("/admin/employee.jsp").forward(request, response);
    }
}
