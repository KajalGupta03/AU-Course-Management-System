package com.AU.backend.Service;

import com.AU.backend.Dao.SkillCourseMappingDao;
import com.AU.backend.Dao.TrendsDao;
import com.AU.backend.Model.Trends;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrendsService {

    @Autowired
    TrendsDao trendsDao;

    public List<Trends> getTrendingSkills() throws Exception {
        return trendsDao.getTrendingSkills();
    }

    public List<Trends> getCourseRating() throws Exception {
        return trendsDao.getCourseRating();
    }
}
