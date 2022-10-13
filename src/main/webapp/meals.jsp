<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru">
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<div>
    <table border="1">
        <thead>
        <tr>
            <th scope="col">Date</th>
            <th scope="col">Description</th>
            <th scope="col">Calories</th>
            <th scope="col">Upd</th>
            <th scope="col">Del</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="meal" items="${meals}">
            <tr>
                    <%--                <c:if test="${meal.excess}">--%>
                    <%--                    <td style="color: red">${meal.dateTime}</td>--%>
                    <%--                    <td style="color: red">${meal.description}</td>--%>
                    <%--                    <td style="color: red">${meal.calories}</td>--%>
                    <%--                </c:if>--%>

                <c:choose>
                    <c:when test="${meal.excess}">
                        <td style="color: red">${meal.dateTime}</td>
                        <td style="color: red">${meal.description}</td>
                        <td style="color: red">${meal.calories}</td>
                    </c:when>
                    <c:otherwise>
                        <td style="color: green">${meal.dateTime}</td>
                        <td style="color: green">${meal.description}</td>
                        <td style="color: green">${meal.calories}</td>
                    </c:otherwise>
                </c:choose>
                <td>
                    <a href="<c:url value="?action=update&id=${meal.id}"/>">Update</a>
                </td>
                <td>
                    <a href="<c:url value="?action=delete&id=${meal.id}"/>">Delete</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <a href="<c:url value='?action=create'/>">Add meal</a>
</div>
</body>
</html>