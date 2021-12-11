<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:if test="${not empty param.language}">
    <c:set var="language" value="${param.language}" scope="session"/>
</c:if>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="Locale"/>

<html lang="${sessionScope.language}">
<fmt:message key="text.hi"/>, ${currentUser.name}
<br>
<form name="logoutForm" method="GET" action="controller">
    <input type="hidden" name="command" value="rideOrder">
    <button type="submit" class="w3-btn w3-gradient w3-round-large ">
        <fmt:message key="button.ride"/></button>
</form>
<form name="logoutForm" method="GET" action="controller">
    <input type="hidden" name="command" value="ridesStatistics"/>
    <input type="hidden" name="pageNumber" value="1"/>
    <button type="submit" class="w3-btn w3-gradient w3-round-large ">
        <fmt:message key="button.statistics"/></button>
</form>
<form name="logoutForm" method="POST" action="controller" class="w3-right-align">
    <input type="hidden" name="command" value="logout"/>
    <button type="submit" class="w3-btn w3-gradient w3-round-large w3-margin-top">
        <fmt:message key="text.log.out"/></button>
</form>

