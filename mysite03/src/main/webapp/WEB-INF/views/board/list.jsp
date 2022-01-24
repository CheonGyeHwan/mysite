<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath }/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp"/>
		<div id="content">
			<div id="board">
				<form id="search_form" action="${pageContext.request.contextPath }/board" method="post">
					<input type="text" id="kwd" name="kwd" value="">
					<input type="submit" value="찾기">
				</form>
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>
					<c:forEach items='${list }' var='list' varStatus='status'>
						<tr>
							<td>${pageMakerDto.total - (pageMakerDto.cri.pageNum - 1) * pageMakerDto.cri.amount - status.index }</td>
							<td style="text-align:left; padding-left:${(list.depth-1)*20 }px">
								<c:if test="${list.orderNo > 1 }">
									<img src="${pageContext.request.contextPath }/assets/images/reply.png"/>
								</c:if>
								<a href="${pageContext.request.contextPath }/board?a=hit&no=${list.no}">${list.title }</a>
							</td>
							<td>${list.userName }</td>
							<td>${list.hit }</td>
							<td>${list.regDate }</td>
							<c:choose>
								<c:when test="${authUser.no == list.userNo }">
									<td><a href="${pageContext.request.contextPath }/board?a=delete&no=${list.no}" 
								  	   class="del" 
							       	   style="background-image:url('${pageContext.request.contextPath }/assets/images/recycle.png')">삭제</a>
									</td>
								</c:when>
								<c:otherwise>
									<td></td>
								</c:otherwise>
							</c:choose>
						</tr>
					</c:forEach>
				</table>			
				
				<div class="pager">
					<ul>
						<c:if test="${pageMakerDto.prev }">
							<li><a href="${pageContext.request.contextPath }/board?pageNum=${pageMakerDto.startPage - 1 }">◀</a></li>
						</c:if>
						<c:forEach begin="${pageMakerDto.startPage }" end="${pageMakerDto.endPage }" step="1" var="pageNumber">
							<c:choose>
								<c:when test="${empty pageMakerDto.searchKeword}">
									<li class="${pageMakerDto.cri.pageNum == pageNumber ? 'selected' : ''}"><a href="${pageContext.request.contextPath }/board?pageNum=${pageNumber }">${pageNumber }</a></li>
								</c:when>
								<c:otherwise>
									<li class="${pageMakerDto.cri.pageNum == pageNumber ? 'selected' : ''}"><a href="${pageContext.request.contextPath }/board?pageNum=${pageNumber }&kwd=${pageMakerDto.searchKeword }">${pageNumber }</a></li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
						<c:if test="${pageMakerDto.next }">
							<li><a href="${pageContext.request.contextPath }/board?pageNum=${pageMakerDto.endPage + 1 }">▶</a></li>
						</c:if>
					</ul>
				</div>	
			
				<c:if test="${not empty authUser }">
					<div class="bottom">
						<a href="${pageContext.request.contextPath }/board?a=writeform" id="new-book">글쓰기</a>
					</div>
				</c:if>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="board"/>
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp"/>
	</div>
</body>
</html>