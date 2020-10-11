package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> save(SecurityUtil.authUserId(), meal));
    }

    @Override
    public Meal save(Integer userId, Meal meal) {
        Map<Integer, Meal> userMeals = repository.computeIfAbsent(userId, meals -> new HashMap<>());
        return save(userMeals, meal);
    }

    private Meal save(Map<Integer, Meal>userMeals, Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            userMeals.put(meal.getId(), meal);
            return meal;
        }
        return userMeals.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(Integer userId, Integer mealId) {
        Map<Integer, Meal> userMeals = repository.get(userId);
        if (userMeals == null) return false;

        return userMeals.remove(mealId) != null;
    }

    @Override
    public Meal get(Integer userId, Integer mealId) {
        Map<Integer, Meal> userMeals = repository.get(userId);
        if (userMeals == null) return null;
        return userMeals.get(mealId);
    }

    @Override
    public Collection<Meal> getAll(Integer userId) {
        List<Meal> list = new ArrayList<>(repository.get(userId).values());
        list.sort((o1, o2) -> o2.getDateTime().compareTo(o1.getDateTime()));
        return list;
    }

    @Override
    public Collection<Meal> getAllByDateTime(Integer userId, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        return getAll(userId).stream().filter(meal -> DateTimeUtil.isBetweenHalfOpen(meal.getDate(), startDate, endDate) &&
                DateTimeUtil.isBetweenHalfOpen(meal.getTime(), startTime, endTime)).collect(Collectors.toList());
    }
}

