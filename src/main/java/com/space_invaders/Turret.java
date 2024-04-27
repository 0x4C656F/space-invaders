package com.space_invaders;

import com.space_invaders.Ships.PlayerShip;
import com.space_invaders.Ships.Ship;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import javafx.scene.layout.AnchorPane;
public class Turret extends Group {
    private Timeline rotationAnimation;
    private ImageView base;
    private Rectangle laser;
    private Rotate rotate; 


    public Turret(double x, double y) {
        Image turretImage = new Image(getClass().getResourceAsStream("/images/turret.png"));
        base = new ImageView(turretImage);
        base.setX(x);
        base.setY(y);
        base.setFitWidth(30);
        base.setPreserveRatio(true);

        laser = new Rectangle(5, Math.sqrt(Math.pow(Constants.Game.FIELD_HEIGHT, 2) + Math.pow(Constants.Game.FIELD_WIDTH, 2)));
        // laser.setFill(Color.RED);
        // laser.setStroke(Color.YELLOW);
        // laser.setStrokeWidth(2); 
        // laser.setY(y);
        laser.setFill(Color.TRANSPARENT);
        laser.setStroke(Color.TRANSPARENT);
        laser.setStrokeWidth(0);
        laser.setX(x + base.getFitWidth()/2 - 2.5); 
        laser.setY(y + 25 - laser.getHeight()); 

        this.getChildren().addAll(base, laser);

        initializeRotationAnimation(x + base.getFitWidth() / 2, y + base.getFitHeight() / 2);

        rotateTurret();
    }

    public void checkInterception() {
        for (Ship ship : Game.ships) {
            if(ship instanceof PlayerShip){
                continue;
            }
            Shape intersection = Shape.intersect(laser, ship);
            if (intersection.getBoundsInLocal().getWidth() != -1) {
                shoot();
            }
        }
    }

    private void initializeRotationAnimation(double pivotX, double pivotY) {
        rotate = new Rotate(0, pivotX, pivotY);
        this.getTransforms().add(rotate);

        rotationAnimation = new Timeline(
            new KeyFrame(Duration.ZERO, new KeyValue(rotate.angleProperty(), 0)),
            new KeyFrame(Duration.seconds(5), new KeyValue(rotate.angleProperty(), 60, Interpolator.EASE_BOTH)),
            new KeyFrame(Duration.seconds(10), new KeyValue(rotate.angleProperty(), 0, Interpolator.EASE_BOTH))
        );
        rotationAnimation.setCycleCount(Timeline.INDEFINITE);
        rotationAnimation.setAutoReverse(false);
    }

    public void rotateTurret() {
        if (rotationAnimation.getStatus() == javafx.animation.Animation.Status.RUNNING) {
            rotationAnimation.pause();
        } else {
            rotationAnimation.play();
        }
    }


    public double getCurrentAngle() {
        return rotate.getAngle();
    }

    public void shoot() {
        Direction direction = Direction.CUSTOM;
        direction.setAngle(-90+(int) rotate.getAngle());

        Bullet bullet = new Bullet(60, 720, 3, direction);
        bullet.toBack();
        ((AnchorPane)getParent()).getChildren().add(bullet);
        Game.addBullet(bullet);
    }
}
