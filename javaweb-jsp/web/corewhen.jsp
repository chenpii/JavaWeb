<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%--定义一个变量，值为85--%>
<c:set var="score" value="55"/>

<c:choose>
    <c:when test="${score>=90}">
        您的成绩为优秀
    </c:when>
    <c:when test="${score>=80}">
        您的成绩为良好
    </c:when>
    <c:when test="${score>=70}">
        您的成绩为一般
    </c:when>
    <c:when test="${score<60}">
        您的成绩为不合格
    </c:when>

</c:choose>
</body>
</html>
