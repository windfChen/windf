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
	
	<script>
		var _basePath = "${rc.contextPath}";
		var _modulePath = "${modulePath}";
	</script>
</head>
<body>
	<h3 class="subjT">模块管理</h3>
	<table width="100%" border="0" cellspacing="0" cellpadding="0"
		class="sResultsTb">

		<thead>
			<tr>
				<th bgcolor="#f9f9f9"><span class="sL4 sL5 cp1">code</span></th>
				<th bgcolor="#f9f9f9"><span class="sL4 sL5 cp1">名称</span></th>
				<th bgcolor="#f9f9f9"><span class="sL4 sL5">访问路径</span></th>
				<th bgcolor="#f9f9f9"><span class="sL4 sL5 cp1">描述</span></th>
				<th bgcolor="#f9f9f9"><span class="sL4 sL5">操作</span></th>
			</tr>
		</thead>
		<tbody>
			<#list page.data as m>
			<tr>
				<td>${m.code}</td>
				<td>${m.name}</td>
				<td>${m.basePath}</td>
				<td>${m.info}</td>
				<td>
					<ul class="clearfix">
						<li class="tabl_op_li">
							<a href="javascript:void(0);" class="tabl_op_link" onclick="minwindown('修改模块', 'create?code=${m.code}', '',{height:'300px'});;return false;">
								<i class="iconfont icon_kzkt-teach-stroke"></i>修改基本信息
							</a>
						</li>
						<li class="tabl_op_li">
							<a href="javascript:void(0);" class="tabl_op_link">
								<i class="iconfont icon_kzkt-penV-stroke"></i>详细信息
							</a>
						</li>
					</ul>
				</td>
			</tr>
			</#list>
		</tbody>
	</table>
	
	<#import "/common/include/page1.ftl" as p>
	<@p.page currentIndex = page.pageIndex indexTotal = page.totalPage indexCount = 8 />
	
	<script>
		function onPageJump(pageIndex) {
			window.location = '?pageIndex=' + pageIndex + '&pageSize=1';
		}
	</script>
	
	<div class="buttons">
		<button class="add_button" onclick="minwindown('添加模块', 'create', '',{height:'300px'});" type="submit">
			<span>添加</span>
		</button>
	</div>
	
	<script type="text/javascript" src="${rc.contextPath}/resources/common/js/canvas-nest.min.js"></script>
	<canvas height="926" width="1920" style="position: fixed; top: 0px; left: 0px; z-index: -1; opacity: 0.5;" id="c_n1"></canvas>
</body>
</html>