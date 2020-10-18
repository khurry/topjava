package ru.javawebinar.topjava.repository.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Repository
public class JdbcMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(JdbcMealRepository.class);

    private static final BeanPropertyRowMapper<Meal> ROW_MAPPER = BeanPropertyRowMapper.newInstance(Meal.class);

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertUser;

    public JdbcMealRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        insertUser = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("meals")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Meal save(Meal meal, int userId) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", meal.getId())
                .addValue("datetime", meal.getDateTime())
                .addValue("description", meal.getDescription())
                .addValue("calories", meal.getCalories())
                .addValue("user_id", userId);

        if (meal.isNew()) {
            Number id = insertUser.executeAndReturnKey(map);
            meal.setId(id.intValue());
            return meal;
        } else {
            log.debug("updating meal");
            int count = namedParameterJdbcTemplate.update("UPDATE meals SET datetime = :datetime, description = :description" +
                    ", calories = :calories WHERE id = :id AND user_id = :user_id", map);
            if (count > 0) return meal;
            else return null;
        }
    }

    @Override
    public boolean delete(int id, int userId) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", id)
                .addValue("user_id", userId);
        return namedParameterJdbcTemplate.update("DELETE FROM meals WHERE id=:id AND user_id=:user_id", map) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", id)
                .addValue("user_id", userId);
        return namedParameterJdbcTemplate.queryForObject("SELECT * FROM meals WHERE id = :id AND user_id = :user_id", map, ROW_MAPPER);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return namedParameterJdbcTemplate.query("SELECT * FROM meals WHERE user_id=:user_id ORDER BY datetime DESC",
                Collections.singletonMap("user_id", userId), ROW_MAPPER);
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("user_id", userId)
                .addValue("startDateTime", startDateTime)
                .addValue("endDateTime", endDateTime);
        return namedParameterJdbcTemplate.query("SELECT * FROM meals WHERE user_id = :user_id AND datetime >= :startDateTime AND datetime < :endDateTime " +
                "ORDER BY datetime DESC", map, ROW_MAPPER);
    }
}
