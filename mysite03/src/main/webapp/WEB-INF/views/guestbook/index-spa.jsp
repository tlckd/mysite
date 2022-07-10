<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="${pageContext.request.contextPath }/assets/css/guestbook-spa.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-3.6.0.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<script type="text/javascript">
	//애플리케이션 안 그림의 App 부분 

	
var messageBox = function(title, message, callback) {
	$("#dialog-message p").text(message);
	$("#dialog-message")
		.attr("title", title)
		.dialog({
			width: 340,
			modal: true,
			buttons: {
				"확인": function(){
					$(this).dialog('close');
				}
			},
			close: callback
		});
}
	
	

var render = function(vo, mode) {
	var htmls = 
		"<li data-no='" + vo.no + "'>" +
		"<strong>" + vo.name + "</strong>" +
		"<p>" + vo.message + "</p>" +
		"<strong></strong>" +
		"<a href='' data-no='" + vo.no + "'>삭제</a>" + 
		"</li>";
	
	$("#list-guestbook")[mode ? "append" : "prepend"](htmls);
}


var fetch = function() {
	$.ajax({
		url: "${pageContext.request.contextPath }/api/guestbook",
		type: "get",
		dataType: "json",
		success: function(response) {
			if(response.result !== 'success'){
				console.error(response.message);
				return;
			}
			
			response.data.forEach(function(vo){
				render(vo, true);
			});
		},error: function(xhr, status, e){
			console.error(status + ":" + e);
		}		
	});
}


var add = $(function() {
	$("#add-form").submit(function(event){
		event.preventDefault();
		
		/* Validation */
		//1. 이름 유효성(empty) 체크
		if($("#input-name").val() === '') {
			messageBox("방명록", "이름이 비어 있습니다.", function() {
				$("#input-name").focus();
			});
			return ;
		}
		//2. 비밀번호 유효성(empty) 체크
		if($("#input-password").val() === '') {
			messageBox("방명록", "비밀번호가 비어 있습니다.", function() {
				$("#input-password").focus();
			});
			return ;
		}
		
		//3. 내용 유효성(empty) 체크
		if($("#tx-content").val() === '') {
			messageBox("방명록", "내용이 비어 있습니다.", function() {
				$("#tx-content").focus();
			});
			return ;
		}
		
		
		var vo = {};
		vo.name = $("#input-name").val();
		vo.password = $("#input-password").val();
		vo.message = $("#tx-content").val();
		
		console.log(vo);
		
		$.ajax({
			url: "${pageContext.request.contextPath }/api/guestbook",
			type: "post",
			dataType: "json",
			contentType: "application/json",
			data: JSON.stringify(vo),
			success: function(response) {
				if(response.result !== 'success'){
					console.error(response.message);
					return;
				}
				render(response.data);
				
				//form reset 
				$("#add-form")[0].reset(); // 돔 api임 제이쿼리꺼아님 추가후 리셋해서 입력된 거 지움 ㅇㅇ
			},error: function(xhr, status, e){
				console.error(status + ":" + e);
			}
		});
	});
})

var deleteContent = function(){
		$.ajax({
			url: "${pageContext.request.contextPath }/api/guestbook/"+$("#hidden-no").val(),
			type: "delete", // 딜리트는 그냥 딜리트 ㅇㅇ 
			dataType: "json",
			contentType: "application/x-www-form-urlencoded", 
			data: "password=" + $("#password-delete").val(),  
			success: function(response) {
				if(response.result != "success"){
					console.error(response.message);
					return;
				}
				
				if(response.data != -1){
					$("#list-guestbook li[data-no=" + response.data + "]").remove();
					dialogDelete.dialog('close');
					return;
				}
				console.log("들어왔는지 테스트 " + "password=" + $("#password-delete").val());
				$("#dialog-delete-form p.validateTips.error").show();
			},error: function(xhr, status, e){
				console.error(status + ":" + e);
			}
		});
}


$(function(){
	
	var vo = {};
	vo.password = $("#password-delete").val();
	
	// 삭제 다이알로그 jQuery객체를 미리 만들기
	var dialogDelete = $("#dialog-delete-form").dialog({
		autoOpen: false,
		modal: true,
		buttons: {
			"삭제": function() {
				console.log("password=" + $("#password-delete").val());
				console.log($("#hidden-no").val());
				console.log("${pageContext.request.contextPath }/api/guestbook/"+$("#hidden-no").val());
				deleteContent();
				
				//console.log("AJAX 삭제 하기");
			},
			"취소": function() {
				$(this).dialog('close');
			}
		},
		close: function() {
			//console.log("dialog close!!!");
			$("#hidden-no").val("");
			$("#password-delete").val("");
			$("#dialog-delete-form p.validateTips.error").hide();
			
		}
	});
	
	// 글 삭제 버튼 Click 이벤트 처리(Live Event)
		$(document).on('click', '#list-guestbook li a', function(event){
		event.preventDefault();
		
		$("#hidden-no").val($(this).data('no'));
		dialogDelete.dialog('open');
		
		
	});

});


$(function(){
	fetch();
});


</script>


</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="guestbook">
				<h1>방명록</h1>
				<form id="add-form" action="" method="post">
					<input type="text" id="input-name" placeholder="이름">
					<input type="password" id="input-password" placeholder="비밀번호">
					<textarea id="tx-content" placeholder="내용을 입력해 주세요."></textarea>
					<input type="submit" value="보내기" />
				</form>
				
				<ul id="list-guestbook">				
				</ul>
				
			</div>
			
			<div id="dialog-delete-form" title="메세지 삭제" style="display:none">
  				<p class="validateTips normal">작성시 입력했던 비밀번호를 입력하세요.</p>
  				<p class="validateTips error" style="display:none">비밀번호가 틀립니다.</p>
  				<form>
 					<input type="password" id="password-delete" value="" class="text ui-widget-content ui-corner-all">
					<input type="hidden" id="hidden-no" value="">
					<input type="submit" tabindex="-1" style="position:absolute; top:-1000px">
  				</form>
			</div>
			
			<div id="dialog-message" title="" style="display:none">
				<p style="line-height:60px"></p>
			</div>
							
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="guestbook-ajax"/>
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>