package com.example.bo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class StartingStage extends Application {
    private GameStage gameStage;
    public static String myCssStyle = "-fx-min-width: 150;-fx-max-width: 150;-fx-min-height: 50;-fx-max-height : " +
            "50;-fx-background-color: #3c7fb1,linear-gradient(#fafdfe, " +
            "#e8f5fc),linear-gradient(#eaf6fd 0%, #d9f0fc 49%, #bee6fd 50%, #a7d9f5 100%);" +
            "fx-background-insets: 0,1,2;"+
            "-fx-background-radius: 3,2,1;-fx-padding: 3 30 3 30;-fx-text-fill: black;-fx-font-size: 14px;";
    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setResizable(false);
        primaryStage.setTitle("minus");

        Button easy = new Button("easy");
        easy.setStyle(myCssStyle);
        Button medium = new Button("medium");
        medium.setStyle(myCssStyle);
        Button hard = new Button("hard");
        hard.setStyle(myCssStyle);
        easy.relocate(50, 100);
        medium.relocate(50, 160);
        hard.relocate(50, 220);
        easy.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                gameStage = new GameStage(Levels.EASY);
                gameStage.start(primaryStage);
                System.out.println("easy  ");
            }
        });
        medium.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                gameStage = new GameStage(Levels.MEDIUM);
                gameStage.start(primaryStage);
                System.out.println("med");
            }
        });
        hard.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                gameStage = new GameStage(Levels.HARD);
                gameStage.start(primaryStage);
                System.out.println("hard");
            }
        });

        Pane pane = new Pane(easy, medium, hard);
        Scene scene = new Scene(pane,250,300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
