package com.library.user.stats.application.controller;

import com.library.user.stats.application.dto.BookDTO;
import com.library.user.stats.application.dto.UserStatsDTO;
import com.library.user.stats.application.service.UserStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/stats")
public class UserStatsController {
    private UserStatsService service;

    @Autowired
    public UserStatsController(UserStatsService service) {
        this.service = service;
    }

    @GetMapping
    public List<UserStatsDTO> getAllStats(){
        return service.getAll();
    }

    @GetMapping("/{id}")
    public List<UserStatsDTO> getUserStatsByUserId(@PathVariable("id") String id){
        return service.findUserStatsByUserId(id);
    }

    @GetMapping("/book/{id}")
    public List<UserStatsDTO> getUserStatsByBookId(@PathVariable("id") String id){
        return service.findUserStatsByBookId(id);
    }

    @GetMapping("/bookById/{id}")
    public BookDTO getBookById(@PathVariable("id") String id){
        return service.getBookById(id);
    }

    @PutMapping
    public UserStatsDTO insert(@Valid @RequestBody UserStatsDTO userStats){
        return service.insert(userStats);
    }

    @PostMapping
    public UserStatsDTO update (@Valid @RequestBody UserStatsDTO userStats){
        return service.update(userStats);
    }

    @DeleteMapping("/{id}")
    public  void delete(@PathVariable("id") String id) {
        service.delete(id);
    }


}
