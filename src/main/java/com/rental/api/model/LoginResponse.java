package com.rental.api.model;

public class LoginResponse {
    private String token;

    private long expiresIn;

    private LoginResponse(){
        // Private to force builder use
    }

    public String getToken() {
        return token;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public static class Builder{
        private String token;
        private long expiresIn;

        public Builder setToken(String token) {
            this.token = token;
            return this;
        }

        public Builder setExpiresIn(long expiresIn) {
            this.expiresIn = expiresIn;
            return this;
        }
        
        public LoginResponse build() {
            LoginResponse response = new LoginResponse();
            response.token = this.token;
            response.expiresIn = this.expiresIn;
            return response;
        }
    }
}
