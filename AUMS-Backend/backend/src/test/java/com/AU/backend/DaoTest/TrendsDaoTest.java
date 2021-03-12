package com.AU.backend.DaoTest;

import com.AU.backend.BackendApplication;
import com.AU.backend.Dao.LoginDao;
import com.AU.backend.Dao.SkillsDao;
import com.AU.backend.Dao.TrendsDao;
import com.AU.backend.Model.Skills;
import com.AU.backend.Model.Trends;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = BackendApplication.class)
public class TrendsDaoTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    TrendsDao trendsDao;
    Trends trends1 = new Trends();
    Trends trends2 = new Trends();

    List<Trends> trendsList = new ArrayList<>();
    @BeforeEach
    public void init(){
        trends1.setValue(1);
        trends1.setName("java");

        trends2.setName("c++");
        trends2.setValue(2);

        trendsList.add(trends1);
        trendsList.add(trends2);
    }

    @Test
    public void getTrendingSkills() throws Exception {

        when(this.jdbcTemplate.query("select count(*), skillName from skillCourseMapping natural join skills group by skills.skillName;", TrendsDao.MapRowToTrendsLambda)).thenReturn(trendsList);
        List<Trends> trendstest = trendsDao.getTrendingSkills();
        assertEquals(trendsList, trendstest);

    }

    @Test
    public void getCourseRating() throws Exception{
        when(this.jdbcTemplate.query("select avg(rating), courseName from feedbacks natural join course group by courseId;", TrendsDao.MapRowToTrendsLambda)).thenReturn(trendsList);
        List<Trends> trendstest = trendsDao.getCourseRating();
        assertEquals(trendsList, trendstest);
    }



}
