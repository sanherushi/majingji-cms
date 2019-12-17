<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>添加链接</title>
<script type="text/javascript" src="/resource/js/jquery.validate.js"></script>
</head>
<body>
	<div>
		<form action="" id="form1">
			<div class="form-group">
				<label for="text">链接名称:</label> <input class="form-control"
					type="text" name="text" id="text">
			</div>
			<div class="form-group">
				<label for="url">链接url:</label> <input class="form-control"
					type="text" name="url" id="url">
			</div>
			
			<div class="form-group">
				<button class="btn btn-success" type="submit"> 保存</button>
				<button  class="btn btn-info" type="reset">重置</button>
				<button  class="btn btn-success" type="button" onclick = "back()">返回</button>
			</div>
		</form>
	</div>
	<script type="text/javascript">
		$("#form1").validate({
			rules:{
				text:{
					required:true
				},
				url:{
					required:true
				}
			},
			messages:{
				text:{
					required:"链接名称不能为空"
				},
				url:{
					required:"链接url不能为空"
				}
			},
			submitHandler:function(){
				$.post(
						"/admin/links/add",
						$("#form1").serialize(),
						function(result){
							alert(result.msg);
							if(result.code==0){
								$("#center").load("/admin/links/links");
							}
						}
					)
			}
		})
		
		function back(){
			$("#center").load("/admin/links/links");
		}
	</script>
</body>
</html>