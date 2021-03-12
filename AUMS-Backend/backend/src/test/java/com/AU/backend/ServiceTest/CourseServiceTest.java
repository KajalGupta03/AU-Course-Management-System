package com.AU.backend.ServiceTest;

import com.AU.backend.Dao.CourseDao;
import com.AU.backend.Model.Course;
import com.AU.backend.Service.CourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@SpringBootTest()
public class CourseServiceTest {

    @Mock
    CourseDao courseDao;

    @InjectMocks
    CourseService courseServices;


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
    public void getAllCoursesTest() throws Exception
    {
        lenient().when(courseDao.getAllCourses()).thenReturn(courses);

        List<Course> allCourses=courseServices.getAllCourses();

        assertEquals(courses,allCourses);
    }


    @Test
    public void getCourseByIdTest() throws Exception
    {
        when(courseDao.getCourseById(course.getCourseId())).thenReturn(course);

        Course returnedCourse=courseServices.getCourseById(course.getCourseId());
        assertEquals(course,returnedCourse);
    }

    @Test
    public void updateCourse() throws Exception
    {
        when(courseDao.updateCourse(course.getCourseId(),course1)).thenReturn(course1);

        Course newCourse=courseServices.updateCourse(course.getCourseId(),course1);
        assertEquals(course1,newCourse);
    }

    @Test
    public  void deleteCourseTest() throws Exception
    {
        when(courseDao.deleteCourse(course.getCourseId())).thenReturn(true);

        Boolean isDeleted=courseServices.deleteCourse(course.getCourseId());
        assertEquals(true,isDeleted);
    }


    @Test
    public  void addCourseTest() throws Exception {
        when(courseDao.addCourse(course)).thenReturn(course);

        Course addedCourse=courseServices.addCourse(course);
        assertEquals(course,addedCourse);
    }

}