<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css"
	href="/resource/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="/resource/css/cms.css" />
<script type="text/javascript" src="/resource/js/jquery-3.2.1.js"></script>
<script type="text/javascript"
	src="/resource/js/bootstrap.bundle.min.js"></script>
</head>
<body>
	<div class="container-fluid">
		<div class="row" style="margin-top: 2px; min-height: 50px">
			<div class="col-md-12" style="background-color: pink;">
				<img alt="" src="/resource/images/logo.jpg" class="rounded-circle">
				<a class="navbar-brand mr-1" href="index.html">CMS系统后台</a>
				<div class="dropdown" style="padding-top: 0.4rem;">
					<a href="#" class="nav-link dropdown-toggle" role="button"
						id="dropdownMenuButton" data-toggle="dropdown"
						aria-haspopup="true" aria-expanded="false"> <c:out
							value="${user.username}" default="cms-User" />
					</a>
					<div class="dropdown-menu dropdown-menu-left"
						aria-labelledby="dropdownMenuButton">
						<a class="dropdown-item" href="/my/">个人主页</a> <a
							class="dropdown-item" href="#">个人设置</a> <a class="dropdown-item"
							href="#">我的文章</a>
						<div class="dropdown-divider"></div>
						<a class="dropdown-item" href="/passport/logout">退出</a>
					</div>
				</div>
			</div>
		</div>

		<div class="row" style="padding-top: 3px; min-height: 500px">
			<!-- <div class="col-md-2">
				<ul class="nav flex-column">
					<li class="nav-item"><a class="nav-link" href="#"
						data="/admin/user/selects">用户管理</a></li>
					<li class="nav-item"><a class="nav-link" href="#"
						data="article/articles">文章管理</a></li>
					<li class="nav-item"><a class="nav-link" href="#">分类管理</a></li>
					<li class="nav-item"><a class="nav-link" href="#">系统设置</a></li>
				</ul>
			</div> -->


			<div class="col-md-2"
				style="padding-top: 20px; background-color: #eceaea;">
				<ul class="navbar">
					<li class="navbar-brand"><a class="nav-link"
						href="/admin/index"><span class="oi oi-monitor">&nbsp;后台首页</a></li>
					<li class="navbar-brand"><a class="nav-link" href="#"
						data="/admin/user/selects"><span class="oi oi-star">&nbsp;用户管理</a></li>
					<li class="navbar-brand"><a class="nav-link" href="#"
						data="/admin/article/articles"><span class="oi oi-sun">&nbsp;文章管理</a></li>
							
					<li class="navbar-brand"><a class="nav-link" href="#"
						data="/admin/links/links">&nbsp;友情链接</a></li>				
							
					<li class="navbar-brand"><a class="nav-link" href="#"><span
							class="oi oi-zoom-in">&nbsp;分类管理</a></li>
					<li class="navbar-brand"><a class="nav-link " href="#"><span
							class="oi oi-wrench">&nbsp;系统设置</a></li>
				</ul>


			</div>


			<div class="col-md-10" id="center"></div>
		</div>
	</div>

	<script type="text/javascript">
		$(function() {
			$(".nav-link").click(function() {
				var url = $(this).attr("data");
				$("#center").load(url);
			})
		})
	</script>
</body>
</html>