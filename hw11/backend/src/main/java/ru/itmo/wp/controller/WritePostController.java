package ru.itmo.wp.controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.exception.ValidationException;
import ru.itmo.wp.form.PostCredential;
import ru.itmo.wp.form.UserCredentials;
import ru.itmo.wp.repository.PostRepository;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/1")
public class WritePostController {
PostRepository postRepository;

    WritePostController(PostRepository postRepository){
        this.postRepository=postRepository;
    }

    @PostMapping("/write")
    public Post write(@RequestBody @Valid PostCredential postCredential) {

        User user =postCredential.getUser();
        String text= postCredential.getText();
        String title= postCredential.getTitle();
        Post post=new Post();
        post.setText(text);
        post.setTitle(title);
        post.setUser(user);
        postRepository.save(post);

        return post;

    }

}
