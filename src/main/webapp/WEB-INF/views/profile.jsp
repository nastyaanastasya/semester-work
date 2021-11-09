<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout title="Profile">
    <script defer src="<c:url value="/js/profileActions.js"/>"></script>
    <!-- page content -->
    <div class="profile-content" id="profileContent">
        <!-- user-info -->
        <div class="side-bar ms-3 bg-light" id="profileSideBar">
            <div class="p-2 ">
                <div class="user-info">
                    <div class="wrapper">
                        <div class="text-center" id="text-center">
                            <div><img src="<c:url value="${user.image}"/>" alt="profile-picture"></div>
                        </div>
                        <ul class="list-group list-group-flush">
                            <li class="list-group-item username"><span id="username">${user.username}</span></li>
                            <li class="list-group-item email"><span id="email">${user.email}</span></li>
                            <li class="list-group-item rating"><span id="userRating">Rating: ${rating}</span></li>
                            <li class="list-group-item recipe_num"><span id="userRecipeNum">
                                Recipes: <c:out value="${user.recipes.size()}" default="0"/></span>
                            </li><c:if test="${user.id != sessionScope.user.id}">
                                <li class="list-group-item recipe_num">
                                    <button type="button" id="follow-btn" class="btn btn-outline-secondary ms-2 "
                                            style="width: 15vw; margin: 0 auto;">Follow
                                    </button>
                                </li>
                            </c:if>
                        </ul>
                    </div>
                </div>
                <hr>
                <ul class="nav nav-pills flex-column mb-auto">
                    <li class="nav-item">
                        <a href="?content=res&id=${user.id}" class="nav-link link-dark"><i class="fas fa-birthday-cake"></i><span>Recipes</span></a>
                    </li>
                    <li class="nav-item">
                        <a href="?content=liked&id=${user.id}" class="nav-link link-dark"><i class="fas fa-thumbs-up"></i><span>Liked recipes</span></a>
                    </li>
                    <li class="nav-item">
                        <c:if test="${user.id == sessionScope.user.id}">
                            <a href="?content=saved&id=${user.id}" class="nav-link link-dark"><i class="fas fa-bookmark"></i><span>Saved recipes</span></a>
                        </c:if>
                    </li>
                    <li class="nav-item">
                        <a href="?content=fol&id=${user.id}" class="nav-link link-dark"><i
                                class="fas fa-heart"></i><span>Following</span></a>
                    </li>
                    <li class="nav-item">
                        <a href="?content=sub&id=${user.id}" class="nav-link link-dark"><i
                                class="fas fa-users"></i><span>Subscribers</span></a>
                    </li>
                </ul>
            </div>
        </div>
        <!-- user-info -->

        <!-- user-content -->
        <div class="user-content ms-3" id="userProfileContent">
            <div class="recipe-actions d-flex justify-content-between">
                <h3 class="user-content-title ms-3" id="profileContentTitle">${contentTitle}</h3>
                <div class="buttons d-flex justify-content-end">
                    <c:if test="${user.id eq sessionScope.user.id}">
                        <button type="button" id="add-new-recipe-btn" class="btn btn-outline-secondary ms-2">Add new
                            recipe
                        </button>
                    </c:if>
                </div>
            </div>
            <div class="row row-cols-1 row-cols-md-4 g-4">
                <c:choose>
                    <c:when test="${recipes && recipeList.size()>0}">
                        <c:forEach var="recipe" items="${recipeList}">
                            <t:recipeCard recipe="${recipe}"/>
                        </c:forEach>
                    </c:when>
                    <c:when test="${users && userList.size()>0}">
                        <c:forEach var="user" items="${userList}">
                            <t:userCard user="${user}"/>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <h1 class="no-content-text mx-auto"
                            style="color: rgba(0,0,0,0.1);margin-top: 15vh;width: auto;">No content here</h1>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
        <!-- user-content -->
    </div>
    <!-- page content -->
</t:layout>

