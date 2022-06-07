<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.servletContext.contextPath }/assets/css/board.css" rel="stylesheet" type="text/css">
</head>

<c:set var="kwd" value="${param.kwd==null?'':param.kwd}"/>

<c:set var="count" value='${boardCount}'></c:set>
<c:set var="currentPage" value='${param.p==null?1:param.p}'></c:set>
<c:set var="currentPage" value='${param.p>pageCount?pageCount:param.p}'></c:set>
<c:set var="currentPage" value='${param.p<1?1:param.p}'></c:set>


<fmt:parseNumber var="startpage" integerOnly="true" value="${(currentPage/5)*5}"></fmt:parseNumber>
<fmt:parseNumber var="startpage" integerOnly="true" value="${startpage<1?1:startpage}"></fmt:parseNumber>
<fmt:parseNumber var="endpage" integerOnly="true" value="${startpage+5}"></fmt:parseNumber>
<fmt:parseNumber var="endpage" integerOnly="true" value="${endpage>pageCount?pageCount:endpage}"></fmt:parseNumber>

<fmt:parseNumber var="pageCount" integerOnly="true" value="${count/5}"></fmt:parseNumber>
<fmt:parseNumber var="pageCount" integerOnly="true" value="${count%5==0?pageCount:pageCount+1}"></fmt:parseNumber>

<c:set var="myBoardlist" value="${boardList}"></c:set>

<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp"/>
		<div id="content">
			<div id="board">
				<form id="search_form" action="${pageContext.request.contextPath}/board?p=1" method="get">
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

				
					<c:forEach items="${myBoardlist}" var='vo' varStatus="status">
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
						<li><a href="${pageContext.request.contextPath}/board?p=1"&kwd=${kwd}>처음</a></li>

						<c:if test="${currentPage>1}">
							<li><a href="${pageContext.request.contextPath}/board?p=${currentPage-1}&kwd=${kwd}">◀</a></li>
						</c:if>

						<c:forEach var="i" begin="${startpage}" end="${endpage}">
							<c:if test="${i<=pageCount}">
								<c:choose>
									<c:when test="${currentPage==i}">
										<li class="selected">${i}</li>
									</c:when>
									<c:otherwise>
										<li><a href="${pageContext.request.contextPath}/board?p=${i}&kwd=${kwd}">${i}</a></li>
									</c:otherwise>
								</c:choose>
							</c:if>
						</c:forEach>

						<c:if test="${pageCount>currentPage}">
							<li><a href="${pageContext.request.contextPath}/board?p=${currentPage+1}&kwd=${kwd}">▶</a></li>
						</c:if>

						<li><a href="${pageContext.request.contextPath}/board?p=${pageCount}&kwd=${kwd}">끝</a></li>
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