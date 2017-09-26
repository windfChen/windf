<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<title>后台管理</title>
	<script type="text/javascript">
		var basePath = '${rc.contextPath}';
		var resourceBasePath = '${rc.contextPath}/resources/';
		var queryString = '${data.queryString}';
		queryString = queryString == ''? '': '?' + queryString;
	</script>
	<link rel="stylesheet" type="text/css" href="${rc.contextPath}/resources/module/index/css/admincss.css" />
	<link rel="stylesheet" type="text/css" href="${rc.contextPath}/resources/common/plugins/extjs/css/ext-all.css" />
	<link rel="stylesheet" type="text/css" href="${rc.contextPath}/resources/common/plugins/extjs/css/ext-ext.css" />
	<link rel="stylesheet" type="text/css" href="${rc.contextPath}/resources/common/plugins/extjs/css/tree.css" />
	<link rel="stylesheet" type="text/css" href="${rc.contextPath}/resources/common/plugins/extjs/examples/Datetime/datetime.css" />
	
	<script type="text/javascript" src="${rc.contextPath}/resources/common/plugins/extjs/pub/adapter/ext/ext-base.js"></script>
	<script type="text/javascript" src="${rc.contextPath}/resources/common/plugins/extjs/pub/ext-all.js"></script>
	<script type="text/javascript" src="${rc.contextPath}/resources/common/plugins/extjs/examples/Datetime/Datetime.js"></script>
	<script type="text/javascript" src="${rc.contextPath}/resources/common/plugins/extjs/pub/ext-lang-zh_CN.js"></script> 
	<script type="text/javascript" src="${rc.contextPath}/resources/module/index/js/tree.js"></script>
	<script type="text/javascript" src="${rc.contextPath}/resources/module/index/js/frame.js"></script>
	<script type="text/javascript" src="${rc.contextPath}/resources/common/plugins/jquery/jquery.js"></script>
	<script type="text/javascript" src="${rc.contextPath}/resources/common/plugins/jquery/jquery-migrate-1.1.0.js"></script>
</head>
<body>
	<div id="north">
		<table width="100%" height="34" border="0" cellspacing="0" cellpadding="0" style="color:#fff" > 	
			<tr>
				<td height="34" width="100%" class="x-topBody">
					<table width="100%"  border="0" cellspacing="0" cellpadding="0" >
						<tr>
							<td width="25%" class="topBarCenter" id="topTimeBox" style="padding-left: 20px;padding-top: 3px" >
								<%=UserName%> 欢迎您进入后台管理！
							</td>
							<td width="50%" class="topBarCenter" style="padding-left: 20px;padding-top: 3px" >
								当前在线人数：<span id ="onlineNum" ></span>&nbsp;&nbsp;累计学习时长：<span id ="studyLong" style="margin-right:100px;" ></span>累计登录人次：<span id ="loginNum" ></span>
							</td>
							<td width="25%" class="topBarCenter" style="text-align: right;padding-right: 20px;"> 
								<a href="javascript:void(0);" onclick="logout()" >注销退出</a>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
  	</div>
</body>
</html>