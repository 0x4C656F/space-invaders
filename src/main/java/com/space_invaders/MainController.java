package com.space_invaders;

import java.util.ArrayList;
import java.util.List;

import com.space_invaders.Game.GameLoop;
import com.space_invaders.Ships.BossShip;
import com.space_invaders.Ships.EnemyShip;
import com.space_invaders.Ships.PlayerShip;
import com.space_invaders.Ships.Ship;
import com.space_invaders.Ships.StormShip;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import javafx.scene.layout.HBox;
import javafx.util.Duration;

public class MainController {

    @FXML
    private AnchorPane field;

    public static boolean isGameEnded = false;

    private List<ImageView> hpImages = new ArrayList<>();

    private Label scoreLabel;

    private AnimationTimer timer;

    private Timeline timeline;

    private Game game;
    
    public void initialize() {
        
        game = new Game(field);

        loadBackToManuButton();

        loadHeartImages();

        loadScore();
        Turret turret = new Turret( 50, Constants.Game.FIELD_HEIGHT-100);
        if(Game.isTurretBought){
            field.getChildren().add(turret);
        }
        GameLoop gameLoop = game.new GameLoop(){
            @Override
            public void handle(long now) {
                super.handle(now);
                scoreLabel.setText(String.valueOf(App.getCoins()));
                
            }
        };
        gameLoop.start();


    }

    private void loadBackToManuButton(){
        Button backButton = new Button("Back to Menu");
        backButton.setLayoutX(20);
        backButton.setLayoutY(20);
        backButton.setOnAction(e -> {
            try{
                clear();
                App.setRoot("main-menu.fxml");
            }catch(Exception ex){
                ex.printStackTrace();
            }
        });
        field.getChildren().add(backButton);
        backButton.toFront();
    }

    private void loadScore() {
        HBox scoreBox = new HBox();
        Image scoreImage = new Image(getClass().getResourceAsStream("/images/coin.png"));
        ImageView scoreImageView = new ImageView(scoreImage);
        scoreImageView.setFitWidth(30);
        scoreImageView.setPreserveRatio(true);

        scoreLabel = new Label(String.valueOf(App.getCoins()));
        scoreLabel.setStyle("-fx-font-size: 24; -fx-text-fill: white; -fx-font-weight: bold;");
        scoreBox.getChildren().addAll(scoreImageView, scoreLabel);
        scoreBox.setLayoutX(Constants.Game.FIELD_WIDTH - 80);
        scoreBox.setLayoutY(Constants.Game.FIELD_HEIGHT - 40);
        field.getChildren().add(scoreBox);
    }

    private void loadHeartImages () {
        Image image = new Image(getClass().getResourceAsStream("/images/heart.png"));
        hpImages.clear(); 

        for (int i = 0; i < game.playerShip.getHp(); i++) {
            ImageView heart = new ImageView(image);
            heart.setFitWidth(30);
            heart.setPreserveRatio(true);
            heart.setLayoutX(10 + i * 30);
            heart.setLayoutY(Constants.Game.FIELD_HEIGHT-40);
            field.getChildren().add(heart);
            hpImages.add(heart); 
        }
    }


    // private void spawnRandomEnemyShip() {
    //     int randomXCoord = (int) (Math.random() * Constants.Game.FIELD_WIDTH);
    //     int randomInt = (int) (Math.random() * 20);
    //     if(randomInt < 19){
    //         EnemyShip ship = new StormShip(randomXCoord, -StormShip.size);
    //         field.getChildren().add(ship);
    //         ships.add(ship);
    //     } else {
    //         EnemyShip ship = new BossShip(randomXCoord, -BossShip.size);
    //         field.getChildren().add(ship);
    //         ships.add(ship);
    //     }
    // }

    // public void checkCollision() {
    //     for (Bullet bullet : bullets) {
    //         for (Ship ship : ships) {
    //             if (ship.getBoundsInParent().intersects(bullet.getBoundsInParent())) {
    //                 if(ship instanceof EnemyShip && (bullet.direction == Direction.UP || bullet.direction == Direction.CUSTOM)){
    //                     ship.hit();
    //                     removeBullet(bullet);
    //                 }
    //                 else if(ship instanceof PlayerShip && bullet.direction == Direction.DOWN){
    //                     ship.hit();
    //                     removeBullet(bullet);

    //                     if(playerShip.getHp() >= 0 && !hpImages.isEmpty()) {
    //                         ImageView lastHeart = hpImages.remove(hpImages.size() - 1);
    //                         field.getChildren().remove(lastHeart);
    //                     }
    
    //                     if(playerShip.getHp() == 0){
    //                         endGame();
    //                     }  
    //                 }
    //                 else{
    //                     continue;
    //                 }
                    
    //             }
    //         }
    //     }
    // }

    // private void endGame(){
    //     isGameEnded = true;
    //     clear();

    //     Label gameOverLabel = new Label("Game Over");
    //     gameOverLabel.setStyle("-fx-font-size: 32; -fx-text-fill: white; -fx-font-weight: bold;");
    //     gameOverLabel.setLayoutX(Constants.Game.FIELD_WIDTH/2 - 100);
    //     gameOverLabel.setLayoutY(300);

    //     Button restartButton = new Button("Restart");
    //     restartButton.setLayoutX(Constants.Game.FIELD_WIDTH/2 - 50);
    //     restartButton.setLayoutY(400);
    //     restartButton.setOnAction(event -> {
    //         isGameEnded = false;
    //         field.getChildren().clear();
    //         initialize();
    //     });


        
    //     field.getChildren().addAll(gameOverLabel, restartButton);
        
    // }

    public void clear(){
        timer.stop();
        timeline.stop();
        field.getChildren().clear();
    }

    // public static void removeBullet(Bullet bullet) {
    //     if(!bullets.contains(bullet) ) return;
    //     bullets.remove(bullet);
    //     AnchorPane parent  = (AnchorPane)  bullet.getParent();
    //     parent.getChildren().remove(bullet);
    // }

    // public static void addBullet(Bullet bullet) {
    //     bullets.add(bullet);
        
        
    // }

    // public static void removeShip(Ship ship) {
    //     if(!ships.contains(ship) ) return;
    //     ships.remove(ship);
    //     ((AnchorPane) ship.getParent()).getChildren().remove(ship);
    // }
}
