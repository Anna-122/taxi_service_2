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
<div class="w3-card-4">
    <div class="w3-container ">
        <form name="addCarForm" method="POST" action="controller">
            <input type="hidden" name="command" value="addCar"/>
            <input type="hidden" name="userid" value=${param.userid}>
            <div class="w3-text-white"><fmt:message key="text.car.number"/><br/></div>
            <input type="text" name="carnumber" value="" class="w3-input w3-animate-input w3-border w3-round-large"
                   style="width: 30%"/>
            <div class="w3-text-white"><fmt:message key="button.car.type"/><br/></div>
            <input type="text" name="cartype" value="" class="w3-input w3-animate-input w3-border w3-round-large"
                   style="width: 30%"/>
            <br/>
            <button type="submit" class="w3-btn w3-gradient w3-round-large w3-margin-bottom"><fmt:message
                    key="button.add.car"/></button>
            <label class="w3-text-red">${errorMessage}<br/>
    </div>
    </form>
</div>
</div>
<div class="w3-container w3-opacity w3-right-align w3-padding">
    <button class="w3-btn w3-gradient w3-round-large w3-border"
            onclick="location.href='/taxi_service/admin/controller?command=users'" ><fmt:message
            key="button.back.to.users"/>
    </button>
</div>
</body>
</html>
