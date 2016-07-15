/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.xdemo.example.springaop.controller;

import javax.servlet.ServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Jayang
 */
@Controller
@RequestMapping("/Home")
public class HomeController {

    public ModelAndView Login(ServletRequest request) {
        request.getParameterMap();
        ModelAndView mv = new ModelAndView();
        return mv;
    }
}
