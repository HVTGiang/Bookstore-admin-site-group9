package com.book.admin.business;

import com.book.dao.OrderDAO;
import com.book.dao.PaymentDAO;
import com.book.entity.Delivery;
import com.book.entity.Order;
import com.book.entity.Paymethod;

import java.util.List;

public class PaymethodBS {
    public static String CheckNameAvailable(Paymethod paymethod) {
        List<Paymethod> foundedList = PaymentDAO.find(paymethod.getName());
        if (foundedList.size() >= 1) {
            return "<b>Tên phương thức đã tồn tại</b>! Vui lòng nhập tên khác";
        }
        return "OK";
    }

    public static int totalOrder(Paymethod paymethod) {
        List<Order> orderByPaymethodId= OrderDAO.getOrderByIdMethod(paymethod.getId());
        if (orderByPaymethodId != null){
            return orderByPaymethodId.size();
        }
        return 0;
    }
}
