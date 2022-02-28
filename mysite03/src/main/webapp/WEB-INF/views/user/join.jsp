<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>${site.title }</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath }/assets/css/user.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
<script>
	$(function() {
		$("#btn-checkemail").click(function() {
			$.ajax({
				url : "${pageContext.request.contextPath }/user/api/checkemail?email=test@test.com",
				type : "get",
				dataType : "json",
				success : function(response) {
					console.log(response);
				},
				error : function(xhr, status, e) {
					console.error(status, e);
				}
			});
		});
	});
</script>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp"/>
		<div id="content">
			<div id="user">

				<form:form
				modelAttribute="userVo" 
				id="join-form" 
				name="joinForm" 
				method="post" 
				action="${pageContext.request.contextPath }/user/join">
				
					<label class="block-label" for="name">이름</label>
					<form:input path="name"/>
					<p style="text-align:left; padding-left:0; color:red">
						<spring:hasBindErrors name="userVo">
							<c:if test="${errors.hasFieldErrors('name') }">
								<spring:message code="${errors.getFieldError('name').codes[0] }"/>
							</c:if>
						</spring:hasBindErrors>
					</p>
					
					<label class="block-label" for="email"><spring:message code="user.join.label.email"/></label>
					<form:input path="email"/>
					<input type="button" id="btn-checkemail" value="중복체크">
					<p style="text-align:left; padding-left:0; color:red">
						<form:errors path="email"/>
					</p>
					
					
					<label class="block-label">패스워드</label>
					<form:password path="password"/>
					<p style="text-align:left; padding-left:0; color:red">
						<form:errors path="password"/>
					</p>
					
					
					<fieldset>
						<legend>성별</legend>
						<form:radiobutton path="gender" value="female" label="여"/>
						<form:radiobutton path="gender" value="male" label="남"/>
					</fieldset>
					
					<fieldset>
						<legend>약관동의</legend>
						<input id="agree-prov" type="checkbox" name="agreeProv" value="y">
						<label>서비스 약관에 동의합니다.</label>
					</fieldset>
					
					<input type="submit" value="가입하기">
					
				</form:form>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp"></c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp"></c:import>
	</div>
</body>
</html>