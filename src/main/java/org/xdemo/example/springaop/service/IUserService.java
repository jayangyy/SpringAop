package org.xdemo.example.springaop.service;

import cr.cdrb.web.edu.security.domains.Users;
import org.xdemo.example.springaop.bean.User;

public interface IUserService {
	
	public void save(User user) throws Throwable;
  public Users getUser();
}
