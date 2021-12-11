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
    <title>Register form</title>
    <link rel="stylesheet" href="styles/registration.css">
</head>
<body>
<div class="background">
    <div class="shape"></div>
    <div class="shape"></div>
</div>
<form name="RegistrationForm" action="controller" method="POST">
    <input type="hidden" name="command" value="registration"/>
    <div class="container">
        <h3><fmt:message key="text.registration"/></h3>
        <hr>

        <label for="name"><fmt:message key="label.name"/></label>
        <input class="input-field" id="name" type="text" name="name" required/>

        <label for="surname"><fmt:message key="text.surname"/></label>
        <input class="input-field" id="surname" type="text" name="surname" required/>

        <label for="email"><fmt:message key="label.email"/></label>
        <input class="input-field" id="email" type="email" name="email" required/>

        <label for="password"><fmt:message key="label.password"/></label>
        <input class="input-field" id="password" type="password" name="password" required/>

        <button type="submit" value="register" class="registerbtn"><fmt:message key="text.register"/></button>
    </div>
</form>
<label class="w3-text-red">${errorMessage}</label>--%>
<div class="">
    <button class="back-to-main" onclick="location.href='/taxi_service'"><fmt:message key="text.back"/></button>
</div>
</body>
</html>

<%--<div class="w3-card-4">--%>
<%--	<div class="w3-container ">--%>
<%--	<form name ="RegistrationForm" action="controller" method="POST">--%>
<%--        <input type="hidden" name="command" value="registration" />--%>
<%--		Email:<input type ="email" name="email" class="w3-input w3-animate-input w3-border w3-round-large" style="width: 30%"/>--%>
<%--		<br/>--%>
<%--		Password:<input type ="password" name="password" class="w3-input w3-animate-input w3-border w3-round-large" style="width: 30%"/>--%>
<%--		<br/>--%>
<%--		Name:<input type ="text" name="name" class="w3-input w3-animate-input w3-border w3-round-large" style="width: 30%"/>--%>
<%--		<br/>--%>
<%--		Surname:<input type ="text" name="surname" class="w3-input w3-animate-input w3-border w3-round-large" style="width: 30%"/>--%>
<%--		<br/>--%>
<%--		<button class="w3-btn w3-green w3-round-large w3-margin-bottom" type="submit">Sign up</button>--%>
<%--	</form>--%>
<%--		<label class="w3-text-red">${errorMessage}</label>--%>

<%--	</div>--%>
<%--</div>--%>
<%--<div class="w3-container w3-opacity w3-right-align w3-padding">--%>
<%--	<button class="w3-btn w3-green w3-round-large w3-border" onclick="location.href='/taxi_service'">Back to main</button>--%>
<%--</div>--%>
