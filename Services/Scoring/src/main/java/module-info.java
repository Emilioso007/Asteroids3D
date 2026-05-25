module Scoring {
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.web;

    opens io.asteroidsjaylib.service.scoring;
}