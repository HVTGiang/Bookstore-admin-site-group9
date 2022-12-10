package com.book.entity;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "`order`", schema = "bookstore")
public class Order {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "idUser")
    private int idUser;
    @Basic
    @Column(name = "idSeller")
    private Integer idSeller;
    @Basic
    @Column(name = "createTime")
    private Date createTime;
    @Basic
    @Column(name = "phone")
    private String phone;
    @Basic
    @Column(name = "address")
    private String address;
    @Basic
    @Column(name = "contactName")
    private String contactName;
    @Basic
    @Column(name = "receiveDate")
    private Date receiveDate;
    @Basic
    @Column(name = "idMethod")
    private int idMethod;
    @Basic
    @Column(name = "idDelivery")
    private int idDelivery;
    @Basic
    @Column(name = "totalPay")
    private int totalPay;
    @Basic
    @Column(name = "`status`")
    private int status;

    @ManyToOne
    @JoinColumn(name = "idUser", referencedColumnName = "id", nullable = true, insertable=false, updatable =false)
    private User userByIdUser;

    @ManyToOne
    @JoinColumn(name = "idSeller", referencedColumnName = "id", nullable = true, insertable=false, updatable =false)
    private User sellerByIdSeller;

    @ManyToOne
    @JoinColumn(name = "idMethod", referencedColumnName = "id", nullable = true, insertable=false, updatable =false)
    private Paymethod paymethodByIdMethod;

    @ManyToOne
    @JoinColumn(name = "idDelivery", referencedColumnName = "id", nullable = false, insertable=false, updatable =false)
    private Paymethod deliveryByIdDelivery;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public Integer getIdSeller() {
        return idSeller;
    }

    public void setIdSeller(Integer idSeller) {
        this.idSeller = idSeller;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public Date getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }

    public int getIdMethod() {
        return idMethod;
    }

    public void setIdMethod(int idMethod) {
        this.idMethod = idMethod;
    }

    public int getIdDelivery() {
        return idDelivery;
    }

    public void setIdDelivery(int idDelivery) {
        this.idDelivery = idDelivery;
    }

    public int getTotalPay() {
        return totalPay;
    }

    public void setTotalPay(int totalPay) {
        this.totalPay = totalPay;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (id != order.id) return false;
        if (idUser != order.idUser) return false;
        if (idMethod != order.idMethod) return false;
        if (idDelivery != order.idDelivery) return false;
        if (totalPay != order.totalPay) return false;
        if (status != order.status) return false;
        if (idSeller != null ? !idSeller.equals(order.idSeller) : order.idSeller != null) return false;
        if (createTime != null ? !createTime.equals(order.createTime) : order.createTime != null) return false;
        if (phone != null ? !phone.equals(order.phone) : order.phone != null) return false;
        if (address != null ? !address.equals(order.address) : order.address != null) return false;
        if (contactName != null ? !contactName.equals(order.contactName) : order.contactName != null) return false;
        if (receiveDate != null ? !receiveDate.equals(order.receiveDate) : order.receiveDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + idUser;
        result = 31 * result + (idSeller != null ? idSeller.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (contactName != null ? contactName.hashCode() : 0);
        result = 31 * result + (receiveDate != null ? receiveDate.hashCode() : 0);
        result = 31 * result + idMethod;
        result = 31 * result + idDelivery;
        result = 31 * result + totalPay;
        result = 31 * result + status;
        return result;
    }
}
