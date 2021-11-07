<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout title="Search">
    <!-- search-content -->
    <div class="search-content">
        <div class="container">

            <!-- search-area -->
            <div class="searh-area mb-3">
                <div class="search-form d-flex justify-content-center">
                    <select class="search-by form-select" aria-label="Default select example" style="width: 15vw;">
                        <option selected>Search by...</option>
                        <option value="1">Recipe</option>
                        <option value="2">User</option>
                    </select>
                    <div class="form-search-field ms-3" style="width: 40vw;">
                        <input type="text" class="form-control" placeholder="Search..." id="search-field">
                    </div>
                    <div class="btn search ms-2" >
                        <i class="fas fa-search"></i>
                    </div>
                </div>

            </div>
            <!-- search-area -->

            <!-- content-area -->
            <div class="content-area">
                <div class="container">
                    <div class="row row-cols-1 row-cols-md-3 row-cols-lg-4">

                    </div>
                </div>
            </div>
            <!-- content-area -->

        <%--JAVASCRIPT CODE GENERATED PAGINATION--%>
            <!-- pagination -->
            <nav class="d-flex justify-content-center mt-3">
                <ul class="pagination pagination-base pagination-boxed pagination-square mb-0">
                    <li class="page-item">
                        <a class="page-link no-border" href="">
                            <span aria-hidden="true">«</span>
                            <span class="sr-only">Previous</span>
                        </a>
                    </li>

                    <li class="page-item active"><a class="page-link no-border" href="">1</a></li>
<%--                    js--%>
                    <li class="page-item">
                        <a class="page-link no-border" href="">
                            <span aria-hidden="true">»</span>
                            <span class="sr-only">Next</span>
                        </a>
                    </li>
                </ul>
            </nav>
        </div>
    </div>
    <!-- pagination -->
    <%--    JAVASCRIPT CODE GENERATED PAGINATION--%>

    <!-- search-content -->

</t:layout>
