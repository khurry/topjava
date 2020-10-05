<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru">


<head>
    <title>Meals</title>
</head>
<style>
    table, th, td {
        padding: 8px;
        border: 2px solid black;
        border-collapse: collapse;
    }

    th {

        border-collapse: collapse;
        font-weight: bolder;
    }
</style>

<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>

<a href="#">Add Meal</a>
<br>
<br>
<table style="width:30%">
    <colgroup>
        <col span="1" style="width: 25%;">
        <col span="1" style="width: 23%;">
        <col span="1" style="width: 10%;">
        <col span="1" style="width: 21%;">
        <col span="1" style="width: 21%;">
    </colgroup>
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th></th>
        <th></th>
    </tr>
    <jsp:useBean id="meals" scope="request" type="java.util.List"/>

    <c:set var = "dt" value = '<%= DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")%>'/>
    <c:forEach var="variable" items="${meals}" >
        <tr>
            <td style="color: ${variable.excess ? 'red' : 'green'}">${dt.format(variable.dateTime)}</td>
            <td style="color: ${variable.excess ? 'red' : 'green'}">${variable.description}</td>
            <td style="color: ${variable.excess ? 'red' : 'green'}">${variable.calories}</td>
            <td><a href="edit_meal.jsp">Update</a></td>
            <td><a href="#">Delete</a></td>
        </tr>
    </c:forEach>

</table>
</body>
</html>