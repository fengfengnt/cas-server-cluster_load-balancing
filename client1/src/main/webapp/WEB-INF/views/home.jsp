<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>
	Hello world!  
</h1>

<P>  The time on the server 1 is ${serverTime}. <a href="<c:url value='/j_spring_cas_security_logout'/>">Logout</a></P>
<img alt="Image" src="<c:url value='/resources/imgs/1.png'/>"/>
</body>
</html>
