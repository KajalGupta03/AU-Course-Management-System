package com.AU.backend.Model;

import lombok.*;

@Setter @Getter @ToString @NoArgsConstructor @AllArgsConstructor
public class User {
    private String firstName;
    private String lastName;
    private String emailId;
    private int userId;

    public User(String firstName, String lastName, String emailId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailId = emailId;
    }

}
