package com.AU.backend.Service;

import com.AU.backend.Dao.FeedbackDao;
import com.AU.backend.Model.Feedback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackService {

    @Autowired
    FeedbackDao feedbackDao;

    public Feedback addFeedback(Feedback feedback)
    {
        return feedbackDao.addFeedback(feedback);
    }

    public Feedback getFeedbackById(int feedbackId) throws Exception {
        return feedbackDao.getFeedbackById(feedbackId);
    }

    public List<Feedback> getFeedbackByCourseId(int courseId) throws Exception {
        return feedbackDao.getFeedbackByCourseId(courseId);
    }

    public List<Feedback> getAllFeedback() throws Exception {
        return feedbackDao.getAllFeedback();
    }

    public Boolean deleteFeedback(int feedbackId) throws Exception {
        return feedbackDao.deleteFeedback(feedbackId);
    }

    public  Boolean deleteFeedbackByCourse(int courseId) throws Exception {
        return feedbackDao.deleteFeedBackByCourse(courseId);
    }
}