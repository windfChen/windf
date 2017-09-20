$(function(){
	var submiting = false;
	$('form').submit(function() {
		if (!submiting) {
			
			// 验证
			if ($('input[name=username]').val() == '') {
				alert('用户名不能为空');
				$('input[name=username]').focus();
				submiting = false;
				return false;
			}
			if ($('input[name=password]').val() == '') {
				alert('密码不能为空');
				$('input[name=password]').focus();
				submiting = false;
				return false;
			} else {
				var p = $('input[name=password]').val();
				$('input[name=password]').val(MD5(p));
			}
			
			// 提交
			$(this).ajaxSubmit({
				dataType: "json",
				beforeSubmit : function(){
					submiting = true;
					
				},
				success : function(data){
					submiting = false;
					if (data.success == 'Y') {
						window.location = 'index';
					} else {
						alert(data.tip);
					}
				}
			});
			
		}
		
				
		return false; 
	});
});