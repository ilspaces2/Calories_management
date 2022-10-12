package ru.javawebinar.topjava.pepository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class MealMemoryStore {

    private final ArrayList<Meal> list = new ArrayList<>();

    private final AtomicInteger countId = new AtomicInteger(3);

    public MealMemoryStore() {
        list.add(1, new Meal(1, LocalDateTime.now(), "dinner1", 1000));
        list.add(2, new Meal(2, LocalDateTime.now(), "dinner2", 2000));
        list.add(3, new Meal(3, LocalDateTime.now(), "dinner3", 3000));
    }

    public void add(Meal meal) {
        list.add(countId.incrementAndGet(), meal);
    }

    public void update(int id, Meal meal) {
        list.add(id, meal);
    }

    public void delete(int id) {
        list.remove(id);
    }

    public List<Meal> getAll() {
        return list;
    }

    public List<MealTo> getByFilter(LocalTime start, LocalTime end, int calories) {
        return MealsUtil.filteredByStreams(list, start, end, calories);
    }
}
