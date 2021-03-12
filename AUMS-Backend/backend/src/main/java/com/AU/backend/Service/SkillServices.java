package com.AU.backend.Service;

import com.AU.backend.Dao.SkillCourseMappingDao;
import com.AU.backend.Model.Skills;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkillServices
{
    @Autowired
    SkillCourseMappingDao skillCourseMappingDao;

    public boolean addSkillInCourses(int courseId,int skillId) throws Exception {
        return skillCourseMappingDao.addSKillsInCourse(courseId,skillId);
    }

    public boolean deleteSkillInCourse(int courseId,int skillId) throws Exception {
        return skillCourseMappingDao.deleteSkillInCourse(courseId, skillId);
    }

    public List<String> getAllSkills(int courseId) throws Exception {
        return skillCourseMappingDao.getAllSkills(courseId);
    }

    public Boolean deleteAllSkills(int courseId) throws Exception {
        return skillCourseMappingDao.deleteAllSKillsInCourse(courseId);
    }

    public List<Skills> getAllSkillsAll() throws Exception {
        return skillCourseMappingDao.getAllSkillsAll();
    }
}