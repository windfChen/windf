<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<title>模块管理</title>
	<link type="text/css" rel="stylesheet" href="${rc.contextPath}/resources/common/css/base.css" />
	<link type="text/css" rel="stylesheet" href="${rc.contextPath}/resources/common/css/page.css" />
	<link type="text/css" rel="stylesheet" href="${rc.contextPath}/resources/common/css/minwindown.css" />
	<link type="text/css" rel="stylesheet" href="${rc.contextPath}/resources/module/development/css/index.css" />
	<script type="text/javascript" src="${rc.contextPath}/resources/common/plugins/jquery/jquery.js"></script>
	<script type="text/javascript" src="${rc.contextPath}/resources/common/js/page.js"></script>
	<script type="text/javascript" src="${rc.contextPath}/resources/common/js/minwindown.js" charset="utf-8"></script>
	
	<script type="text/javascript" src="${rc.contextPath}/resources/module/development/js/url.js" charset="utf-8"></script>
	
	<script>
		var _basePath = "${rc.contextPath}";
		
		var moduleCode = "${module}example";
		
		$(function(){
			list(moduleCode);
		})
	</script>
</head>
<body>
	<h3 class="subjT">URL管理</h3>
	<table id="data" width="100%" border="0" cellspacing="0" cellpadding="0" class="sResultsTb">

		<thead>
			<tr>
				<th bgcolor="#f9f9f9"><span class="sL4 sL5 cp1">控制器</span></th>
				<th bgcolor="#f9f9f9"><span class="sL4 sL5 cp1">路径</span></th>
				<th bgcolor="#f9f9f9"><span class="sL4 sL5 cp1">请求方式</span></th>
				<th bgcolor="#f9f9f9"><span class="sL4 sL5 cp1">ajax</span></th>
				<th bgcolor="#f9f9f9"><span class="sL4 sL5">操作</span></th>
			</tr>
		</thead>
		<tbody>
			
		</tbody>
	</table>
	
	<div class="buttons">
		<button class="add_button" onclick="minwindown('添加URL', 'create', '',{height:'300px'});" type="submit">
			<span>添加</span>
		</button>
	</div>
	
	<script type="text/javascript" src="${rc.contextPath}/resources/common/js/canvas-nest.min.js"></script>
	<canvas height="926" width="1920" style="position: fixed; top: 0px; left: 0px; z-index: -1; opacity: 0.5;" id="c_n1"></canvas>
</body>
</html>