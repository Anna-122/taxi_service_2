<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="header.jsp"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:if test="${not empty param.language}">
    <c:set var="language" value="${param.language}" scope="session"/>
</c:if>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="Locale"/>

<html lang="${sessionScope.language}">
<head>
    <link rel="stylesheet" href="styles/ride.css">
</head>
<body>
<c:choose>
    <c:when test="${empty cost}">
        <jsp:useBean id="taxitypes" type="java.util.List<edu.goncharova.domain.TaxiType>" scope="request"/>
        <div class="w3-card-4">
            <div class="w3-container ">
                <form class="w3-form-ride" name="loginForm" method="POST" action="controller">
                    <input type="hidden" name="command" value="calculateCost"/>
                    <div class="w3-text-white"><fmt:message key="text.from"/></div>
                    <br/>
                    <input type="text" name="from" value=""/>
                    <br/>
                    <div class="w3-text-white"><fmt:message key="text.to"/></div>
                    <br/>
                    <input type="text" name="to" value=""/>
                    <br/>
                    <div class="w3-text-white"><fmt:message key="text.type.taxi"/></div>
                    <br/>
                    <select name="taxitype"
                            class="w3-ride-input w3-animate-input w3-border-ride w3-round-large w3-select">
                        <c:forEach var="taxiType" items="${taxitypes}">
                            <option value=${taxiType.taxiTypeName} class="w3-option"> ${taxiType.taxiTypeName} </option>
                        </c:forEach>
                    </select>
                    <button type="submit" class="w3-btn w3-gradient w3-ride-btn"><fmt:message
                            key="text.calculate.cost"/>
                    </button>
                </form>
            </div>
        </div>
    </c:when>
    <c:when test="${not empty cost}">
        <c:import url="rideInformation.jsp"/>
    </c:when>
</c:choose>
<div class="w3-container w3-opacity w3-right-align w3-padding">
    <button class="w3-btn w3-gradient w3-round-large w3-border" onclick="location.href='/taxi_service'">
        <fmt:message key="button.back"/>
    </button>
</div>

</body>
</html>
