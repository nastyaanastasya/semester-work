<%@tag description="header" pageEncoding="utf-16" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@attribute name="title" required="true" %>

<html>
    <head>
        <title>${title}</title>
        <t:head/>
    </head>
    <body>
        <t:header/>
        <jsp:doBody/>
        <t:footer/>
    </body>
</html>