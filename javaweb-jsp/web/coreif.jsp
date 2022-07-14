<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--引入JSTL核心标签库，我们才能使用核心标签 core--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<h4>if测试</h4>

<hr>
<form action="" method="get">
    <%--
        EL表达式获取表单中的数据
        ${param.参数名}
        --%>
    <input type="text" name="username" value="${param.username}">
    <input type="submit" value="登录">
</form>
<%--判断如果提交的用户名是管理员，则登录成功--%>
<c:if test="${param.username=='admin'}" var="isAdmin">
    <c:out value="欢迎您，管理员！"/>
</c:if>

<c:out value="${isAdmin}"/>

</body>
</html>
