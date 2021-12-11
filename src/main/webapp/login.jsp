<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:if test="${not empty param.language}">
    <c:set var="language" value="${param.language}" scope="session"/>
</c:if>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="Locale"/>

<html lang="${sessionScope.language}">
<head>
    <title> <fmt:message key="title"/></title>
    <link rel="stylesheet" href="styles/login.css">
</head>
<body>
<div class="background">
    <div class="shape"></div>
    <div class="shape"></div>
</div>
<form name="loginForm" method="POST" action="controller">
    <input type="hidden" name="command" value="login"/>
    <h3> <fmt:message key="label.log.in"/></h3>

    <label for="email"><fmt:message key="label.email"/> </label>
    <input type="text" placeholder="Email" id="email" name="email">

    <label for="password"><fmt:message key="label.password"/></label>
    <input type="password" placeholder="Password" id="password" name="password">

    <button> <fmt:message key="button.log.in"/></button>

</form>
<div>
    <button class="back-to-main w3-back-btn" onclick="location.href='/taxi_service'"> <fmt:message key="text.back"/></button>
</div>
</body>
</html>

