package io.asteroidsjaylib;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main{

    static void main() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        Game game = context.getBean(Game.class);
        game.start();
        context.close();
    }

}