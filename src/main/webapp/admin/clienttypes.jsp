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
                <td style="border-right: 1px solid"><fmt:message key="text.discount"/></td>
                <td style="border-right: 1px solid">Money spent</td>
                <td></td>
            </tr>
            <jsp:useBean id="clienttypes" type="java.util.List<edu.goncharova.domain.ClientType>" scope="request"/>

            <c:forEach var="clienttype" items="${clienttypes}">
                <tr>
                    <td style="border-right: 1px solid">${clienttype.clientTypeId}</td>
                    <td style="border-right: 1px solid">${clienttype.name}</td>
                    <td style="border-right: 1px solid">${clienttype.discount}</td>
                    <td style="border-right: 1px solid">${clienttype.moneySpent}</td>
                    <td>
                        <form action="updateClientType.jsp" method="post">
                            <input type="hidden" name="clienttypeid" value="${clienttype.clientTypeId}">
                            <input type="hidden" name="name" value="${clienttype.name}"/>
                            <input type="hidden" name="discount" value="${clienttype.discount}"/>
                            <input type="hidden" name="moneyspent" value="${clienttype.moneySpent}"/>
                            <button type="submit" class="w3-btn w3-gradient w3-round-large w3-margin-bottom"
                                    style="width: 100px; margin: 0"><fmt:message key="text.edit"/>
                            </button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <form action="addClientType.jsp" method="get">
            <button type="submit" class="w3-btn w3-gradient w3-round-large w3-margin-bottom"><fmt:message key="button.add.client.type"/></button>
        </form>
        <label class="w3-text-red">${errorMessage}</label>
        </form>
        <div class="w3-container w3-opacity w3-padding">
            <button class="w3-btn w3-gradient w3-round-large w3-border" onclick="location.href='/taxi_service/admin'">
                <fmt:message key="button.back"/>
            </button>
        </div>
        <br><br>
    </div>
</div>
</body>
</html>

