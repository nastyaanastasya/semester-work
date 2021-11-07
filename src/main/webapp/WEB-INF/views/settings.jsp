<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout title="Settings">
<script defer src="<c:url value="/js/settingsActions.js"/>"></script>
<!-- settings-content -->
<div class="settings-content">
    <div class="container">
        <div class="col d-flex justify-content-center">
            <div class="editing">
                <div class="col mb-3">
                    <div class="card">
                        <div class="card-body">
                            <div class="editing-profile">
                                <div class="row">
                                    <div class="col-12 col-sm-auto mb-3">
                                        <div class="mx-auto">
                                            <c:choose>
                                                <c:when test="${user.image != null}">
                                                    <a href="#"><img class="image-changed" id="image-changed" src="<c:url value="file:///${user.image}"/>" alt="profile-picture"></a>
                                                </c:when>
                                                <c:otherwise>
                                                    <a href="#"><img class="image-changed" id="image-changed" src="<c:url value="/res/user_default.png"/>"  alt="profile-picture"></a>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>
                                    <div class="col d-flex flex-column flex-sm-row justify-content-between mb-3">
                                        <div class="text-sm-left mb-3 mb-sm-0">
                                            <form method="post" enctype="multipart/form-data">
                                            <h4 class="pt-sm-2 pb-1 mb-3 text-nowrap">${user.username}</h4>
                                            <div class="mt-3">
                                                <button class="btn btn-outline-secondary" id="btn-file-input-settings" type="button">
                                                    <i class="fa fa-fw fa-camera"></i>
                                                    <span>Change Photo</span>
                                                    <input id="file-input-settings" type="file" name="uploaded-image" accept="image/*" style="display: none;" />
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="tab-content pt-3">
                                    <div class="tab-pane active">
                                            <div class="row">
                                                <div class="col">
                                                    <div class="row">
                                                        <div class="col">
                                                            <div class="form-group">
                                                                <label>Username</label>
                                                                <input class="form-control" type="text" name="new-username" value="${user.username}" placeholder="New username">
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col col-sm-6 mb-3">
                                                    <div class="row">
                                                        <div class="col">
                                                            <div class="form-group">
                                                                <label>Current Password</label>
                                                                <input class="form-control" name="cur-pass" type="password">
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col">
                                                            <div class="form-group">
                                                                <label>New Password</label>
                                                                <input class="form-control" name="new-pass" type="password">
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col">
                                                            <div class="form-group">
                                                                <label><span class="d-none d-xl-inline">Repeat Password</span></label>
                                                                <input class="form-control" name="rep-new-pass" type="password">
                                                            </div>
                                                            <p class="text-danger text-center" id="helpMessage">${message}</p>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col d-flex justify-content-end">
                                                    <button class="btn btn-outline-danger" id="delete-account-btn" type="button">Delete Account</button>
                                                    <button class="btn btn-outline-secondary ms-2" id="save-changes-btn" name="save-changes" type="submit">Save Changes</button>
                                                </div>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
    <!-- settings-content -->
</t:layout>
