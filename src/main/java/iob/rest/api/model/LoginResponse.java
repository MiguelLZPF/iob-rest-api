package iob.rest.api.model;

import lombok.Getter;

@Getter
public class LoginResponse {
    private String token;

    public LoginResponse(String token) {
        this.token = token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
