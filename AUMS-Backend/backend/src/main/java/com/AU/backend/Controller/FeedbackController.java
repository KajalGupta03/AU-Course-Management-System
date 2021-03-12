package com.AU.backend.Controller;

import com.AU.backend.Model.Feedback;
import com.AU.backend.Service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {

    @Autowired
    FeedbackService feedbackService;


    @GetMapping("all/{courseId}")
    @CrossOrigin("*")
    public ResponseEntity<List<Feedback>> getAllFeedbackByCourse(@PathVariable ("courseId") int courseId)
    {
        try{
            return new ResponseEntity<>(feedbackService.getFeedbackByCourseId(courseId), HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/all")
    @CrossOrigin("*")
    public ResponseEntity<List<Feedback>> getAllFeedback() throws Exception {
        return new ResponseEntity<>(feedbackService.getAllFeedback(), HttpStatus.OK);
    }

    @GetMapping("{feedbackId}")
    @CrossOrigin("*")
    public ResponseEntity<Feedback> getFeedbackById(@PathVariable ("feedbackId") int feedbackId) throws Exception {
        return new ResponseEntity<>(feedbackService.getFeedbackById(feedbackId), HttpStatus.OK);
    }


    @DeleteMapping("delete/{feedbackId}")
    @CrossOrigin("*")
    public ResponseEntity<Boolean> deleteFeedback(@PathVariable ("feedbackId")int feedbackId)
    {
        try {
            return  new ResponseEntity<>(feedbackService.deleteFeedback(feedbackId),HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @PostMapping("/addFeedback")
    @CrossOrigin("*")
    public ResponseEntity<Feedback> addFeedback(@RequestBody Feedback feedback)
    {

        return new ResponseEntity<>(feedbackService.addFeedback(feedback),HttpStatus.OK);
    }

}