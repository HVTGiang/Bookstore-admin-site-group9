package com.book.controller;

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
import java.util.Objects;

@WebServlet(name = "BookServlet", value = "/admin/book")
public class BookServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<ProductEntity> bookList = ProductDAO.getAll();
        List<CategoryEntity> categoryEntityList = CategoryDAO.getAll();
        int totalBook=Product.totalBook(bookList);
        request.setAttribute("total",totalBook);

        int sellBook=Product.sellBook(bookList);
        request.setAttribute("sell",sellBook);

        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        request.setAttribute("bookList", bookList);
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
                actionHome(request, response);
                break;
            default:
                request.getRequestDispatcher("/admin/book.jsp").forward(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<ProductEntity> bookList = ProductDAO.getAll();
        int totalBook=Product.totalBook(bookList);
        request.setAttribute("total",totalBook);
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        request.setAttribute("bookList", bookList);
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
                actionHome(request, response);
                break;
            default:
                request.getRequestDispatcher("/admin/book.jsp").forward(request, response);
                break;
        }
    }

    protected void actionInsert(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<CategoryEntity> categoryEntityList = CategoryDAO.getAll();
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        request.setAttribute("action", "insert");
        request.setAttribute("categoryList", categoryEntityList);
        request.getRequestDispatcher("/admin/book-form.jsp").forward(request, response);
    }

    protected void actionSave(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        List<CategoryEntity> categoryEntityList = CategoryDAO.getAll();
        request.setAttribute("categoryList", categoryEntityList);

        String pID = request.getParameter("pID");
        String pName = request.getParameter("bookName");
        String pOriginalPrice = request.getParameter("bookOriginalPrice");
        String pSalePrice = request.getParameter("bookSalePrice");
        String pDescription = request.getParameter("bookDescription");
        String pQuantity = request.getParameter("bookQuantity");
        String pCategoryID = request.getParameter("bookCategoryID");
        String pImageLink = request.getParameter("bookImageLink");


//        Kiểm tra người dùng có nhập dữ liệu vào hay chưa
        if (!pName.trim().equals("") && !pOriginalPrice.trim().equals("") && !pSalePrice.trim().equals("") && !pQuantity.trim().equals("")) {
//            Thực hiện kiểm tra dữ liệu vào

//            Tạo một đối tượng Product để lưu dữ liệu
            ProductEntity book = new ProductEntity();
            if (!pID.equals("")) {
                book.setId(Integer.parseInt(pID));
            }
            book.setName(pName);
            book.setOriginalPrice(Integer.parseInt(pOriginalPrice));
            book.setSalePrice(Integer.parseInt(pSalePrice));
            book.setDiscription(pDescription);
            book.setQuantity(Integer.parseInt(pQuantity));
            book.setIdCategory(Integer.parseInt(pCategoryID));
            book.setImage(pImageLink);

//            Kiểm tra dữ liệu đầu vào đang được lưu trong book
//            Nêu dữ liệu chưa hợp lệ
            if (!"OK".equals(Product.CheckInputData(book))) {
                categoryEntityList = CategoryDAO.getAll();
                request.setAttribute("book", book);
                request.setAttribute("categoryList", categoryEntityList);
                request.setAttribute("message", Product.CheckInputData(book));
                request.getRequestDispatcher("/admin/book-form.jsp").forward(request, response);
            }
//            Nếu dữ liệu hợp lệ
            else {
//              Kiểm tra xem có ID chưa, nếu chưa là thêm mới, nếu có là cập nhật
//              Đang thêm sách mới
                if (book.getId() == 0) {
//                  Kiểm tra xem tên có chưa
//                  Nếu tên sách đã tồn tại
                    if (!"OK".equals(Product.CheckNameAvailable(book))) {
                        categoryEntityList = CategoryDAO.getAll();
                        request.setAttribute("book", book);
                        request.setAttribute("categoryList", categoryEntityList);
                        request.setAttribute("message", Product.CheckNameAvailable(book));
                        request.getRequestDispatcher("/admin/book-form.jsp").forward(request, response);
                    }
//                  Nếu tên chưa tồn tại
                    else {
                        ProductDAO.save(book);
                        String message = new String("Vừa thêm sách <b>" + book.getName() + "</b>");
                        request.setAttribute("message", message);
                        List<ProductEntity> bookList = ProductDAO.getAll();
                        int totalBook=Product.totalBook(bookList);
                        request.setAttribute("total",totalBook);
                        request.setAttribute("bookList", bookList);
                        request.getRequestDispatcher("/admin/book.jsp").forward(request, response);
                    }
                }
//                Đang cập nhật sách
                else {
                    ProductDAO.update(book);
                    String message = new String("Vừa cập nhật sách <b>" + book.getName() + "</b>");
                    request.setAttribute("message", message);
                    List<ProductEntity> bookList = ProductDAO.getAll();
                    request.setAttribute("bookList", bookList);
                    int totalBook=Product.totalBook(bookList);
                    request.setAttribute("total",totalBook);
                    request.getRequestDispatcher("/admin/book.jsp").forward(request, response);
                }
            }
        }
    }

    protected void actionEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        Thêm tiếng việt
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
//        Lấy id của product được truyền xuống nè
        String pID = request.getParameter("productID");
//        Lấy product có id tương ứng ra
        ProductEntity selectedBook = ProductDAO.findById(Integer.parseInt(pID));
        request.setAttribute("book", selectedBook);
        request.setAttribute("action", "edit");

        List<CategoryEntity> categoryEntityList = CategoryDAO.getAll();
        request.setAttribute("categoryList", categoryEntityList);
        request.getRequestDispatcher("/admin/book-form.jsp").forward(request, response);
    }

    protected void actionDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        Thêm tiếng việt
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
//        Lấy id của product được truyền xuống nè
        String pID = request.getParameter("productID");
//        Lấy product có id tương ứng ra
        ProductEntity selectedBook = ProductDAO.findById(Integer.parseInt(pID));
        String message;
        if (selectedBook != null) {
            message = new String("Vừa xóa sách <b>" + selectedBook.getName() + "</b>");
            selectedBook.setActive(true);
            ProductDAO.update(selectedBook);
        } else {
            message = new String("Sách <b>" + selectedBook.getName() + "</b> hong tồn tại!");
        }
        request.setAttribute("message", message);
        List<ProductEntity> bookList = ProductDAO.getAll();
        request.setAttribute("bookList", bookList);
        int totalBook=Product.totalBook(bookList);
        request.setAttribute("total",totalBook);
        List<CategoryEntity> categoryEntityList = CategoryDAO.getAll();
        request.setAttribute("categoryList", categoryEntityList);
        request.getRequestDispatcher("/admin/book.jsp").forward(request, response);
    }

    protected void actionHome(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        Thêm tiếng việt
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
//        Lấy id của category được truyền xuống nè
        String cID = request.getParameter("cID");
        CategoryEntity selectedCategory=new CategoryEntity();
        List<CategoryEntity> categoryEntityList = CategoryDAO.getAll();
        request.setAttribute("categoryList", categoryEntityList);
        if(cID==null){
            cID="0";
        }
//        Lấy product có id tương ứng ra
        if(cID.equals("0")){
            List<ProductEntity> bookList = ProductDAO.getAll();
            request.setAttribute("bookList", bookList);
            selectedCategory.setName("Toàn bộ sách");
            request.setAttribute("category", selectedCategory);
            int totalBook=Product.totalBook(bookList);
            request.setAttribute("total",totalBook);
            int sellBook=Product.sellBook(bookList);
            request.setAttribute("sell",sellBook);
            request.getRequestDispatcher("/admin/book.jsp").forward(request, response);
        }
        else{
            selectedCategory = CategoryDAO.findCategory(Integer.parseInt(cID));
            List<ProductEntity> bookList = ProductDAO.findByCategoryID(Integer.parseInt(cID));
            request.setAttribute("bookList", bookList);
            request.setAttribute("category", selectedCategory);
            int totalBook=Product.totalBook(bookList);
            request.setAttribute("total",totalBook);
            int sellBook=Product.sellBook(bookList);
            request.setAttribute("sell",sellBook);
            request.getRequestDispatcher("/admin/book.jsp").forward(request, response);
        }
    }
}
