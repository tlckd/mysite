<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.servletContext.contextPath }/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<c:set var="count" value='${fn:length(boardList)}'></c:set>
<c:set var="pages" value='${count/5}'></c:set>
<c:set var="pageCount" value="${pages+(1-(pages%1))%1 }"></c:set>

<c:set var="currentPage" value='${param.p}'></c:set>

<fmt:parseNumber var="startpage" integerOnly="true" value="${(currentPage/5)*5}"></fmt:parseNumber>

<c:set var="endpage" value='${startpage+4}'></c:set>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp"/>
		<div id="content">
			<div id="board">
				<form id="search_form" action="" method="post">
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

				
					<c:forEach items="${boardList}" var='vo' varStatus="status">				
					<tr>
						<td>${count-status.index}</td>
						
						<td style="text-align: left; padding-left:${vo.depth*10}px">
							<c:if test="${vo.depth !=1 }">
								<img src="${pageContext.servletContext.contextPath}/assets/images/reply.png">
							</c:if>
							<a href="${pageContext.request.contextPath}/board?a=viewform&boardno=${vo.no}"> ${vo.title}</a>
						</td>
						
						<td>${vo.name }</td>
						
						<td>${vo.hit }</td>
						
						<td>${vo.regDate }</td>
						<c:if test="${not empty authUser and (authUser.no == vo.userNo)}">
							<td><a href="${pageContext.request.contextPath}/board?a=delete&boardno=${vo.no}" class="del">삭제</a></td>
						</c:if>
					</tr>
					</c:forEach>				
				</table>
				
				<!-- pager 추가 -->
				<div class="pager">
					<ul>
						<c:choose>
							<c:when test="${currentPage>1}">
								<li><a href="${pageContext.servletContext.contextPath }/board?p=${currentPage-1}">◀</a></li>
							</c:when>
						</c:choose>
						
						<c:choose>
							<c:when test="${startpage == currentPage}">
								<li class="selected">${startpage }</a></li>
							</c:when>
							<c:otherwise>
								<li><a href="${pageContext.servletContext.contextPath }/board?p=${startpage}">${startpage }</a></li>
							</c:otherwise>
						</c:choose>
						
						<!--  
						<c:choose>
							<c:when test="${startpage+1 == currentPage}">
								<li class="selected">${startpage+1 }</a></li>
							</c:when>
							<c:otherwise>
								<li><a href="${pageContext.servletContext.contextPath }/board?p=${startpage+1}">${startpage+1 }</a></li>
							</c:otherwise>
						</c:choose>
						-->
								
						
						<!--  <li><a href="">${startpage+2 }</a></li> -->
						<!-- 
						<li>${startpage+3 }</li>
						<li>${startpage+4 }</li>
						-->
						<li><a href="${pageContext.servletContext.contextPath }/board?p=${currentPage+1}">▶</a></li>

					</ul>
				</div>					
				<!-- pager 추가 -->
				
				<div class="bottom">
					<c:if test="${not empty authUser}">
						<a href="${pageContext.request.contextPath}/board?a=writeform" id="new-book">글쓰기</a>
					</c:if>
				</div>				
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="board"/>
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp"/>
	</div>
</body>
</html>