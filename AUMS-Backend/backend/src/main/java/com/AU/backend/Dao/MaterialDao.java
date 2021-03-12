package com.AU.backend.Dao;

import com.AU.backend.Model.Course;
import com.AU.backend.Model.Material;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigInteger;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class MaterialDao {
    @Autowired
    JdbcTemplate jdbcTemplate;

    Logger logger = LoggerFactory.getLogger(MaterialDao.class);

    public Material getMaterialById(int materialId)
    {
        try{
            Material material= this.jdbcTemplate.queryForObject("select * from materials where materialId="+materialId,MaterialDao.MapRowToMaterialLambda);
            logger.info("Retrieved material with materialId="+materialId);
            return  material;
        }
        catch (Exception e)
        {
            logger.error(e.getCause()+" in this method "+Thread.currentThread().getStackTrace()[1].getMethodName());
            throw e;
        }
    }

    public int getNumberOfVersions(int courseId)
    {
        return this.jdbcTemplate.queryForObject("select count(*) from materials where courseId="+courseId,Integer.class);
    }

    public boolean materialAlreadyExist(int courseId)
    {
        return this.jdbcTemplate.queryForObject("select count(*) from materials where courseId="+courseId,Integer.class)>0;
    }

    public Material addMaterial(Material newMaterial,MultipartFile file) throws Exception{

        final String addMaterialQuery="insert into materials( courseId,materialDescription,fileType,fileData,createdAt, lastUpdated ) values ( ? , ? , ? , ? , ?, ?)";

        java.util.Date utilDate = new java.util.Date();
        java.sql.Timestamp timestamp = new java.sql.Timestamp(utilDate.getTime());

        KeyHolder keyHolder=new GeneratedKeyHolder();
        this.jdbcTemplate.update(connection -> {
            PreparedStatement stmt=connection.prepareStatement(addMaterialQuery, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1,newMaterial.getCourseId());
            stmt.setString(2,newMaterial.getMaterialDescription());
            stmt.setString(3, newMaterial.getFileType());
            try {
                stmt.setBinaryStream(4,file.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            stmt.setTimestamp(5,timestamp);
            stmt.setTimestamp(6, timestamp);
            return stmt;
        },keyHolder);

        int materialId=(int) keyHolder.getKey();
        return getMaterialById(materialId);
    }



    public Material updateMaterial(Material newMaterial, MultipartFile newFile, int previousMaterialId) throws Exception
    {

        try{

            String getLastVersion="select materialId,parentId from materials where courseId= ? and isCurrent= ? and materialId=?";
            ArrayList<Integer> lastVersion=this.jdbcTemplate.queryForObject(getLastVersion, new RowMapper<ArrayList<Integer>>() {
                @Override
                public ArrayList<Integer> mapRow(ResultSet resultSet, int i) throws SQLException {
                    return new ArrayList<>(Arrays.asList(resultSet.getInt("materialId"),resultSet.getInt("parentId")));
                }
            },newMaterial.getCourseId(),true,previousMaterialId);
            int parentId= lastVersion.get(0) ;

            logger.info("Retrieved the latest version before updating");

            String updateLastVersion="update materials set isCurrent="+false+" where materialId="+lastVersion.get(0);
            this.jdbcTemplate.update(updateLastVersion);

            logger.info("Updated the last latest versions isCurrent to false");

            String updateMaterial="insert into materials (courseId,materialDescription,fileType,fileData,parentId) values (?,?,?,?,?)";
            KeyHolder keyHolder=new GeneratedKeyHolder();
            this.jdbcTemplate.update(connection -> {
                PreparedStatement stmt=connection.prepareStatement(updateMaterial,Statement.RETURN_GENERATED_KEYS);
                stmt.setInt(1,newMaterial.getCourseId());
                stmt.setString(2,newMaterial.getMaterialDescription());
                stmt.setString(3, newMaterial.getFileType());
                try {
                    stmt.setBinaryStream(4,newFile.getInputStream());
                } catch (IOException e) {
                    logger.error(e.getCause()+" in this method "+Thread.currentThread().getStackTrace()[1].getMethodName());
                }
                stmt.setInt(5,parentId);
                return stmt;
            },keyHolder);

            int materialId=(int)keyHolder.getKey().intValue();
            logger.info("Inserted the new latest version Training Material with materialId="+materialId);
            return getMaterialById((int) materialId);
        }
        catch (Exception e)
        {
            logger.error(e.getCause()+" in this method "+Thread.currentThread().getStackTrace()[1].getMethodName());
            throw e;
        }
    }


    public List<Material> getLatestMaterialByCourseId(int courseId) throws Exception
    {
        try{
            final String getLatest="select * from materials where courseId = ? and isCurrent= ? ";
            System.out.println(courseId);
            List<Material> material=this.jdbcTemplate.query(getLatest,MaterialDao.MapRowToMaterialLambda,courseId,true);
            logger.info("Retrieved the latest material of courseID="+courseId);
            return material;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            logger.error(e.getCause()+" in this method "+Thread.currentThread().getStackTrace()[1].getMethodName());
            throw e;
        }

    }

    public List<Material> getAllMaterialsByCourseId(int courseId, int materialId) throws Exception
    {
        try{
            List<Material> materials=new ArrayList<>();
            String getLatest="select * from materials where courseId = ? and materialId= ? ";
            Material material= this.jdbcTemplate.queryForObject(getLatest,MaterialDao.MapRowToMaterialLambda,courseId,materialId);
            logger.info("Retrieved every material version of courseId="+courseId+" and materialId"+materialId);
            materials.add(material);
            int parentId=material.getParentId();
            while(parentId!=0)
            {
                getLatest="select * from materials where courseId = ? and materialId= ? ";
                material= this.jdbcTemplate.queryForObject(getLatest,MaterialDao.MapRowToMaterialLambda,courseId,parentId);
                logger.info("Retrieved every material version of courseId="+courseId+" and materialId="+parentId);
                materials.add(material);
                parentId=material.getParentId();
            }
            return materials;
        }
        catch (Exception e)
        {
            logger.error(e.getCause()+" in this method "+Thread.currentThread().getStackTrace()[1].getMethodName());
            throw e;
        }
    }


    public Boolean deleteLatestVersion(int courseId) throws Exception
    {
        try{
            List<Material> latestMaterial=getLatestMaterialByCourseId(courseId);
            if(latestMaterial.get(0).getParentId()==0)
            {
                deleteAllMaterial(courseId);
            }
            String makePreviousLatest="update materials set isCurrent=? where materialId=?";
            this.jdbcTemplate.update(makePreviousLatest,true,latestMaterial.get(0).getParentId());
            return this.jdbcTemplate.update("delete from materials where materialId=?",latestMaterial.get(0).getMaterialId())>=1;
        }
        catch (Exception e)
        {
            logger.error(e.getCause()+" in this method "+Thread.currentThread().getStackTrace()[1].getMethodName());
            throw e;
        }
    }


    public boolean deleteAllMaterial(int courseId) throws Exception
    {
        try
        {
            if(materialAlreadyExist(courseId))
            {
                boolean isDeleted=this.jdbcTemplate.update("delete from materials where courseId= ? ",courseId)>=1;
                logger.info("Material deleted with courseId="+courseId);
                return isDeleted;
            }
            else
            {
                logger.info("Material with courseId="+ courseId +" doesn't exist");
                return true;
            }
        }
        catch (Exception e)
        {
            logger.error(e.getCause()+" in this method "+Thread.currentThread().getStackTrace()[1].getMethodName());
            throw e;
        }
    }


    public static RowMapper<Material> MapRowToMaterialLambda = ((resultSet, i)->{

//    private Material mapRowToMaterial(ResultSet resultSet, int rowNumber) throws SQLException {
        Material material=new Material();
        material.setMaterialId(resultSet.getInt("materialId"));
        material.setCourseId(resultSet.getInt("courseId"));
        material.setMaterialDescription(resultSet.getString("materialDescription"));
        material.setCreatedAt(resultSet.getTimestamp("createdAt"));
        material.setCurrent(resultSet.getBoolean("isCurrent"));
        material.setFileType(resultSet.getString("fileType"));
        material.setLastUpdated(resultSet.getTimestamp("lastUpdated"));
        material.setFileData(resultSet.getBytes("fileData"));
        material.setParentId(resultSet.getInt("parentId"));
        return material;
    });

}