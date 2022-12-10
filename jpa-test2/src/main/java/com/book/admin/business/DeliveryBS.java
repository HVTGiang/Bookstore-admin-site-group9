package com.book.admin.business;

import com.book.dao.DeliveryDAO;
import com.book.dao.OrderDAO;
import com.book.dao.OrderItemDAO;
import com.book.entity.Delivery;
import com.book.entity.Order;
import com.book.entity.Orderitem;
import com.book.entity.Product;

import java.util.ArrayList;
import java.util.List;

public class DeliveryBS {
    public static String CheckInputData(Delivery delivery) {
        if (delivery.getShipFee() <= 0) {
            return "Vui lòng <b>tiền ship dương</b>!";
        }
        if(!"OK".equals(DeliveryBS.CheckNameAvailable(delivery))){
            return "<b>Tên phương thức đã tồn tại</b>! Vui lòng nhập tên khác";
        }
        return "OK";
    }
    public static String CheckNameAvailable(Delivery delivery) {
        List<Delivery> foundedList = DeliveryDAO.getDeliveryByName(delivery.getName());
        if (foundedList.size() >= 1) {
            return "<b>Tên phương thức đã tồn tại</b>! Vui lòng nhập tên khác";
        }
        return "OK";
    }

    public static int totalOrder(Delivery delivery) {
        List<Order> orderByDeliveryId=OrderDAO.getOrderByIdDelivery(delivery.getId());
        if (orderByDeliveryId != null){
            return orderByDeliveryId.size();
        }
        return 0;
    }
}
