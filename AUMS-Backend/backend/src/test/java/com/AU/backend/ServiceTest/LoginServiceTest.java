package com.AU.backend.ServiceTest;

import com.AU.backend.Dao.LoginDao;
import com.AU.backend.Model.User;
import com.AU.backend.Service.LoginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class LoginServiceTest {

    @Mock
    LoginDao loginDao;

    @InjectMocks
    LoginService loginService;

    User user1;
    User user2;

    @BeforeEach
    void init(){
        user1=new User();
        user1.setUserId(1);
        user1.setFirstName("jatin");
        user1.setLastName("kumar");
        user1.setEmailId("email1");


        user2=new User();
        user2.setUserId(2);
        user2.setFirstName("komal");
        user2.setLastName("gupta");
        user2.setEmailId("email2");
    }

    @Test
    public void loginByGoogleTest() throws Exception{
        when(loginDao.loginByGoogle(user1)).thenReturn(user1);

        User returnedUser=loginService.loginByGoogle(user1);
        assertEquals(user1,returnedUser);
    }

}
