package org.xdemo.example.springaop.service;

import cr.cdrb.web.edu.daointerface.IUserdao;
import cr.cdrb.web.edu.domains.security.Users;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.xdemo.example.springaop.annotation.Log;
import org.xdemo.example.springaop.bean.User;

@Service
public class UserServiceImpl implements IUserService {

    @Resource
    private IUserdao userDao;
	@Log(name="您访问了保存用户信息")
	public void save(User user) throws Throwable {
		System.out.println(user.getName());
                throw new Exception("我是服务层异常");
	}

    @Override
    public Users getUser() {     
       return  userDao.GetUsers("user");
      //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
	
}
