package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class DataJpaMealRepository implements MealRepository {

    private final CrudMealRepository crudRepository;

    private final CrudUserRepository userRepository;

    public DataJpaMealRepository(CrudMealRepository crudRepository, CrudUserRepository userRepository) {
        this.crudRepository = crudRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Meal save(Meal meal, int userId) {
        User user = userRepository.getOne(userId);
        if (!meal.isNew() && get(meal.id(), userId) == null) return null;
        meal.setUser(user);
        return crudRepository.save(meal);
    }

    @Override

    public boolean delete(int id, int userId) {
        return crudRepository.delete(id, userId) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        Meal meal = crudRepository.findById(id).orElse(null);
        return meal != null && meal.getUser() != null && meal.getUser().getId() == userId ? meal : null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return crudRepository.findAllByUserIdOrderByDateTimeDesc(userId);
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return crudRepository.findAllBetween(userId, startDateTime, endDateTime);
    }
}
