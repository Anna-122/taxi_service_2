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
<div class="w3-container w3-center">
    <div class="w3-bar w3-padding-large w3-padding-24">
        <table class="w3-table-all w3-light-green">
            <tr>
                <td style="border-right: 1px solid"><fmt:message key="label.id"/></td>
                <td style="border-right: 1px solid"><fmt:message key="label.name"/></td>
                <td style="border-right: 1px solid"><fmt:message key="label.frame"/></td>
                <td></td>
            </tr>
            <jsp:useBean id="taxitypes" type="java.util.List<edu.goncharova.domain.TaxiType>" scope="request"/>

            <c:forEach var="clienttype" items="${taxitypes}">
                <tr>
                    <td style="border-right: 1px solid">${clienttype.taxiTypeId}</td>
                    <td style="border-right: 1px solid">${clienttype.taxiTypeName}</td>
                    <td style="border-right: 1px solid">${clienttype.fare}</td>
                    <td>
                        <form action="updateTaxiType.jsp" method="post">
                            <input type="hidden" name="taxitypeid" value="${clienttype.taxiTypeId}">
                            <input type="hidden" name="taxitypename" value="${clienttype.taxiTypeName}"/>
                            <input type="hidden" name="fare" value="${clienttype.fare}"/>
                            <button type="submit" class="w3-btn w3-gradient w3-round-large"
                                    style="width: 100%; margin: 0;"><fmt:message key="text.edit"/></button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <form action="addTaxiType.jsp" method="get">
            <button type="submit" class="w3-btn w3-gradient w3-round-large w3-margin-bottom"><fmt:message
                    key="button.add.taxi.type"/></button>
        </form>
        <label class="w3-text-red">${errorMessage}</label>
        </form>
        <div class="w3-container w3-opacity w3-right-align w3-padding">
            <button class="w3-btn w3-gradient w3-round-large w3-border" onclick="location.href='/taxi_service/admin'">
                <fmt:message key="button.back"/>
            </button>
        </div>
        <br><br>
    </div>
</div>
</body>
</html>

