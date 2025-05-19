package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wp.form.NoticeCredentials;
import ru.itmo.wp.form.validator.NoticeAddValidation;
import ru.itmo.wp.service.NoticeService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
@Controller
public class AdditionPage extends Page {
    private final NoticeService noticeService;


    public AdditionPage(NoticeService noticeService) {
        this.noticeService = noticeService;

    }

    @GetMapping("/addition")
    public String registerGet(Model model) {
        model.addAttribute("additionForm", new NoticeCredentials());
        return "AdditionPage";
    }

    @PostMapping("/addition")
    public String registerPost(@Valid @ModelAttribute("additionForm") NoticeCredentials additionForm,

                               HttpSession httpSession) {


        setNotice(httpSession, noticeService.add(additionForm));


        return "redirect:/";
    }
}
