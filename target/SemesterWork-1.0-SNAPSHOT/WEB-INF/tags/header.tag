<%@tag description="header" pageEncoding="utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<header>
<!-- header -->
<div class="header">
    <!-- navbar -->
    <nav class="navbar navbar-expand-lg navbar-light p-3 mb-3 bg-light border-bottom">
        <div class="container-fluid">
            <div class="d-flex justify-content-start">
            <div class="icon"><img src="<c:url value="/res/icon.png"/>"></div>
            <div class="nav-link px-2 link-secondary"><h3>SweetLife</h3></div>
            </div>
            <c:if test="${requestScope.isAuth}">
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNavDropdown">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="${pageContext.request.contextPath}/profile">Home</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="${pageContext.request.contextPath}/search?content=recipes">Find recipe</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="${pageContext.request.contextPath}/search?content=users">Find user</a>
                    </li>
                </ul>
            </div>
                <div class="dropdown text-end d-flex">
                <div class="d-block link-dark text-decoration-none dropdown-toggle" id="dropdownUser1" data-bs-toggle="dropdown" aria-expanded="false">
                    <img src="<c:url value="/res/user-side-icon.png"/>" alt="mdo" class="rounded-circle">
                </div>
                <ul class="dropdown-menu text-small" aria-labelledby="dropdownUser1">
                    <li><a class="dropdown-item" href="${pageContext.request.contextPath}/profile">Profile</a></li>
                    <li><a class="dropdown-item" href="${pageContext.request.contextPath}/profile/settings">Settings</a></li>
                    <li><hr class="dropdown-divider"></li>
                    <li><a class="dropdown-item" href="${pageContext.request.contextPath}/logout">Sign out</a></li>
                </ul>
            </div>
            </c:if>
        </div>
    </nav>
    <!-- navbar -->
</div>
<!-- header -->
</header>
