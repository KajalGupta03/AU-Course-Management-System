package com.AU.backend.Model;

import lombok.*;

import java.sql.Timestamp;

@Setter @Getter @AllArgsConstructor @ToString @NoArgsConstructor
public class Feedback {

        private String participantName;
        private int feedbackId;
        private int courseId;
        private String feedbackText;
        private float rating;
        private Timestamp createdAt;
}
