package com.AU.backend.Dao;
import com.AU.backend.Model.Course;
import com.AU.backend.Model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class LoginDao {

    Logger logger= LoggerFactory.getLogger(LoginDao.class);
    @Autowired
    JdbcTemplate jdbcTemplate;

    public boolean doesUserExist(String emailId) throws Exception
    {
        try{
            String finalQuery= "select count(*) from users where emailId='"+emailId+"'";
            boolean doesExist=jdbcTemplate.queryForObject(finalQuery,Integer.class) > 0;
            logger.info("User Exist Query Worked");
            return doesExist;
        }
        catch(Exception e)
        {
            logger.error(e.getCause()+"in this method "+Thread.currentThread().getStackTrace()[1].getMethodName());
            throw e;
        }
    }


    public User loginByGoogle(User user) throws  Exception
    {
        try
        {
            boolean alreadyExist=doesUserExist(user.getEmailId());
            if(alreadyExist)
            {
                return jdbcTemplate.queryForObject("select firstName,lastName,emailId,userId from users where emailId='"+user.getEmailId()+"'",LoginDao.MapRowToUserLambda);
            }
            else
            {
                final String add_new_user="insert into users (firstName,lastName,emailId,joiningDate) values( ? , ? , ? , ? )";
                KeyHolder keyHolder = new GeneratedKeyHolder();
                java.sql.Date date = new Date(System.currentTimeMillis());
                this.jdbcTemplate.update(connection -> {
                    PreparedStatement stmt = connection.prepareStatement(add_new_user);
                    stmt.setString(1, user.getFirstName());
                    stmt.setString(2, user.getLastName());
                    stmt.setString(3,user.getEmailId());
                    stmt.setDate(4,date );
                    return stmt;
                });
            }
            logger.info("New User Created with emailId="+user.getEmailId());
            User newUser= jdbcTemplate.queryForObject("select firstName,lastName,emailId,userId from users where emailId='"+user.getEmailId()+"'",LoginDao.MapRowToUserLambda);
            logger.info("Successfully retrieved the newly created user");
            return newUser;
        }
        catch (Exception e)
        {
            logger.error(e.getCause()+"in this method "+Thread.currentThread().getStackTrace()[1].getMethodName());
            throw e;
        }
    }

    public static RowMapper<User> MapRowToUserLambda = ((resultSet, i)->{
        User user= new User();
        user.setUserId(resultSet.getInt("userId"));
        user.setEmailId(resultSet.getString("emailId"));
        user.setFirstName(resultSet.getString("firstName"));
        user.setLastName(resultSet.getString("lastName"));
        return user;
    });
}
