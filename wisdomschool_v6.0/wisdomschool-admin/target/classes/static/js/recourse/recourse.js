/**
 * 资源上传实现。
 */

// 所用val名称列表，注意不要冲突，其中一些参数能够设定界面行为。
let locationpath = "root";// 记录当前文件路径
let parentpath;// 记录当前文件路径的父级目录
let ap;// 音乐播放器对象
let zipTimer;// 打包下载计时器
let folderView;// 返回的文件系统视图对象
let originFolderView;// 保存原始的文件视图对象
let fs;// 选中的要上传的文件列表
let checkedMovefiles;// 移动文件的存储列表
let constraintLevel;// 当前文件夹限制等级
let account;// 用户账户
let isUpLoading = false;// 是否正在执行其他上传操作
let xhr;// 文件上传请求对象
let viewerPageSize = 15; // 显示图片页的最大长度，注意最好是奇数
let viewer; // viewer对象，用于预览图片功能
let viewerPageIndex; // 分页预览图片——已浏览图片页号
let viewerTotal; // 分页预览图片——总页码数
let pvl;// 预览图片列表的JSON格式对象
let checkFilesTip = "提示：您还未选择任何文件，请先选中一些文件后再执行本操作：<br /><br /><kbd>单击</kbd>：选中某一文件<br /><br /><kbd><kbd>Shift</kbd>+<kbd>单击</kbd></kbd>：选中多个文件<br /><br /><kbd><kbd>Shift</kbd>+<kbd>双击</kbd></kbd>：选中连续的文件<br /><br /><kbd><kbd>Shitf</kbd>+<kbd>A</kbd></kbd>：选中/取消选中所有文件";// 选取文件提示

let storage = "http://132.232.13.116:8081/storage/downloadFile?fileId=";

// 界面功能方法定义
// 页面初始化
(function ($) {
    window.onresize = function () {
        changeFilesTableStyle();
    };
    getServerOS();// 得到服务器操作系统信息
    showFolderView("root");// 显示根节点页面视图
    // 点击空白处取消选中文件（已尝试兼容火狐，请期待用户反馈，如不好使再改）
    $(document).click(function (e) {
        let filetable = $("#filetable")[0];
        let srcElement = e.srcElement;
        if (srcElement === undefined) {
            return;
        }
        if (!srcElement) {
            srcElement = e.target;
        }
        if (srcElement !== filetable && !$.contains(filetable, e.target)) {
            $(".filerow").removeClass("info");
        }
    });
    // 关闭音乐播放模态框自动停止播放并将进度归0
    $('#audioPlayerModal').on('hidden.bs.modal', function (e) {
        if (ap != null) {
            ap.seek(0);
            ap.pause();
        }
    });
    // 关闭打包下载模态框自动停止计时
    $('#downloadAllCheckedModal').on('hidden.bs.modal', function (e) {
        if (zipTimer != null) {
            window.clearInterval(zipTimer);
        }
    });
    // 关闭登陆模态框自动清空输入数据
    $('#loginModal').on('hidden.bs.modal', function (e) {
        $("#accountid").val('');
        $("#accountpwd").val('');
    });
    // 各个模态框的打开判定及回车响应功能。该功能仅对“首选”的按钮有效，对其他按钮无效，以避免用户误操作。
    $('.modal').on('shown.bs.modal', function (e) {
        $(this).addClass("shown");
    });
    $('.modal').on('hidden.bs.modal', function (e) {
        $(this).removeClass("shown");
    });
    $("body").keypress(function (e) {
        let keyCode = e.keyCode ? e.keyCode : e.which ? e.which : e.charCode;
        if (keyCode == 13) {
            if ("searchKeyWordIn" === document.activeElement.id) {
                doSearchFile();
            } else {
                let g = $(".shown .btn-primary");
                if (g.get(0) != null) {
                    g.click();
                }
            }
            return false;
        }
    });
    // 开启登陆模态框自动聚焦账户输入框
    $('#loginModal').on('shown.bs.modal', function (e) {
        $("#accountid").focus();
    });
    // 开启新建文件夹框自动初始化状态
    $('#newFolderModal').on('show.bs.modal', function (e) {
        $("#folderalert").removeClass("alert");
        $("#folderalert").removeClass("alert-danger");
        $("#foldernamebox").removeClass("has-error");
        $("#folderalert").text("");
        $("#foldername").val("");
        $("#foldertypelist").html("");
        /*if (account != null) {
            $("#foldername").attr("folderConstraintLevel", constraintLevel + "");
            $("#newfoldertype").text(folderTypes[constraintLevel]);
            for (let i = constraintLevel; i < folderTypes.length; i++) {
                $("#foldertypelist").append("<li><a onclick='changeNewFolderType(" + i + ")'>" + folderTypes[i] + "</a></li>");
            }
        } else {
            $("#foldertypelist").append("<li><a onclick='changeNewFolderType(0)'>" + folderTypes[0] + "</a></li>");
        }*/
    });
    // 开启新建文件夹模态框自动聚焦文件名输入框
    $('#newFolderModal').on('shown.bs.modal', function (e) {
        $("#foldername").focus();
    });
    // 关闭上传模态框时自动提示如何查看上传进度
    $('#uploadFileModal').on('hidden.bs.modal', function (e) {
        if (isUpLoading) {
            $('#operationMenuBox').attr("data-placement", "top");
            $('#operationMenuBox').attr("data-trigger", "focus");
            $('#operationMenuBox').attr("data-title", "上传中");
            $('#operationMenuBox').attr("data-content", "您可以重新打开上传窗口查看上传进度。");
            $('#operationMenuBox').popover();
            $('#operationMenuBox').popover('show');
            // 2秒后消失提示框
            let closeUploadTips = setTimeout(
                function () {
                    $('#operationMenuBox').attr("data-title", "");
                    $('#operationMenuBox').attr("data-content", "");
                    $('#operationMenuBox').popover('destroy');
                }, 2000
            );
        }
    });
    // 开启编辑文件夹框自动初始化状态
    $('#renameFolderModal').on('show.bs.modal', function (e) {
        $("#newfolderalert").removeClass("alert");
        $("#newfolderalert").removeClass("alert-danger");
        $("#folderrenamebox").removeClass("has-error");
        $("#newfolderalert").text("");
        $("#editfoldertypelist").html("");
        if (account != null) {
            for (let i = constraintLevel; i < folderTypes.length; i++) {
                $("#editfoldertypelist").append("<li><a onclick='changeEditFolderType(" + i + ")'>" + folderTypes[i] + "</a></li>");
            }
        } else {
            $("#editfoldertypelist").append("<li><a onclick='changeEditFolderType(0)'>" + folderTypes[0] + "</a></li>");
        }
    });
    // 响应拖动上传文件
    document.ondragover = function (e) {
        if (e.preventDefault) {
            e.preventDefault();
            e.stopPropagation();
        } else {
            window.event.cancelBubble = true;
            window.event.returnValue = false;
        }
    }
    document.ondrop = function (e) {
        if (e.preventDefault) {
            e.preventDefault();
            e.stopPropagation();
        } else {
            window.event.cancelBubble = true;
            window.event.returnValue = false;
        }
        if (folderView.authList != null) {
            if (checkAuth(folderView.authList, "U")) {// 如果有上传权限且未进行其他上传
                if (isUpLoading) {
                    alert("提示：您正在执行另一项上传任务，请在上传窗口关闭后再试。");
                } else {
                    if (!(window.ActiveXObject || "ActiveXObject" in window)) {// 判断是否为IE
                        let dt;
                        if (e.dataTransfer != null) {
                            dt = e.dataTransfer; // 获取到拖入上传的文件对象
                        } else {
                            dt = window.event.dataTransfer;
                        }
                        let testFile = true;
                        if (dt.items !== undefined) {
                            for (let i = 0; i < dt.items.length; i++) {
                                let item = dt.items[i];
                                if (item.kind === "file" && item.webkitGetAsEntry().isFile) {

                                } else {
                                    testFile = false;
                                }
                            }
                        } else {
                            for (let i = 0; i < dt.files.length; i++) {
                                let dropFile = df.files[i];
                                if (dropFile.type) {

                                } else {
                                    try {
                                        let fileReader = new FileReader();
                                        fileReader.readAsDataURL(dropFile.slice(0, 10));
                                        fileReader.addEventListener('load', function (e) {

                                        }, false);
                                        fileReader.addEventListener('error', function (e) {
                                            testFile = false;
                                        }, false);
                                    } catch (e) {
                                        testFile = false;
                                    }
                                }
                            }
                        }
                        if (testFile) {
                            fs = e.dataTransfer.files; // 获取到拖入上传的文件对象
                            showUploadFileModel();
                            showfilepath();
                            checkUploadFile();
                        } else {
                            alert("提示：您拖入的文件中包含了一个或多个文件夹，无法进行上传。");
                        }
                    } else {
                        alert("提示：IE浏览器不支持拖拽上传。您可以使用现代浏览器或将浏览模式切换为“极速模式”来体验该功能。");
                    }
                }
            } else {
                alert("提示：您不具备上传权限，无法上传文件。");
            }
        } else {
            alert("提示：您不具备上传权限，无法上传文件。");
        }
    }
    // Shift+A全选文件/反选文件，Shift+N新建文件夹，Shift+U上传文件，Shift+C&V剪切粘贴，Shift+D批量删除
    $(document).keypress(function (e) {
        if ($('.modal.shown').length == 0 || ($('.modal.shown').length == 1 && $('.modal.shown').attr('id') == 'loadingModal')) {
            let keyCode = e.keyCode ? e.keyCode : e.which ? e.which : e.charCode;
            if (isShift(e)) {
                switch (keyCode) {
                    case 65:
                        checkallfile();
                        break;
                    case 78:
                        $('#createFolderButtonLi a').click();
                        break;
                    case 85:
                        $('#uploadFileButtonLi a').click();
                        break;
                    case 68:
                        $('#deleteSeelectFileButtonLi a').click();
                        break;
                    case 67:
                        if ((!$("#cutSignTx").hasClass("cuted")) && checkedMovefiles == undefined) {
                            $('#cutFileButtonLi a').click();
                        }
                        break;
                    case 86:
                        if ($("#cutSignTx").hasClass("cuted") && checkedMovefiles !== undefined) {
                            $('#cutFileButtonLi a').click();
                        }
                        break;

                    default:
                        break;
                }
                return false;
            }
        }
    });
    // 关闭移动提示框自动取消移动
    $('#moveFilesModal').on('hidden.bs.modal', function (e) {
        checkedMovefiles = undefined;
        $("#cutSignTx").html("剪切 <span class='pull-right'><span class='glyphicon glyphicon-arrow-up' aria-hidden='true'></span>+C</span>");
        $("#cutSignTx").removeClass("cuted");
        $('#moveFilesBox').html("");
    });
    // IE内核浏览器内的startsWith方法的自实现
    if (typeof String.prototype.startsWith != 'function') {
        String.prototype.startsWith = function (prefix) {
            return this.slice(0, prefix.length) === prefix;
        };
    }
    if (typeof String.prototype.endsWith != 'function') {
        String.prototype.endsWith = function (suffix) {
            return this.indexOf(suffix, this.length - suffix.length) !== -1;
        };
    }
    // 开启详细信息模态框自动显示信息内容
    $('#folderInfoModal').on('show.bs.modal', function (e) {
        let f = folderView.folder;
        $("#fim_name").text(f.folderName);
        $("#fim_creator").text(f.folderCreator);
        $("#fim_folderCreationDate").text(f.folderCreationDate);
        $("#fim_statistics").text("共包含 " + folderView.folderList.length + " 个文件夹， " + folderView.fileList.length + " 个文件。");
    });
    // 关闭下载提示模态框自动隐藏下载链接
    $('#downloadModal').on('hidden.bs.modal', function (e) {
        $('#downloadURLCollapse').collapse('hide');
    });
})(jQuery);

// 根据屏幕大小增删表格显示内容
function changeFilesTableStyle() {
    let win = $(window).width();
    if (win < 800) {
        $('#filetableheadera').addClass('filetableheaderstyle');
        $('.hiddenColumn').hide();
        $('.rightbtn').hide();
        $('#vicetbbox').show();
        $('#filetableoptmenusreach').hide();
    } else {
        $('#filetableheadera').removeClass('filetableheaderstyle');
        $('.hiddenColumn').show();
        $('.rightbtn').show();
        $('#vicetbbox').hide();
        $('#filetableoptmenusreach').show();
    }
    if (win < 768) {
        $('#filetableheadera').attr('data-toggle', 'collapse');
        $('#filetableheadera').attr('data-target', '#filetableoptmenu');
        $('#mdropdownicon').html('（点击展开/折叠菜单）');
    } else {
        $('#filetableheadera').attr('data-toggle', 'modal');
        $('#filetableheadera').attr('data-target', '#folderInfoModal');
        $('#mdropdownicon').html('');
    }
}

// 全局请求失败提示
function doAlert() {
    $.modal.alertError("错误：无法连接到存储服务器，请检查您的网络连接或联系管理员。");
}

// 获取服务器操作系统
function getServerOS() {
    $.operate.getJson("recourse/getServerOS", {}, true, function (data) {
        $("#serverOS").text(data.msg)
    });
}

// 获取实时文件夹视图
function showFolderView(fid) {
    $.operate.getJson('/recourse/folderView/get/' + fid, null, true, function (result) {
        folderView = result.data;
        locationpath = folderView.folder.folderId;
        parentpath = folderView.folder.folderParent;
        constraintLevel = folderView.folder.folderConstraint;
        screenedFoldrView = null;
        $("#searchKeyWordIn").val("");
        showParentList(folderView);
        showAccountView(folderView);
        showPublishTime(folderView);
        originFolderView = $.extend(true, {}, folderView);
        $("#sortByFN").removeClass();
        $("#sortByCD").removeClass();
        $("#sortByFS").removeClass();
        $("#sortByCN").removeClass();
        showFolderTable(folderView);
    });
}


// 获取实时文件夹视图
/*function showFolderView(fid) {
    startLoading();
    $.ajax({
        type: 'POST',
        dataType: 'text',
        data: {
            fid: fid
        },
        url: 'recourse/getFolderView',
        success: function (result) {
            endLoading();
                folderView = eval("(" + result + ")");
        },
        error: function () {
            endLoading();
            doAlert();
            $("#tb").html("<span class='graytext'>获取失败，请尝试刷新</span>");
            $("#publishTime").html("<span class='graytext'>获取失败，请尝试刷新</span>");
            $("#parentlistbox")
                .html("<span class='graytext'>获取失败，请尝试刷新</span>");
        }
    });
}*/

// 开始文件视图加载动画
function startLoading() {
    $('#loadingModal').modal({backdrop: 'static', keyboard: false});
    $('#loadingModal').modal('show');
    $('#loadingModal').addClass("shown");
}

// 结束文件视图加载动画
function endLoading() {
    $('#loadingModal').modal('hide');
    $('#loadingModal').removeClass("shown");
}


// 显示当前文件夹的父级路径
function showParentList(folderView) {
    $("#parentFolderList").html("");
    let f = folderView.folder;
    if (folderView.parentList.length > 0) {
        $.each(folderView.parentList, function (n, val) {
            $("#parentFolderList").append("<li><a href='javascript:void(0);' onclick='entryFolder(" + '"' + val.folderId + '"' + ")'>" + val.folderName + "</a></li>");
        });
    } else {
        $("#parentFolderList").html("<li class='disabled'><a>无</a></li>");
    }
    if (f.folderName.length > 6) {
        $("#currentFolderName").text(f.folderName.substr(0, 6) + "...");
    } else {
        $("#currentFolderName").text(f.folderName);
    }
    if (f.folderName == "ROOT") {
        $("#folderIconSpan").removeClass("glyphicon-folder-close");
        $("#folderIconSpan").addClass("glyphicon-home");
    } else {
        $("#folderIconSpan").removeClass("glyphicon-home");
        $("#folderIconSpan").addClass("glyphicon-folder-close");
    }
}

// 显示用户视图，包括文件列表、登录信息、操作权限接口等
function showAccountView(folderView) {
    $("#tb,#tb2").html("");
    account = folderView.account;
    let authList = folderView.authList;
    // 对操作菜单进行初始化，根据权限显示可操作的按钮（并非约束）。
    $("#fileListDropDown li").addClass("disabled");
    $("#fileListDropDown li a").attr("onclick", "");
    $("#fileListDropDown li a").attr("href", "javascript:void(0);");
    if (authList != null) {
        if (checkAuth(authList, "C")) {
            $("#createFolderButtonLi").removeClass("disabled");
            $("#createFolderButtonLi a").attr("onclick", "showNewFolderModel()");
        }
        if (checkAuth(authList, "U")) {
            $("#uploadFileButtonLi").removeClass("disabled");
            $("#uploadFileButtonLi a").attr("onclick", "showUploadFileModel()");
        }
        if (checkAuth(authList, "L")) {
            $("#packageDownloadBox")
                .html(
                    "<button class='btn btn-link navbar-btn' onclick='showDownloadAllCheckedModel()'><span class='glyphicon glyphicon-briefcase'></span> 打包下载</button>");
        } else {
            $("#packageDownloadBox").html("");
        }
        if (checkAuth(authList, "D")) {
            $("#deleteSeelectFileButtonLi").removeClass("disabled");
            $("#deleteSeelectFileButtonLi a").attr("onclick", "showDeleteAllCheckedModel()");
        }
        if (checkAuth(authList, "M")) {
            $("#cutFileButtonLi").removeClass("disabled");
            $("#cutFileButtonLi a").attr("onclick", "startMoveFile()");
            if (checkedMovefiles !== undefined && checkedMovefiles.length > 0) {
                $("#cutSignTx").text("粘贴（" + checkedMovefiles.length + "）");
                $("#cutSignTx").addClass("cuted");
            }
        }
    }
}

// 检查权限列表
function checkAuth(authList, auth) {
    let k = false;
    $.each(authList, function (n, a) {
        if (a == auth) {
            k = true;
        }
    });
    return k;
}

// 显示视图更新时间
function showPublishTime(folderView) {
    $("#publishTime").html("");
    let pt = "";
    if (folderView.publishTime != null) {
        pt = folderView.publishTime;
    } else {
        pt = "--";
    }
    $("#publishTime").text(pt);
}

// 刷新文件夹视图
function refreshFolderView() {
    if (locationpath != null && locationpath.length > 0) {
        showFolderView(locationpath);
    } else {
        showFolderView('root');
    }
}

// 返回上一级文件夹
function returnPF() {
    if (parentpath !== undefined && parentpath !== "") {
        showFolderView(parentpath);
    } else {
        showFolderView('root');
    }
}

// 显示文件夹内容
function showFolderTable(folderView) {
    var id=folderView.account;

    $("#foldertable").html("");
    if (parentpath !== undefined && parentpath !== "") {
        $("#foldertable")
            .append(
                "<tr onclick='returnPF()'><td><button onclick='returnPF()' class='btn btn-link btn-xs'>返回上一级</button></td><td class='hiddenColumn'>--</td><td>--</td><td class='hiddenColumn'>--</td><td>--</td></tr>");
    }
    let authList = folderView.authList;
    let aD = false;
    let aR = false;
    let aL = false;
    if (checkAuth(authList, "D")) {
        aD = true;
    }
    if (checkAuth(authList, "R")) {
        aR = true;
    }
    if (checkAuth(authList, "L")) {
        aL = true;
    }
    $
        .each(
            folderView.folderList,
            function (n, f) {
                let folderRow = "<tr id='" + f.folderId + "' onclick='checkfile(event," + '"' + f.folderId + '"' + ")' ondblclick='checkConsFile(event," + '"' + f.folderId + '"' + ")' class='filerow' iskfolder='true' ><td><button onclick='entryFolder("
                    + '"' + f.folderId + '"'
                    + ")' class='btn btn-link btn-xs'>/"
                    + f.folderName + "</button></td><td class='hiddenColumn'>"
                    + f.folderCreationDate + "</td><td>--</td><td class='hiddenColumn'>"
                    + f.folderCreator + "</td><td>";
                if (aD&&f.folderId!='gongxiang') {
                    folderRow = folderRow
                        + "<button onclick='showDeleteFolderModel("
                        + '"'
                        + f.folderId
                        + '","'
                        + f.folderName
                        + '"'
                        + ")' class='btn btn-link btn-xs'><span class='glyphicon glyphicon-remove'></span> 删除</button>";
                }
                if (aR&&f.folderId!='gongxiang') {
                    folderRow = folderRow
                        + "<button onclick='showRenameFolderModel("
                        + '"'
                        + f.folderId
                        + '","'
                        + f.folderName
                        + '",'
                        + f.folderConstraint
                        + ")' class='btn btn-link btn-xs'><span class='glyphicon glyphicon-wrench'></span> 编辑</button>";
                }
                if (!aR && !aD) {
                    folderRow = folderRow + "--";
                }
                folderRow = folderRow + "</td></tr>";
                $("#foldertable").append(folderRow);
            });
    $
        .each(
            folderView.fileList,
            function (n, fi) {
                let fileRow = "<tr onclick='checkfile(event," + '"'
                    + fi.fileId + '"' + ")' ondblclick='checkConsFile(event," + '"' + fi.fileId + '"' + ")' id='" + fi.fileId
                    + "' class='filerow'><td>" + fi.fileName
                    + "<button onclick='selectFile(\"" + fi.fileId + "\",\"" + fi.fileName + "\")' "
                    + "class='btn btn-link btn-xs'><span class='glyphicon glyphicon-ok'></span> 选择</button></td><td class='hiddenColumn'>" + fi.fileCreationDate + "</td>";
                if (fi.fileSize == "0") {
                    fileRow = fileRow + "<td>&lt;1MB</td>";
                } else {
                    fileRow = fileRow + "<td>" + fi.fileSize + "MB</td>";
                }
                fileRow = fileRow + "<td class='hiddenColumn'>" + fi.fileCreator + "</td><td>";
                if (aL) {
                    fileRow = fileRow
                        + "<button onclick='showDownloadModel("
                        + '"'
                        + fi.fileId
                        + '","'
                        + fi.fileName
                        + '"'
                        + ")' class='btn btn-link btn-xs'><span class='glyphicon glyphicon-cloud-download'></span> 下载</button>";
                    // 对于各种特殊格式文件提供的预览和播放功能
                    if (getSuffix(fi.fileName) == "mp4" || getSuffix(fi.fileName) == "flv"
                        || getSuffix(fi.fileName) == "webm" || getSuffix(fi.fileName) == "mov") {
                        fileRow = fileRow
                            + "<button onclick='playVideo("
                            + '"'
                            + fi.fileId
                            + '"'
                            + ")' class='btn btn-link btn-xs'><span class='glyphicon glyphicon-play'></span> 播放</button>";
                    } else if (getSuffix(fi.fileName) == "pdf") {
                        fileRow = fileRow
                            + "<button onclick='pdfView("
                            + '"'
                            + fi.filePath
                            + '"'
                            + ")' class='btn btn-link btn-xs'><span class='glyphicon glyphicon-eye-open'></span> 预览</button>";
                    } else if (getSuffix(fi.fileName) == "jpg"
                        || getSuffix(fi.fileName) == "jpeg"
                        || getSuffix(fi.fileName) == "gif"
                        || getSuffix(fi.fileName) == "png"
                        || getSuffix(fi.fileName) == "bmp") {
                        fileRow = fileRow
                            + "<button onclick='showPicture("
                            + '"'
                            + fi.fileId
                            + '"'
                            + ")' class='btn btn-link btn-xs'><span class='glyphicon glyphicon-picture'></span> 查看</button>";
                    } else if (getSuffix(fi.fileName) == "mp3"
                        || getSuffix(fi.fileName) == "wav"
                        || getSuffix(fi.fileName) == "ogg") {
                        fileRow = fileRow
                            + "<button onclick='playAudio("
                            + '"'
                            + fi.fileId
                            + '"'
                            + ")' class='btn btn-link btn-xs'><span class='glyphicon glyphicon-play'></span> 播放</button>";
                    }
                }
                if (aD) {
                    fileRow = fileRow
                        + "<button onclick='showDeleteFileModel("
                        + '"'
                        + fi.fileId
                        + '","'
                        + fi.fileName
                        + '"'
                        + ")' class='btn btn-link btn-xs'><span class='glyphicon glyphicon-remove'></span> 删除</button>";
                }
                if (aR) {
                    fileRow = fileRow
                        + "<button onclick='showRenameFileModel("
                        + '"'
                        + fi.fileId
                        + '"'
                        + ","
                        + '"'
                        + fi.fileName
                        + '"'
                        + ")' class='btn btn-link btn-xs'><span class='glyphicon glyphicon-wrench'></span> 重命名</button>";
                }
                if (!aR && !aD && !aL) {
                    fileRow = fileRow + "--";
                }
                fileRow = fileRow + "</td></tr>";
                $("#foldertable").append(fileRow);
            });
    changeFilesTableStyle();
}

let folderTypes = ['公开的', '仅小组', '仅创建者'];// 文件夹约束条件（由小至大）

// 显示新建文件夹模态框
function showNewFolderModel() {
    $('#newFolderModal').modal('show');
}

// 修改新建文件夹约束等级
function changeNewFolderType(type) {
    $("#newfoldertype").text(folderTypes[type]);
    $("#foldername").attr("folderConstraintLevel", type + "");
}

// 创建新的文件夹
function createfolder() {
    let fn = $("#foldername").val();
    let fc = $("#foldername").attr("folderConstraintLevel");
    let reg = new RegExp("^[0-9a-zA-Z_\\u4E00-\\u9FFF]+$", "g");
    if (fn.length === 0) {
        showFolderAlert("提示：文件夹名称不能为空。");
    } else if (fn.length > 20) {
        showFolderAlert("提示：文件夹名称太长。");
    } else if (reg.test(fn)) {
        $("#folderalert").removeClass("alert");
        $("#folderalert").removeClass("alert-danger");
        $("#foldernamebox").removeClass("has-error");
        $("#folderalert").text("");
        $.operate.postJson(ctx + '/recourse/createFolder', {
            pid: locationpath,
            fn: fn,
            fc: fc
        }, true, function (result) {
            if (result.code === 0) {
                $('#newFolderModal').modal('hide');
                showFolderView(locationpath);
            } else {
                showFolderAlert("提示：" + result.msg);
            }
        });
    } else {
        showFolderAlert("提示：文件夹名只能包含英文字母、数组、汉字和下划线。");
    }
}

// 显示新建文件夹状态提示
function showFolderAlert(txt) {
    $("#folderalert").addClass("alert");
    $("#folderalert").addClass("alert-danger");
    $("#foldernamebox").addClass("has-error");
    $("#folderalert").text(txt);
}

// 进入某一文件夹
function entryFolder(folderId) {
    showFolderView(folderId);
}

// 显示删除文件夹模态框
function showDeleteFolderModel(folderId, folderName) {
    $('#deleteFolderBox')
        .html(
            "<button id='dmbutton' type='button' class='btn btn-danger' onclick='deleteFolder("
            + '"' + folderId + '"' + ")'>删除</button>");
    $("#dmbutton").attr('disabled', false);
    $('#deleteFolderMessage').text(
        "提示：确定要彻底删除文件夹：[" + folderName + "]及其全部内容么？该操作不可恢复");
    $('#deleteFolderModal').modal('toggle');
}

// 执行删除文件夹
function deleteFolder(folderId) {
    $("#dmbutton").attr('disabled', true);
    $.operate.getJson(ctx + '/recourse/deleteFolder/' + folderId, {}, true, function (result) {
        if (result.code === web_status.SUCCESS) {
            $('#deleteFolderModal').modal('hide');
            showFolderView(locationpath);
        } else {
            $.modal.alertError(result.msg);
        }
    });
    $("#dmbutton").attr('disabled', false);
}

// 显示重命名文件夹模态框
function showRenameFolderModel(folderId, folderName, type) {
    $("#renameFolderBox").html(
        "<button type='button' class='btn btn-primary btn-sm' onclick='renameFolder("
        + '"' + folderId + '"' + ")'>修改</button>");
    $("#newfoldername").val(folderName);
    changeEditFolderType(type);
    $("#renameFolderModal").modal('show');
}

// 修改编辑文件夹的约束等级
function changeEditFolderType(type) {
    $("#editfoldertype").text(folderTypes[type]);
    $("#newfoldername").attr("folderConstraintLevel", type + "");
}

// 执行重命名文件夹
function renameFolder(folderId) {
    let newName = $("#newfoldername").val();
    let fc = $("#newfoldername").attr("folderConstraintLevel");
    let reg = new RegExp("^[0-9a-zA-Z_\\u4E00-\\u9FFF]+$", "g");
    if (newName.length == 0) {
        showRFolderAlert("提示：文件夹名称不能为空。");
    } else if (newName.length > 20) {
        showRFolderAlert("提示：文件夹名称太长。");
    } else if (reg.test(newName)) {
        $("#newfolderalert").removeClass("alert");
        $("#newfolderalert").removeClass("alert-danger");
        $("#folderrenamebox").removeClass("has-error");
        $("#newfolderalert").text("");
        $.operate.postJson('/recourse/renameFolder', {
            fid: folderId,
            nn: newName,
            fc: fc
        }, true, function (result) {
            if (result.code === web_status.SUCCESS) {
                $('#renameFolderModal').modal('hide');
                showFolderView(locationpath);
            } else {
                showRFolderAlert("提示：" + result.msg);
            }
        });
    } else {
        showRFolderAlert("提示：文件夹名只能包含英文字母、数组、汉字和下划线。");
    }
}

// 显示重命名文件夹状态提示
function showRFolderAlert(txt) {
    $("#newfolderalert").addClass("alert");
    $("#newfolderalert").addClass("alert-danger");
    $("#folderrenamebox").addClass("has-error");
    $("#newfolderalert").text(txt);
}

// 显示上传文件模态框
function showUploadFileModel() {
    $("#uploadFileAlert").removeClass("alert");
    $("#uploadFileAlert").removeClass("alert-danger");
    $("#uploadFileAlert").text("");
    if (isUpLoading == false) {
        $("#filepath").removeAttr("disabled");
        $("#uploadfile").val("");
        $("#filepath").val("");
        $("#pros").width("0%");
        $("#pros").attr('aria-valuenow', '0');
        $("#umbutton").attr('disabled', false);
        $("#filecount").text("");
        $("#uploadstatus").text("");
        $("#selectcount").text("");
        $("#selectFileUpLoadModelAsAll").removeAttr("checked");
        $("#selectFileUpLoadModelAlert").hide();
    }
    $('#uploadFileModal').modal('show');
}

// 点击文本框触发input:file选择文件动作
function checkpath() {
    $('#uploadfile').click();
}

// 获取选中文件
function getInputUpload() {
    fs = $("#uploadfile").get(0).files;
    showfilepath();
}

// 文件选中后自动回填文件路径
function showfilepath() {
    let filename = "";
    for (let i = 0; i < fs.length; i++) {
        filename = filename + fs[i].name;
        if (i < (fs.length - 1)) {
            filename = filename + "、";
        }
    }
    if (fs.length <= 1) {
        $("#selectcount").text("");
    } else {
        $("#selectcount").text("（共" + fs.length + "个）");
    }
    $("#filepath").val(filename);
}
//获取小数点后面的字符(获取后缀)
function getCaption(obj){
    var index=obj.lastIndexOf("\.");
    obj=obj.substring(index+1,obj.length);
// console.log(obj);
    return obj;
}
// 检查是否能够上传
function checkUploadFile() {
    if (isUpLoading == false) {
        if (fs != null && fs.length > 0) {
            var geshi=0;
            var daxiao=0;
            for (let i=0; i < fs.length; i++) {
                var name=fs[i].name;
                if (getCaption(name)=='mp3'||getCaption(name)=='mp4'||getCaption(name)=='mov'||getCaption(name)=='flv'||
                    getCaption(name)=='webm'||getCaption(name)=='ogg'||getCaption(name)=='ppt'||
                    getCaption(name)=='pptx'||getCaption(name)=='xlsx'||getCaption(name)=='xls'||
                    getCaption(name)=='doc'||getCaption(name)=='docx'||getCaption(name)=='zip'||
                    getCaption(name)=='rar'||getCaption(name)=='pdf'||getCaption(name)=='jpg')
                {
                    geshi++;
                }
            }
            for (let i=0; i < fs.length; i++) {
                var dansize=fs[i].size;
                if (dansize<=209715200)
                {
                    daxiao++;
                }
            }
            if (geshi==fs.length){
                if (daxiao==fs.length) {
                    $("#filepath").attr("disabled", "disabled");
                    $("#umbutton").attr('disabled', true);
                    isUpLoading = true;
                    repeModelList = null;
                    $("#uploadFileAlert").removeClass("alert");
                    $("#uploadFileAlert").removeClass("alert-danger");
                    $("#uploadFileAlert").text("");
                    let filenames = new Array();
                    for (let i = 0; i < fs.length; i++) {
                        filenames[i] = fs[i].name.replace(/^.+?\\([^\\]+?)?$/gi, "$1");
                    }
                    let namelist = JSON.stringify(filenames);

                    $.operate.postJson(ctx + "/recourse/checkUploadFile", {
                        fid: locationpath,
                        n: namelist
                    }, true, function (result) {
                        if (result.code === web_status.SUCCESS) {
                            doupload(1);
                        } else {
                            showUploadFileAlert("提示：" + result.msg);
                        }
                    })
                }else {
                    showUploadFileAlert("提示:仅支持大小:200M以下");
                }

            } else {
                showUploadFileAlert("提示:仅支持格式:mp3,mp4,mov,webm,ogg,ppt,pptx,xlsx,xls,doc,docx,zip,rar,pdf,jpg");
            }

        } else {
            showUploadFileAlert("提示：您未选择任何文件，无法开始上传");
        }
    }
}

let repeList;// 这个是重复文件名的列表，型如['xxx','ooo',...]
let repeIndex;// 当前设定上传模式的文件序号
let repeModelList;// 这个是对每一个重复文件选取的上传模式，型如{'xxx':'skip','ooo':'both',...}

// 针对同名文件，选择上传的模式：跳过（skip）、覆盖（cover）和保留两者（both）
function selectFileUpLoadModelStart() {
    $("#selectFileUpLoadModelAlert").show();
    $("#repeFileName").text(repeList[repeIndex]);
}

function selectFileUpLoadModelEnd(t) {
    if (repeModelList == null) {
        repeModelList = {};
    }
    repeModelList[$("#repeFileName").text()] = t;
    $("#selectFileUpLoadModelAlert").hide();
    if ($('#selectFileUpLoadModelAsAll').prop('checked')) {
        for (let i = repeIndex; i < repeList.length; i++) {
            repeModelList[repeList[i]] = t;
        }
        doupload(1);
    } else {
        repeIndex++;
        if (repeIndex < repeList.length) {
            selectFileUpLoadModelStart();
        } else {
            doupload(1);
        }
    }
}

// 执行文件上传并实现上传进度显示
function doupload(count) {
    let fcount = fs.length;
    $("#pros").width("0%");// 先将进度条置0
    $("#pros").attr('aria-valuenow', "0");
    let uploadfile = fs[count - 1];// 获取要上传的文件
    if (uploadfile != null) {
        let fname = uploadfile.name;
        if (fcount > 1) {
            $("#filecount").text("（" + count + "/" + fcount + "）");// 显示当前进度
        }
        $("#uploadstatus").prepend(
            "<p>" + fname + "<span id='uls_" + count
            + "'>[正在上传...]</span></p>");
        xhr = new XMLHttpRequest();// 这东西类似于servlet里面的request

        let fd = new FormData();// 用于封装文件数据的对象

        fd.append("f", uploadfile);// 将文件对象添加到FormData对象中，字段名为uploadfile
        fd.append("fid", locationpath);
        if (repeModelList != null && repeModelList[fname] != null) {
            if (repeModelList[fname] == 'skip') {
                $("#uls_" + count).text("[已完成]");
                if (count < fcount) {
                    doupload(count + 1);
                    return;
                } else {
                    // 清空所有提示信息，还原上传窗口
                    isUpLoading = false;
                    $("#filepath").removeAttr("disabled");
                    $("#uploadfile").val("");
                    $("#filepath").val("");
                    $("#pros").width("0%");
                    $("#pros").attr('aria-valuenow', "0");
                    $("#umbutton").attr('disabled', false);
                    $("#filecount").text("");
                    $("#uploadstatus").text("");
                    $("#selectcount").text("");
                    $('#uploadFileModal').modal('hide');
                    showFolderView(locationpath);
                    return;
                }
            }
            fd.append("repeType", repeModelList[fname]);
        }
        xhr.open("POST", ctx + "/recourse/upload", true);// 上传目标

        xhr.upload.addEventListener("progress", uploadProgress, false);// 这个是对上传进度的监听
        // 上面的三个参数分别是：事件名（指定名称）、回调函数、是否冒泡（一般是false即可）

        xhr.send(fd);// 上传FormData对象

        // 上传结束后执行的回调函数
        xhr.onloadend = function () {
            if (xhr.status === 200) {
                // TODO 上传成功
                let result = $.parseJSON(xhr.responseText);
                if (result.code === web_status.SUCCESS) {
                    $("#uls_" + count).text("[已完成]");
                    $.modal.closeLoading();
                    if (count < fcount) {
                        doupload(count + 1);
                    } else {
                        // 清空所有提示信息，还原上传窗口
                        isUpLoading = false;
                        $("#filepath").removeAttr("disabled");
                        $("#uploadfile").val("");
                        $("#filepath").val("");
                        $("#pros").width("0%");
                        $("#pros").attr('aria-valuenow', "0");
                        $("#umbutton").attr('disabled', false);
                        $("#filecount").text("");
                        $("#uploadstatus").text("");
                        $("#selectcount").text("");
                        $('#uploadFileModal').modal('hide');
                        showFolderView(locationpath);
                    }
                } else {
                    showUploadFileAlert("提示：出现意外错误，文件：[" + fname
                        + "]上传失败，上传被中断。错误信息：" + result.msg);
                    $("#uls_" + count).text("[失败]");
                    $.modal.closeLoading();
                }
            } else {
                showUploadFileAlert("提示：出现意外错误，文件：[" + fname + "]上传失败，上传被中断。");
                $("#uls_" + count).text("[失败]");
                $.modal.closeLoading();
            }
        };
    } else {
        showUploadFileAlert("提示：要上传的文件不存在。");
        $("#uploadstatus").prepend(
            "<p>未找到要上传的文件<span id='uls_" + count + "'>[失败]</span></p>");
    }
}

function uploadProgress(evt) {
    if (evt.lengthComputable) {
        // evt.loaded：文件上传的大小 evt.total：文件总的大小
        let percentComplete = Math.round((evt.loaded) * 100 / evt.total);
        // 加载进度条，同时显示信息
        $("#pros").width(percentComplete + "%");
        $("#pros").attr('aria-valuenow', "" + percentComplete);
        if (percentComplete == 100) {
            $.modal.loading("即将完成，请稍后...");
        }
    }
}

// 显示上传文件错误提示
function showUploadFileAlert(txt) {
    isUpLoading = false;
    $("#filepath").removeAttr("disabled");
    $("#uploadFileAlert").addClass("alert");
    $("#uploadFileAlert").addClass("alert-danger");
    $("#uploadFileAlert").text(txt);
    $("#umbutton").attr('disabled', false);
}

// 显示下载文件模态框
function showDownloadModel(fileId, fileName) {
    $("#downloadModal").modal('toggle');
    $("#downloadFileName").text("提示：您确认要下载文件：[" + fileName + "]么？");
    $("#downloadHrefBox").html("<a href='" + storage  + fileId + "'>" + storage + fileId + "</a>");
    $("#downloadFileBox")
        .html(
            "<button id='dlmbutton' type='button' class='btn btn-primary' onclick='dodownload("
            + '"' + fileId + '"' + ")'>开始下载</button>");
    $("#dlmbutton").attr('disabled', false);
}

// 执行下载操作
function dodownload(fileId) {
    $("#dlmbutton").attr('disabled', true);
    $("#downloadFileName").text("提示：准备开始下载，请稍候...");
    let t = setTimeout("$('#downloadModal').modal('hide');", 800);
    window.location.href = storage + fileId;
}

// 显示删除文件模态框
function showDeleteFileModel(fileId, fileName) {
    $('#deleteFileBox')
        .html(
            "<button id='dfmbutton' type='button' class='btn btn-danger' onclick='deleteFile("
            + '"' + fileId + '"' + ")'>删除</button>");
    $("#dfmbutton").attr('disabled', false);
    $('#deleteFileMessage').text("提示：确定要彻底删除文件：[" + fileName + "]么？该操作不可恢复");
    $('#deleteFileModal').modal('toggle');
}

// 执行删除文件操作
function deleteFile(fileId) {
    $("#dfmbutton").attr('disabled', true);
    $.operate.getJson(ctx + '/recourse/deleteFile/' + fileId, {}, true, function (result) {
        if (result.code === web_status.SUCCESS) {
            $('#deleteFileModal').modal('hide');
            showFolderView(locationpath);
        } else {
            $.modal.alertError(result.msg);
        }
    });
}

// 显示重命名文件模态框
function showRenameFileModel(fileId, fileName) {
    $("#newFileNamealert").removeClass("alert");
    $("#newFileNamealert").removeClass("alert-danger");
    $("#filerenamebox").removeClass("has-error");
    $("#newFileNamealert").text("");
    $("#renameFileBox").html(
        "<button type='button' class='btn btn-primary' onclick='renameFile("
        + '"' + fileId + '"' + ")'>修改</button>");
    $("#newfilename").val(fileName);
    $("#renameFileModal").modal('toggle');
}

// 修改文件名
function renameFile(fileId) {
    let reg = new RegExp("[\/\|\\s\\\\\*\\<\\>" + '"' + "]+", "g");
    let newFileName = $("#newfilename").val();
    if (newFileName.length > 0) {
        if (newFileName.length < 128) {
            if (!reg.test(newFileName) && newFileName.indexOf(".") !== 0) {
                $.operate.postJson('/recourse/renameFile', {
                    fid: fileId,
                    nn: newFileName
                }, true, function (result) {
                    if (result.code === web_status.SUCCESS) {
                        $('#renameFileModal').modal('hide');
                        showFolderView(locationpath);
                    } else {
                        showRFileAlert("提示：" + result.msg);
                    }
                });
            } else {
                showRFileAlert("提示：文件名中不应含有：空格 引号 / \ * | < > 且不能以“.”开头。");
            }
        } else {
            showRFileAlert("提示：文件名称太长。");
        }
    } else {
        showRFileAlert("提示：文件名不能为空。");
    }
}

// 显示重命名文件状态提示
function showRFileAlert(txt) {
    $("#newFileNamealert").addClass("alert");
    $("#newFileNamealert").addClass("alert-danger");
    $("#filerenamebox").addClass("has-error");
    $("#newFileNamealert").text(txt);
}

// 取消上传
function abortUpload() {
    isUpLoading = false;
    if (xhr != null) {
        xhr.abort();
        $("#umbutton").attr('disabled', false);
        $("#pros").width("0%");
        $("#pros").attr('aria-valuenow', "0");
        $("#filecount").text("");
    }
    $("#uploadfile").val("");
    $("#filepath").val("");
    $("#uploadstatus").html("");
    $("#selectcount").text("");
    $('#uploadFileModal').modal('hide');
    showFolderView(locationpath);
}

// 获取文件名的后缀名，以小写形式输出
function getSuffix(filename) {
    let index1 = filename.lastIndexOf(".");
    let index2 = filename.length;
    let suffix = filename.substring(index1 + 1, index2);
    return suffix.toLowerCase();
}

// 播放指定格式的视频
function playVideo(fileId) {
    window.open("/recourse/video/file/" + fileId);
}

// 预览PDF文档
function pdfView(filePath) {
    window.open("/pdfview?file=/fileblocks/" + filePath);
}

// 查看图片
function showPicture(fileId) {
    $.ajax({
        url: "homeController/getPrePicture.ajax",
        data: {
            fileId: fileId
        },
        type: "POST",
        dataType: "text",
        success: function (result) {
            if (result != "ERROR") {
                pvl = eval("(" + result + ")");
                // TODO 整合viewer.js插件
                if (pvl.pictureViewList.length <= viewerPageSize) {
                    createViewList();// 以全列方式显示图片列表
                } else {
                    // 以分页方式显示图片列表
                    viewerPageIndex = Math.ceil((pvl.index + 1) / viewerPageSize);
                    viewerTotal = Math.ceil(pvl.pictureViewList.length / viewerPageSize);
                    createViewListByPage();
                    let innerIndex = pvl.index - ((viewerPageIndex - 1) * viewerPageSize);
                    if (viewerPageIndex > 1) {
                        innerIndex++;
                    }
                    viewer.viewer('view', innerIndex);
                    viewer.viewer('show', true);
                }
                // end
            } else {
                alert("错误：无法定位要预览的文件或该操作未被授权。");
            }
        },
        error: function () {
            alert("错误：请求失败，请刷新重试。");
        }
    });
}

// 用于创建并显示小于2*limit+1长度的图片列表
function createViewList() {
    if (viewer == null) {
        let images = document.createElement("ul");
        for (let i = 0; i < pvl.pictureViewList.length; i++) {
            if (pvl.pictureViewList[i].filePath.startsWith("homeController")) {
                $(images).append("<li><img src='" + pvl.pictureViewList[i].filePath + "' alt='" + pvl.pictureViewList[i].fileName + "' /></li>");
            } else {
                $(images).append("<li><img src='fileblocks/" + pvl.pictureViewList[i].filePath + "' alt='" + pvl.pictureViewList[i].fileName + "' /></li>");
            }
        }
        viewer = $(images);
        viewer.viewer({
            loop: false,
            hidden: function () {
                viewer.data('viewer').destroy();
                viewer = null;
            }
        });
    }
    viewer.viewer('view', pvl.index);
    viewer.viewer('show', true);
}

// 用于创建长于2*limit+1长度的图片分页列表
function createViewListByPage() {
    // 初始化分页结构
    if (viewer == null) {
        let images = document.createElement("ul");
        let startIndex = (viewerPageIndex - 1) * viewerPageSize;
        if (viewerPageIndex > 1) {
            $(images).append("<li><img src='css/left.png' alt='上一页' /></li>");
        }
        for (let i = 0; i < viewerPageSize && i < (pvl.pictureViewList.length - (viewerPageIndex - 1) * viewerPageSize); i++) {
            if (pvl.pictureViewList[startIndex + i].filePath.startsWith("homeController")) {
                $(images).append("<li><img src='" + pvl.pictureViewList[startIndex + i].filePath + "' alt='" + pvl.pictureViewList[startIndex + i].fileName + "' /></li>");
            } else {
                $(images).append("<li><img src='fileblocks/" + pvl.pictureViewList[startIndex + i].filePath + "' alt='" + pvl.pictureViewList[startIndex + i].fileName + "' /></li>");
            }
        }
        if (viewerPageIndex < viewerTotal) {
            $(images).append("<li><img src='css/right.png' alt='下一页' /></li>");
        }
        viewer = $(images);
        viewer.viewer({
            loop: false,
            view: function (event) {
                // 点击的计数为event.detail.index;
                if (event.detail.index == 0 && viewerPageIndex != 1) {
                    viewerPageIndex--;
                    viewer.data('viewer').destroy();
                    viewer.empty();
                    viewer = null;
                    createViewListByPage();
                    if (viewerPageIndex > 1) {
                        viewer.viewer('view', viewerPageSize);
                    } else {
                        viewer.viewer('view', viewerPageSize - 1);
                    }
                } else if (event.detail.index == viewerPageSize + 1 || (event.detail.index == viewerPageSize && viewerPageIndex == 1)) {
                    viewerPageIndex++;
                    viewer.data('viewer').destroy();
                    viewer.empty();
                    viewer = null;
                    createViewListByPage();
                    viewer.viewer('view', 1);
                }
            },
            hidden: function () {
                viewer.data('viewer').destroy();
                viewer.empty();
                viewer = null;
            }
        });
    }
}

// 兼容Chrome、IE、FF的Shift判定
function isShift(event) {
    let e = window.event || event;
    if (e.shiftKey) {
        return true;
    } else {
        return false;
    }
}

// 选中某一行文件，如果使用Shift点击则为多选
function checkfile(event, fileId) {
    if (!isShift(event)) {
        $(".filerow").removeClass("info");
        $("#" + fileId).addClass("info");
    } else {
        if ($("#" + fileId).hasClass("info")) {
            $("#" + fileId).removeClass("info");
        } else {
            $("#" + fileId).addClass("info");
        }
    }
}

// 连续选中若干行文件：Shift+双击，选中规则为：前有选前，后有选后，全有也选后。
function checkConsFile(event, fileId) {
    if (isShift(event)) {
        let endRow = $("#" + fileId);
        let endRowIndex = endRow.index();
        let startRowIndex = $('.filerow.info:last').index();
        if (startRowIndex != -1) {
            if (startRowIndex < endRowIndex) {
                while (endRow[0] && !endRow.hasClass("info")) {
                    endRow.addClass("info");
                    endRow = endRow.prev();
                }
            } else {
                while (endRow[0] && !endRow.hasClass("info")) {
                    endRow.addClass("info");
                    endRow = endRow.next();
                }
            }
        }
    }
}


// 用于获取全部选中的文件夹和文件ID，该function返回一个对象。
// 该对象中，filesId属性为文件ID，foldersId属性为文件夹ID。两个属性均为JSON数组形式的字符串，可直接发送至服务器。此外还有size，包含了元素的总数。
function getCheckedFilesAndFolders() {
    let filesAndFolders = new Object();
    filesAndFolders.size = 0;
    let filesId = new Array();
    let foldersId = new Array();
    let checkedfiles = $(".info").get();
    for (let i = 0; i < checkedfiles.length; i++) {
        if (checkedfiles[i].getAttribute("iskfolder") == "true") {
            foldersId.push(checkedfiles[i].id);
        } else {
            filesId.push(checkedfiles[i].id);
        }
        filesAndFolders.size++;
    }
    filesAndFolders.filesId = JSON.stringify(filesId);
    filesAndFolders.foldersId = JSON.stringify(foldersId);
    return filesAndFolders;
}

// 切换全部文件行的选中或非选中
function checkallfile() {
    if ($(".filerow.info").length == $(".filerow").length) {
        $(".filerow").removeClass("info");
    } else {
        $(".filerow").addClass("info");
    }
}

// 显示打包下载模态框
function showDownloadAllCheckedModel() {
    $("#downloadAllCheckedBox").html("");
    $("#downloadAllCheckedLoad").text("");
    let faf = getCheckedFilesAndFolders();
    if (faf.size == 0) {
        $("#downloadAllCheckedName").html(checkFilesTip);
    } else {
        $("#downloadAllCheckedName").text(
            "提示：您确认要打包并下载这" + faf.size + "项么？");
        $("#downloadAllCheckedBox")
            .html(
                "<button id='dclmbutton' type='button' class='btn btn-primary' onclick='downloadAllChecked()'>开始下载</button>");
        $("#dclmbutton").attr('disabled', false);
    }
    $("#downloadAllCheckedModal").modal('toggle');
}

// 下载选中的所有文件
function downloadAllChecked() {
    $("#dclmbutton").attr('disabled', true);
    let faf = getCheckedFilesAndFolders();
    $("#downloadAllCheckedName").text(
        "提示：服务器正在对选中资源进行压缩（共" + faf.size
        + "项），这可能需要一些时间（文件越大耗时越长），压缩完成将自动开始下载。");
    // 计算预计耗时
    $.ajax({
        url: 'homeController/getPackTime.ajax',
        type: 'POST',
        data: {
            strIdList: faf.filesId,
            strFidList: faf.foldersId
        },
        dataType: 'text',
        success: function (result) {
            if (result != "0") {
                let count = 0;
                $("#downloadAllCheckedLoad").text("已耗时：" + count + "秒（预计耗时：" + result + "）");
                zipTimer = setInterval(function () {
                    count++;
                    $("#downloadAllCheckedLoad").text("已耗时：" + count + "秒（预计耗时：" + result + "）");
                }, 1000);
            } else {
                let count = 0;
                $("#downloadAllCheckedLoad").text("已耗时：" + count + "秒");
                zipTimer = setInterval(function () {
                    count++;
                    $("#downloadAllCheckedLoad").text("已耗时：" + count + "秒");
                }, 1000);
            }
        },
        error: function () {
            $("#downloadAllCheckedLoad").text("（无法获取预计耗时）");
        }
    });
    // 同时发送压缩下载请求
    $.ajax({
        type: "POST",
        url: "homeController/downloadCheckedFiles.ajax",
        data: {
            strIdList: faf.filesId,
            strFidList: faf.foldersId
        },
        dataType: "text",
        success: function (result) {
            if (zipTimer != null) {
                window.clearInterval(zipTimer);
            }
            if (result == "ERROR") {
                $("#downloadAllCheckedName")
                    .text("提示：压缩过程出错。无法完成压缩，请重试或告知管理员。");
            } else {
                $("#downloadAllCheckedLoad").text("");
                $("#downloadAllCheckedName").text("提示：压缩完成！准备开始下载...");
                let t = setTimeout(
                    "$('#downloadAllCheckedModal').modal('hide');", 800);
                // POST提交全部下载请求
                let temp = document.createElement("form");
                temp.action = 'homeController/downloadCheckedFilesZip.do';
                temp.method = "post";
                temp.style.display = "none";
                let sl = document.createElement("input");
                sl.name = 'zipId';
                sl.value = result;
                temp.appendChild(sl);
                document.body.appendChild(temp);
                temp.submit();
            }
        },
        error: function () {
            $("#downloadAllCheckedName").text("提示：请求失败。无法完成压缩，请重试或告知管理员。");
        }
    });
}

// 删除选中的所有文件
function showDeleteAllCheckedModel() {
    $('#deleteFileBox').html("");
    let faf = getCheckedFilesAndFolders();
    $("#dfmbutton").attr('disabled', false);
    if (faf.size == 0) {
        $('#deleteFileMessage').html(checkFilesTip);
    } else {
        $('#deleteFileBox')
            .html(
                "<button id='dfmbutton' type='button' class='btn btn-danger' onclick='deleteAllChecked()'>全部删除</button>");
        $('#deleteFileMessage').text(
            "提示：确定要彻底删除这" + faf.size + "项么？该操作不可恢复！");
    }
    $('#deleteFileModal').modal('toggle');
}

// 删除选中的所有文件
function deleteAllChecked() {
    // TODO 提交全部删除请求
    let faf = getCheckedFilesAndFolders();
    $("#dfmbutton").attr('disabled', true);
    $('#deleteFileMessage').text("提示：正在删除，请稍候...");
    $.ajax({
        type: "POST",
        dataType: "text",
        data: {
            strIdList: faf.filesId,
            strFidList: faf.foldersId
        },
        url: "homeController/deleteCheckedFiles.ajax",
        success: function (result) {
            if (result == "mustLogin") {
                window.location.href = "login.html";
            } else {
                if (result == "noAuthorized") {
                    $('#deleteFileMessage').text("提示：您的操作未被授权，删除失败");
                    $("#dfmbutton").attr('disabled', false);
                } else if (result == "errorParameter") {
                    $('#deleteFileMessage').text("提示：参数不正确，未能全部删除文件");
                    $("#dfmbutton").attr('disabled', false);
                } else if (result == "cannotDeleteFile") {
                    $('#deleteFileMessage').text("提示：出现意外错误，可能未能删除全部文件");
                    $("#dfmbutton").attr('disabled', false);
                } else if (result == "deleteFileSuccess") {
                    $('#deleteFileModal').modal('hide');
                    showFolderView(locationpath);
                } else {
                    $('#deleteFileMessage').text("提示：出现意外错误，可能未能删除全部文件");
                    $("#dfmbutton").attr('disabled', false);
                }
            }
        },
        error: function () {
            $('#deleteFileMessage').text("提示：出现意外错误，可能未能删除全部文件");
            $("#dfmbutton").attr('disabled', false);
        }
    });
}

function selectFile(fid, fn) {
    parent.attrType.value = fn.substring(fn.lastIndexOf(".") + 1);
    parent.attrId.value = fid;
    parent.attrFile.value = fn;
    parent.fname.value = fn.substring(0, fn.lastIndexOf("."));
    $.modal.close();
}

// 播放音乐
function playAudio(fileId) {
    $('#audioPlayerModal').modal('show');
    if (ap == null) {
        ap = new APlayer({
            container: document.getElementById('aplayer'),
            lrcType: 3,
            mutex: true,
            volume: 0.7,
            theme: '#EDEDED',
            audio: []
        });
        ap.on('pause', function () {
            $("#playOrPause").html("<span class='glyphicon glyphicon-play' aria-hidden='true'></span>");
        });
        ap.on('play', function () {
            $("#playOrPause").html("<span class='glyphicon glyphicon-pause' aria-hidden='true'></span>");
        });
    }
    ap.list.clear();
    $.ajax({
        url: 'homeController/playAudios.ajax',
        data: {
            fileId: fileId
        },
        type: 'POST',
        dataType: 'text',
        success: function (result) {
            let ail = eval("(" + result + ")");
            ap.list.add(ail.as);
            ap.list.switch(ail.index);
            audio_play();
        },
        error: function () {
            alert("错误：无法获取音乐列表，请稍后再试");
            closeAudioPlayer();
        }
    });
}

// 关闭音乐播放器
function closeAudioPlayer() {
    $('#audioPlayerModal').modal('hide');
    ap.seek(0);
    ap.pause();
}

// 切换按钮状态与
function audio_playOrPause() {
    ap.toggle();
}

// 播放
function audio_play() {
    ap.play();
}

// 暂停
function audio_pasue() {
    ap.pause();
}

// 下一首
function audio_fw() {
    ap.skipForward();
}

// 上一首
function audio_bw() {
    ap.skipBack();
}

// 音量加大，每次10%
function audio_vulome_up() {
    ap.volume(ap.audio.volume + 0.1, true);
}

// 音量减少，每次10%
function audio_vulome_down() {
    ap.volume(ap.audio.volume - 0.1, true);
}

// 按文件名排序
function sortbyfn() {
    $("#sortByFN").addClass("glyphicon glyphicon-triangle-bottom");
    $("#sortByCD").removeClass();
    $("#sortByFS").removeClass();
    $("#sortByCN").removeClass();
    folderView.fileList.sort(function (v1, v2) {
        return v1.fileName.localeCompare(v2.fileName, "zh");
    });
    folderView.folderList.sort(function (v1, v2) {
        return v1.folderName.localeCompare(v2.folderName, "zh");
    });
    showFolderTable(folderView);
}

// 按创建日期排序
function sortbycd() {
    $("#sortByFN").removeClass();
    $("#sortByCD").addClass("glyphicon glyphicon-triangle-bottom");
    $("#sortByFS").removeClass();
    $("#sortByCN").removeClass();
    folderView.fileList.sort(function (v1, v2) {
        let v1DateStr = v1.fileCreationDate.replace("年", "-").replace("月", "-").replace("日", "");
        let v2DateStr = v2.fileCreationDate.replace("年", "-").replace("月", "-").replace("日", "");
        let res = ((new Date(Date.parse(v1DateStr)).getTime()) - (new Date(Date.parse(v2DateStr)).getTime()));
        return -1 * res;
    });
    folderView.folderList.sort(function (v1, v2) {
        let v1DateStr = v1.folderCreationDate.replace("年", "-").replace("月", "-").replace("日", "");
        let v2DateStr = v2.folderCreationDate.replace("年", "-").replace("月", "-").replace("日", "");
        let res = ((new Date(Date.parse(v1DateStr)).getTime()) - (new Date(Date.parse(v2DateStr)).getTime()));
        return -1 * res;
    });
    showFolderTable(folderView);
}

// 按文件大小排序
function sortbyfs() {
    $("#sortByFN").removeClass();
    $("#sortByCD").removeClass();
    $("#sortByFS").addClass("glyphicon glyphicon-triangle-bottom");
    $("#sortByCN").removeClass();
    folderView.fileList.sort(function (v1, v2) {
        return v2.fileSize - v1.fileSize;
    });
    showFolderTable(folderView);
}

// 按创建者排序
function sortbycn() {
    $("#sortByFN").removeClass();
    $("#sortByCD").removeClass();
    $("#sortByFS").removeClass();
    $("#sortByCN").addClass("glyphicon glyphicon-triangle-bottom");
    folderView.fileList.sort(function (v1, v2) {
        return v1.fileCreator.localeCompare(v2.fileCreator, "zh");
    });
    folderView.folderList.sort(function (v1, v2) {
        return v1.folderCreator.localeCompare(v2.folderCreator, "zh");
    });
    showFolderTable(folderView);
}

// 显示原始的顺序
function showOriginFolderView() {
    $("#sortByFN").removeClass();
    $("#sortByCD").removeClass();
    $("#sortByFS").removeClass();
    $("#sortByCN").removeClass();
    if (screenedFoldrView != null) {
        folderView = $.extend(true, {}, screenedFoldrView);
    } else {
        folderView = $.extend(true, {}, originFolderView);
    }
    showFolderTable(folderView);
}

// 确认文件移动（剪切-粘贴）操作
function startMoveFile() {
    if ($("#cutSignTx").hasClass("cuted") && checkedMovefiles !== undefined) {
        $('#moveFilesMessage').text("提示：确定将这" + checkedMovefiles.size + "项移动到当前位置么？");
        $('#moveFilesBox').html("<button id='dmvfbutton' type='button' class='btn btn-danger' onclick='doMoveFiles()'>全部移动</button>");
        $("#selectFileMoveModelAsAll").removeAttr("checked");
        $("#selectFileMoveModelAlert").hide();
        $('#moveFilesModal').modal('show');
    } else {
        checkedMovefiles = getCheckedFilesAndFolders();
        if (checkedMovefiles == undefined || checkedMovefiles.size == 0) {
            $('#moveFilesMessage').html(checkFilesTip);
            $("#selectFileMoveModelAsAll").removeAttr("checked");
            $("#selectFileMoveModelAlert").hide();
            $('#moveFilesModal').modal('show');
        } else {
            $("#cutSignTx").html("粘贴（" + checkedMovefiles.size + "）<span class='pull-right'><span class='glyphicon glyphicon-arrow-up' aria-hidden='true'></span>+V</span>");
            $("#cutSignTx").addClass("cuted");
        }
    }
}

let repeMap;
let strMoveOptMap;
let mRepeSize;

// 执行文件移动操作
function doMoveFiles() {
    $("#dmvfbutton").attr('disabled', true);
    $('#moveFilesMessage').text("提示：正在移动，请稍候...");
    // 确认移动目标位置
    $.ajax({
        type: "POST",
        dataType: "text",
        data: {
            strIdList: checkedMovefiles.filesId,
            strFidList: checkedMovefiles.foldersId,
            locationpath: locationpath
        },
        url: "homeController/confirmMoveFiles.ajax",
        success: function (result) {
            if (result == "mustLogin") {
                window.location.href = "login.html";
            } else {
                if (result == "noAuthorized") {
                    $('#moveFilesMessage').text("提示：您的操作未被授权，移动失败");
                    $("#dmvfbutton").attr('disabled', false);
                } else if (result == "errorParameter") {
                    $('#moveFilesMessage').text("提示：参数不正确，未能全部移动文件，请刷新后重试");
                    $("#dmvfbutton").attr('disabled', false);
                } else if (result == "cannotMoveFiles") {
                    $('#moveFilesMessage').text("提示：出现意外错误，可能未能移动全部文件，请刷新后重试");
                    $("#dmvfbutton").attr('disabled', false);
                } else if (result == "confirmMoveFiles") {
                    strMoveOptMap = {};
                    sendMoveFilesReq();
                } else if (result.startsWith("duplicationFileName:")) {
                    repeMap = eval("(" + result.substring(20) + ")");
                    repeIndex = 0;
                    strMoveOptMap = {};
                    mRepeSize = repeMap.repeFolders.length + repeMap.repeNodes.length;
                    if (repeMap.repeFolders.length > 0) {
                        $("#mrepeFileName").text(repeMap.repeFolders[repeIndex].folderName);
                    } else {
                        $("#mrepeFileName").text(repeMap.repeNodes[repeIndex].fileName);
                    }
                    $("#selectFileMoveModelAlert").show();
                } else if (result.startsWith("CANT_MOVE_TO_INSIDE:")) {
                    $('#moveFilesMessage').text("错误：不能将一个文件夹移动到其自身内部：" + result.substring(20));
                } else {
                    $('#moveFilesMessage').text("提示：出现意外错误，可能未能移动全部文件，请刷新后重试");
                    $("#dmvfbutton").attr('disabled', false);
                }
            }
        },
        error: function () {
            $('#moveFilesMessage').text("提示：出现意外错误，可能未能移动全部文件");
            $("#dmvfbutton").attr('disabled', false);
        }
    });
}

// 对冲突的移动进行依次询问
function selectFileMoveModel(t) {
    if ($("#selectFileMoveModelAsAll").prop("checked")) {
        while (repeIndex < mRepeSize) {
            if (repeIndex < repeMap.repeFolders.length) {
                strMoveOptMap[repeMap.repeFolders[repeIndex].folderId] = t;
            } else {
                strMoveOptMap[repeMap.repeNodes[repeIndex - repeMap.repeFolders.length].fileId] = t;
            }
            repeIndex++;
        }
        $("#selectFileMoveModelAlert").hide();
        sendMoveFilesReq();
    }
    if (repeIndex < repeMap.repeFolders.length) {
        strMoveOptMap[repeMap.repeFolders[repeIndex].folderId] = t;
    } else {
        strMoveOptMap[repeMap.repeNodes[repeIndex - repeMap.repeFolders.length].fileId] = t;
    }
    repeIndex++;
    if (repeIndex < mRepeSize) {
        if (repeIndex < repeMap.repeFolders.length) {
            $("#mrepeFileName").text(repeMap.repeFolders[repeIndex].folderName);
        } else {
            $("#mrepeFileName").text(repeMap.repeNodes[repeIndex - repeMap.repeFolders.length].fileName);
        }
    } else {
        $("#selectFileMoveModelAlert").hide();
        sendMoveFilesReq();
    }
}


function sendMoveFilesReq() {
    // 执行移动行为
    let strOptMap = JSON.stringify(strMoveOptMap);
    $.ajax({
        type: "POST",
        dataType: "text",
        data: {
            strIdList: checkedMovefiles.filesId,
            strFidList: checkedMovefiles.foldersId,
            strOptMap: strOptMap,
            locationpath: locationpath
        },
        url: "homeController/moveCheckedFiles.ajax",
        success: function (result) {
            if (result == "mustLogin") {
                window.location.href = "login.html";
            } else {
                if (result == "noAuthorized") {
                    $('#moveFilesMessage').text("提示：您的操作未被授权，移动失败");
                    $("#dmvfbutton").attr('disabled', false);
                } else if (result == "errorParameter") {
                    $('#moveFilesMessage').text("提示：参数不正确，未能全部移动文件，请刷新后重试");
                    $("#dmvfbutton").attr('disabled', false);
                } else if (result == "cannotMoveFiles") {
                    $('#moveFilesMessage').text("提示：出现意外错误，可能未能移动全部文件，请刷新后重试");
                    $("#dmvfbutton").attr('disabled', false);
                } else if (result == "moveFilesSuccess") {
                    $('#moveFilesModal').modal('hide');
                    showFolderView(locationpath);
                } else {
                    $('#moveFilesMessage').text("提示：出现意外错误，可能未能移动全部文件，请刷新后重试");
                    $("#dmvfbutton").attr('disabled', false);
                }
            }
        },
        error: function () {
            $('#moveFilesMessage').text("提示：出现意外错误，可能未能移动全部文件");
            $("#dmvfbutton").attr('disabled', false);
        }
    });
}

let screenedFoldrView;// 经过排序的文件视图

// 执行搜索功能
function doSearchFile() {
    startLoading();
    try {
        let keyworld = $("#searchKeyWordIn").val();
        if (keyworld.length != 0) {
            let reg = new RegExp(keyworld + "+");
            screenedFoldrView = $.extend(true, {}, originFolderView);
            screenedFoldrView.folderList = [];
            screenedFoldrView.fileList = [];
            for (let i = 0, j = originFolderView.folderList.length; i < j; i++) {
                if (reg.test(originFolderView.folderList[i].folderName)) {
                    screenedFoldrView.folderList.push(originFolderView.folderList[i]);
                }
            }
            for (let i = 0, j = originFolderView.fileList.length; i < j; i++) {
                if (reg.test(originFolderView.fileList[i].fileName)) {
                    screenedFoldrView.fileList.push(originFolderView.fileList[i]);
                }
            }
            $("#sortByFN").removeClass();
            $("#sortByCD").removeClass();
            $("#sortByFS").removeClass();
            $("#sortByCN").removeClass();
            folderView = $.extend(true, {}, screenedFoldrView);
            showFolderTable(folderView);
        } else {
            screenedFoldrView = null;
            showOriginFolderView();
        }
    } catch (e) {
        alert("错误：搜索关键字有误。请在特殊符号（例如“*”）前加上“\\”进行转义。");
    }
    endLoading();
}