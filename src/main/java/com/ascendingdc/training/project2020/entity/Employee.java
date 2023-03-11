package com.ascendingdc.training.project2020.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "employee")
public class Employee {
    public Employee() { }
    public Employee(String name, String firstName, String lastName, String email, String address) {
        this.name = name;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
    }

    @Id
    //@SequenceGenerator(name = "employee_id_generator", sequenceName = "employee_id_seq", allocationSize = 1)
    //@GeneratedValue(strategy = SEQUENCE, generator = "employee_id_generator")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private String address;

    @Column(name = "hired_date")
    private LocalDate hiredDate;
//    private Date hiredDate;

    /*
    temporarial created for testing JDBC only
     */
    @Transient
    private Long departmentId;

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public LocalDate getHiredDate() {
//    public Date getHiredDate() {
        return hiredDate;
    }

    public void setHiredDate(LocalDate hiredDate) {
//    public void setHiredDate(Date hiredDate) {
        this.hiredDate = hiredDate;
    }

//    @OneToOne(fetch = FetchType.EAGER, mappedBy = "employee", cascade = CascadeType.ALL)
//    private EmployeeDetail employeeDetail;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

//    @OneToMany(mappedBy = "employee", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
//    @OneToMany(mappedBy = "employee", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Account> accounts;

    public void addAccount(Account account) {
        this.getAccounts().add(account);
        account.setEmployee(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

//    public EmployeeDetail getEmployeeDetail() {
//        return employeeDetail;
//    }
//
//    public void setEmployeeDetail(EmployeeDetail employeeDetail) {
//        this.employeeDetail = employeeDetail;
//    }

    public Set<Account> getAccounts() {
//        try {
//            int size = accounts.size();
//        }
//        catch (Exception e) {
//            return null;
//        }
        if(accounts == null)
            accounts = new HashSet<Account>();
        return accounts;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return id == employee.id &&
                name.equals(employee.name) &&
                Objects.equals(firstName, employee.firstName) &&
                Objects.equals(lastName, employee.lastName) &&
                Objects.equals(email, employee.email) &&
                Objects.equals(address, employee.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, firstName, lastName, email, address);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", No. of Account ='" + getAccounts().size() + '\'' +
                '}';
    }

    //    @Override
//    public String toString() {
//        ObjectMapper objectMapper = new ObjectMapper();
//        String str = null;
//        try {
//            str = objectMapper.writeValueAsString(this);
//        }
//        catch(JsonProcessingException jpe) {
//            jpe.printStackTrace();
//        }
//
//        return str;
//    }

}
