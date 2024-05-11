package com.space_invaders;

public class Constants {
    
    public static class Game {
        public static final int FIELD_WIDTH = 600; // Width of the game field
        public static final int FIELD_HEIGHT = 800; // Height of the game field
    }

    public static class Bullet {
        public static final int SPEED = 3; // Speed of the bullet
    }

    public static class EnemyShip{
        public static final double SIZE_SPEED_KOEFFICIENT = 200; // Speed of the enemy ship. If I increase this value, the enemy will move faster
    }

    public static class PlayerShip {
        public static final int SIZE = 50; // Size of the player ship
        public static final int MOVE_SPEED = 50; // Speed of the player ship
        public static final int HP = 3; // Health points of the player ship

    }

    public static class StormShip {
        public static final int SIZE = 50; // Size of the storm ship
        public static final int HP = 1; // Health points of the storm ship
        public static final int COIN_REWARD = 1; // Coin reward for destroying the storm ship
        public static final int MOVE_SPEED = 50; // Speed of the storm ship
    }

    public static class BossShip {
        public static final int SIZE = 100; // Size of the boss ship
        public static final int HP = 5; // Health points of the boss ship
        public static final int COIN_REWARD = 3; // Coin reward for destroying the boss ship
        public static final int MOVE_SPEED = 20; // Speed of the boss ship
    }

}
