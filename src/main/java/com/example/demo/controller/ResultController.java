package com.example.demo.controller;

import com.example.demo.entity.Result;
import com.example.demo.repository.ResultRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/results")
@CrossOrigin
public class ResultController {

    private final ResultRepository resultRepository;

    public ResultController(ResultRepository resultRepository) {
        this.resultRepository = resultRepository;
    }

    @PostMapping("/submit")
    public String submitResult(@RequestBody Result result) {
        resultRepository.save(result);
        return "SCORE_SAVED";
    }
}
