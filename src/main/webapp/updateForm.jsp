<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals update</h2>
<div>
    <form action="meals" method='POST'>
        <input type="hidden" name="id" value="${meal.id}">

                <div class="form-group">
                    <label>Date
                        <input type="datetime-local" name="dateTime" value="${meal.dateTime}" required>
                    </label>
                </div>

        <div class="form-group">
            <label>Description
                <input type="text" name="description" value="${meal.description}" required>
            </label>
        </div>

        <div class="form-group">
            <label>Calories
                <input type="number" name="calories" value="${meal.calories}" required>
            </label>
        </div>
        <input type="submit" value="Submit"/>
        <a href="<c:url value='/meals'/>">Back</a>
    </form>
</div>
</body>
</html>