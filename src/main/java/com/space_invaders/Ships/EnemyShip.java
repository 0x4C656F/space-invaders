package com.space_invaders.Ships;

import javafx.util.Duration;

import java.util.Random;

import com.space_invaders.App;
import com.space_invaders.Bullet;
import com.space_invaders.Direction;
import com.space_invaders.Game;

import javafx.animation.KeyFrame; 
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
public class EnemyShip extends Ship{

    public int coinReward ;

    private Timeline shootTimeline;

    private final int TRANSITION_SPEED_SIZE_KOEFFICIENT = 40; //if i increase this value, the enemy will move faster

    public EnemyShip(@SuppressWarnings("exports") Image node, int x, int y, int size) {
        super(node, x, y, size);
        initiateShootingTimeline();
        initiateRandomMovement();
    }

    private void initiateShootingTimeline() {
        shootTimeline = new Timeline(new KeyFrame(Duration.seconds(3), event -> {
            shoot();
        }));
        shootTimeline.setCycleCount(Timeline.INDEFINITE);
        shootTimeline.play();
    }

    private void initiateRandomMovement() {
        boolean moveRight = new Random().nextBoolean();

        Timeline timeline = new Timeline(
            new KeyFrame(Duration.seconds(0.5 * size/TRANSITION_SPEED_SIZE_KOEFFICIENT), e -> moveBottom()),
            new KeyFrame(Duration.seconds(0.5 * 2 * size/TRANSITION_SPEED_SIZE_KOEFFICIENT), e -> {
                if (moveRight) {
                    moveRight();
                } else {
                    moveLeft();
                }
            }),
            new KeyFrame(Duration.seconds(0.5 * 3 * size/TRANSITION_SPEED_SIZE_KOEFFICIENT), e -> moveBottom()),
            new KeyFrame(Duration.seconds(0.5 * 4 * size/TRANSITION_SPEED_SIZE_KOEFFICIENT), e -> {
                if (moveRight) {
                    moveLeft();
                } else {
                    moveRight();
                }
            })
        );
        timeline.setOnFinished(e -> initiateRandomMovement()); 
        timeline.setCycleCount(1); 
        timeline.play();
    }


    void animateMovement(double deltaX, double deltaY) {
        TranslateTransition transition = new TranslateTransition(new Duration(300), this);
        transition.setByX(deltaX);
        transition.setByY(deltaY);
        transition.play();
    }

    private void moveBottom() {
        if(hp <= 0){
            return;
        }
        double newY = Math.min(this.getTranslateY() + moveSpeed, 800 - size);
        if (newY != this.getTranslateY()) {
            animateMovement( 0, (double) newY - this.getTranslateY());
        }
    }
    @Override
    public void shoot(){
        super.shoot();
        if(hp <= 0){
            return;
        }
        int bulletWidth = size / 12;
        int xCoord = (int) getTranslateX() + size / 2 - bulletWidth/ 2;
        int yCoord = (int) getTranslateY() +  size + bulletWidth * 2;
        Bullet bullet = new Bullet(xCoord, yCoord,bulletWidth, Direction.DOWN);
        AnchorPane parent = (AnchorPane)getParent();
        if(parent == null){
            return;
        }
        parent.getChildren().add(bullet);

        Game.addBullet(bullet);
    }

    @Override
    void destroy() {
        super.destroy();
        App.addCoins(coinReward);
        shootTimeline.stop();
    }

  
    
}
