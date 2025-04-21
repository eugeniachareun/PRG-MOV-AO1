package com.chareun410.miagenda.domain;

public class Contact {
    private Long id;
    private String name;
    private String lastName;
    private String phone;
    private String address;
    private Gender gender;
    private static Long contador = 0L;

    public Contact() {
        contador++;
    }

    public Contact(String name, String lastName, String phone, String address, Gender gender) {
        this.id = contador++;
        this.name = name;
        this.lastName = lastName;
        this.phone = phone;
        this.address = address;
        this.gender = gender;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getFullname() {
        return name + " " + lastName;
    }

    public Long getId() {
        return id;
    }
}
