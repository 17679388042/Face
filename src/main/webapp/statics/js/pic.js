// demo 程序将粘贴事件绑定到 document 上
document.addEventListener("paste", function(e) {
	var cbd = e.clipboardData;
	var ua = window.navigator.userAgent;

	// 如果是 Safari 直接 return
	if (!(e.clipboardData && e.clipboardData.items)) {
		return;
	}

	// Mac平台下Chrome49版本以下 复制Finder中的文件的Bug Hack掉
	if (cbd.items && cbd.items.length === 2 && cbd.items[0].kind === "string"
			&& cbd.items[1].kind === "file" && cbd.types
			&& cbd.types.length === 2 && cbd.types[0] === "text/plain"
			&& cbd.types[1] === "Files" && ua.match(/Macintosh/i)
			&& Number(ua.match(/Chrome\/(\d{2})/i)[1]) < 49) {
		return;
	}

	for (var i = 0; i < cbd.items.length; i++) {
		var item = cbd.items[i];
		if (item.kind == "file") {
			var blob = item.getAsFile();
			if (blob.size === 0) {
				return;
			}

			var reader = new FileReader();
			reader.onload = function(eve) {
				// 判断文件是否读取完成
				if (eve.target.readyState == FileReader.DONE) {
					// 读取文件完成后，创建img标签来显示服务端传来的字节数//组
					var img = document.createElement("img");
					
					// 读取文件完成后内容放在this===当前
					// fileReader对象的result属性中，将该内容赋值img标签//浏览器就可以自动解析内容格式并且渲染在浏览器中
					img.src = this.result;
					// 将标签添加到id为show的div中否则，即便是有img也看不见
					document.body.appendChild(img);
					var deliveryInfoDiv = document
							.getElementById("deliveryInfo");
					deliveryInfoDiv.appendChild(img);
				}
			};
			reader.readAsDataURL(blob);

		}
	}
}, false);