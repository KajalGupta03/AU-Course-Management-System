package com.AU.backend.Dao;

import com.AU.backend.Model.Course;
import com.AU.backend.Model.Skills;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;


@Repository
public class SkillsDao {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public Skills getSkillsById(int skillId)
    {
        return this.jdbcTemplate.queryForObject("select * from skills where skillId="+skillId,SkillsDao.MapRowToSkillLambda);
    }

    public static RowMapper<Skills> MapRowToSkillLambda = ((resultSet, i)->{
        Skills skills= new Skills();
        skills.setSkillId(resultSet.getInt("skillId"));
        skills.setSkillName(resultSet.getString("skillName"));

        return  skills;
    });

}
