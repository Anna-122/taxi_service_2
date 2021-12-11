<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:if test="${not empty param.language}">
    <c:set var="language" value="${param.language}" scope="session"/>
</c:if>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="Locale"/>
<html lang="${sessionScope.language}">
<head>
    <link rel="stylesheet" href="styles/w3.css">
    <title><fmt:message key="title.admin"/></title>
</head>
<body class="" style="background: #080710">
<div class="w3-container w3-gradient w3-opacity ">
    <h1><fmt:message key="label.admin.taxi.system"/></h1>
</div>