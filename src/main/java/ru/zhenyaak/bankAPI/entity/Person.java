package ru.zhenyaak.bankAPI.entity;

import java.sql.Date;

public class Person {

    int id_person;
    String lastName;
    String firstName;
    Date birthday;

    public Person(){

    }

    public Person(int id_person, String lastName, String firstName, Date birthday) {
        this.id_person = id_person;
        this.lastName = lastName;
        this.firstName = firstName;
        this.birthday = birthday;
    }

    public int getId_person() {
        return id_person;
    }

    public void setId_person(int id_person) {
        this.id_person = id_person;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
