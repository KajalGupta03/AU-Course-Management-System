package com.AU.backend.DaoTest;

import com.AU.backend.BackendApplication;
import com.AU.backend.Dao.LoginDao;
import com.AU.backend.Dao.SkillsDao;
import com.AU.backend.Model.Skills;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = BackendApplication.class)
public class SkillDaoTest {

    @Mock
    private JdbcTemplate jdbcTemplate;


    @InjectMocks
    private SkillsDao skillsDao;

    Skills skill = new Skills();

    @BeforeEach
    public void init(){
        skill.setSkillId(1);
        skill.setSkillName("Java");
    }

    @Test
    public void getSkillsById() throws Exception{
        when(this.jdbcTemplate.queryForObject("select * from skills where skillId="+skill.getSkillId(),SkillsDao.MapRowToSkillLambda)).thenReturn(skill);
        Skills newskill = skillsDao.getSkillsById(skill.getSkillId());
        assertEquals(skill, newskill);
    }
}
