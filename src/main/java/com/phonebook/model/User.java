package com.phonebook.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.*;

/**
 * <code>User</code> class describes the information about user with list of contacts.
 *
 * @author Zabudskyi Oleksandr zabudskyioleksandr@gmail.com
 */
@Entity
@Table(name = "user")
@JsonIgnoreProperties({"passwordConfirm"})
public class User {

    private Long id;
    private String username;
    private String password;
    private String passwordConfirm;
    private String fullName;
    private List<Contact> contacts = new LinkedList<>();

    public User() {
    }

    public User(String username, String password, String passwordConfirm, String fullName, List<Contact> contacts) {
        this.username = username;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
        this.fullName = fullName;
        this.contacts = contacts;
    }

    public User(String username, String password, String fullName) {
        this(username, password, null, fullName, null);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "user_name")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Transient
    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    @Column(name = "full_name")
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @OneToMany(mappedBy = "user")
    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(username, user.username) &&
                Objects.equals(password, user.password) &&
                Objects.equals(passwordConfirm, user.passwordConfirm) &&
                Objects.equals(fullName, user.fullName) &&
                Objects.equals(contacts, user.contacts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, passwordConfirm, fullName, contacts);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", passwordConfirm='" + passwordConfirm + '\'' +
                ", fullName='" + fullName + '\'' +
                '}';
    }
}
