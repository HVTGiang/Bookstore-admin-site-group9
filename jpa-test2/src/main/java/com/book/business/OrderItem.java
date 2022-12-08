package com.book.business;

import com.book.dao.ProductDAO;
import com.book.entity.OrderitemEntity;
import com.book.entity.ProductEntity;

import java.util.ArrayList;
import java.util.List;

public class OrderItem {
    public static List<OrderitemEntity> finalOrderItemList(List<OrderitemEntity> itemList) {
        List<OrderitemEntity> finalList = new ArrayList<>();
        for (OrderitemEntity item : itemList) {
            ProductEntity product = ProductDAO.findById(item.getIdProduct());
            item.setProductByIdProduct(product);
            finalList.add(item);
        }
        return finalList;
    }
}
