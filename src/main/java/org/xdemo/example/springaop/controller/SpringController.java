package org.xdemo.example.springaop.controller;

import cr.cdrb.web.edu.domains.security.Users;
import cr.cdrb.web.edu.security.EduInvocationSecurityMetadataSourceService;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.InvalidCsrfTokenException;
import org.springframework.security.web.csrf.MissingCsrfTokenException;
import org.springframework.security.web.util.UrlUtils;

import org.springframework.stereotype.Controller;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.xdemo.example.springaop.annotation.Log;
import org.xdemo.example.springaop.bean.User;
import org.xdemo.example.springaop.service.IUserService;

@Controller
public class SpringController {

    @Resource
    IUserService userService;

    @Log(name = "您访问了aop1方法")
    @ResponseBody
    @RequestMapping(value = "aop1")
    public String aop1() {
        EduInvocationSecurityMetadataSourceService s = new EduInvocationSecurityMetadataSourceService();
        return "AOP";
    }

    @Log(name = "您访问了aop2方法")
    @ResponseBody
    @RequestMapping(value = "aop2")
//    @Secured({"ROLE_ADMIN"})
    public String aop2(String string) throws Throwable /// throws InterruptedException
    {
        Users user1 = userService.getUser();
        User user = new User();
        user.setName(string);
        userService.save(user);
        return string;
    }

    @RequestMapping("/json")
    @ResponseBody
    public Object json() {
        Map<String, String> result = new HashMap<String, String>();
        result.put("zhangsan", "hello");
        result.put("lisi", "world");
        result.put("wangwu", "nihao");
        //  PrintWriter writer = response.getWriter();
        return result;
    }

    @RequestMapping("/json1")
    @ResponseBody
    public Object json1() {
        Map<String, String> result = new HashMap<String, String>();
        result.put("zhangsan", "hello");
        result.put("lisi", "world");
        result.put("wangwu", "nihao");
        //  PrintWriter writer = esponse.getWriter();
        return "dfdfd";
    }
//       @Log(name = "您访问了aop1方法")
//    @ResponseBody

    @RequestMapping(value = "test")
    public String aop2() {
        ///  EduInvocationSecurityMetadataSourceService s = new EduInvocationSecurityMetadataSourceService();

        return "index";
    }

    //Spring Security see this :
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout) {

        ModelAndView model = new ModelAndView();
        if (error != null) {
            model.addObject("error", "用户名或密码不正确!");
        }

        if (logout != null) {
            model.addObject("msg", "您已成功注销系统.");
        }
// 先从tokenRepository中加载token  

   // 如果为空，则tokenRepository生成新的token，并保存到tokenRepository中  

    
   // 将token写入request的attribute中，方便页面上使用  
 
        return model;

    }

}
