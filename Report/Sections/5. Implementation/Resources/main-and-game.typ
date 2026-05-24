#figure(
    ```java

    static void main() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        Game game = context.getBean(Game.class);
        game.start();
        context.close();
    }

    @Autowired
    public Game(List<BaseSystem> systems, List<EntitySPI> entitySPIS) {
        this.systems = systems;
        this.entitySPIS = entitySPIS;
    }

    ```,
    caption: [Main method entrypoint and Game constructor.],
    supplement: [Code Snippet]
) <main-and-game>
