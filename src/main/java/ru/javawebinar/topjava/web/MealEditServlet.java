package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealsCrudDao;
import ru.javawebinar.topjava.dao.MealsMemoryDao;
import ru.javawebinar.topjava.model.Meal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.slf4j.LoggerFactory.getLogger;

public class MealEditServlet extends HttpServlet {
    MealsCrudDao repo;
    private static final Logger log = getLogger(MealEditServlet.class);

    public MealEditServlet() {
        super();
        repo = MealsMemoryDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("edit meal");
        String attr = request.getParameter("action");
        switch (attr) {
            case "create":
                request.setAttribute("meal", new Meal(LocalDateTime.now(), "Enter a description", 0));
                request.getRequestDispatcher("/edit_meal.jsp").forward(request, response);
                break;
            case "update": {
                int id = Integer.parseInt(request.getParameter("id"));
                request.setAttribute("meal", repo.getById(id));
                request.getRequestDispatcher("/edit_meal.jsp").forward(request, response);
                break;
            }
            case "delete": {
                int id = Integer.parseInt(request.getParameter("id"));
                repo.deleteById(id);
                response.sendRedirect(request.getContextPath() + "/meals");
                break;
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        int id = Integer.parseInt(request.getParameter("id"));
        LocalDateTime dateTime = LocalDateTime.from(DateTimeFormatter.ISO_LOCAL_DATE_TIME.parse(request.getParameter("datetime")));
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        Meal meal = new Meal(dateTime, description, calories);
        meal.setId(id);
        repo.createOrUpdate(meal);
        response.sendRedirect(request.getContextPath() + "/meals");
    }
}
