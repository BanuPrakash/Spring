package com.example.shopapp.service;

import com.example.shopapp.dto.Post;
import com.example.shopapp.dto.User;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class AggregatorService {
    private final PostService postService;
    private final UserService userService;

    @Async("posts-pool")
    public CompletableFuture<List<User>> getUsers() {
        System.out.println(Thread.currentThread() + " getting users");
        // simulate delay
        return  CompletableFuture.completedFuture(userService.getUsers());
    }

    @Async("posts-pool")
    public CompletableFuture<List<Post>> getPosts() {
        System.out.println(Thread.currentThread() + " getting posts");
        // simulate delay
        return  CompletableFuture.completedFuture(postService.getPosts());
    }

}
