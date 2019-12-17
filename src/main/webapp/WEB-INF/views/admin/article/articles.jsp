<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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

</head>
<body>
	<div style="text-align: center;">
		<div class="form-group form-inline">
			<label for="title">标题:</label> <input type="text" class="title"
				id="title" value="${article.title }" name="title">&emsp;
			文章状态:<select class="form-control form-control-sm" name="status"
				id="status">
				<option value="0">待审</option>
				<option value="1">已审</option>
				<option value="-1">驳回</option>
				<option value="">全部</option>
			</select>&emsp;
			<button type="button" class="btn btn-success btn-sm"
				onclick="query()">查询</button>
		</div>
		<table class="table table-hover table-bordered">
			<thead class="table-danger">
				<tr>
					<th>序号</th>
					<th>文章标题</th>
					<th>作者</th>
					<th>是否热门</th>
					<th>所属栏目</th>
					<th>所属分类</th>
					<th>更新时间</th>
					<th>审核状态</th>
					<th>是否删除</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${page.list }" var="a" varStatus="i">
					<tr>
						<td>${(page.pageNum-1) * page.pageSize+i.index+1 }</td>
						<td>${a.title }</td>
						<td>${a.user.username }</td>
						<td><c:if test="${a.hot==0 }">
								<button type="button" class="btn btn-info"
									onclick="update(this,${a.id})">否</button>
							</c:if> <c:if test="${a.hot==1 }">
								<button type="button" class="btn btn-success"
									onclick="update(this,${a.id})">是</button>

							</c:if></td>
						<td>${a.channel.name }</td>
						<td>${a.category.name }</td>
						<td><fmt:formatDate value="${a.updated }"
								pattern="yyyy-MM-dd HH:mm:ss" /></td>
						<td>${a.status==1?'审核通过':a.status==0?'待审核':'驳回' }</td>
						<td>
							<c:if test="${a.deleted==0 }">
								<button type="button" class ="btn btn-primary btn-sm" onclick = "del(this,${a.id})">正常</button>
							</c:if>
							<c:if test="${a.deleted==1 }">
								<button type="button" class ="btn btn-danger btn-sm" onclick = "del(this,${a.id})">已删除</button>
							</c:if>
						</td>
						<td><button type="button" class="btn btn-warning btn-sm" 
						onclick="detail(${a.id})">详情</button></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<jsp:include page="/WEB-INF/views/common/pages.jsp"></jsp:include>
	</div>
	<script type="text/javascript">
		 $(function(){
			//就绪函数用于下拉框的回显
			$("[name=status]").val('${article.status}');
		}) 
		function goPage(pageNum){
			var title = $("[name=title]").val();
			var status = $("[name=status]").val();
			$("#center").load("/admin/article/articles?title="+title+"&status="+status+"&pageNum="+pageNum);
		}
		
		function query(){
			var title = $("[name=title]").val();
			var status = $("[name=status]").val();
			$("#center").load("/admin/article/articles?title="+title+"&status="+status);
		}
		
		function update(thiz,id){
			var hot = $(thiz).text()=="否"?1:0;
			$.post(
				"/admin/article/update",
				{hot:hot,id:id},
				function(result){
					if(result.code==0){
						$(thiz).text(hot==1?"是":"否");
						$(thiz).attr("class",hot==1?"btn btn-success":"btn btn-info");
					}else{
						alert(result.msg);
					}
				}
			)
			
		}
		function detail(id){
			var title = $("[name=title]").val();
			var status = $("[name=status]").val();
			var pageNum = ${page.pageNum};
			$("#center").load("/admin/article/article?id="+id+"&title="+title+"&status="+status+"&pageNum="+pageNum);
		}
		function del(thiz,id){
			var deleted = $(thiz).text()=="正常"?1:0;
			$.post(
				"/admin/article/update",
				{id:id,deleted:deleted},
				function(result){
					if(result.code==0){
						$(thiz).text(deleted==0?"正常":"已删除");
						$(thiz).attr("class",deleted==0?"btn btn-primary btn-sm":"btn btn-danger btn-sm");
					}else{
						alert(result.msg);
					}
				}
			)
		}
	</script>
</body>
</html>