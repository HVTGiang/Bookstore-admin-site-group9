package com.book.business;

import com.book.dao.OrderDAO;
import com.book.dao.OrderItemDAO;
import com.book.dao.ProductDAO;
import com.book.dao.UserDAO;
import com.book.entity.OrderEntity;
import com.book.entity.OrderitemEntity;
import com.book.entity.ProductEntity;
import com.book.entity.UserEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Product {
    public static String CheckInputData(ProductEntity book) {
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

    public static String CheckNameAvailable(ProductEntity book) {
        List<ProductEntity> foundedList = ProductDAO.findByName(book.getName());
        if (foundedList.size() == 1) {
            return "Tên sách <b>đã tồn tại</b>! Vui lòng nhập tên khác";
        }
        return "OK";
    }

    public static int totalBook(List<ProductEntity> books) {
        int total = 0;
        for (ProductEntity book : books) {
            total += book.getQuantity();
        }
        return total;
    }

    public static int sellBook(List<ProductEntity> books) {
        int total = 0;
        for (ProductEntity book : books) {
            for (OrderEntity order : OrderDAO.getAll()) {
                if (order.getStatus() == 1) {
                    continue;
                }
                for (OrderitemEntity item : OrderItemDAO.orderItemList(order.getId())) {
                    if (item.getIdProduct() == book.getId()) {
                        total += item.getQuantity();
                    }
                }
            }
        }
        return total;
    }

    //    Hàm tính tổng số order mà cuốn sách này đang nằm trong đó
    public static int totalOrder(ProductEntity product) {
        List<OrderitemEntity> orderItemOFThisProduct = OrderItemDAO.orderItemListByProduct(product.getId());
        List<OrderEntity> orderEntities = new ArrayList<>();
        if (orderItemOFThisProduct.size() > 0) {
            for (OrderitemEntity item : orderItemOFThisProduct) {
                orderEntities.add(OrderDAO.find(item.getIdOrder()));
            }
        }
        return orderEntities.size();
    }

    public static ProductEntity BestSellerBook() {
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
        List<ProductEntity> books = ProductDAO.getAll();
        List<Temp> topBook = new ArrayList<>();
        for (ProductEntity book : books) {
            List<OrderitemEntity> orderItems = OrderItemDAO.getAll();
            if (orderItems.size() >= 1) {
                Temp tempBook = new Temp();
                tempBook.id = book.getId();
                for (OrderitemEntity orderItem : orderItems) {
                    if (orderItem.getIdProduct() == book.getId() && OrderDAO.find(orderItem.getIdOrder()).getStatus() != 5) {
                        tempBook.quantity += orderItem.getQuantity();
                    }
                }
                topBook.add(tempBook);
            }
        }
        if (topBook.size() > 0) {
            Collections.sort(topBook);
            return ProductDAO.findById(topBook.get(topBook.size() - 1).id);
        }
        return null;
    }
}
