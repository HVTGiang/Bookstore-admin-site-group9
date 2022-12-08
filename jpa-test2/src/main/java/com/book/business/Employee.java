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

public class Employee {
    public static List<UserEntity> getAll() {
        List<UserEntity> employees = UserDAO.findByRole(2); // 2 - employee
        return employees;
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
        if (foundedList.size() == 1) {
            return "<b>SĐT đã tồn tại</b>! Vui lòng nhập SĐT khác";
        }
        return "OK";
    }

    public static String CheckEmailAvailable(UserEntity user) {
        List<UserEntity> foundedList = UserDAO.findByEmail(user.getEmail());
        if (foundedList.size() == 1) {
            return "<b>Email đã tồn tại</b>! Vui lòng nhập email khác";
        }
        return "OK";
    }

    public static UserEntity BestEmployeeByPrice() {
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
        List<UserEntity> employees = getAll();
        List<Temp> topEmployee = new ArrayList<>();
        for (UserEntity emp : employees) {
            List<OrderEntity> orderOFThisEmployee = OrderDAO.findByEmployee(emp.getId());
            if (orderOFThisEmployee.size() >= 1) {
                Temp tempCustomer = new Temp(emp.getId(), 0);
                for (OrderEntity order : orderOFThisEmployee) {
                    tempCustomer.quantity += order.getTotalPay();
                }
                topEmployee.add(tempCustomer);
            }
        }
        if (topEmployee.size() > 0) {
            Collections.sort(topEmployee);
            return UserDAO.find(topEmployee.get(topEmployee.size() - 1).id);
        }
        return null;
    }

    public static UserEntity BestEmployeeByBooks() {
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
        List<UserEntity> employees = getAll();
        List<Temp> topEmployee = new ArrayList<>();
        for (UserEntity emp : employees) {
            List<OrderEntity> orderOFThisEmployee = OrderDAO.findByEmployee(emp.getId());
            if (orderOFThisEmployee.size() >= 1) {
                Temp tempEmployee = new Temp(emp.getId(), 0);
                for (OrderEntity order : orderOFThisEmployee) {
                    List<OrderitemEntity> orderitemEntityList = OrderItemDAO.orderItemList(order.getId());
                    for (OrderitemEntity item : orderitemEntityList) {
                        tempEmployee.quantity += item.getQuantity();
                    }
                }
                topEmployee.add(tempEmployee);
            }
        }
        if (topEmployee.size() > 0) {
            Collections.sort(topEmployee);
            return UserDAO.find(topEmployee.get(topEmployee.size() - 1).id);
        }
        return null;
    }

    public static int totalOrder(UserEntity user) {
        List<OrderEntity> orderOFThisEmployee = OrderDAO.findByEmployee(user.getId());
        if (orderOFThisEmployee.size() > 0) {
            return orderOFThisEmployee.size();
        }
        return 0;
    }
}
