package com.manish.contactmanager.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "CONTACT", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contact_id")
    private long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    private String email;
    private String description;
    @ManyToOne(cascade = CascadeType.ALL)
    private User user;
    @OneToOne(cascade = CascadeType.ALL)
    private Work work;
    @OneToMany(cascade = CascadeType.ALL)
    private List<PhoneNumber> phoneNumbers;

    public Contact() {
    }

    public Contact(String firstName, String lastName, String email, String description) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Work getWork() {
        return work;
    }

    public void setWork(Work work) {
        this.work = work;
    }

    public List<PhoneNumber> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<PhoneNumber> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "contactId=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", description='" + description + '\'' +
                ", user=" + user +
                ", work=" + work +
                ", phoneNumbers=" + phoneNumbers +
                '}';
    }
}
