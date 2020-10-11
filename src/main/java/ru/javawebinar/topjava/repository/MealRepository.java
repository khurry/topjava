package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.*;
import java.util.Collection;

public interface MealRepository {
    // null if not found, when updated
    Meal save(Integer userId, Meal meal);

    // false if not found
    boolean delete(Integer userId, Integer mealId);

    // null if not found
    Meal get(Integer userId, Integer mealId);

    Collection<Meal> getAll(Integer userId);

    Collection<Meal> getAllByDateTime(Integer userId, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime);
}
