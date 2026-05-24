#figure(
    ```java

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
        public List<EntitySPI> entitySpis() {
            /* Similar logic as above */
        }

    }

    ```,
    caption: [Appconfig class],
    supplement: [Code Snippet]
) <appconfig-class>