<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout title="Recipe">
    <script defer src="<c:url value="/js/recipeEditing.js"/>"></script>
    <script defer src="<c:url value="/js/recipeSaving.js"/>"></script>
    <!-- recipe-content -->
    <div class="recipe-content">
        <div class="container mb-3">
            <h3 class="title">Recipe</h3>
            <form class="recipe-form mx-auto" id="form-editing" method="POST" enctype="multipart/form-data">
                <div class="col">
                    <div class="select mb-3"><input class="form-control" type="file" name="loaded-img"
                                                    aria-label="Select file" id="loaded-img" multiple accept="image/*">
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
                                        <input type="text" class="form-control" id="title" value="${title}" name="recipe-title"
                                               placeholder="Recipe title">
                                    </div>
                                    <div class="time-of-cooking mb-3">
                                        <span class="time-title">Time of cooking:</span>
                                        <input type="text" class="form-control ms-3" name="hours" placeholder="hh">
                                        <span class="colon ms-1">:</span>
                                        <input type="text" class="form-control ms-1" name="minutes" placeholder="mm">
                                    </div>
                                    <div class="ingredients mb-3" id="ingredients-view">
                                        <div class="ingredients-title mb-3">Ingredients:</div>
                                        <div class="ingredient-item mb-2">
                                            <span class="ingredient-num">1.</span>
                                            <input class="form-control ingredient ms-3" type="text" id="ingredient"
                                                   value="${recipe.ingredients.get(0).name}" name="ingredient"
                                                   placeholder="Ingredient" style="width: 250px;">
                                            <input class="form-control amount ms-3" type="text" id="amount"
                                                   value="${recipe.ingredients.get(0).amount}" name="amount"
                                                   placeholder="Amount" style="width: 150px;">
                                            <input class="form-control amount ms-3" type="text" id="unit"
                                                   value="${recipe.ingredients.get(0).unit}" name="unit"
                                                   placeholder="Unit" style="width: 150px;">
                                            <div class="new-ingredient"><i class="fas fa-plus ms-3 mt-2"></i></div>
                                        </div>
                                        <c:if test="${recipe.ingredients}">
                                            <c:forEach var="ingr"
                                                       items="${recipe.ingredients.sublist(1, recipe.ingredients.size())}">
                                                <script>
                                                    addNextIngredient(${ingr.name}, ${ingr.amount}, ${ingr.unit});
                                                </script>
                                            </c:forEach>
                                        </c:if>
                                    </div>
                                    <div class="form-floating mb-3">
                                        <textarea class="form-control" name="description" id="description">
                                            <c:if test="${recipe.description}">
                                                ${recipe.description}
                                            </c:if>
                                        </textarea>
                                        <label for="description">Describe recipe</label>
                                    </div>
                                    <div class="feedback">
                                        <p class="text-danger text-center" id="helpMessage">${message}</p>
                                    </div>
                                </div>
                                <div class="action-buttons">
                                    <button type="submit" name="save-changes" id="save-changes"
                                            class="btn btn-outline-success">Save
                                    </button>
                                    <button type="submit" id="recipe-delete-btn" name="delete" value="${recipe.id}"
                                            class="btn btn-outline-danger ms-2">Delete
                                    </button>
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
