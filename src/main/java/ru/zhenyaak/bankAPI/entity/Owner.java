package ru.zhenyaak.bankAPI.entity;

public class Owner {

    int id;
    char type_owner;

    public Owner(){

    }

    public Owner(int id, char type_owner) {
        this.id = id;
        this.type_owner = type_owner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public char getType_owner() {
        return type_owner;
    }

    public void setType_owner(char type_owner) {
        this.type_owner = type_owner;
    }
}
