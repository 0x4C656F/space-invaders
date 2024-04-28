package com.space_invaders;
import java.util.ArrayList;
import java.util.List;

import com.space_invaders.Ships.BossShip;
import com.space_invaders.Ships.EnemyShip;
import com.space_invaders.Ships.PlayerShip;
import com.space_invaders.Ships.Ship;
import com.space_invaders.Ships.StormShip;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class Game {
    public static boolean isTurretBought = false;

    public PlayerShip playerShip;
    
    public static boolean isGameEnded = false;

    public static List<Ship> ships = new ArrayList<>();

    public static List<Bullet> bullets = new ArrayList<>();

    @SuppressWarnings("exports")
    public AnimationTimer gameLoop;

    public Turret turret;

    @SuppressWarnings("exports")
    public AnchorPane field;

    private long lastCheckTime = 0;

    private Timeline enemySpawnTimer;

    Game(AnchorPane field){
        this.field = field;

        loadPlayerShip();

        Turret turret = new Turret( 50, Constants.Game.FIELD_HEIGHT-100);
        if(isTurretBought){
            field.getChildren().add(turret);
        }
        this.field = field;
        ships.add(playerShip);
        enemySpawnTimer = new Timeline(new KeyFrame(Duration.seconds(1.5), event -> {
            spawnRandomEnemyShip();
        }));

        enemySpawnTimer.setCycleCount(Timeline.INDEFINITE);
        enemySpawnTimer.play();
    }


    public abstract class GameLoop extends AnimationTimer{
        @Override
        public void handle(long now) {
            if(playerShip.getHp() <= 0) {
                enemySpawnTimer.stop();
                gameLoop.stop();    
                return;
            };

            if (now - lastCheckTime > 300_000_000 && isTurretBought  ) { 
                turret.checkInterception();
                lastCheckTime = now;
            }
            checkCollision();
        }

        public void stopGame(){
            enemySpawnTimer.stop();
            gameLoop.stop();
        }
    }

    private void loadPlayerShip() {
        playerShip = new PlayerShip( Constants.Game.FIELD_WIDTH/2 - Constants.PlayerShip.SIZE, 700);
        playerShip.setHp(3);
        ships.add(playerShip);

        field.getChildren().add(playerShip);
        field.setFocusTraversable(true);
        field.requestFocus();
        field.setOnKeyReleased(playerShip.new OverrideControls());
        
    }
    private void spawnRandomEnemyShip() {
        int randomXCoord = (int) (Math.random() * Constants.Game.FIELD_WIDTH);
        int randomInt = (int) (Math.random() * 20);
        if(randomInt < 19){
            EnemyShip ship = new StormShip(randomXCoord, -StormShip.size);
            field.getChildren().add(ship);
            ships.add(ship);
        } else {
            EnemyShip ship = new BossShip(randomXCoord, -BossShip.size);
            field.getChildren().add(ship);
            ships.add(ship);
        }
    }

    public void checkCollision() {
        for (Bullet bullet : bullets) {
            for (Ship ship : ships) {
                if (ship.getBoundsInParent().intersects(bullet.getBoundsInParent())) {
                    if(ship instanceof EnemyShip && (bullet.direction == Direction.UP || bullet.direction == Direction.CUSTOM)){
                        ship.hit();
                        removeBullet(bullet);
                    }
                    else if(ship instanceof PlayerShip && bullet.direction == Direction.DOWN){
                        ship.hit();
                        removeBullet(bullet);
                    }
                    else{
                        continue;
                    }
                    
                }
            }
        }
    }

    public static void removeBullet(Bullet bullet) {
        if(!bullets.contains(bullet) ) return;
        bullets.remove(bullet);
        AnchorPane parent  = (AnchorPane)  bullet.getParent();
        parent.getChildren().remove(bullet);
    }

    public static void addBullet(Bullet bullet) {
        bullets.add(bullet);
        
        
    }

    public static void removeShip(Ship ship) {
        if(!ships.contains(ship) ) return;
        ships.remove(ship);
        ((AnchorPane) ship.getParent()).getChildren().remove(ship);
    }

    private void endGame(){
        isGameEnded = true;
        clear();

        Label gameOverLabel = new Label("Game Over");
        gameOverLabel.setStyle("-fx-font-size: 32; -fx-text-fill: white; -fx-font-weight: bold;");
        gameOverLabel.setLayoutX(Constants.Game.FIELD_WIDTH/2 - 100);
        gameOverLabel.setLayoutY(300);

        Button restartButton = new Button("Restart");
        restartButton.setLayoutX(Constants.Game.FIELD_WIDTH/2 - 50);
        restartButton.setLayoutY(400);
        restartButton.setOnAction(event -> {
            isGameEnded = false;
            field.getChildren().clear();
            
        });

        field.getChildren().addAll(gameOverLabel, restartButton);
        
    }

    public void clear(){
        enemySpawnTimer.stop();
        gameLoop.stop();
        field.getChildren().clear();
        ships.clear();
        bullets.clear();
    }
}
