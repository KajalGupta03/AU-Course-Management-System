package com.AU.backend.ControllerTest;

import com.AU.backend.Controller.CourseController;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CourseController.class)
public class CourseControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CourseService courseServices;

    @Autowired
    ObjectMapper objectMapper;


    @MockBean
    SkillServices skillServices;

    Course course=new Course();
    Course course1=new Course();
    List<Course> courses=new ArrayList<>();

    @BeforeEach
    void init()
    {
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
    public void getAllCourseTest() throws Exception {
        lenient().when(courseServices.getAllCourses()).thenReturn(courses);
        mockMvc.perform(get("/course/all")).andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    public void getCourseByIdTest() throws Exception{
        lenient().when(courseServices.getCourseById(course1.getCourseId())).thenReturn(course1);

        mockMvc.perform(get("/course/getById/"+course1.getCourseId()))
                .andDo(print()).andExpect(status().isOk());
    }


    @Test
    public void updateCourseTest() throws Exception{
        lenient().when(courseServices.updateCourse(course1.getCourseId(),course)).thenReturn(course);

        String jsonString=objectMapper.writeValueAsString(course);
        mockMvc.perform(put("/course/updateCourse/"+course1.getCourseId())
                .contentType(MediaType.APPLICATION_JSON).content(jsonString))
                .andDo(print()).andExpect(status().isOk());
    }


    @Test
    public void deleteCourse() throws Exception{
        lenient().when(courseServices.deleteCourse(course1.getCourseId())).thenReturn(true);

        mockMvc.perform(delete("/course/deleteCourse/"+course1.getCourseId())).andDo(print()).andExpect(status().isOk());
    }


    @Test
    public void addCourse() throws Exception{
        lenient().when(courseServices.addCourse(course)).thenReturn(course);

        String jsonString=objectMapper.writeValueAsString(course);
        mockMvc.perform(post("/course/addCourse")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString))
                .andDo(print())
                .andExpect(status().isCreated());
    }

}