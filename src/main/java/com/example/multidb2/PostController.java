package com.example.multidb2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class PostController {
    @Autowired
    private PostRepository postRepository;

    @GetMapping("/test")
    @ResponseBody
    public Iterable<Post> getTest(@RequestParam(defaultValue = "main") String client) {
        switch (client) {
            case "client-a":
                DBContextHolder.setCurrentDb("DB_A");
                break;
            case "client-b":
                DBContextHolder.setCurrentDb("DB_B");
                break;
        }
        return postRepository.findAll();
    }


    @PostMapping("/saveTest")
    @ResponseBody
    public Post createTest(@RequestParam(defaultValue = "main") String client, @RequestBody Post post) {
        switch (client) {
            case "client-a":
                DBContextHolder.setCurrentDb("DB_A");
                break;
            case "client-b":
                DBContextHolder.setCurrentDb("DB_B");
                break;
        }
        return postRepository.save(post);
    }

}