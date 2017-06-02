<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<title>模块管理</title>
	<link type="text/css" rel="stylesheet" href="${rc.contextPath}/resources/common/css/base.css" />
	<link type="text/css" rel="stylesheet" href="${rc.contextPath}/resources/common/css/page.css" />
	<link type="text/css" rel="stylesheet" href="${rc.contextPath}/resources/module/development/css/index.css" />
	<script type="text/javascript" src="${rc.contextPath}/resources/common/js/page.js"></script>
	<script type="text/javascript" src="${rc.contextPath}/resources/common/plugins/jquery/jquery.js"></script>
	<script>
		var _basePath = "${rc.contextPath}";
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
							<a href="javascript:void(0);" class="tabl_op_link">
								<i class="iconfont icon_kzkt-teach-stroke"></i>修改基本信息
							</a>
						</li>
						<li class="tabl_op_li">
							<a href="javascript:void(0);" class="tabl_op_link" onclick="removeModule('${m.code}');return false;">
								<i class="iconfont icon_kzkt-penV-stroke"></i>删除
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
		
		function removeModule(code) {
			window.location = _basePath + '/dev/module/remove?code=' + code;
		}
	</script>
</body>
</html>