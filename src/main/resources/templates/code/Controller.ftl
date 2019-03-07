package ${package_name}.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ${package_name}.model.${table_name}Model;
import ${package_name}.service.${table_name}Service;

import java.util.List;


/**
* 描述：${table_annotation}控制层
* @author ${author}
* @date ${date}
*/
@Api(tags = {"${table_annotation}操作接口"}, description = "${table_annotation}操作接口")
@RestController
@RequestMapping("/${table_name?uncap_first}")
public class ${table_name}Controller {
    private static final Logger log = LoggerFactory.getLogger(${table_name}Controller.class);

    @Autowired
    private ${table_name}Service ${table_name?uncap_first}Service;


    @ApiOperation(value = "查询分页信息", notes = "查询分页信息",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "limit", value = "每页条数", required = true, paramType = "query", dataType = "int")
    })
    @RequestMapping(value = "/getListByPage")
    public Response<PageInfo<${table_name}Model>> getListByPage(@RequestParam(value = "page", defaultValue = "1") int page,
                                                             @RequestParam(value = "limit", defaultValue = "10") int limit,
                                                             ${table_name}Model model) {
        Response<PageInfo<${table_name}Model>> response = new Response<>();
        try {
            PageHelper.startPage(page, limit);
            List<${table_name}Model> list = ${table_name?uncap_first}Service.selectList(SearchUtil.getSearch(model));
            PageInfo<${table_name}Model> pageInfo = new PageInfo<>(list);
            response.setResult(pageInfo);
        } catch (Exception e) {
            log.error("查询${table_annotation}信息异常！原因：{}", e.getStackTrace());
            e.printStackTrace();
            response.setError("查询失败");
        }
        return response;
    }

    @ApiOperation(value = "查询信息列表", notes = "查询信息列表",httpMethod = "GET")
    @RequestMapping(value = "/getList")
    public Response<List<${table_name}Model>> getList(${table_name}Model model) {
        Response<List<${table_name}Model>> response = new Response<>();
        try {
            List<${table_name}Model> list = ${table_name?uncap_first}Service.selectList(SearchUtil.getSearch(model));
            response.setResult(list);
        } catch (Exception e) {
            log.error("查询${table_annotation}信息异常！原因：{}", e.getStackTrace());
            e.printStackTrace();
            response.setError("查询失败");
        }
        return response;
    }

    @ApiOperation(value = "保存信息", notes = "保存信息",httpMethod = "POST")
    @RequestMapping(value = "/save")
    public Response<String> save(${table_name}Model model) {
        Response<String> response = new Response<>();
        try {
            ${table_name?uncap_first}Service.insert(model);
            response.setResult("保存成功");
        } catch (Exception e) {
            log.error("保存${table_annotation}信息异常！原因：{}", e.getStackTrace());
            e.printStackTrace();
            response.setError("保存失败");
        }
        return response;
    }

    @ApiOperation(value = "更新信息", notes = "更新信息",httpMethod = "POST")
    @RequestMapping(value = "/update")
    public Response<String> update(${table_name}Model model) {
        Response<String> response = new Response<>();
        try {
            ${table_name?uncap_first}Service.update(model);
            response.setResult("更新成功");
        } catch (Exception e) {
            log.error("更新${table_annotation}信息异常！原因：{}", e.getStackTrace());
            e.printStackTrace();
            response.setError("更新失败");
        }
        return response;
    }

    @ApiOperation(value = "删除信息", notes = "删除信息",httpMethod = "GET")
    @RequestMapping(value = "/delete")
    public Response<String> delete(@RequestParam(value = "id") Integer id) {
        Response<String> response = new Response<>();
        try {
            ${table_name?uncap_first}Service.delete(id);
            response.setResult("删除成功");
        } catch (Exception e) {
            log.error("删除${table_annotation}信息异常！原因：{}", e.getStackTrace());
            e.printStackTrace();
            response.setError("删除失败");
        }
        return response;
    }

    @ApiOperation(value = "根据ID查询信息", notes = "根据ID查询信息",httpMethod = "GET")
    @RequestMapping(value = "/getById")
    public Response<${table_name}Model> getById(@RequestParam(value = "id") Integer id) {
        Response<${table_name}Model> response = new Response<>();
        try {
            ${table_name}Model model = ${table_name?uncap_first}Service.getById(id);
            response.setResult(model);
        } catch (Exception e) {
            log.error("查询${table_annotation}信息异常！原因：{}", e.getStackTrace());
            e.printStackTrace();
            response.setError("查询失败");
        }
        return response;
    }
}