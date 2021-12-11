<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="../header.jsp"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:if test="${not empty param.language}">
    <c:set var="language" value="${param.language}" scope="session"/>
</c:if>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="Locale"/>

<html lang="${sessionScope.language}">

<div class="w3-card-4">
    <div class="w3-container ">

        <form name="updateTaxiTypeForm" method="POST" action="controller">
            <input type="hidden" name="command" value="updateTaxiType"/>
            <input type="hidden" name="taxitypeid" value="${param.taxitypeid}">
            <div class="w3-text-white"><fmt:message key="text.taxi.type.name"/><br/></div>
            <input type="text" name="name" value="${param.taxitypename}"
                   class="w3-input w3-animate-input w3-border w3-round-large" style="width: 30%"/>
            <div style="color: #ffffff"><fmt:message key="text.taxi.type.fare"/><br/></div>
            <input type="text" name="fare" value="${param.fare}"
                   class="w3-input w3-animate-input w3-border w3-round-large" style="width: 30%"/>
            <br/>
            <button type="submit" class="w3-btn w3-gradient w3-round-large w3-margin-bottom">Add taxitype</button>
            <label class="w3-text-red">${errorMessage}<br/></label>
        </form>
    </div>
</div>
<div class="w3-container w3-opacity w3-right-align w3-padding">
    <button class="w3-btn w3-gradient w3-round-large w3-border"
            onclick="location.href='/taxi_service/admin/controller?command=taxitypes'"><fmt:message key="button.back.to.taxi.types"/>
    </button>
</div>
</body>
</html>
