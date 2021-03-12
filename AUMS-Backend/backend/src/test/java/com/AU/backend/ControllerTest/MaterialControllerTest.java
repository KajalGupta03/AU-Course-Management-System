package com.AU.backend.ControllerTest;

import com.AU.backend.Controller.MaterialController;
import com.AU.backend.Model.Material;
import com.AU.backend.Service.MaterialService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.lenient;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MaterialController.class)
public class MaterialControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    MaterialService materialService;

    List<Material> materials=new ArrayList<>();

    Material material1;
    Material material2;
    Material material3;


    MultipartFile multipartFile = new MockMultipartFile("test.txt", new FileInputStream(new File("D:\\Linux\\Documents\\Photo\\_DSC6799.jpg")));

    public MaterialControllerTest() throws IOException {
    }

    @BeforeEach
    void init(){
        material1 = new Material();
        material1.setMaterialId(1);
        material1.setMaterialDescription("description1");
        material1.setCourseId(1);
        material1.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        material1.setFileType("pdf");
        material1.setLastUpdated(new Timestamp(System.currentTimeMillis()));
        material1.setParentId(0);
        material1.setCurrent(true);

        material3 = new Material();
        material3.setMaterialId(3);
        material3.setMaterialDescription("description3");
        material3.setCourseId(1);
        material3.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        material3.setFileType("pdf");
        material3.setLastUpdated(new Timestamp(System.currentTimeMillis()));
        material3.setParentId(1);
        material3.setCurrent(true);


        material2 = new Material();
        material2.setMaterialId(2);
        material2.setMaterialDescription("description3");
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
    public void addNewMaterialTest() throws Exception{
        lenient().when(materialService.addMaterial(material1,multipartFile)).thenReturn(material1);

        mockMvc.perform(multipart("/material/addMaterial")
                .file("file", multipartFile.getBytes())
                .param("courseId",String.valueOf(material1.getCourseId()))
                .param("materialDescription",material1.getMaterialDescription())
                .param("fileType", material1.getFileType()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void deleteLatestTest() throws Exception {
        lenient().when(materialService.deleteLatestMaterial(material1.getCourseId())).thenReturn(true);

        mockMvc.perform(delete("/material/deleteLatest/"+material1.getCourseId()))
                .andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    public void getAllMaterialByCourseTest() throws Exception
    {
        lenient().when(materialService.getAllMaterialByCourse(material3.getCourseId(),material3.getMaterialId())).thenReturn(Arrays.asList(material3,material1));

        mockMvc.perform(get("/material/getAllMaterial/"+material3.getCourseId()+"/"+material3.getMaterialId()))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    public void getMaterialByCourseTest() throws Exception
    {
        lenient().when(materialService.getLatestMaterialByCourse(material3.getCourseId())).thenReturn(Arrays.asList(material3,material2));

        mockMvc.perform(get("/material/getLatestMaterial/"+material3.getCourseId()))
                .andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    public  void updateMaterialTest() throws Exception{
        lenient().when(materialService.updateMaterial(material3,multipartFile,material1.getMaterialId())).thenReturn(material3);

        mockMvc.perform(multipart("/material/updateMaterial")
                .file("file",multipartFile.getBytes())
                .param("previousMaterialId",String.valueOf(material1.getMaterialId()))
                .param("courseId",String.valueOf(material1.getCourseId()))
                .param("fileType", material1.getFileType())
                .param("materialDescription",material3.getMaterialDescription()).with(new RequestPostProcessor() {
                    @Override
                    public MockHttpServletRequest postProcessRequest(MockHttpServletRequest mockHttpServletRequest) {
                        mockHttpServletRequest.setMethod("PUT");
                        return mockHttpServletRequest;
                    }
                }))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
