package io.asteroidsjaylib.service.scoring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ScoringService {

    private int score = 0;

    public static void main(String[] args) {
        SpringApplication.run(ScoringService.class, args);
    }

    @GetMapping("/score")
    public int score() {
        return score;
    }

    @PostMapping("/score")
    public void score(@RequestParam(value = "increment") int increment) {
        score += increment;
    }

}
