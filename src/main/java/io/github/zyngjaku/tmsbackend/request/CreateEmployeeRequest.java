package io.github.zyngjaku.tmsbackend.request;

import io.github.zyngjaku.tmsbackend.dao.entity.User;

import java.io.Serializable;

public class CreateEmployeeRequest implements Serializable {
    private String mail;
    private String firstName;
    private String lastName;
    private User.Role role;

    public CreateEmployeeRequest() {
    }

    public CreateEmployeeRequest(String mail, String firstName, String lastName, User.Role role) {
        setMail(mail);
        setFirstName(firstName);
        setLastName(lastName);
        setRole(role);
    }

    public String getMail() {
        return this.mail;
    }
    public void setMail(String mail) {
        this.mail = mail;
    }

    public User.Role getRole() {
        return role;
    }
    public void setRole(User.Role role) {
        this.role = role;
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
}
