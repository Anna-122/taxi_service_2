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
                <td style="border-right: 1px solid"><fmt:message key="text.surname"/></td>
                <td style="border-right: 1px solid"><fmt:message key="label.email"/></td>
                <td style="border-right: 1px solid"><fmt:message key="label.to.delete"/></td>
                <td style="border-right: 1px solid"><fmt:message key="label.register.as.driver"/></td>
                <td><fmt:message key="label.add.car.to.driver"/></td>
            </tr>
            <jsp:useBean id="users" type="java.util.List<edu.goncharova.domain.User>" scope="request"/>

            <c:forEach var="user" items="${users}">
                <tr>
                    <td style="border-right: 1px solid">${user.userId}</td>
                    <td style="border-right: 1px solid">${user.name}</td>
                    <td style="border-right: 1px solid">${user.surname}</td>
                    <td style="border-right: 1px solid">${user.email}</td>
                    <td style="border-right: 1px solid">
                        <form action="controller" method="post">
                            <input type="hidden" name="command" value="delete"/>
                            <input type="hidden" name="userid" value=${user.userId}>
                            <button type="submit" class="w3-btn w3-gradient w3-round-large w3-margin-bottom"
                                    style="width: 200px"><fmt:message key="button.delete.user"/>
                            </button>
                        </form>
                    </td>
                    <td style="border-right: 1px solid">
                        <form action="controller" method="post">
                            <input type="hidden" name="command" value="registerDriver"/>
                            <input type="hidden" name="userid" value=${user.userId}>
                            <button type="submit" class="w3-btn w3-gradient w3-round-large w3-margin-bottom"
                                    style="width: 200px"><fmt:message key="button.make.a.driver"/>
                            </button>
                        </form>
                    </td>
                    <td>
                        <form action="addCar.jsp" method="get">
                            <input type="hidden" name="userid" value=${user.userId}>
                            <button type="submit" class="w3-btn w3-gradient w3-round-large w3-margin-bottom"
                                    style="width: 200px"><fmt:message key="button.add.car"/>
                            </button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <c:if test="${param.pageNumber>1}"><a href="controller?command=users&pageNumber=${param.pageNumber-1}"><fmt:message key="text.previous.page"/></a></c:if>
        <c:if test="${param.pageNumber<pageAmount}"><a href="controller?command=users&pageNumber=${param.pageNumber+1}"><fmt:message key="text.next.page"/></a></c:if>
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

