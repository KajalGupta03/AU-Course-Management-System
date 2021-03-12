package com.AU.backend.ServiceTest;

import com.AU.backend.Dao.SkillCourseMappingDao;
import com.AU.backend.Model.Skills;
import com.AU.backend.Service.SkillServices;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class SkillServiceTest {

    @Mock
    SkillCourseMappingDao skillCourseMappingDao;

    @InjectMocks
    SkillServices skillServices;

    @Test
    public void addSkillInCourseTest() throws Exception{
        when(skillCourseMappingDao.addSKillsInCourse(1,2)).thenReturn(true);

        boolean isAdded=skillServices.addSkillInCourses(1,2);
        assertEquals(true,isAdded);
    }


    @Test
    public void  deleteSkillInCourseTest() throws Exception
    {
        when(skillCourseMappingDao.deleteSkillInCourse(1,2)).thenReturn(true);

        boolean isDeleted=skillServices.deleteSkillInCourse(1,2);
        assertEquals(true,isDeleted);
    }

    @Test
    public void deleteAlSkillsTest() throws Exception
    {
        when(skillCourseMappingDao.deleteAllSKillsInCourse(1)).thenReturn(true);

        boolean isAllDeleted=skillServices.deleteAllSkills(1);
        assertEquals(true,isAllDeleted);
    }

    @Test
    public void getAllSkillsTest() throws Exception
    {
        when(skillCourseMappingDao.getAllSkills(1)).thenReturn(Arrays.asList("angular","java"));

        List<String> skills=skillServices.getAllSkills(1);
        assertEquals(Arrays.asList("angular","java"),skills);
    }

    @Test
    public void getAllSkillFromSkill() throws Exception{
        Skills skill1=new Skills("angular",1);
        Skills skill2=new Skills("Java",2);

        when(skillCourseMappingDao.getAllSkillsAll()).thenReturn(Arrays.asList(skill1,skill2));

        List<Skills> allSkills=skillServices.getAllSkillsAll();
        assertEquals(Arrays.asList(skill1,skill2),allSkills);
    }

}
