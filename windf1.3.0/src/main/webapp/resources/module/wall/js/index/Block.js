/***********************************
1、添加length函数，判断字符串的长度（或者复写string的方法）
2、Block的所有状态设置统一的函数
3、var obj = this; 这句话出现的次数太多了

***********************************/
/************************砖块*start**********************************/
function Block(seat, owner){
	this.seat = seat;
	this.owner = owner;
};
Block.prototype.exists = function(){};
Block.prototype.isShow = function(){};
Block.prototype.show = function(){};
Block.prototype.hide = function(){};
Block.prototype.getHTML = function(){};
Block.prototype.getOwner = function(){};
Block.prototype = {
	registerUrl: BASE_PATH + '/wall/register',
	loginUrl: BASE_PATH + '/wall/login',
	style_class: 'block',
	
	exists:	function() {
		return this.seat !== undefined;
	},
	isShow: function(){
		var div = $("#" + this.getId());
		return div.html() !== '';
	},
	getOwner: function() {
		var result = this.owner;
		if (!result.exists()) {
			result = new User();
		}
		
		return result;
	},
	
	getStatus:function() {
		if (this.getOwner().username === CURRENT_USER_USERNAME) {
			return 'me';
		} else if (!this.getOwner().exists()) {
			return 'showEmpty';
		} else {
			return 'showUser';
		}
	},
	show: function(status) {
		status = status?status:this.getStatus();
		
		var obj = this;
		var div = $("#" + this.getId());
		
		
		switch(status) {
			case 'none': 
				this.showBackground();
				break;
			case 'user': 
				if (this.getOwner().username === CURRENT_USER_USERNAME) {
					this.showMe();
				} else if (!this.getOwner().exists()) {
					this.showEmpty();
				} else {
					this.showUser();
				}
				break;
			case 'select': 
				this.select();
				break;
			default: 
				alert('default'); 
				break;
		}
		
		if (CURRENT_SELECT_BLOCK === this && status === 'login') {
			alert('CURRENT_SELECT_BLOCK === this');
			div.removeClass('select');
			CURRENT_SELECT_BLOCK = undefined;
			return;
		} else if (CURRENT_SELECT_BLOCK !== undefined && status !== 'select') {
			return;
		}
		
		// 隐藏背景
		div.css('background-image', 'none');
		
		if (status == 'background') {
			$('#' + obj.getId()).addClass("out").removeClass("in");
			setTimeout(function() {
				$('#' + obj.getId()).addClass("in").removeClass("out");
				// 隐藏内容
				div.html('');
				// 恢复背景
				div.css('background-image', 'url(' + obj.seat.img + ')');
			}, 225);
			
			if (CURRENT_USER_USERNAME !== '') {
				div.removeClass('select');
				CURRENT_SELECT_BLOCK = undefined;
			}
		} else if (status == 'login') {
			if (CURRENT_USER_USERNAME !== '') {
				div.addClass('select');
				CURRENT_SELECT_BLOCK = this;
				return;
			}
			
			div.html('<div><span class="inputDiv" ><input type="password" placeholder="密码" /><button>登录</button></span></div>');
			div.find('div').click(function(e) {
				e.stopPropagation();
			});
			div.find('button').click(function(e) {
				e.stopPropagation();
				$(this).attr('disabled', true);
				
				var pwd = $(this).parent().find('input[type=password]').val().trim();

				// 验证
				if (pwd === '') {
					alert('密码不能为空');
				} else if (pwd.length > 20) {
					alert('密码长度不能超过20');
				} else {
					obj.login(pwd);
				}
				$(this).removeAttr('disabled');
			});
		} else if (status == 'register') {
			
			if (CURRENT_USER_USERNAME !== '') {
				div.addClass('select');
				CURRENT_SELECT_BLOCK = this;
				return;
			}

			div.html('<span class="inputDiv" ><input type="text" placeholder="用户名"/><input type="password" placeholder="密码" /><button>提交</button></span>')
			div.find('button').click(function() {
				var btn = $(this).parent().find('btn');
				btn.attr('disabled', true);
				
				var pwd = $(this).parent().find('input[type=password]').val().trim();
				var name = $(this).parent().find('input[type=text]').val().trim();
				
				// 验证
				if (name === '') {
					alert('用户名不能为空');
				} else if (pwd === '') {
					alert('密码不能为空');
				} else if (name.length < 2) {
					alert('名字长度不能小于2');
				} else if (name.length > 10) {
					alert('名字长度不能超过10');
				} else if (pwd.length > 20) {
					alert('密码长度不能超过20');
				} else {
					obj.register(name, pwd);
				}
				
				btn.removeAttr('disabled');
			});
			div.find('span').click(function(e){
				e.stopPropagation();
			});
		} else if (status == 'showEmpty') {
			div.html('<div><span class="getIt" ><button>占领</button></span></div>');
			div.find('div').click(function(e) {
				obj.show('register');
				e.stopPropagation();
			});
		} else if (status == 'showUser') {
			
			div.html('<div class="owner" title="点击登录"><span class="username" >' + this.getOwner().username + '</span></div>');
			div.find('.owner').click(function(e){
				obj.show('login');
				e.stopPropagation();
			});
		} else if (status == 'me') {
			div.html('<div class="owner" ><span class="username" >我</span></div>');
		} else if (status === 'select') {
			
		}
	},
	select : function () {
		if (CURRENT_SELECT_BLOCK !== undefined) {
				var selectBlock = CURRENT_SELECT_BLOCK;
				CURRENT_SELECT_BLOCK = undefined;
				selectBlock.show('background');
				this.show();
				this.show('login');
			}
	},
	login: function(pwd) {
		var obj = this;
		$.ajax({
			   async:false,
			   url: this.loginUrl,
			   type: "post",
			   dataType: 'json',
			   data: {'p' :pwd, 's':this.seat.toString()},
			   timeout: 5000,
			   success: function (json) {
					if (json.success === 'Y') {
						var loginId = json.data;
					    obj.owner = new User(loginId);
						
						// 修改当前用户
						CURRENT_USER_USERNAME = loginId;
						ShowDock();
				   
					    var div = $("#" + obj.getId());
					    div.html('<div class="owner"><span class="username" >我</span></div>');
					} else {
						alert(json.tip);
					}
			   },
			   error: function(xhr){
				   alert("请求出错");
			   }
		});
	},
	register: function(name, pwd) {
		var obj = this;
		$.ajax({
			   async:false,
			   url: this.registerUrl,
			   type: "post",
			   dataType: 'json',
			   data: {'u': name, 'p' :pwd, 's':this.seat.toString()},
			   timeout: 5000,
			   success: function (json) {//客户端jquery预先定义好的callback函数,成功获取跨域服务器上的json数据后,会动态执行这个callback函数
					if (json.success === 'Y') {
					   var loginId = json.data;
					   obj.owner = new User(loginId);
					   
					   // 修改当前用户
					   CURRENT_USER_USERNAME = loginId;
					   ShowDock();
					   
					   w.show(obj);
					} else {
						alert(json.tip);
					}
			      
			   },
			   error: function(xhr){
				   alert("请求出错");
			   }
		});
	},
	hide: function() {
		this.show('background');
	},
	getId: function() {
		return this.style_class + "_" + this.seat.toString();
	},
	getHTML: function(wall) {
		var obj = this;
		var span = document.createElement("span");
		span.id = this.getId();
		span.className = this.style_class;
		$(span).css('background-image', 'url(' + this.seat.img + ')');
		$(span).mouseover(function() {
			wall.show(obj);
		});
		$(span).mouseleave(function() {
			wall.show(new Block());
		});
		$(span).click(function() {
			obj.show('select');
		});
		
		return span;
	}
}
/************************砖块*end**********************************/