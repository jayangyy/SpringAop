package org.xdemo.example.springaop.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.xdemo.example.springaop.annotation.Log;
import org.xdemo.example.springaop.bean.User;
import org.xdemo.example.springaop.service.IUserService;


@Controller
@RequestMapping("/aop")
public class SpringController {
	
	@Resource IUserService userService;
	
	@Log(name="您访问了aop1方法")
	@ResponseBody
	@RequestMapping(value="aop1")
	public String aop1(){
		return "AOP";
	}
	
	@Log(name="您访问了aop2方法")
	@ResponseBody
	@RequestMapping(value="aop2")
	public String aop2(String string) throws Throwable
               /// throws InterruptedException
        {
		User user=new User();
		user.setName(string);
		userService.save(user);
		return string;
	}
	
}
