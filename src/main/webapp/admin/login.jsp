<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%--<c:import url="header.jsp"/>--%>
<c:if test="${not empty param.language}">
    <c:set var="language" value="${param.language}" scope="session"/>
</c:if>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="Locale"/>

<html lang="${sessionScope.language}">

<%--<html>--%>
<head>
    <title><fmt:message key="title"/></title>
    <link rel="stylesheet" href="styles/login.css">
</head>
<body>
<div class="background">
    <div class="shape"></div>
    <div class="shape"></div>
</div>
<form name="loginForm" method="POST" action="controller">
    <input type="hidden" name="command" value="adminLogin"/>
    <h3><fmt:message key="label.log.in"/></h3>

    <label for="email"><fmt:message key="label.email"/></label>
    <input type="text" placeholder="Email" id="email" name="email">

    <label for="password"><fmt:message key="label.password"/></label>
    <input type="password" placeholder="Password" id="password" name="password">

    <button><fmt:message key="button.log.in"/></button>

</form>
<div>
    <button class="back-to-main w3-back-btn" onclick="location.href='/taxi_service/admin'"><fmt:message key="text.back"/></button>
</div>
</body>
</html>

<%--<div class="w3-card-4">--%>
<%--    <div class="w3-container ">--%>
<%--        <form name="loginForm" method="POST" action="controller">--%>
<%--           --%>
<%--            <div class="w3-text-black">Login:<br/></div>--%>
<%--            <input type="text" name="email" value="" class="w3-input w3-animate-input w3-border w3-round-large" style="width: 30%"/>--%>
<%--            <br/>Password:<br/>--%>
<%--            <input type="password" name="password" value="" class="w3-input w3-animate-input w3-border w3-round-large" style="width: 30%"/>--%>
<%--            <br/>--%>
<%--            <button type="submit" class="w3-btn w3-green w3-round-large w3-margin-bottom">Log in</button>--%>
<%--            <label class="w3-text-red">${errorMessageLogin}<br/></label>--%>
<%--        </form>--%>
<%--    </div>--%>
<%--</div>--%>
<%--<div class="w3-container w3-opacity w3-right-align w3-padding">--%>
<%--    <button class="w3-btn w3-green w3-round-large w3-border" onclick="location.href='/taxi_service/admin'">Back to main</button>--%>
<%--</div>--%>

<%--</body>--%>
<%--</html>--%>
