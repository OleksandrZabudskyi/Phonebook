package com.phonebook.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Objects;

/**
 * <code>Contact</code> class describes the information about contact.
 *
 * @author Zabudskyi Oleksandr zabudskyioleksandr@gmail.com
 */
@Entity
@Table(name = "contact")
@JsonIgnoreProperties({"user"})
public class Contact {
    private Long id;
    private String firstName;
    private String lastName;
    private String additionalName;
    private String mobilePhone;
    private String homePhone;
    private String address;
    private String email;
    private User user;

    public Contact() {
    }

    public Contact(String firstName, String lastName, String additionalName, String mobilePhone, User user) {
        this(firstName, lastName, additionalName, mobilePhone, null, null, null, user);
    }

    public Contact(String firstName, String lastName, String additionalName, String mobilePhone,
                   String homePhone, String address, String email, User user) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.additionalName = additionalName;
        this.mobilePhone = mobilePhone;
        this.homePhone = homePhone;
        this.address = address;
        this.email = email;
        this.user = user;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "contact_id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "first_name")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "last_name")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "additional_name")
    public String getAdditionalName() {
        return additionalName;
    }

    public void setAdditionalName(String additionalName) {
        this.additionalName = additionalName;
    }

    @Column(name = "mobile_phone")
    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    @Column(name = "home_phone")
    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Contact)) return false;
        Contact contact = (Contact) o;
        return Objects.equals(id, contact.id) &&
                Objects.equals(firstName, contact.firstName) &&
                Objects.equals(lastName, contact.lastName) &&
                Objects.equals(additionalName, contact.additionalName) &&
                Objects.equals(mobilePhone, contact.mobilePhone) &&
                Objects.equals(homePhone, contact.homePhone) &&
                Objects.equals(address, contact.address) &&
                Objects.equals(email, contact.email) &&
                Objects.equals(user, contact.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, additionalName, mobilePhone, homePhone, address, email, user);
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", additionalName='" + additionalName + '\'' +
                ", mobilePhone='" + mobilePhone + '\'' +
                ", homePhone='" + homePhone + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
