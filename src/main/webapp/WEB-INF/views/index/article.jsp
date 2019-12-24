<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>文章详情</title>
<!-- <script type="text/javascript" src="/resource/js/cms.js"></script> -->
<script type="text/javascript" src="/resource/js/jquery-3.2.1.js"></script>
<script type="text/javascript"
	src="/resource/js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="/resource/css/bootstrap.min.css" />
</head>
<body>

	<div style="text-align: center">
		<dl>
			<dt>
				<h2>${article.title }</h2>
			</dt>

			<hr>

			<dd id="mc">
				<c:if test="${isCollect==1}">
					<span style="font-size: 20px; color: red">★ (已收藏)</span>
				</c:if>
				<c:if test="${isCollect!=1}">
					<span style="font-size: 20px; color: blue;"> <a
						href="javascript:collect()">☆ (未收藏)</a>
					</span>
				</c:if>
				<span style="font-size: 20px; color: blue;">点击量:${article.hits }</span>
			</dd>

			<dd>
				<!-- <button type="button" class="btn btn-info" onclick="close()">关闭</button> -->
			</dd>
			<dd>
				<fmt:formatDate value="${aritcle.updated }"
					pattern="yyyy-MM-dd HH:mm:ss" />
			</dd>
			<dd>${article.content }</dd>

		</dl>

	</div>

	<div class="container">
		<h3>发表评论</h3>
		<textarea rows="4" cols="" name="content" class="container-fluid"></textarea>
		<button type="button" class="btn btn-success btn-sm" onclick="addComment()">发表</button>
		<span id="sp"></span>
		<br><br><h4>最新评论</h4><hr>
		<c:forEach items="${page.list }" var="c">
			<dl>
				<dt>
					<h5>${c.user.username}&emsp;<fmt:formatDate
							value="${c.created}" pattern="yyyy-MM-dd HH:mm:ss" />
					</h5>
				</dt>
				<dd>${c.content }</dd>
				<br>
				<hr>
			</dl>
		</c:forEach>
	</div>
	
	<jsp:include page="/WEB-INF/views/common/pages.jsp"></jsp:include>
	
	<script type="text/javascript">
		$(function(){
			if(${page.pageNum}>1){
				//刷新页面时自动到底部
				var h = $(document).height()-$(window).height();
		        $(document).scrollTop(h);
			}
		})
		function close() {
			this.close();
		}

		function collect() {
			//这里的el表达式外面一定要加'',表示是一个字符串
			var text = '${article.title }';
			//获取当前窗口的地址
			var url = window.location.href;
			
						$.post(
							"/collect",
							{
								text : text,
								url : url
							},
							function(result) {
								if (result.code == 0) {
									alert(result.msg);
									$("#mc")
											.html(
													"<span style='font-size: 20px; color: red'>★ (已收藏)</span>");
								} else {
									alert(result.msg);
								}
							})
		}

		function addComment() {
			var username = '${sessionScope.user.username}';
			if (username == '') {
				alert("请先登录");
				return;
			}
			if ($("[name = content]").val() == '') {
				$("#sp").text("评论内容不能为空");
				return;
			} 
			var articleId = ${article.id};
			var content = $("[name=content]").val();
			$.post("/comment", {
				articleId : articleId,
				content : content
			}, function(result) {
				if (result.code == 0) {
					alert(result.msg);
					location = "/article?id="+${article.id};
				} else {
					alert(result.msg);
				}
			})
		}
		
		function goPage(pageNum){
			location = "/article?id=${article.id}&pageNum="+pageNum;
		}
	</script>
</body>
</html>