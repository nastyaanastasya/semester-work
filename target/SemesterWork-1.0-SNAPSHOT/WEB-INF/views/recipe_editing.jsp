<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout title="Recipe editing">
<script defer src="<c:url value="/js/recipeEditing.js"/>"></script>
    <!-- recipe-content -->
    <div class="recipe-content">
        <div class="container mb-3">
            <h3 class="title">Add new recipe</h3>
            <form class="recipe-form mx-auto" method="post" enctype="multipart/form-data">
            <div class="col">
                <div class="select mb-3"><input class="form-control" type="file"  aria-label="Select file" id="loaded-img" multiple accept="image/*">
                </div>
                <div class="container-content">
                    <div class="recipe-images ms-0 mb-3">
                        <div class="images-title text-center mb-2">Selected images:</div>
                        <div class="row row-cols-1 row-cols-md-2 g-4" id="selected-img">
                            <c:if test="${recipe.images}">
                                <script>
                                    showImages(${recipe.images});
                                </script>
                            </c:if>
                        </div>
                    </div>
                        <div class="recipe-attributes ms-3 mb-3 justify-content-end">
                            <div class="form-add">
                                <div class="inputs">
                                    <div class="title mb-3">
                                        <input type="text" class="form-control" id="title"  placeholder="Recipe title">
                                    </div>
                                    <div class="time-of-cooking mb-3">
                                        <span class="time-title">Time of cooking:</span>
                                        <input type="text" class="form-control ms-3" id="hours" placeholder="hh">
                                        <span class="colon ms-1">:</span>
                                        <input type="text" class="form-control ms-1" id="minutes" placeholder="mm">
                                    </div>
                                    <div class="ingredients mb-3">
                                        <div class="ingredients-title mb-3">Ingredients:</div>
                                        <c:choose>
                                            <c:when test="${recipe.ingredients}">
                                                <script>
                                                    <%--showIngredients(${recipe.ingredients});--%>
                                                </script>
                                            </c:when>
                                            <c:otherwise>
                                                <div class="ingredient-item">
                                                    <span class="ingredient-num">1.</span>
                                                    <input class="form-control ingredient ms-3" type="text"id="ingredient" name="ingredient" placeholder="Ingredient" style="width: 250px;">
                                                    <input class="form-control amount ms-3" type="text"id="amount" name="amount"  placeholder="Amount" style="width: 150px;">
                                                    <input class="form-control amount ms-3" type="text"id="unit" name="unit" placeholder="Unit" style="width: 150px;">
                                                    <i class="fas fa-plus ms-3 mt-2" id="new-ingr"></i>
                                                </div>
                                                <script>
                                                    <%--addIngredients(${recipe.ingredients}, 2);--%>
                                                </script>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                    <div class="form-floating mb-3">
                                        <textarea class="form-control"  id="description"></textarea>
                                        <label for="description">Describe recipe</label>
                                    </div>
                                </div>
                                <div class="action-buttons">
                                    <button type="submit" name="save-changes" name="save" class="btn btn-outline-success">Save</button>
                                    <button type="submit" id="recipe-delete-btn" name="delete" class="btn btn-outline-danger ms-2">Delete</button>
                                </div>
                            </div>
                        </div>
                </div>
            </div>
            </form>
        </div>
    </div>
    <!-- recipe-content -->
</t:layout>
