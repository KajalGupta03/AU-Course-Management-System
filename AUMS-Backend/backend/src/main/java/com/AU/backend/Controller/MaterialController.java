package com.AU.backend.Controller;

import com.AU.backend.Model.Material;
import com.AU.backend.Service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/material")
public class MaterialController {
    @Autowired
    MaterialService materialService;

    @PostMapping("/addMaterial")
    @CrossOrigin("*")
    ResponseEntity<Object> addNewMaterial(@RequestParam("courseId") int courseID, @RequestParam("materialDescription") String materialDescription, @RequestParam("fileType") String fileType, @RequestParam("file") MultipartFile[] files) {
        List<Material> materials = new ArrayList<>();
        for (MultipartFile file : files) {
            Material material = new Material();
            material.setCourseId(courseID);
            material.setMaterialDescription(materialDescription);
            material.setFileType(file.getContentType());
//            material.setFileName(file.getOriginalFilename());
            if (file.isEmpty()) {
                return new ResponseEntity<>("File is not Uploaded", HttpStatus.BAD_REQUEST);
            }
            try {
                materials.add(materialService.addMaterial(material, file));
                //return new ResponseEntity<>(materialService.addMaterial(material,file), HttpStatus.OK);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<>(materials, HttpStatus.OK);
}


    @PutMapping("updateMaterial")
    @CrossOrigin("*")
    ResponseEntity<Object> updateMaterial(@RequestParam("previousMaterialId") int previousMaterialId,@RequestParam("courseId") int courseID,@RequestParam("materialDescription") String materialDescription ,@RequestParam("fileType") String fileType  , @RequestParam("file") MultipartFile file) throws Exception {
        Material material=new Material();
        material.setCourseId(courseID);
        material.setMaterialDescription(materialDescription);
        material.setFileType(fileType);
        return new ResponseEntity<>(materialService.updateMaterial(material,file,previousMaterialId),HttpStatus.OK);
    }


    @GetMapping("getLatestMaterial/{courseId}")
    @CrossOrigin("*")
    ResponseEntity<List<Material>> getMaterialByCourse(@PathVariable("courseId") int courseId) throws Exception {
        return new ResponseEntity<>(materialService.getLatestMaterialByCourse(courseId),HttpStatus.OK);
    }


    @GetMapping("getAllMaterial/{courseId}/{materialId}")
    @CrossOrigin("*")
    ResponseEntity<List<Material>> getAllMaterialByCourse(@PathVariable("courseId") int courseId,@PathVariable("materialId") int materialId) throws Exception {
        return new ResponseEntity<>(materialService.getAllMaterialByCourse(courseId,materialId),HttpStatus.OK);
    }

    @DeleteMapping("deleteLatest/{courseId}")
    @CrossOrigin("*")
    ResponseEntity<Boolean> deleteLatestMaterial(@PathVariable("courseId") int courseId) throws Exception {
        return new ResponseEntity<>(materialService.deleteLatestMaterial(courseId),HttpStatus.OK);
    }

}