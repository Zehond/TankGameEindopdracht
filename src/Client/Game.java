package Client;

import Data.Bullet;
import Data.InputHandler;
import Data.Map;
import Data.Tank;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.List;

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
    public void Init() {
        player = new Tank(this.inputHandler = new InputHandler());
        enemy = new Tank(this.inputHandler = new InputHandler());

    }
    public void Update() {
        player.update();
        enemy.update();

        updateBullets(player);
        updateBullets(enemy);
        updateCollisions();
    }

    private void updateBullets(Tank tank) {
        List<Bullet> bullets = tank.getBullets();
        for (Bullet bullet : bullets) {
            bullet.update();
            if (bulletHitWall(bullet)){
                bullets.remove(bullet);
            }
        }


    }
    private boolean bulletHitWall(Bullet bullet){
        //todo: implement this method with collision detection logic to the wall
        return false;
    }
   private void checkBulletTankCollision(Tank shooter, Tank enemy){
       List<Bullet> bullets = shooter.getBullets();
         for (Bullet bullet : bullets){
              if (isHitTank(bullet, enemy)){
                enemy.takeDamage();
                bullets.remove(bullet);
              }
         }

   }
    private boolean isHitTank(Bullet bullet, Tank enemy){
        //TODO: Implement this method with collision detection logic
        return false;
    }

    private void updateCollisions(){
        checkBulletTankCollision(player, enemy);
        checkBulletTankCollision(enemy, player);
    }

}
