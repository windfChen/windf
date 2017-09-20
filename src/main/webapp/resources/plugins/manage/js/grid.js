var themeTemplate = 'null';
var storePageForReload; //用于刷新页面
var search; //用于高级搜索方法
var varHiddenColumn = ''; //取得那些需要隐藏的列

Ext.onReady(function () {
	Ext.Ajax.timeout = 120000;
	Ext.Ajax.request({
		url: 'grid.json',
		method: 'GET',
		async: false,
		callback: function (options, success, response) {
			if (success) {
				var responseJson = Ext.util.JSON.decode(response.responseText);
				initGrid(responseJson.data);
			} else {
			}
		}
	});
	
});
	
function initGrid (gridConfig) {
	var searchFields = [];
	for (var i = 0; i < gridConfig.columns.length; i++) {
		var c = gridConfig.columns[i];
		if (c.canSearch) {
			var o = {};
			o.formName = 'condition.' + c.dataIndex;
			o.inputName = '_s_' + c.dataIndex;
			searchFields[searchFields.length] = o;
		}
	}
	function getSearchParams(start, limit) {
		var searchParams = {}
		searchParams.start = start? start: g_start;
		searchParams.limit = limit? limit: g_limit;
		searchParams.page = searchParams.start / searchParams.limit + 1;
		searchParams['gridConfig.moreSort'] = moreSort.form.findField('moreSort').getValue();
		for (var i = 0; i < gridConfig.columns.length; i++) {
			var c = gridConfig.columns[i];
			if (c.canSearch) {
				searchParams['condition.' + c.dataIndex] = Ext.get('_s_' + c.dataIndex).dom.value
			}
		}
		return searchParams;
	}
	
	var g_start = 0;
	var g_limit = 20;
	var g_page = 1;
	var g_total = 0;
	
	/****************所有下拉框*start********************/
	// TODO2 下拉框设置
	var courseType_nameStore = new Ext.data.SimpleStore({
			proxy: new Ext.data.HttpProxy({
				url: '/manager/basic/peTchCourse_comboDate.action?menuId=1001&menuName=%E8%AF%BE%E7%A8%8B%E5%88%97%E8%A1%A8&column=courseType.name'
			}),
			fields: ['id', 'name'],
			remoteSort: true
		});
	var peSchool_nameStore = new Ext.data.SimpleStore({
			proxy: new Ext.data.HttpProxy({
				url: '/manager/basic/peTchCourse_comboDate.action?menuId=1001&menuName=%E8%AF%BE%E7%A8%8B%E5%88%97%E8%A1%A8&column=peSchool.name'
			}),
			fields: ['id', 'name'],
			remoteSort: true
		});
	/****************所有下拉框*end********************/

	/****************数据加载*start********************/
	var allFields = [];
	for (var i = 0; i < gridConfig.columns.length; i++) {
		var c = gridConfig.columns[i];
		allFields[allFields.length] = c.dataIndex;
	}
	Ext.form.Field.prototype.msgTarget = 'side';
	var store = new Ext.data.Store({
		
			proxy: new Ext.data.HttpProxy({
				timeout: 100000000,
				url: 'list.json',
				method: 'GET'
			}),
			reader: new Ext.data.JsonReader({
				root: 'models',
				totalProperty: 'totalCount',
				id: 'id',
				fields: allFields
			}),
			remoteSort: true
		});
	storePageForReload = store;

	//如果有默认排序将 sort 设置默认排序的第一个字段 否则为id -- yinxu
	store.setDefaultSort('id', 'desc');

	store.on('beforeload', function () {});

	// store 加载成功
	store.on('load', function () {
		g_total = store.getTotalCount();
		if (g_total == 0) {
			Ext.MessageBox.alert('提示', '没有符合条件的数据');
		}
	});
	// store 加载失败
	store.on('loadexception', function () {
		Ext.MessageBox.alert('错误', '数据加载错误');
	});
	/****************数据加载*end********************/

	/****************excel导出*start********************/
	var exportField = [];
	for (var i = 0; i < gridConfig.columns.length; i++) {
		var c = gridConfig.columns[i];
		if (c.canReport) {
			exportField[exportField.length] = [c.dataIndex, c.name];
		}
	}
	var typeData = new Ext.data.SimpleStore({
			fields: ['val', 'type', 'tip'],
			data: [
				//加一个导出excel选择列
				['XLS', 'Excel 当前页', ''], ['XLSALL', 'Excel 全部', ''], ['XLSCHOOSED', 'Excel 已选', '']
			]
		});
	var typeCombo = new Ext.form.ComboBox({
		store: typeData,
		valueField: 'val',
		displayField: 'type',
		typeAhead: true,
		mode: 'local',
		triggerAction: 'all',
		editable: false,
		selectOnFocus: true
	});

	// get the column status and submit to action
	function checkColumn() {
		var i = 0;
		var colStr = ',';
		for (i = 0; i < grid.colModel.config.length; i++) {
			var cm = grid.colModel.config[i];
			// 判断列标识不为空,并且不是hidden
			if (cm.dataIndex != '' && !cm.hidden) {
				//可以获得 其index,及header 参见 grid的定义;
				colStr = colStr + cm.dataIndex + ",";
			}
		}
		document.getElementById('exp_column').value = colStr;
	}

	typeCombo.on("select", function () {
		//加个导出选择窗口   ---zhaochen
		//判断是否导出选择列
		var temp = "";
		if (typeCombo.value == 'XLSCHOOSED') {
			var m = grid.getSelectionModel().getSelections();
			if (m.length > 0) {
				for (var i = 0, len = m.length; i < len; i++) {
					var ss = m[i].get("id");
					if (i == 0)
						temp = temp + ss;
					else
						temp = temp + "," + ss;
					//是否要去掉选择的列 store.remove(m[i]);
				}
				temp = temp + ",";
			} else {
				Ext.MessageBox.alert('错误', '请至少先选中一个条目再执行操作');
				return;
			}
		}
		var excelChoose = new Ext.form.FormPanel({
				labelWidth: 40,
				width: 525,
				frame: true,
				items: [{
						xtype: 'textfield',
						fieldLabel: '标题',
						//id:'excelTitle',
						name: "excelTitle",
						value: gridConfig.title,
						width: 200
					}, {
						xtype: "itemselector",
						name: "excelChoose",
						fieldLabel: '内容',
						dataFields: ["code", "desc"],
						toData: exportField,
						fromData: [], //初始化选择的列
						msWidth: 200,
						msHeight: 220,
						valueField: "code",
						displayField: "desc",
						//hideNavIcons:true,
						drawUpIcon: false,
						drawDownIcon: false,
						drawLeftIcon: true,
						drawRightIcon: true,
						drawTopIcon: false,
						drawBotIcon: false,
						fromLegend: "删除",
						toLegend: "已选"
					}
				]

			});
		var excelChooseWin = new Ext.Window({
				width: 525,
				height: 340,
				minWidth: 300,
				minHeight: 250,
				layout: 'fit',
				plain: true,
				closable: true,
				closeAction: 'hide',
				bodyStyle: 'padding:5px;',
				buttonAlign: 'center',
				items: excelChoose,
				buttons: [{
						text: '确认',
						handler: function () {

							excelChooseWin.hide();
							var tt = excelChoose.form.findField('excelChoose').getValue();
							var tmp = "<form target='_blank' action='/manager/basic/peTchCourse_abstractExcel.action?menuId=1001&amp;menuName=%E8%AF%BE%E7%A8%8B%E5%88%97%E8%A1%A8&format=" + typeCombo.value + "&date=" + (new Date()).getTime() + "&start=" + pagingbar.cursor + "&limit=" + g_limit + "&sort=" + store.getSortState().field + "&dir=" + store.getSortState().direction + "' method='post' name='exportExcel'><input type='hidden' id='exp_column'/> <input type='hidden' id='XLSCHOOSED' name='ids' value='" + temp + "'/><input type='hidden' id='moreSort' name='gridConfig.moreSort' value='" + moreSort.form.findField('moreSort').getValue() + "'/> <input type='hidden' id='excelChoose' name='gridConfig.excelChoose' value='" + excelChoose.form.findField('excelChoose').getValue() + "'/> <input type='hidden'  id='gridConfig.title'  name='gridConfig.title' value='" + excelChoose.form.findField('excelTitle').getValue() + "'/>" + " <input type='hidden' name='search__name' value='" + Ext.get('_s_name').dom.value + "' /> <input type='hidden' name='search__code' value='" + Ext.get('_s_code').dom.value + "' /> <input type='hidden' name='search__courseType.name' value='" + Ext.get('_s_courseType_name').dom.value + "' /> <input type='hidden' name='search__peSchool.name' value='" + Ext.get('_s_peSchool_name').dom.value + "' />" + "</form>";
							document.getElementById('exportexcel').innerHTML = tmp;
							checkColumn(); // 将form 的参数exp_colunm 的值设入
							document.exportExcel.submit();
						}
					}, {
						text: '取消',
						handler: function () {
							excelChooseWin.hide();
						}
					}
				]
			});
		excelChooseWin.show();
	});
	/****************excel导出*end********************/
	
	/****************分页配置*start********************/
	//继承ext的PagingToolbar
	//从外层拿到里面 ,外面得到不里面的from属性  ---zhaochen
	Ext.PagingToolbarEx = Ext.extend(Ext.PagingToolbar, {
			doLoad: function (start) {
				this.store.load({
					params: getSearchParams(start, this.pageSize)
				});
			}
		});
	Ext.reg('paging', Ext.PagingToolbarEx);
	
	var pageData = new Ext.data.SimpleStore({
		fields: ['val', 'type', 'tip'],
		//data : Ext.exampledata.states // from states.js
		data: [
			['20', '20', ''],
			['50', '50', ''],
			['100', '100', ''],
			['200', '200', ''],
			['500', '500', ''],
			['1000', '1000', ''],
			['1000000', '全部', ''],
			['0', '自定义', '']
		]
	});
	var pageCombo = new Ext.form.ComboBox({
		width: 80,
		store: pageData,
		valueField: 'val',
		displayField: 'type',
		typeAhead: true,
		mode: 'local',
		triggerAction: 'all',
		editable: false,
		value: g_limit,
		emptyText: g_limit
	});
	
	pageCombo.on("select", function () {
		//添加自定义显示每页记录数 --yinxu
		if (pageCombo.value == 0) {
			Ext.MessageBox.prompt('输入框', '请输入数据显示条数据', function (btn, text) {
				if (btn == 'ok') {
					var regex = '^\\d*$'; //为数字
					var regExp = new RegExp(regex);
					if (regExp.test(text) && text.length != 0) {
						g_limit = text;
						pagingbar.pageSize = 1 * g_limit;
						store.load({
							params: getSearchParams()
						});
					} else {
						Ext.MessageBox.alert('提示', '必须输入数字!');
					}
				}

			});
		} else {
			g_limit = pageCombo.value;
			pagingbar.pageSize = 1 * g_limit;
			store.load({
				params:getSearchParams() 
			});
		}

	});
	
	//取得当前页数 --yinxu 用于修改、删除以及ajax提交的一些功能按钮(如审核)
	function pageCursor() {
		var a = pagingbar.cursor;
		var b = pagingbar.pageSize;
		return a;
	}
	
	var pagingbar = new Ext.PagingToolbarEx({
		pageSize: g_limit,
		store: store,
		displayInfo: true,
		displayMsg: '显示 {0} - {1} of {2}',
		emptyMsg: "没有",
		items: [
			'-', '每页条数', pageCombo,

			'-', '请选择报表格式', typeCombo]
	});
	/****************分页配置*start********************/
	
	/****************列函数，用于修改数据*start********************/
	// TODO3 列的模板
	function GridConfigRenderFunction_0(p_value, metadata, record) {
		if (p_value == '1') {
			return '已上架';
		} else {
			return '已下架';
		}
	}
	function GridConfigRenderFunction_1(p_value, metadata, record) {
		return "<a href='/manager/basic/peTchCourseRefer.action?courseId=" + record.data['id'] + "' )>" + record.data['tnum'] + "</a>";
	}
	function GridConfigRenderFunction_2(p_value, metadata, record) {
		return "<a href='/manager/label/prTagEntity.action?entityId=" + p_value + "&tagEntityType=0' )>设置</a>";
	}
	function GridConfigRenderFunction_3(p_value, metadata, record) {
		if (p_value == '0') {
			return '是';
		} else {
			return '否';
		}
	}
	function GridConfigRenderFunction_4(p_value, metadata, record) {
		if (p_value == '1') {
			return '是';
		} else {
			return '否';
		}
	}
	function GridConfigRenderFunction_5(p_value, metadata, record) {
		return "<a href='/learning/manager/basic/peTchCourse/peTchCourse_toImportDesign.action?courseId=" + p_value + "' )>导入教学设计</a>";
	}
	function GridConfigRenderFunction_6(p_value, metadata, record) {
		return "<a href='/learning/sign/signLearn.action?sign=1&courseId=" + p_value + "' target='_blank'>教学设计</a>";
	}
	function GridConfigRenderFunction_7(p_value, metadata, record) {
		return "<a href='/learning/sign/signLearn.action?sign=0&courseId=" + p_value + "' target='_blank'>教学模板</a>";
	}
	/****************列函数，用于修改数据*end********************/

	/****************列配置*start********************/
	var listFieldColumn = [
		new Ext.grid.CheckboxSelectionModel()
	]
	for (var i = 0; i < gridConfig.columns.length; i++) {
		var c = gridConfig.columns[i];
		if (!c.canList) {
			continue;
		}
		
		function getRendererFunction(c) {	// 闭包传参
			var rendererFunction = function (value, metadata, record) {
				var newValue = value + '';
				var searchValueDom = Ext.get('_s_' + c.dataIndex);
				if (searchValueDom) {
					var searchValue = Ext.get('_s_' + c.dataIndex).dom.value;
					if (searchValue != null && searchValue.length > 0 && newValue.indexOf(searchValue) >= 0) {
						newValue = (newValue).replace(searchValue, '<font color=#FF0000>' + searchValue + '</font>');	// 搜索字标红
					}
				}
				var result = newValue;
				if(c.display != null && c.display.length > 0) {
					result = c.display;
					result = result.replace('${value}', newValue);
					
					for (var i = 0; i < gridConfig.columns.length; i++) {
						var c1 = gridConfig.columns[i];
						result = result.replace('${' + c1.dataIndex + '}', record.data[c1.dataIndex]);
					}
					
					//record.data['name']gridConfig
				}
				
				return result;
			}
			return rendererFunction;
		}
		
		
		var g = {
			header: c.name,
			dataIndex: c.dataIndex,
			width: 100,
			renderer: getRendererFunction(c)
		}
		listFieldColumn[listFieldColumn.length] = g;
	}
	var cm = new Ext.grid.ColumnModel(listFieldColumn);

	// by default columns are sortable
	cm.defaultSortable = true;
	/****************列配置*end********************/

	/****************菜单配置*start********************/
	var menubar = [];
	if (gridConfig.canAdd) {
		menubar[menubar.length] = '-';
		menubar[menubar.length] = {
			text: '添加',
			iconCls: 'addItem',
			handler: openAddModelWin
		};
	}
	if (gridConfig.canDelete) {
		menubar[menubar.length] = '-';
		menubar[menubar.length] = {
			text: '删除',
			iconCls: 'remove',
			handler: deleteModels
		};
	}
	for (var i = 0; i < gridConfig.meuns.length; i++) {
		var m = gridConfig.meuns[i];
		menubar[menubar.length] = '-';
		menubar[menubar.length] = {
			text: m.name,
			iconCls: 'selfDef',
			handler: GridConfigMenuFunction_0
		};
	}
	//加多列排序按钮 ---zhaochen
	menubar[menubar.length] = '->';
	menubar[menubar.length] = {
		text: '排序',
		// TODO4 判断是否有默认排序 --yinxu

		iconCls: 'selfDef',

		handler: sort
	};
	menubar[menubar.length] = '->';
	menubar[menubar.length] = {
		text: '重置',
		iconCls: 'selfDef',
		handler: function () {
			resizeGrid(grid);
		}
	};
	menubar[menubar.length] = '-';
	
	//GridConfig定义方法
	function GridConfigMenuScript_2() {
		var m = grid.getSelectionModel().getSelections();
		var ids = '';
		if (m.length > 0) {
			for (var i = 0, len = m.length; i < len; i++) {
				var ss = m[i].get('id');
				if (i == 0) {
					ids = ids + ss;
				} else {
					ids = ids + ',' + ss;
				}
			}
		} else {
			Ext.MessageBox.alert('错误', '请至少选择一条信息');
			return;
		}
		var fids = new Ext.form.Hidden({
				name: 'ids',
				value: ids
			});
		Ext.Ajax.request({
			params: {
				ids: ids
			},
			url: 'manager/basic/peTchCourse_getCourseStuElective.action',
			async: false,
			method: 'POST',
			success: function (response, opts) {
				if (response.responseJSON == null || response.responseJSON == undefined) {
					response.responseJSON = Ext.util.JSON.decode(response.responseText);
				}
				var tips = response.responseJSON.responseText;
				Ext.MessageBox.confirm('确定', tips, function (btn) {
					if (btn == 'yes') {
						Ext.Ajax.request({
							url: 'manager/basic/peTchCourse_updateCourseFlagUpMarket.action',
							async: false,
							params: {
								ids: ids
							},
							method: 'post',
							success: function (response) {
								var result = Ext.util.JSON.decode(response.responseText);
								Ext.Msg.alert('提示', result.info);
								store.load({
									params: getSearchParams()
								});
							},
							failure: function () {
								Ext.Msg.alert('提示', '方法调用失败');
							}
						});
					}
				});
			},
			failure: function (response, opts) {
				Ext.Msg.alert('系统提示', '验证出错，请重新操作！')
			}
		});
	}
	
	function GridConfigMenuFunction_0() {
		var m = grid.getSelectionModel().getSelections();

		if (m.length == 1)
			{
			Ext.MessageBox.confirm('确认',
				//根据设置显示操作提示信息--yinxu


				'您确定要执行此操作吗？',

				function (btn) {
				if (btn == 'yes') {
					var jsonData = "";
					for (var i = 0, len = m.length; i < len; i++) {
						var checkColumn = 'id';
						var ss = m[i].get(checkColumn);
						if (i == 0)
							jsonData = jsonData + ss;
						else
							jsonData = jsonData + "," + ss;
					}
					jsonData = jsonData + ",";

					Ext.MessageBox.show({
						title: '提示',
						closable: false,
						msg: '处理中，请稍候...'
					});
					Ext.Ajax.request({
						timeout: 100000000,
						url: 'manager/basic/peTchCourse_deepCopyCourse.action',
						params: {
							ids: jsonData
						},
						method: 'post',
						waitMsg: '处理中，请稍候...',
						success: function (response, options) {
							var responseArray = Ext.util.JSON.decode(response.responseText);
							if (responseArray.success == 'Y') {
								Ext.MessageBox.alert('提示', responseArray.info + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
							} else {
								Ext.MessageBox.alert('错误', responseArray.info + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
							}
							//更新(ajax)后页面停留在当前页面 --yinxu
							store.load({
								params: getSearchParams(pageCursor(), g_limit)
							});
						}
					});

				}
			});
		} else {
			Ext.MessageBox.alert('错误', '请您选择一条记录');
		}
	}

	function GridConfigMenuFunction_1() {
		var m = grid.getSelectionModel().getSelections();
		if (m.length > 0) {
			Ext.MessageBox.confirm('确认',
				//根据设置显示操作提示信息--yinxu


				'您确定要执行此操作吗？',

				function (btn) {
				if (btn == 'yes') {
					var jsonData = "";
					for (var i = 0, len = m.length; i < len; i++) {
						var ss = m[i].get("id");
						if (i == 0)
							jsonData = jsonData + ss;
						else
							jsonData = jsonData + "," + ss;
					}
					jsonData = jsonData + ",";
					progressStart(); //进度条开始
					Ext.Ajax.request({
						timeout: 100000000,
						url: '/manager/basic/peTchCourse_abstractUpdateColumn.action',
						params: {
							ids: jsonData,
							column: 'flagUpMarket',
							value: '1'
						},
						method: 'post',
						waitMsg: '处理中，请稍候...',
						success: function (response, options) {
							progressStop(); //进度条结束
							var responseArray = Ext.util.JSON.decode(response.responseText);
							if (responseArray.success == 'Y') {
								Ext.MessageBox.alert('提示', responseArray.info + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
							} else {
								Ext.MessageBox.alert('错误', responseArray.info + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
							}
							//更新(ajax)后页面停留在当前页面 --yinxu
							store.load({
								params: getSearchParams(pageCursor(), g_limit)
							});
						}
					});
				}
			});
		} else {
			Ext.MessageBox.alert('错误',
				'请至少先选中一个条目再执行操作');
		}
	}

	function GridConfigMenuFunction_3() {
		var m = grid.getSelectionModel().getSelections();

		if (m.length > 0)
			{
			Ext.MessageBox.confirm('确认',
				//根据设置显示操作提示信息--yinxu


				'您确定要执行此操作吗？',

				function (btn) {
				if (btn == 'yes') {
					var jsonData = "";
					for (var i = 0, len = m.length; i < len; i++) {
						var checkColumn = 'id';
						var ss = m[i].get(checkColumn);
						if (i == 0)
							jsonData = jsonData + ss;
						else
							jsonData = jsonData + "," + ss;
					}
					jsonData = jsonData + ",";

					Ext.MessageBox.show({
						title: '提示',
						closable: false,
						msg: '处理中，请稍候...'
					});
					Ext.Ajax.request({
						timeout: 100000000,
						url: 'manager/basic/peTchCourse_updateTopCourse.action?isTop=1',
						params: {
							ids: jsonData
						},
						method: 'post',
						waitMsg: '处理中，请稍候...',
						success: function (response, options) {
							var responseArray = Ext.util.JSON.decode(response.responseText);
							if (responseArray.success == 'Y') {
								Ext.MessageBox.alert('提示', responseArray.info + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
							} else {
								Ext.MessageBox.alert('错误', responseArray.info + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
							}
							//更新(ajax)后页面停留在当前页面 --yinxu
							store.load({
								params: getSearchParams(pageCursor(), g_limit)
							});
						}
					});

				}
			});
		} else {

			Ext.MessageBox.alert('错误',
				'请至少先选中一个条目再执行操作');

		}
	}

	function GridConfigMenuFunction_4() {
		var m = grid.getSelectionModel().getSelections();

		if (m.length > 0)
			{
			Ext.MessageBox.confirm('确认',
				//根据设置显示操作提示信息--yinxu


				'您确定要执行此操作吗？',

				function (btn) {
				if (btn == 'yes') {
					var jsonData = "";
					for (var i = 0, len = m.length; i < len; i++) {
						var checkColumn = 'id';
						var ss = m[i].get(checkColumn);
						if (i == 0)
							jsonData = jsonData + ss;
						else
							jsonData = jsonData + "," + ss;
					}
					jsonData = jsonData + ",";

					Ext.MessageBox.show({
						title: '提示',
						closable: false,
						msg: '处理中，请稍候...'
					});
					Ext.Ajax.request({
						timeout: 100000000,
						url: 'manager/basic/peTchCourse_updateTopCourse.action?isTop=0',
						params: {
							ids: jsonData
						},
						method: 'post',
						waitMsg: '处理中，请稍候...',
						success: function (response, options) {
							var responseArray = Ext.util.JSON.decode(response.responseText);
							if (responseArray.success == 'Y') {
								Ext.MessageBox.alert('提示', responseArray.info + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
							} else {
								Ext.MessageBox.alert('错误', responseArray.info + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
							}
							//更新(ajax)后页面停留在当前页面 --yinxu
							store.load({
								params: getSearchParams(pageCursor(), g_limit)
							});
						}
					});

				}
			});
		} else {

			Ext.MessageBox.alert('错误',
				'请至少先选中一个条目再执行操作');

		}
	}
	/****************菜单配置*end********************/

	/****************表格整体整合*start********************/
	var winW = document.body.clientWidth;
	var winH = document.body.clientHeight;
	if (winH < 100) {
		winH = 500;
	}
	var panelW = winW - 4;
	var panelH = winH - 40;

	//根据隐藏列 控制模版显示 yinxu
	var enableHdMenu = true;
	/**模板*start*/
	var t = new Ext.Template(
			'<style>.x-grid-hcell-bgcolor { background:', themeTemplate, ' url(/js/extjs/images/', themeTemplate, '/grid/grid3-hrow-over.gif) repeat-x left bottom;}',
			'.x-grid3-hd-btn{display:block;background:#c3daf9 url(/js/extjs/images/', themeTemplate, '/grid/grid3-hd-btn.gif) no-repeat -14px center;right:0;top:0;z-index:2;cursor:pointer;position:absolute;width:14px;height:23px;}</style>',
			'<td class="x-grid3-hd x-grid3-cell {id:this.renderSex} {css}" style="{style}">',
			'<div {tooltip} {attr} class="x-grid3-hd-inner x-grid3-hd-{id}" unselectable="on" style="{istyle}">',
			enableHdMenu ? '<a class="{id:this.displayBg}" href="#"></a>' : '',
			'{value}<img class="x-grid3-sort-icon" src="', Ext.BLANK_IMAGE_URL, '" />',
			'</div></td>');
	t.renderSex = function (value) {
		if (value != 'checker') {
			return "x-grid3-td-" + value;
		} else {
			return "x-grid3-td-" + value;
		}
	};
	t.displayBg = function (value) {
		if (value == 'checker') {
			return "x-grid3-td-" + value;
		} else {
			return "x-grid3-hd-btn x-grid3-td-" + value;
		}
	};
	t.compile();
	/**模板*end*/
	var grid = new Ext.grid.GridPanel({
			el: 'model-grid',
			width: panelW,
			height: panelH,
			store: store,
			cm: cm,
			trackMouseOver: true,
			sm: new Ext.grid.CheckboxSelectionModel(),
			loadMask: true,
			enableHdMenu: enableHdMenu,
			viewConfig: {
				forceFit: false,
				renderBody: function () {
					var markup = this.renderRows();
					if (markup == "")
						markup = "<div style='width:" + this.getTotalWidth()
							 + "'>&#160;</div>";
					return this.templates.body.apply({
						rows: markup
					});
				}
				//判断是否有隐藏列 如果有则自定义显示 --yinxu

			},
			listeners: {
				'beforerender': function (grid) {
					grid.on('hiddenchange', function (cm, columnIndex, hidden) {
						if (!cm.initColumn) {
							cm.initColumn = {};
						}
						//监听事件,把cm的显示情况存入数据库
						//alert(cm.getDataIndex(columnIndex));
						//if(cm.getDataIndex(columnIndex) != ''){
						cm.initColumn[cm.getDataIndex(columnIndex)] = hidden;
						var c = cm.config[columnIndex];
						if (c.hidden !== true) {
							Ext.Ajax.request({
								timeout: 3000000,
								url: '/entity/first/firstPageCombo_gridView.action',
								params: 'column=' + columnIndex + '&path=' + '/manager/basic/peTchCourse' + '&actionId=' + '',
								success: function (response, options) {},
								failure: function (response, options) {}
							});
						} else {
							Ext.Ajax.request({
								timeout: 3000000,
								url: '/entity/first/firstPageCombo_gridHidden.action',
								params: 'column=' + columnIndex + '&path=' + '/manager/basic/peTchCourse' + '&actionId=' + '',
								success: function (response, options) {},
								failure: function (response, options) {}
							});
						}
						//}
					}, this);
					//托管事件
					grid.relayEvents(grid.getColumnModel(), ['hiddenchange']);
				}
			},
			tbar: menubar,
			bbar: pagingbar,
			stateful: true,
			stateId: '/manager/basic/peTchCourse'

	});

	grid.on('rowdblclick', function (gridPanel, rowIndex, e) {

		var selectedId = store.data.items[rowIndex].id;
		new openUpdateModelWin(selectedId);

	});
	/****************表格整体整合*start********************/


	/****************排序窗口*start********************/
	var sortFromData = [];
	for (var i = 0; i < gridConfig.columns.length; i++) {
		var c = gridConfig.columns[i];
		if (!c.canSearch) {
			continue;
		}
		var item = [c.dataIndex + '|升序', c.name + '|升序']
		sortFromData[sortFromData.length] = item;
		item = [c.dataIndex + '|降序', c.name + '|降序']
		sortFromData[sortFromData.length] = item;
	}
	//加个多列排序   ---zhaochen
	var moreSort = new Ext.form.FormPanel({
		labelWidth: 40,
		width: 525,
		frame: true,
		items: [{
				xtype: "itemselector",
				name: "moreSort",
				fieldLabel: "排序",
				dataFields: ["code", "desc"],
				fromData: sortFromData,
				toData: [

				], //初始化选择的列
				msWidth: 200,
				msHeight: 220,
				valueField: "code",
				displayField: "desc",
				toLegend: "已选",
				fromLegend: "备选",
				toTBar: [{
						text: "清空",
						handler: function () {
							var ii = moreSort.getForm().findField("moreSort");
							ii.reset.call(ii);
						}
					}
				]
			}
		]

	});
	//多列排序窗口---zhaochen
	var sortWin = new Ext.Window({
		width: 525,
		height: 325,
		minWidth: 300,
		minHeight: 250,
		layout: 'fit',
		plain: true,
		closable: true,
		closeAction: 'hide',
		bodyStyle: 'padding:5px;',
		buttonAlign: 'center',
		items: moreSort,
		buttons: [{
				text: '确认',
				handler: function () {
					store.load({
						params: getSearchParams()
					});
					sortWin.hide();
				}
			}, {
				text: '取消',
				handler: function () {
					sortWin.hide();
				}
			}
		]
	});
	//初始化  窗口 ---zhaochen
	sortWin.show();
	sortWin.hide();
	function sort() {
		sortWin.show();
	}
	/****************排序窗口*end********************/

	/****************和添加、修改、删除有关的定义和方法*start********************/
	//以下为和添加、修改、删除有关的定义和方法---------------
	//批量添加界面
	function openBatchAddModelWin(btn, pressed) {
		var downloadexcel = new Ext.Action({
				text: '下载标准格式',
				handler: function () {
					window.open('/manager/basic/peTchCourse_batchAddExcel.action');
				},
				iconCls: 'excelModel'
			});
		var excelUpload = new Ext.form.TextField({
				fieldLabel: '上载文件*',
				name: '_upload',
				allowBlank: false,
				regex: new RegExp(/^(.*)(\.xls)$/),
				regexText: '文件格式错误!',
				inputType: 'file',
				anchor: '90%'
			});
		var formPanel = new Ext.form.FormPanel({
				frame: true,
				labelWidth: 100,
				defaultType: 'textfield',
				autoScroll: true,
				fileUpload: true,
				items: [new Ext.Button(downloadexcel), excelUpload]

			});
		var batchAddModelWin = new Ext.Window({
				title: 'Excel导入',
				width: 525,
				height: 250,
				minWidth: 300,
				minHeight: 250,
				layout: 'fit',
				plain: true,
				bodyStyle: 'padding:5px;',
				buttonAlign: 'center',
				items: formPanel,

				buttons: [{
						text: '保存',
						handler: function () {
							// check form value
							if (formPanel.form.isValid()) {
								formPanel.form.submit({
									url: '/manager/basic/peTchCourse_batchAddExcelUpload.action',
									waitMsg: '处理中，请稍候...',

									success: function (form, action) {
										var responseArray = action.result;
										if (responseArray.success == 'Y') {
											Ext.MessageBox.alert('提示', responseArray.info + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
											store.load({
												params: getSearchParams()
											});
											batchAddModelWin.close();
										} else {
											Ext.MessageBox.alert('错误', responseArray.info + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
										}
									}
								});
							} else {
								Ext.MessageBox.alert('错误', '输入错误，请先修正提示的错误');
							}
						}
					}, {
						text: '取消',
						handler: function () {
							batchAddModelWin.close();
						}
					}
				]

			});
		batchAddModelWin.show();

	}

	function getFormPanelItems(isUpdate) {
		var formPanelItems = [];
		for (var i = 0; i < gridConfig.columns.length; i++) {
			var c = gridConfig.columns[i];
			if(!isUpdate) {
				if (!c.canAdd) {
					continue;
				}
			} else {
				if (!c.canUpdate) {
					continue;
				}
			}
			
			var item = {};
			if (c.type == 'TextField') {
				item = new Ext.form.TextField({
					name: 'entity.' + c.dataIndex,
					fieldLabel: c.name + '*',
					allowBlank: false,
					maxLength: 50,
					regex: new RegExp(/^(\S|\S.*\S)$/),
					regexText: '输入格式：不能以空格开头和结尾',
					anchor: '90%'
				});
			} else if (c.type == 'ComboBox') {
				/*
				var courseType_name = new Ext.form.WhatyComboBoxForAdd({
					store: courseType_nameStore,
					listeners: {
						'blur': function () {
							if (this.getRawValue() == '' || this.getValue() == '') {
								this.setRawValue('');
								this.setValue('');
							}
						}
					},
					valueField: 'id',
					displayField: 'name',
					selectOnFocus: true,
					forceSelection: true,
					allowBlank: true,
					typeAhead: true,
					fieldLabel: '课程分类',
					name: 'bean.courseType.name',
					id: '_bean.courseType.name',
					triggerAction: 'all',
					editable: true,
					mode: 'local',
					emptyText: '',
					anchor: '90%',
					blankText: ''
				});
				*/
			} else if (c.type == 'DateField') {
				/*
				var startDate = new Ext.form.DateField({
						fieldLabel: '开课日期*',
						allowBlank: false,
						name: 'bean.startDate',
						anchor: '60%',
						format: 'Y-m-d',
						readOnly: false
					});
					*/
			}
			
			formPanelItems[formPanelItems.length] = item;
		}
		
		return formPanelItems;
	}
	
	//打开添加界面
	function openAddModelWin(btn, pressed) {

				
		var formPanel = new Ext.form.FormPanel({
				frame: true,
				labelWidth: 100,
				defaultType: 'textfield',
				autoScroll: true,
				items: getFormPanelItems()
			});

		var addModelWin = new Ext.Window({
				title: '添加新条目',
				width: 525,
				height: 325,
				minWidth: 300,
				minHeight: 250,
				layout: 'fit',
				plain: true,
				bodyStyle: 'padding:5px;',
				buttonAlign: 'center',
				items: formPanel,
				buttons: [{
						text: '保存',
						handler: function () {

							if (formPanel.form.isValid()) {
								formPanel.form.submit({
									url: 'save.json',
									waitMsg: '处理中，请稍候...',
									success: function (form, action) {
										var responseArray = action.result;
										if (responseArray.success == 'Y') {
											Ext.MessageBox.alert('提示', '添加成功&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;');
											store.load({
												params: getSearchParams()
											});
											addModelWin.close();
										} else {
											Ext.MessageBox.alert('错误', responseArray.tip + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
										}
									}
								});
							} else {
								Ext.MessageBox.alert('错误', '输入错误，请先修正提示的错误');
							}
						}
					}, {
						text: '取消',
						handler: function () {
							addModelWin.close();
						}
					}
				]

			});
		addModelWin.show();

	}

	//打开修改界面
	var formPanelReaderData = [];
	for (var i = 0; i < gridConfig.columns.length; i++) {
		var c = gridConfig.columns[i];
		if (!c.canUpdate) {
			continue;
		}
		formPanelReaderData[formPanelReaderData.length] = 'entity.' + c.dataIndex;
	}
	function openUpdateModelWin(selectedId) {
		var formPanel = new Ext.form.FormPanel({
			labelWidth: 100,
			bodyStyle: 'padding:5px',
			autoScroll: true,

			frame: true,
			reader: new Ext.data.JsonReader({
				root: 'data'
			}, formPanelReaderData),
			items: getFormPanelItems(true)
		});

		formPanel.form.load({
			url: 'detail.json?id=' + selectedId,
			method: 'get',
			waitMsg: 'Loading'
		});

		var updateModelWin = new Ext.Window({
			title: '编辑条目',

			width: 525,
			height: 325,

			minWidth: 300,
			minHeight: 250,
			layout: 'fit',
			plain: true,
			bodyStyle: 'padding:5px;',
			buttonAlign: 'center',
			items: formPanel,

			buttons: [{
					text: '保存',
					handler: function () {

						// check form value
						if (formPanel.form.isValid()) {
							formPanel.form.submit({
								url: 'update.json',
								params: {
									'entity.id': selectedId
								},
								method: 'post',
								waitMsg: '处理中，请稍候...',

								success: function (form, action) {
									var responseArray = action.result;
									if (responseArray.success == 'Y') {
										Ext.MessageBox.alert('提示', '保存成功&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;');
										//更新后停留在当前页 --yinxu
										store.load({
											params: getSearchParams(pageCursor(), g_limit)
										});
										updateModelWin.close();
									} else {
										Ext.MessageBox.alert('错误', responseArray.info + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
									}
								}
							});
						} else {
							Ext.MessageBox.alert('错误', '输入错误，请先修正提示的错误');
						}
					}
				}, {
					text: '取消',
					handler: function () {
						updateModelWin.close();
					}
				}
			]
		});
		updateModelWin.show();
	}

	//删除
	function deleteModels() {
		var m = grid.getSelectionModel().getSelections();
		if (m.length > 0) {
			Ext.MessageBox.confirm('确认',
				'您确认要删除选中的条目吗？',
				function (btn) {
				if (btn == 'yes') {
					Ext.MessageBox.show({
						title: '提示',
						closable: false,
						msg: '处理中，请稍候...'
					});
					var jsonData = "";
					for (var i = 0, len = m.length; i < len; i++) {
						var ss = m[i].get("id");
						if (i == 0)
							jsonData = jsonData + ss;
						else
							jsonData = jsonData + "," + ss;
						store.remove(m[i]);
					}
					Ext.Ajax.request({
						timeout: 100000000,
						url: 'delete.json',
						params: {
							ids: jsonData
						},
						method: 'post',
						waitMsg: '处理中，请稍候...',
						success: function (response, options) {
							var responseArray = Ext.util.JSON.decode(response.responseText);
							if (responseArray.success == 'Y') {
								Ext.MessageBox.alert('提示', '删除成功&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;');
							} else {
								Ext.MessageBox.alert('错误', responseArray.info + '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;');
							}
							//删除后页面停留在当前页面 --yinxu
							store.load({
								params: getSearchParams(pageCursor(), g_limit)
							});
						}
					});

				}
			});
		} else {
			Ext.MessageBox.alert('错误',
				'请至少先选中一个条目再执行操作');
		}
	}
	/****************和添加、修改、删除有关的定义和方法*end********************/

	/****************进度条*start********************/
	//进度条的一些属性和方法 --zhaochen
	var pbar2;
	var nowItems; // 初始化
	var allItems; //总数
	var progressWin; //进度条窗口
	var task = { // 定义一个任务
		run: function () {
			Ext.Ajax.request({
				url: 'progress.action?' + Math.random(),
				method: 'POST',
				success: function (response, options) {
					if (Ext.util.JSON.decode(response.responseText).nowItems != null) {
						nowItems = Ext.util.JSON.decode(response.responseText).nowItems;
						allItems = Ext.util.JSON.decode(response.responseText).allItems;
					}
				}
			});
			if (nowItems != null) {
				pbar2.updateProgress(nowItems / allItems); // 让进度条更新到指定的进度。这个值是0-1之间的数，我让i/100.0,以让它的结果是一个小数
				pbar2.updateText(nowItems + "/" + allItems); // 进度条显示的文本
			}
		},
		interval: 500 // 定时访问更新一次
	}
	function progressStart() { //进度条开始
		progressWin = new Ext.Window({
				title: '当前执行进度',
				width: 300,
				height: 52,
				layout: 'fit',
				closable: false,
				resizable: false,
				plain: true,
				modal: true,
				items: [{
						xtype: 'panel',
						width: 300,
						html: '<div id="progress1"></div>'
					}
				]
			});
		progressWin.show();
		pbar2 = new Ext.ProgressBar({
				renderTo: "progress1",
				width: 300
				//,text : "单击按钮开始..."
			});
		Ext.TaskMgr.start(task);
	}
	function progressStop() { //进度条结束
		nowItems = null;
		allItems = null;
		progressWin.close();
		Ext.TaskMgr.stop(task);
		Ext.Ajax.request({
			url: '/manager/basic/peTchCourse_stopProgress.action',
			method: 'POST',
			success: function (response, options) {}
		});
	}
	/****************进度条*end********************/

	/****************搜索*start********************/
	function createSearchForm(oldFormPanel) {
		
		var searchItems = [];
		for (var i = 0; i < gridConfig.columns.length; i++) {
			var c = gridConfig.columns[i];
			if (!c.canSearch) {
				continue;
			}
			
			var oldInput = document.getElementById('_s_' + c.dataIndex);
			var oldValue = '';
			if (oldInput != undefined) {
				oldValue = oldInput.value;
			}
			
			var _s_textField = new Ext.form.TextField({
				fieldLabel: c.name,
				value: oldValue,
				name: '_s_' + c.dataIndex,
				id: '_s_' + c.dataIndex
			});
			_s_textField.on('render', function () {
				this.getEl().on('dblclick', function () {
					search('_s_' + c.dataIndex, null, c.type);
				});
			});
			
			var item = {
				columnWidth: 280 / panelW,
				layout: 'form',
				items: [_s_textField]
			}
			searchItems[searchItems.length] = item;
		}
		
		//搜索
		/*
		var _s_courseType_name = new Ext.form.WhatyComboBox({
				store: courseType_nameStore,
				editable: true,
				listeners: {
					'blur': function () {
						if (this.getRawValue() == '' || this.getValue() == '') {
							this.setRawValue('');
							this.setValue('');
						}
					}
				},
				valueField: 'id',
				displayField: 'name',
				value: '',
				selectOnFocus: true,
				allowBlank: true,
				fieldLabel: '课程分类',
				name: '_s_courseType_name',
				id: '_s_courseType_name',
				triggerAction: 'all',
				emptyText: '',
				anchor: '90%',
				blankText: ''
			});
		_s_courseType_name.on('render', function () {
			_s_courseType_name.getEl().on('dblclick', function () {
				search('_s_courseType_name', courseType_nameStore, 'ComboBox1');
			});
		});
		*/
		var _s_search = new Ext.Button({
			type: 'submit',
			text: '搜索',
			handler: function () {
				store.load({
					params: getSearchParams(0, g_limit)
				});
			}
		});
		var _s_searchItem = {
			columnWidth: .1,
			layout: 'form',
			items: [_s_search]
		}
		searchItems[searchItems.length] = _s_searchItem;
		var _s_clean = new Ext.Button({
			type: 'button',
			text: '清空',
			handler: function () {
				cleanForm();
			}
		});
		var _s_cleanItem = {
			columnWidth: .1,
			layout: 'form',
			items: [_s_clean]
		}
		searchItems[searchItems.length] = _s_cleanItem;
		
		if (oldFormPanel != undefined) {
			oldFormPanel.destroy();
		}
		var s_formPanel = new Ext.FormPanel({
			buttonAlign: 'right',
			width: panelW,
			labelAlign: 'left',
			labelWidth: 100,
			frame: true,
			title: gridConfig.title,

			collapsible: true,
			//监听搜索框关闭打开改变grid高度
			listeners: {
				'collapse': function (e) {
					grid.render();
					grid.setHeight(panelH);
					grid.setWidth(panelW);

				},
				'expand': function (e) {

					grid.setWidth(panelW);
					grid.setHeight(window.document.body.clientHeight - 35 - e.getInnerHeight());

				}
			},

			collapsed: false, //默认展开
			animCollapse: false, //动画效果关闭
			items: [{
					layout: 'column',
					items: searchItems
			}]
		});
		
		return s_formPanel;
	}
	
	var s_formPanel = createSearchForm();
	
	//来进行设置列是否显示的情况
	HiddenMyColumn(grid, varHiddenColumn);

	//判断是否需要预搜索
	//TODO5 预搜索

	s_formPanel.render("searchtool");
	grid.render();
	grid.setHeight(window.document.body.clientHeight - 35 - s_formPanel.getInnerHeight()); //第一次搜索展开高度不准需要重新设置高度

	//s_formPanel.collapse(); //搜索框收缩
	s_formPanel.expand(); //搜索框展开

	store.load({
		params: getSearchParams()
	});

	//根据参数改变grid,s_formPanel的大小
	function resizeWin(panelW, panelH) {
		if (document.getElementById('searchtool').innerHTML == "") {
			return;
		}
		grid.setWidth(panelW);
		grid.setHeight(panelH);
		var is_collapsed = s_formPanel.collapsed;;
		
		s_formPanel = createSearchForm(s_formPanel);
		
		s_formPanel.render("searchtool");
		s_formPanel.collapse();
		if (!is_collapsed) {
			s_formPanel.expand();
			grid.setHeight(window.document.body.clientHeight - 35 - s_formPanel.getInnerHeight());
		}
	}
	window.onresize = function () {
		var Sys = {};
		var ua = navigator.userAgent.toLowerCase();
		if (window.ActiveXObject) {
			Sys.ie = ua.match(/msie ([\d.]+)/)[1];
		} else if (window.MessageEvent && !document.getBoxObjectFor) {
			Sys.chrome = ua.match(/chrome\/([\d.]+)/)[1]
			if (Sys.ie) {
				resizeWin(window.document.body.offsetWidth - 4, window.document.body.offsetHeight - 40);
			}
			if (Sys.chrome) {
				resizeWin(window.document.body.offsetWidth - 4, window.document.body.clientHeight - 40);
			}
		}
					
	}
	// 搜索框框提示信息
	for (var i = 0; i < gridConfig.columns.length; i++) {
		var c = gridConfig.columns[i];
		if (!c.canSearch) {
			continue;
		}
		new Ext.ToolTip({
			target: '_s_' + c.dataIndex,
			title: '双击搜索',
			width: 200,
			html: '双击输入框设置更多条件',
			mouseOffset: [15, 18],
			showDelay: 300
		});
	}
	//清除搜索条件的方法   by czc 2010-10-18
	function cleanForm() {
		for (var i = 0; i < gridConfig.columns.length; i++) {
			var c = gridConfig.columns[i];
			if (!c.canSearch) {
				continue;
			}
			Ext.get('_s_' + c.dataIndex).dom.value = '';
		}
	}
	
	//高级搜索的方法--zhaochen
	search = function (id, store, type) {
		var advancedSearch;
		if (store != null && type == 'ComboBox1') {
			store = id.indexOf('_s_combobox_') == 0 ? store.store : store;
			store.load();
			advancedSearch = new Ext.form.FormPanel({
					labelWidth: 1,
					width: 330,
					height: 245,
					labelSeparator: " ",
					items: [{
							xtype: 'textfield',
							width: 303,
							emptyText: "搜索",
							enableKeyEvents: true,
							listeners: {
								'keyup': function (textfield, e) {
									var exp = ".*" + textfield.getValue();
									store.filter('name', new RegExp(exp));
								}
							}
						}, {
							xtype: "multiselect",
							name: "advancedSearch",
							dataFields: ["id", "name"],
							store: store,
							displayField: "name",
							width: 302,
							height: 200
						}
					]
				});
		}
		if (store != null && type == 'ComboBox2') {
			advancedSearch = new Ext.form.FormPanel({
					labelWidth: 1,
					width: 330,
					height: 245,
					labelSeparator: " ",
					items: [{
							xtype: 'textfield',
							width: 303,
							emptyText: "搜索",
							enableKeyEvents: true,
							listeners: {
								'keyup': function (textfield, e) {
									var exp = ".*" + textfield.getValue();
									store.store.filter('name', new RegExp(exp));
								}
							}
						}, {
							xtype: "multiselect",
							name: "advancedSearch",
							dataFields: ["id", "name"],
							store: store.store,
							displayField: "name",
							width: 302,
							height: 200
						}
					]
				});
		}
		if (store != null && type == 'ComboBox3') {
			advancedSearch = new Ext.form.FormPanel({
					labelWidth: 1,
					width: 330,
					height: 245,
					labelSeparator: " ",
					items: [{
							xtype: 'textfield',
							width: 303,
							emptyText: "搜索",
							enableKeyEvents: true,
							listeners: {
								'keyup': function (textfield, e) {
									var exp = ".*" + textfield.getDisplayField();
									store.store.filter('name', new RegExp(exp));
								}
							}
						}, {
							xtype: "multiselect",
							name: "advancedSearch",
							hiddenName: "advancedSearch",
							dataFields: ["id", "name"],
							store: store.store,
							displayField: "name",
							valueField: "id",
							width: 302,
							height: 200
						}
					]
				});
		}
		if (type == 'Date') {
			advancedSearch = new Ext.form.FormPanel({
					labelWidth: 1,
					width: 330,
					height: 245,
					labelSeparator: " ",
					items: [{
							xtype: "datepicker",
							name: "advancedSearch",
							format: "Y-m-d",
							//不加这个宽度不会变
							style: 'width:100%;height:100%',
							listeners: {
								'render': function (dp) {
									// 取得DatePicker的DOM节点的第一个子节点
									var outerTable = dp.el.dom.firstChild;
									// 取得DatePicker的顶部导航条
									var tbar = outerTable.firstChild.childNodes[0];
									// 取得DatePicker的主体
									var main = outerTable.firstChild.childNodes[1];
									// 取得DatePicker底部工具条
									var bbar = outerTable.firstChild.childNodes[2];
									var innerTable = main.firstChild.firstChild;
									var thead = innerTable.firstChild;
									// 使用行内样式修改
									outerTable.style.width = '100%';
									outerTable.style.height = '100%';
									// 以下为解决DatePicker变形
									tbar.style.height = '30px';
									bbar.style.height = '30px';
									innerTable.style.height = '100%';
									thead.firstChild.style.height = '20px';
								}
							},
							handler: function () {
								setSearchContent(this.getValue()
									.format('Y-m-d'));
							}
						}
					]
				});
		}
		if (type == 'TextField') {
			advancedSearch = new Ext.form.FormPanel({
					labelWidth: 1,
					width: 330,
					height: 245,
					labelSeparator: " ",
					items: [{
							xtype: "multiselect",
							name: "advancedSearch",
							dataFields: ["id", "name"],
							displayField: "name",
							width: 300,
							height: 200
						}
					]
				});
		}
		//高级搜索的关系式
		var relation = new Ext.form.FormPanel({
				labelWidth: 1,
				height: 245,
				labelSeparator: " ",
				items: [{
						xtype: "multiselect",
						name: "relation",
						dataFields: ["id", "name"],
						data: [["and", "and"], ["or", "or"], ["(", "("],
							[")", ")"], ["not", "not"], [">", ">"], ["<", "<"],
							[">=", ">="], ["<=", "<="], ["=", "="]],
						displayField: "name",
						width: 120,
						height: 225
					}
				],
				width: 157
			});
		//高级搜索的面板
		var advancedSearchPanel = new Ext.Panel({
				layout: 'table',
				width: 525,
				frame: true,
				layoutConfig: {
					columns: 2
				}, // 将父容器分成2列
				items: [advancedSearch, relation]
			});
		//高级搜索的窗口
		var advancedSearchWin = new Ext.Window({
				title: '设置搜索条件',
				tbar: [{
						xtype: 'label',
						style: 'margin-left:5px;',
						html: '条件: '
					}, {
						xtype: 'textfield',
						fieldLabel: 'Title',
						id: 'searchContent',
						value: Ext.get(id).dom.value,
						name: "searchContent",
						width: 395
					}, '-', {
						iconCls: 'clear',
						text: '清空',
						handler: function () {
							Ext.get('searchContent').dom.value = ''
						}
					}
				],
				width: 525,
				height: 373,
				minWidth: 300,
				minHeight: 250,
				layout: 'fit',
				plain: true,
				bodyStyle: 'padding:5px;',
				buttonAlign: 'center',
				items: [advancedSearchPanel],
				buttons: [{
						text: '确认',
						handler: function () {
							if (Ext.get('searchContent').dom != null) {
								Ext.get(id).dom.value = Ext.get('searchContent').dom.value;
							}
							if (store != null) {
								if (type == 'ComboBox1')
									store.clearFilter();
								if (type == 'ComboBox2')
									store.store.clearFilter();
								if (type == 'ComboBox3')
									store.store.clearFilter();
							}
							advancedSearchWin.close();
							_s_search.handler();
						}
					}, {
						text: '取消',
						handler: function () {
							if (store != null) {
								if (type == 'ComboBox1')
									store.clearFilter();
								if (type == 'ComboBox2')
									store.store.clearFilter();
								if (type == 'ComboBox3')
									store.store.clearFilter();
							}
							advancedSearchWin.close();
						}
					}
				]
			});
		advancedSearchWin.show();
	}
	/****************搜索*end********************/


	/****************找不到用处*start********************/
	Ext.QuickTips.init();
	function toggleDetails(btn, pressed) {
		var view = grid.getView();
		view.showPreview = pressed;
		view.refresh();
	}
	/****************找不到用处*end********************/
	/*****myend**************************************/
}

var editorInstance;
/**
 * FCKEditor初始化完成将调用此方法
 * @param {Object} editorInstance
 */
function FCKeditor_OnComplete(instance) {
	editorInstance = instance;
};

// 隐藏列函数
function HiddenMyColumn(grid, varColumnIndex) {

	if (varHiddenColumn != "") {
		var cms = grid.getColumnModel(); // 获得列模型
		var strarrayUserColumn = new Array();
		strarrayUserColumn = varHiddenColumn.split(",");
		//alert(strarrayUserColumn);
		for (var i = 0; i < strarrayUserColumn.length; i++) {
			var tmc = strarrayUserColumn[i];
			//alert("tmc:"+tmc);
			if (strarrayUserColumn[i] != '') {
				for (var j = 0; j < grid.colModel.config.length; j++) {
					var cm = grid.colModel.config[j];
					if (tmc == j) {
						cm.hidden = true; //设置为隐藏
					}
				}
			}

		}
	}
}

/* 同步预搜索参数到搜索栏 */
function rsyncSearchParems(search_formPanel, s_formPanel) {
	var preSearch = search_formPanel.items.items;
	for (var i = 0; i < preSearch.length; i++) {
		var test = preSearch[i].id.replace("search_", "_s_");
		s_formPanel.findById(test).setValue(search_formPanel.findById(preSearch[i].id).getValue())
	}
}

Ext.override(Ext.menu.DateMenu, {
	render: function () {
		Ext.menu.DateMenu.superclass.render.call(this);
		if (Ext.isGecko || Ext.isSafari) {
			this.picker.el.dom.childNodes[0].style.width = '178px';
			this.picker.el.dom.style.width = '178px';
		}
	}
});