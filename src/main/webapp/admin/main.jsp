<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:if test="${not empty param.language}">
    <c:set var="language" value="${param.language}" scope="session"/>
</c:if>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="Locale"/>

<html lang="${sessionScope.language}">
<fmt:message key="text.hi"/><, ${admin.name}
<br>
<form action="controller" method="GET">
    <input type="hidden" name="command" value="users"/>
    <input type ="hidden" name="pageNumber" value="1"/>
    <button type="submit" class="w3-btn w3-gradient w3-round-large w3-margin-bottom">
        <fmt:message key="button.watch.users.list"/>
</button>
</form>
<form action="controller" method="GET">
    <input type="hidden" name="command" value="taxitypes"/>
    <button type="submit" class="w3-btn w3-gradient w3-round-large w3-margin-bottom">
        <fmt:message key="button.watch.taxi.types.list"/>
    </button>
</form>
<form action="controller" method="GET">
    <input type="hidden" name="command" value="clienttypes"/>
    <button type="submit" class="w3-btn w3-gradient w3-round-large w3-margin-bottom">
        <fmt:message key="button.watch.users.list"/>
    </button>
</form>
<form name="logoutForm" method="POST" action="controller" class="w3-right-align" >
        <input type="hidden" name="command" value="adminLogout" />
        <button type="submit" class="w3-btn w3-gradient w3-round-large w3-margin-top"><fmt:message key="text.log.out"/><</button>
</form>