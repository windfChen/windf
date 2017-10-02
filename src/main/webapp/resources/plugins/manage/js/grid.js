function Grid() {
	this.gridConfig = {};
	
	this.tableTitleHTML = '';
	
	this.pageNum = 1;
	this.pageSize = 10;
	
	this.submiting = false;
	this.dataColumnWidth = 0;
	this.idColunmWidth = 0;
	
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
			obj.load();
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
			this.initSavePage();
			$('#add_btn').click(function(){
				obj.showSavePage();
			});
		}
		if (this.gridConfig.canDelete) {
			$('#del_btn').show();
			$('#del_btn').click(function(){
				obj.del();
			});
		}
		
		// 计算宽度
		var titleCount = 0;
		for (var i = 0; i < this.gridConfig.columns.length; i++) {
			var c = this.gridConfig.columns[i];
			if (c.canList) {
				titleCount ++;
			}
		}
		this.dataColumnWidth = parseInt((12 - 1) / (titleCount));
		this.idColunmWidth = (12 - 1) % (titleCount) + 1
		
		this.initTitle();
		this.initMenu();
	},
	
	initTitle : function () {

		// 初始化表头
		var tableTitle = '<li id="title_li" class="col-xs-' + this.idColunmWidth + '"><span class="table"><label class="label_check"><div class="btn_check"></div><input id="select_all_input" type="checkbox"/></label>全选</span></li>';
		for (var i = 0; i < this.gridConfig.columns.length; i++) {
			var c = this.gridConfig.columns[i];
			
			if (c.canList) {
				tableTitle += '<li class="col-xs-' + this.dataColumnWidth + '">' + c.name + '</li>';
			}
		}
		
		
		$('#grid').html(tableTitle);
		$('#select_all_input').click(function(){
			if ($(this).is(":checked")) {
				$('.grid_id').each(function(){
					$(this).attr('checked', 'true');
					$(this).parent().addClass('c_on');
				});
			} else {
				$('.grid_id').each(function(){
				$('.grid_id').removeAttr("checked");
					$(this).parent().removeClass('c_on');
				});
			}
		});
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
				obj.initTitle();
				if (data.models && data.models.length > 0) {	
					for (var i = 0; i < data.models.length; i++) {
						var d = data.models[i];
						var h = '<li class="col-xs-' + obj.idColunmWidth + '"> <span class="table"> <label class="label_check"> <div class="btn_check"></div> \
							<input type="checkbox" class="grid_id" data_id="' + d.id + '" name=""/> </label> </span> </li>';
						for (var j = 0; j < obj.gridConfig.columns.length; j++) {
							var c = obj.gridConfig.columns[j];
							if (c.canList) {
								h += '<li class="col-xs-' + obj.dataColumnWidth + '">' + obj._getColumnDisplay(d, c) + '</li>';
							}
						}	
						$('#grid').append(h);
					}
					
					if (data.totalCount > obj.pageSize) {
						$('#page').html(getPageHtml(pageNum, data.totalCount, obj.pageSize));
					}
					
					gridEvent();
				}
					
				
			},
			error:function(XHR, textStatus, errorThrown){
				alert('error: ' + errorThrown);
			}
		});
	},
	
	_getColumnDisplay : function (d, c) {
		var result = this._getValue(d, c.dataIndex);
		if (c.display) {
			result = c.display;
			result = result.replace('$' + '{value}', this._getValue(d, c.dataIndex));
			
			for (var i = 0; i < this.gridConfig.columns.length; i++) {
				var c1 = this.gridConfig.columns[i];
				result = result.replace('$' + '{' + c1.dataIndex + '}', this._getValue(d, c1.dataIndex));
			}
			
			if (result.indexOf('href="/') > 0 || result.indexOf('href=\'/') > 0) {
				result = result.replace('href="/', 'href="' + basePath + '/').replace('href=\'/', 'href=\'' + basePath + '/');
			}
			
			if (result.startsWith('function')) {
				eval('result = ' + result + '().toString();');
			}
		}
		return result == ''? '&nbsp;': result;
	},
	
	_getValue : function (d, dataIndex) {
		var result = '';
		try {
			eval('result = d.' + dataIndex + ';');
		} catch (e){
			
		}
		return result;
	},
	
	del : function() {
		var ids = this.getSelections();
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
	},
	
	initSavePage : function(){
		$('#savePage').html('<div class="mod_title1">\
						<div class="pull-right work_qxbtn">\
							<a href="javascript:;" id="return_btn"><i class="iconfont icon-arrLeft"></i>返回</a>\
						</div>\
						<h3 class="pull-left">' + this.gridConfig.title + '-添加</h3>\
					</div>\
					<div class="work_fabu">\
						<form id="form" action="save.json' + queryString + '" method="post">'	+		
							'<div id="form_inputs"></div>\
							<div class="clearfix work_table">\
								<div class="col-md-12 col-xs-12"><button type="submit" class="work_save trans" >保存</button></div>\
							</div>\
						</form>\
					</div>');
					
					for (var i = 0; i < this.gridConfig.columns.length; i++) {
						var c = this.gridConfig.columns[i];
						
						if (!c.canAdd) {
							continue;
						}
						
						if (c.type == 'TextField') {
							var a = '<div class="clearfix work_table">\
								<div class="col-md-1 col-xs-2 center_table_t">*' + c.name + ':</div>\
								<div class="col-md-11 col-xs-10">\
									<div class="center_table_input"><input type="text" name="entity.' + c.dataIndex + '" placeholder="请输入' + c.name + '" class="work_input"/></div>\
								</div>\
							</div>';
							$('#form_inputs').append(a);
						} else if (c.type == 'TextArea') {
							var a = '<div class="clearfix work_table">\
								<div class="col-md-1 col-xs-2 center_table_t">*' + c.name + ':</div>\
								<div class="col-md-11 col-xs-10">\
									<div class="center_table_input"><textarea name="entity.' + c.dataIndex + '" placeholder="请输入' + c.name + '" class="work_input"></textarea></div>\
								</div>\
							</div>';
							$('#form_inputs').append(a);
						} else if (c.type == 'ComboBox') {
							var _dataIndex = c.dataIndex.replace('.', '');
							var a = '<div class="clearfix work_table">\
								<div class="col-md-1 col-xs-2 center_table_t">*' + c.name + ':</div>\
								<div class="col-md-11 col-xs-10">\
									<div class="center_table_input"><select name="entity.' + (c.dataIndex.replace('.name', '.id')) + '" id="combox_' + _dataIndex + '"></select></div>\
								</div>\
							</div>';
							$('#form_inputs').append(a);
							if (c.comboUrl) {
								var a = function (_dataIndex, comboUrl) {
									return function(_dataIndex, comboUrl) {
										$.getJSON(basePath + comboUrl, function(data) {
											var b = '';
											for (var j = 0; j < data.length; j++) {
												var d = data[j];
												b += '<option value="' + d[0] + '">' + d[1] + '</option>';
											}
											$('#combox_' + _dataIndex).append(b);
											
										});
									}(_dataIndex, comboUrl);
								};
								a(_dataIndex, c.comboUrl);
							} else {// [{"id":"F","name":"女"},{"id":"M","name":"男"}],
								var b = '';
								for (var j = 0; j < c.comboDataArray.length; j++) {
									var d = c.comboDataArray[j];
									b += '<option value="' + d.id + '">' + d.name + '</option>';
								}
								$('#combox_' + _dataIndex).append(b);
							}
						}
						
					}
							
		var obj = this;
		$('#return_btn').click(function(){
			obj.showSavePage();
		});
		$('#form').submit(function() {
			obj.save();
			return false;
		});
	},
	
	showSavePage : function() {
		if ($('#listPage').is(":hidden")) {
			$('#listPage').show();
			$('#savePage').hide();
		} else {
			$('#listPage').hide();
			$('#savePage').show();
		}
	},
	
	save : function() {
		var obj = this;
		if (!this.submiting) {
			
			// 验证
			if ($('input[name=username]').val() == '') {
				alert('用户名不能为空');
				$('input[name=username]').focus();
				this.submiting = false;
				return false;
			}
			
			// 提交
			$('#form').ajaxSubmit({
				dataType: "json",
				beforeSubmit : function(){
					this.submiting = true;
				},
				success : function(data){
					$('#form')[0].reset();
					this.submiting = false;
					if (data.success == 'Y') {
						obj.showSavePage();
						obj.load();
					} else {
						alert(data.tip);
					}
				}
			});
			
		}
	},
	
	getSelections : function () {
		var ids = '';
		$('.grid_id').each(function() {
			if (this.checked) {
				if (ids.length != '') {
					ids += ',';
				}
				ids += $(this).attr('data_id');
			}
		});
		return ids;
	},
	
	initMenu : function () {
		var obj = this;
		for (var i = 0; i < this.gridConfig.menus.length; i++) {
			var menu = this.gridConfig.menus[i];
			
			var menuStyleClass = '';
			if (menu.name.indexOf('返回') > -1) {
				menuStyleClass = 'icon-arrLeft';
			}
			if (menu.name.indexOf('添加') > -1) {
				menuStyleClass = 'icon-tianjia';
			}
			if (menu.name.indexOf('修改') > -1) {
				menuStyleClass = 'icon-bianji';
			}
			if (menu.name.indexOf('删除') > -1) {
				menuStyleClass = 'icon-weibiaoti--';
			}
			var a = '<a href="javascript:;" id="menu_' + i + '"><i class="iconfont ' + menuStyleClass + '"></i>' + menu.name + '</a>';
			
			$('#menus_div').append(a);
			
			$('#menu_' + i).click(getMenuOption(menu));
			
			function getMenuOption(menu) {
				return function () {
					var ids = obj.getSelections();
					
					// 关于选择的提醒
					if (menu.canSelect) {
						if (ids.length == 0) {
							if (menu.isSingleSelect) {
								alert('请选择一条记录');
								return;
							} else {
								alert('请至少选择一条记录');
								return;
							}
						}
						
						if (ids.indexOf(',') > 0 && menu.isSingleSelect) {
							alert('只能选择一条记录');
							return;
						}
					}
					
					// TODO 弹窗字段
					
					var menuFunciotn = function(){
						if (menu.actionAddress) {
							var actionAddress = menu.actionAddress;
							if (actionAddress.indexOf('${queryString}') > 0) {
								actionAddress = actionAddress.replace('${queryString}', queryString);
							}
							if (actionAddress.startsWith("/")) {
								actionAddress = basePath + actionAddress;
							}
							if (menu.checkColumn) {
								var data = ids;
								
								if (menu.ajaxReturn) {
									// TODO 等待框
									$.ajax({
										url: actionAddress,
										data: {
											ids: data
										},
										method: 'post',
										dataType : 'json',
										waitMsg: '处理中，请稍候...',
										success: function (json) {
											
											// TODO 自定义ajax处理方法
											
											if (json.success == 'Y') {
												alert((json.tip && json.tip != 'success'? json.tip: '操作成功！'), function(){
													obj.load();
												});
											} else {
												alert(json.tip, function(){
													obj.load();
												});
											}
											
										}
									});
								} else {
									// TODO 非ajax提交数据
								}
							} else {
								window.location = actionAddress;
							}
						}
					};
					
					if(menu.confirm != "false") {
						var confirmMsg = menu.confirm == true || menu.confirm == '' ?'您确定要执行此操作吗？' : menu.config;
						confirm(confirmMsg,function(){
							menuFunciotn();
						});
					} else {
						menuFunciotn();
					}
				}
			}
		}
	},
	
	getCombox : function (c) {
		
	var comboBoxStory = {}
		function getStory(c) {

			return function(fieldConfig) {
				var myStore = comboBoxStory[fieldConfig.dataIndex];
				if (!myStore) {
					if (c.comboUrl) {
						myStore = new Ext.data.SimpleStore({
							proxy: new Ext.data.HttpProxy({
								url: basePath + fieldConfig.comboUrl
							}),
							fields: ['id', 'name'],
							remoteSort: true
						});
					} else if (c.comboDataArray) {
						var data = [];
						for (var i = 0; i < c.comboDataArray.length; i++) {
							var d = [c.comboDataArray[i].id, c.comboDataArray[i].name];
							data[data.length] = d;
						}
						myStore = new Ext.data.SimpleStore({
							data: data,
							fields: ['id', 'name'],
							remoteSort: true
						});
					}
					comboBoxStory[fieldConfig.dataIndex] = myStore;
				}
				return myStore;
			}(c);

		}
	}
	
	
}
	