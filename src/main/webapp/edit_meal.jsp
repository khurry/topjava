<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meal</title>
</head>
<style>
    label {
        /*padding-right: 20px;*/
    }

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
            <td><input type="datetime-local" id="datetime" name="datetime" value="2020-01-02T11:00"></td>
        </tr>
        <tr>
            <td><label for="description">Description:</label></td>
            <td><input type="text" size="40" id="description" name="description"></td>
        </tr>
        <tr>
            <td><label for="calories">Calories:</label></td>
            <td><input type="number" id="calories" name="calories"></td>
        </tr>
    </table>
    <button name="save">Save</button>
    <button name="cancel">Cancel</button>

</form>
</body>
</html>
