package ru.zhenyaak.bankAPI.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Card {

    int id;
    String number;
    int id_account;
    String status_card;

    public Card(){

    }

    public Card(int id, String number, int id_account, String status_card) {
        this.id = id;
        this.number = number;
        this.id_account = id_account;
        this.status_card = status_card;
    }

    @JsonIgnore
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

    @JsonIgnore
    public int getId_account() {
        return id_account;
    }

    public void setId_account(int id_account) {
        this.id_account = id_account;
    }

    public String getStatus_card() {
        return status_card;
    }

    public void setStatus_card(String status_card) {
        this.status_card = status_card;
    }
}
