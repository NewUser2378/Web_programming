package ru.itmo.wp.form.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.itmo.wp.form.NoticeCredentials;
import ru.itmo.wp.form.UserCredentials;
import ru.itmo.wp.service.NoticeService;
import ru.itmo.wp.service.UserService;
@Component
public class NoticeAddValidation implements Validator {
    private final NoticeService noticeService;

    // Исправлено имя сервиса в конструкторе
    public NoticeAddValidation(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return NoticeCredentials.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (!errors.hasErrors()) {
            NoticeCredentials noticeForm = (NoticeCredentials) target;
            if (noticeService.findByContent(noticeForm.getContent()) == null) {
                // Исправлено условие и текст ошибки
                errors.rejectValue("content", "content.invalid", "Недопустимый контент");
            }
        }
    }
}

