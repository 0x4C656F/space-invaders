package com.space_invaders;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class MainMenuController {
    
    @FXML
    private VBox field;

    @FXML
    private Button startButton;

    @FXML
    private Button shopButton;

    public void initialize() {
        startButton.setOnAction(e -> {
            try{
                App.setRoot("main.fxml");
            }catch(IOException ex){
                ex.printStackTrace();
            }
        });
        shopButton.setOnAction(e -> {
            try{
                App.setRoot("shop.fxml");
            }catch(IOException ex){
                ex.printStackTrace();
            }
        });
    }
}
