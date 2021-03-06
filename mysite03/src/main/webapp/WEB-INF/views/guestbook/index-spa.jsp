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
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
	var sn = 0;

	var messageBox = function(title, message, callback) {
		$("#dialog-message p").text(message);
		$("#dialog-message")
			.attr("title", title)
			.dialog({
				width: 340,
      			modal: true,
      			buttons: {
      				"확인": function() {
      					$(this).dialog("close");
      				}
      			},
      			close: callback
    		});
	};
	
	var render = function(vo) {
		var html = 
			"<li data-no='" + vo.no + "'>" +
			"<strong>" + vo.name + "</strong>" +
			"<p>" + vo.message.replace(/\n/gi, "<br/>") + "</p>" +
			"<strong></strong>" +
			"<a href='' data-no='" + vo.no + "'>삭제</a>" + 
			"</li>";
			
	 	return html;
	};

	var fetch = function() {
		var nextIdx = $("#list-guestbook li:last-child").data("no") == null ? 0 : $("#list-guestbook li:last-child").data("no");
		
		// 최초 fetch 이후 중복 실행 검사
		if (nextIdx != 0 && sn == nextIdx) {
			return ;
		}
		 
		sn = nextIdx;
		
		$.ajax({
			url : "${pageContext.request.contextPath }/api/guestbook?sn=" + sn,
			type : "get",
			dataType : "json",
			success : function(response) {
				if (response.result !== "success") {
					console.error(response.message);
					return ;
				}
			
				response.data.forEach(function(data) {
					var html = render(data);
					$("#list-guestbook").append(html);
				})
			}
		});
	};
	
	var addMessage = function(vo) {
		if (vo.name === "") {
			messageBox("방명록", "이름을 적어주세요.", function() {
				$("#input-name").focus();
			});
			return  ;
		}
		
		if (vo.password === "") {
			messageBox("방명록", "비밀번호를 적어주세요.", function() {
				$("#input-password").focus();
			});
			return  ;
		}
		
		if (vo.message === "") {
			messageBox("방명록", "방명록을 적어주세요.", function() {
				$("#tx-content").focus();
			});
			return ;
		}
	
		$.ajax({
			url : "${pageContext.request.contextPath }/api/guestbook",
			type : "post",
			dataType : "json",
			contentType : "application/json",
			data : JSON.stringify(vo),
			success : function(response) {
				if (response.result !== "success") {
					console.error(response.message);
					return ;
				}
				
				var html = render(vo);
				$("#list-guestbook").prepend(html);
				
				$("#input-name").val("");
				$("#input-password").val("");
				$("#tx-content").val("");
			}
		});
	};
	
	$(function() {
		// 최초 리스트 받기
		fetch();
		
		// Insert
		$("#add-form").submit(function(event) {
			event.preventDefault();
			
			var vo = {};
			vo.name = $("#input-name").val();
			vo.password = $("#input-password").val();
			vo.message = $("#tx-content").val();
			
			addMessage(vo);
		})
		
		// Delete
		$(document).on("click", "#list-guestbook li a", function(event) {
			event.preventDefault();
			var no = $(this).data("no");
			$("#hidden-no").val(no);
			
			dialogDelete.dialog("open");
		})
		
		var dialogDelete =  $("#dialog-delete-form").dialog({
			autoOpen : false,
			modal : true,
			buttons : {
				"삭제" : function() {
					var no = $("#hidden-no").val();
					var password = $("#password-delete").val();
					var url = "${pageContext.request.contextPath }/api/guestbook/" + no;
					
					$.ajax({
						url : url,
						type : "delete",
						dataType : "json",
						data : "password=" + password,
						success : function(response) {
							if (response.result !== "success") {
								console.error(response.message);
								return ;
							}
							
							if (!response.data) {
								$(".validateTips.error").show();
								$("#password-delete").val("").focus();
								return ;
							}
							
							$("#list-guestbook li[data-no = '" + no + "']").remove();
							dialogDelete.dialog("close");
						}	
					})
				},
				"취소" : function() {$(this).dialog("close")}
			},
			close : function() {
				$("#password-delete").val("");
				$("#hidden-no").val("");
				$(".validateTips.error").hide();
			}
		});
		
		// Scroll
		var flag = true;
		
		$(window).scroll(function(e) {
			var $window = $(this);
			var $document = $(document);
			
			var windowHeight = $window.height();
			var documentHeight = $document.height();
			var scrollTop = $window.scrollTop();
			
			if ((scrollTop + windowHeight + 10 > documentHeight) && flag) {
				fetch();
				flag = false;
				return ;
			}
			
			flag = true;
		});
		
	})
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
				<ul id="list-guestbook"></ul>
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
  				<p style="line-height: 60px"></p>
			</div>						
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="guestbook-ajax"/>
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>