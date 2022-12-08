package com.book.entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;

@Entity
@Table(name = "`order`", schema = "bookstore", catalog = "")
public class OrderEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "`idUser`")
    private int idUser;
    @Basic
    @Column(name = "idSeller")
    private int idSeller;
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
    @JoinColumn(name = "idUser", referencedColumnName = "id", nullable = false, insertable=false, updatable =false)
    private UserEntity userByIdUser;
    @ManyToOne
    @JoinColumn(name = "idMethod", referencedColumnName = "id", nullable = false, insertable=false, updatable =false)
    private PaymethodEntity paymethodByIdMethod;
    @ManyToOne
    @JoinColumn(name = "idDelivery", referencedColumnName = "id", nullable = false, insertable=false, updatable =false)
    private DeliveryEntity deliveryByIdDelivery;
    @OneToMany(mappedBy = "orderByIdOrder")
    private Collection<OrderitemEntity> orderitemsById;

    public OrderEntity(int id, int idUser, int idSeller, Date createTime, String phone, String address, String contactName, Date receiveDate, int idMethod, int idDelivery, int totalPay, int status, UserEntity userByIdUser, PaymethodEntity paymethodByIdMethod, DeliveryEntity deliveryByIdDelivery, Collection<OrderitemEntity> orderitemsById) {
        this.id = id;
        this.idUser = idUser;
        this.idSeller = idSeller;
        this.createTime = createTime;
        this.phone = phone;
        this.address = address;
        this.contactName = contactName;
        this.receiveDate = receiveDate;
        this.idMethod = idMethod;
        this.idDelivery = idDelivery;
        this.totalPay = totalPay;
        this.status = status;
        this.userByIdUser = userByIdUser;
        this.paymethodByIdMethod = paymethodByIdMethod;
        this.deliveryByIdDelivery = deliveryByIdDelivery;
        this.orderitemsById = orderitemsById;
    }

    public OrderEntity() {
    }

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

    public int getIdSeller() {
        return idSeller;
    }

    public void setIdSeller(int idSeller) {
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

        OrderEntity that = (OrderEntity) o;

        if (id != that.id) return false;
        if (idUser != that.idUser) return false;
        if (idSeller != that.idSeller) return false;
        if (idMethod != that.idMethod) return false;
        if (idDelivery != that.idDelivery) return false;
        if (totalPay != that.totalPay) return false;
        if (status != that.status) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (phone != null ? !phone.equals(that.phone) : that.phone != null) return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;
        if (contactName != null ? !contactName.equals(that.contactName) : that.contactName != null) return false;
        if (receiveDate != null ? !receiveDate.equals(that.receiveDate) : that.receiveDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + idUser;
        result = 31 * result + idSeller;
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

    public UserEntity getUserByIdUser() {
        return userByIdUser;
    }

    public void setUserByIdUser(UserEntity userByIdUser) {
        this.userByIdUser = userByIdUser;
    }

    public PaymethodEntity getPaymethodByIdMethod() {
        return paymethodByIdMethod;
    }

    public void setPaymethodByIdMethod(PaymethodEntity paymethodByIdMethod) {
        this.paymethodByIdMethod = paymethodByIdMethod;
    }

    public DeliveryEntity getDeliveryByIdDelivery() {
        return deliveryByIdDelivery;
    }

    public void setDeliveryByIdDelivery(DeliveryEntity deliveryByIdDelivery) {
        this.deliveryByIdDelivery = deliveryByIdDelivery;
    }

    public Collection<OrderitemEntity> getOrderitemsById() {
        return orderitemsById;
    }

    public void setOrderitemsById(Collection<OrderitemEntity> orderitemsById) {
        this.orderitemsById = orderitemsById;
    }
}
