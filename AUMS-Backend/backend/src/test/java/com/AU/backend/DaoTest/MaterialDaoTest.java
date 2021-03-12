package com.AU.backend.DaoTest;

import com.AU.backend.Dao.MaterialDao;
import com.AU.backend.Model.Material;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest()
public class MaterialDaoTest {

    @Mock
    JdbcTemplate jdbcTemplate;

    @InjectMocks
    MaterialDao materialDao;

    List<Material> materials=new ArrayList<>();

    Material material1;
    Material material2;
    Material material3;

    @BeforeEach
    void init(){
        material1 = new Material();
        material1.setMaterialId(1);
        material1.setMaterialDescription("Description 1");
        material1.setCourseId(1);
        material1.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        material1.setFileType("pdf");
        material1.setLastUpdated(new Timestamp(System.currentTimeMillis()));
        material1.setParentId(0);
        material1.setCurrent(true);

        material3 = new Material();
        material3.setMaterialId(3);
        material3.setMaterialDescription("Description 2");
        material3.setCourseId(1);
        material3.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        material3.setFileType("pdf");
        material3.setLastUpdated(new Timestamp(System.currentTimeMillis()));
        material3.setParentId(1);
        material3.setCurrent(true);


        material2 = new Material();
        material2.setMaterialId(2);
        material2.setMaterialDescription("Description 3");
        material2.setCourseId(2);
        material2.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        material2.setFileType("txt");
        material2.setLastUpdated(new Timestamp(System.currentTimeMillis()));
        material2.setParentId(0);
        material2.setCurrent(true);

        materials.add(material1);
        materials.add(material2);

    }

    @Test
    public void getMaterialByIdTest() throws Exception{
        when(this.jdbcTemplate.queryForObject("select * from materials where materialId="+material1.getMaterialId(), MaterialDao.MapRowToMaterialLambda)).thenReturn(material1);
        Material material = materialDao.getMaterialById(material1.getMaterialId());
        assertEquals(material1,material);
    }

    @Test
    public void getNumberOfVersions() throws Exception{
        when(this.jdbcTemplate.queryForObject("select count(*) from materials where courseId="+material1.getCourseId(),Integer.class)).thenReturn(1);
        int count = materialDao.getNumberOfVersions(material1.getCourseId());
        assertEquals(1,count);
    }

    @Test
    public void materialAlreadyExist() throws Exception{
        when(this.jdbcTemplate.queryForObject("select count(*) from materials where courseId="+material1.getCourseId(),Integer.class)).thenReturn(1);
        boolean exist = materialDao.materialAlreadyExist(material1.getCourseId());
        assertEquals(true, exist);
    }

    @Test
    public void getLatestMaterialByCourseId() throws Exception{
        when(this.jdbcTemplate.query("select * from materials where courseId = ? and isCurrent= ? ",MaterialDao.MapRowToMaterialLambda,material1.getCourseId(),material1.isCurrent())).thenReturn(Arrays.asList(material1));
        List<Material> materialTest = materialDao.getLatestMaterialByCourseId(material1.getCourseId());
        assertEquals(Arrays.asList(material1),materialTest);
    }

    @Test
    public void getAllMaterialsByCourseId() throws Exception{
        when(this.jdbcTemplate.queryForObject("select * from materials where courseId = ? and materialId= ? ",MaterialDao.MapRowToMaterialLambda,material1.getCourseId(),material1.getMaterialId())).thenReturn(material1);
        List<Material> materialTest = materialDao.getAllMaterialsByCourseId(material1.getCourseId(), material1.getMaterialId());
        assertEquals(Arrays.asList(material1), materialTest);
    }

    @Test
    public void deleteLatestVersionTest() throws Exception
    {

        when(this.jdbcTemplate.query("select * from materials where courseId = ? and isCurrent= ? ",MaterialDao.MapRowToMaterialLambda,material1.getCourseId(),material1.isCurrent())).thenReturn(Arrays.asList(material3,material1));
        when(this.jdbcTemplate.update("update materials set isCurrent=? where materialId=?",true,material3.getParentId())).thenReturn(0);
        when(this.jdbcTemplate.update("delete from materials where materialId=?",material3.getMaterialId())).thenReturn(1);

        boolean isDeleted1=materialDao.deleteLatestVersion(material3.getCourseId());
        assertEquals(true,isDeleted1);
    }

    @Test
    public void deleteAllMaterial() throws Exception{
        when(this.jdbcTemplate.update("delete from materials where courseId= ? ",material1.getCourseId())).thenReturn(1);
        when(this.jdbcTemplate.queryForObject("select count(*) from materials where courseId="+material1.getCourseId(),Integer.class)).thenReturn(1);
        boolean isDeleted = materialDao.deleteAllMaterial(material1.getCourseId());
        assertEquals(true,isDeleted);
    }

}
