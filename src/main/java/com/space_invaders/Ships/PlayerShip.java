package com.space_invaders.Ships;


import com.space_invaders.Bullet;
import com.space_invaders.Constants;
import com.space_invaders.Direction;
import com.space_invaders.Game;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
public class PlayerShip extends Ship{


    public PlayerShip( int x, int y) {
        super(new Image(PlayerShip.class.getResourceAsStream("/images/player-ship.png")), x, y, Constants.PlayerShip.SIZE);
        this.hp = Constants.PlayerShip.HP;
        this.moveSpeed = Constants.PlayerShip.MOVE_SPEED;
    }
    
    @Override
    public void hit(){
        System.out.println("PlayerShip hit");
        super.hit();
        if(hp == 2){
            setFill(new ImagePattern(new Image(getClass().getResourceAsStream("/images/player-ship-broken-1.png"))));

        }
        else if(hp == 1){
            setFill(new ImagePattern(new Image(getClass().getResourceAsStream("/images/player-ship-broken-2.png"))));
        }
        
    }

    public int getHp(){return hp;}
    public int setHp(int hp){return this.hp = hp;}

    public class OverrideControls implements EventHandler<KeyEvent> {
        @Override
        public void handle(@SuppressWarnings("exports") KeyEvent event) {
            if (event.getCode() == KeyCode.LEFT) {
                moveLeft();
            } else if (event.getCode() == KeyCode.RIGHT) {
                moveRight();
            } 
            if (event.getCode() == KeyCode.SPACE) {
                shoot();
            }
            event.consume();
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
        int yCoord = (int) ((int) getTranslateY() + size / 2 - bulletWidth * 2);
        Bullet bullet = new Bullet(xCoord, yCoord,bulletWidth, Direction.UP);
        ((AnchorPane)getParent()).getChildren().add(bullet);
        Game.addBullet(bullet);
        bullet.toBack();
    }

    
}
