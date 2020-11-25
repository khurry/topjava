###### Curl commands to test MealRestController
get meal:

curl http://localhost:8080/topjava/rest/meals/100003

getAll:

curl http://localhost:8080/topjava/rest/meals

getAllBetween:

curl http://localhost:8080/topjava/rest/meals/filter?startDate=2020-01-31&startTime=00:00:00&endDate=2020-02-01&endTime=23:00:00

createMeal:

curl -d "{"""dateTime""": """2020-01-30T13:00:01""","""description""": """asd""","""calories""": """1001"""}" -H "Content-Type: application/json" -X POST http://localhost:8080/topjava/rest/meals

updateMeal:

curl -d "{"""dateTime""": """2020-01-30T13:00:00""","""description""": """asd""","""calories""": """1001"""}" -H "Content-Type: application/json" -X PUT http://localhost:8080/topjava/rest/meals/100003

deleteMeal:

curl -X DELETE http://localhost:8080/topjava/rest/meals/100003

