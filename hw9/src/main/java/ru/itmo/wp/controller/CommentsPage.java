package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.itmo.wp.domain.Comment;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.domain.Role;
import ru.itmo.wp.security.AnyRole;
import ru.itmo.wp.service.CommentService;
import ru.itmo.wp.service.PostService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
public class CommentsPage extends Page {
    private final PostService postService;
    private final CommentService commentService;

    private static final String POST_ID_SESSION_KEY = "postId";

    public CommentsPage(PostService postService, CommentService commentService) {
        this.postService = postService;
        this.commentService = commentService;
    }

    @AnyRole(Role.Name.ADMIN)
    @GetMapping("/post/{id}")
    public String showComments(@PathVariable("id") Long postId, Model model) {
        Post post = postService.findById(postId);
        List<Comment> comments = commentService.findByPostId(postId);

        model.addAttribute("post", post);
        model.addAttribute("comments", comments);

        return "CommentsPage";
    }

    @AnyRole({Role.Name.WRITER, Role.Name.ADMIN})
    @GetMapping("/post/{id}/writeComment")
    public String writeCommentGet(Model model, @PathVariable Long id, HttpSession httpSession) {
        model.addAttribute("post", postService.findById(id));
        httpSession.setAttribute(POST_ID_SESSION_KEY, id);
        model.addAttribute("comment", new Comment());
        return "CommentsPage";
    }

    @ModelAttribute("comment")
    public Comment getComment() {
        return new Comment();
    }

    @AnyRole({Role.Name.WRITER, Role.Name.ADMIN})
    @PostMapping("/post/{id}/writeComment")
    public String writeCommentPost(@Valid @ModelAttribute("comment") Comment comment,
                                   BindingResult bindingResult,
                                   @PathVariable Long id,
                                   HttpSession httpSession) {
        if (bindingResult.hasErrors() || comment.getText() == null || comment.getText().trim().isEmpty()) {
            // Если есть ошибки валидации или текст комментария пуст, вернуться на страницу комментариев
            return "ErrorPage";
        }

        postService.writeComment(postService.findById(id), comment);
        putMessage(httpSession, "You published a new comment");

        return "redirect:/post/" + id;
    }

}