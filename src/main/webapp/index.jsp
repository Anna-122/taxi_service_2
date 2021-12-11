<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:import url="header.jsp"/>
<c:if test="${not empty param.language}">
    <c:set var="language" value="${param.language}" scope="session"/>
</c:if>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="Locale"/>

<html lang="${sessionScope.language}">

<body>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="styles/index.css">
    <title></title>
</head>
<c:if test="${not empty justRegistered}">
    <label class="w3-text-white"> <fmt:message key="label.great"/></label>
    <c:remove var="justRegistered"/>
</c:if>
<c:choose>
    <c:when test="${empty currentUser}">
        <div class="w3-container w3-center">
            <div class="w3-bar w3-padding-large w3-padding-24">
                <button class="w3-btn w3-gradient w3-round-large w3-margin-bottom"
                        onclick="location.href='/taxi_service/registration.jsp'">
                    <fmt:message key="button.sign.up"/>
                </button>
                <button class="w3-btn w3-gradient w3-round-large w3-margin-bottom"
                        onclick="location.href='/taxi_service/login.jsp'">
                    <fmt:message key="button.sign.in"/>
                </button>
            </div>
        </div>
    </c:when>
    <c:when test="${not empty currentUser}">
        <c:import url="main.jsp"/>
    </c:when>
</c:choose>

</body>
</html>
