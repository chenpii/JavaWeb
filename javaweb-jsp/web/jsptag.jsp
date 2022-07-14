<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>1</h1>
<%--jsp:include--%>

<%--
http://localhost:8080/jsptag.jsp?name=kuangshen&age=12

--%>
<jsp:forward page="/jsptag2.jsp">
    <jsp:param name="name" value="kuangshen"/>
    <jsp:param name="age" value="12"/>
</jsp:forward>


</body>
</html>
