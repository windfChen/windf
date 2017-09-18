<!DOCTYPE html>
<html lang="en" class="no-js">
<head>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge"> 
<meta name="viewport" content="width=device-width, initial-scale=1"> 
<title>login</title>
<link rel="stylesheet" type="text/css" href="${rc.contextPath}/resources/module/user/css/normalize.css" />
<link rel="stylesheet" type="text/css" href="${rc.contextPath}/resources/module/user/css/demo.css" />
<!--必要样式-->
<link rel="stylesheet" type="text/css" href="${rc.contextPath}/resources/module/user/css/component.css" />
<!--[if IE]>
<script src="js/html5.js"></script>
<![endif]-->

<style>
	input:-webkit-autofill {  
-webkit-box-shadow: 0 0 0px 1000px white inset;  
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
						<form action="#" name="f" method="post">
							<div class="input_outer">
								<span class="u_user"></span>
								<input name="logname" autocomplete="off" class="text" style="color: #FFFFFF !important" type="text" placeholder="请输入账户">
							</div>
							<div class="input_outer">
								<span class="us_uer"></span>
								<input name="logpass" autocomplete="off" class="text" style="color: #FFFFFF !important; position:absolute; z-index:100;"value="" type="password" placeholder="请输入密码">
							</div>
							<div class="mb2"><a class="act-but submit" href="javascript:;" style="color: #FFFFFF">登录</a></div>
						</form>
					</div>
				</div>
			</div>
		</div>
		<script src="${rc.contextPath}/resources/module/user/js/TweenLite.min.js"></script>
		<script src="${rc.contextPath}/resources/module/user/js/EasePack.min.js"></script>
		<script src="${rc.contextPath}/resources/module/user/js/rAF.js"></script>
		<script src="${rc.contextPath}/resources/module/user/js/demo-1.js"></script>
	</body>
</html>