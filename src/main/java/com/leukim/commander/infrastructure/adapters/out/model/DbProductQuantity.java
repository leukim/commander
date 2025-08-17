package com.leukim.commander.infrastructure.adapters.out.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public final class DbProductQuantity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private DbOrder order;
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private DbProduct product;
    private double quantity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DbOrder getOrder() {
        return order;
    }

    public void setOrder(DbOrder order) {
        this.order = order;
    }

    public DbProduct getProduct() {
        return product;
    }

    public void setProduct(DbProduct product) {
        this.product = product;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public DbProductQuantity() {
    }

    public DbProductQuantity(Long id, DbOrder order, DbProduct product, double quantity) {
        this.id = id;
        this.order = order;
        this.product = product;
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (DbProductQuantity) obj;
        return Objects.equals(this.product, that.product) &&
                Double.doubleToLongBits(this.quantity) == Double.doubleToLongBits(that.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, quantity);
    }

    @Override
    public String toString() {
        return "DbProductQuantity[" +
                "product=" + product + ", " +
                "quantity=" + quantity + ']';
    }


}
