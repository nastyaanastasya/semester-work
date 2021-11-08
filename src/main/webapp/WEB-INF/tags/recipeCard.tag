<%@tag description="header" pageEncoding="utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@attribute name="recipe" type="ru.kpfu.models.Recipe" %>
<div class="col h-100 mb-2" id="recipeCard">
    <div class="card">
        <c:choose>
            <c:when test="${recipe.image}">
                <a href="#"><img src="<c:url value="file:///${recipe.image}"/>" class="card-img-top" alt="..."></a>
            </c:when>
            <c:otherwise>
                <a href="#"><img src="<c:url value="/res/icon.png"/>" class="card-img-top" alt="..."></a>
            </c:otherwise>
        </c:choose>
        <div class="card-body">
            <h5 class="card-title">${recipe.title}</h5>
            <hr>
            <div class="recipe-info d-flex justify-content-start">
                <div class="item"><i class="fas fa-clock"></i><span>${recipe.timeOfCooking}</span></div>
                <div class="item ms-2"><i class="fas fa-star"></i><span>${recipe.rating}</span></div>
            </div>
        </div>
        <div class="card-footer">
            <small class="text-muted">${recipe.date}</small>
        </div>
    </div>
</div>