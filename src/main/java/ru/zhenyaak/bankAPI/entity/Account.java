package ru.zhenyaak.bankAPI.entity;

import java.math.BigDecimal;
import java.util.Objects;

public class Account {

    int id;
    String number;
    int id_owner;
    BigDecimal balance;
    String status_account;

    public Account() {

    }

    public Account(int id, String number, int id_owner, BigDecimal balance, String status_account) {
        this.id = id;
        this.number = number;
        this.id_owner = id_owner;
        this.balance = balance;
        this.status_account = status_account;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getId_owner() {
        return id_owner;
    }

    public void setId_owner(int id_owner) {
        this.id_owner = id_owner;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getStatus_account() {
        return status_account;
    }

    public void setStatus_account(String status_account) {
        this.status_account = status_account;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", id_owner=" + id_owner +
                ", balance=" + balance +
                ", status_account='" + status_account + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return id == account.id && id_owner == account.id_owner && Objects.equals(number, account.number) && Objects.equals(balance, account.balance) && Objects.equals(status_account, account.status_account);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, id_owner, balance, status_account);
    }
}
