var TOKEN = "";
var TT = TAOTAO = {
	checkLogin : function() {
		var _ticket = $.cookie("TT_TOKEN");
		if (!_ticket) {
			return;
		}
		$
				.ajax({
					url : "http://localhost:8084/user/token/" + _ticket
							+ "?callback=alert",
					dataType : "jsonp",
					type : "GET",
					success : function(data) {
						if (data.status == 200) {
							TOKEN = _ticket;
							var username = data.data.username;
							var html = username
									+ "，欢迎来到淘淘！"
									+ "<a href=\"javascript:userLogout()\" class=\"link-logout\">[退出]</a>";
							$("#loginbar").html(html);
						}
					}
				});
	}
}
// chung
function userLogout() {
	$.getJSONP("http://localhost:8084/user/logout/" + TOKEN
			+ "?callback=logouted", logouted);
}

function logouted(data) {
	if (data.status == 200) {
		alert("退出成功");
		window.location.reload();
	} else {
		alert("退出失败" + data.msg);
	}
}

$(function() {
	// 查看是否已经登录，如果已经登录查询登录信息
	TT.checkLogin();
});
