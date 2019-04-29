package com.chq.project.admin.system.controller;

import com.chq.project.admin.common.entity.Response;
import com.chq.project.admin.system.model.PermissionModel;
import com.chq.project.admin.system.model.UserModel;
import com.chq.project.admin.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * 首页
 *
 * @author CHQ
 */
@Controller
public class IndexController {

    @Autowired
    private UserService userService;

    /**
     * 跳转到首页
     *
     * @param model
     * @return
     */
    @RequestMapping("/")
    public ModelAndView index(ModelAndView model) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserModel user = userService.getByUsername(userDetails.getUsername());
        List<PermissionModel> menus = userService.getMenusByUsername(userDetails.getUsername());
        model.addObject("menus", menus);
        model.addObject("user", user);
        model.setViewName("index");
        return model;
    }

    /**
     * 跳转到首页
     *
     * @param model
     * @return
     */
    @RequestMapping("/home/console")
    public ModelAndView main(ModelAndView model) {
        model.setViewName("home/console");
        return model;
    }

    /**
     * 跳转到首页
     *
     * @param model
     * @return
     */
    @RequestMapping("/home/homepage1")
    public ModelAndView main1(ModelAndView model) {
        model.setViewName("home/homepage1");
        return model;
    }

    /**
     * 跳转到首页
     *
     * @param model
     * @return
     */
    @RequestMapping("/home/homepage2")
    public ModelAndView main2(ModelAndView model) {
        model.setViewName("home/homepage2");
        return model;
    }

    /**
     * 跳转到登录页面
     *
     * @return
     */
    @RequestMapping("/login")
    public String login() {
        return "common/login";
    }

    /**
     * 跳转到登录页面
     *
     * @return
     */
    @RequestMapping("/logout")
    @ResponseBody
    public Response<String> logout() {
        Response<String> response = new Response<>();
        response.setResult("退出成功");
        return response;
    }

    /**
     * 登录成功后重定向到首页
     *
     * @return
     */
    @RequestMapping("/loginSuccess")
    public String loginSuccess() {
        return "redirect:/";
    }

}
