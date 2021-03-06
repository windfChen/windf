var Table = function (divId, formId) {
	this.divId = divId;
	this.formId = formId;

	this.tableData = {};	// 接收到的总数据
	
	this.headTr = null;	// 标题行
	this.bodyTrs = [];	// 内容行
	this.footTr = null;	// 底部行
	
	this.colCount = 0;	// 总列数
	this.rowCount = 0; 	// 总行数
	
	this.titleLeafNode = [];	// title的叶子节点
	this.leftLeafNode = [];		// left的叶子节点
}
Table.prototype = {
		
	initByUrl : function (url) {
		var obj = this;
		
		$.ajax({
			async:false,
			url: url,
			type: "GET",
			dataType: 'json',
			success: function (data) {
				obj.init(data);
			}
		});
		
		return this;
	},

	/*
	 * 初始化
	 */
	init : function(data) {
		this.tableData = data;

		// 深度优先，依次获得title和left的叶子节点
		if (this.tableData.title.length > 0 && this.tableData.left.length > 0) {
			this.titleLeafNode = this.getLeafNodes(this.tableData.title);
			this.leftLeafNode = this.getLeafNodes(this.tableData.left);	
			
			// 广度优先初始化title的tr、th
			var trs = this.initTitle();
			this.bodyTrs = this.bodyTrs.concat(trs);
			this.colCount = this.getTrCellCount(trs[0]);
			
			// 深度优先初始化Left的tr、th、td
			trs = this.initLeft();
			this.bodyTrs = this.bodyTrs.concat(trs);
		}
		
		if (this.tableData.data && this.tableData.data.length > 0) {
			this.bodyTrs = this.initData(this.tableData.titleCount, this.tableData.data);
		}
		
		
		//初始化tfoot
		this.footTr = this.initFoot();
	},
	
	/*
	 * 显示
	 */
	display : function () {
		var $form = $('#' + this.divId);
		$form.append('<caption></caption><thead></thead><tbody></tbody><tfoot></tfoot>');
		
		// 显示title
		$form.find('caption').text(this.tableData.info.title);
		
		// 显示thead
		$form.find('thead').append(this.headTr);
	 
		// 显示tbody
		for (var i = 0; i < this.bodyTrs.length; i++) {
			$form.find('tbody').append(this.bodyTrs[i]);
		}
		
		// 显示底部
		if (this.footTr != null) {
			$form.find('tfoot').append(this.footTr);
		}
		
		// 输入框设置宽度
		$form.find('tbody td input').each(function(){
			var w = $(this).parent().css('width');
			w = w.replace('px', '') - 20 + "px";
			$(this).css('width', w);
		});
		$form.find('tbody td textarea').each(function(){
			var w = $(this).parent().css('width');
			var h = $(this).parent().css('height');
			w = w.replace('px', '') - 20 + "px";
			h = h.replace('px', '') - 10 + "px";
			$(this).css('width', w);
			$(this).css('height', h);
		});
		
	},
	
	/*
	 * getUserData
	 */
	submitData : function(url) {
		var $form = $('#' + this.divId);
		
		var data = {};
		$form.find('input,textarea').each(function(input) {
			var name = $(this).attr('name');
			var value = $(this).val();
			
			// 验证空 TODO目前，全部不能为空
			/*
			if (value.trim() == '') {
				alert('存在空数据');
				$(this).focus();
				return false;
			}*/
			
			data['data.' + name + ''] = value;
		});
		data['formId'] = this.formId;
		
		$.ajax({
			async:false,
			url: url,
			data : data,
			type: "POST",
			dataType: 'json',
			success: function (data) {
				if(data.success == 'Y') {
					alert('提交成功');
				} else {
					alert(data.message);
				}
			}
		});
	},

	initUserData : function(url) {
		var obj = this;
		$.ajax({
			async:true,
			url: url,
			data : {'formId': this.formId},
			type: "GET",
			dataType: 'json',
			success: function (data) {
				obj.setDate(data.data)
			}
		});
	},

	setDate : function(data) {
		var $form = $('#' + this.divId);
		for (var i = 0; i < data.length; i++) {
			var d = data[i];

			$form.find('input[name=' + d.formItem.code + ']').val(d.value);
			$form.find('textarea[name=' + d.formItem.code + ']').val(d.value);

		}
	},
	
	/*
	 * 初始化title
	 */
	initTitle : function () {
		var trs = [];
		
		var obj = this;
		/*
		 * 递归初始化方法
		 */
		var _initTitle = function (title, rowNum) {
					
			// 添加元素到列中
			if (trs.length <= rowNum || trs[rowNum] == undefined) {
				trs[rowNum] = $('<tr></tr>');
			}
			trs[rowNum].append(obj.createTH(title, trs[rowNum]));
			
			// 如果存在子元素，依次遍历
			if (title.subData && title.subData.length > 0) {
				// 下一行，为本行加上行合并单元格数
				rowNum += title.rowSpan == undefined? 1: title.rowSpan;
				
				// 依次递归遍历
				for (var i = 0; i < title.subData.length; i++) {
					arguments.callee(title.subData[i], rowNum, trs);
				}
			}
		}
			
		var allTitle = this.tableData.title;
			
		// 递归初始化
		for (var i = 0; i < allTitle.length; i++) {
			_initTitle(allTitle[i], 0);
		}
		
		return trs;
	},
	
	/*
	 * 初始化left
	 */
	initLeft : function () {
		var allLeft = this.tableData.left;
		
		var trs = [];
		
		var lefts = [];
		// 初始化队列
		lefts = lefts.concat(allLeft).reverse();
		
		var $tr = $('<tr></tr>');	// 当前的遍历行
		var left = undefined;
		var dataRowNum = 0;
		while (true) {
			left = lefts.pop();
			if (left == undefined) {
				break;
			}
		
			$tr.append(this.createTH(left, $tr));
			
			// 判断是否有子元素
			if (left.subData && left.subData.length > 0) {	// 子元素入栈准备遍历
				var subData = [];
				subData = subData.concat(left.subData);
				lefts = lefts.concat(subData.reverse());
			} else {	// 如果没有子元素，则需要换行
				// 补充行剩下的td
				this.fullTrs($tr, dataRowNum++);
				
				// 当前行遍历完毕	
				trs[trs.length] = $tr;
				
				// 下一行
				var $tr = $('<tr></tr>');
				
				continue;
			}
		}
				
		return trs;
	},
	
	initData : function (titleCount, data) {
		var trs = [];
		var $tr = null;
		var count = 0;
		for (var i = 0 ; i < data.length; i++) {
			
			if (count % titleCount == 0) {
				$tr = $('<tr></tr>');
				trs[trs.length] = $tr;
			}
			
			var d = data[i];
			
			var $td = $('<td></td>');
			if (d.type == 'label') {
				$td = $('<th></th>');
				$td.append(d.text);
			} else if (d.type == 'input') {
				$td.append('<input name="' + d.code + '" type="text" />');
			} else if (d.type == 'texarea') {
				$td.append('<textarea  name="' + d.code + '" ></textarea >');
			}
			
			if (d.colSpan && d.colSpan > 1) {
				$td.attr('colSpan', d.colSpan);
				count += d.colSpan;
			} else {
				count++;
			}
			if (d.rowSpan && d.rowSpan > 1) {
				$td.attr('rowSpan', d.rowSpan);
			}
			
			if (d.width) {
				$td.css('width', d.width + 'px');
			}
			if (d.height) {
				$td.css('height', d.height + 'px');
			}
			
			$tr.append($td);
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
			$tr = $('<tr><td colSpan="' + this.colCount + '">' + footInfo + '</td></tr>');
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
		if (o.rowSpan != undefined && o.rowSpan > 1) {
			spans += ' rowSpan=' + o.rowSpan;
		}
		if (o.colSpan != undefined && o.colSpan > 1) {
			spans += ' colSpan=' + o.colSpan;
		}

		// 创建单元格
		var $td = $('<th ' + style + '' + spans + ' >' + o.text +'</th>');
		
		return $td;
	},
		
	/*
	 * 用td填充tr
	 */
	fullTrs : function ($tr, dataRowNum) {
		var cellCount = 0;
		// 第一次填充的数目为准，以后所有的填充都是这个数
		if (this.cellCount != undefined) {
			cellCount = this.cellCount;
		} else {
			cellCount = this.getTrCellCount($tr);
			this.cellCount = cellCount;
		}

		for (var i = cellCount; i < this.colCount; i++) {
			$tr.append('<td><input name="' + this.leftLeafNode[dataRowNum].code + '-' + this.titleLeafNode[i - 1].code + '" /></td>');
		}
	},
	
	/*
	 * 深度优先，依次获得title的叶子节点
	 */
	getLeafNodes : function(allDatas) {
		var leafNodes = [];
		
		// 初始化队列
		var datas = [];
		var datas = datas.concat(allDatas).reverse();
		
		var data = undefined;
		var colNum = 0;
		while (true) {
			data = datas.pop();
			if (data == undefined) {
				break;
			}
		
			// 判断是否有子元素
			if (data.subData && data.subData.length > 0) {	// 子元素入栈准备遍历
				var subData = [];
				subData = subData.concat(data.subData);
				datas = datas.concat(subData.reverse());
			} else {	// 如果没有子元素，则需要换行
				var colSpan = data.colSpan == undefined? 1: data.colSpan;
				for (var i = 0; i < colSpan; i++) {
					leafNodes[leafNodes.length] = data;
				}
			}
		}
		
		return leafNodes;
	},
	
	/*
	 * 获得tr内的td总数
	 */
	getTrCellCount : function ($tr){
		var count = 0;
		for (var i = 0; i < $tr[0].cells.length; i++) {
			var c = $tr[0].cells[i];
			count += c.colSpan == undefined? 1: c.colSpan;
		}
		return count;
	}



}