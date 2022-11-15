package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
@RequestMapping("/meals")
public class JspMealController extends AbstractMealController {

    public JspMealController(MealService service) {
        super(service);
    }

    @GetMapping("/delete")
    public String deleteMeals(@RequestParam int id) {
        super.delete(id);
        return "redirect:/meals";
    }

    @GetMapping("")
    public String getAll(Model model) {
        model.addAttribute("meals", super.getAll());
        return "meals";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("action", "create");
        model.addAttribute("meal",
                new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000));
        return "mealForm";
    }

    @GetMapping("/update")
    public String updateForm(Model model, @RequestParam("id") int id) {
        model.addAttribute("meal", super.get(id));
        return "mealForm";
    }

    @PostMapping("/execute")
    public String createOrUpdate(@ModelAttribute Meal meal) {
        if (meal.isNew()) {
            super.create(meal);
        }
        super.update(meal, meal.id());
        return "redirect:/meals";
    }

    @GetMapping("/filter")
    public String getMealsBetween(Model model,
                                  @RequestParam String startDate,
                                  @RequestParam String endDate,
                                  @RequestParam String startTime,
                                  @RequestParam String endTime) {
        LocalDate startDateParam = parseLocalDate(startDate);
        LocalDate endDateParam = parseLocalDate(endDate);
        LocalTime startTimeParam = parseLocalTime(startTime);
        LocalTime endTimeParam = parseLocalTime(endTime);
        model.addAttribute("meals", super.getBetween(startDateParam, startTimeParam, endDateParam, endTimeParam));
        return "meals";
    }
}

