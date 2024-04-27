package com.space_invaders.Ships;

import com.space_invaders.Constants;

import javafx.scene.image.Image;

public class StormShip extends EnemyShip {
    public static int size = Constants.StormShip.SIZE;
    public StormShip(int x, int y) {
        super(new Image(StormShip.class.getResourceAsStream("/images/storm-ship.png")), x, y ,size);
        this.hp = Constants.StormShip.HP;
        this.coinReward = Constants.StormShip.COIN_REWARD;
        this.moveSpeed = Constants.StormShip.MOVE_SPEED;

    }
}
