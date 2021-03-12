package com.AU.backend.Controller;

import com.AU.backend.Model.Skills;
import com.AU.backend.Service.SkillServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course")
public class SkillsController {

    @Autowired
    SkillServices skillServices;

    @PostMapping("/addSkillInCourse/{courseId}/{skillId}")
    @CrossOrigin("*")
    ResponseEntity<Boolean> addSKillInCourse(@PathVariable ("courseId") int courseId, @PathVariable("skillId") int skillId)
    {
        try{
            return new ResponseEntity<>(skillServices.addSkillInCourses(courseId,skillId),HttpStatus.CREATED);
        }
        catch (Exception e)
        {
            return  new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteSkillInCourse/{courseId}/{skillId}")
    @CrossOrigin("*")
    ResponseEntity<Boolean> deleteSkillInCourse(@PathVariable ("courseId") int courseId, @PathVariable("skillId") int skillId)
    {
        try {
            return new ResponseEntity<>(skillServices.deleteSkillInCourse(courseId, skillId),HttpStatus.OK);
        }
        catch (Exception e)
        {
            return  new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("getAllSkills/{courseId}")
    @CrossOrigin("*")
    ResponseEntity<List<String>> getAllSkills(@PathVariable ("courseId") int courseId)
    {
        try{
            return  new ResponseEntity<>(skillServices.getAllSkills(courseId),HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("getAllSkillsAll")
    @CrossOrigin("*")
    ResponseEntity<List<Skills>> getAllSkillsAll()
    {
        try{
            return  new ResponseEntity<>(skillServices.getAllSkillsAll(),HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
