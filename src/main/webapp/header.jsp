<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${not empty param.language}">
    <c:set var="language" value="${param.language}" scope="session"/>
</c:if>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="Locale"/>

<html lang="${sessionScope.language}">
<head>
    <link rel="stylesheet" href="styles/w3.css">
    <title><fmt:message key="label.h1"/></title>
</head>
<body class="" style="background-color: #080710">
<div class="w3-container w3-opacity" style="background: linear-gradient(to bottom, #3399ff 0%, #ffccff 100%)">
    <h1><fmt:message key="label.h1"/></h1>
    <button class="w3-icon english" onclick="location.href = '/taxi_service/?language=en'"></button>
    <button class="w3-icon russian" onclick="location.href = '/taxi_service/?language=ru'"></button>
</div>