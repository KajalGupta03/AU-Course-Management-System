package com.AU.backend.Dao;


import com.AU.backend.Model.Course;
import com.AU.backend.Model.Feedback;
import com.AU.backend.Model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Repository
public class FeedbackDao {

    Logger logger= LoggerFactory.getLogger(FeedbackDao.class);

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Feedback> getFeedbackByCourseId(int courseId) throws Exception
    {
        /*
            Returns the list of all the feedback for a specific course
         */
        try{
            final String getFeedbackByCourseId="select * from feedbacks where courseId="+courseId;
            List<Feedback> feedbacks= this.jdbcTemplate.query(getFeedbackByCourseId,FeedbackDao.MapRowToFeedbackLambda);
            logger.info("All the feedbacks for CourseID="+courseId+" retrieved successfully");
            return feedbacks;
        }
        catch (Exception e){
            logger.error(e.getCause()+" in this method "+Thread.currentThread().getStackTrace()[1].getLineNumber());
            throw e;
        }
    }

    public Feedback getFeedbackById(int feedbackId) throws Exception
    {

        try {
            final String getFeedbackById="select * from feedbacks where feedbackId="+feedbackId;
            Feedback feedback= this.jdbcTemplate.queryForObject(getFeedbackById,FeedbackDao.MapRowToFeedbackLambda);
            logger.info("Retrieved the feedback By feedbackId"+feedbackId);
            return  feedback;
        }
        catch (Exception e)
        {
            logger.error(e.getCause()+" in this method "+Thread.currentThread().getStackTrace()[1].getLineNumber());
            throw e;
        }
    }


    public List<Feedback> getAllFeedback() throws Exception
    {
        try
        {
            final String getAll="select * from feedbacks";
            List<Feedback> feedbacks=this.jdbcTemplate.query(getAll,FeedbackDao.MapRowToFeedbackLambda);
            logger.info("Successfully retrieved all the feedbacks ");
            return feedbacks;
        }
        catch (Exception e)
        {
            logger.error(e.getCause()+" in this method "+Thread.currentThread().getStackTrace()[1].getLineNumber());
            throw e;
        }
    }

    public Boolean deleteFeedback(int feedbackId) throws Exception
    {
        /*
            Returns
              False - If fails to delete
              True - If delete was success
         */
        try{
            final String deleteFeedbackQuery="delete from feedbacks where feedbackId="+feedbackId;
            boolean isDeleted= this.jdbcTemplate.update(deleteFeedbackQuery) == 1 ;
            logger.info("delete FeedBack Query was successfully executed");
            return isDeleted;
        }
        catch (Exception e)
        {
            logger.error(e.getCause()+" in this method "+Thread.currentThread().getStackTrace()[1].getLineNumber());
            throw e;
        }
    }

    public Boolean deleteFeedBackByCourse(int courseId) throws Exception
    {
        try{
            if(getFeedbackByCourseId(courseId).size()>0)
            {
                Boolean isDeleted=this.jdbcTemplate.update("delete from feedbacks where courseId=?", Integer.class, courseId) >= 1;
                logger.info("Query deleteFeedbackByCourseID ran Successfully");
                return isDeleted;
            }
            else
                logger.info("Feedback for courseId="+courseId+" don't exist");
            return true;
        }
        catch (Exception e)
        {
            logger.error(e.getCause()+" in this method "+Thread.currentThread().getStackTrace()[1].getLineNumber());
            throw e;
        }
    }

    public Feedback addFeedback(Feedback feedback)
    {
        /*
            Returns the newly added feedback object
         */
        KeyHolder keyHolder=new GeneratedKeyHolder();
        final  String addNewFeedBack="insert into feedbacks (participantName,courseId,feedbackText,rating) values (?,?,?,?)";
        this.jdbcTemplate.update(connection -> {
            PreparedStatement stmt=connection.prepareStatement(addNewFeedBack, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1,feedback.getParticipantName());
            stmt.setInt(2,feedback.getCourseId());
            stmt.setString(3,feedback.getFeedbackText());
            stmt.setFloat(4,feedback.getRating());
            return stmt;
        },keyHolder);
        return jdbcTemplate.queryForObject("select * from feedbacks where feedbackId="+keyHolder.getKey(),FeedbackDao.MapRowToFeedbackLambda);
    }

    public static RowMapper<Feedback> MapRowToFeedbackLambda = ((resultSet, i)->{
        Feedback feedback= new Feedback();
        feedback.setFeedbackId(resultSet.getInt("feedbackId"));
        feedback.setFeedbackText(resultSet.getString("feedbackText"));
        feedback.setCourseId(resultSet.getInt("courseId"));
        feedback.setCreatedAt(resultSet.getTimestamp("createdAt"));
        feedback.setRating(resultSet.getFloat("rating"));
        feedback.setParticipantName(resultSet.getString("participantName"));
        return  feedback;
    });
}
