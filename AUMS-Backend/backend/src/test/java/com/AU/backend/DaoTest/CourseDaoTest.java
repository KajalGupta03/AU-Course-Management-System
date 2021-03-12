package com.AU.backend.DaoTest;

import com.AU.backend.BackendApplication;
import com.AU.backend.Dao.CourseDao;
import com.AU.backend.Model.Course;
import com.AU.backend.Service.FeedbackService;
import com.AU.backend.Service.MaterialService;
import com.AU.backend.Service.SkillServices;
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
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = BackendApplication.class)
public class CourseDaoTest {

    @Mock
    JdbcTemplate jdbcTemplate;

    @InjectMocks
    CourseDao courseDao;

    @Mock
    SkillServices skillServices;

    @Mock
    FeedbackService feedbackService;

    @Mock
    MaterialService materialService;


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
        when(this.jdbcTemplate.query("select * from course", CourseDao.MapRowToCourseLambda)).thenReturn(courses);

        List<Course> allCourses=courseDao.getAllCourses();
        assertEquals(courses,allCourses);
    }

    @Test
    public void getCoursesByIdTest() throws Exception {
        when(this.jdbcTemplate.queryForObject("select * from course where courseId="+course1.getCourseId(),CourseDao.MapRowToCourseLambda)).thenReturn(course1);

        Course testCourse=courseDao.getCourseById(course1.getCourseId());
        assertEquals(course1,testCourse);
    }

    @Test
    public  void deleteCourseTest() throws Exception {
        lenient().when(this.jdbcTemplate.update("delete from course where courseId= ? ",course.getCourseId())).thenReturn(1);

        lenient().when(skillServices.deleteAllSkills(course.getCourseId())).thenReturn(true);
        lenient().when(feedbackService.deleteFeedbackByCourse(course.getCourseId())).thenReturn(true);
        lenient().when(materialService.deleteAllMaterial(course.getCourseId())).thenReturn(true);

        boolean isDeleted=courseDao.deleteCourse(course.getCourseId());
        assertEquals(true,isDeleted);

    }

}
