package com.AU.backend.ControllerTest;

import com.AU.backend.Controller.SkillsController;
import com.AU.backend.Model.Course;
import com.AU.backend.Model.Skills;
import com.AU.backend.Service.CourseService;
import com.AU.backend.Service.SkillServices;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.lenient;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = SkillsController.class)
public class SkillControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CourseService courseServices;

    @Autowired
    ObjectMapper objectMapper;


    @MockBean
    SkillServices skillServices;

    Course course = new Course();
    Course course1 = new Course();
    List<Course> courses = new ArrayList<>();

    @BeforeEach
    void init() {
        course.setImageUrl("img");
        course.setCourseName("angular");
        course.setCourseDescription("description1");
        course.setPrerequisite("pre1");
        course.setCourseId(1);
        course.setCreatedOn(new Timestamp(System.currentTimeMillis()));
        course.setLastUpdated(new Timestamp(System.currentTimeMillis()));


        course1.setImageUrl("img2");
        course1.setCourseName("angular2");
        course1.setCourseDescription("description2");
        course1.setPrerequisite("pre2");
        course1.setCourseId(2);
        course1.setCreatedOn(new Timestamp(System.currentTimeMillis()));
        course1.setLastUpdated(new Timestamp(System.currentTimeMillis()));

        courses.add(course);
        courses.add(course1);
    }

    @Test
    public void addSkillInCourse() throws Exception {
        lenient().when(skillServices.addSkillInCourses(course1.getCourseId(), 1)).thenReturn(true);

        mockMvc.perform(post("/course/addSkillInCourse/"+String.valueOf(course1.getCourseId())+"/"+String.valueOf(1)))
                .andDo(print())
                .andExpect(status().isCreated());
    }


    @Test
    public void deleteSkillInCourseTest() throws Exception {
        lenient().when(skillServices.deleteSkillInCourse(course1.getCourseId(), 1)).thenReturn(true);

        mockMvc.perform(delete("/course/deleteSkillInCourse/"+String.valueOf(course1.getCourseId())+"/"+String.valueOf(1)))
                .andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    public void getAllSkills() throws Exception {

        lenient().when(skillServices.getAllSkills(course1.getCourseId())).thenReturn(Arrays.asList("Angular", "java"));

        mockMvc.perform(get("/course/getAllSkills/" + course1.getCourseId()))
                .andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    public void getSkillsTest() throws Exception {
        lenient().when(skillServices.getAllSkillsAll()).thenReturn(Arrays.asList(new Skills("angular", 1), new Skills("java", 2)));

        mockMvc.perform(get("/course/getAllSkillsAll"))
                .andDo(print())
                .andExpect(status().isOk());

    }
}
