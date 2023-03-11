package com.ascendingdc.training.project2020.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Student  {

    public Student() {
    }

    public Student(String loginName, String password, String firstName,
                   String lastName, String email, String address) {
        this.loginName = loginName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
    }

    private Long id;

    private String loginName;

    private String password;

    private String firstName;

    private String lastName;

    private String email;

    private String address;

    private LocalDate enrolledDate;

    private Long majorId;

    private String majorName;

    private List<Project> projectList = new ArrayList<Project>();

    public Long getMajorId() {
        return majorId;
    }

    public void setMajorId(Long majorId) {
        this.majorId = majorId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public LocalDate getEnrolledDate() {
        return enrolledDate;
    }

    public void setEnrolledDate(LocalDate enrolledDate) {
        this.enrolledDate = enrolledDate;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    public List<Project> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<Project> projectList) {
        this.projectList = projectList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student student = (Student) o;
//        return Objects.equals(getId(), student.getId()) && Objects.equals(getLoginName(), student.getLoginName()) && Objects.equals(getPassword(), student.getPassword()) && Objects.equals(getFirstName(), student.getFirstName()) && Objects.equals(getLastName(), student.getLastName()) && Objects.equals(getEmail(), student.getEmail()) && Objects.equals(getAddress(), student.getAddress()) && Objects.equals(getEnrolledDate(), student.getEnrolledDate());
        return Objects.equals(getId(), student.getId()) && Objects.equals(getLoginName(), student.getLoginName()) && Objects.equals(getPassword(), student.getPassword()) && Objects.equals(getFirstName(), student.getFirstName()) && Objects.equals(getLastName(), student.getLastName()) && Objects.equals(getEmail(), student.getEmail()) && Objects.equals(getAddress(), student.getAddress());
//        return Objects.equals(getId(), student.getId());
    }

    @Override
    public int hashCode() {
//        return Objects.hash(getId(), getLoginName(), getPassword(), getFirstName(), getLastName(), getEmail(), getAddress(), getEnrolledDate());
        return Objects.hash(getId(), getLoginName(), getPassword(), getFirstName(), getLastName(), getEmail(), getAddress());
//        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", loginName='" + loginName + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", enrolledDate=" + enrolledDate +
                '}';
    }

//    @Override
//    public int compareTo(Student student) {
//        int equalIntValue = 0;
//        if(student != null && student.getId() != null && this.getId() != null)
//            equalIntValue = (int)(this.getId() - student.getId());
//        return equalIntValue;
//    }
}
