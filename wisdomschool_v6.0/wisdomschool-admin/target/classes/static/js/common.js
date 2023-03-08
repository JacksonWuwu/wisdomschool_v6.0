/**
 * 通用js方法封装处理
 * Copyright (c) 2018 wstom
 */
(function ($) {
    $.extend({
        _treeTable: {},
        _tree: {},
        // 表格封装处理
        table: {
            _option: {},
            _params: {},
            // 初始化表格参数
            init: function (options) {
                $.table._option = options;
                $.table._params = $.common.isEmpty(options.queryParams) ? $.table.queryParams : options.queryParams;
                let _sortOrder = $.common.isEmpty(options.sortOrder) ? "asc" : options.sortOrder;
                let _sortName = $.common.isEmpty(options.sortName) ? "" : options.sortName;
                let _striped = $.common.isEmpty(options.striped) ? false : options.striped;
                let _content = $.common.isEmpty(options.content) ? 'bootstrap-table' : options.content;
                let _toolbar = $.common.isEmpty(options.toolbar) ? 'toolbar' : options.toolbar;
                let _visible = $.common.visible(options.pagination);
                $('#' + _content).bootstrapTable({
                    url: options.url,                                     // 请求后台的URL（*）
                    contentType: "application/x-www-form-urlencoded",     // 编码类型
                    method: 'post',                                       // 请求方式（*）
                    cache: false,                                         // 是否使用缓存
                    striped: _striped,                                    // 是否显示行间隔色
                    sortable: true,                                       // 是否启用排序
                    sortStable: true,                                     // 设置为 true 将获得稳定的排序
                    sortName: _sortName,                                  // 排序列名称
                    sortOrder: _sortOrder,                                // 排序方式  asc 或者 desc
                    pagination: _visible,                                 // 是否显示分页（*）
                    pageNumber: 1,                                        // 初始化加载第一页，默认第一页
                    pageSize: 10,                                         // 每页的记录行数（*）
                    pageList: [10, 25, 50],                               // 可供选择的每页的行数（*）
                    iconSize: 'outline',                                  // 图标大小：undefined默认的按钮尺寸 xs超小按钮sm小按钮lg大按钮
                    toolbar: '#' + _toolbar,                              // 指定工作栏
                    sidePagination: "server",                             // 启用服务端分页
                    search: $.common.visible(options.search),             // 是否显示搜索框功能
                    showSearch: $.common.visible(options.showSearch),     // 是否显示检索信息
                    showRefresh: $.common.visible(options.showRefresh),   // 是否显示刷新按钮
                    showColumns: $.common.visible(options.showColumns),   // 是否显示隐藏某列下拉框
                    showToggle: $.common.visible(options.showToggle),     // 是否显示详细视图和列表视图的切换按钮
                    showExport: $.common.visible(options.showExport),     // 是否支持导出文件
                    queryParams: $.table._params,                         // 传递参数（*）
                    columns: options.columns,                             // 显示列信息（*）
                    responseHandler: $.table.responseHandler              // 回调函数
                });
            },
            // 查询条件
            queryParams: function (params) {
                return {
                    // 传递参数查询参数
                    pageSize: params.limit,                               // 页面大小
                    pageNum: params.offset / params.limit + 1,            // 页面数量
                    searchValue: params.search,                           // 搜索内容
                    orderByColumn: params.sort,                           // 排序列名称
                    isAsc: params.order                                   // 排序方式  asc 或者 desc
                };
            },
            // 请求获取数据后处理回调函数
            responseHandler: function (res) {
                if (res.code == 0) {
                    return {
                        rows: res.rows,
                        total: res.total
                    };
                } else {
                    $.modal.alertWarning(res.msg);
                    return {
                        rows: [],
                        total: 0
                    };
                }
            },
            // 搜索-默认第一个form
            search: function (formId) {
                let currentId = $.common.isEmpty(formId) ? $('form').attr('id') : formId;
                let params = $("#bootstrap-table").bootstrapTable('getOptions');
                params.queryParams = function (params) {
                    let search = {};
                    $.each($("#" + currentId).serializeArray(), function (i, field) {
                        search[field.name] = field.value;
                    });
                    search.pageSize = params.limit;
                    search.pageNum = params.offset / params.limit + 1;
                    search.searchValue = params.search;
                    search.orderByColumn = params.sort;
                    search.isAsc = params.order;
                    return search;
                };
                $("#bootstrap-table").bootstrapTable('refresh', params);
            },
            // 导出excel-默认第一个form
            exportExcel: function (formId) {
                let currentId = $.common.isEmpty(formId) ? $('form').attr('id') : formId;
                $.modal.loading("正在导出数据，请稍后...");
                $.post($.table._option.exportUrl, $("#" + currentId).serializeArray(), function (result) {
                    if (result.code == web_status.SUCCESS) {
                         window.location.href = "/common/download?fileName=" + result.msg + "&delete=" + true+"&token="+localStorage.getItem("token");
                    } else {
                        $.modal.alertError(result.msg);
                    }
                    $.modal.closeLoading();
                }, 'json');
            },
            // 刷新表格
            refresh: function () {
                $("#bootstrap-table").bootstrapTable('refresh', {
                    url: $.table._option.url,
                    silent: true
                });
                $("#bootstrap-table-resource-list").bootstrapTable('refresh', {
                    url: $.table._option.url,
                    silent: true
                });
                $("#bootstrap-table-forum-list").bootstrapTable('refresh', {
                    url: $.table._option.url,
                    silent: true
                });
                $("#bootstrap-table-type").bootstrapTable('refresh', {
                    url: $.table._option.url,
                    silent: true
                });
            },
            // 查询表格指定列值
            selectColumns: function (column) {
                return $.map($('#bootstrap-table').bootstrapTable('getSelections'), function (row) {
                    return row[column];
                });
            },
            // 查询表格首列值
            selectFirstColumns: function () {
                return $.map($('#bootstrap-table').bootstrapTable('getSelections'), function (row) {
                    return row[$.table._option.columns[1].field];
                });
            },
            // 回显数据字典
            selectDictLabel: function (datas, value) {
                let actions = [];
                $.each(datas, function (index, dict) {
                    if (dict.dictValue == value) {
                        actions.push("<span class='badge badge-" + dict.listClass + "'>" + dict.dictLabel + "</span>");
                        return false;
                    }
                });
                return actions.join('');
            },
            // 下载模板
            exportTemplate: function (fileName) {
                $.getJSON($.table._option.importTemplateUrl, {"fileName": fileName}, function (result) {
                    if (result.code === web_status.SUCCESS) {
                        window.location.href = ctx + "common/download?fileName=" + result.msg + "&delete=" + true;
                    } else {
                        $.modal.alertError(result.msg);
                    }
                });
            },
            // 导入数据
            importExcel: function (formId) {
                let currentId = $.common.isEmpty(formId) ? 'importForm' : formId;
                $.form.reset(currentId);
                layer.open({
                    type: 1,
                    area: ['400px', '230px'],
                    fix: false,
                    //不固定
                    maxmin: true,
                    shade: 0.3,
                    title: '导入' + $.table._option.modalName + '数据',
                    content: $('#' + currentId),
                    btn: ['<i class="fa fa-check"></i> 导入', '<i class="fa fa-remove"></i> 取消'],
                    // 弹层外区域关闭
                    shadeClose: true,
                    btn1: function (i, layero) {
                        let file = layero.find('#file').val();
                        if (file == '' || (!$.common.endWith(file, '.xls') && !$.common.endWith(file, '.xlsx'))) {
                            $.modal.msgWarning("请选择后缀为 “xls”或“xlsx”的文件。");
                            return false;
                        }
                        let index = layer.load(2, {shade: false});
                        $.modal.disable();
                        let formData = new FormData();
                        formData.append("file", $('#file')[0].files[0]);
                        formData.append("updateSupport", $("input[name='updateSupport']").is(':checked'));
                        $.ajax({
                            url: $.table._option.importUrl,
                            data: formData,
                            cache: false,
                            contentType: false,
                            processData: false,
                            dataType: 'json',
                            type: 'POST',
                            success: function (result) {
                                if (result.code == web_status.SUCCESS) {
                                    $.modal.closeAll();
                                    $.modal.alertSuccess(result.msg);
                                    $.table.refresh();
                                } else {
                                    layer.close(index);
                                    $.modal.enable();
                                    $.modal.alertError(result.msg);
                                }
                            }
                        });
                    }
                });
            }
        },
        // 表格树封装处理
        treeTable: {
            _option: {},
            // 初始化表格
            init: function (options) {
                $.table._option = options;
                let _striped = $.common.isEmpty(options.striped) ? false : options.striped;
                let _expandColumn = $.common.isEmpty(options.expandColumn) ? '1' : options.expandColumn;
                let treeTable = $('#bootstrap-table').bootstrapTreeTable({
                    code: options.code,                                 // 用于设置父子关系
                    parentCode: options.parentCode,                     // 用于设置父子关系
                    type: 'get',                                        // 请求方式（*）
                    url: options.url,                                   // 请求后台的URL（*）
                    ajaxParams: {},                                     // 请求数据的ajax的data属性
                    expandColumn: _expandColumn,                        // 在哪一列上面显示展开按钮
                    striped: _striped,                                  // 是否显示行间隔色
                    bordered: true,                                     // 是否显示边框
                    toolbar: '#toolbar',                                // 指定工作栏
                    showRefresh: $.common.visible(options.showRefresh), // 是否显示刷新按钮
                    showColumns: $.common.visible(options.showColumns), // 是否显示隐藏某列下拉框
                    expandAll: $.common.visible(options.expandAll),     // 是否全部展开
                    expandFirst: $.common.visible(options.expandFirst), // 是否默认第一级展开--expandAll为false时生效
                    columns: options.columns
                });
                $._treeTable = treeTable;
            },
            // 条件查询
            search: function (formId) {
                let currentId = $.common.isEmpty(formId) ? $('form').attr('id') : formId;
                let params = {};
                $.each($("#" + currentId).serializeArray(), function (i, field) {
                    params[field.name] = field.value;
                });
                $._treeTable.bootstrapTreeTable('refresh', params);
            },
            // 刷新
            refresh: function () {
                $._treeTable.bootstrapTreeTable('refresh');
            },
            // tree表格树 展开/折叠
            expandAllFlag: function () {
                let expandFlag = !$(this).data("expand");
                if (expandFlag) {
                    $('#bootstrap-table').bootstrapTreeTable('expandAll');
                } else {
                    $('#bootstrap-table').bootstrapTreeTable('collapseAll');
                }
                $(this).data("expand", expandFlag);
            }
        },
        /**
         * 上传处理
         */
        upload: {
            _options: {},
            /**
             * 上传组件初始化
             * @param option
             */
            init: function (option) {
                $.upload._options = option;
                /*id默认为multipartFile*/
                let content = $.common.isEmpty($.upload._options.content) ? "multipartFile" : $.upload._options.content;
                /*let url = $('input[name=thumbnail]').val();*/
                let reviewUrl = $.common.isEmpty($.upload._options.initPreviewUrl) ? "" : $.upload._options.initPreviewUrl;
                /**
                 * 默认为图片类型
                 * @type {string[]}
                 */
                let allowFileExt = $.common.isEmpty($.upload._options.allowFileExt) ? ['jpg', 'png', 'bmp', 'jpeg'] : $.upload._options.allowFileExt;
                let allowFileNum = $.common.isEmpty($.upload._options.allowFileNum) ? 1 : $.upload._options.allowFileNum;
                let maxFileSize = $.common.isEmpty($.upload._options.maxFileSize) ? 500 : $.upload._options.maxFileSize;
                $("#" + content).fileinput({
                    language: 'zh', //设置语言
                    initialPreview: [reviewUrl],
                    initialPreviewAsData: true,
                    browseClass: "btn btn-default btn-block",
                    showCaption: false,
                    enctype: 'multipart/form-data',
                    allowedFileExtensions: allowFileExt,//接收的文件后缀['jpg', 'png','bmp','jpeg']
                    showUpload: false, //是否显示上传按钮
                    showPreview: true, //展前预览
                    maxFileSize: maxFileSize * 1024, //上传文件最大的尺寸 单位：M
                    maxFilesNum: allowFileNum,   //允许文件上传数
                    dropZoneEnabled: true,//是否显示拖拽区域
                    uploadAsync: false,
                    showClose: false,
                    showRemove: false,
                    autoReplace: true,
                    dropZoneTitle: "拖拽文件到这里 &hellip;",
                    msgFilesTooMany: "选择上传的文件数量({n}) 超过允许的最大数值{m}！"
                });
            }
        },
        // 表单封装处理
        form: {
            // 表单重置
            reset: function (formId) {
                let currentId = $.common.isEmpty(formId) ? $('form').attr('id') : formId;
                $("#" + currentId)[0].reset();
            },
            // 获取选中复选框项
            selectCheckeds: function (name) {
                let checkeds = "";
                $('input:checkbox[name="' + name + '"]:checked').each(function (i) {
                    if (0 === i) {
                        checkeds = $(this).val();
                    } else {
                        checkeds += ("," + $(this).val());
                    }
                });
                return checkeds;
            },
            // 获取选中下拉框项
            selectSelects: function (name) {
                let selects = "";
                $('#' + name + ' option:selected').each(function (i) {
                    if (0 === i) {
                        selects = $(this).val();
                    } else {
                        selects += ("," + $(this).val());
                    }
                });
                return selects;
            },
            loadContent: function (callBack) {
                $("input[data-load-action='load-content']").focus(function() {
                    $(".panel-drop").show();
                    /*
                    加载面板内容，只加载一次
                     */
                    if(!$("#load-panel").data("_init")) {
                        $("#load-panel").load(prefix + $("#load-panel").data("include"), null, function () {
                            callBack();
                            $("#load-panel").data("_init", true);
                        })
                    }
                });
            }
        },
        // 弹出层封装处理
        modal: {
            // 显示图标
            icon: function (type) {
                let icon = "";
                if (type === modal_status.WARNING) {
                    icon = 0;
                } else if (type === modal_status.SUCCESS) {
                    icon = 1;
                } else if (type === modal_status.FAIL) {
                    icon = 2;
                } else {
                    icon = 3;
                }
                return icon;
            },
            // 消息提示
            msg: function (content, type) {
                if (type !== undefined) {
                    layer.msg(content, {
                        icon: $.modal.icon(type),
                        time: 1000,
                        shift: 5,
                        zIndex: 99999999
                    });
                } else {
                    layer.msg(content);
                }
            },
            // 错误消息
            msgError: function (content) {
                $.modal.msg(content, modal_status.FAIL);
            },
            // 成功消息
            msgSuccess: function (content) {
                $.modal.msg(content, modal_status.SUCCESS);
            },
            // 警告消息
            msgWarning: function (content) {
                $.modal.msg(content, modal_status.WARNING);
            },
            // 弹出提示
            alert: function (content, type) {
                layer.alert(content, {
                    icon: $.modal.icon(type),
                    title: "系统提示",
                    btn: ['确认'],
                    btnclass: ['btn btn-primary'],
                });
            },
            // 消息提示并刷新父窗体
            msgReloadWindow: function (msg, type) {
                layer.msg(msg, {
                        icon: $.modal.icon(type),
                        time: 500,
                        shade: [0.1, '#8F8F8F']
                    },
                    function () {
                        $.modal.reload();
                    });
            },
            // 消息提示并关闭模态框
            msgCloseModel: function (msg, type) {
                layer.msg(msg, {
                        icon: $.modal.icon(type),
                        time: 500,
                        shade: [0.1, '#8F8F8F']
                    },
                    function () {
                        $.modal.close();
                    });
            },
            msgCallback: function(msg, type, callback) {
                layer.msg(msg, {
                        icon: $.modal.icon(type),
                        time: 500,
                        shade: [0.1, '#8F8F8F']
                    },
                    function () {
                        callback();
                    });
            },
            // 错误提示
            alertError: function (content) {
                $.modal.alert(content, modal_status.FAIL);
                $.table.refresh();
            },
            // 成功提示
            alertSuccess: function (content) {
                $.modal.alert(content, modal_status.SUCCESS);
                $.table.refresh();
            },
            // 警告提示
            alertWarning: function (content) {
                $.modal.alert(content, modal_status.WARNING);
            },
            // 关闭窗体
            close: function () {
                let index = parent.layer.getFrameIndex(window.name);
                parent.layer.close(index);
            },
            // 关闭全部窗体
            closeAll: function () {
                layer.closeAll();
            },
            // 确认窗体
            confirm: function (content, callBack) {
                layer.confirm(content, {
                    icon: 3,
                    title: "系统提示",
                    btn: ['确认', '取消'],
                    btnclass: ['btn btn-primary', 'btn btn-danger'],
                }, function (index) {
                    layer.close(index);
                    callBack(true);
                });
            },
            // 弹出层指定宽度

            open: function (title, url, callback, width, height, endCallback) {
                let token = localStorage.getItem("token");
                if (url.indexOf("?") === -1){
                    url=url+"?token="+token
                }else {
                    url=url+"&token="+token
                }
                //如果是移动端，就使用自适应大小弹窗
                if (navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)) {
                    width = 'auto';
                    height = 'auto';
                }
                if ($.common.isEmpty(title)) {
                    title = false;
                }
                if ($.common.isEmpty(url)) {
                    url = "/404.html";
                }
                if ($.common.isEmpty(width)) {
                    width = 800 + 'px';
                }
                if ($.common.isEmpty(height)) {
                    height = ($(window).height() - 150) + 'px';
                }
                layer.open({
                    type: 2,
                    area: [width, height],
                    fix: false,
                    //不固定
                    maxmin: true,
                    shade: 0.3,
                    title: title,
                    zIndex: 6666,
                    content: url,
                    btn: ['确定', '关闭'],
                    // 弹层外区域关闭
                    shadeClose: true,
                    yes: function (index, layero) {
                        let iframeWin = layero.find('iframe')[0];
                        iframeWin.contentWindow.submitHandler();
                        if (callback !== undefined) {
                            callback(iframeWin.contentWindow);
                        }
                    },
                    cancel: function (index) {
                        return true;
                    },
                    end: function () {
                        if (endCallback !== undefined) {
                            endCallback();
                        }
                    }
                });
            },
            // 弹出层指定参数选项
            openOptions: function (options) {
                let _url = $.common.isEmpty(options.url) ? "/404.html" : options.url;
                let _title = $.common.isEmpty(options.title) ? "系统窗口" : options.title;
                let _width = $.common.isEmpty(options.width) ? "800" : options.width;
                let _height = $.common.isEmpty(options.height) ? ($(window).height() - 50) : options.height;
                layer.open({
                    type: 2,
                    maxmin: true,
                    shade: 0.3,
                    title: _title,
                    fix: false,
                    area: [_width + 'px', _height + 'px'],
                    content: _url,
                    shadeClose: true,
                    btn: ['<i class="fa fa-check"></i> 确认', '<i class="fa fa-close"></i> 关闭'],
                    yes: function (index, layero) {
                        if (options.callBack !== undefined) {
                            options.callBack(index, layero)
                        }
                    },
                    cancel: function () {
                        return true;
                    }
                });
            },
            /**
             * 弹出层全屏
             * @param {String} title 标题
             * @param {String} url 请求地址
             * @param {Object} width 弹窗宽度
             * @param {Object} height 弹窗高度
             * @param callback 回调函数
             */
            openFull: function (title, url, callback, width, height, endCallback) {
                let token = localStorage.getItem("token");
                if (url.indexOf("?") === -1){
                    url=url+"?token="+token
                }else {
                    url=url+"&token="+token
                }
                //如果是移动端，就使用自适应大小弹窗
                if (navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)) {
                    width = 'auto';
                    height = 'auto';
                }
                if ($.common.isEmpty(title)) {
                    title = false;
                }
                if ($.common.isEmpty(url)) {
                    url = "/404.html";
                }
                if ($.common.isEmpty(width)) {
                    width = 800;
                }
                if ($.common.isEmpty(height)) {
                    height = ($(window).height() - 50);
                }
                let index = layer.open({
                    type: 2,
                    area: [width + 'px', height + 'px'],
                    fix: false,
                    //不固定
                    maxmin: true,
                    shade: 0.3,
                    title: title,
                    content: url,
                    // 弹层外区域关闭
                    shadeClose: true,
                    btn: ['确定', '关闭'],
                    yes: function (index, layero) {
                        let iframeWin = layero.find('iframe')[0];
                        iframeWin.contentWindow.submitHandler();
                        if (!$.common.isEmpty(callback)) {
                            callback(iframeWin.contentWindow);
                        }
                    },
                    cancel: function (index) {
                        return true;
                    },
                    end: function () {
                        if (!$.common.isEmpty(endCallback)) {
                            endCallback();
                        }
                    }
                });
                layer.full(index);
            },
            /**
             * 禁用按钮
             */
            disable: function () {
                let doc = window.top == window.parent ? window.document : window.parent.document;
                $("a[class*=layui-layer-btn]", doc).addClass("layer-disabled");
            },
            /**
             * 启用按钮
             */
            enable: function () {
                let doc = window.top == window.parent ? window.document : window.parent.document;
                $("a[class*=layui-layer-btn]", doc).removeClass("layer-disabled");
            },
            /**
             * 打开遮罩层
             * @param {String} message 提示信息
             */
            loading: function (message) {
                $.blockUI({
                    message: '<div class="loader-box"><div class="loading-activity"></div> ' + message + '</div>',
                    css: {
                        border: 'none',
                        backgroundColor: 'transparent',
                        'z-index': 9999999
                    }
                });
            },
            /**
             * 关闭遮罩层
             */
            closeLoading: function () {
                setTimeout(function () {
                    $.unblockUI();
                }, 50);
            },
            /**
             * 重新加载
             */
            reload: function () {
                parent.location.reload();
            }
        },
        // 操作封装处理
        operate: {
            /**
             * 提交数据
             * @param {*} url 提交地址
             * @param {*} type 提交类型 get/post
             * @param {*} dataType 数据类型
             * @param {*} data 携带参数
             * @param async
             * @param {function} callback 回调函数
             */
            submit: function (url, type, dataType, data, async, callback) {
                //默认异步上传
                let asy = $.common.isEmpty(async) ? true : async;
                $.operate._ajaxRequest(url, type, data, dataType, asy, callback);
            },
            /**
             * 保存信息， 模态框操作
             */
            saveModal: function (url, data) {
                $.operate.submit(url, "post", "json", data, true, $.operate.ajaxModalSuccess);
            },
            /**
             * post请求传输
             * @param {String} url 提交地址
             * @param {var} data 请求参数
             */
            submitPost: function (url, data) {
                $.operate.submit(url, "post", "json", data, $.operate.ajaxSuccess);
            },
            /**
             * 请求传输
             * @param {String} url 提交地址
             * @param form
             * @param callback
             */
            submitXHR: (url, form, callback) => {
                let data = new FormData($(form)[0]);
                let xhr = new XMLHttpRequest();
                xhr.open("POST", url, true);
                xhr.setRequestHeader("Authorization",localStorage.getItem("token"))

                xhr.onloadend = () => {
                    $.modal.loading("正在处理中，请稍后...");
                    if (xhr.status === 200) {
                        $.modal.closeLoading();
                        let result = $.parseJSON(xhr.responseText);
                        if (callback !== undefined) {
                            callback(result);
                        }
                    } else {
                        $.modal.closeLoading();
                        $.modal.alertError("上传异常!请稍后再试");
                    }
                };
                xhr.send(data);
            },
            postJson: (url, data, async, callback) => {
                $.operate._ajaxRequest(url, 'post', data, 'json', async, callback);
            },
            getJson: (url, data, async, callback, endCallback) => {
                $.operate._ajaxRequest(url, 'get', data, 'json', async, callback, endCallback);
            },
            _ajaxRequest: (url, type, data, dataType, async, callback, endCallback) => {
                $.modal.loading("正在处理中，请稍后...");
                let config = {
                    url: url,
                    type: type,
                    async: async,
                    dataType: dataType,
                    data: data,
                    success: function (result) {
                        if (callback != undefined) {
                            callback(result);
                        }
                        $.modal.closeLoading();
                    },
                    error: function (msg) {
                        $.modal.msgError("请求失败！请检查网络状态或通知网络管理员");
                    },
                    end: function () {
                        if (endCallback !== undefined) {
                            endCallback();
                        }
                    }
                };
                $.ajax(config)
            },
            // 详细信息
            detail: function (id, width, height) {
                let _url = $.common.isEmpty(id) ? $.table._option.detailUrl : $.table._option.detailUrl.replace("{id}", id);
                let _width = $.common.isEmpty(width) ? "800" : width;
                let _height = $.common.isEmpty(height) ? ($(window).height() - 50) : height;
                //如果是移动端，就使用自适应大小弹窗
                if (navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)) {
                    _width = 'auto';
                    _height = 'auto';
                }
                layer.open({
                    type: 2,
                    area: [_width + 'px', _height + 'px'],
                    fix: false,
                    //不固定
                    maxmin: true,
                    shade: 0.3,
                    title: $.table._option.modalName + "详细",
                    content: _url,
                    btn: '关闭',
                    // 弹层外区域关闭
                    shadeClose: true,
                    success: function (layer) {
                        layer[0].childNodes[3].childNodes[0].attributes[0].value = 'layui-layer-btn1';
                    },
                    btn1: function (index) {
                        layer.close(index);
                    }
                });
            },
            // 删除信息
            remove: function (id, callback) {
                $.modal.confirm("确定删除该条" + $.table._option.modalName + "数据吗？", function () {
                    let url = $.common.isEmpty(id) ? $.table._option.removeUrl : $.table._option.removeUrl.replace("{id}", id);
                    let data = {
                        "ids": id
                    };
                    $.operate.submit(url, "post", "json", data, true, callback);
                });
            },
            // 批量删除信息
            removeAll: function () {
                let rows = $.common.isEmpty($.table._option.uniqueId) ? $.table.selectFirstColumns() : $.table.selectColumns($.table._option.uniqueId);
                if (rows.length <= 0) {
                    $.modal.alertWarning("请至少选择一条记录");
                    return;
                }
                $.modal.confirm("确认要删除选中的" + rows.length + "条数据吗?", function () {
                    let url = $.table._option.removeUrl;
                    let data = {
                        "ids": rows.join()
                    };
                    $.operate.submit(url, "post", "json", data, true, $.operate.ajaxSuccess);
                });
            },
            // 清空信息
            clean: function () {
                $.modal.confirm("确定清空所有" + $.table._option.modalName + "吗？", function () {
                    let url = $.table._option.cleanUrl;
                    $.operate.submit(url, "post", "json", "", true, $.operate.ajaxSuccess);
                });
            },
            // 添加信息
            add: function (id) {
                let url = $.common.isEmpty(id) ? $.table._option.createUrl : $.table._option.createUrl.replace("{id}", id);
                //title, url, callback, width, height, endCallback
                $.modal.open("添加" + $.table._option.modalName, url, undefined, undefined, undefined, $.table.refresh);
            },
            // 添加信息
            loadAdd: function (contentId, id) {
                let url = $.common.isEmpty(id) ? $.table._option.createUrl : $.table._option.createUrl.replace("{id}", id);
                $.common.loadPage(contentId, url);
            },
            // 修改信息
            edit: function (id) {
                let url = "/404.html";
                if ($.common.isNotEmpty(id)) {
                    url = $.table._option.updateUrl.replace("{id}", id);
                } else {
                    let id = $.common.isEmpty($.table._option.uniqueId) ? $.table.selectFirstColumns() : $.table.selectColumns($.table._option.uniqueId);
                    if (id.length == 0) {
                        $.modal.alertWarning("请至少选择一条记录");
                        return;
                    }
                    url = $.table._option.updateUrl.replace("{id}", id);
                }
                $.modal.open("修改" + $.table._option.modalName, url, undefined, undefined, undefined, $.table.refresh);
            },
            // 修改信息,非弹窗式
            loadEdit: function (contentId, id) {
                let url = "/404.html";
                if ($.common.isNotEmpty(id)) {
                    url = $.table._option.updateUrl.replace("{id}", id);
                } else {
                    let id = $.common.isEmpty($.table._option.uniqueId) ? $.table.selectFirstColumns() : $.table.selectColumns($.table._option.uniqueId);
                    if (id.length == 0) {
                        $.modal.alertWarning("请至少选择一条记录");
                        return;
                    }
                    url = $.table._option.updateUrl.replace("{id}", id);
                }

                $.common.loadPage(contentId, url);
            },
            // 工具栏表格树修改
            editTree: function () {
                let row = $('#bootstrap-table').bootstrapTreeTable('getSelections')[0];
                if ($.common.isEmpty(row)) {
                    $.modal.alertWarning("请至少选择一条记录");
                    return;
                }
                let url = $.table._option.updateUrl.replace("{id}", row[$.table._option.uniqueId]);
                $.modal.open("修改" + $.table._option.modalName, url);
            },
            // 添加信息 全屏
            addFull: function (id, endCallback) {
                let url = $.common.isEmpty(id) ? $.table._option.createUrl : $.table._option.createUrl.replace("{id}", id);
                $.modal.openFull("添加" + $.table._option.modalName, url, undefined, undefined, undefined, endCallback);
            },
            // 修改信息 全屏
            editFull: function (id) {
                let url = "/404.html";
                if ($.common.isNotEmpty(id)) {
                    url = $.table._option.updateUrl.replace("{id}", id);
                } else {
                    let row = $.common.isEmpty($.table._option.uniqueId) ? $.table.selectFirstColumns() : $.table.selectColumns($.table._option.uniqueId);
                    url = $.table._option.updateUrl.replace("{id}", row);
                }
                $.modal.openFull("修改" + $.table._option.modalName, url);
            },
            // 保存结果弹出msg刷新table表格
            ajaxSuccess: function (result) {
                if (result.code === web_status.SUCCESS) {
                    $.table.refresh();
                    $.modal.msgSuccess(result.msg);
                } else {
                    $.modal.alertError(result.msg);
                }
                $.modal.closeLoading();
            },
            // 保存结果提示msg
            ajaxModalSuccess: function (result) {
                if (result.code == web_status.SUCCESS) {
                    $.modal.msgCloseModel("保存成功……", modal_status.SUCCESS);
                } else {
                    $.modal.alertError(result.msg);
                }
                $.modal.closeLoading();
            }
        },
        // 校验封装处理
        validate: {
            // 判断返回标识是否唯一 false 不存在 true 存在
            unique: function (value) {
                return value != '0';
            },
            // 表单验证
            form: function (formId) {
                let currentId = $.common.isEmpty(formId) ? $('form').attr('id') : formId;
                return $("#" + currentId).validate().form();
            }
        },
        // 树插件封装处理
        tree: {
            _option: {},
            _lastValue: {},
            // 初始化树结构
            init: function (options) {
                $.tree._option = options;
                // 属性ID
                let _id = $.common.isEmpty(options.id) ? "tree" : options.id;
                // 展开等级节点
                let _expandLevel = $.common.isEmpty(options.expandLevel) ? 0 : options.expandLevel;
                // 展开等级节点
                let _async = $.common.isEmpty(options.async) ? true : options.async;
                // 树结构初始化加载
                let setting = {
                    check: options.check,
                    view: {
                        selectedMulti: false,
                        nameIsHTML: true
                    },
                    data: {
                        key: {
                            title: "title"
                        },
                        simpleData: {
                            enable: true
                        }
                    },
                    callback: {
                        onClick: options.onClick,
                        onExpand: options.onExpand,
                        onCollapse: options.onCollapse
                    }
                };
                $.operate.getJson(options.url, null, _async, function (data) {
                    if (data === undefined || data.length <= 0) {
                        $("#" + _id).html("<h4 class='text-danger'>暂无数据信息！请添加</h4>");
                        return;
                    }
                    $("#" + _id).html();
                    let treeName = $("#treeName").val();
                    let treeId = $("#treeId").val();
                    let tree = $.fn.zTree.init($("#" + _id), setting, data);
                    $._tree = tree;
                    // 展开第一级节点
                    let nodes = tree.getNodesByParam("level", 0);
                    for (let i = 0; i < nodes.length; i++) {
                        if (_expandLevel > 0) {
                            tree.expandNode(nodes[i], true, false, false);
                        }
                        $.tree.selectByIdName(treeId, treeName, nodes[i]);
                    }
                    // 展开第二级节点
                    nodes = tree.getNodesByParam("level", 1);
                    for (let i = 0; i < nodes.length; i++) {
                        if (_expandLevel > 1) {
                            tree.expandNode(nodes[i], true, false, false);
                        }
                        $.tree.selectByIdName(treeId, treeName, nodes[i]);
                    }
                    // 展开第三级节点
                    nodes = tree.getNodesByParam("level", 2);
                    for (let i = 0; i < nodes.length; i++) {
                        if (_expandLevel > 2) {
                            tree.expandNode(nodes[i], true, false, false);
                        }
                        $.tree.selectByIdName(treeId, treeName, nodes[i]);
                    }
                });
            },
            // 搜索节点
            searchNode: function () {
                // 取得输入的关键字的值
                let value = $.common.trim($("#keyword").val());
                if ($.tree._lastValue === value) {
                    return;
                }
                // 保存最后一次搜索名称
                $.tree._lastValue = value;
                let nodes = $._tree.getNodes();
                // 如果要查空字串，就退出不查了。
                if (value == "") {
                    $.tree.showAllNode(nodes);
                    return;
                }
                $.tree.hideAllNode(nodes);
                // 根据搜索值模糊匹配
                $.tree.updateNodes($._tree.getNodesByParamFuzzy("name", value));
            },
            addNode: function (pid, newNode) {
                $._tree.addNodes($._tree.getNodesByParam("pId", pid), newNode);
            },
            // 根据Id和Name选中指定节点
            selectByIdName: function (treeId, treeName, node) {
                if ($.common.isNotEmpty(treeName) && $.common.isNotEmpty(treeId)) {
                    if (treeId == node.id && treeName == node.name) {
                        $._tree.selectNode(node, true);
                    }
                }
            },
            // 显示所有节点
            showAllNode: function (nodes) {
                nodes = $._tree.transformToArray(nodes);
                for (let i = nodes.length - 1; i >= 0; i--) {
                    if (nodes[i].getParentNode() != null) {
                        $._tree.expandNode(nodes[i], true, false, false, false);
                    } else {
                        $._tree.expandNode(nodes[i], true, true, false, false);
                    }
                    $._tree.showNode(nodes[i]);
                    $.tree.showAllNode(nodes[i].children);
                }
            },
            // 隐藏所有节点
            hideAllNode: function (nodes) {
                let tree = $.fn.zTree.getZTreeObj("tree");
                nodes = $._tree.transformToArray(nodes);
                for (let i = nodes.length - 1; i >= 0; i--) {
                    $._tree.hideNode(nodes[i]);
                }
            },
            // 显示所有父节点
            showParent: function (treeNode) {
                let parentNode;
                while ((parentNode = treeNode.getParentNode()) != null) {
                    $._tree.showNode(parentNode);
                    $._tree.expandNode(parentNode, true, false, false);
                    treeNode = parentNode;
                }
            },
            // 显示所有孩子节点
            showChildren: function (treeNode) {
                if (treeNode.isParent) {
                    for (let idx in treeNode.children) {
                        let node = treeNode.children[idx];
                        $._tree.showNode(node);
                        $.tree.showChildren(node);
                    }
                }
            },
            // 更新节点状态
            updateNodes: function (nodeList) {
                $._tree.showNodes(nodeList);
                for (let i = 0, l = nodeList.length; i < l; i++) {
                    let treeNode = nodeList[i];
                    $.tree.showChildren(treeNode);
                    $.tree.showParent(treeNode)
                }
            },
            // 获取当前被勾选集合
            getCheckedNodes: function (column) {
                let _column = $.common.isEmpty(column) ? "id" : column;
                let nodes = $._tree.getCheckedNodes(true);
                return $.map(nodes, function (row) {
                    return row[_column];
                }).join();
            },
            // 不允许根父节点选择
            notAllowParents: function (_tree) {
                let nodes = _tree.getSelectedNodes();
                for (let i = 0; i < nodes.length; i++) {
                    if (nodes[i].level == 0) {
                        $.modal.msgError("不能选择根节点（" + nodes[i].name + "）");
                        return false;
                    }
                    if (nodes[i].isParent) {
                        $.modal.msgError("不能选择父节点（" + nodes[i].name + "）");
                        return false;
                    }
                }
                return true;
            },
            // 隐藏/显示搜索栏
            toggleSearch: function () {
                $('#search').slideToggle(200);
                $('#btnShow').toggle();
                $('#btnHide').toggle();
                $('#keyword').focus();
            },
            // 目录树折叠
            collapse: function () {
                $._tree.expandAll(false);
            },
            // 目录树展开
            expand: function () {
                $._tree.expandAll(true);
            }
        },
        // 通用方法封装处理
        common: {
            // 判断字符串是否为空
            isEmpty: function (value) {
                if (value == null || this.trim(value) === "") {
                    return true;
                }
                return false;
            },
            // 判断一个字符串是否为非空串
            isNotEmpty: function (value) {
                return !$.common.isEmpty(value);
            },
            // 是否显示数据 为空默认为显示
            visible: function (value) {
                if ($.common.isEmpty(value) || value == true) {
                    return true;
                }
                return false;
            },
            // 空格截取
            trim: function (value) {
                if (value == null) {
                    return "";
                }
                return value.toString().replace(/(^\s*)|(\s*$)|\r|\n/g, "");
            },
            // 指定随机数返回
            random: function (min, max) {
                return Math.floor((Math.random() * max) + min);
            },
            // 指定随机数返回
            randomStr: function (length) {
                return Math.random().toString(36).substr(length);
            },
            startWith: function (value, start) {
                let reg = new RegExp("^" + start);
                return reg.test(value)
            },
            endWith: function (value, end) {
                let reg = new RegExp(end + "$");
                return reg.test(value)
            },


            /**
             * 加载页面
             * @param id 待装入节点
             * @param url 待装入 HTML 网页网址。
             * @param data 发送至服务器的 key/value 数据。在jQuery 1.3中也可以接受一个字符串了。
             * @param callback 载入成功时回调函数。
             */
            loadPage: function (id, url, data, callback) {
                $.modal.loading("正在处理中，请稍后...");
                 var token=localStorage.getItem("token");
                if (url.indexOf("?") === -1){
                    url=url+"?token="+token
                }else {
                    url=url+"&token="+token
                }
                if (!url.search("redirect:")) {
                    let sub = url.substring(9);
                    window.location.href = sub;
                } else if (!url.search("open:")) {
                    let sub = url.substring(5);
                    console.log(sub);``
                    window.open(sub);
                } else {
                    $("#" + id).load(url, data, function (res, status) {
                        $.modal.closeLoading();
                        if (status === 'error') {
                            $.modal.alertError('页面加载失败！请检查网络是否正常');
                            return;
                        }

                        let obj;
                        try {
                            obj = jQuery.parseJSON(res);
                        } catch (e) {
                            obj = undefined;
                        }

                        if (obj != undefined && obj.code != undefined) {
                            if (obj.code == web_status.NOT_LOGIN) {
                                let wp = window;
                                if (wp.parent && wp.parent !== wp) {
                                    while (wp.parent && wp.parent !== wp) {
                                        wp = wp.parent;
                                    }
                                }
                                wp.location.href = '/login';
                            } else if (obj.code != web_status.SUCCESS) {
                                $.modal.alertError(obj.msg);
                                return;
                            }
                        }
                        if (callback !== undefined) {
                            callback();
                        }
                    });
                }
            },
            /**
             * 初始化事件
             */
            initBind: function () {
                // select2复选框事件绑定
                if ($.fn.select2 !== undefined) {
                    $("select.form-control:not(.noselect2)").each(function () {
                        let $that = $(this);

                        $that.select2().on("change", function () {
                            $(this).valid();
                        });
                        let selected = $that.data('selected');
                        let selectedId;
                        let selectedTmp;
                        console.log("---------");
                        console.log(selectedId);
                        if (selected !== undefined) {
                            let s = selected.split(":");
                            $that.append('<option value="' + s[0] + '">' + s[1] + '</option>');
                            selectedId = s[0];
                        }
                        if ($that.hasClass("select2Cascade")) {
                            $that.on('select2:opening', function () {
                                if ($that.data('init') == true) {
                                    let url = $that.data('url');
                                    let fid = $that.data('fid');
                                    let attrName = $that.data('attrname');
                                    attrName = $.common.isEmpty(attrName) ? 'name' : attrName;
                                    let arr = attrName.split(".");
                                    $.operate.postJson(url, fid ? {'params[fid]': fid} : null, false, function (res) {
                                        if (res && res.rows) {
                                            let options = res.rows.map((value, index) => {
                                                let val = value;
                                                for (a of arr) {
                                                    val = val[a];
                                                }
                                                if (value.id == selectedId) {
                                                    console.log("*******");
                                                    console.log(value.id);
                                                    selectedTmp = '<option value="' + value.id + '">' + val + '</option>';
                                                    return null;
                                                }
                                                return '<option value="' + value.id + '">' + val + '</option>';
                                            });
                                            $that.empty();
                                            if (selectedTmp !== undefined) {

                                                options = $.grep(options, function(n,i){
                                                    return n === null;
                                                }, true);
                                                console.log("+++++++");
                                                console.log(selectedTmp);
                                                $that.append(selectedTmp);
                                            } else {
                                                $that.append('<option value="">--请选择--</option>');
                                            }
                                            $that.append(options.join(''));
                                            $that.data('init', false);
                                        } else {
                                            $that.select2({
                                                data: null
                                            });
                                        }
                                    });
                                }
                            });
                            $that.on('change', function () {
                                let nextId = $that.data('next');
                                if (nextId) {
                                    $("#" + nextId).empty().data('fid', $that.val()).data('init', true);
                                    while (nextId !== undefined) {
                                        nextId = $("#" + nextId).empty().data("next");
                                    }
                                }
                            })
                        }
                    })
                }

                let initFileInput = function () {
                    let url = $('input[name=thumbnail]').val();
                    $("#multipartFile").fileinput({
                        language: 'zh', //设置语言
                        initialPreview: [url],
                        initialPreviewAsData: true,
                        browseClass: "btn btn-default btn-block",
                        showCaption: false,
                        enctype: 'multipart/form-data',
                        allowedFileExtensions: ['jpg', 'png', 'bmp', 'jpeg'],//接收的文件后缀
                        showUpload: false, //是否显示上传按钮
                        showPreview: true, //展前预览
                        maxFileSize: 500, //上传文件最大的尺寸
                        maxFilesNum: 1,   //
                        dropZoneEnabled: true,//是否显示拖拽区域
                        uploadAsync: false,
                        showClose: false,
                        showRemove: false,
                        autoReplace: true,
                        dropZoneTitle: "拖拽图片到这里 &hellip;",
                        msgFilesTooMany: "选择上传的文件数量({n}) 超过允许的最大数值{m}！"
                    });
                };

                //多选控件
                $('.check-context').each(function () {

                    let $self = $(this);
                    let url = $self.data('url');
                    let name = $self.data('name');
                    let label = $self.data('label');
                    let selectedId = $self.data('selected');
                    //请求数据
                    $.operate.postJson(url, null, true, function (result) {
                        if (result.code) {
                            $.modal.msgError('获取数据失败!');
                        } else {
                            $self.empty();
                            let child = [];
                            $(result.rows).each(function (i) {
                                let flag = '';
                                if (selectedId !== undefined) {
                                    let _sid = ("" + selectedId).split(',');
                                    for (let v of _sid) {
                                        if (v == this['id']) {
                                            flag = 'checked';
                                        }
                                    }
                                }
                                let id = $.common.randomStr(5);
                                child.push('<label class="check-box" for="check' + id + '"><input id="check' + id + '" type="checkbox" name="' + name + '" value="' + this['id'] + '" ' + flag + '/> ' + this[label] + ' </label>');
                            });
                            $self.append(child.join(''));
                            if ($(".check-box").length > 0) {
                                $(".check-box").iCheck({
                                    checkboxClass: 'icheckbox-blue',
                                    radioClass: 'iradio-blue',
                                })
                            }
                        }
                    });
                });

                // checkbox 事件绑定
                if ($(".check-box").length > 0) {
                    $(".check-box").iCheck({
                        checkboxClass: 'icheckbox-blue',
                        radioClass: 'iradio-blue',
                    })
                }
                // radio 事件绑定
                if ($(".radio-box").length > 0) {
                    $(".radio-box").iCheck({
                        checkboxClass: 'icheckbox-blue',
                        radioClass: 'iradio-blue',
                    })
                }
                // laydate 时间控件绑定
                layui.use('laydate', function () {
                    let laydate = layui.laydate;
                    let startDate = laydate.render({
                        elem: '#startTime',
                        max: $('#endTime').val(),
                        theme: 'molv',
                        trigger: 'click',
                        done: function (value, date) {
                            // 结束时间大于开始时间
                            if (value !== '') {
                                endDate.config.min.year = date.year;
                                endDate.config.min.month = date.month - 1;
                                endDate.config.min.date = date.date;
                            } else {
                                endDate.config.min.year = '';
                                endDate.config.min.month = '';
                                endDate.config.min.date = '';
                            }
                        }
                    });
                    let endDate = laydate.render({
                        elem: '#endTime',
                        min: $('#startTime').val(),
                        theme: 'molv',
                        trigger: 'click',
                        done: function (value, date) {
                            // 开始时间小于结束时间
                            if (value !== '') {
                                startDate.config.max.year = date.year;
                                startDate.config.max.month = date.month - 1;
                                startDate.config.max.date = date.date;
                            } else {
                                startDate.config.max.year = '';
                                startDate.config.max.month = '';
                                startDate.config.max.date = '';
                            }
                        }
                    });
                    let onceDate = laydate.render({
                        elem: '#onceTime',
                        theme: 'molv',
                        value: $('#onceTime').val()
                    });
                });
                // tree 关键字搜索绑定
                $(".main-content-inner").on("focus", "#keyword", function focusKey(e) {
                    if ($("#keyword").hasClass("empty")) {
                        $("#keyword").removeClass("empty");
                    }
                });
                $(".main-content-inner").on("blur", "#keyword", function blurKey(e) {
                    if ($("#keyword").val() === "") {
                        $("#keyword").addClass("empty");
                    }
                    $.tree.searchNode(e);
                });
                $(".main-content-inner").on("input propertychange", "#keyword", $.tree.searchNode);
                // 复选框后按钮样式状态变更
                $(".main-content-inner").on("check.bs.table uncheck.bs.table check-all.bs.table uncheck-all.bs.table", "#bootstrap-table", function () {
                    let ids = $("#bootstrap-table").bootstrapTable("getSelections");
                    $('#toolbar .btn-del').toggleClass('disabled', !ids.length);
                    $('#toolbar .btn-edit').toggleClass('disabled', ids.length!=1);
                });

                $(".main-nav-list li a").on("click", function () {
                    $(".main-nav-list").find('li.active').removeClass('active');
                    $(this).parent("li").addClass("active");
                })
            }
        }

    });
})(jQuery);

/** 消息状态码 */
web_status = {
    SUCCESS: 0,
    FAIL: -1,
    NOT_LOGIN: 401
};

/** 弹窗状态码 */
modal_status = {
    SUCCESS: "success",
    FAIL: "error",
    WARNING: "warning"
};

/** 提交方法 */
SUBMIT_METHOD = {
    POST: "post",
    GET: "get"
};

/** 提交方法 */
SUBMIT_METHOD = {
    POST: "post",
    GET: "get"
};

/** 数据类型 */
DATA_TYPE = {
    JSON: "json",
    TEXT: "text"
};
/**
 * 页面跳转
 * @param url   请求地址xxx\xxx?xxx=xx&xx=xx
 * @param params 可选参数 json对象数据{‘a’:1}
 */
function httpPostLocationUrl (url,params) {
    var form = $("<form method='post' style='display:none'></form>");
    if(!params)params ={};
    if(url.indexOf('?')!=-1){
        var paramsStr = url.split("?")[1].split('&');
        for (var i = 0 ;i<paramsStr.length;i++){
            if(paramsStr[i]&&paramsStr[i].indexOf("=")!=-1){
                var data =  paramsStr[i].split('=');
                params[data[0]] = data[1];
            }
        }
        url = url.split("?")[0];
    }
    form.attr({"action": url});
    if(!$.isEmptyObject(params)){
        for (arg in params ) {
            var input = $("<input type='hidden'>");
            input.attr({"name": arg});
            input.val(params[arg]);
            form.append(input);
        }
    }
    $("html").append(form); //兼容火狐
    form.submit();
}
function windowOpen(url){
    $.ajax({
        type: "get",
        url: url,
        success: function (res) {
            document.open();
            document.write(res)
            document.close();
        }
    });
}
function newWindowOpen(url){
    $.ajax({
        type: "get",
        url: url,
        success: function (res) {
            let newWindow = window.open('about:blank');
            newWindow.document.write(res);
            newWindow.focus();
            //如果要关闭这个页面
            if (newWindow) {
                newWindow.close();
                newWindow = null;
            }
        }
    });
}
/** 设置全局ajax处理 */
$.ajaxSetup({
    beforeSend: function (xhr) {
        let token = localStorage.getItem("token");
        if(token && token !== '') {
            xhr.setRequestHeader("Authorization", token);
        }
    },
    complete: function(XMLHttpRequest, textStatus) {
        if (textStatus === 'timeout') {
            $.modal.alertWarning("服务器超时，请稍后再试！");
            $.modal.closeLoading();
        } else if (textStatus === "parsererror") {
            $.modal.alertWarning("服务器错误，请联系管理员！");
            $.modal.closeLoading();
        }
    }
});



