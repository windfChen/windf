<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<title>My Wall</title> 
	<link type="text/css" rel="stylesheet" href="${rc.contextPath}/resources/model/wall/css/general.css" />
	<link type="text/css" rel="stylesheet" href="${rc.contextPath}/resources/model/wall/css/index.css" />
	<link type="text/css" rel="stylesheet" href="${rc.contextPath}/resources/common/plugins/alert/alert.css" />
	<link type="text/css" rel="stylesheet" href="${rc.contextPath}/resources/model/wall/plugins/dock/dock.css" />
	<script type="text/javascript" src="${rc.contextPath}/resources/common/plugins/jquery/jquery-1.9.0.min.js"></script>
	<script type="text/javascript" src="${rc.contextPath}/resources/common/js/base.js"></script>
	<script type="text/javascript" src="${rc.contextPath}/resources/common/js/string.js"></script>
	<script type="text/javascript" src="${rc.contextPath}/resources/common/js/number.js"></script>
	<script type="text/javascript" src="${rc.contextPath}/resources/common/plugins/alert/alert.js"></script>
	<script type="text/javascript" src="${rc.contextPath}/resources/model/wall/plugins/dock/dock.js"></script>
	<script type="text/javascript">
		<#if Session["current_session_user"]?exists>
			<#assign userName = Session["current_session_user"].username >
		</#if>
		BASE_PATH = '${rc.contextPath}';
		CURRENT_USER_USERNAME = '${userName}';
		CURRENT_SELECT_BLOCK = undefined;
	</script>	
	<script type="text/javascript">	
		$(function(){
			window.w = new Wall(5, 5, 3, 3).bind($('#wall')).start();
		});
	</script>
	<script type="text/javascript">
		function login(pwd, seatStr, callback) {
			getAjax(BASE_PATH + '/wall/login', {'p' :pwd, 's':seatStr}, function (loginId){
				obj.owner = new User(loginId);
				
				// 修改当前用户
				CURRENT_USER_USERNAME = loginId;
				ShowDock();
		   
				var div = $("#" + obj.getId());
				div.html('<div class="owner"><span class="username" >我</span></div>');
			});
		}
		function register(name, pwd, seatStr) {
			getAjax(BASE_PATH + '/wall/register', {'u': name, 'p' :pwd, 's':seatStr}, function (loginId){
			    obj.owner = new User(loginId);
			   
			    // 修改当前用户
			    CURRENT_USER_USERNAME = loginId;
			    ShowDock();
			   
			    w.show(obj);
			});
		}
		
		function getAjax(url, param, callback) {
			$.ajax({
				   async:false,
				   url: url,
				   type: "post",
				   dataType: 'json',
				   data: param,
				   timeout: 5000,
				   success: function (json) {//客户端jquery预先定义好的callback函数,成功获取跨域服务器上的json数据后,会动态执行这个callback函数
						if (json.success === 'Y') {
							if (callback) {
								call(callback, json.data);
							}
							return json.data;
						} else {
							alert(json.tip);
						}
					  
				   },
				   error: function(xhr){
					   alert("请求出错");
				   }
			});
			
		}
	</script>
</head>
<body>
	<header></header>
	<section id="content">
		<div id="wall"></div>
	</section>
	
	<div class="dock" id="dock">
		<div>
			<span id='gongji'><img alt="攻击" title="攻击" src="${rc.contextPath}/resources/model/wall/plugins/dock/images/gongji.png" src_c="${rc.contextPath}/resources/model/wall/plugins/dock/images/gongji_c.png" /></span>
			<span id='fangyu'><img alt="防御" title="防御" src="${rc.contextPath}/resources/model/wall/plugins/dock/images/fangyu.png" src_c="${rc.contextPath}/resources/model/wall/plugins/dock/images/fangyu_c.png" /></span>
			<span id='liuyan'><img alt="留言" title="留言" src="${rc.contextPath}/resources/model/wall/plugins/dock/images/liuyan.png" src_c="${rc.contextPath}/resources/model/wall/plugins/dock/images/liuyan_c.png" /></span>
			<span id='shezhi'><img alt="设置" title="设置" src="${rc.contextPath}/resources/model/wall/plugins/dock/images/shezhi.png" src_c="${rc.contextPath}/resources/model/wall/plugins/dock/images/shezhi_c.png" /></span>
		</div>
	</div>
	
	<footer style="display:none;">
		<a href='http://www.miitbeian.gov.cn/'>京ICP备15008123号-1</a>
	</footer>
	
	
	<script type="text/javascript" src="${rc.contextPath}/resources/model/wall/js/index/Seat.js" ></script>
	<script type="text/javascript" src="${rc.contextPath}/resources/model/wall/js/index/User.js" ></script>
	<script type="text/javascript" src="${rc.contextPath}/resources/model/wall/js/index/Block.js" ></script>
	<script type="text/javascript" src="${rc.contextPath}/resources/model/wall/js/index/Wall.js" ></script>
</body>
</html>