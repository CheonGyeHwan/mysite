<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>${site.title }</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath }/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp"/>
		<div id="content">
			<div id="board">
				<form id="search_form" action="${pageContext.request.contextPath }/board" method="post">
					<input type="text" id="kwd" name="keyword" value="">
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
					<c:forEach items='${map.list }' var='list' varStatus='status'>
						<tr>
							<td>${map.pagingVo.total - (map.pagingVo.currentPage - 1) * map.pagingVo.boardAmount - status.index }</td>
							<td style="text-align:left; padding-left:${(list.depth-1)*20 }px">
								<c:if test="${list.orderNo > 1 }">
									<img src="${pageContext.request.contextPath }/assets/images/reply.png"/>
								</c:if>
								<a href="${pageContext.request.contextPath }/board/view/${list.no}">${list.title }</a>
							</td>
							<td>${list.userName }</td>
							<td>${list.hit }</td>
							<td>${list.regDate }</td>
							<c:choose>
								<c:when test="${authUser.no == list.userNo }">
									<td><a href="${pageContext.request.contextPath }/board/delete/${list.no}" 
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
						<c:if test="${map.pagingVo.prev }">
							<li><a href="${pageContext.request.contextPath }/board/${map.pagingVo.startPage - 1 }">◀</a></li>
						</c:if>
						<c:forEach begin="${map.pagingVo.startPage }" end="${map.pagingVo.endPage }" step="1" var="currentPage">
							<c:choose>
								<c:when test="${empty map.pagingVo.keyword }">
									<li class="${map.pagingVo.currentPage == currentPage ? 'selected' : ''}"><a href="${pageContext.request.contextPath }/board/${currentPage }">${currentPage }</a></li>
								</c:when>
								<c:otherwise>
									<li class="${map.pagingVo.currentPage == currentPage ? 'selected' : ''}"><a href="${pageContext.request.contextPath }/board/${map.pagingVo.keyword }/${currentPage }">${currentPage }</a></li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
						<c:if test="${map.pagingVo.next }">
							<li><a href="${pageContext.request.contextPath }/board/${map.pagingVo.endPage + 1 }">▶</a></li>
						</c:if>
					</ul>
				</div>
			
				<c:if test="${not empty authUser }">
					<div class="bottom">
						<a href="${pageContext.request.contextPath }/board/write" id="new-book">글쓰기</a>
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