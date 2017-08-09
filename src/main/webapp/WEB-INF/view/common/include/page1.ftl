<#macro page currentIndex indexTotal indexCount >
		
	<#assign startIndex = 1 >
	<#assign endIndex = indexTotal >
	<#assign frontMore=false >
	<#assign nextMore=false >
	<#-- 总页数大于要显示的页数 -->
	<#if indexTotal gt indexCount >
		<#-- 初始赋值 -->
		<#assign startIndex = currentIndex - indexCount / 2 + 1 >
		<#assign endIndex = currentIndex + indexCount / 2 >
		
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
		<a class="pagin-link" href="javascript:void(0);" onclick="pageJump(${currentIndex - 1}, ${currentIndex}, ${indexTotal});">上一页</a>

		<#if frontMore >
			<a class="pagin-link" href="javascript:void(0);" onclick="pageJump(1, ${currentIndex}, ${indexTotal});">1</a>
			<span class="pagin-ell">...</span>
		</#if>
		<#assign pagesIndex=startIndex..endIndex/>
		<#list pagesIndex as index> 
			<#if index==currentIndex> 
				<a class="pagin-link pagin-cur" href="javascript:void(0);" onclick="pageJump(${index}, ${currentIndex}, ${indexTotal});">${index}</a>
			<#else> 
				<a class="pagin-link" href="javascript:void(0);" onclick="pageJump(${index}, ${currentIndex}, ${indexTotal});">${index}</a>
			</#if> 
		</#list> 
		
		<#if nextMore >
			<span class="pagin-ell">...</span>
			<a class="pagin-link" href="javascript:void(0);" onclick="pageJump(${indexTotal}, ${currentIndex}, ${indexTotal});">${indexTotal}</a>
		</#if>
		
		<a class="pagin-link" href="javascript:void(0);" onclick="pageJump(${currentIndex + 1}, ${currentIndex}, ${indexTotal});">下一页</a>
		
		<span class="pagin-txt">到</span>
		<span class="pagin-jump">
			<input class="pagin-input" onfocus="pageJumpInputFocus(this);" onblur="pageJumpInputBlur(this);" onkeyup="pageJumpInputKeyUp(this);" id="page_jump_input" type="text">
			<a class="pagin-btn" id="pagin_jump_btn" onclick="pageJump(document.getElementById('page_jump_input').value, ${currentIndex}, ${indexTotal});" href="javascript:void(0);">确定</a>
		</span>
		<span class="pagin-txt">页</span>
	</div>
</#macro>