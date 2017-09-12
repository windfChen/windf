<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<title>填写表格</title>
	<link type="text/css" rel="stylesheet" href="${rc.contextPath}/resources/common/css/base.css" />
	<link type="text/css" rel="stylesheet" href="${rc.contextPath}/resources/common/css/page.css" />
	<link type="text/css" rel="stylesheet" href="${rc.contextPath}/resources/module/form/css/table.css" />
	<script type="text/javascript" src="${rc.contextPath}/resources/common/plugins/jquery/jquery.js"></script>
	<link type="text/css" rel="stylesheet" href="${rc.contextPath}/resources/module/form/js/table.js" />
	
	<script>
		var _basePath = "${rc.contextPath}";
	</script>
	
	<script>
		$(function(){
			
			$.getJSON("${rc.contextPath}/resources/module/form/json/table.json", function(data){ 
				var title = data.title;
				var left = data.left;
				var grids = data.grids;
				
				var $tr = $('<tr></tr>');
				for (var i = 0; i < title.length; i++) {
					var t = title[i];
					var $td = $('<th><span class="sL4 sL5 cp1">' + t.text +'</span></th>');
					$tr.append($td);
				}
				$('#thead').append($tr);
				
				for (var i = 0; i < left.length; i++) {
					var t = left[i];
					var $tr = $('<tr></tr>');
					for (var j = 0; j < title.length; j++) {
						console.log(j)
						var $td = null;
						if (j == 0) {
							$td = $('<td>' + t.text + '</td>');
						} else {
							$td = $('<td>&nbsp;</td>');
						}
						$tr.append($td);
					}
					$('#tbody').append($tr);
				}
				
			});


		});
	</script>
</head>
<body>
	<h3 class="subjT">填写表格</h3>
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="sResultsTb">

		<thead id="thead">
		</thead>
		<tbody id="tbody">
			
		</tbody>
	</table>
	
	
	<script type="text/javascript" src="${rc.contextPath}/resources/common/js/canvas-nest.min.js"></script>
	<canvas height="926" width="1920" style="position: fixed; top: 0px; left: 0px; z-index: -1; opacity: 0.5;" id="c_n1"></canvas>
</body>
</html>