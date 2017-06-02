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
							<a href="javascript:void(0);" class="tabl_op_link">
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
	
	<#assign currentIndex = page.pageIndex >
	<#assign indexCount = 8 >
	<#assign indexTotal = page.totalPage >
	
	<#assign startIndex = 1 >
	<#assign endIndex = indexTotal >
	<#assign frontMore=false >
	<#assign nextMore=false >
	<#-- 总页数大于要显示的页数 -->
	<#if indexTotal gt indexCount >
		<#-- 初始赋值 -->
		<#assign startIndex=currentIndex - indexCount/2 + 1 >
		<#assign endIndex=currentIndex + indexCount/2 >
		
		<#-- 首尾超出后移动 -->
		<#if startIndex lt 1>
			<#assign endIndex = endIndex + (1 - startIndex) >
			<#assign startIndex = 1 >
		</#if>
		<#if endIndex gt indexTotal>
			<#assign startIndex = startIndex + (indexTotal - endIndex) >
			<#assign endIndex = indexTotal >
		</#if>
		
		<#-- 首尾不够的情况下，显示... -->
		<#if startIndex gt 1>
			<#assign startIndex = startIndex + 1 >
			<#assign frontMore=true >
		</#if>
		<#if endIndex lt indexTotal>
			<#assign endIndex = endIndex - 1 >
			<#assign nextMore=true >
		</#if>
		
	</#if>
	
	<div class="pagin pt20 tr">
		<a class="pagin-link" href="javascript:void(0);" onclick="pageJump(${currentIndex - 1});">上一页</a>

		<#if frontMore >
			<a class="pagin-link" href="javascript:void(0);" onclick="pageJump(1);">1</a>
			<span class="pagin-ell">...</span>
		</#if>
		<#assign pagesIndex=startIndex..endIndex/>
		<#list pagesIndex as index> 
			<#if index==currentIndex> 
				<a class="pagin-link pagin-cur" href="javascript:void(0);" onclick="pageJump(${index});">${index}</a>
			<#else> 
				<a class="pagin-link" href="javascript:void(0);" onclick="pageJump(${index});">${index}</a>
			</#if> 
		</#list> 
		
		<#if nextMore >
			<span class="pagin-ell">...</span>
			<a class="pagin-link" href="javascript:void(0);" onclick="pageJump(${indexTotal});">${indexTotal}</a>
		</#if>
		
		<a class="pagin-link" href="javascript:void(0);" onclick="pageJump(${currentIndex + 1});">下一页</a>
		
		<span class="pagin-txt">到</span>
		<span class="pagin-jump">
			<input class="pagin-input" onfocus="pageJumpInputFocus(this);" onblur="pageJumpInputBlur(this);" onkeyup="pageJumpInputKeyUp(this);" id="page_jump_input" type="text">
			<a class="pagin-btn" id="pagin_jump_btn" onclick="pageJump(document.getElementById('page_jump_input').value);" href="javascript:void(0);">确定</a>
		</span>
		<span class="pagin-txt">页</span>
	</div>
	
	<script>
		function pageJumpInputFocus(obj) {
			$(obj).parent().addClass('pagin-jump-focus').removeClass('pagin-jump');
		}
		function pageJumpInputBlur(obj) {
			if ($(obj).val().length == 0) {
				$(obj).parent().addClass('pagin-jump').removeClass('pagin-jump-focus');
			}
		}
		function pageJumpInputKeyUp(obj) {
			if (event.keyCode == "13") {
				$('#pagin_jump_btn').click();
			}
		}
		function pageJump(pageIndex, currentIndex, indexTotal) {
			if (!currentIndex) {
				currentIndex = ${currentIndex};
			}
			if (!indexTotal) {
				indexTotal = ${indexTotal};
			}
			if (!pageIndex) {
				return false;
			}
			if (pageIndex < 1) {
				pageIndex = 1;
			}
			if (pageIndex > indexTotal) {
				pageIndex = indexTotal;
			}
			
			if (currentIndex == pageIndex) {
				return false;
			}
			
			onPageJump(pageIndex)
		}
	
		function onPageJump(pageIndex) {
			window.location = '?pageIndex=' + pageIndex + '&pageSize=1'
		}
	</script>
</body>
</html>