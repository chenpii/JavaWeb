<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<%--
    public static final int PAGE_SCOPE		= 1;
    public static final int REQUEST_SCOPE	= 2;
    public static final int SESSION_SCOPE	= 3;
    public static final int APPLICATION_SCOPE	= 4;

    //scope作用域
    public void setAttribute(String name, Object attribute, int scope) {
        switch(scope) {
        case 1:
            this.mPage.put(name, attribute);
            break;
        case 2:
            this.mRequest.put(name, attribute);
            break;
        case 3:
            this.mSession.put(name, attribute);
            break;
        case 4:
            this.mApp.put(name, attribute);
            break;
        default:
            throw new IllegalArgumentException("Bad scope " + scope);
        }

    }
--%>
<%
    pageContext.setAttribute("hello1", "hello1", PageContext.SESSION_SCOPE);
    //等价于
    //session.setAttribute("hello1","hello1");
%>
</body>
</html>
