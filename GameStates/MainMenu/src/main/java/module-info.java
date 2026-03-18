import io.asteroidsjaylib.common.ecs.IGameStateProvider;
import io.asteroidsjaylib.state.mainmenu.MainMenu;

module MainMenu {
    uses io.asteroidsjaylib.common.button.ButtonSPI;
    uses io.asteroidsjaylib.common.ecs.BaseSystem;
    requires Common;
    requires CommonButton;

    provides IGameStateProvider with MainMenu;
}