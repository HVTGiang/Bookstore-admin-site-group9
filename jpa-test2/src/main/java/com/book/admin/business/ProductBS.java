package com.book.admin.business;

import com.book.dao.OrderDAO;
import com.book.dao.OrderItemDAO;
import com.book.dao.ProductDAO;
import com.book.entity.Order;
import com.book.entity.Orderitem;
import com.book.entity.Product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductBS {
    public static String CheckInputData(Product book) {
        if (book.getOriginalPrice() < 0) {
            return "Vui lòng nhập <b>số tiền nhập dương</b>!";
        }
        if (book.getSalePrice() < 0) {
            return "Vui lòng nhập <b>số tiền bán dương</b>!";
        }
        if (book.getSalePrice() < book.getOriginalPrice()) {
            return "Vui lòng nhập <b>số tiền bán lớn hơn tiền nhập sách</b>! (Tính bán vì đam mê hay gì :>>)";
        }
        if (book.getQuantity() < 0) {
            return "Vui lòng nhập <b>số sách dương!</b>";
        }
        if (book.getIdCategory() == 0) {
            return "Vui lòng <b>chọn loại sách</b>!";
        }
        return "OK";
    }

    public static String CheckNameAvailable(Product book) {
        List<Product> foundedList = ProductDAO.searchByname(book.getName());
        if (foundedList.size() == 1) {
            return "Tên sách <b>đã tồn tại</b>! Vui lòng nhập tên khác";
        }
        return "OK";
    }

    public static int totalBook(List<Product> books) {
        int total = 0;
        for (Product book : books) {
            total += book.getQuantity();
        }
        return total;
    }

//    Đếm số sac bán được của một danh sách các sản phẩm nào đó (thường là cho từng loại sách)
    public static int sellBook(List<Product> books) {
        int total = 0;
        for (Product book : books) {
            for (Order order : OrderDAO.getAll()) {
                if (order.getStatus() == 1 || order.getStatus() == 5) {
                    continue;
                }
                for (Orderitem item : OrderItemDAO.orderItemList(order.getId())) {
                    if (item.getIdProduct() == book.getId()) {
                        total += item.getQuantity();
                    }
                }
            }
        }
        return total;
    }

    //    Hàm tính tổng số order mà cuốn sách này đang nằm trong đó
    public static int totalOrder(Product product) {
        List<Orderitem> orderItemOFThisProduct = OrderItemDAO.orderItemListByProduct(product.getId());
        List<Order> orderEntities = new ArrayList<>();
        if (orderItemOFThisProduct.size() > 0) {
            for (Orderitem item : orderItemOFThisProduct) {
                orderEntities.add(OrderDAO.getOrderByIdOrder(item.getIdOrder()));
            }
        }
        return orderEntities.size();
    }

    public static Product BestSellerBook() {
        class Temp implements Comparable<Temp> {
            public int id;
            public int quantity;

            Temp() {
            }

            ;

            public Temp(int idValue, int quantityValue) {
                id = idValue;
                quantity = quantityValue;
            }


            @Override
            public int compareTo(Temp o) {
                if (quantity == o.quantity)
                    return 0;
                else if (quantity > o.quantity)
                    return 1;
                else
                    return -1;
            }
        }
        List<Product> books = ProductDAO.getAll();
        List<Temp> topBook = new ArrayList<>();
        for (Product book : books) {
            List<Orderitem> orderItems = OrderItemDAO.getAll();
            if (orderItems.size() >= 1) {
                Temp tempBook = new Temp();
                tempBook.id = book.getId();
                for (Orderitem orderItem : orderItems) {
                    if (orderItem.getIdProduct() == book.getId() && OrderDAO.getOrderByIdOrder(orderItem.getIdOrder()).getStatus() != 5) {
                        tempBook.quantity += orderItem.getQuantity();
                    }
                }
                topBook.add(tempBook);
            }
        }
        if (topBook.size() > 0) {
            Collections.sort(topBook);
            return ProductDAO.getProductByID(topBook.get(topBook.size() - 1).id);
        }
        return null;
    }
}
