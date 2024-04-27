package com.space_invaders;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class App extends Application {

    public static int coins;

    private static Scene scene;

    @SuppressWarnings("exports")
    @Override
    public void start(Stage stage) throws IOException {
       
        loadCoins();

        scene = new Scene(loadFXML("main-menu.fxml"), Constants.Game.FIELD_WIDTH, Constants.Game.FIELD_HEIGHT);
        stage.setScene(scene);
        stage.show();
        setBackground();

    }

    public static void loadCoins() {      
        Path path = Paths.get(System.getProperty("user.dir"), "coins.txt");
        File file = path.toFile();
    
        if (!file.exists()) {
            try {
                Files.createFile(path);
                coins = 0; 
                Files.write(path, String.valueOf(coins).getBytes());
            } catch (IOException e) {
                System.err.println("An error occurred while creating or writing to the coins file: " + e.getMessage());
            }
        } else {
            try {
                String content = Files.readString(path);
                coins = Integer.parseInt(content);
            } catch (IOException e) {
                System.err.println("An error occurred while reading the coins file: " + e.getMessage());
            } catch (NumberFormatException e) {
                System.err.println("The coins file does not contain a valid integer: " + e.getMessage());
            }
        }
    }

    public static void setBackground(){
        Image bgImage = new Image(App.class.getResourceAsStream("/images/bg.png"));
        BackgroundImage backgroundImage = new BackgroundImage(bgImage,
        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
        BackgroundSize.DEFAULT);
        Background background = new Background(backgroundImage);
        ((HBox) scene.getRoot()).setBackground(background);
        
    }

    public static int getCoins(){
        return coins;
    }

    public static int addCoins(int amount){
        coins += amount;
        writeCoinsToFile();
        return coins;
    }
    
    private static void writeCoinsToFile() {
        Path path = Paths.get(System.getProperty("user.dir"), "coins.txt");
        try {
            Files.write(path, String.valueOf(coins).getBytes());
        } catch (IOException e) {
            System.err.println("An error occurred while writing to the coins file: " + e.getMessage());
        }
    }
 
    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
        setBackground();
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}