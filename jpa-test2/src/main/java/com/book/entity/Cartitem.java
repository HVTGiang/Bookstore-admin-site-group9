package com.book.entity;

import javax.persistence.*;

@Entity
@Table(name = "cartitem", schema = "bookstore", catalog = "")
public class Cartitem {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "idUser")
    private int idUser;
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
    @JoinColumn(name = "idUser", referencedColumnName = "id", nullable = false, insertable=false, updatable =false)
    private User userByIdUser;
    @ManyToOne
    @JoinColumn(name = "idProduct", referencedColumnName = "id", nullable = false, insertable=false, updatable =false)
    private Product productByIdProduct;

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

        Cartitem that = (Cartitem) o;

        if (id != that.id) return false;
        if (idUser != that.idUser) return false;
        if (idProduct != that.idProduct) return false;
        if (quantity != that.quantity) return false;
        if (price != that.price) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + idUser;
        result = 31 * result + idProduct;
        result = 31 * result + quantity;
        result = 31 * result + price;
        return result;
    }

    public User getUserByIdUser() {
        return userByIdUser;
    }

    public void setUserByIdUser(User userByIdUser) {
        this.userByIdUser = userByIdUser;
    }

    public Product getProductByIdProduct() {
        return productByIdProduct;
    }

    public void setProductByIdProduct(Product productByIdProduct) {
        this.productByIdProduct = productByIdProduct;
    }
}
