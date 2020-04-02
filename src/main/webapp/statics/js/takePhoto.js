////////////获取摄像流数据		
var localMediaStream = "";

// /////////视频聊天窗口
var video = document.getElementById('myVideo');

// ///获取拍照的canvas
var takePhotoCanvas = document.getElementById('photo');

var cameraWidth = 0;
var cameraHeight = 0;
// ///////////摄像机开启成功时执行该函数
function onSuccess(stream) {
	localMediaStream = stream;
	if (window.URL) {
		video.src = window.URL.createObjectURL(stream);
	} else {
		video.src = stream;
	}
	// 在这里是拿不到video的宽高的
	// console.log(video.videoWidth + " " + video.videoHeight);
	video.play();
}

// 摄像机开启失败时执行该函数
function onError() {
	console.log("获取摄像头出现问题了。。。");
}
var clipboard = "";
 

var photoInfo = "";
// 拍照

function takePhoto() {
			document
			.getElementById("faceImage").innerHTML = "";
			var photoWidth = video.videoWidth;
			var photoHeight = video.videoHeight;
			var width_height = photoWidth / photoHeight;
			var pic_width = 200;
			var pic_height = pic_width / width_height;
			document.getElementById("photo").width = pic_width;
			document.getElementById("photo").height = pic_height;
			var video_src = video;
			
			takePhotoCanvas.getContext("2d").drawImage(video, 0, 0, pic_width,
					pic_height);
			
			photo_data = takePhotoCanvas.toDataURL("image/png");
			// 将图像数据传往后台，用于登录
			console.log("aaaa")
			
			
			faceLogin(photo_data);
			var photo_img = document.createElement("img");
			
			photo_img.src = photo_data;
			
			var deliveryInfoDiv = document
					.getElementById("faceImage");
			deliveryInfoDiv.appendChild(photo_img);
			
		};
		
		
function faceRegister(faceImage) {
	var rootpath = getRootPath();
	var faceRegisterUrl = rootpath + "/user/faceRegister.do";
	$.ajax({
		"type" : "POST",
		"url" : faceRegister,
		"dataType" : "json",
		"data" : {
			"faceImage" : faceImage,
			"userName" : "pcchen",
		},
	 
		"connectiontimeout" : 1000,
		"success" : function(resp) {
			loginJudge(resp);
		},
		"error" : function(e) {
			console.log("文件上传失败。。");
			console.log(e);
		}
	});
}
		
var takingPhoto = "";
// 控制摄像头是否打开
$("#controlVideo").click(function() {
	if (localMediaStream == "") {

		navigator.getUserMedia({
			video : true,
			audio : false
		}, onSuccess, onError);
		document.getElementById("controlVideo").innerText = "关闭摄像头";
		takingPhoto = window.setInterval(takePhoto, 3000);
		
	} else {
		// document.getElementById("faceImage").innerHTML = "";
		localMediaStream.getVideoTracks()[0].stop();
		localMediaStream = "";
		document.getElementById("controlVideo").innerText = "使用摄像头登录";
		window.clearTimeout(takingPhoto);
	}
	 

})

function faceLogin(faceImage) {
	 var rootpath = getRootPath();
	 var faceLoginUrl = rootpath + "/user/loginByFace.do";
	$.ajax({
		"type" : "POST",
		"url" : faceLoginUrl,
		"dataType" : "json",
		"data" : {
			"faceImage" : faceImage,
			"userName" : "pcchen",
		},
	 
		"connectiontimeout" : 1000,
		"success" : function(resp) {
			loginJudge(resp);
		},
		"error" : function(e) {
			console.log("文件上传失败。。");
			console.log(e);
		}
	});
}
// 判断是否登录成功

function loginJudge(result) {
	//如果用户不存在，给出提示
	if(result.user == 'notExist') {
		window.clearTimeout(takingPhoto);
		var r=confirm("把脸靠近点！看不清。。。");
		    if (r==true){
		    	takingPhoto = window.setInterval(takePhoto, 1000);
		    }
		    else{
		        
		    }
	} else if(result.user == 'alreadyExist'){  // 用户已经登录了
		var r=confirm("用户已在别处登录，是否继续登录！");
		
		if(r == true) {   // 确认再次登录
			var username = document.getElementById("username").value;
			var password = document.getElementById("password").value;
			 $.ajax({
					type: "POST",
					url: url,
			    	data: {
			    		username : username,
			    		password : password,
			    		confirmLogin : 'confirmLogin' 
			    	},
			    	 
					dataType:'json',
					success: function(data){
						
						window.location.href = rootpath + "/user/customer.do";
						 
					}
				});
			
		} else {   // 不再登录了
			
			
		}
		
	} else {   
		window.location.href = rootpath + "/user/customer.do";

	}
}
