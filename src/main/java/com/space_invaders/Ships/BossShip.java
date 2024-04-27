package com.space_invaders.Ships;

import com.space_invaders.Constants;

import javafx.scene.image.Image;

public class BossShip extends EnemyShip {
    public static int size = Constants.BossShip.SIZE;
    
    public BossShip(int x, int y) {
        super(new Image(StormShip.class.getResourceAsStream("/images/boss-ship.png")), x, y ,size);
        this.hp = Constants.BossShip.HP;
        this.coinReward = Constants.BossShip.COIN_REWARD;
        this.moveSpeed = Constants.BossShip.MOVE_SPEED; 
    }
    
}
