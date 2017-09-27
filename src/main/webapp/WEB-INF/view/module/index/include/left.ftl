
				<div class="te-md-2">
					<div class="center_left boxClear">
						<div class="center_personal">
							<!--<div class="portrait_md"><img src="images/portraits/portrait01.jpg" alt=""></div>-->
							<h2 class="center_personal_name"><strong>${user.name}</strong></h2>
						</div>
						<div class="center_menu">
							<ul id="menu">
							</ul>
					    </div>
					</div>
				</div>
				
				<script>
					$(function(){
						$.getJSON(basePath + '/menu.json?id=9', function(data) {
							for (var i = 0; i < data.length; i++) {
								var d = data[i];
								$('#menu').append('<li><a href="' + (d.url? basePath + d.url: 'javascript:void(0)') + '"><i class="iconfont icon-' + d.code + '"></i>' + d.text + '</a></li>');
							}
						})
					})
				</script>