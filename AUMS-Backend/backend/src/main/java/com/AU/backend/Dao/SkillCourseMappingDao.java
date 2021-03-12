package com.AU.backend.Dao;

import com.AU.backend.Model.Feedback;
import com.AU.backend.Model.Skills;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class SkillCourseMappingDao {

    Logger logger= LoggerFactory.getLogger(SkillCourseMappingDao.class);

    @Autowired
    JdbcTemplate jdbcTemplate;

    public boolean addSKillsInCourse(int courseId,int skillId) throws Exception
    {
        try{
            final String addSkillQuery="insert into skillCourseMapping (courseId,skillId) values ( ?, ? )";
            boolean isSkillAdded= this.jdbcTemplate.update(addSkillQuery,courseId,skillId)==1;
            logger.info("Skill with skillId = "+skillId+" in course with courseId = "+courseId);
            return isSkillAdded;
        }
        catch (Exception e)
        {
            logger.error(e.getCause()+" in this method "+Thread.currentThread().getStackTrace()[1].getMethodName());
            throw e;
        }
    }

    public boolean checkCourseExist(int courseId)throws Exception
    {
        try{
            boolean doesCourseExist=this.jdbcTemplate.queryForObject("select count(*) from skillCourseMapping where courseId = ? ",Integer.class,courseId)>=1;
            logger.info("Retrieved if any skill exist for a course with courseID="+courseId);
            return  doesCourseExist;
        }
        catch (Exception e)
        {
            logger.error(e.getCause()+" in this method "+Thread.currentThread().getStackTrace()[1].getMethodName());
            throw e;
        }
    }

    public boolean deleteSkillInCourse(int courseId,int skillId) throws Exception
    {
        try{
            final String deleteSkillQuery="delete from skillCourseMapping where courseId= ? and skillId= ? ";
            boolean isDeleted= this.jdbcTemplate.update(deleteSkillQuery,courseId,skillId)==1;
            logger.info("Deleted skill from skillCourseMapping with skillId="+skillId+" with courseID="+courseId);
            return isDeleted;
        }
        catch (Exception e)
        {
            logger.error(e.getCause()+" in this method "+Thread.currentThread().getStackTrace()[1].getMethodName());
            throw e;
        }
    }

    public Boolean deleteAllSKillsInCourse(int courseId) throws Exception
    {
        try{
            if(checkCourseExist(courseId)) {
                boolean isDeleted= this.jdbcTemplate.update("delete from skillCourseMapping where courseId=?", courseId) >= 1;
                logger.info("Deleted all skills from skillCourseMapping with courseID="+courseId);
                return isDeleted;
            }
            else
                return true;
        }
        catch (Exception e)
        {
            logger.error(e.getCause()+" in this method "+Thread.currentThread().getStackTrace()[1].getMethodName());
            throw e;
        }
    }

    public List<String> getAllSkills(int courseId) throws Exception
    {
        try{
            final String getAllSkill= " select skillName from skillCourseMapping ,skills  where courseId ="+courseId+"  and skillCourseMapping.skillId=skills.skillId ";
            List<String> skills=this.jdbcTemplate.query(getAllSkill, new RowMapper<String>() {
                @Override
                public String mapRow(ResultSet resultSet, int i) throws SQLException {
                    return resultSet.getString("skillName");
                }
            });
            logger.info("Retrieve all skills of courseId="+courseId);
            return skills;
        }
        catch (Exception e)
        {
            logger.error(e.getCause()+" in this method "+Thread.currentThread().getStackTrace()[1].getMethodName());
            throw e;
        }
    }

    public List<Skills> getAllSkillsAll() throws Exception {
        try{
            List<Skills> allSkills=this.jdbcTemplate.query("select * from skills",SkillCourseMappingDao.MapRowToSkillLambda);
            return allSkills;
        }
        catch (Exception e){
            logger.info(e.getCause()+" in this method " +Thread.currentThread().getStackTrace()[1].getMethodName());
            throw e;
        }
    }

    public static RowMapper<Skills> MapRowToSkillLambda = ((resultSet, i)->{
        Skills skills = new Skills();
        skills.setSkillId(resultSet.getInt("skillId"));
        skills.setSkillName(resultSet.getString("skillName"));
        return  skills;
    });
}