$(function () {
    /**
     * 点击菜单按钮
     */
    $('li[class="treeview"]').on('click', function (e) {
        var href = $(this).find('a').attr('href');
        if (href && href != '#') {
            //主界面加载链接对应的页面
            $('#content-wrapper').load(href, function () {

            });
            e.preventDefault();
        }
    });


});
/**
 * 常用的插件封装.
 */
(function ($) {
    var _fill_form = function (row) {
        var $form = $(this);
        $.each(row, function (key, value) {
            // 如果类型为单选框
            if ($form.find('[name="' + key + '"]').attr('type') == 'radio') {
                $form.find('[name="' + key + '"][value="' + value + '"]').prop('checked', true);
            } else if (typeof(value) === "boolean") {
                //布尔类型转换为数值0和1
                $('#editForm [name="' + key + '"]').val(value + 0);
            } else {
                $form.find("[name='" + key + "']").val(value);
            }
        })
        //select2
        //Initialize Select2 Elements
        $('.select2').select2({width: '100%'});
    }

    //填充表单
    $.fn.fillForm = _fill_form;
})(jQuery);