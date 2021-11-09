<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout title="Search">
    <!-- search-content -->
    <div class="search-content">
        <div class="container">

            <!-- search-area -->
            <form class="search-form" method="post">
            <div class="searh-area mb-3">
                <div class="search-form d-flex justify-content-center">
                    <div class="form-search-field ms-3" style="width: 60vw;">
                        <input type="text" class="form-control" name="search-field" placeholder="Search..." id="search-field">
                    </div>
                    <button class="btn search ms-2"  type="submit" name="search-btn">
                        <i class="fas fa-search"></i>
                    </button>
                </div>
            </div>
            </form>
            <!-- search-area -->

            <!-- content-area -->
            <div class="content-area">
                <div class="container">
                    <div class="row row-cols-1 row-cols-md-3 row-cols-lg-4">

                    </div>
                </div>
            </div>
            <!-- content-area -->
        </div>
    </div>
    <!-- search-content -->

</t:layout>
