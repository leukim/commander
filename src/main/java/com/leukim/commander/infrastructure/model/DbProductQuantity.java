package com.leukim.commander.infrastructure.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.util.Objects;

@Entity
public final class DbProductQuantity {
    @Id
    private Long id;
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private DbOrder order;
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private DbProduct product;
    private double quantity;

    public DbProduct getProduct() {
        return product;
    }

    public void setProduct(DbProduct product) {
        this.product = product;
    }

    public DbProductQuantity() {
    }

    public DbProductQuantity(DbProduct product, double quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public DbProduct product() {
        return product;
    }

    public double quantity() {
        return quantity;
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
