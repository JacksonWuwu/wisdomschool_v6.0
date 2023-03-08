<html lang="en">

<head>
	<title>监控画面</title>

	<meta name="viewport" content="width=device-width, initial-scale=1" charset="utf-8"/>

	<link rel="shortcut icon" href="images/favicon.ico" type="image/x-icon"/>
	<link rel="stylesheet" href="bootstrap.min.css"/>
	<link rel="stylesheet" href="font-awesome.min.css"/>
	<link rel="styleSheet" href="style.css" type="text/css" media="screen"/>
	<script src="bootstrap.min.js" ></script>
	<script src="jquery-3.3.1.min.js" ></script>
	<script src="openvidu-browser-2.13.0.js"></script>
</head>

<body>
	<div id="main-container" class="container">
		<div id="logged">
			<div id="session">
				<div id="session-header">
					<button id="buttonLeaveSession" class="btn btn-large btn-danger"  onclick="leaveSession()">离开会议</button>
				</div>
				<div id="main-video" class="col-md-6" hidden>
					<p class="nickName"></p>
					<p class="userName"></p>
					<video autoplay="autoplay" playsinline="true"></video>
				</div>
				<div id="video-container" class="col-md-6"></div>
			</div>
		</div>
	</div>



</body>

<script type="text/javascript">

	var Authorization=localStorage.getItem("token")
	var sessionName;
	var token;
	var nickName;
	var userName
	window.onload=function(){
		initData();
	}

   function initData(){
		var url
	   if (${role}===0){ //如果为0则为订阅者角色，即为监考人员
		   url="/examMonitor/teacher/${paperId}"
	    }else {
		   url="/examMonitor/student/${paperId}"
	   }
	   $.ajax({
		   type: "get",
		   url: url,
		   headers: {
			   'Authorization':Authorization,
		   },
		   success: function (res) {
			   console.log(res)
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

	console.warn('Request of TOKEN gone WELL (TOKEN:' + token + ')');
	function initOV() {
		//--1）获取OpenVidu对象---
		OV = new OpenVidu();

		//---2）初始化会话---
		session = OV.initSession();

		//---3）指定会话中发生事件时的操作---
		//在收到的每一条新流上。。。
		session.on('streamCreated', (event) => {
			//订阅流以接收它
			//HTML视频将附加到具有“video-container”id的元素
			var subscriber = session.subscribe(event.stream, 'video-container');

			//将HTML视频附加到DOM后。。。
			subscriber.on('videoElementCreated', (event) => {

				//在视频中为用户名和昵称添加新的HTML元素
				appendUserData(event.element, subscriber.stream.connection);
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
					$('#session-title').text(sessionName);
					$('#join').hide();
					$('#session').show();


					//在这里，我们以某种方式检查用户以前是否具有“publisher”角色
					//正在尝试发布其流。即使有人修改了客户端的代码
					//已发布流，如果令牌在会话中发送，它将不起作用。连接
					//方法未被OpenVidu服务器识别为“PUBLIHSER”角色
					if (isPublisher()) {
						// --- 6) 获取自己的相机流 ---
						var publisher = OV.initPublisher('video-container', {
							audioSource: undefined, //音频的来源。如果未定义默认麦克风
							videoSource: undefined, //视频的来源。如果未定义默认网络摄像头
							publishAudio: true,  	//是否要在音频未静音的情况下开始发布
							publishVideo: true,  	//是否要在启用视频的情况下开始发布
							resolution: '640x480',  //视频的分辨率
							frameRate: 30,			//视频的帧速率
							insertMode: 'APPEND',	//如何将视频插入目标元素“视频容器”
							mirror: false       	//是否镜像本地视频
						});

						//---7）指定在发布服务器中发生事件时的操作---
						//当我们的HTML视频被添加到DOM。。。
						publisher.on('videoElementCreated', (event) => {
							//与我们一起初始化主视频并附加数据
							var userData = {
								nickName: nickName,
								userName: userName
							};
							initMainVideo(event.element, userData);
							appendUserData(event.element, userData);
							$(event.element).prop('muted', true); //静音本地视频
						});


						//---8）发布您的流---
						session.publish(publisher);

					} else {
						console.warn('You don\'t have permissions to publish');
						initMainVideoThumbnail(); //在主视频中显示订户消息
					}
				})
				.catch(error => {
					console.warn('There was an error connecting to the session:', error.code, error.message);
				});

	}
	function leaveSession() {
		$.ajax({
			type: "POST",
			url: "/examMonitor/leavesession",
			headers: {
				'Authorization':Authorization,
			},
			data:{
				 "sessionName":${paperId},
				 "token":token
			},
			success: function (res) {
				alert(res)
				session.disconnect();
			}
		});

		//---9）通过在会话对象上调用“disconnect”方法离开会话---

	}

	function appendUserData(videoElement, connection) {
		var clientData;
		var serverData;
		var nodeId;
		if (connection.nickName) { //附加本地视频数据
			clientData = connection.nickName;
			serverData = connection.userName;
			nodeId = 'main-videodata';
		} else {
			clientData = JSON.parse(connection.data.split('%/%')[0]).clientData;
			serverData = JSON.parse(connection.data.split('%/%')[1]).serverData;
			nodeId = connection.connectionId;
		}
		var dataNode = document.createElement('div');
		dataNode.className = "data-node";
		dataNode.id = "data-" + nodeId;
		dataNode.innerHTML = '<p class="nickName">' + clientData + '</p><p class="userName">' + serverData + '</p>';
		videoElement.parentNode.insertBefore(dataNode, videoElement.nextSibling);
		addClickListener(videoElement, clientData, serverData);
	}

	function removeUserData(connection) {
		var userNameRemoved = $("#data-" + connection.connectionId);
		if ($(userNameRemoved).find('p.userName').html() === $('#main-video p.userName').html()) {
			cleanMainVideo(); //主要视频中的参与者已离开
		}
		$("#data-" + connection.connectionId).remove();
	}

	function removeAllUserData() {
		$(".data-node").remove();
	}

	function cleanMainVideo() {
		$('#main-video video').get(0).srcObject = null;
		$('#main-video p').each(function () {
			$(this).html('');
		});
	}

	function addClickListener(videoElement, clientData, serverData) {
		videoElement.addEventListener('click', function () {
			var mainVideo = $('#main-video video').get(0);
			if (mainVideo.srcObject !== videoElement.srcObject) {
				$('#main-video').fadeOut("fast", () => {
					$('#main-video p.nickName').html(clientData);
					$('#main-video p.userName').html(serverData);
					mainVideo.srcObject = videoElement.srcObject;
					$('#main-video').fadeIn("fast");
				});
			}
		});
	}

	function initMainVideo(videoElement, userData) {
		$('#main-video video').get(0).srcObject = videoElement.srcObject;
		$('#main-video p.nickName').html(userData.nickName);
		$('#main-video p.userName').html(userData.userName);
		$('#main-video video').prop('muted', true);
	}

	function initMainVideoThumbnail() {
		$('#main-video video').css("background", "url('images/subscriber-msg.jpg') round");
	}

	function isPublisher() {
		return token.includes('PUBLISHER');
	}
	function isSubscriber() {
		return token.includes('SUBSCRIBER');
	}
</script>

</html>
