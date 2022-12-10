package com.book.admin.business;

import com.book.dao.ProductDAO;
import com.book.entity.Orderitem;
import com.book.entity.Product;

import java.util.ArrayList;
import java.util.List;

public class OrderItemBS {
    public static List<Orderitem> finalOrderItemList(List<Orderitem> itemList) {
        List<Orderitem> finalList = new ArrayList<>();
        for (Orderitem item : itemList) {
            Product product = ProductDAO.getProductByID(item.getIdProduct());
            item.setProductByIdProduct(product);
            finalList.add(item);
        }
        return finalList;
    }
}
