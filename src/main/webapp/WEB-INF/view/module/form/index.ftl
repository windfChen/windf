<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<title>填写表格</title>
	<link type="text/css" rel="stylesheet" href="${rc.contextPath}/resources/common/css/base.css" />
	<link type="text/css" rel="stylesheet" href="${rc.contextPath}/resources/common/css/page.css" />
	<link type="text/css" rel="stylesheet" href="${rc.contextPath}/resources/module/form/css/table.css" />
	<script type="text/javascript" src="${rc.contextPath}/resources/common/plugins/jquery/jquery.js"></script>
	<script type="text/javascript" src="${rc.contextPath}/resources/module/form/js/table.js"></script>
	
	<script>
		var _basePath = "${rc.contextPath}";
	</script>
	
	<script>
		$(function(){
			var t = new Table('table', '${data.id}');
			t.init(${data.gridView});
			t.display();
			t.initUserData('user/value.json');
			table = t;
			
			$('#submit').click(function(){
				t.submitData('saveUserValue.json');
			});
			
		});
	</script>
</head>
<body>
	
	
	<table id="table" width="width: auto;" border="0" cellspacing="0" cellpadding="0" class="imagetable">
	</table>
	
	
	<div class="btns" ><input type="button" id="submit" class="btn" value="提交" /></div>
	
	<script type="text/javascript" src="${rc.contextPath}/resources/common/js/canvas-nest.min.js"></script>
	<canvas height="926" width="1920" style="position: fixed; top: 0px; left: 0px; z-index: -1; opacity: 0.5;" id="c_n1"></canvas>
</body>
</html>