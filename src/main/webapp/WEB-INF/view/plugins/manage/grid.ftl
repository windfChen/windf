<!DOCTYPE html>
<html>
	<head>
		<title></title>
		<#include "/module/index/include/res.ftl" >
		
		<script>
			$(function(){
				window.grid = new Grid();
				grid.init();
				grid.load();
			});
		</script>
	</head>
	<body>
	
		<#include "/module/index/include/header.ftl" >
		
		<!--main start -->
		<section class="container mt-sm25 mt-xs-15">
			<div class="row">
			
				<#include "/module/index/include/left.ftl" >
				
				<div class="te-md-8 mt-xs-10">
					<div class="center_rigt">
						<div class="boxClear">
							<div class="mod_title1">
								<div class="pull-right work_qxbtn">
									<a href="javascript:;" style="display:none;" id="add_btn"><i class="iconfont icon-tianjia"></i>添加</a>
									<a href="javascript:;" style="display:none;" id="del_btn"><i class="iconfont icon-weibiaoti--"></i>删除</a>
								</div>
						    	<h3 class="pull-left" id="title"></h3>
						    </div>
						    <div class="work_qxbox">
						    	<div class="work_qxlist">
						    		<ul id="grid" class="clearfix">
									<!--
						    			<li class="col-xs-3">
						    				<span class="table">
												<label class="label_check">
													<div class="btn_check"></div>
													<input type="checkbox"/>
												</label>全选
											</span>
						    			</li>
						    			<li class="col-xs-3">角色名称</li>
						    			<li class="col-xs-3">角色类型</li>
						    			<li class="col-xs-3">用户人数</li>
						    			
						    			<li class="col-xs-3">
						    				<span class="table">
												<label class="label_check">
													<div class="btn_check"></div>
													<input type="checkbox"/>
												</label>
											</span>
						    			</li>
						    			<li class="col-xs-3">党建</li>
						    			<li class="col-xs-3">党建</li>
						    			<li class="col-xs-3">5人</li>
						    			
						    			<li class="col-xs-3">
						    				<span class="table">
												<label class="label_check">
													<div class="btn_check"></div>
													<input type="checkbox"/>
												</label>
											</span>
						    			</li>
						    			<li class="col-xs-3">学工</li>
						    			<li class="col-xs-3">学工</li>
						    			<li class="col-xs-3">5人</li>
						    			
						    			<li class="col-xs-3">
						    				<span class="table">
												<label class="label_check">
													<div class="btn_check"></div>
													<input type="checkbox"/>
												</label>
											</span>
						    			</li>
						    			<li class="col-xs-3">宣传</li>
						    			<li class="col-xs-3">宣传</li>
						    			<li class="col-xs-3">5人</li>
						    			
						    			<li class="col-xs-3">
						    				<span class="table">
												<label class="label_check">
													<div class="btn_check"></div>
													<input type="checkbox"/>
												</label>
											</span>
						    			</li>
						    			<li class="col-xs-3">统战</li>
						    			<li class="col-xs-3">统战</li>
						    			<li class="col-xs-3">5人</li>
						    			
						    			<li class="col-xs-3">
						    				<span class="table">
												<label class="label_check">
													<div class="btn_check"></div>
													<input type="checkbox"/>
												</label>
											</span>
						    			</li>
						    			<li class="col-xs-3">综合</li>
						    			<li class="col-xs-3">综合</li>
						    			<li class="col-xs-3">综合</li>
						    			-->
						    		</ul>
						    	</div>
								<div id="page" ></div>
						    </div>
						</div>
					</div>
				</div>
			</div>
		</section>
	    <!-- main end -->
	</body>
</html>
