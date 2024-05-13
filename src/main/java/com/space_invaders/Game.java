package com.space_invaders;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.space_invaders.Ships.BossShip;
import com.space_invaders.Ships.EnemyShip;
import com.space_invaders.Ships.PlayerShip;
import com.space_invaders.Ships.Ship;
import com.space_invaders.Ships.StormShip;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Bounds;
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
        isGameEnded = false;
        clearBulletsAndShips();

        loadPlayerShip();
        loadTurret();

        field.setFocusTraversable(true);
        field.requestFocus();

        System.out.println("Game initialized");
        System.out.println("Ships: " + ships);
        System.out.println("Bullets: " + bullets);

        
    }

    public void loadTurret(){
        turret = new Turret( 50, Constants.Game.FIELD_HEIGHT-100);
        if(isTurretBought){
            field.getChildren().add(turret);
        }
    }

    private void loadEnemySpawnTimer(){
        enemySpawnTimer = new Timeline(new KeyFrame(Duration.seconds(1.5), event -> {
            spawnRandomEnemyShip();
        }));
        enemySpawnTimer.setCycleCount(Timeline.INDEFINITE);
        enemySpawnTimer.play();
    }


    // This part is fabulous, it encapsulates the game loop and the game logic,
    // but allows other classes to override it and add their own logic.
    public abstract class GameLoop extends AnimationTimer{

        GameLoop(){
            super();
            loadEnemySpawnTimer();
        }

        @Override
        public void handle(long now) {
            if (now - lastCheckTime > 300_000_000 && isTurretBought  ) { 
                turret.checkInterception();
                lastCheckTime = now;
            }
            checkCollision();

            if(playerShip.getHp() <= 0){
                endGame();
            }
        }
        @Override
        public void stop(){
            super.stop();
            if(this != null && enemySpawnTimer !=null){
                enemySpawnTimer.stop();
            }
            
        }
    }

    private void loadPlayerShip() {
        playerShip = new PlayerShip( Constants.Game.FIELD_WIDTH/2 - Constants.PlayerShip.SIZE, 700);
        playerShip.setHp(3);
        ships.add(playerShip);

        field.getChildren().add(playerShip);
        field.setOnKeyReleased(playerShip.new OverrideControls());
        
    }
    private void spawnRandomEnemyShip() {
        int randomXCoord = (int) (Math.random() * Constants.Game.FIELD_WIDTH);
        int randomInt = (int) (Math.random() * 20);
        if(randomInt < 18){
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
    Set<Bullet> bulletsToRemove = new HashSet<>();
    for (Bullet bullet : bullets) {
        if (!bullet.isActive || bullet.hasHit) continue;

        for (Ship ship : ships) {
            if (bullet.hasHit) break; 

            Bounds bulletBounds = bullet.getBoundsInParent();
            Bounds shipBounds = ship.getBoundsInParent();

            if (shipBounds.intersects(bulletBounds)) {
                boolean isEnemyShip = ship instanceof EnemyShip;
                boolean isPlayerShip = ship instanceof PlayerShip;
                boolean bulletUpOrCustom = bullet.direction == Direction.UP || bullet.direction == Direction.CUSTOM;
                boolean bulletDown = bullet.direction == Direction.DOWN;

                if ((isEnemyShip && bulletUpOrCustom) || (isPlayerShip && bulletDown)) {
                    System.out.println("Collision detected");
                    System.out.println("Ship: " + ship);
                    System.out.println("Bullet: " + bullet);
                    bullet.hasHit = true; 
                    bulletsToRemove.add(bullet);
                    ship.hit();
                }
            }
        }
    }
   
    bullets.removeAll(bulletsToRemove); 
    for (Bullet bullet : bulletsToRemove) {
        if (bullet.getParent() != null) {
            ((AnchorPane) bullet.getParent()).getChildren().remove(bullet);
        } else {
            System.out.println("Attempting to cleanup orphan bullet.");
        }
    }
}

    
    
    public void removeBullet(Bullet bullet) {
        field.getChildren().remove(bullet);
        bullets.remove(bullet);
    }


    public static void removeBulletStatic(Bullet bullet){
        if (!bullets.contains(bullet)) return;
        if (bullet.getParent() == null) {
            System.out.println("Attempting to cleanup orphan bullet.");
        } else {
            ((AnchorPane)bullet.getParent()).getChildren().remove(bullet);
        }
        bullets.remove(bullet);
    }
    

    public static void addBullet(Bullet bullet) {
        if(isGameEnded){
            return;
        }
        System.out.println("Adding bullet to game at: (" + bullet.getX() + ", " + bullet.getY() + ")");
        bullets.add(bullet);

    }
    private void endGame(){
        Game.isGameEnded = true;

        clearBulletsAndShips();
        stopAllGameLoopsAndTimers();

        field.getChildren().clear();

        Label gameOverLabel = new Label("Game Over");
        gameOverLabel.setStyle("-fx-font-size: 32; -fx-text-fill: white; -fx-font-weight: bold;");
        gameOverLabel.setLayoutX(Constants.Game.FIELD_WIDTH/2 - 100);
        gameOverLabel.setLayoutY(300);

        Button restartButton = new Button("Restart");
        restartButton.setLayoutX(Constants.Game.FIELD_WIDTH/2 - 50);
        restartButton.setLayoutY(400);
        restartButton.setOnAction(event -> {
            Game.isGameEnded = false;
            restartGame();
            
        });

        field.getChildren().addAll(gameOverLabel, restartButton);
        
    }

    public static void removeShip(Ship ship) {
        if(!ships.contains(ship) ) return;
        ships.remove(ship);
        AnchorPane parent = (AnchorPane) ship.getParent();
        if(parent == null) return;
        parent.getChildren().remove(ship);
    }



    public void restartGame(){
        try {
            stopAllGameLoopsAndTimers(); 
            clearBulletsAndShips();      
            field.getChildren().clear();  
            App.setRoot("main.fxml");     
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void stopAllGameLoopsAndTimers() {
        if (gameLoop != null) {
            gameLoop.stop();  
        }
        if (enemySpawnTimer != null) {
            enemySpawnTimer.stop();  
        }
        
    }

    public static List<Ship> getShips(){
        return ships;
    }

    public void clearBulletsAndShips(){
        Game.bullets.clear();
        Game.ships.clear();
        ships.clear();
        bullets.clear();
    }
}
    