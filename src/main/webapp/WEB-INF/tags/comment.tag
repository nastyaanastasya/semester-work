<%@tag description="header" pageEncoding="utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@attribute name="comment" type="ru.kpfu.models.Comment" %>

<!-- comment -->
<div class="comment-container">
    <div class="media d-flex justify-content-between">
        <c:choose>
            <c:when test="${comment.user.image}">
                <div><img src="<c:url value="${comment.user.image}"/>"alt="profile-picture"></div>
            </c:when>
            <c:otherwise>
                <div><img src="<c:url value="/res/user_default.png"/>" alt="profile-picture"></div>
            </c:otherwise>
        </c:choose>
        <div class="media-body">
            <h4 class="media-heading">${comment.user.username}</h4>
            <p>
<%--                JAVASCRIPT CODE--%>
<%--                beautiful text view--%>
            </p>
            <div class="info d-flex justify-content-between">
                <ul class="list-unstyled list-inline media-detail pull-left d-flex justify-content-start mt-1">
                    <li><i class="fa fa-calendar"></i>${comment.date.toLocaleString()}</li>
                    <li><i class="like-btn fa fa-thumbs-up"></i>${comment.rating}</li>
                </ul>
                <div class="rating-result mb-3">
<%--                    JAVASCRIPT--%>
<%--                    generate number of active stars--%>
                    <span class="active"></span>
                    <span ></span>
                    <span ></span>
                    <span></span>
                    <span></span>
<%--                    JAVASCRIPT--%>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- comment -->