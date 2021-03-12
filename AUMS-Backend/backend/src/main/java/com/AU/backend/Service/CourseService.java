package com.AU.backend.Service;

import com.AU.backend.Dao.CourseDao;
import com.AU.backend.Model.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    @Autowired
    CourseDao courseDao;

    public Course updateCourse(int courseId, Course updatedCourse) throws Exception {

        return courseDao.updateCourse(courseId,updatedCourse);
    }

    public Course addCourse(Course course) throws Exception {
        return courseDao.addCourse(course);
    }


    public Course getCourseById(int courseId) throws Exception {
        return courseDao.getCourseById(courseId);
    }

    public List<Course> getAllCourses() throws Exception {
        return courseDao.getAllCourses();
    }

    public Boolean deleteCourse(int courseId) throws Exception {
        return courseDao.deleteCourse(courseId);
    }

//    public List getAllCourseSkill() {
//    }
}
