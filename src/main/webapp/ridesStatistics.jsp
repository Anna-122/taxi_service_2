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
<div class="w3-container w3-center">
    <div class="w3-bar w3-padding-large w3-padding-24">
        <table class="w3-table-all w3-light-green">
            <tr>
                <td> <fmt:message key="text.car.number"/></td>
                <td><fmt:message key="text.cost"/></td>
                <td> <fmt:message key="text.distance"/></td>
                <td><fmt:message key="text.start.time"/>e</td>
                <td><fmt:message key="text.end.time"/></td>
            </tr>
            <jsp:useBean id="rides" type="java.util.List<edu.goncharova.domain.Ride>" scope="request"/>
            <jsp:useBean id="carNumbers" type="java.util.List<java.lang.String>" scope="request"/>
            <c:forEach var="ride" items="${rides}" varStatus="ridesCount">
                <tr>
                    <td>${carNumbers[ridesCount.count-1]}</td>
                    <td>${ride.cost}</td>
                    <td>${ride.distance}</td>
                    <td>${ride.rideStart}</td>
                    <td>${ride.rideFinish}</td>
                </tr>
            </c:forEach>
        </table>
        <c:if test="${param.pageNumber>1}"><a
                href="controller?command=ridesStatistics&pageNumber=${param.pageNumber-1}">Previous page</a></c:if>
        <c:if test="${param.pageNumber<pageAmount}"><a
                href="controller?command=ridesStatistics&pageNumber=${param.pageNumber+1}">Next page</a></c:if>

        <label class="w3-text-red">${errorMessage}</label>

        </form>
        <div class="w3-container w3-opacity w3-right-align w3-padding">
            <button class="w3-btn w3-gradient w3-round-large" onclick="location.href='/taxi_service'">
                <fmt:message key="button.back"/>
            </button>
        </div>

        <br><br>
    </div>
</div>
</html>


