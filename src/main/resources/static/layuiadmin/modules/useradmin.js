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
            , {field: 'id', width: 100, title: 'ID', sort: true}
            , {field: 'realName', title: '姓名'}
            , {field: 'username', title: '账号'}
            , {field: 'userType', title: '类型', templet: '#userTypeTpl'}
            , {field: 'isUsable', title: '状态', templet: '#buttonTpl'}
            , {field: 'createTime', title: '创建时间'}
            , {title: '操作', width: 200, align: 'center', fixed: 'right', toolbar: '#table-useradmin-admin-user'}
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
            layer.prompt({
                formType: 1
                , title: '敏感操作，请验证口令'
            }, function (value, index) {
                layer.close(index);

                layer.confirm('真的删除行么', function (index) {
                    obj.del();
                    layer.close(index);
                });
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
                        , submitID = 'LAY-user-front-submit'
                        , submit = layero.find('iframe').contents().find('#' + submitID);

                    //监听提交
                    iframeWindow.layui.form.on('submit(' + submitID + ')', function (data) {
                        var field = data.field; //获取提交的字段

                        //提交 Ajax 成功后，静态更新表格中的数据
                        //$.ajax({});
                        table.reload('LAY-user-front-submit'); //数据刷新
                        layer.close(index); //关闭弹层
                    });

                    submit.trigger('click');
                }
                , success: function (layero, index) {

                }
            });
        }
    });

    //管理员管理
    table.render({
        elem: '#LAY-user-back-manage'
        , url: layui.setter.base + 'json/useradmin/mangadmin.js' //模拟接口
        , cols: [[
            {type: 'checkbox', fixed: 'left'}
            , {field: 'id', width: 80, title: 'ID', sort: true}
            , {field: 'loginname', title: '登录名'}
            , {field: 'telphone', title: '手机'}
            , {field: 'email', title: '邮箱'}
            , {field: 'role', title: '角色'}
            , {field: 'jointime', title: '加入时间', sort: true}
            , {field: 'check', title: '审核状态', templet: '#buttonTpl', minWidth: 80, align: 'center'}
            , {title: '操作', width: 150, align: 'center', fixed: 'right', toolbar: '#table-useradmin-admin'}
        ]]
        , text: '对不起，加载出现异常！'
    });

    //监听工具条
    table.on('tool(LAY-user-back-manage)', function (obj) {
        var data = obj.data;
        if (obj.event === 'del') {
            layer.prompt({
                formType: 1
                , title: '敏感操作，请验证口令'
            }, function (value, index) {
                layer.close(index);
                layer.confirm('确定删除此管理员？', function (index) {
                    console.log(obj)
                    obj.del();
                    layer.close(index);
                });
            });
        } else if (obj.event === 'edit') {
            var tr = $(obj.tr);

            layer.open({
                type: 2
                , title: '编辑管理员'
                , content: '../../../views/user/administrators/adminform.html'
                , area: ['420px', '420px']
                , btn: ['确定', '取消']
                , yes: function (index, layero) {
                    var iframeWindow = window['layui-layer-iframe' + index]
                        , submitID = 'LAY-user-back-submit'
                        , submit = layero.find('iframe').contents().find('#' + submitID);

                    //监听提交
                    iframeWindow.layui.form.on('submit(' + submitID + ')', function (data) {
                        var field = data.field; //获取提交的字段

                        //提交 Ajax 成功后，静态更新表格中的数据
                        //$.ajax({});
                        table.reload('LAY-user-front-submit'); //数据刷新
                        layer.close(index); //关闭弹层
                    });

                    submit.trigger('click');
                }
                , success: function (layero, index) {

                }
            })
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