package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealsCrudDao {
    Meal createOrUpdate(Meal meal);

    Meal getById(Integer id);

    Meal deleteById(Integer id);

    List<Meal> getAll();
}
