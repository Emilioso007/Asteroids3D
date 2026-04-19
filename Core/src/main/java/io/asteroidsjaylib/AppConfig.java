package io.asteroidsjaylib;

import io.asteroidsjaylib.common.ecs.BaseSystem;
import io.asteroidsjaylib.common.ecs.EntitySpi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

@Configuration
@ComponentScan(basePackages = "io.asteroidsjaylib")
public class AppConfig {

    @Bean
    public List<BaseSystem> baseSystems() {
        List<BaseSystem> systems = new ArrayList<>();
        ServiceLoader.load(BaseSystem.class).forEach(systems::add);
        return systems;
    }

    @Bean
    public List<EntitySpi> entitySpis() {
        List<EntitySpi> spis = new ArrayList<>();
        ServiceLoader.load(EntitySpi.class).forEach(spis::add);
        return spis;
    }

}
