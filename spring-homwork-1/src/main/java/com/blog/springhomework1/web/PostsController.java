package com.blog.springhomework1.web;


import com.blog.springhomework1.domain.PostsService;
import com.blog.springhomework1.exception.InvalidEntityException;
import com.blog.springhomework1.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostsController {
    @Autowired
    private PostsService postsService;

    @GetMapping
    public List<Post> getPosts() {
        return postsService.findAll();
    }

    @GetMapping("{id}")
    public Post getPostById(@PathVariable("id") String postId) {
        return postsService.findById(postId);
    }

    @PostMapping
    public ResponseEntity<Post> addPost(@Valid @RequestBody Post post, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasFieldErrors()) {
            String message = bindingResult.getFieldErrors().stream()
                    .map(err -> String.format("Invalid '%s' -> '%s': %s\n",
                            err.getField(), err.getRejectedValue(), err.getDefaultMessage()))
                    .reduce("", (acc, errStr) -> acc + errStr);
            throw new InvalidEntityException(message);
        }
        post.setAuthor(principal.getName());
        Post created = postsService.add(post);
        return ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentRequest().pathSegment("{id}").build(created.getId()))
                .body(created);
    }

    @PutMapping("{id}")
    public Post update(@PathVariable String id, @Valid @RequestBody Post post) {
        if (!id.equals(post.getId())) {
            throw new InvalidEntityException(
                    String.format("Entity ID='%s' is different from URL resource ID='%s'", post.getId(), id));
        }
        validateIsOwnerOfPost(id);
        return postsService.update(post);
    }

    @DeleteMapping("{id}")
    public Post remove(@PathVariable String id) {
        validateIsOwnerOfPost(id);
        return postsService.remove(id);
    }

    private void validateIsOwnerOfPost(String postId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Post post = postsService.findById(postId);
        if(!post.getAuthor().equals(authentication.getName())) {
            throw new InvalidEntityException("You cannot delete other users posts");
        }
    }
}
