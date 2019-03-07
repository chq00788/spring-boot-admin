package com.chq.project.admin.system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 异常跳转控制类
 *
 * @author CHQ
 */
@Controller
public class ExceptionController {

    /**
     * 无权限跳转页面
     *
     * @return
     */
    @RequestMapping("/403")
    public String exception403() {
        return "error/403";
    }

    /**
     * 无效连接跳转页面
     *
     * @return
     */
    @RequestMapping("/404")
    public String exception404() {
        return "error/404";
    }

    /**
     * 内部跳转页面
     *
     * @return
     */
    @RequestMapping("/500")
    public String exception500() {
        return "error/500";
    }
}
