package com.AU.backend.DaoTest;

import com.AU.backend.BackendApplication;
import com.AU.backend.Dao.LoginDao;
import com.AU.backend.Model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = BackendApplication.class)
public class UserDaoTest {

    @Mock
    private JdbcTemplate jdbcTemplate;


    @InjectMocks
    private LoginDao loginDao;


    User user1=new User();
    User user2=new User();
    List<User> allUser=new ArrayList<>();

    @BeforeEach
    void  init(){
        user1.setUserId(1);
        user1.setEmailId("email1");
        user1.setFirstName("ABC");
        user1.setLastName("XYZ");

        user2.setUserId(2);
        user2.setEmailId("email2");
        user2.setFirstName("DEF");
        user2.setLastName("ZXY");
        allUser.add(user1);
        allUser.add(user2);
    }

    @Test
    public void doesUserExist() throws Exception {

        when(this.jdbcTemplate.queryForObject("select count(*) from users where emailId='email1'", Integer.class)).thenReturn(1);
        boolean doesExist= loginDao.doesUserExist("email1");
        assertEquals(true, doesExist);

    }

    @Test
    public void addUser() throws Exception {
        when(this.jdbcTemplate.queryForObject("select count(*) from users where emailId='email1'", Integer.class)).thenReturn(1);

        when(this.jdbcTemplate.queryForObject("select firstName,lastName,emailId,userId from users where emailId='email1'",LoginDao.MapRowToUserLambda)).thenReturn(user1);

        User user=loginDao.loginByGoogle(user1);

        assertEquals(user1,user);

    }

}