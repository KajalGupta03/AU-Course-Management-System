package com.AU.backend.ServiceTest;


import com.AU.backend.Dao.FeedbackDao;
import com.AU.backend.Model.Feedback;
import com.AU.backend.Service.FeedbackService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class FeedbackServiceTest {

    @Mock
    FeedbackDao feedbackDao;

    @InjectMocks
    FeedbackService feedbackService;

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
        feedback1.setParticipantName("ABC");

        feedback2=new Feedback();
        feedback2.setFeedbackId(2);
        feedback2.setFeedbackText("Awesome");
        feedback2.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        feedback2.setCourseId(2);
        feedback2.setRating(5);
        feedback2.setParticipantName("DEF");

        feedbacks.add(feedback1);
        feedbacks.add(feedback2);
    }


    @Test
    public void addFeedBackTest() throws Exception{
        when(feedbackDao.addFeedback(feedback1)).thenReturn(feedback1);

        Feedback returnedFeedback=feedbackService.addFeedback(feedback1);
        assertEquals(feedback1,returnedFeedback);
    }


    @Test
    public void getFeedbackByCourseIdTest() throws Exception{
        when(feedbackDao.getFeedbackByCourseId(feedback1.getCourseId())).thenReturn(Arrays.asList(feedback1));

        List<Feedback> allfeedbacks=feedbackService.getFeedbackByCourseId(feedback1.getCourseId());
        assertEquals(Arrays.asList(feedback1),allfeedbacks);
    }


    @Test
    public void deleteFeedbackTest() throws Exception{
        when(feedbackDao.deleteFeedback(feedback1.getFeedbackId())).thenReturn(true);

        boolean isDeleted=feedbackService.deleteFeedback(feedback1.getFeedbackId());
        assertEquals(true,isDeleted);
    }

    @Test
    public void deleteFeedbackByCourseTest() throws Exception{
        when(feedbackDao.deleteFeedBackByCourse(feedback1.getCourseId())).thenReturn(true);

        boolean isDeleted=feedbackService.deleteFeedbackByCourse(feedback1.getCourseId());
        assertEquals(true,isDeleted);
    }

}
