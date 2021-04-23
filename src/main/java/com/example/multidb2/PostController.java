package com.example.multidb2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.PersistenceContext;
import java.util.Random;

@RestController
public class PostController {
    @Autowired
    private PostRepository postRepository;

    // test -> main DB
    // test?client=client-a -> Client A DB
    // test?client=client-b -> Client B DB
    @GetMapping("/test")
    @ResponseBody
    public Iterable<Post> getTest(@RequestParam(defaultValue = "main") String client) {
        switch (client) {
            case "client-a":
                DBContextHolder.setCurrentDb(DBTypeEnum.CLIENT_A);
                break;
            case "client-b":
                DBContextHolder.setCurrentDb(DBTypeEnum.CLIENT_B);
                break;
        }
        return postRepository.findAll();
    }


    @PostMapping("/saveTest")
    @ResponseBody
    public Post createTest(@RequestParam(defaultValue = "main") String client, @RequestBody Post post) {
        switch (client) {
            case "client-a":
                DBContextHolder.setCurrentDb(DBTypeEnum.CLIENT_A);
                break;
            case "client-b":
                DBContextHolder.setCurrentDb(DBTypeEnum.CLIENT_B);
                break;
        }
        return postRepository.save(post);
    }

}