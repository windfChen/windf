<%@page import="java.io.PrintWriter"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"  isErrorPage="true"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"> 
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"> 
<title>无标题文档</title>
<link href="<%=request.getContextPath()%>/error/css/error.css" rel="stylesheet" type="text/css">
</head>

<body>
<div class="wt-error wt-500">
</div>
<div class="wt-a" onclick="location.href='<%=request.getContextPath()%>';">返回首页</div>
<div style="display:none;">
<%
if(exception!=null){
	try{
		exception.printStackTrace();
		exception.printStackTrace(new PrintWriter(out));
	}catch(Exception e){
		e.printStackTrace();
	}
}
%>
</div>
</body>
</html>
