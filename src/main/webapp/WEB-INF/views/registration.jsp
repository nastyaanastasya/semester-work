<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout title="Registration">
    <div class="form-container">
        <h2>Registration</h2>
        <form class="registration-form mx-auto" method="post">
            <div class="mb-3">
                <label for="inputUsername" class="form-label">Username</label>
                <input type="text" class="form-control" id="inputUsername" value="${requestScope.username}" name="username" required="true">
            </div>
            <div class="mb-3">
                <label for="inputEmail" class="form-label">Email address</label>
                <input type="email" class="form-control" id="inputEmail" name="email" value="${requestScope.email}" aria-describedby="emailHelp" required="true">
            </div>
            <div class="mb-3">
                <label for="inputPassword" class="form-label">Password</label>
                <input type="password" id="inputPassword" name="password" class="form-control" aria-describedby="passwordHelp" required="true">
                <div id="passwordHelp" class="form-text">
                    Your password must be 8-20 characters long, contain upper and lower case letters, special characters and numbers, and must not contain spaces or emoji.
                </div>
            </div>
            <div class="mb-3">
                <label for="repPassword" class="form-label">Repeat Password</label>
                <input type="password" id="repPassword" name="repPassword" class="form-control" required="true">
                <div class="feedback">
                    <p class="text-danger text-center" id="helpMessage">${message}</p>
                </div>
                <div class="redirect login">
                    <p>
                        Do you already have an account? <a class="page-redirect" href="${pageContext.request.contextPath}/login">Sign In</a>
                    </p>
                </div>
                <div class="button-container">
                    <button type="submit" name="sent" class="btn btn-secondary">Sign Up</button>
                </div>
            </div>
        </form>
    </div>
</t:layout>