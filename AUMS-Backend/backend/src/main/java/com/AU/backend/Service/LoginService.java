package com.AU.backend.Service;

import com.AU.backend.Dao.LoginDao;
import com.AU.backend.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    LoginDao loginDao;
    public User loginByGoogle(User user) throws Exception {
        return loginDao.loginByGoogle(user);
    }
}
