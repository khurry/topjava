<%--<jsp:useBean id="startDate" scope="request" type="java.time.LocalDate"/>--%>
<%--<jsp:useBean id="endDate" scope="request" type="java.time.LocalDate"/>--%>
<%--<jsp:useBean id="startTime" scope="request" type="java.time.LocalTime"/>--%>
<%--<jsp:useBean id="endTime" scope="request" type="java.time.LocalTime"/>--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%--<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>--%>
<html>
<head>
    <title>Meal list</title>
    <style>
        .normal {
            color: green;
        }

        .excess {
            color: red;
        }
    </style>
</head>
<body>
<section>
    <h3><a href="index.html">Home</a></h3>
    <hr/>
    <h2>Meals</h2>
    <form method="get" action="meals">
        <input type="hidden" name="action" value="filter">
        <table>
            <tr>
                <th>Date from (inclusive):</th>
                <th>Date to(inclusive):</th>
                <th>Time from (inclusive):</th>
                <th>Time to (exclusive):</th>
            </tr>
            <tr>
                <th><input type="date" value="${param.containsKey("startDate") ? param.startDate : ""}" name="startDate"></th>
                <th><input type="date" value="${param.containsKey("endDate") ? param.endDate : ""}" name="endDate"></th>
                <th><input type="time" value="${param.containsKey("startTime") ? param.startTime : ""}" name="startTime"></th>
                <th><input type="time" value="${param.containsKey("endTime") ? param.endTime : ""}" name="endTime"></th>
            </tr>
        </table>

        <button type="submit">Filter</button>
        <button type="reset">Cancel</button>
    </form>

    <a href="meals?action=create">Add Meal</a>
    <br><br>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${meals}" var="meal">
            <jsp:useBean id="meal" type="ru.javawebinar.topjava.to.MealTo"/>
            <tr class="${meal.excess ? 'excess' : 'normal'}">
                <td>
                        <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                        <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                        <%--${fn:replace(meal.dateTime, 'T', ' ')}--%>
                        ${fn:formatDateTime(meal.dateTime)}
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
                <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>