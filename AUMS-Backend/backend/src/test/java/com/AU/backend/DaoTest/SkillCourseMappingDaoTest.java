package com.AU.backend.DaoTest;

import com.AU.backend.BackendApplication;
import com.AU.backend.Dao.SkillCourseMappingDao;
import com.AU.backend.Model.Skills;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = BackendApplication.class)
public class SkillCourseMappingDaoTest {


    @Mock
    JdbcTemplate jdbcTemplate;

    @InjectMocks
    SkillCourseMappingDao skillCourseMappingDao;

    @Test
    public  void addSkillinCourseTest() throws Exception {
        when(this.jdbcTemplate.update("insert into skillCourseMapping (courseId,skillId) values ( ?, ? )",1,2)).thenReturn(1);

        boolean isAdded=skillCourseMappingDao.addSKillsInCourse(1,2);
        assertEquals(true,isAdded);
    }


    @Test
    public void checkCourseExistTest() throws Exception {
        when(this.jdbcTemplate.queryForObject("select count(*) from skillCourseMapping where courseId = ? ",Integer.class,1)).thenReturn(1);

        Boolean isChecked=skillCourseMappingDao.checkCourseExist(1);

        assertEquals(true,isChecked);
    }

    @Test
    public void deleteSkillIncourseTest () throws Exception {
        when(this.jdbcTemplate.update("delete from skillCourseMapping where courseId= ? and skillId= ? ",1,2)).thenReturn(1);

        boolean isDeleted=skillCourseMappingDao.deleteSkillInCourse(1,2);
        assertEquals(true,isDeleted);
    }

    @Test
    public void deleteAllSKillsInCourseTest() throws Exception {
        when(this.jdbcTemplate.queryForObject("select count(*) from skillCourseMapping where courseId = ? ",Integer.class,1)).thenReturn(1);
        when(this.jdbcTemplate.update("delete from skillCourseMapping where courseId=?", 1)).thenReturn(1);

        boolean isDeleted=skillCourseMappingDao.deleteAllSKillsInCourse(1);
        assertEquals(true,isDeleted);
    }

    @Test
    public void getAllSkillsfromSkillTest() throws Exception {
        List<Skills> skills=new ArrayList<>();
        skills.add(new Skills("ang",1));
        skills.add(new Skills("ang1",2));
        when(this.jdbcTemplate.query("select * from skills", SkillCourseMappingDao.MapRowToSkillLambda)).thenReturn(skills);

        List<Skills> allSkills=skillCourseMappingDao.getAllSkillsAll();
        assertEquals(skills,allSkills);
    }


}