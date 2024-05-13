package com.space_invaders.Ships;



import com.space_invaders.Constants;
import com.space_invaders.Game;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
public class Ship extends Rectangle{
    int hp;
    int moveSpeed;
    int size;
    int damage;

    Ship(Image image, int x, int y, int size) {
        super(size, size); 

        setFill(new ImagePattern(image));
        setTranslateX(x);
        setTranslateY(y);
        
        this.size = size;
    }   

    public void moveRight() {
        if(hp <= 0){
            return;
        }
        double newX = Math.min(this.getTranslateX() + moveSpeed, Constants.Game.FIELD_WIDTH - size);
        if (newX != this.getTranslateX()) {
            animateMovement(newX - this.getTranslateX(), 0); 
        }
    }

    public void moveLeft() {
        if(hp <= 0){
            return;
        }
        double newX = Math.max(this.getTranslateX() - moveSpeed, 0);
        if (newX != this.getTranslateX()) {
            animateMovement(newX - this.getTranslateX(), 0); 
        }
    }

     void animateMovement(double deltaX, double deltaY) {
        TranslateTransition transition = new TranslateTransition(new Duration(100), this);
        transition.setByX(deltaX);
        transition.setByY(deltaY);
        transition.play();
    }
    
    public void shoot(){
        if(Game.isGameEnded){
            return;
        }
    };

    public void hit(){
        hp--;

        if(hp == 0){
            triggerExplosion();
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
                destroy();
            }));
            timeline.setCycleCount(1);
            timeline.play();
            

            
        }
    }

    public void triggerExplosion(){
        setFill(new ImagePattern(new Image(getClass().getResourceAsStream("/images/explosion.png"))));
    }

     void destroy(){
        Game.removeShip(this);
    }
}
