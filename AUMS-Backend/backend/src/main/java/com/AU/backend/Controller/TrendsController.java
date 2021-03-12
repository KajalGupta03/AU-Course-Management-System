package com.AU.backend.Controller;

import com.AU.backend.Model.Trends;
import com.AU.backend.Service.SkillServices;
import com.AU.backend.Service.TrendsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/trends")
public class TrendsController {

    @Autowired
    TrendsService trendsService;

    @GetMapping("getTrendingSkills")
    @CrossOrigin("*")
    ResponseEntity<List<Trends>> getTrendingSkills() throws Exception
    {
        try{
            return  new ResponseEntity<>(trendsService.getTrendingSkills(),HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("getCourseRating")
    @CrossOrigin("*")
    ResponseEntity<List<Trends>> getCourseRating() throws Exception
    {
        try{
            System.out.println("****");
            return  new ResponseEntity<>(trendsService.getCourseRating(),HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}