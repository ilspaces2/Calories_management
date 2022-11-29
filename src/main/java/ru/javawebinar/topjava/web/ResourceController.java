package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

@Controller()
@RequestMapping("/resources")
public class ResourceController {

    private static final Logger log = LoggerFactory.getLogger(ResourceController.class);

    @GetMapping()
    public String getStyle(HttpServletResponse response) {
        log.info("style.css");
        response.setHeader("Content-Type", "text/css");
        return "redirect:resources/css/style.css";
    }
}
