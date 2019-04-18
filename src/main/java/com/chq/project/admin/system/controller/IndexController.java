package com.chq.project.admin.system.controller;

import com.chq.project.admin.system.model.PermissionModel;
import com.chq.project.admin.system.model.UserModel;
import com.chq.project.admin.system.service.RoleService;
import com.chq.project.admin.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @Autowired
    private RoleService roleService;


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
     * 跳转到登录页面
     *
     * @return
     */
    @RequestMapping("/login")
    public String login() {
        return "common/login";
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

    /**
     * 跳转到用户管理页面
     *
     * @param model
     * @return
     */
    @RequestMapping("/system/user")
    public String user(Model model) {
        model.addAttribute("title", "用户管理");
        return "system/user/userList";
    }

    /**
     * 跳转到用户管理页面
     *
     * @param model
     * @return
     */
    @RequestMapping("/system/user/toDetail")
    public String toUserDetail(Model model, Integer id) {
        UserModel user = userService.getById(id);
        model.addAttribute("user", user);
        return "system/user/userDetail";
    }

    /**
     * 跳转到角色管理页面
     *
     * @param model
     * @return
     */
    @RequestMapping("/system/role")
    public String role(Model model) {
        model.addAttribute("title", "角色管理");
        return "system/role/roleList";
    }
}
