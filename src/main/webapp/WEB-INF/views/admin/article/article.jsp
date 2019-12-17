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
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="/resource/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="/resource/css/cms.css" />
<script type="text/javascript" src="/resource/js/jquery-3.2.1.js"></script>

</head>
<body>
	<div style="text-align: center;">
		<dl>
			<dd>
				<dt>
					<h2>${article.title }</h2>
				</dt>
			</dd>
			<dd>
				<button type="button" class="btn btn-success btn-sm" onclick = "update(${article.id},1)">同意</button>&emsp;
				<button type="button" class="btn btn-danger btn-sm" onclick = "update(${article.id},-1)">驳回</button>&emsp;
				<button type="button" class="btn btn-primary btn-sm" onclick="back()">返回列表</button>
			</dd>
			<dd><fmt:formatDate value="${aritcle.updated }" pattern="yyyy-MM-dd HH:mm:ss"/> </dd>
			<dd>${article.content }</dd>
		</dl>
	</div>
	<script type="text/javascript">
		function back() {
			$("#center").load("/admin/article/articles?title="+'${a.title}'+"&status="+'${a.status}'+"&pageNum="+'${pageNum}');
		}
		function update(id,status){
			$.post(
					"/admin/article/update",
					{status:status,id:id},
					function(obj){
						if(obj){
							alert("操作成功");
							$("#center").load("/admin/article/articles?title="+'${a.title}'+"&status="+'${a.status}'+"&pageNum="+'${pageNum}');
						}else{
							alert("设置失败");
						}
					}
				)
		}
	</script>
</body>
</html>