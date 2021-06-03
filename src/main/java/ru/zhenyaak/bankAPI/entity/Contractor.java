package ru.zhenyaak.bankAPI.entity;

import java.util.Objects;

public class Contractor {

    int id_contractor;
    String name;
    String inn;

    public Contractor(){

    }

    public Contractor(int id_contractor, String name, String inn) {
        this.id_contractor = id_contractor;
        this.name = name;
        this.inn = inn;
    }

    public int getId_contractor() {
        return id_contractor;
    }

    public void setId_contractor(int id_contractor) {
        this.id_contractor = id_contractor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    @Override
    public String toString() {
        return "Contractor{" +
                "id_contractor=" + id_contractor +
                ", name='" + name + '\'' +
                ", inn='" + inn + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contractor that = (Contractor) o;
        return id_contractor == that.id_contractor && Objects.equals(name, that.name) && Objects.equals(inn, that.inn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_contractor, name, inn);
    }
}
