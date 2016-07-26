/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.xdemo.example.springaop.controller;

import cr.cdrb.web.edu.common.encryption;
import cr.cdrb.web.edu.security.EduUserDetailsService;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
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
        ModelAndView mv = new ModelAndView();
        return mv;
    }

    @RequestMapping(value = "/index1", method = RequestMethod.GET)
    public ModelAndView Login1(ServletRequest request) {
        Map<String, String[]> params = request.getParameterMap();
        ModelAndView mv = new ModelAndView();
        return mv;
    }

    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public void uploadFile(HttpServletResponse response, HttpServletRequest request, @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        byte[] bytes = file.getBytes();
        System.out.println(file.getOriginalFilename());
        String uploadDir = request.getRealPath("/") + "upload";
        File dirPath = new File(uploadDir);
        if (!dirPath.exists()) {
            dirPath.mkdirs();
        }
        String sep = System.getProperty("file.separator");
        File uploadedFile = new File(uploadDir + sep
                + file.getOriginalFilename());
        FileCopyUtils.copy(bytes, uploadedFile);
        response.getWriter().write("true");
    }

    @RequestMapping(value = "/EduLogin", method = RequestMethod.GET)
    public ModelAndView GetUser(HttpServletRequest request, @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout) throws Throwable {
        //Home/EduLogin?VXNlclBJRD1hZG1pbg==
        ModelAndView model = new ModelAndView();
        UserDetails userDetails = userDetailsService.loadUserByUsername("user");
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
