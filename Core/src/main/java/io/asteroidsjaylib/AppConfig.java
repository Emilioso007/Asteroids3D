package io.asteroidsjaylib;

import io.asteroidsjaylib.common.ecs.BaseSystem;
import io.asteroidsjaylib.common.ecs.EntitySpi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

@Configuration
@ComponentScan(basePackages = "io.asteroidsjaylib")
public class AppConfig {

    @Autowired
    private ConfigurableListableBeanFactory beanFactory;

    @Bean
    public List<BaseSystem> baseSystems() {
        List<BaseSystem> systems = new ArrayList<>();
        for (BaseSystem system : ServiceLoader.load(BaseSystem.class)) {

            String beanName = system.getClass().getName();

            beanFactory.registerSingleton(beanName, system);
            beanFactory.autowireBean(system);
            beanFactory.initializeBean(system, beanName);
            systems.add(system);
        }
        return systems;
    }

    @Bean
    public List<EntitySpi> entitySpis() {
        List<EntitySpi> spis = new ArrayList<>();
        for(EntitySpi entitySpi : ServiceLoader.load(EntitySpi.class)) {
            String beanName = entitySpi.getClass().getName();
            beanFactory.registerSingleton(beanName, entitySpi);
            beanFactory.autowireBean(entitySpi);
            beanFactory.initializeBean(entitySpi, beanName);
            spis.add(entitySpi);
        }
        return spis;
    }

}
