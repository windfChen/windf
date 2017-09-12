<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<title>添加模块</title>
	<link type="text/css" rel="stylesheet" href="${rc.contextPath}/resources/common/css/base.css" />
	<link type="text/css" rel="stylesheet" href="${rc.contextPath}/resources/module/development/css/create.css" />
	<script type="text/javascript" src="${rc.contextPath}/resources/common/plugins/jquery/jquery.js"></script>
	<script type="text/javascript" src="${rc.contextPath}/resources/common/plugins/lhgdialog/lhgdialog.js?self=true&skin=idialog"></script>
	
	<script>
		var _basePath = "${rc.contextPath}";
	</script>
	
	<script>
		// 投票验证
		function checkSubmit(id){
			var code = $("#code").val();
			if(code==""){
				$.dialog.alert("code不能为空");
				return false;
			}
				
			var name = $("#name").val();
			if(name==""){
				$.dialog.alert("名称不能为空");
				return false;
			}
			
			var basePath = $("#base_path").val();
			if(basePath==""){
				$.dialog.alert("访问路径不能为空");
				return false;
			}
			
			var info = $("#info").val();
			if(info==""){
				$.dialog.alert("描述不能为空");
				return false;
			}
			
			submit();
			
			return false;
		}
		
		function submit() {
			$.ajax({
				url : $('#loginform').attr('action'),
				type: "post",
				data: $('#loginform').serialize(),
				dataType : "json",
				success : function(data) {
					if(data.success == 'Y'){
						$.dialog.alert(data.tip, function (){
							top.window.location = top.window.location;
						});
					} else {
						$.dialog.alert(data.tip);
					}
				}
			});
		}
	</script>
</head>

<body>
	<div class="theme-popbod dform">
		<form class="theme-signin" name="loginform" id="loginform" action="<#if bean?exists>modify<#else>create</#if>" method="post" onSubmit="return checkSubmit();" >
			<ol>
				 <li><h4>&nbsp;</h4></li>
				 <li><strong>code*：</strong><input class="ipt" type="text" name="code" id="code" value="${bean.code}" <#if bean.code?exists>readonly</#if> size="20" /></li>
				 <li><strong>名称*：</strong><input class="ipt" type="text" name="name" id="name" value="${bean.name}" size="20" /></li>
				 <li><strong>访问路径*：</strong><input class="ipt" type="text" name="basePath" value="${bean.basePath}" id="base_path" size="20" /></li>
				 <li><strong>描述*：</strong><input class="ipt" type="text" name="info" id="info" value="${bean.info}" size="20" /></li>
				 <li><input class="btn btn-primary" type="submit" value=" 提 交 " /></li>
			</ol>
		</form>
	</div>
</body>
</html>