package com.book.business;

import com.book.dao.PayDAO;
import com.book.dao.UserDAO;
import com.book.entity.PaymethodEntity;
import com.book.entity.UserEntity;

import java.util.List;

public class Paymethod {
    public static String CheckNameAvailable(PaymethodEntity paymethod) {
        List<PaymethodEntity> foundedList = PayDAO.find(paymethod.getName());
        if (foundedList.size() >= 1) {
            return "<b>Tên phương thức đã tồn tại</b>! Vui lòng nhập tên khác";
        }
        return "OK";
    }
}
