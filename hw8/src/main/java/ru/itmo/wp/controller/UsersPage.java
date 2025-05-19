package ru.itmo.wp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itmo.wp.service.UserService;

@Controller
public class UsersPage extends Page {

    private final UserService userService;

    @Autowired
    public UsersPage(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/all")
    public String users(Model model) {
        model.addAttribute("users", userService.findAll());
        return "UsersPage";
    }

    @PostMapping("/users/updateStatus")
    public String updateStatus(@RequestParam Long userId, @RequestParam String newStatus) {
        userService.updateStatus(userId, newStatus);
        return "redirect:/users/all";
    }
}
