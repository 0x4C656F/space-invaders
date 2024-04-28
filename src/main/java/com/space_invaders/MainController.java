package com.space_invaders;

import java.util.ArrayList;
import java.util.List;

import com.space_invaders.Game.GameLoop;
import javafx.animation.AnimationTimer;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import javafx.scene.layout.HBox;

public class MainController {
    //To stop game you have to stop the gameLoop, and clear the field
    @FXML
    private AnchorPane field;

    public static boolean isGameEnded = false;

    private List<ImageView> hpImages = new ArrayList<>();

    private Label scoreLabel;

    private GameLoop gameLoop;

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
        gameLoop = game.new GameLoop(){
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

    public void clear(){
        gameLoop.stop();    
        field.getChildren().clear();
    }

}
