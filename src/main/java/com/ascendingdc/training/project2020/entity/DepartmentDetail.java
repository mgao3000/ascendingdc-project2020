package com.ascendingdc.training.project2020.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "department_details")
public class DepartmentDetail {
    @Id
    //@SequenceGenerator(name = "employee_id_generator", sequenceName = "employee_id_seq", allocationSize = 1)
    //@GeneratedValue(strategy = SEQUENCE, generator = "employee_id_generator")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "revenue")
    private int revenue;

    @Column(name = "size")
    private int size;

    @JsonIgnore
//    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @OneToOne(fetch = FetchType.LAZY)
    @OneToOne
    @JoinColumn(name="DEPARTMENT_ID")
    private Department department;

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

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
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
