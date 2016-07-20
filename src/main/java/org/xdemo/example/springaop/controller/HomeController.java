/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.xdemo.example.springaop.controller;

import cr.cdrb.web.edu.common.encryption;
import cr.cdrb.web.edu.security.EduUserDetailsService;
import java.util.Map;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Jayang
 */
@Controller
@RequestMapping("/Home")
public class HomeController {

    @Autowired
    EduUserDetailsService userDetailsService;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView Login(ServletRequest request) {
        Map<String, String[]> params = request.getParameterMap();
        String param = request.getParameter("test");
        SecurityContextHolder.getContext();
        ModelAndView mv = new ModelAndView();
        return mv;
    }

    @RequestMapping(value = "/EduLogin", method = RequestMethod.GET)
    public ModelAndView GetUser(HttpServletRequest request, @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout) throws Throwable {
        //Home/EduLogin?VXNlclBJRD1hZG1pbg==
        ModelAndView model = new ModelAndView();
        Map<String, String[]> params = request.getParameterMap();
        String param = request.getQueryString();
        System.out.print(param);
        if (param == null) {
            /// model.addObject("error", "用户参数不能为空!");
           throw new AccessDeniedException("无权限!");
        }
        if (param.length() == 0) {
            // model.addObject("error", "用户参数不能为空!");
              throw new UsernameNotFoundException("用户参数不能为空!");
        }
        String username = encryption.getFromBase64(param);
        //从spring容器中获取UserDetailsService(这个从数据库根据用户名查询用户信息,及加载权限的service)  
        //根据用户名username加载userDetails  
        System.out.printf(username.substring(username.indexOf("=") + 1));
        UserDetails userDetails = userDetailsService.loadUserByUsername(username.substring(username.indexOf("=") + 1));
        //根据userDetails构建新的Authentication,这里使用了  
        //PreAuthenticatedAuthenticationToken当然可以用其他token,如UsernamePasswordAuthenticationToken                 
        UsernamePasswordAuthenticationToken authentication
                = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
        //设置authentication中details  
        authentication.setDetails(new WebAuthenticationDetails(request));
        //存放authentication到SecurityContextHolder  
        SecurityContextHolder.getContext().setAuthentication(authentication);
        HttpSession session = request.getSession(true);
        //在session中存放security context,方便同一个session中控制用户的其他操作  
        session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());       
        return model;
    }
}
