module Scoring {
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.web;
    requires spring.core;
    requires spring.beans;
    requires spring.context;

    requires java.instrument;

    opens io.asteroidsjaylib.service.scoring to spring.core, spring.beans, spring.context, spring.web;
}