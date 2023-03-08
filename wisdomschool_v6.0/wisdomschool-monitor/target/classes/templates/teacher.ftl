<html lang="en">

<head>
	<title>监控画面</title>
	<meta name="viewport" content="width=device-width, initial-scale=1" charset="utf-8"/>
	<link rel="stylesheet" href="css/bootstrap.css"/>
	<link rel="stylesheet" href="font-awesome.min.css"/>
	<link rel="styleSheet" href="style.css" type="text/css" media="screen"/>
	<script src="js/bootstrap.min.js" ></script>
	<script src="jquery-3.3.1.min.js" ></script>
	<script src="openvidu-browser-2.13.0.js"></script>
	<script type="text/javascript" src="/js/common.js"></script>
	<script type="text/javascript" src="/js/plugins/jquery-validation/jquery.validate.min.js"></script>
	<script type="text/javascript" src="/js/plugins/jquery-validation/messages_zh.min.js"></script>
	<script type="text/javascript" src="/js/plugins/layer/layer.js"></script>
</head>

<body>

<div id="main-container" class="container ">

	<div class="row">
		<div id="session-header" class="col-md-12">
			<div class="row">
				<div class="col-md-12 text-right">
					<button id="buttonLeaveSession" class="btn  btn-danger text-right"  onclick="closeSession()">关闭会议</button>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12 text-center mt-3 mb-1"><h1 class="text-primary font-weight-bold ">在线考试监控页面</h1></div>
			</div>
			<div class="row col-md-12">
				<button class="btn btn-danger btn-sm col-md-2 mr-3" onclick="broadcast()">广播</button>
				<input type="text" class="form-control col-md-3 mr-3" id="broadcast">
				<textarea class="form-control col-md-6" aria-label="消息框" id="msg"></textarea>
			</div>
		</div>
	</div>
	<div class="row">

		<div id="video-container" class="col-md-12 img-thumbnail " >
			<video autoplay  muted style="width:0;height:0;" ></video>
		</div>


	</div>
</div>





</body>

<script type="text/javascript">

	// var Authorization=localStorage.getItem("token")
	var sessionName;
	var token;
	var nickName;
	var userName
	window.onload=function(){
		initData();
	}

   function initData(){
	   $.ajax({
		   type: "get",
		   url: "/examMonitor/teacher/${paperId}",
		   headers: {
			   'Authorization':localStorage.getItem("token")
		   },
		   success: function (res) {
		   	if(res.code===0){
				sessionName=res.data.sessionName;
				token=res.data.token;
				nickName=res.data.nickName;
				userName=res.data.userName;
				initOV();
			}
		   }
	   });
   }

	function initOV() {
		//--1）获取OpenVidu对象---
		OV = new OpenVidu();

		//---2）初始化会话---
		session = OV.initSession();

		//---3）指定会话中发生事件时的操作---
		//在收到的每一条新流上。。。
		session.on('streamCreated', (event) => {

			var clientData;
			var serverData;
			var subscriber = session.subscribe(event.stream, undefined);

			clientData = JSON.parse(subscriber.stream.connection.data.split('%/%')[0]).clientData;
			serverData = JSON.parse(subscriber.stream.connection.data.split('%/%')[1]).serverData;
			var videoDiv = document.createElement('div');
			var connectionId = subscriber.stream.connection.connectionId;

			var divId='div'+connectionId;
			let elementById = document.getElementById(divId);
			if(!elementById) {
				videoDiv.id =divId
				videoDiv.style.width = "360px"
				videoDiv.style.height = "400px"
				videoDiv.className="img-thumbnail  row h-100 mr-2"
				// videoDiv.style.marginRight = "1px"
				videoDiv.style.marginLeft = "1px"
				videoDiv.style.padding = "5px"
				videoDiv.innerHTML =
						'<div class="row col-md-12 mb-1 " style="height: 10%;margin-left: 1px"><label  class="text-primary text-center h-100 h5">'+'考生号:'+clientData+'</label></div>'+
						'<div class="row col-md-12 mb-1" style="height: 12%;margin-left: 1px"><input id="input'+connectionId+'"  class="col-md-5 m-1 h-100 img-thumbnail"/><button id="send'+connectionId+'" type="button" class="col-md-3 btn btn-danger btn-sm mr-1 mb-1" >发送</button><button id="sub'+connectionId+'"  type="button" class="col-md-3 btn btn-danger btn-sm mb-1" >收卷</button></div>'+
						'<div class="row col-md-12  img-thumbnail " style="height: 73%;margin-left: 1px"><video class="col-md-12  h-100" id="' + connectionId + '" autoplay muted  controls ></video></div>';
				var videoContainer = document.getElementById("video-container");
				videoContainer.appendChild(videoDiv)
				var video = document.getElementById(connectionId);
				video.setAttribute('poster', '/images/user.png')
				subscriber.addVideoElement(video);
				video.controls = true
			}
			//订阅流以接收它
			//HTML视频将附加到具有“video-container”id的元素

			//将HTML视频附加到DOM后。。。
			subscriber.on('videoElementCreated', (event) => {

			});
		});

		//在每一条被摧毁的流上。。。
		session.on('streamDestroyed', (event) => {
			//删除带有用户名和昵称的HTML元素
			removeUserData(event.stream.connection);
		});

		//---4）连接到会话，并从中传递检索到的令牌和一些其他数据
		//客户机（在本例中为JSON，昵称由用户选择）---

		session.connect(token, {clientData: nickName})
				.then(() => {

					// --- 5)设置活动通话的页面布局---


					// var publisher = OV.initPublisher('video-container', {
					// 	audioSource: undefined, //音频的来源。如果未定义默认麦克风
					// 	publishVideo: false,  	//是否要在启用视频的情况下开始发布
					// });
					// session.publish(publisher);
				})
				.catch(error => {
					console.warn('There was an error connecting to the session:', error.code, error.message);
				});
		session.on('signal', (event) => {
			if (event.type.toString()=='signal:student'){
				var clientData = JSON.parse(event.from.data.split('%/%')[0]).clientData;
				var serverData = JSON.parse(event.from.data.split('%/%')[1]).serverData;
				var name='考生:'+clientData
				let val = $("#msg").val();
				val=val+"\n"+name+":"+event.data;
				$("#msg").val(val)
			}

		});

		this.session.on('connectionCreated', (event) => {
			var connection=event.connection
			if(connection.rpcSessionId===undefined){
				var sendid="send"+connection.connectionId
				var subid="sub"+connection.connectionId
				var inputid="input"+connection.connectionId
				$(document).on("click", "#"+sendid,function () {
					var msg=$('#'+inputid).val();
					session.signal({
						data: msg,
						to: [connection],
						type: 'monitor'
					});
					$('#'+inputid).val("");
				});

				$(document).on("click", "#"+subid,function () {
					var statu = confirm("确定要强制收卷吗");//在js里面写confirm，在页面中弹出提示信息。
					if (!statu) {
						return false;
					} else {
						session.signal({
							data: "你已经被强制收卷",
							to: [connection],
							type: 'sub'
						});
					}
				});

			}

		});


	}
	//收卷
	function subPaper(loginName){

	}
	function broadcast(){
		var msg=$('#broadcast').val();
		session.signal({
			data: msg,
			to: [],
			type: 'broadcast'
		});
		$('#broadcast').val("");
	}
	function closeSession() {
		$.ajax({
			type: "POST",
			url: "/examMonitor/closesession",
			headers: {
				'Authorization':localStorage.getItem("token")
			},
			data:{
				 "sessionName":${paperId},
			},
			success: function (res) {
				$.modal.alertWarning(res);
				session.disconnect();
				$('#nickName').remove();
				$('#userName').remove();
			}
		});

		//---9）通过在会话对象上调用“disconnect”方法离开会话---

	}



	function removeUserData(connection) {
		$("#data-" + connection.connectionId).remove();
	}






</script>

</html>
