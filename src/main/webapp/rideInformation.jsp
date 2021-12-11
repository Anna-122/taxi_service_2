<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:if test="${not empty param.language}">
    <c:set var="language" value="${param.language}" scope="session"/>
</c:if>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="Locale"/>

<html lang="${sessionScope.language}">

<div class="w3-card-4">
    <div class="w3-container ">
        <label class="w3-text-white">Distance is ${distance} km, your discount is ${discount}, cost is $${cost}</label><br/>
        <label class="w3-text-white">Car number is ${taxi.carNumber}, IT IS - ${arrivalTime} minute<c:if test="${arrivalTime mod 10 !=1}">s</c:if></label>
        <c:choose>
            <c:when test="${empty approved}">
                <form action="controller" method="post">
                    <input type="hidden" name="cost" value=${cost} />
                    <input type="hidden" name="carNumber" value=${taxi.carNumber} />
                    <input type="hidden" name="distance" value=${distance} />
                    <input type="hidden" name="command" value="ride">
                    <input type="hidden" name="discount" value=${discount} />
                    <input type="hidden" name="arrivalTime" value=${arrivalTime} />
                    <input type="hidden" name="approved" value="true" />
                    <button  type="submit" class="w3-btn w3-gradient w3-round-large"> <fmt:message key="button.approve"/></button>
                </form>
                <form action="controller" method="post">
                    <input type="hidden" name="command" value="ride"/>
                    <input type="hidden" name="approved" value="false" />
                    <button  type="submit" class="w3-btn w3-gradient w3-round-large"> <fmt:message key="button.refuse"/></button>
                </form>
            </c:when>
            <c:when test="${not empty approved}">
                <br><label class="w3-text-white"> <fmt:message key="text.driver.is.going"/></label>
            </c:when>
        </c:choose>
    </div>
</div>