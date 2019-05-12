package com.chq.project.admin.system.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.chq.project.admin.common.entity.Response;
import com.chq.project.admin.common.utils.SearchUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.chq.project.admin.system.model.PermissionModel;
import com.chq.project.admin.system.service.PermissionService;

import java.util.List;


/**
* 描述：权限管理控制层
* @author CHQ
* @date 2019-01-19
*/
@Api(tags = {"权限管理操作接口"}, description = "权限管理操作接口")
@Controller
@RequestMapping("/system/perm")
public class PermissionController {
    private static final Logger log = LoggerFactory.getLogger(PermissionController.class);

    @Autowired
    private PermissionService permissionService;


    @ApiOperation(value = "查询分页信息", notes = "查询分页信息",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "limit", value = "每页条数", required = true, paramType = "query", dataType = "int")
    })
    @RequestMapping(value = "/getListByPage")
    @ResponseBody
    public Response<PageInfo<PermissionModel>> getListByPage(@RequestParam(value = "page", defaultValue = "1") int page,
                                                             @RequestParam(value = "limit", defaultValue = "10") int limit,
                                                             PermissionModel model) {
        Response<PageInfo<PermissionModel>> response = new Response<>();
        try {
            PageHelper.startPage(page, limit);
            List<PermissionModel> list = permissionService.selectList(SearchUtil.getSearch(model));
            PageInfo<PermissionModel> pageInfo = new PageInfo<>(list);
            response.setResult(pageInfo);
        } catch (Exception e) {
            log.error("查询权限管理信息异常！原因：{}", e.getStackTrace());
            e.printStackTrace();
            response.setError("查询失败");
        }
        return response;
    }

    @ApiOperation(value = "查询信息列表", notes = "查询信息列表",httpMethod = "GET")
    @RequestMapping(value = "/getList")
    @ResponseBody
    public Response<List<PermissionModel>> getList(PermissionModel model) {
        Response<List<PermissionModel>> response = new Response<>();
        try {
            List<PermissionModel> list = permissionService.selectList(SearchUtil.getSearch(model));
            response.setResult(list);
        } catch (Exception e) {
            log.error("查询权限管理信息异常！原因：{}", e.getStackTrace());
            e.printStackTrace();
            response.setError("查询失败");
        }
        return response;
    }

    @ApiOperation(value = "保存信息", notes = "保存信息",httpMethod = "POST")
    @RequestMapping(value = "/save")
    @ResponseBody
    public Response<String> save(PermissionModel model) {
        Response<String> response = new Response<>();
        try {
            permissionService.insert(model);
            response.setResult("保存成功");
        } catch (Exception e) {
            log.error("保存权限管理信息异常！原因：{}", e.getStackTrace());
            e.printStackTrace();
            response.setError("保存失败");
        }
        return response;
    }

    @ApiOperation(value = "更新信息", notes = "更新信息",httpMethod = "POST")
    @RequestMapping(value = "/update")
    @ResponseBody
    public Response<String> update(PermissionModel model) {
        Response<String> response = new Response<>();
        try {
            permissionService.update(model);
            response.setResult("更新成功");
        } catch (Exception e) {
            log.error("更新权限管理信息异常！原因：{}", e.getStackTrace());
            e.printStackTrace();
            response.setError("更新失败");
        }
        return response;
    }

    @ApiOperation(value = "删除信息", notes = "删除信息",httpMethod = "GET")
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Response<String> delete(@RequestParam(value = "id") Integer id) {
        Response<String> response = new Response<>();
        try {
            permissionService.delete(id);
            response.setResult("删除成功");
        } catch (Exception e) {
            log.error("删除权限管理信息异常！原因：{}", e.getStackTrace());
            e.printStackTrace();
            response.setError("删除失败");
        }
        return response;
    }

    @ApiOperation(value = "根据ID查询信息", notes = "根据ID查询信息",httpMethod = "GET")
    @RequestMapping(value = "/getById")
    @ResponseBody
    public Response<PermissionModel> getById(@RequestParam(value = "id") Integer id) {
        Response<PermissionModel> response = new Response<>();
        try {
            PermissionModel model = permissionService.getById(id);
            response.setResult(model);
        } catch (Exception e) {
            log.error("查询权限管理信息异常！原因：{}", e.getStackTrace());
            e.printStackTrace();
            response.setError("查询失败");
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
        model.addAttribute("title", "菜单管理");
        return "system/perm/list";
    }
}