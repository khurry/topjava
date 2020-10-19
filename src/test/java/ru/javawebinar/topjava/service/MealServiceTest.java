package ru.javawebinar.topjava.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {
    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal actual = service.get(MEAL_ID, USER_ID);
        assertThat(actual).usingRecursiveComparison().isEqualTo(meal1);
    }

    @Test
    public void getNotFound() {
        Assert.assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND, USER_ID));
    }

    @Test
    public void getNotCurrentUserMeal() {
        Assert.assertThrows(NotFoundException.class, () -> service.get(MEAL_ID, ADMIN_ID));
    }

    @Test
    public void delete() {
        service.delete(MEAL_ID, USER_ID);
        Assert.assertThrows(NotFoundException.class, () -> service.get(MEAL_ID, USER_ID));
    }

    @Test
    public void deleteNotFound() {
        Assert.assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND, USER_ID));
    }

    @Test
    public void deleteNotCurrentUserMeal() {
        Assert.assertThrows(NotFoundException.class, () -> service.delete(MEAL_ID, ADMIN_ID));
    }

    @Test
    public void getBetweenInclusive() {
        LocalDate date = LocalDate.of(2020, Month.JANUARY, 30);
        List<Meal> actualMeals = service.getBetweenInclusive(date, date, USER_ID);
        assertThat(actualMeals).hasSize(3).contains(meal1, meal2, meal3);
    }

    @Test
    public void getBetweenInclusiveNullConstraints() {
        assertThat(service.getBetweenInclusive(null, null, USER_ID)).containsExactlyInAnyOrder(meal1, meal2, meal3, meal4, meal5, meal6, meal7);
    }

    @Test
    public void getAll() {
        assertThat(service.getAll(USER_ID)).containsExactlyInAnyOrder(meal1, meal2, meal3, meal4, meal5, meal6, meal7);
    }

    @Test
    public void update() {
        Meal updated = getUpdated();
        service.update(updated, USER_ID);
        Meal actual = service.get(MEAL_ID, USER_ID);
        assertThat(actual).usingRecursiveComparison().isEqualTo(updated);
    }

    @Test
    public void updateNotCurrentUserMeal() {
        Meal updated = getUpdated();
        Assert.assertThrows(NotFoundException.class, () -> service.update(updated, ADMIN_ID));
    }

    @Test
    public void updateIdNotExists() {
        Meal updated = getUpdated();
        updated.setId(NOT_FOUND);
        Assert.assertThrows(NotFoundException.class, () -> service.update(updated, USER_ID));
    }

    @Test
    public void create() {
        Meal newMeal = getNew();
        Meal createRet = service.create(newMeal, USER_ID);
        assertThat(createRet).usingRecursiveComparison().ignoringFields("id").isEqualTo(newMeal);
        Meal getRet = service.get(createRet.getId(), USER_ID);
        assertThat(getRet).usingRecursiveComparison().isEqualTo(createRet);
    }

    @Test
    public void duplicateDateTimeCreate() throws Exception {
        assertThrows(DataAccessException.class, () ->
                service.create(new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "descr", 1234), USER_ID));
    }

}