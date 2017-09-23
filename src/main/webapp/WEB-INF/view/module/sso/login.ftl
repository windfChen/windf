<!DOCTYPE html>
<html lang="en" class="no-js">
<head>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge"> 
<meta name="viewport" content="width=device-width, initial-scale=1"> 
<title>login</title>

<script type="text/javascript">
	var basePath = '${rc.contextPath}';
	var resourceBasePath = '${rc.contextPath}/resources/';
	var queryString = '${data.queryString}';
	queryString = queryString == ''? '': '?' + queryString;
</script>
<link rel="stylesheet" type="text/css" href="${rc.contextPath}/resources/module/sso/css/normalize.css" />
<link rel="stylesheet" type="text/css" href="${rc.contextPath}/resources/module/sso/css/demo.css" />
<!--必要样式-->
<link rel="stylesheet" type="text/css" href="${rc.contextPath}/resources/module/sso/css/component.css" />
<!--[if IE]>
<script src="js/html5.js"></script>
<![endif]-->

<style>
input:-webkit-autofill {  
	-webkit-box-shadow: 0 0 0 1000px transparent inset !important;
	border: 1px solid #CCC!important;
} 
</style>
</head>
<body>
		<div class="container demo-1">
			<div class="content">
				<div id="large-header" class="large-header">
					<canvas id="demo-canvas"></canvas>
					<div class="logo_box">
						<h3>高教社登录</h3>
						<form action="${rc.contextPath}/login.json" name="f" method="post" autocomplete="off">
							<div class="input_outer">
								<span class="u_user"></span>
								<input name="username" class="text" style="color: #FFFFFF !important" type="text" placeholder="请输入用户名" />
							</div>
							<div class="input_outer">
								<span class="us_uer"></span>
								<input name="password" class="text" style="color: #FFFFFF !important; position:absolute; z-index:100;"value="" type="password" placeholder="请输入密码" autocomplete="off" />
							</div>
							<div class="mb2"><input class="act-but submit" href="javascript:;" style="color: #FFFFFF" type="submit" value="登录"></div>
						</form>
					</div>
				</div>
			</div>
		</div>
		<script src="${rc.contextPath}/resources/module/sso/js/TweenLite.min.js"></script>
		<script src="${rc.contextPath}/resources/module/sso/js/EasePack.min.js"></script>
		<script src="${rc.contextPath}/resources/module/sso/js/rAF.js"></script>
		<script src="${rc.contextPath}/resources/module/sso/js/demo-1.js"></script>
		<script src="${rc.contextPath}/resources/common/plugins/jquery/jquery.js"></script>
		<script src="${rc.contextPath}/resources/common/plugins/jquery/jquery.form.js"></script>
		<script src="${rc.contextPath}/resources/common/js/MD5.js"></script>
		<script src="${rc.contextPath}/resources/module/sso/js/login.js"></script>
	</body>
</html>