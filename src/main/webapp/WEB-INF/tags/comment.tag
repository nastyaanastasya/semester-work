<%@tag description="header" pageEncoding="utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@attribute name="comment" type="ru.kpfu.models.Comment" %>

<!-- comment -->
<div class="comment-container">
    <div class="media d-flex justify-content-start">
        <div><img src="<c:url value="${comment.user.image}"/>"alt="profile-picture"></div>
        <div class="media-body ms-2">
            <h4 class="media-heading"><a href="${pageContext.request.contextPath}/profile?id=${comment.user.id}">${comment.user.username}</a></h4>
            <p>${comment.review}</p>
            <div class="info d-flex justify-content-between">
                <ul class="list-unstyled list-inline media-detail pull-left d-flex justify-content-start mt-1">
                    <li><i class="fa fa-calendar"></i>${comment.date}</li>
                </ul>
                <div class="rating-result mb-3" id="comment-rate">
                    <li><i class="fa fa-star"></i>${comment.rating}</li>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- comment -->