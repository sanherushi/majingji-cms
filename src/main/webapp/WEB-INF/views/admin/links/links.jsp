<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>链接管理</title>
<script type="text/javascript" src="/resource/js/cms.js"></script>
</head>
<body>
	<div style="text-align: center;">
		<table class="table table-hover  table-bordered">
			<thead class="thead-light">
				<tr>
					<th>序号</th>
					<th>链接名称</th>
					<th>url</th>
					<th>创建时间</th>
					<th>操作
						<button class="btn btn-info" onclick="add()">增加链接</button>
					</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${page.list}" var="l" varStatus="i">
					<tr>
						<td>${(page.pageNum-1)*page.pageSize+i.index+1 }</td>
						<td>${l.text }</td>
						<td>${l.url }</td>
						<td><fmt:formatDate value="${l.created }"
								pattern="yyyy-MM-dd HH:mm:ss" /></td>
						<td><a href="${l.url }" target="_blank">浏览</a>&emsp;<a
							href="javascript:deleteLinksbyId(${l.id})">删除</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<!-- 引入分页信息 -->
	<jsp:include page="/WEB-INF/views/common/pages.jsp" />
	<script type="text/javascript">
		function add() {
			$("#center").load("/admin/links/add");
		}

		function goPage(pageNum) {
			$("#center").load("/admin/links/links?pageNum=" + pageNum);
		}

		function deleteLinksbyId(id) {
			if (confirm("确认要删除?")) {
				$.post("/admin/links/delLinks", {
					id : id
				}, function(result) {
					if (result.code == 0) {
						alert(result.msg);
						$("#center").load("/admin/links/links");
					} else {
						alert(result.msg);
					}
				})
			}
		}
	</script>
</body>
</html>