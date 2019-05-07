/**

 @Name：layuiAdmin 用户管理 管理员管理 角色管理
 @Author：star1029
 @Site：http://www.layui.com/admin/
 @License：LPPL

 */
layui.define(['table', 'form'], function (exports) {
    var $ = layui.$
        , table = layui.table
        , form = layui.form;

    //用户管理
    table.render({
        elem: '#LAY-user-manage'
        , url: 'getListByPage' //模拟接口
        , cols: [[
            {type: 'checkbox', fixed: 'left'}
            , {field: 'id', width: 80, title: 'ID', sort: true}
            , {field: 'realName', title: '姓名'}
            , {field: 'username', title: '账号'}
            , {field: 'userType', title: '类型', templet: '#userTypeTpl'}
            , {field: 'isUsable', title: '状态', templet: '#buttonTpl'}
            , {field: 'createTime', title: '创建时间'}
            , {title: '操作', width: 240, align: 'center', fixed: 'right', toolbar: '#table-useradmin-admin-user'}
        ]]
        , page: true
        , limit: 10
        , height: 'full-220'
        , parseData: function (res) { //res 即为原始返回的数据
            var success = res.success;
            if (success) {
                var result = res.result;
                return {
                    "code": 0, //解析接口状态
                    "msg": '', //解析提示文本
                    "count": result.total, //解析数据长度
                    "data": result.list //解析数据列表
                };
            }
        }
    });

    //监听工具条
    table.on('tool(LAY-user-manage)', function (obj) {
        var data = obj.data;
        if (obj.event === 'del') {
            layer.confirm('确定删除此用户？', function (index) {
                $.ajax({
                    url: "delete",
                    type: "post",
                    data: {
                        id: data.id
                    },
                    success: function (result) {
                        if (result.success) {
                            table.reload('LAY-user-manage'); //数据刷新
                            layer.close(index); //关闭弹层
                            layer.msg('删除成功');
                        } else {
                            layer.msg('删除失败');
                        }
                    }
                });
                layer.close(index);
            });
        } else if (obj.event === 'edit') {
            var tr = $(obj.tr);
            layer.open({
                type: 2
                , title: '编辑用户'
                , content: 'toEdit?id=' + data.id
                , maxmin: true
                , area: ['500px', '450px']
                , btn: ['确定', '取消']
                , yes: function (index, layero) {
                    var iframeWindow = window['layui-layer-iframe' + index]
                        , submitID = 'LAY-user-edit-submit'
                        , submit = layero.find('iframe').contents().find('#' + submitID);

                    //监听提交
                    iframeWindow.layui.form.on('submit(' + submitID + ')', function (data) {
                        var field = data.field; //获取提交的字段
                        if (field.isUsable === "on") {
                            field.isUsable = '1';
                        } else {
                            field.isUsable = '0';
                        }
                        $.ajax({
                            url: "update",
                            type: "post",
                            data: field,
                            success: function (result) {
                                if (result.success) {
                                    table.reload('LAY-user-manage'); //数据刷新
                                    layer.close(index); //关闭弹层
                                    layer.msg('修改成功');
                                } else {
                                    layer.msg('修改失败');
                                }
                            }
                        });
                    });

                    submit.trigger('click');
                }
                , success: function (layero, index) {

                }
            });
        } else if (obj.event === 'role') {
            layer.open({
                type: 2
                , title: '角色设置'
                , content: 'toRole?id=' + data.id
                , maxmin: true
                , area: ['500px', '450px']
                , btn: ['确定', '取消']
                , yes: function (index, layero) {
                    var iframeWindow = window['layui-layer-iframe' + index]
                        , submitID = 'LAY-user-role-submit'
                        , submit = layero.find('iframe').contents().find('#' + submitID);

                    //监听提交
                    iframeWindow.layui.form.on('submit(' + submitID + ')', function (data) {
                        var field = data.field; //获取提交的字段
                        var param = [];
                        var roles = [];
                        for (var i in field) {
                            param.push(field[i])
                        }
                        console.log(param);
                        for (var i = 1; i < param.length; i++) {
                            roles.push(param[i]);
                        }
                        console.log(roles);
                        field.roles = roles;
                        $.ajax({
                            url: "setRole",
                            type: "post",
                            data: field,
                            success: function (result) {
                                if (result.success) {
                                    table.reload('LAY-user-manage'); //数据刷新
                                    layer.close(index); //关闭弹层
                                    layer.msg('设置成功');
                                } else {
                                    layer.msg('设置失败');
                                }
                            }
                        });
                    });

                    submit.trigger('click');
                }
                , success: function (layero, index) {

                }
            });
        }
    });

    //角色管理
    table.render({
        elem: '#LAY-user-back-role'
        , url: 'getListByPage'
        , page: true //开启分页
        , cols: [[
            {type: 'checkbox', fixed: 'left'}
            , {field: 'id', width: 80, title: 'ID', sort: true}
            , {field: 'roleName', title: '角色名'}
            , {field: 'roleCode', title: '角色编码'}
            , {field: 'isUsable', title: '状态', templet: '#buttonTpl'}
            , {field: 'roleDesc', title: '描述'}
            , {title: '操作', width: 150, align: 'center', fixed: 'right', toolbar: '#table-useradmin-admin'}
        ]]
        , parseData: function (res) { //res 即为原始返回的数据
            var success = res.success;
            if (success) {
                var result = res.result;
                return {
                    "code": 0, //解析接口状态
                    "msg": '', //解析提示文本
                    "count": result.total, //解析数据长度
                    "data": result.list //解析数据列表
                };
            }
        }
    });

    //监听工具条
    table.on('tool(LAY-user-back-role)', function (obj) {
        var data = obj.data;
        if (obj.event === 'del') {
            layer.confirm('确定删除此角色？', function (index) {
                $.ajax({
                    url: "delete",
                    type: "post",
                    data: {
                        id: data.id
                    },
                    success: function (result) {
                        if (result.success) {
                            table.reload('LAY-user-back-role'); //数据刷新
                            layer.close(index); //关闭弹层
                            layer.msg('删除成功');
                        } else {
                            layer.msg('删除失败');
                        }
                    }
                });
                layer.close(index);
            });
        } else if (obj.event === 'edit') {
            layer.open({
                type: 2
                , title: '编辑角色'
                , content: 'toEdit?id=' + data.id
                , area: ['500px', '480px']
                , btn: ['确定', '取消']
                , yes: function (index, layero) {
                    var iframeWindow = window['layui-layer-iframe' + index]
                        , submit = layero.find('iframe').contents().find("#LAY-user-role-submit");

                    //监听提交
                    iframeWindow.layui.form.on('submit(LAY-user-role-submit)', function (data) {
                        var field = data.field; //获取提交的字段
                        if (field.isUsable === "on") {
                            field.isUsable = 1;
                        } else {
                            field.isUsable = 0;
                        }
                        //提交 Ajax 成功后，静态更新表格中的数据
                        $.ajax({
                            url: "update",
                            type: "post",
                            data: field,
                            success: function (result) {
                                if (result.success) {
                                    table.reload('LAY-user-back-role'); //数据刷新
                                    layer.close(index); //关闭弹层
                                    layer.msg('修改成功');
                                } else {
                                    layer.msg('修改失败');
                                }
                            }
                        });
                    });
                    submit.trigger('click');
                }
                , success: function (layero, index) {
                }
            })
        }
    });
    exports('useradmin', {})
});