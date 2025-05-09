package com.wildfireinventory.model;

import java.sql.Timestamp;

public class Category {
    private int categoryId;
    private String categoryName;
    private Timestamp createdAt;

    public int getCategoryId() { return categoryId; }
    public void setCategoryId(int id) { this.categoryId = id; }

    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String name) { this.categoryName = name; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp ts) { this.createdAt = ts; }
}
