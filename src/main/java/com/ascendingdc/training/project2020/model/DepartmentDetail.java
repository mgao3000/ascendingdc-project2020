package com.ascendingdc.training.project2020.model;

public class DepartmentDetail {
    private Long id;

    private String description;

    private int revenue;

    private int size;

    private Long departmentId;

    public DepartmentDetail() {
    }

    public DepartmentDetail(String description, int revenue, int size) {
        this.description = description;
        this.revenue = revenue;
        this.size = size;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRevenue() {
        return revenue;
    }

    public void setRevenue(int revenue) {
        this.revenue = revenue;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    @Override
    public String toString() {
        return "DepartmentDetail{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", revenue=" + revenue +
                ", size=" + size +
                '}';
    }
}
