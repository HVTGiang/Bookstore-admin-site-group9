package com.book.business;

import com.book.entity.OrderEntity;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Order {
    public static List<OrderStatus> statusList() {
        List<OrderStatus> statuses = new ArrayList<>();
        OrderStatus st1 = new OrderStatus(1, "Chờ xác nhận");
        OrderStatus st2 = new OrderStatus(2, "Chờ lấy hàng");
        OrderStatus st3 = new OrderStatus(3, "Đang giao");
        OrderStatus st4 = new OrderStatus(4, "Đã giao");
        OrderStatus st5 = new OrderStatus(5, "Đã hủy");
        statuses.add(st1);
        statuses.add(st2);
        statuses.add(st3);
        statuses.add(st4);
        statuses.add(st5);
        return statuses;
    }

    public static Date estimateReceiveDate(OrderEntity order) {
        Date dt = order.getCreateTime();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dt);
        int delivery = order.getIdDelivery();
        int deleveryTime = 0;
        switch (delivery) {
            case 1:
                deleveryTime = 3;
                break;
            case 2:
                deleveryTime = 2;
                break;
            default:
                deleveryTime = 3;
                break;
        }
        calendar.add(Calendar.DATE, deleveryTime);
        String newYear = String.valueOf(calendar.get(Calendar.YEAR));
        String newMonth = String.valueOf(calendar.get(Calendar.MONTH)+1);
        String newDate = String.valueOf(calendar.get(Calendar.DATE));
        String receiveDate = newYear + "-" + newMonth + "-" + newDate;
        Date newdt = Date.valueOf(receiveDate);
        return newdt;
    }

    public static OrderStatus findStatusByID(int id) {
        OrderStatus selectedStatus = null;
        for (OrderStatus status : statusList()) {
            if (status.getId() == id) {
                selectedStatus = status;
            }
        }
        return selectedStatus;
    }

}
