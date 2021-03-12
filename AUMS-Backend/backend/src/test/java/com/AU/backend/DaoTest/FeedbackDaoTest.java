package com.AU.backend.DaoTest;

import com.AU.backend.Dao.FeedbackDao;
import com.AU.backend.Model.Feedback;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest()
public class FeedbackDaoTest {

    @Mock
    JdbcTemplate jdbcTemplate;

    @InjectMocks
    FeedbackDao feedbackDao;

    List<Feedback> feedbacks=new ArrayList<>();

    Feedback feedback1;
    Feedback feedback2;


    @BeforeEach
    void init()
    {
        feedback1=new Feedback();
        feedback1.setFeedbackId(1);
        feedback1.setFeedbackText("Nice");
        feedback1.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        feedback1.setCourseId(1);
        feedback1.setRating(4);
        feedback1.setParticipantName("Jatin Kumar");

        feedback2=new Feedback();
        feedback2.setFeedbackId(2);
        feedback2.setFeedbackText("Awesome");
        feedback2.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        feedback2.setCourseId(2);
        feedback2.setRating(5);
        feedback2.setParticipantName("Lakshay ");

        feedbacks.add(feedback1);
        feedbacks.add(feedback2);
    }

    @Test
    public void getFeedbackByCourseId() throws Exception {
        when(this.jdbcTemplate.query("select * from feedbacks where courseId="+1, FeedbackDao.MapRowToFeedbackLambda)).thenReturn(feedbacks);

        List<Feedback> allFeedback=feedbackDao.getFeedbackByCourseId(1);
        assertEquals(feedbacks,allFeedback);
    }

    @Test
    public void getFeedback() throws Exception {
        when(this.jdbcTemplate.queryForObject("select * from feedbacks where feedbackId="+feedback1.getFeedbackId(),FeedbackDao.MapRowToFeedbackLambda)).thenReturn(feedback1);
        Feedback returnFeedback=feedbackDao.getFeedbackById(feedback1.getFeedbackId());
        assertEquals(feedback1,returnFeedback);
    }

    @Test
    public void getAllFeedback() throws Exception{
        when(this.jdbcTemplate.query("select * from feedbacks",FeedbackDao.MapRowToFeedbackLambda)).thenReturn(feedbacks);

        List<Feedback> allFeedacks=feedbackDao.getAllFeedback();
        assertEquals(feedbacks,allFeedacks);
    }

    @Test
    public void deletefeedbackTest() throws Exception {
        when(this.jdbcTemplate.update("delete from feedbacks where feedbackId="+feedback1.getFeedbackId())).thenReturn(1);

        boolean isDeleted=feedbackDao.deleteFeedback(feedback1.getFeedbackId());
        assertEquals(true,isDeleted);
    }


    @Test
    public void deleteFeedBackByCourseTest() throws Exception {
        when(this.jdbcTemplate.update("delete from feedbacks where courseId=?",feedback1.getCourseId())).thenReturn(1);

        boolean isDeleted=feedbackDao.deleteFeedBackByCourse(feedback1.getCourseId());

        assertEquals(true,isDeleted);
    }




}
