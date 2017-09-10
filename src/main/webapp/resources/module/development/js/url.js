function list(moduleCode) {
	$.getJSON('url/list.json?module=' + moduleCode, function(resp){
		for (var i = 0; i < resp.data.length; i++) {
			var d = resp.data[i];
			var s = '<tr>\
				<td>' + d.controler + '</td>\
				<td>' + d.subPath + '</td>\
				<td>' + d.requestMethod + '</td>\
				<td>' + d.ajaxReturn + '</td>\
				<td>\
					<ul class="clearfix">\
						<li class="tabl_op_li">\
							<a href="javascript:void(0);" class="tabl_op_link" onclick="minwindown(\'修改模块\', \'create?code=${m.code}\', \'\',{height:\'300px\'});;return false;">\
								<i class="iconfont icon_kzkt-teach-stroke"></i>修改基本信息\
							</a>\
						</li>\
						<li class="tabl_op_li">\
							<a href="javascript:void(0);" class="tabl_op_link">\
								<i class="iconfont icon_kzkt-penV-stroke"></i>详细信息\
							</a>\
						</li>\
					</ul>\
				</td>\
			</tr>';
			
			$('#data tbody').append(s);
		}
	});
}