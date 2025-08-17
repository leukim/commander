package com.leukim.commander.infrastructure.adapters.out.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
public final class DbOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    @OneToMany(mappedBy = "order", cascade = CascadeType.REMOVE)
    private List<DbProductQuantity> items;
    private boolean picked;

    public DbOrder() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DbProductQuantity> getItems() {
        return items;
    }

    public void addItem(DbProductQuantity item) {
        this.items.add(item);
    }

    public void setItems(List<DbProductQuantity> items) {
        this.items = items;
    }

    public boolean isPicked() {
        return picked;
    }

    public void setPicked(boolean picked) {
        this.picked = picked;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        var that = (DbOrder) obj;
        return Objects.equals(this.id, that.id)
            && Objects.equals(this.name, that.name)
            && Objects.equals(this.items, that.items)
            && this.picked == that.picked;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, items, picked);
    }

    @Override
    public String toString() {
        return "DbOrder["
            + "id=" + id + ", "
            + "name=" + name + ", "
            + "items=" + items + ", "
            + "picked=" + picked + ']';
    }
}
