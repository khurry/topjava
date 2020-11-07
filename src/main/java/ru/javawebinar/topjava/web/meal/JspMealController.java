package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
@RequestMapping("/meals")
public class JspMealController extends AbstractMealController {

    @PostMapping
    public String saveMeal(HttpServletRequest request) throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");

        Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));

        if (!StringUtils.hasText(request.getParameter("id"))) {
            create(meal);
        } else {
            update(meal, getId(request));
        }
        return "redirect:meals";
    }

    @GetMapping("/delete")
    public String deleteMeal(HttpServletRequest request) {
        int id = getId(request);
        delete(id);
        return "redirect:../meals";
    }

    @GetMapping("/update")
    public String updateMeal(HttpServletRequest request) {
        final Meal meal = get(getId(request));
        request.setAttribute("meal", meal);
        request.setAttribute("action", "update");

        return "mealForm";
    }

    @GetMapping("/create")
    public String createMeal(HttpServletRequest request) {
        final Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
        request.setAttribute("meal", meal);
        request.setAttribute("action", "create");
        return "mealForm";
    }

    @GetMapping("/filter")
    public String filterMeals(HttpServletRequest request) {
        LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = parseLocalTime(request.getParameter("endTime"));
        request.setAttribute("meals", getBetween(startDate, startTime, endDate, endTime));
        return "meals";
    }

    @GetMapping
    public String getMeals(HttpServletRequest request) {
        request.setAttribute("meals", getAll());
        return "meals";

    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
