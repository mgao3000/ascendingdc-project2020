package com.ascendingdc.training.project2020.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Account {
    public  Account() {}
    public  Account(String accountType, BigDecimal balance) {
        this.accountType = accountType;
        this.balance = balance;
    }

    private Long id;

    private String accountType;

    private BigDecimal balance;

    private long employeeId;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountType() {
        return accountType;
    }
    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return id == account.id &&
                account.balance.compareTo(balance) == 0 &&
                accountType.equals(account.accountType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, accountType, balance);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", accountType='" + accountType + '\'' +
                ", balance=" + balance +
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
