package com.chq.project.admin.system.controller;

import com.chq.project.admin.common.entity.Response;
import com.chq.project.admin.common.utils.SearchUtil;
import com.chq.project.admin.system.model.PermissionModel;
import com.chq.project.admin.system.model.RoleModel;
import com.chq.project.admin.system.model.UserModel;
import com.chq.project.admin.system.service.RoleService;
import com.chq.project.admin.system.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


/**
 * 描述：用户管理控制层
 *
 * @author CHQ
 * @date 2019-01-19
 */
@Api(tags = {"用户管理操作接口"}, description = "用户管理操作接口")
@Controller
@RequestMapping("/system/user")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;


    @ApiOperation(value = "查询分页信息", notes = "查询分页信息", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "limit", value = "每页条数", required = true, paramType = "query", dataType = "int")
    })
    @RequestMapping(value = "/getListByPage")
    @ResponseBody
    public Response<PageInfo<UserModel>> getListByPage(@RequestParam(value = "page", defaultValue = "1") int page,
                                                       @RequestParam(value = "limit", defaultValue = "10") int limit,
                                                       UserModel model) {
        Response<PageInfo<UserModel>> response = new Response<>();
        try {
            PageHelper.startPage(page, limit);
            List<UserModel> list = userService.selectList(SearchUtil.getSearch(model));
            PageInfo<UserModel> pageInfo = new PageInfo<>(list);
            response.setResult(pageInfo);
        } catch (Exception e) {
            log.error("查询用户管理信息异常！原因：{}", e.getStackTrace());
            e.printStackTrace();
            response.setError("查询失败");
        }
        return response;
    }

    @ApiOperation(value = "查询信息列表", notes = "查询信息列表", httpMethod = "GET")
    @RequestMapping(value = "/getList")
    @ResponseBody
    public Response<List<UserModel>> getList(UserModel model) {
        Response<List<UserModel>> response = new Response<>();
        try {
            List<UserModel> list = userService.selectList(SearchUtil.getSearch(model));
            response.setResult(list);
        } catch (Exception e) {
            log.error("查询用户管理信息异常！原因：{}", e.getStackTrace());
            e.printStackTrace();
            response.setError("查询失败");
        }
        return response;
    }

    @ApiOperation(value = "保存信息", notes = "保存信息", httpMethod = "POST")
    @RequestMapping(value = "/save")
    @ResponseBody
    public Response<String> save(UserModel model) {
        Response<String> response = new Response<>();
        try {
            //检查用户名重复
            UserModel user = userService.getInfoByUsername(model.getUsername());
            if (null != user) {
                response.setError("账号已存在");
                return response;
            }
            userService.insert(model);
            response.setResult("保存成功");
        } catch (Exception e) {
            log.error("保存用户管理信息异常！原因：{}", e.getStackTrace());
            e.printStackTrace();
            response.setError("保存失败");
        }
        return response;
    }

    @ApiOperation(value = "更新信息", notes = "更新信息", httpMethod = "POST")
    @RequestMapping(value = "/update")
    @ResponseBody
    public Response<String> update(UserModel model) {
        Response<String> response = new Response<>();
        try {
            UserModel oldModel = userService.getById(model.getId());
            oldModel.setRealName(model.getRealName());
            oldModel.setUserType(model.getUserType());
            oldModel.setIsUsable(model.getIsUsable());
            userService.update(oldModel);
            response.setResult("更新成功");
        } catch (Exception e) {
            log.error("更新用户管理信息异常！原因：{}", e.getStackTrace());
            e.printStackTrace();
            response.setError("更新失败");
        }
        return response;
    }

    @ApiOperation(value = "删除信息", notes = "删除信息", httpMethod = "GET")
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Response<String> delete(@RequestParam(value = "id") Integer id) {
        Response<String> response = new Response<>();
        try {
            userService.delete(id);
            response.setResult("删除成功");
        } catch (Exception e) {
            log.error("删除用户管理信息异常！原因：{}", e.getStackTrace());
            e.printStackTrace();
            response.setError("删除失败");
        }
        return response;
    }

    @ApiOperation(value = "根据ID查询信息", notes = "根据ID查询信息", httpMethod = "GET")
    @RequestMapping(value = "/getById")
    @ResponseBody
    public Response<UserModel> getById(@RequestParam(value = "id") Integer id) {
        Response<UserModel> response = new Response<>();
        try {
            UserModel model = userService.getById(id);
            response.setResult(model);
        } catch (Exception e) {
            log.error("查询用户管理信息异常！原因：{}", e.getStackTrace());
            e.printStackTrace();
            response.setError("查询失败");
        }
        return response;
    }

    @ApiOperation(value = "更新状态信息", notes = "更新状态信息", httpMethod = "GET")
    @RequestMapping(value = "/changeStatus")
    @ResponseBody
    public Response<String> changeStatus(Integer id, String isUsable) {
        Response<String> response = new Response<>();
        try {
            UserModel oldModel = userService.getById(id);
            oldModel.setIsUsable(isUsable);
            userService.update(oldModel);
            response.setResult("更新状态成功");
        } catch (Exception e) {
            log.error("更新用户状态信息异常！原因：{}", e.getStackTrace());
            e.printStackTrace();
            response.setError("更新状态失败");
        }
        return response;
    }

    @ApiOperation(value = "根据用户名查询信息", notes = "根据ID查询信息", httpMethod = "GET")
    @RequestMapping(value = "/getByUsername")
    @ResponseBody
    public Response<UserModel> getByUsername(String username) {
        Response<UserModel> response = new Response<>();
        try {
            UserModel model = userService.getByUsername(username);
            response.setResult(model);
        } catch (Exception e) {
            log.error("查询用户管理信息异常！原因：{}", e.getStackTrace());
            e.printStackTrace();
            response.setError("查询失败");
        }
        return response;
    }


    @ApiOperation(value = "查询用户菜单权限信息", notes = "查询用户菜单权限信息", httpMethod = "GET")
    @RequestMapping(value = "/getMenuListByUser")
    @ResponseBody
    public Response<List<PermissionModel>> getMenuListByUser(Authentication authentication) {
        Response<List<PermissionModel>> response = new Response<>();
        try {

            List<PermissionModel> list = userService.getMenusByUsername(authentication.getName());
            response.setResult(list);
        } catch (Exception e) {
            log.error("查询用户菜单权限信息异常！原因：{}", e.getStackTrace());
            e.printStackTrace();
            response.setError("查询失败");
        }
        return response;
    }

    @ApiOperation(value = "更新用户角色信息", notes = "更新用户角色信息", httpMethod = "GET")
    @RequestMapping(value = "/setRole")
    @ResponseBody
    public Response<String> setRole(Integer id, @RequestParam(value = "roles[]") Integer[] roles) {
        Response<String> response = new Response<>();
        try {
            userService.saveUserRole(id, roles);
            response.setResult("更新用户角色信息成功");
        } catch (Exception e) {
            log.error("更新用户角色信息异常！原因：{}", e.getStackTrace());
            e.printStackTrace();
            response.setError("更新用户角色信息失败");
        }
        return response;
    }

    @ApiOperation(value = "批量删除信息", notes = "批量删除信息", httpMethod = "GET")
    @RequestMapping(value = "/batchDelete")
    @ResponseBody
    public Response<String> batchDelete(@RequestParam(value = "ids[]") Integer[] ids) {
        Response<String> response = new Response<>();
        try {
            for (int i = 0; i < ids.length; i++) {
                userService.delete(ids[i]);
            }
            response.setResult("批量删除信息成功");
        } catch (Exception e) {
            log.error("批量删除用户管理信息异常！原因：{}", e.getStackTrace());
            e.printStackTrace();
            response.setError("批量删除失败");
        }
        return response;
    }

    /**
     * 跳转到用户管理页面
     *
     * @param model
     * @return
     */
    @RequestMapping("/toList")
    public String toList(Model model) {
        model.addAttribute("title", "用户管理");
        return "system/user/list";
    }

    /**
     * 跳转到用户详情页面
     *
     * @param model
     * @return
     */
    @RequestMapping("/toDetail")
    public String toDetail(Model model, Integer id) {
        UserModel user = userService.getById(id);
        model.addAttribute("user", user);
        return "system/user/detail";
    }

    /**
     * 跳转到用户编辑页面
     *
     * @param model
     * @return
     */
    @RequestMapping("/toEdit")
    public String toEdit(Model model, Integer id) {
        UserModel user = userService.getById(id);
        model.addAttribute("user", user);
        return "system/user/edit";
    }

    /**
     * 跳转到用户添加页面
     *
     * @return
     */
    @RequestMapping("/toAdd")
    public String toAdd() {
        return "system/user/add";
    }

    /**
     * 跳转到用户角色设置页面
     *
     * @return
     */
    @RequestMapping("/toRole")
    public String toUserRole(Model model, Integer id) {
        UserModel user = userService.getById(id);
        model.addAttribute("user", user);
        List<RoleModel> roleModels = roleService.selectRoleListByUserId(id);
        model.addAttribute("roles", roleModels);
        return "system/user/role";
    }
}