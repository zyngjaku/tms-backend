package io.github.zyngjaku.tmsbackend.request;

import io.github.zyngjaku.tmsbackend.dao.entity.User;

import java.io.Serializable;

public class CreateCompanyRequest implements Serializable {
    private String mail;
    private String password;
    private String firstName;
    private String lastName;
    private String name;
    private String street;
    private String city;
    private String zipCode;
    private String country;

    public CreateCompanyRequest() {
    }

    public CreateCompanyRequest(String mail, String password, String firstName, String lastName, String name, String street, String city, String zipCode, String country) {
        setMail(mail);
        setPassword(password);
        setFirstName(firstName);
        setLastName(lastName);
        setName(name);
        setStreet(street);
        setCity(city);
        setZipCode(zipCode);
        setCountry(country);
    }

    public String getMail() {
        return this.mail;
    }
    public void setMail(String mail) {
        this.mail = mail;
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

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getStreet() {
        return street;
    }
    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
}
