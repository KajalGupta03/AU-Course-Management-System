package com.AU.backend.Model;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @ToString
public class Material {
    private int materialId;
    private int courseId;
    private int parentId;
    private boolean isCurrent;
    private Timestamp createdAt;
    private Timestamp lastUpdated;
    private String materialDescription;
    private String fileType;
    private byte[] fileData;

    public byte[] getFileData() {
        return fileData;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }




}