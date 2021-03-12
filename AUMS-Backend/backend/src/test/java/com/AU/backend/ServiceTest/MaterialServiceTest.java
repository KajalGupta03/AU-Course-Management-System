package com.AU.backend.ServiceTest;

import com.AU.backend.Dao.MaterialDao;
import com.AU.backend.Model.Material;
import com.AU.backend.Service.MaterialService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class MaterialServiceTest {

    @Mock
    MaterialDao materialDao;

    @InjectMocks
    MaterialService materialService;


    List<Material> materials=new ArrayList<>();

    Material material1;
    Material material2;
    Material material3;

    MultipartFile multipartFile = new MockMultipartFile("test.txt", new FileInputStream(new File("C:\\Users\\kajal\\Desktop\\testingController.txt")));

    public MaterialServiceTest() throws IOException {
    }


    @BeforeEach
    void init(){
        material1 = new Material();
        material1.setMaterialId(1);
        material1.setMaterialDescription("Description1");
        material1.setCourseId(1);
        material1.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        material1.setFileType("pdf");
        material1.setLastUpdated(new Timestamp(System.currentTimeMillis()));
        material1.setParentId(0);
        material1.setCurrent(true);

        material3 = new Material();
        material3.setMaterialId(3);
        material3.setMaterialDescription("Description2");
        material3.setCourseId(1);
        material3.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        material3.setFileType("pdf");
        material3.setLastUpdated(new Timestamp(System.currentTimeMillis()));
        material3.setParentId(1);
        material3.setCurrent(true);


        material2 = new Material();
        material2.setMaterialId(2);
        material2.setMaterialDescription("Description3");
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
    public void addMaterialTest() throws Exception{
        when(materialDao.addMaterial(material1,multipartFile)).thenReturn(material1);

        Material returnedMaterial=materialService.addMaterial(material1,multipartFile);
        assertEquals(material1,returnedMaterial);
    }

    @Test
    public void updateMaterialTest() throws Exception{
        when(materialDao.updateMaterial(material3,multipartFile,material1.getMaterialId())).thenReturn(material3);

        Material newMaterial=materialService.updateMaterial(material3,multipartFile,material1.getMaterialId());
        assertEquals(material3,newMaterial);
    }


    @Test
    public void getLatestMaterialByCourseTest() throws Exception{
        when(materialDao.getLatestMaterialByCourseId(material1.getCourseId())).thenReturn(Arrays.asList(material3,material2));

        List<Material> allMaterials=materialService.getLatestMaterialByCourse(material3.getCourseId());
        assertEquals(Arrays.asList(material3,material2),allMaterials);
    }

    @Test
    public void getAllMaterialByCourse() throws Exception{
        when(materialDao.getAllMaterialsByCourseId(material3.getCourseId(),material3.getMaterialId())).thenReturn(Arrays.asList(material3,material1));

        List<Material> allPreviousMaterials=materialService.getAllMaterialByCourse(material3.getCourseId(),material3.getMaterialId());
        assertEquals(Arrays.asList(material3,material1),allPreviousMaterials);

    }


    @Test
    public void deleteLatestMaterialTest() throws Exception {
        when(materialDao.deleteLatestVersion(material3.getMaterialId())).thenReturn(true);
        boolean isDeleted=materialService.deleteLatestMaterial(material3.getMaterialId());
        assertEquals(true,isDeleted);
    }

    @Test
    public void deleteAllMaterial() throws Exception{
        when(materialDao.deleteAllMaterial(material3.getCourseId())).thenReturn(true);
        boolean isDeleted=materialService.deleteAllMaterial(material3.getCourseId());
        assertEquals(true,isDeleted);
    }
}
