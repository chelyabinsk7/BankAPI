package ru.zhenyaak.bankAPI.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Objects;

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

//    @JsonIgnore
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

//    @JsonIgnore
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

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", id_account=" + id_account +
                ", status_card='" + status_card + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return id == card.id && id_account == card.id_account && Objects.equals(number, card.number) && Objects.equals(status_card, card.status_card);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, id_account, status_card);
    }
}
