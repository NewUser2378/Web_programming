package ru.itmo.wp.service;

import org.springframework.stereotype.Service;
import ru.itmo.wp.domain.Comment;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.repository.CommentRepository;
import ru.itmo.wp.repository.PostRepository;

import java.util.List;



@Service
public class PostService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;


    public PostService(PostRepository postRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    public List<Post> findAll() {
        return postRepository.findAllByOrderByCreationTimeDesc();
    }


    public void writeComment(Post post,Comment comment) {


           comment.setUser(post.getUser());
           comment.setPost(post);
           post.addComment(comment);
           postRepository.save(post);
          // commentRepository.save(comment);

    }


    public Post findById(Long id) {
        return id == null ? null : postRepository.findById((id)).orElse(null);
    }
}
