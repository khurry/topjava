<%@ page import="java.time.format.DateTimeFormatter" %>
<jsp:useBean id="meal" scope="request" type="ru.javawebinar.topjava.model.Meal"/>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://example.com/functions" %>

<html>
<head>
    <title>Meal</title>
</head>
<style>
    label, input, button {
        margin: 3px;
    }
</style>
<body>
<form method="post">
    <h3><a href="index.html">Home</a></h3>
    <hr>
    <h2>Edit Meal</h2>

    <table style="width: 30%">
        <tr>
            <td><label for="datetime">DateTime:</label></td>

            <td><input type="datetime-local" id="datetime" name="datetime"
                       value=${f:formatLocalDateTimeT(meal.dateTime)}></td>
        </tr>
        <tr>
            <td><label for="description">Description:</label></td>
            <td><input type="text" size="40" id="description" name="description" value="${meal.description}"></td>
        </tr>
        <tr>
            <td><label for="calories">Calories:</label></td>
            <td><input type="number" id="calories" name="calories" value=${meal.calories}></td>
        </tr>
    </table>
    <input type="hidden" id="id" name="id" value=${meal.id}>
    <input type="submit" name="save" value="Save">
    <input type="button" value="Cancel" onclick="location.href='meals';">
</form>
</body>
</html>
