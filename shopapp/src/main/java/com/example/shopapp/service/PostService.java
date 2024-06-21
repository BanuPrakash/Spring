package com.example.shopapp.service;

import com.example.shopapp.dto.Post;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.List;

@HttpExchange(url="/posts", accept = "application/json", contentType = "application/json")
public interface PostService {
    @GetExchange
    List<Post> getPosts();

    @GetExchange("/{id}")
    Post getPost(@PathVariable("id") int id);

    @PostMapping
    Post addPost(@RequestBody Post p);
}
