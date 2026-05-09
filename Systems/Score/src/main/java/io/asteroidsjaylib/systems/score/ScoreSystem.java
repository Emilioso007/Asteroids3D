package io.asteroidsjaylib.systems.score;

import io.asteroidsjaylib.common.IWorld;
import io.asteroidsjaylib.common.ecs.ResponseSystem;
import io.asteroidsjaylib.common.score.ScoreEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class ScoreSystem extends ResponseSystem {

    private final String scoringServiceUrl = "http://localhost:8080/score";
    private final RestTemplate restTemplate;

    public ScoreSystem(){
        restTemplate = new RestTemplate();
    }

    @Override
    public void start(IWorld world) {

    }

    @EventListener
    private void handleScoreIncrement(ScoreEvent scoreEvent){
        String url = scoringServiceUrl + "?increment=" + scoreEvent.increment;
        try {
            restTemplate.postForLocation(url, null);
        } catch (RestClientException e) {
            System.out.println("Score Service not responding: " + e.getMessage());
        }
    }

}
