function Grid() {
	this.gridConfig = {};
	
	this.tableTitleHTML = '';
	
	this.pageNum = 1;
	this.pageSize = 10;
	
	window.load = function (obj) {
		return function(pageNum){
			obj.load(pageNum);
		}
	}(this);
	
}
Grid.prototype = {
	init : function () {
		var obj = this;
		$.getJSON('grid.json' + queryString, function(gridConfig){
			obj.gridConfig = gridConfig.data;
			obj.initGrid();
		})
	},
	initGrid : function () {
		// 初始化标题
		$('#title').html(this.gridConfig.title);
		$('title').html(this.gridConfig.title);
		
		// 添加删除功能
		var obj = this;
		if (this.gridConfig.canAdd) {
			$('#add_btn').show();
		}
		if (this.gridConfig.canDelete) {
			$('#del_btn').show();
			$('#del_btn').click(function(){
				obj.del();
			});
		}

		// 初始化表头
		var tableTitle = '<li class="col-xs-3"><span class="table"><label class="label_check"><div class="btn_check"></div><input type="checkbox"/></label>全选</span></li>';
		for (var i = 0; i < this.gridConfig.columns.length; i++) {
			var c = this.gridConfig.columns[i];
			
			if (c.canList) {
				tableTitle += '<li class="col-xs-3">' + c.name + '</li>';
			}
		}
		this.tableTitleHTML = tableTitle;
		$('#grid').html(tableTitle);
	},
	
	load : function (pageNum){
		pageNum = pageNum? pageNum: this.pageNum;
		var obj = this;
		$.ajax({
			async:true,
			url: 'list.json' + queryString,
			type: "GET",
			dataType: 'json',
			data: {'page':pageNum, 'limit': this.pageSize},
			beforeSend: function(){
			},
			success: function (data) {
				$('#grid').html('');
				$('#grid').html(obj.tableTitleHTML);
				for (var i = 0; i < data.models.length; i++) {
					var d = data.models[i];
					var h = '<li class="col-xs-3"> <span class="table"> <label class="label_check"> <div class="btn_check"></div> \
						<input type="checkbox" class="grid_id" data_id="' + d.id + '" name=""/> </label> </span> </li>';
					for (var j = 0; j < obj.gridConfig.columns.length; j++) {
						var c = obj.gridConfig.columns[j];
						if (c.canList) {
							h += '<li class="col-xs-3">' + obj._getColumnDisplay(d, c) + '</li>';
						}
					}	
					$('#grid').append(h);
				}
				
				if (data.totalCount > obj.pageSize) {
					$('#page').html(getPageHtml(pageNum, data.totalCount, obj.pageSize));
				}
				
				gridEvent();
			},
			error:function(XHR, textStatus, errorThrown){
				alert('error: ' + errorThrown);
			}
		});
	},
	
	_getColumnDisplay : function (d, c) {
		var result = d[c.dataIndex];
		if (c.display) {
			result = c.display;
			result = result.replace('$' + '{value}', d[c.dataIndex]);
			
			for (var i = 0; i < this.gridConfig.columns.length; i++) {
				var c1 = this.gridConfig.columns[i];
				result = result.replace('$' + '{' + c1.dataIndex + '}', d[c1.dataIndex]);
			}
			
			if (result.indexOf('href="/') > 0 || result.indexOf('href=\'/') > 0) {
				result = result.replace('href="/', 'href="' + basePath + '/').replace('href=\'/', 'href=\'' + basePath + '/');
			}
			
			if (result.startsWith('function')) {
				eval('result = ' + result + '().toString();');
			}
		}
		return result;
	},
	
	del : function() {
		var ids = '';
		$('.grid_id').each(function() {
			if (this.checked) {
				if (ids.length != '') {
					ids += ',';
				}
				ids += $(this).attr('data_id');
			}
		});
		if (ids == '') {
			alert('至少选择一条记录');
			return;
		}
		
		confirm('确定删除？删除后无法恢复！',function (){
			$.ajax({
				async:true,
				url: 'delete.json' + queryString,
				type: "POST",
				dataType: 'json',
				data: {'ids':ids},
				beforeSend: function(){
				},
				success: function (data) {
					if (data.success == 'Y') {
						alert('删除成功');
					} else {
						alert(data.tip);
					}
					
					load();
				},
				error:function(XHR, textStatus, errorThrown){
					alert('error: ' + errorThrown);
				}
			});
		})
	}
}
	