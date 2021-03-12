package com.AU.backend.Controller;


import com.AU.backend.Service.CourseService;
import com.AU.backend.Model.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    CourseService courseService;
    @PostMapping("/addCourse")
    @CrossOrigin("*")
    public ResponseEntity<Course> addCourse (@RequestBody Course course)
    {
        try {
            return new ResponseEntity<>(courseService.addCourse(course),HttpStatus.CREATED);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/all")
    @CrossOrigin("*")
    ResponseEntity<List<Course>> getAllCourses()
    {
        try {
            return new ResponseEntity<>(courseService.getAllCourses(), HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("deleteCourse/{courseId}")
    @CrossOrigin("*")
    ResponseEntity<Boolean> deleteCourse(@PathVariable ("courseId") int courseId)
    {
        try{
            return new ResponseEntity<>(courseService.deleteCourse(courseId),HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("updateCourse/{courseId}")
    @CrossOrigin("*")
    ResponseEntity<Course> updateCourse(@PathVariable ("courseId") int courseId,@RequestBody Course updatedCourse)
    {
        try
        {
            return new ResponseEntity<>(courseService.updateCourse(courseId , updatedCourse),HttpStatus.OK);
        }
        catch (Exception e)
        {
            return  new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("getById/{Id}")
    @CrossOrigin("*")
    ResponseEntity<Course> getCourseById(@PathVariable ("Id") int courseId)
    {
        try{
            return new ResponseEntity<>(courseService.getCourseById(courseId),HttpStatus.OK);
        }
        catch (Exception e)
        {
            return  new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @GetMapping("/getAllCourseSkill")
//    @CrossOrigin("*")
//    ResponseEntity<Course> getAllCourseSkill()
//    {
//        return new ResponseEntity<>(courseService.getAllCourseSkill(),HttpStatus.OK);
//    }

}