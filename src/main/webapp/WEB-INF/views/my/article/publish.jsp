<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
	String htmlData = request.getParameter("content1") != null ? request.getParameter("content1") : "";
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8" />
<title>KindEditor JSP</title>
<link rel="stylesheet"
	href="/resource/kindeditor/themes/default/default.css" />
<link rel="stylesheet"
	href="/resource/kindeditor/plugins/code/prettify.css" />
<script charset="utf-8"
	src="/resource/kindeditor/plugins/code/prettify.js"></script>
<script charset="utf-8" src="/resource/kindeditor/kindeditor-all.js"></script>
    
<script charset="utf-8" src="/resource/kindeditor/lang/zh-CN.js"></script>
<script src="/resource/js/jquery-3.2.1.js"></script>
<script type="text/javascript" src="/resource/js/jquery.validate.js"></script>
<script>
	KindEditor.ready(function(K) {
		window.editor1 = K.create('textarea[name="content1"]', {
			cssPath : '/resource/kindeditor/plugins/code/prettify.css',
			uploadJson : '/resource/kindeditor/jsp/upload_json.jsp',
			fileManagerJson : '/resource/kindeditor/jsp/file_manager_json.jsp',
			allowFileManager : true,
			afterCreate : function() {
				var self = this;
				K.ctrl(document, 13, function() {
					self.sync();
					document.forms['example'].submit();
				});
				K.ctrl(self.edit.doc, 13, function() {
					self.sync();
					document.forms['example'].submit();
				});
			}
		});
		prettyPrint();
	});
	function query() {
		alert(editor1.html())
		//alert( $("[name='content1']").attr("src"))
	}
</script>
</head>
<body>
	<form id="form1" onreset="res()">
		<div class="form-group">
			<label for="title">文章标题:</label> <input type="text"
				class="form-control" id="title" name="title">
		</div>
		<div class="form-group">
			<textarea name="content1" cols="100" rows="8"
				style="width: 100%; height: 250px; visibility: hidden;"><%=htmlspecialchars(htmlData)%></textarea>
			<br />

		</div>
		<div class="form-inline form-group">
			所属栏目: <select class="form-control form-control-sm" name="channelId"
				id="channel">
				<option selected value="-1">请选择</option>
			</select> 所属分类: <select class="form-control form-control-sm" name="categoryId"
				id="category">
				<option selected>请选择</option>
			</select>
		</div>

		<div class="form-group">
			标题图片: <input type="file" name="file"
				class="form-control form-control-sm ">
		</div>
		<div class="form-group">
			<button type="submit" class="btn btn-success">发布文章</button>
			<button type="reset" class="btn btn-warning">重置</button>
		</div>
	</form>
	<script type="text/javascript">
		$(function() {
			$("#form1").validate({
				rules:{
					title:{
						required:true
					},
					content1:{
						required:true,
					},
					channelId:{
						min:1
					},
					categoryId:{
						min:1
					}
				},
				messages:{
					title:{
						required:"文章标题不能为空"
					},
					content1:{
						required:"请输入文章内容",
					},
					channelId:{
						min:"请选择一个栏目"
					},
					categoryId:{
						min:"请选择一个类别"
					}
				},
				submitHandler:function(form){//验证通过后进行文章的发布
					//获取formDada对象
					var formData = new FormData($("#form1")[0]);
					//单独封装富文本编辑中内容(html格式的)
					formData.set("content",editor1.html());
					$.ajax({
						type:"post",
						url:"/my/publish",
						data:formData,
						//告诉jquery不要去处理发送的数据
						processData:false,
						//告诉jquery不要去处理content-Type请求头
						contentType : false,
						success:function(result){
							if(result.code==0){
								alert("发布成功");
								location = "/my";
							}else{
								alert(result.msg);
							}
						}
					})
				}
			})
			
			$.get("/channel/selects", function(obj) {
				for ( var i in obj) {
					$("#channel").append(
							"<option value='"+obj[i].id+"'>" + obj[i].name
									+ "</option>");
				}
			}, "json")

		})

		$("#channel").change(
				function() {
					//清空二级下拉框中的内容,避免多次操作后多次追加内容
					$("#category").empty();
					var channelId = $(this).val();
					if (channelId == -1) {
						$("#category").html("<option>请选择</option>");
						return;
					}
					$.get("/category/selects", {
						channelId : channelId
					}, function(list) {
						for ( var i in list) {
							$("#category").append(
									"<option value = '"+list[i].id+"'>"
											+ list[i].name + "</option>");
						}
					})

				})
		
		function res(){
			$("#category").html("<option>请选择</option>");
		}
	</script>
</body>
</html>
<%!private String htmlspecialchars(String str) {
		str = str.replaceAll("&", "&amp;");
		str = str.replaceAll("<", "&lt;");
		str = str.replaceAll(">", "&gt;");
		str = str.replaceAll("\"", "&quot;");
		return str;
	}%>