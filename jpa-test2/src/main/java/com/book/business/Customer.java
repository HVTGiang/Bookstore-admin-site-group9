package com.book.business;

import com.book.dao.OrderDAO;
import com.book.dao.OrderItemDAO;
import com.book.dao.UserDAO;
import com.book.entity.OrderEntity;
import com.book.entity.OrderitemEntity;
import com.book.entity.UserEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Customer {
    public static List<UserEntity> getAll() {
        List<UserEntity> customers = UserDAO.findByRole(3); // 3 - customer
        return customers;
    }

    public static String CheckInputData(UserEntity user) {
        if (user.getPhone().length() < 10) {
            return "Vui lòng <b>nhập số điện thoại có 10 số</b>!";
        }
        if (!isPhoneNumber(user.getPhone())) {
            return "Vui lòng <b>nhập định dạng đúng của số điện thoại</b>!";
        }
        return "OK";
    }

    private static boolean isPhoneNumber(String phone) {
        for (int i = 0; i < phone.length(); i++) {
            if (Character.isLetter(phone.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static String CheckPhoneAvailable(UserEntity user) {
        List<UserEntity> foundedList = UserDAO.findByPhone(user.getPhone());
        if (foundedList.size() >= 1) {
            return "<b>SĐT đã tồn tại</b>! Vui lòng nhập SĐT khác";
        }
        return "OK";
    }

    public static String CheckEmailAvailable(UserEntity user) {
        List<UserEntity> foundedList = UserDAO.findByEmail(user.getEmail());
        if (foundedList.size() >= 1) {
            return "<b>Email đã tồn tại</b>! Vui lòng nhập email khác";
        }
        return "OK";
    }

    public static UserEntity BestCustomerByPrice() {
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
        List<UserEntity> customers = getAll();
        List<Temp> topCustomer = new ArrayList<>();
        for (UserEntity cus : customers) {
            List<OrderEntity> orderOFThisCustomer = OrderDAO.findByCustomer(cus.getId());
            if (orderOFThisCustomer.size() >= 1) {
                Temp tempCustomer = new Temp(cus.getId(), 0);
                for (OrderEntity order : orderOFThisCustomer) {
                    tempCustomer.quantity += order.getTotalPay();
                }
                topCustomer.add(tempCustomer);
            }
        }
        if (topCustomer.size() > 0) {
            Collections.sort(topCustomer);
            return UserDAO.find(topCustomer.get(topCustomer.size()-1).id);
        }
        return null;
    }

    public static UserEntity BestCustomerByBooks() {
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
        List<UserEntity> customers = getAll();
        List<Temp> topCustomer = new ArrayList<>();
        for (UserEntity cus : customers) {
            List<OrderEntity> orderOFThisCustomer = OrderDAO.findByCustomer(cus.getId());
            if (orderOFThisCustomer.size() >= 1) {
                Temp tempCustomer = new Temp(cus.getId(), 0);
                for (OrderEntity order : orderOFThisCustomer) {
                    List<OrderitemEntity> orderitemEntityList = OrderItemDAO.orderItemList(order.getId());
                    for (OrderitemEntity item : orderitemEntityList) {
                        tempCustomer.quantity+=item.getQuantity();
                    }
                }
                topCustomer.add(tempCustomer);
            }
        }
        if (topCustomer.size() > 0) {
            Collections.sort(topCustomer);
            return UserDAO.find(topCustomer.get(topCustomer.size()-1).id);
        }
        return null;
    }

    public static int totalOrder(UserEntity user) {
        List<OrderEntity> orderOFThisCustomer = OrderDAO.findByCustomer(user.getId());
        if (orderOFThisCustomer.size() > 0) {
            return orderOFThisCustomer.size();
        }
        return 0;
    }
}
