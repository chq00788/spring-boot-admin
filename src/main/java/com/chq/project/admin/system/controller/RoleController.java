package com.chq.project.admin.system.controller;

import com.chq.project.admin.common.entity.Response;
import com.chq.project.admin.common.utils.SearchUtil;
import com.chq.project.admin.system.model.PermissionModel;
import com.chq.project.admin.system.model.RoleModel;
import com.chq.project.admin.system.service.PermissionService;
import com.chq.project.admin.system.service.RoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


/**
 * 描述：角色管理控制层
 *
 * @author CHQ
 * @date 2019-01-19
 */
@Api(tags = {"角色管理操作接口"}, description = "角色管理操作接口")
@Controller
@RequestMapping("/system/role")
public class RoleController {
    private static final Logger log = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;


    @ApiOperation(value = "查询分页信息", notes = "查询分页信息", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "limit", value = "每页条数", required = true, paramType = "query", dataType = "int")
    })
    @RequestMapping(value = "/getListByPage")
    @ResponseBody
    public Response<PageInfo<RoleModel>> getListByPage(@RequestParam(value = "page", defaultValue = "1") int page,
                                                       @RequestParam(value = "limit", defaultValue = "10") int limit,
                                                       RoleModel model) {
        Response<PageInfo<RoleModel>> response = new Response<>();
        try {
            PageHelper.startPage(page, limit);
            List<RoleModel> list = roleService.selectList(SearchUtil.getSearch(model));
            PageInfo<RoleModel> pageInfo = new PageInfo<>(list);
            response.setResult(pageInfo);
        } catch (Exception e) {
            log.error("查询角色管理信息异常！原因：{}", e.getStackTrace());
            e.printStackTrace();
            response.setError("查询失败");
        }
        return response;
    }

    @ApiOperation(value = "查询信息列表", notes = "查询信息列表", httpMethod = "GET")
    @RequestMapping(value = "/getList")
    @ResponseBody
    public Response<List<RoleModel>> getList(RoleModel model) {
        Response<List<RoleModel>> response = new Response<>();
        try {
            List<RoleModel> list = roleService.selectList(SearchUtil.getSearch(model));
            response.setResult(list);
        } catch (Exception e) {
            log.error("查询角色管理信息异常！原因：{}", e.getStackTrace());
            e.printStackTrace();
            response.setError("查询失败");
        }
        return response;
    }

    @ApiOperation(value = "保存信息", notes = "保存信息", httpMethod = "POST")
    @RequestMapping(value = "/save")
    @ResponseBody
    public Response<String> save(RoleModel model) {
        Response<String> response = new Response<>();
        try {
            roleService.insert(model);
            response.setResult("保存成功");
        } catch (Exception e) {
            log.error("保存角色管理信息异常！原因：{}", e.getStackTrace());
            e.printStackTrace();
            response.setError("保存失败");
        }
        return response;
    }

    @ApiOperation(value = "更新信息", notes = "更新信息", httpMethod = "POST")
    @RequestMapping(value = "/update")
    @ResponseBody
    public Response<String> update(RoleModel model) {
        Response<String> response = new Response<>();
        try {
            roleService.update(model);
            response.setResult("更新成功");
        } catch (Exception e) {
            log.error("更新角色管理信息异常！原因：{}", e.getStackTrace());
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
            roleService.delete(id);
            response.setResult("删除成功");
        } catch (Exception e) {
            log.error("删除角色管理信息异常！原因：{}", e.getStackTrace());
            e.printStackTrace();
            response.setError("删除失败");
        }
        return response;
    }

    @ApiOperation(value = "根据ID查询信息", notes = "根据ID查询信息", httpMethod = "GET")
    @RequestMapping(value = "/getById")
    @ResponseBody
    public Response<RoleModel> getById(@RequestParam(value = "id") Integer id) {
        Response<RoleModel> response = new Response<>();
        try {
            RoleModel model = roleService.getById(id);
            response.setResult(model);
        } catch (Exception e) {
            log.error("查询角色管理信息异常！原因：{}", e.getStackTrace());
            e.printStackTrace();
            response.setError("查询失败");
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
                roleService.delete(ids[i]);
            }
            response.setResult("批量删除信息成功");
        } catch (Exception e) {
            log.error("批量删除角色信息异常！原因：{}", e.getStackTrace());
            e.printStackTrace();
            response.setError("批量删除失败");
        }
        return response;
    }

    @ApiOperation(value = "查询权限信息列表", notes = "查询权限信息列表", httpMethod = "GET")
    @RequestMapping(value = "/getPermList")
    @ResponseBody
    public Response<List<PermissionModel>> getPermList(@RequestParam(value = "id") Integer id) {
        Response<List<PermissionModel>> response = new Response<>();
        try {
            List<PermissionModel> list = permissionService.getPermListByRoleId(id);
            response.setResult(list);
        } catch (Exception e) {
            log.error("查询权限信息异常！原因：{}", e.getStackTrace());
            e.printStackTrace();
            response.setError("查询失败");
        }
        return response;
    }

    @ApiOperation(value = "保存角色权限信息", notes = "保存角色权限信息", httpMethod = "POST")
    @RequestMapping(value = "/setPerm")
    @ResponseBody
    public Response<String> setPerm(Integer id,@RequestParam(value = "perms[]") Integer[] perms) {
        Response<String> response = new Response<>();
        try {
            roleService.setPerm(id,perms);
            response.setResult("保存成功");
        } catch (Exception e) {
            log.error("保存角色权限信息异常！原因：{}", e.getStackTrace());
            e.printStackTrace();
            response.setError("保存失败");
        }
        return response;
    }

    /**
     * 跳转到角色管理页面
     *
     * @param model
     * @return
     */
    @RequestMapping("/toList")
    public String toList(Model model) {
        model.addAttribute("title", "角色管理");
        return "system/role/list";
    }

    /**
     * 跳转到角色添加页面
     *
     * @param model
     * @return
     */
    @RequestMapping("/toAdd")
    public String toAdd(Model model) {
        model.addAttribute("title", "角色管理");
        return "system/role/add";
    }

    /**
     * 跳转到角色修改页面
     *
     * @param model
     * @return
     */
    @RequestMapping("/toEdit")
    public String toEdit(Model model, Integer id) {
        RoleModel role = roleService.getById(id);
        model.addAttribute("role", role);
        return "system/role/edit";
    }

    /**
     * 跳转到角色权限页面
     *
     * @param model
     * @return
     */
    @RequestMapping("/toPerm")
    public String toPerm(Model model, Integer id) {
        RoleModel role = roleService.getById(id);
        model.addAttribute("role", role);
        return "system/role/perm";
    }
}