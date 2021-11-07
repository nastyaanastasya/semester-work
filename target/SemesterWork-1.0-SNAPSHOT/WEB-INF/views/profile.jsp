<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
                        <c:choose>
                            <c:when test="${user.image != null}">
                                <div><img id="image-changed" src="<c:url value="file:///${user.image}"/>"alt="profile-picture"></div>
                            </c:when>
                            <c:otherwise>
                                <div><img src="<c:url value="/res/user_default.png"/>" alt="profile-picture"></div>
                            </c:otherwise>
                        </c:choose>
                        </div>
                        <ul class="list-group list-group-flush">
                            <li class="list-group-item username"><span id="username">${user.username}</span></li>
                            <li class="list-group-item email"><span id="email">${user.email}</span></li>
                            <li class="list-group-item rating"><span id="userRating">Rating: ${user.rating}</span></li>
                            <li class="list-group-item recipe_num"><span id="userRecipeNum">Recipes: <c:out value="${user.recipes.size()}" default="0"/></span></li>
                            <c:if test="${id}">
                            <li class="list-group-item recipe_num">
                                <button type="button" class="btn btn-outline-secondary ms-2 " style="width: 15vw; margin: 0 auto;">Follow</button>
                            </li>
                            </c:if>
                        </ul>
                    </div>
                </div>
                <hr>
                <ul class="nav nav-pills flex-column mb-auto" >
                    <li class="nav-item">
                        <a href="?content=res" class="nav-link link-dark" id="user-recipes"><i class="fas fa-birthday-cake"></i><span>Recipes</span></a>
                    </li>
                    <li class="nav-item">
                        <a href="?content=liked" class="nav-link link-dark" id="liked-recipes"><i class="fas fa-thumbs-up"></i><span>Liked recipes</span></a>
                    </li>
                    <li class="nav-item">
                        <c:if test="${not id}">
                            <a href="?content=saved" class="nav-link link-dark" id="saved-recipes"><i class="fas fa-bookmark"></i><span>Saved recipes</span></a>
                        </c:if>
                    </li>
                    <li class="nav-item">
                        <a href="?content=fol" class="nav-link link-dark" id="following"><i class="fas fa-heart"></i><span>Following</span></a>
                    </li>
                    <li class="nav-item">
                        <a href="?content=sub" class="nav-link link-dark" id="subscribers"><i class="fas fa-users"></i><span>Subscribers</span></a>
                    </li>
                </ul>
            </div>
        </div>
        <!-- user-info -->

        <!-- user-content -->
        <div class="user-content ms-3" id="userProfileContent">
            <div class="recipe-actions d-flex justify-content-between">
                <h3 class="user-content-title ms-3" id="profileContentTitle"><c:out value="${contentTitle}" default="Recipes"/></h3>
                <div class="buttons d-flex justify-content-end">
                    <select class="search-by form-select" id="profileSortCondition" aria-label="Default select example" style="width: 9vw;">
                        <option selected>Sort by</option>
                        <option id="new" name="sort" value="new">New</option>
                        <option id="old" name="sort" value="old">Old</option>
                        <option id="pop" name="sort" value="pop">Popular</option>
                    </select>
                    <c:if test="${not id}">
                        <button type="button" id="add-new-recipe-btn" class="btn btn-outline-secondary ms-2">Add new recipe</button>
                    </c:if>
                </div>
            </div>
            <div class="row row-cols-1 row-cols-md-4 g-4">
            <c:choose>
                <c:when test="${recipeList}">
                    <c:forEach var="recipe" items="${recipeList}" >
                        <t:recipeCard recipe="${recipe}"/>
                    </c:forEach>
                </c:when>
                <c:when test="${userList}">
                    <c:forEach var="user" items="${userList}" >
                        <t:userCard user="${user}"/>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <h1 class="no-content-text mx-auto" style="color: rgba(0,0,0,0.1);margin-top: 15vh;width: auto;">No content here</h1>
                </c:otherwise>
            </c:choose>
            </div>
        </div>
        <!-- user-content -->
    </div>
    <!-- page content -->
</t:layout>

