package com.blog.springhomework1.domain;

import com.blog.springhomework1.dao.PostsRepository;
import com.blog.springhomework1.exception.NonexisitngEntityException;
import com.blog.springhomework1.model.Post;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class PostServiceImpl implements PostsService {
    @Autowired
    private PostsRepository repo;

    @Override
    public List<Post> findAll() {
        return repo.findAll();
    }

    @Override
    public Post findById(String postId) {
        return repo.findById(postId).orElseThrow(() -> new NonexisitngEntityException(
                String.format("Post with ID='%s' does not exist.", postId)));
    }

    @Override
    public Post add(Post Post) {
        return repo.insert(Post);
    }

    @Override
    public Post update(Post post) {
        Optional<Post> old = repo.findById(post.getId());
        if (!old.isPresent()) {
            throw new NonexisitngEntityException(
                    String.format("Post with ID='%s' does not exist.", post.getId()));
        }
        post.setCreated(old.get().getCreated());
        return repo.save(post);
    }

    @Override
    public Post remove(String PostId) {
        Optional<Post> old = repo.findById(PostId);
        log.info("!!!!!! PostID = " + PostId);
        if (!old.isPresent()) {
            throw new NonexisitngEntityException(
                    String.format("Post with ID='%s' does not exist.", PostId));
        }
        repo.deleteById(PostId);
        return old.get();
    }
}
