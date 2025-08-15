package com.leukim.commander.infrastructure.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public final class DbProduct {
    @Id
    private String id;
    private String name;
    private String description;
    private DbUnitOfMeasure uom;

    public DbProduct() {
    }

    public DbProduct(String id, String name, String description, DbUnitOfMeasure uom) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.uom = uom;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DbUnitOfMeasure getUom() {
        return uom;
    }

    public void setUom(DbUnitOfMeasure uom) {
        this.uom = uom;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (DbProduct) obj;
        return Objects.equals(this.id, that.id) &&
                Objects.equals(this.name, that.name) &&
                Objects.equals(this.description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
    }

    @Override
    public String toString() {
        return "DbItemType[" +
                "id=" + id + ", " +
                "name=" + name + ", " +
                "description=" + description + "]";
    }

}