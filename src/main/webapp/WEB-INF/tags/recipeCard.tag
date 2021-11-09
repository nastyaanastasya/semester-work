<%@tag description="header" pageEncoding="utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@attribute name="recipe" type="ru.kpfu.models.Recipe" %>
<div class="col h-100 mb-2" id="recipeCard">
    <div class="card">
        <c:choose>
            <c:when test="${recipe.images.size()>0}">
                    <a href="${pageContext.request.contextPath}/recipe?id=${recipe.id}"><img src="<c:url value="${recipe.images.get(0)}" />" class="card-img-top" alt="recipe-image"></a>
            </c:when>
            <c:otherwise>
                <a href="${pageContext.request.contextPath}/recipe?id=${recipe.id}"><img src="<c:url value="/res/icon.png"/>" class="card-img-top" alt="recipe-image"></a>
            </c:otherwise>
        </c:choose>
        <div class="card-body">
            <h5 class="card-title">${recipe.title}</h5>
            <hr>
            <div class="recipe-info d-flex justify-content-start">
                <div class="item ms-2"><i class="fas fa-star"></i><span>${recipe.rating}</span></div>
            </div>
        </div>
        <div class="card-footer">
            <small class="text-muted">${recipe.date}</small>
        </div>
    </div>
</div>