package com.example.demo.controller;

import com.example.demo.entity.Question;
import com.example.demo.repository.QuestionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
@CrossOrigin
public class QuestionController {

    private final QuestionRepository questionRepository;

    // ✅ Constructor Injection (Best Practice)
    public QuestionController(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<Question> addQuestion(@RequestBody Question question) {
        Question savedQuestion = questionRepository.save(question);
        return ResponseEntity.ok(savedQuestion);
    }

    // READ ALL
    @GetMapping
    public ResponseEntity<List<Question>> getAllQuestions() {
        return ResponseEntity.ok(questionRepository.findAll());
    }

    // READ BY ID
    @GetMapping("/{id}")
    public ResponseEntity<Question> getQuestionById(@PathVariable int id) {
        return questionRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Question> updateQuestion(
            @PathVariable int id,
            @RequestBody Question updatedQuestion) {

        return questionRepository.findById(id)
                .map(q -> {
                    q.setQuestion(updatedQuestion.getQuestion());
                    q.setOptionA(updatedQuestion.getOptionA());
                    q.setOptionB(updatedQuestion.getOptionB());
                    q.setOptionC(updatedQuestion.getOptionC());
                    q.setOptionD(updatedQuestion.getOptionD());
                    q.setCorrectOption(updatedQuestion.getCorrectOption());
                    return ResponseEntity.ok(questionRepository.save(q));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable int id) {
        if (!questionRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        questionRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
