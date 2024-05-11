package Client;

import Data.InputHandler;
import Data.Map;
import Data.Tank;
import javafx.application.Application;
import javafx.stage.Stage;

public class Game extends Application {
    private Map map;
    private Tank player;
    private Tank enemy;
    private InputHandler inputHandler;

    public Game() {

    }

    public void start(Stage stage) throws Exception {

    }

    public void Draw() {

    }

    public void Update() {

    }

    public void Init() {
        player = new Tank(this.inputHandler = new InputHandler());
        enemy = new Tank(this.inputHandler = new InputHandler());

    }
}
