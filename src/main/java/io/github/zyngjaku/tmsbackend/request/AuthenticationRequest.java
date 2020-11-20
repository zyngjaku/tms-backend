package io.github.zyngjaku.tmsbackend.request;

import java.io.Serializable;

public class AuthenticationRequest implements Serializable {
    private String mail;
    private String password;

    public AuthenticationRequest() {
    }

    public AuthenticationRequest(String mail, String password) {
        this.setMail(mail);
        this.setPassword(password);
    }

    public String getMail() {
        return this.mail;
    }
    public void setMail(String mail) {
        this.mail = mail;
    }
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
