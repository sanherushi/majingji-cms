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
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
<title>欢迎注册</title>
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/top.jsp"></jsp:include>
	<br>
	<div class="container">
		<div class="row passport_bg">

			<div class="col-md-6 ">

				<div class="card" style="width: 26rem; margin-top: 5px">
					<div class="card-header" style="height: 40px">
						<h5 class="card-title" align="center">欢迎注册</h5>
					</div>
					<div class="card-body">
						<form id="form1" action="/passport/reg" method="post">
							<div>
								<span style="color: red">${message }</span>
							</div>
							<div class="form-group">
								<label for="username">用户名:</label> <input id="username"
									class="form-control" type="text" name="username">
							</div>
							<div class="form-group">
								<label for="password">密码:</label> <input id="password"
									class="form-control" type="password" name="password">
							</div>
							<div class="form-group">
								<label for="repassword">确认密码:</label> <input id="repassword"
									class="form-control" type="password" name="repassword">
							</div>
							<div class="form-group form-inline">
								<label>性别:</label> <input class="form-control" type="radio"
									name="gender" value="1" checked="checked">男 <input
									class="form-control" type="radio" name="gender" value="0">女
							</div>
							<div class="form-group">
								<button type="submit" class="btn btn-success">注册</button>
								<button type="reset" class="btn btn-warning">重置</button>
							</div>

						</form>
					</div>
				</div>

			</div>
			<div class="col-md-6">
				<div class="passport_r">
					<h3>最新加入的用户：</h3>
					<p align="center">
						<img src="/resource/images/guest.jpg" alt="..."
							class="rounded-circle img-thumbnail" /> &nbsp;&nbsp;&nbsp;&nbsp;
						<img src="/resource/images/guest1.jpg" alt="..."
							class="rounded-circle img-thumbnail" />
					</p>
				</div>
			</div>
		</div>
	</div>
	<div>
		<br /> <br />
	</div>
	<jsp:include page="/WEB-INF/views/common/footer.jsp" />
	<script type="text/javascript">
		$(function() {
			$("#form1").validate({
				rules : {
					//定义验证规则
					username : {
						required : true,
						rangelength : [ 2, 5 ]
					//用户名在2-5之间,","可以不省略
					},
					password : {
						required : true,
						rangelength : [ 6, 10 ]
					},
					repassword : {
						required : true,
						equalTo : "#password",//两次密码必须一致
					}
				},
				messages : {
					username : {
						required : "用户名不能为空",
						rangelength : "用户名长度是2-5"
					},
					password : {
						required : "密码不能为空",
						rangelength : "密码长度是6-10"
					},
					repassword : {
						required : "确认密码不能为空",
						equalTo : "两次密码必须一致",
					}
				}

			})
		})
	</script>
	<script type="text/javascript" src="/resource/js/jquery-3.2.1.js"></script>
	<script type="text/javascript" src="/resource/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="/resource/js/jquery.validate.js"></script>
</body>
</html>