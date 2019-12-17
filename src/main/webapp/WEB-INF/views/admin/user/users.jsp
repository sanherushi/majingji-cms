<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

	<!-- text-align: center;文本内容居中 -->
	<div style="text-align: center;">
		<div class="form-group form-inline">
			<label for="username">用户名:</label> <input type="text" id="username"
				name="username" value="${user.username }">&nbsp;
			<button type="button" class="btn btn-success btn-sm"
				onclick="query()">查询</button>
		</div>
		<!-- table-hover 划过效果  table-bordered 表格分隔线-->
		<table class="table table-hover table-bordered">
			<thead class="table-primary">
				<tr>
					<th>序号</th>
					<th>用户名</th>
					<th>昵称</th>
					<th>性别</th>
					<th>生日</th>
					<th>注册时间</th>
					<th>更新时间</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${page.list}" var="u" varStatus="i">
					<tr>
						<td>${(page.pageNum-1)*page.pageSize+i.index+1 }</td>
						<td>${u.username }</td>
						<td>${u.nickname }</td>
						<td>${u.gender==0?"男":"女" }</td>
						<td><fmt:formatDate value="${u.birthday}"
								pattern="yyyy-MM-dd" /></td>
						<td><fmt:formatDate value="${u.created }"
								pattern="yyyy-MM-dd HH:mm:ss" /></td>
						<td><fmt:formatDate value="${u.updated }"
								pattern="yyyy-MM-dd HH:mm:ss" /></td>
						<td>
							<c:if test="${u.locked==1 }">
								<button type="button" class="btn btn-danger" onclick="update(${u.id},this)">禁用</button>
							</c:if>
							<c:if test="${u.locked==0 }">
								<button type="button" class="btn btn-success" onclick="update(${u.id},this)">正常</button>
							</c:if>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<!-- 引入分页信息 -->
		<jsp:include page="/WEB-INF/views/common/pages.jsp"></jsp:include>
		
	</div>
	<script type="text/javascript">
		function query() {
			var name = $("[name=username]").val();
			$("#center").load("/admin/user/selects?username=" + name);
		}
		
		function goPage(pageNum){
			var name = $("[name=username]").val();
			$("#center").load("/admin/user/selects?username="+name+"&pageNum="+pageNum);
		}
		
		function update(id,thiz){
			var locked = $(thiz).text()=="禁用"?0:1;
			$.post(
				"/admin/user/update",
				{id:id,locked:locked},
				function(result){
					if(result.code==0){
						$(thiz).text(locked==1?"禁用":"正常");
						$(thiz).attr("class",locked==1?"btn btn-danger":"btn btn-success");
					}else{
						alert(result.msg);
					}
				}
			)
		}
	</script>
