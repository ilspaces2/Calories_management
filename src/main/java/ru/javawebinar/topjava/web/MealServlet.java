package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.pepository.MealMemoryStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {

    private static final Logger log = getLogger(UserServlet.class);

    private static final int CALORIES_PER_DAY = 1000;

    private final MealMemoryStore mealMemoryStore = new MealMemoryStore();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String action = request.getParameter("action");
//        String path = "/meals.jsp";
//        if ("delete".equals(action)) {
//            path = "/users.jsp";
//        }

        log.info(String.valueOf(mealMemoryStore.getAll().size()));
//        request.setAttribute("meals", meals.getByFilter(null, null, CALORIES_PER_DAY));
        request.setAttribute("meals", mealMemoryStore.getAll());
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }
}
