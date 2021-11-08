<%@tag description="header" pageEncoding="utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@attribute name="user" type="ru.kpfu.models.User" %>
<div class="col h-100 mb-2" id="userCard">
    <div class="card">
        <c:choose>
            <c:when test="${user.image}">
                <a href="#"><img src="<c:url value="file:///${user.image}"/>" class="card-img-top" alt="profile-picture"></a>
            </c:when>
            <c:otherwise>
                <a href="#"><img src="<c:url value="/res/user_default.png"/>" class="card-img-top" alt="profile-picture"></a>
            </c:otherwise>
        </c:choose>
        <div class="card-body">
            <h5 class="card-title">${user.username}</h5>
            <hr>
            <div class="user-info d-flex justify-content-start">
                <div class="item"><i class="fas fa-star"></i><span>${user.rating}</span></div>
                <div class="item ms-2"><i class="fas fa-users"></i><span>${user.subscribers.size()}</span></div>
                <div class="item ms-2"><i class="fas fa-birthday-cake"></i><span>${user.recipes.size()}</span></div>
            </div>
        </div>
        <div class="card-footer">
            <small class="text-muted">${user.date}</small>
        </div>
    </div>
</div>