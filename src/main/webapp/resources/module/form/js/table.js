var Table = function (formId, url) {
	this.formId = formId;
	this.url = url;
	this.tableData = {};	// 接收到的总数据
	
	this.headTrs = [];	// 标题行
	this.bodyTrs = [];	// 内容行
	this.footTr = null;	// 底部行
	
	this.colCount = 0;	// 总列数
	this.rowCount = 0; 	// 总行数
}
Table.prototype = {
		
	init : function () {
		var obj = this;
		
		$.ajax({
			async:false,
			url: this.url,
			type: "GET",
			dataType: 'json',
			success: function (data) {
				obj.tableData = data;
				
				// 广度优先初始化title的tr、th
				var trs = obj.initTitle();
				obj.colCount = obj.getTrCellCount(trs[0]);
				obj.headTrs = trs;
				
				// 深度优先初始化Left的tr、th、td
				trs = obj.initLeft();
				obj.bodyTrs = trs;
				
				//初始化tfoot
				obj.footTr = obj.initFoot();
			}
		});
		
		return this;
	},
	
	/*
	 * 显示
	 */
	display : function () {
		var $form = $('#' + this.formId);
		$form.append('<caption></caption><thead></thead><tbody></tbody><tfoot></tfoot>');
		
		// 显示title
		$form.find('caption').text(this.tableData.info.title);
		
		// 显示thead
		for (var i = 0; i < this.headTrs.length; i++) {
			$form.find('thead').append(this.headTrs[i]);
		}
	 
		// 显示tbody
		for (var i = 0; i < this.bodyTrs.length; i++) {
			$form.find('tbody').append(this.bodyTrs[i]);
		}
		
		// 显示底部
		if (this.footTr != null) {
			$form.find('tfoot').append(this.footTr);
		}
		
	},
	
	/*
	 * 初始化title
	 */
	initTitle : function () {
		var allTitle = this.tableData.title;
		
		var colCount = 0;
		var trs = []
		
		var titles = [];
		// 初始化队列
		titles = titles.concat(allTitle);
		titles[titles.length - 1].lineEnd = true;
		
		var $tr = $('<tr></tr>');	// 当前的遍历行
		var title = undefined;
		var isFirstLine = true;
		while (true) {
			title = titles.shift();
			if (title == undefined) {
				break;
			}
			
			if (isFirstLine) {
				var colSpan = title.colspan == undefined? 1: title.colspan;
				colCount += colSpan * 1;
			}
		
			$tr.append(this.createTH(title, $tr));
			
			// 子元素入队准备遍历
			if (title.subTitle && title.subTitle.length > 0) {
				titles = titles.concat(title.subTitle);
				if (title.lineEnd) {
					titles[titles.length - 1].lineEnd = true;
				}
			}
			
			// 判断是否换行
			if (title.lineEnd) {
				// 当前行遍历完毕	
				trs[trs.length] = $tr;
				
				// 下一行
				var $tr = $('<tr></tr>');
				isFirstLine = false;
			}
		}
		
		return trs;
	},
	
	/*
	 * 初始化left
	 */
	initLeft : function () {
		var allLeft = this.tableData.left;
		var colCount = this.colCount;
		
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
		
			$tr.append(this.createTH(left, $tr));
			
			// 判断是否有子元素
			if (left.subLeft && left.subLeft.length > 0) {	// 子元素入栈准备遍历
				lefts = lefts.concat(left.subLeft.reverse());
			} else {	// 如果没有子元素，则需要换行
				// 补充行剩下的td
				this.fullTrs($tr, colCount, colNum);
				
				// 当前行遍历完毕	
				trs[trs.length] = $tr;
				
				// 下一行
				var $tr = $('<tr></tr>');
				
				continue;
			}
		}
				
		return trs;
	},
	
	/* 
	 * 初始化foot
	 */
	initFoot : function() {
		var $tr = null;
		var footInfo = this.tableData.info.foot;
		if (footInfo) {
			$tr = $('<tr><td colspan="' + this.colCount + '">' + footInfo + '</td></tr>');
		}
		return $tr;
	},
	
		
	/*
	 * 创建Th
	 */
	createTH : function (o, $tr) {
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
		var $td = $('<th ' + style + '' + spans + ' >' + o.text +'</th>');
		
		return $td;
	},
		
	/*
	 * 用td填充tr
	 */
	fullTrs : function ($tr, count) {
		var cellCount = 0;
		// 第一次填充的数目为准，以后所有的填充都是这个数
		if (this.cellCount != undefined) {
			cellCount = this.cellCount;
		}  else {
			cellCount = this.getTrCellCount($tr);
			this.cellCount = cellCount;
		}			

		for (var i = cellCount; i < count; i++) {
			$tr.append('<td></td>');
		}
	},
	
	getTrCellCount : function ($tr){
		var count = 0;
		for (var i = 0; i < $tr[0].cells.length; i++) {
			var c = $tr[0].cells[i];
			count += c.colSpan == undefined? 1: c.colSpan;
		}
		return count;
	}



}