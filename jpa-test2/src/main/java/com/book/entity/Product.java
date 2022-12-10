package com.book.entity;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "product", schema = "bookstore", catalog = "")
public class Product {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "idCategory")
    private int idCategory;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "image")
    private String image;
    @Basic
    @Column(name = "originalPrice")
    private int originalPrice;
    @Basic
    @Column(name = "salePrice")
    private int salePrice;
    @Basic
    @Column(name = "discription")
    private String discription;
    @Basic
    @Column(name = "quantity")
    private int quantity;
    @Basic
    @Column(name = "active")
    private boolean active;
    @OneToMany(mappedBy = "productByIdProduct")
    private Collection<Cartitem> cartitemsById;
    @OneToMany(mappedBy = "productByIdProduct")
    private Collection<Orderitem> orderitemsById;

    @ManyToOne
    @JoinColumn(name = "idCategory", referencedColumnName = "id", nullable = false, insertable=false, updatable =false)
    private Category categoryByIdCategory;

    public int getId() {
        return id;
    }

    public Product() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(int originalPrice) {
        this.originalPrice = originalPrice;
    }

    public int getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(int salePrice) {
        this.salePrice = salePrice;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product that = (Product) o;

        if (id != that.id) return false;
        if (idCategory != that.idCategory) return false;
        if (originalPrice != that.originalPrice) return false;
        if (salePrice != that.salePrice) return false;
        if (quantity != that.quantity) return false;
        if (active != that.active) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (image != null ? !image.equals(that.image) : that.image != null) return false;
        if (discription != null ? !discription.equals(that.discription) : that.discription != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + idCategory;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (image != null ? image.hashCode() : 0);
        result = 31 * result + originalPrice;
        result = 31 * result + salePrice;
        result = 31 * result + (discription != null ? discription.hashCode() : 0);
        result = 31 * result + quantity;
        result = 31 * result + (active ? 1 : 0);
        return result;
    }

    public Collection<Cartitem> getCartitemsById() {
        return cartitemsById;
    }

    public void setCartitemsById(Collection<Cartitem> cartitemsById) {
        this.cartitemsById = cartitemsById;
    }

    public Collection<Orderitem> getOrderitemsById() {
        return orderitemsById;
    }

    public void setOrderitemsById(Collection<Orderitem> orderitemsById) {
        this.orderitemsById = orderitemsById;
    }

    public Category getCategoryByIdCategory() {
        return categoryByIdCategory;
    }

    public void setCategoryByIdCategory(Category categoryByIdCategory) {
        this.categoryByIdCategory = categoryByIdCategory;
    }
}
