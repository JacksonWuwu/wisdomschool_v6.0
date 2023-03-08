<!DOCTYPE html>
<html lang="zh" xmlns:th="http://www.thymeleaf.org" >
<meta charset="utf-8">
<title>用户头像修改</title>
<link th:href="@{/ajax/libs/cropbox/cropbox.css}" rel="stylesheet"/>
<body class="white-bg">
    <input name="userId" id="userId" type="hidden"  th:value="${sysUser.userId}" />
    <div class="container"> 
        <div class="imageBox"> 
	        <div class="thumbBox"></div> 
	        <div class="spinner" style="display: none">Loading...</div> 
	   </div> 
	   <div class="action"> 
	       <div class="new-contentarea tc">
	           <a href="javascript:void(0)" class="upload-img"> <label for="avatar">上传图像</label> </a> 
	           <input type="file" class="" name="avatar" id="avatar" accept="image/*"/>
	       </div> 
	   <input type="button" id="btnCrop" class="Btnsty_peyton" value="裁切" /> 
	   <input type="button" id="btnZoomIn" class="Btnsty_peyton" value="+" /> 
	   <input type="button" id="btnZoomOut" class="Btnsty_peyton" value="-" /> 
	   </div> 
	   <div class="cropped"></div> 
    </div>
<div th:include="include::footer"></div>
<script th:src="@{/ajax/libs/cropbox/cropbox.js}"></script>
<script type="text/javascript">
var cropper;
$(window).load(function() {
	var avatar = '[[${sysUser.avatar}]]';
    var options = {
        thumbBox: '.thumbBox',
        spinner: '.spinner',
        imgSrc: $.common.isEmpty(avatar) ? ctx + 'img/profile.jpg' : ctx + 'profile/avatar/' + avatar
    }
    cropper = $('.imageBox').cropbox(options);
    $('#avatar').on('change', function() {
        var reader = new FileReader();
        reader.onload = function(e) {
            options.imgSrc = e.target.result;
            //根据MIME判断上传的文件是不是图片类型
            if((options.imgSrc).indexOf("image/")==-1){
                parent.layer.alert("文件格式错误，请上传图片类型,如：JPG,JEPG，PNG后缀的文件。", {icon: 2,title:"系统提示"});
            } else {
                cropper = $('.imageBox').cropbox(options);
            }
        }
        reader.readAsDataURL(this.files[0]);
    })
    
	$('#btnCrop').on('click', function(){
		var img = cropper.getDataURL();
		$('.cropped').html('');
		$('.cropped').append('<img src="'+img+'" align="absmiddle" style="width:64px;margin-top:4px;border-radius:64px;box-shadow:0px 0px 12px #7E7E7E;" ><p>64px*64px</p>');
		$('.cropped').append('<img src="'+img+'" align="absmiddle" style="width:128px;margin-top:4px;border-radius:128px;box-shadow:0px 0px 12px #7E7E7E;"><p>128px*128px</p>');
		$('.cropped').append('<img src="'+img+'" align="absmiddle" style="width:180px;margin-top:4px;border-radius:180px;box-shadow:0px 0px 12px #7E7E7E;"><p>180px*180px</p>');
	})
	
	$('#btnZoomIn').on('click', function(){
		cropper.zoomIn();
	})
	
	$('#btnZoomOut').on('click', function(){
		cropper.zoomOut();
	})
});

function submitHandler() {
    var img = cropper.getBlob();
    var formdata = new FormData();
    formdata.append("avatarfile", img);
    formdata.append("userId", $("#userId").val());
    $.ajax({
        url: ctx + "system/sysUser/profile/updateAvatar",
        data: formdata,
        type: "post",
        processData: false,
        contentType: false,
        success: function(result) {
            $.operate.ajaxModalSuccess(result);
        }
    })
}
</script>
</body>
</html>
