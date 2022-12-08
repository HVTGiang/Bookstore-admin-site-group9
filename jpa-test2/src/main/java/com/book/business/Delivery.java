package com.book.business;

import com.book.dao.DeliveryDAO;
import com.book.dao.PayDAO;
import com.book.entity.DeliveryEntity;
import com.book.entity.PaymethodEntity;
import com.book.entity.UserEntity;

import java.util.List;

public class Delivery {
    public static String CheckInputData(DeliveryEntity delivery) {
        if (delivery.getShipFee() <= 0) {
            return "Vui lòng <b>tiền ship dương</b>!";
        }
        if(!"OK".equals(Delivery.CheckNameAvailable(delivery))){
            return "<b>Tên phương thức đã tồn tại</b>! Vui lòng nhập tên khác";
        }
        return "OK";
    }
    public static String CheckNameAvailable(DeliveryEntity delivery) {
        List<DeliveryEntity> foundedList = DeliveryDAO.find(delivery.getName());
        if (foundedList.size() >= 1) {
            return "<b>Tên phương thức đã tồn tại</b>! Vui lòng nhập tên khác";
        }
        return "OK";
    }
}
