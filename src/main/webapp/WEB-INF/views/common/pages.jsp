<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

	<nav aria-label="Page navigation example">
		<!-- 分页居中 -->
		<ul class="pagination justify-content-center">
			<li class="page-item"><a class="page-link"
				href="javascript:goPage(${page.prePage==0?1:page.prePage })"
				aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
			</a></li>
			<c:forEach items="${nums }" var="n">
				<li class="page-item ${page.pageNum==n?'active':'' }"><a class="page-link"
					href="javascript:goPage(${n })">${n }</a></li>
			</c:forEach>
			<li class="page-item"><a class="page-link"
				href="javascript:goPage(${page.nextPage==0?page.pages:page.nextPage })"
				aria-label="Next"> <span aria-hidden="true">&raquo;</span>
			</a></li>
		</ul>
	</nav>
