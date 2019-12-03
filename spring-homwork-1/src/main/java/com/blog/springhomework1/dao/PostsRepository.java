package com.blog.springhomework1.dao;


import com.blog.springhomework1.model.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PostsRepository extends MongoRepository<Post, String> {

}
