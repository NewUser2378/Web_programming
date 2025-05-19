package ru.itmo.wp.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.itmo.wp.domain.Notice;
import ru.itmo.wp.domain.User;

import java.util.List;


public interface NoticeRepository extends JpaRepository<Notice, Long> {


    @Transactional
    @Modifying
    @Query(
            value = "UPDATE notice SET content = ?2 WHERE id = ?1",
            nativeQuery = true
    )
    void updateContent(long id,String newContent);

    @Query(
            value = "SELECT * FROM notice WHERE l content = ?1",
            nativeQuery = true
    )
    List<Notice> findByContent(String content);


    List<Notice> findAllByOrderByIdDesc();


}
