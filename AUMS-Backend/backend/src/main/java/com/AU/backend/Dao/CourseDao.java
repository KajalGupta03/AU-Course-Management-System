package com.AU.backend.Dao;

import com.AU.backend.Model.Course;
import com.AU.backend.Model.User;
import com.AU.backend.Service.FeedbackService;
import com.AU.backend.Service.MaterialService;
import com.AU.backend.Service.SkillServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
public class CourseDao {

    Logger logger= LoggerFactory.getLogger(CourseDao.class);

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    SkillServices skillServices;

    @Autowired
    MaterialService materialService;

    @Autowired
    FeedbackService feedbackService;

    public Course addCourse(Course course)throws Exception {
        try{

        KeyHolder keyHolder = new GeneratedKeyHolder();
        final String add_new_course = "insert into course (courseName,courseDescription,prerequisite, userId, createdOn, lastUpdated, imageUrl) values( ? , ? , ? , ? , ?, ?, ?)";

        java.util.Date utilDate = new java.util.Date();
        java.sql.Timestamp timestamp = new java.sql.Timestamp(utilDate.getTime());

        this.jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(add_new_course, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, course.getCourseName());
            stmt.setString(2, course.getCourseDescription());
            stmt.setString(3, course.getPrerequisite());
            stmt.setInt(4, course.getUserId());
            stmt.setTimestamp(5, timestamp);
            stmt.setTimestamp(6, timestamp);
            stmt.setString(7, course.getImageUrl());
            return stmt;
        }, keyHolder);

        logger.info("Added the new Course ");
        return jdbcTemplate.queryForObject("select courseName,courseDescription,courseId,prerequisite,userId, createdOn, lastUpdated, imageUrl from course where courseId=" + keyHolder.getKey(), CourseDao.MapRowToCourseLambda);
    }
    catch (Exception e)
    {
        logger.error(e.getCause()+" in this method "+Thread.currentThread().getStackTrace()[1].getMethodName());
        throw e;
    }
    }


    public Course updateCourse(int courseId, Course course)throws Exception {

        try {
            final String add_new_course = "UPDATE course SET courseName=?, courseDescription=?, prerequisite=?, userId=?, lastUpdated=now() WHERE courseId=" + courseId;

            java.util.Date utilDate = new java.util.Date();
            java.sql.Timestamp timestamp = new java.sql.Timestamp(utilDate.getTime());

            this.jdbcTemplate.update(connection -> {
                PreparedStatement stmt = connection.prepareStatement(add_new_course);
                stmt.setString(1, course.getCourseName());
                stmt.setString(2, course.getCourseDescription());
                stmt.setString(3, course.getPrerequisite());
                stmt.setInt(4, course.getUserId());
                return stmt;
            });
            logger.info("updated the course with courseId="+courseId);
            return jdbcTemplate.queryForObject("select courseName,courseDescription,courseId,prerequisite,userId, createdOn, lastUpdated from course where courseId='" + courseId + "'", CourseDao.MapRowToCourseLambda);
        }
        catch (Exception e){
            logger.error(e.getCause()+" in this method "+Thread.currentThread().getStackTrace()[1].getMethodName());
            throw e;
        }
    }

    public Course getCourseById(int courseId) throws Exception
    {
        try{
            Course course=this.jdbcTemplate.queryForObject("select * from course where courseId="+courseId,CourseDao.MapRowToCourseLambda);
            logger.info("Retrieved the course with courseId="+courseId);
            return course;
        }
        catch (Exception e)
        {
            logger.error(e.getCause()+" in this method "+Thread.currentThread().getStackTrace()[1].getMethodName());
            throw e;
        }
    }

    public List<Course> getAllCourses() throws Exception
    {
        try{
            List<Course> courses= this.jdbcTemplate.query("select * from course",CourseDao.MapRowToCourseLambda);
            logger.info("Retrieved all the courses");
            return courses;
        }
        catch (Exception e)
        {
            logger.error(e.getCause()+" in this method "+Thread.currentThread().getStackTrace()[1].getMethodName());
            throw e;
        }
    }

    public boolean deleteCourse(int courseId)throws Exception {
        /*
            Return
                1 - if success
                0 - if fails
         */
        try{
        final String deleteCourse = "delete from course where courseId= ? ";
        boolean isSkillsDeleted = skillServices.deleteAllSkills(courseId);
        boolean isMaterialDeleted = materialService.deleteAllMaterial(courseId);
        boolean isFeedBacksDeleted = feedbackService.deleteFeedbackByCourse(courseId);
        boolean isDeleted = false;
        if (isMaterialDeleted && isSkillsDeleted && isFeedBacksDeleted) {
            isDeleted = this.jdbcTemplate.update(deleteCourse, courseId) == 1;
            logger.info("Deleted the course with courseId="+courseId);
        }
        return isDeleted && isMaterialDeleted && isSkillsDeleted;
    }
        catch (Exception e)
        {
            logger.error(e.getCause()+" in this method "+Thread.currentThread().getStackTrace()[1].getMethodName());
            throw e;
        }

    }

    public static RowMapper<Course> MapRowToCourseLambda = ((resultSet, i)->{
        Course course= new Course();
        course.setCourseId(resultSet.getInt("courseId"));
        course.setUserId(resultSet.getInt("userId"));
        course.setCourseName(resultSet.getString("courseName"));
        course.setCourseDescription(resultSet.getString("courseDescription"));
        course.setPrerequisite(resultSet.getString("prerequisite"));
        course.setCreatedOn(resultSet.getTimestamp("createdOn"));
        course.setLastUpdated(resultSet.getTimestamp("lastUpdated"));
        course.setImageUrl(resultSet.getString("imageUrl"));
        return  course;
    }) ;

}
