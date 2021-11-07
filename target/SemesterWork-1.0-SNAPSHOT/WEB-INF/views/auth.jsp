<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout title="Authentication">
    <div class="form-container">
        <h2>Authentication</h2>
        <form class="login-form mx-auto" method="post">
            <div class="mb-3">
                <label for="inputEmail" class="form-label">Email address</label>
                <input type="email" class="form-control" id="inputEmail" name="email" value="${email}" aria-describedby="emailHelp" required="true">
            </div>
            <div class="mb-3">
                <label for="inputPassword" class="form-label">Password</label>
                <input type="password" id="inputPassword" name="password" class="form-control" aria-describedby="passwordHelp" required="true">
            </div>
            <div class="feedback">
                <p class="text-danger text-center" id="helpMessage">${message}</p>
            </div>
            <div class="redirect registration">
                <p>
                    Don't have an account? <a class="page-redirect" href="${pageContext.request.contextPath}/registration">Sign Up</a>
                </p>
            </div>
            <div class="button-container">
                <button type="submit" name="sent" class="btn btn-secondary">Sign In</button>
            </div>
        </form>
    </div>
</t:layout>