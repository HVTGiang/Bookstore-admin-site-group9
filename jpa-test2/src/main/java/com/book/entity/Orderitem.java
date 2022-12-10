package com.book.entity;

import javax.persistence.*;

@Entity
@Table(name = "orderitem", schema = "bookstore", catalog = "")
public class Orderitem {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "idOrder" )
    private int idOrder;
    @Basic
    @Column(name = "idProduct")
    private int idProduct;
    @Basic
    @Column(name = "quantity")
    private int quantity;
    @Basic
    @Column(name = "price")
    private int price;
    @ManyToOne
    @JoinColumn(name = "idOrder", referencedColumnName = "id", nullable = false, insertable=false, updatable =false)
    private Order orderByIdOrder;
    @ManyToOne
    @JoinColumn(name = "idProduct", referencedColumnName = "id", nullable = false, insertable=false, updatable =false)
    private Product productByIdProduct;

    public Orderitem() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Orderitem that = (Orderitem) o;

        if (id != that.id) return false;
        if (idOrder != that.idOrder) return false;
        if (idProduct != that.idProduct) return false;
        if (quantity != that.quantity) return false;
        if (price != that.price) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + idOrder;
        result = 31 * result + idProduct;
        result = 31 * result + quantity;
        result = 31 * result + price;
        return result;
    }

    public Order getOrderByIdOrder() {
        return orderByIdOrder;
    }

    public void setOrderByIdOrder(Order orderByIdOrder) {
        this.orderByIdOrder = orderByIdOrder;
    }

    public Product getProductByIdProduct() {
        return productByIdProduct;
    }

    public void setProductByIdProduct(Product productByIdProduct) {
        this.productByIdProduct = productByIdProduct;
    }
}
