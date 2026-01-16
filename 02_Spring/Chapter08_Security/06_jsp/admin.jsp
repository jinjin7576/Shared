<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- 시큐리티 태그 라이브러리 --> 
<%@ taglib uri = "http://www.springframework.org/security/tags" prefix = "sec" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>admin.jsp</h1>
	
	<!-- principal은 CustomUser를 의미 (현재 사용자 정보)-->
	<p>principal : <sec:authentication property="principal"/> </p>
	<!--  -->
	<p>MemberVO : <sec:authentication property="principal.member"/> </p>
	<p>사용자 이름 : <sec:authentication property="principal.member.userName"/> </p>
	<p>사용자 아이디1 : <sec:authentication property="principal.member.userId"/> </p>
	<p>사용자 아이디2 : <sec:authentication property="principal.username"/> </p>
	<!-- 그러면 사용자 아이디1과 2는 해시코드도 동일한가? -->
	<p>사용자 권한 리스트 : <sec:authentication property="principal.member.authList"/> </p>
	
	<a href="/customLogout">Logout</a>
</body>
</html>