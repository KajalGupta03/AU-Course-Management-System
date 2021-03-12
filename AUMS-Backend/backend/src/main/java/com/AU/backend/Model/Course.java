package com.AU.backend.Model;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Setter @Getter @ToString @NoArgsConstructor @AllArgsConstructor
public class Course {
    private String courseName;
    private String courseDescription;
    private String prerequisite;
    private int userId;
    private int courseId;
    private Timestamp createdOn;
    private Timestamp lastUpdated;
    private String imageUrl;

    public Course(String courseName, String courseDescription, String prerequisite, int userId, Timestamp createdOn, Timestamp lastUpdated) {
        this.courseName = courseName;
        this.courseDescription = courseDescription;
        this.prerequisite = prerequisite;
        this.userId = userId;
        this.createdOn = createdOn;
        this.lastUpdated = lastUpdated;
    }
}
