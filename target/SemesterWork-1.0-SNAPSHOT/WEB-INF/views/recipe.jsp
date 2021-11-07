<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout title="${recipe.title}">
    <!-- recipe -->
    <div class="recipe-view">
        <div class="container">
            <div class="preview">
                <div class="preparation h-100 mb-3">
                    <h2 class="recipe-title mt-2">Title</h2>
                    <ul class="ingredient-list-dots mb-2">
                        <div class="mb-3"><i class="fas fa-clock"></i><span class="ms-1">
<%--                            JAVASCRIPT FOR TIME --%>
                        </span></div>
                        <c:forEach var="ingredient" items="${recipe.ingredients}">
                        <li>
                            <span class="title">${ingredient.name}</span>
                            <span class="chapter">${ingredient.amount} ${ingredient.unit}</span>
                        </li>
                        </c:forEach>
                    </ul>
                </div>
                <div class="result-images h-100">
                    <c:choose>
                        <c:when test="${recipe.images}">
                        <div id="carouselExampleDark" class="carousel carousel-dark slide mt-2" data-bs-ride="carousel">
                        <div class="carousel-inner">
                            <c:forEach var="path" items="${recipe.images}">
                                <div class="carousel-item" data-bs-interval="4000">
                                    <img src="${path}" class="d-block" alt="...">
                                    <div class="carousel-caption d-none d-md-block">
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                        <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleDark" data-bs-slide="prev">
                            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                            <span class="visually-hidden">Previous</span>
                        </button>
                        <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleDark" data-bs-slide="next">
                            <span class="carousel-control-next-icon" aria-hidden="true"></span>
                            <span class="visually-hidden">Next</span>
                        </button>
                        </div>
                        </c:when>
                        <c:otherwise>
                            <h3 style="margin-top: 8vh; color: rgba(0,0,0,0.2)">No images added</h3>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
            <div class="description mx-auto">
                <h5 class="description-title mx-auto mb-3 mt-3">Description</h5>
                <div class="recipe-text mb-2">
                    <p>
<%--                        BEAUTIFUL TEXT VIEW WITH JS--%>
                    </p>
                </div>
            </div>
            <hr>
            <div class="action-icons d-flex justify-content-end mb-3">
<%--                JAVASCRIPT FOR CLICK--%>
                <div class="icon"><i class="fas fa-thumbs-up"></i></div>
                <div class="icon ms-3"><i class="fas fa-bookmark"></i></div>
<%--                JAVASCRIPT FOR CLICK--%>
            </div>
        </div>
    </div>
    <!-- recipe -->

    <div class="comment-content mb-2">
        <div class="container">
            <div class="new-comment mb-2">
                <!-- comment box -->
                <form action="#" class="comment-area-box mb-3">
                    <span class="input-icon">
                        <textarea rows="3" class="form-control mb-2" placeholder="Write something..."></textarea>
                    </span>
                    <div class="comment-area-btn">
                        <div class="float-end">
<%--                            JAVASCRIPT FOR ADDING COMMENTS--%>
                            <button type="submit" class="btn btn-sm btn-secondary waves-effect waves-light">Post</button>
<%--                            JAVASCRIPT FOR ADDING COMMENTS--%>
                        </div>
                        <div class="comment-actions d-flex justify-content-start">
                            <a href="" class="btn btn-sm btn-light text-black-50"><i class="fa fa-camera"></i></a>
                            <div class="rating-area">
                                <input type="radio" id="star-5" name="rating" value="5">
                                <label for="star-5" title="Rate «5»"></label>
                                <input type="radio" id="star-4" name="rating" value="4">
                                <label for="star-4" title="Rate «4»"></label>
                                <input type="radio" id="star-3" name="rating" value="3">
                                <label for="star-3" title="Rate «3»"></label>
                                <input type="radio" id="star-2" name="rating" value="2">
                                <label for="star-2" title="Rate «2»"></label>
                                <input type="radio" id="star-1" name="rating" value="1">
                                <label for="star-1" title="Rate «1»"></label>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
            <h5 class="comments-title mt-3">Comments</h5>
            <hr>
            <div class="old-comments">
                <c:forEach var="c" items="${recipe.comments}">
                    <t:comment comment="${c}"/>
                </c:forEach>
            </div>
        </div>
    </div>
    <!-- comment-content -->
</t:layout>
