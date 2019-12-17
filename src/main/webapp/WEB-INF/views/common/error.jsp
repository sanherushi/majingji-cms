<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String path=request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>系统错误</title>
<script type="text/javascript" src="<%=path %>/resource/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="<%=path %>/resource/js/jquery.validate.js"></script>
<link rel="stylesheet" href="<%=path %>/resource/css/index3.css"  type="text/css"> 
</head>
<body style="background: url('/resource/images/404.png');background-repeat: no-repeat;">
	系统崩溃了......
</body>
</html>