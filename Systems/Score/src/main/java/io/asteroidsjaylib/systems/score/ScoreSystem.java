package io.asteroidsjaylib.systems.score;

import io.asteroidsjaylib.common.IWorld;
import io.asteroidsjaylib.common.ecs.ResponseSystem;
import io.asteroidsjaylib.common.score.ScoreEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

public class ScoreSystem extends ResponseSystem {

    private final String scoringServiceUrl = "http://localhost:8080/score";
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public void start(IWorld world) {
    }

    @EventListener
    private void handleScoreIncrement(ScoreEvent scoreEvent){
        String url = scoringServiceUrl + "?increment=" + scoreEvent.increment;

        CompletableFuture.runAsync(() -> {
            try {
                restTemplate.postForLocation(url, null);
            } catch (RestClientException e) {
                System.out.println("Score Service not responding: " + e.getMessage());
            }
        });

    }

}
