package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.pepository.MealMemoryStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {

    private static final Logger log = getLogger(UserServlet.class);

    private static final String MEALS_PATH = "/meals.jsp";

    private static final String MEALS_CREATE_PATH = "/createForm.jsp";

    private static final String MEALS_UPDATE_PATH = "/updateForm.jsp";

    private static final int CALORIES_PER_DAY = 3000;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    private final MealMemoryStore mealMemoryStore = new MealMemoryStore();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String path = MEALS_PATH;
        if ("delete".equals(action)) {
            mealMemoryStore.delete(Integer.parseInt(request.getParameter("id")));
            request.setAttribute("meals", mealMemoryStore.getByFilter
                    (LocalTime.of(0, 0), LocalTime.of(23, 0), CALORIES_PER_DAY));
        } else if ("create".equals(action)) {
            path = MEALS_CREATE_PATH;
        } else if ("update".equals(action)) {
            path = MEALS_UPDATE_PATH;
            request.getSession().setAttribute("meal", mealMemoryStore.findById(Integer.parseInt(request.getParameter("id"))));
        } else {
            request.setAttribute("meals", mealMemoryStore.getByFilter
                    (LocalTime.of(0, 0), LocalTime.of(23, 0), CALORIES_PER_DAY));
        }
        request.getRequestDispatcher(path).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime"), FORMATTER),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories"))
        );
        int mealId = Integer.parseInt(request.getParameter("id"));
        if (mealId == 0) {
            mealMemoryStore.add(meal);
        } else {
            log.info(String.valueOf(mealId));
            meal.setId(mealId);
            mealMemoryStore.update(mealId, meal);
        }
        request.setAttribute("meals", mealMemoryStore.getByFilter(LocalTime.of(0, 0), LocalTime.of(23, 0), CALORIES_PER_DAY));
        request.getRequestDispatcher(MEALS_PATH).forward(request, response);
    }
}
