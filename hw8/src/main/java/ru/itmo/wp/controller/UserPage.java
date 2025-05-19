package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.service.UserService;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/user")
public class UserPage {

    private final UserService userService;

    public UserPage(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public String getUserProfile(@PathVariable(required = false) String id, Model model, HttpServletResponse response) {
        // Проверка, является ли id числом
        if (!isNumeric(id)) {
            // Если id не является числом, выполняем редирект на корень
            return "redirect:/";
        }

        Long userId = Long.parseLong(id);
        User user = userService.findById(userId);

        if (user != null) {
            model.addAttribute("user", user);
            return "UserPage";
        } else {
            model.addAttribute("errorMessage", "No such user");
            return "ErrorPage";
        }
    }

    @GetMapping("/")
    public String redirectToRoot() {
        return "redirect:/";
    }


    private boolean isNumeric(String str) {
        if (str == null) {
            return false;
        }
        try {
            Long.parseLong(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}


