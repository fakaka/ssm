<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>

<head>
<meta charset="utf-8">

</head>
<body>
	list
	<%=request.getAttribute("users")%>
	<c:forEach var="user" items="${users}">
		<ul>
			<li>${user.name}</li>
		</ul>
	</c:forEach>

</body>

</html>