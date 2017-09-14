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
			
			function initTitle(allTitle, trs) {
				var colCount = 0;
				
				var titles = [];
				// 初始化队列
				titles = titles.concat(allTitle);
				titles.push({"lineEnd":true});
				
				var $tr = $('<tr></tr>');	// 当前的遍历行
				var title = undefined;
				var isFirstLine = true;
				while (true) {
					title = titles.shift();
					if (title == undefined) {
						break;
					}
					
					// 判断是否换行
					if (title.lineEnd) {
						// 当前行遍历完毕	
						trs[trs.length] = $tr;
						
						// 下一行
						var $tr = $('<tr></tr>');
						isFirstLine = false;
						
						continue;
					}
					
					if (isFirstLine) {
						var colSpan = title.colspan == undefined? 1: title.colspan;
						colCount += colSpan * 1;
					}
				
					$tr.append(createTH(title, $tr));
					
					// 子元素入队准备遍历
					if (title.subTitle && title.subTitle.length > 0) {
						titles = titles.concat(title.subTitle);
						titles.push({"lineEnd":true});
					}
				}
				
				return colCount;
			}
			
			function isLeftLineEnd(left) {
				if (left.rowspan == undefined || left.rowspan == 1) {
					return true;
				}
				return false;
			}
			
			// 用td填充tr
			function fullTrs($tr, count) {
				var cellCount = 0;
				// 第一次填充的数目为准，以后所有的填充都是这个数
				if (fullTrs.prototype.cellCount != undefined) {
					cellCount = fullTrs.prototype.cellCount;
				}  else {
					for (var i = 0; i < $tr[0].cells.length; i++) {
						var c = $tr[0].cells[i];
						cellCount += c.colspan == undefined? 1: c.colspan;
					}
					fullTrs.prototype.cellCount = cellCount;
				}			
			
				for (var i = cellCount; i < count; i++) {
					$tr.append('<td></td>');
				}
			}
			
			function initLeft(allLeft, colCount) {
				var trs = [];
				
				var lefts = [];
				// 初始化队列
				lefts = lefts.concat(allLeft.reverse());
				
				var $tr = $('<tr></tr>');	// 当前的遍历行
				var left = undefined;
				var colNum = 0;
				while (true) {
					left = lefts.pop();
					if (left == undefined) {
						break;
					}
				
					$tr.append(createTH(left, $tr));
					
					// 判断是否有子元素
					if (left.subLeft && left.subLeft.length > 0) {	// 子元素入栈准备遍历
						lefts = lefts.concat(left.subLeft.reverse());
					} else {	// 如果没有子元素，则需要换行
						// 补充行剩下的td
						fullTrs($tr, colCount, colNum);
						
						// 当前行遍历完毕	
						trs[trs.length] = $tr;
						
						// 下一行
						var $tr = $('<tr></tr>');
						
						continue;
					}
				}
						
				return trs;
			}
			
			function createTH(o, $tr) {
				// 第一列和第一行需要设置高度
				var style = '';
				if (o.width) {
					style += 'style="';
					style += 'width:' + o.width + 'px;';
					style += '"';
				}
				if (o.height) {
					$tr.css('height', o.height + 'px');
				}
				
				// 单元格合并
				var spans = '';
				if (o.rowspan != undefined && o.rowspan > 1) {
					spans += ' rowspan=' + o.rowspan;
				}
				if (o.colspan != undefined && o.colspan > 1) {
					spans += ' colspan=' + o.colspan;
				}
			
				// 创建单元格
				var $td = $('<th ' + style + '' + spans + ' ><span class="sL4 sL5 cp1">' + o.text +'</span></th>');
				
				return $td;
			}
			
			$.getJSON("${rc.contextPath}/resources/module/form/json/table.json", function(data){ 
				var title = data.title;
				var left = data.left;
				var data = data.data;
				
				var trs = [];
				
				// 广度优先初始化title的tr、th
				var colCount = initTitle(title, trs);
				for (var i = 0; i < trs.length; i++) {
					$('#thead').append(trs[i]);
				}
				
				// 深度优先初始化Left的tr、th、td
				trs = initLeft(left, colCount);
				for (var i = 0; i < trs.length; i++) {
					$('#tbody').append(trs[i]);
				}
				
			});


		});
	</script>
</head>
<body>
	
	<table width="width: auto;" border="0" cellspacing="0" cellpadding="0" class="imagetable">
		<caption><h3>填写表格</h3></caption>
		<thead id="thead">
		</thead>
		<tbody id="tbody">
			
		</tbody>
	</table>
	
	
	<script type="text/javascript" src="${rc.contextPath}/resources/common/js/canvas-nest.min.js"></script>
	<canvas height="926" width="1920" style="position: fixed; top: 0px; left: 0px; z-index: -1; opacity: 0.5;" id="c_n1"></canvas>
</body>
</html>