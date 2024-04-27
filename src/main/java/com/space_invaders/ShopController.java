package com.space_invaders;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class ShopController {
    

    @FXML
    private Button backButton;

    @FXML
    private VBox turretBox;

    public void initialize() {
        backButton.setOnAction(e -> {
            try{
                App.setRoot("main-menu.fxml");
            }catch(Exception ex){
                ex.printStackTrace();
            }
        });
        if(Game.isTurretBought){
            return;
        }
        Image turretImage = new Image(getClass().getResourceAsStream("/images/turret.png"));
        Button turretButton = new Button("Buy Turret - 20 coins", new ImageView(turretImage));
        turretButton.setStyle("-fx-background-color: #000000; -fx-text-fill: #ffffff; -fx-font-size: 20px; -fx-padding: 10px; -fx-border-color: #ffffff; -fx-border-width: 2px; -fx-border-radius: 10px; -fx-background-radius: 10px;");
        
        ImageView turretImageView = new ImageView(turretImage);
        turretImageView.preserveRatioProperty();
        turretImageView.setFitWidth(50);
        turretButton.setOnAction(e -> {
            if(App.coins >= 20){
                App.coins -= 20;
                Game.isTurretBought = true;
                try{
                    App.setRoot("main-menu.fxml");
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }else{
                turretButton.setText("Not enough coins");}
        });
        turretBox.getChildren().add(turretButton);
    }
}
