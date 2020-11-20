package io.github.zyngjaku.tmsbackend.response;

import java.io.Serializable;

public class JwtResponse implements Serializable {
    private String jwttoken;

    public JwtResponse(String jwttoken) {
        this.jwttoken = jwttoken;
    }

    public void setToken(String jwttoken) {
        this.jwttoken = jwttoken;
    }
    public String getToken() {
        return this.jwttoken;
    }
}