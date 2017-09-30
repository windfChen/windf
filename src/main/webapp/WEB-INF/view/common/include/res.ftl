		
		
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1, user-scalable=no">		
		<link rel="stylesheet" type="text/css" href="${rc.contextPath}/resources/module/index/font/iconfont.css">
		<link rel="stylesheet" type="text/css" href="${rc.contextPath}/resources/module/index/css/reset.css" />
		<link rel="stylesheet" type="text/css" href="${rc.contextPath}/resources/module/index/css/public.css" />
		<link rel="stylesheet" type="text/css" href="${rc.contextPath}/resources/module/index/css/grid.subtw.css" />
		<link rel="stylesheet" type="text/css" href="${rc.contextPath}/resources/module/index/css/center.css" />
		<link rel="stylesheet" type="text/css" href="${rc.contextPath}/resources/common/plugins/dialog/css/dialog.css" />
		<link rel="stylesheet" type="text/css" href="${rc.contextPath}/resources/module/index/css/BreakingNews.css" />
		<link rel="stylesheet" type="text/css" href="${rc.contextPath}/resources/plugins/manage/css/grid.css" />
		
		<#include "/common/include/baseRes.ftl" >
		
		<script type="text/javascript" src="${rc.contextPath}/resources/common/plugins/jquery/jquery.js"></script>
		<script type="text/javascript" src="${rc.contextPath}/resources/common/plugins/jquery/jquery.form.js"></script>
		<script type="text/javascript" src="${rc.contextPath}/resources/common/js/page.js"></script>
		<script type="text/javascript" src="${rc.contextPath}/resources/module/index/js/scripts.js"></script>
		<script type="text/javascript" src="${rc.contextPath}/resources/common/plugins/dialog/js/dialog.js"></script>
		<script type="text/javascript" src="${rc.contextPath}/resources/module/index/js/BreakingNews.js"></script>
		<script type="text/javascript" src="${rc.contextPath}/resources/plugins/manage/js/grid.js"></script>
		
		<script type="text/javascript">
		$(function(){
			$('#breakingnews').BreakingNews({
				linkhovercolor: '#bf2025',
				timer: 3000,
				effect: 'slide'
			});
		});
		</script>
	    <!--[if lt IE 9]>
	      <script src="${rc.contextPath}/resources/module/index/js/html5shiv.min.js"></script>
	      <script src="${rc.contextPath}/resources/module/index/js/respond.min.js"></script>
	    <![endif]-->
	