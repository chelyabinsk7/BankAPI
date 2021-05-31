package ru.zhenyaak.bankAPI.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class AccountTransaction {

    int id;
    int id_from;
    int id_to;
    BigDecimal amount;
    Timestamp time;
    char status_transaction;
    String message;

    public AccountTransaction(){

    }

    public AccountTransaction(int id, int id_from, int id_to, BigDecimal amount, Timestamp time, char status_transaction, String message) {
        this.id = id;
        this.id_from = id_from;
        this.id_to = id_to;
        this.amount = amount;
        this.time = time;
        this.status_transaction = status_transaction;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_from() {
        return id_from;
    }

    public void setId_from(int id_from) {
        this.id_from = id_from;
    }

    public int getId_to() {
        return id_to;
    }

    public void setId_to(int id_to) {
        this.id_to = id_to;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public char getStatus_transaction() {
        return status_transaction;
    }

    public void setStatus_transaction(char status_transaction) {
        this.status_transaction = status_transaction;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
