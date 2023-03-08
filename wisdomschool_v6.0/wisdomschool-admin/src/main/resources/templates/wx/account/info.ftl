<!DOCTYPE html>
<html lang="en">
<head>
    <#--    设定界面大小，取消用户缩放   -->
    <meta charset="UTF-8" name="viewport" content="width=device-width,user-scalable=no">
    <title>账户信息</title>
    <link rel="stylesheet" href="/css/bootstrap.min.css">
</head>
<body>
    <div class="container">
        <h2 class="heading">账户信息</h2>

        <div class="all-content">
            <div class="form-group">
                <label>学生学号：</label><p id="studentId">${info.loginName}</p>
                <label>学生姓名：</label><p>${info.userName}</p>
                <label>年级：</label><p>${info.grades.name}</p>
                <label>系部：</label><p>${info.department.name}</p>
                <label>专业：</label><p>${info.major.name}</p>
                <label>班级：</label><p>${info.clbum.name}
                <p hidden id="openId">${openId}</p>
            </div>
        </div>

        <div>
            <button class="btn btn-danger btn" id="unbind">取消绑定</button>
        </div>
    </div>
</body>
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/js/bootstrap.min.js"></script>
<script>
    $(function () {
        $("#unbind").click(function () {
            if (window.confirm('确定是否解除绑定')) {
                unbind()
            }
        });

        var openId = $("#openId").text();
        var studentId = $("#studentId").text();
        var unbindUrl = '/wx/account/unbind';
        var register = '/wx/account/register/' + openId + '?returnUrl=' + window.location.href;

        function unbind() {
            console.log(studentId);
            $.ajax({
                type: "post",
                url: unbindUrl,
                dataType: "json",
                data: {
                    "openId": openId,
                    "studentId": studentId
                },
                success: function(result) {
                    //  响应code判断
                    if (result.code === 1) {
                        alert(result.data);
                        window.location.replace(register);
                    } else if (result.code === 0) {
                        alert(result.data);
                    } else {
                        alert('未知错误');
                    }
                }
            })
        }
    });

</script>
</html>