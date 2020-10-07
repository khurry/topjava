package ru.javawebinar.topjava.dao;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.slf4j.LoggerFactory.getLogger;

public class MealsMemoryDao implements MealsCrudDao {
    private final Map<Integer, Meal> meals = new ConcurrentHashMap<>();
    private static final Logger log = getLogger(MealsMemoryDao.class);
    private static Integer counter = 0;


    private static MealsMemoryDao instance;

    private MealsMemoryDao() {
        createOrUpdate(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        createOrUpdate(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        createOrUpdate(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        createOrUpdate(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        createOrUpdate(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        createOrUpdate(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        createOrUpdate(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
    }

    public synchronized static MealsMemoryDao getInstance() {
        if (instance == null) {
            instance = new MealsMemoryDao();
        }
        return instance;
    }

    @Override
    public Meal createOrUpdate(Meal meal) {
        if (!meals.containsKey(meal.getId())) {
            synchronized (MealsMemoryDao.class) {
                counter++;
                meal.setId(counter);
            }
        }
        return meals.put(meal.getId(), meal);
    }

    @Override
    public Meal getById(Integer id) {
        return meals.get(id);
    }

    @Override
    public Meal deleteById(Integer id) {
        return meals.remove(id);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(meals.values());
    }
}
