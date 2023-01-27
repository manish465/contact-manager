package com.manish.contactmanager.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Phone_Number")
public class PhoneNumber {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "phone_id")
    private long id;

    @Column(name = "country_code")
    private String countryCode;
    private String number;
    private String type;
    @ManyToOne(cascade = CascadeType.ALL)
    private Contact contact;

    public PhoneNumber() {
    }

    public PhoneNumber(String countryCode, String number, String type) {
        this.countryCode = countryCode;
        this.number = number;
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    @Override
    public String toString() {
        return "PhoneNumber{" +
                "phoneId=" + id +
                ", countryCode='" + countryCode + '\'' +
                ", number='" + number + '\'' +
                ", type='" + type + '\'' +
                ", contact=" + contact +
                '}';
    }
}
