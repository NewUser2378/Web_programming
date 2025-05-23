package ru.itmo.wp.service;

import org.springframework.stereotype.Service;
import ru.itmo.wp.domain.Notice;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.form.NoticeCredentials;
import ru.itmo.wp.form.UserCredentials;
import ru.itmo.wp.repository.NoticeRepository;

import java.util.List;
@Service
public class NoticeService {

    private final NoticeRepository noticeRepository;

    public NoticeService(NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }
    public Notice findById(Long id) {
        return id == null ? null : noticeRepository.findById(id).orElse(null);
    }

    public List<Notice> findAll() {
        return noticeRepository.findAllByOrderByIdDesc();
    }

    public List<Notice> findByContent(String content) {
        return noticeRepository.findByContent(content);
    }


    public Notice add(NoticeCredentials noticeCredentials) {
        Notice notice = new Notice();
        notice.setContent(noticeCredentials.getContent());
        noticeRepository.save(notice);
        noticeRepository.updateContent(notice.getId(), noticeCredentials.getContent());
        return notice;
    }
}
