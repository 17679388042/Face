function getRootPath() {
	// 获取当前网址，如： http://localhost:8088/test/test.jsp
	var curPath = window.document.location.href;
	// 获取主机地址之后的目录，如： test/test.jsp
	var pathName = window.document.location.pathname;
	var pos = curPath.indexOf(pathName);
	// 获取主机地址，如： http://localhost:8088
	var localhostPath = curPath.substring(0, pos);
	// 获取带"/"的项目名，如：/test
	var projectName = pathName
			.substring(0, pathName.substr(1).indexOf('/') + 1);
	return (localhostPath + projectName);// 发布前用此
}

 
$(document).ready(function() {
	isLogin();
});
rootPath = getRootPath();
function isLogin() {
	var rootPath = getRootPath();
	var URL = rootPath + "/user/isLogin.do";
	$.ajax({
		url : URL,
		type : 'post',
		data : {

		},
		success : function(data) {
			var userInfo = eval("(" + data + ")");
			var username = userInfo.userInfo; 
			console.log(username);
			if (username.length == 0) {
				document.getElementById("isLogin").innerHTML = "未登录/";
				var login = "<a   href='" + rootPath + "'>登录</a>";
				$("#isLogin").append(login);
			} else {
				document.getElementById("isLogin").innerHTML = "已登录/";
				var logoff = "<a href='#' onclick=logout() >注销</a>";
				
				document.getElementById("loginUser").innerText = "欢迎：" + username;
				
				$("#isLogin").append(logoff);
			}
		}
	});
}
function logout() {
	var URL = rootPath + "/user/logout.do";
		$.ajax({
			url : URL,
			type : 'post',
			data : {

			},
			success : function(data) {
				console.log(data);
				window.location.href = rootPath;
			},
			error : function(e) {
				console.log(e);
				alert("注销出错。。");
			}
		});
	 
}
 
 