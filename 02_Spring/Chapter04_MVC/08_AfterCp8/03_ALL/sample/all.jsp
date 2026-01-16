<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri = "http://www.springframework.org/security/tags" prefix = "sec" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>all.jsp</h1>
	
	<!-- 시큐리티의 if같은 느낌 -->
	<sec:authorize access="isAnonymous()"> <!-- 익명 사용자라면 -->
		<a href="/customLogin">Login</a>
	</sec:authorize>
	<sec:authorize access="isAuthenticated()"> <!-- 인증(로그인 한) 사용자 -->
		<a href="/customLogout">Logout</a>
	</sec:authorize>
	
</body>
</html>