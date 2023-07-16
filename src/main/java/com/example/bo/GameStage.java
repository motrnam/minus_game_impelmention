package com.example.bo;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Optional;

public class GameStage extends Application {
    private final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    private final int UNIT = 30;
    private Game game;
    private final Label[][] allLabels;
    private Label bombLabel;
    private final StartingStage startingStage;
    private static Stage myStage;
    private boolean firstUse = true;
    private Scene scene = new Scene(new Pane());


    public GameStage(Levels level,StartingStage startingStage) {
        this.level = level;
        game = new Game(level);
        this.startingStage = startingStage;
        allLabels = new Label[level.getHeight()][level.getWidth()];
    }

    private final Levels level;

    @Override
    public void start(Stage primaryStage) {
        if (firstUse) {
            alert.setHeaderText(null);
            firstUse = false;
            myStage = primaryStage;
            Pane gamePane = new Pane();
            for (int i = 0; i < level.getHeight(); i++) {
                for (int j = 0; j < level.getWidth(); j++) {
                    Label label = new Label();
                    allLabels[i][j] = label;
                    label.setPrefSize(UNIT, UNIT);
                    label.relocate(UNIT * i, UNIT * j);
                    label.setStyle("-fx-background-color : yellow;-fx-border-color: black");
                    int finalI = i;
                    int finalJ = j;
                    label.setOnMouseClicked(event -> {
                        if (event.getButton() == MouseButton.PRIMARY)
                            rightClick(finalI, finalJ);
                        else
                            leftClick(finalI, finalJ);
                    });
                    gamePane.getChildren().add(label);
                }
            }

            Button resetButton = new Button("reset");
            resetButton.setStyle(StartingStage.myCssStyle);
            resetButton.setOnMouseClicked(event -> reset());
            resetButton.relocate(50, UNIT * level.getHeight() + 55);

            Button backButton = new Button("back");
            backButton.setStyle(StartingStage.myCssStyle);
            backButton.setOnMouseClicked(event -> goBack());
            backButton.relocate(200, UNIT * level.getHeight() + 55);

            gamePane.relocate(50, 50);
            bombLabel = new Label(String.valueOf(game.getRemindedBomb()));
            bombLabel.relocate(148, 20);
            Pane mainPane = new Pane(gamePane, bombLabel, resetButton, backButton);
            scene = new Scene(mainPane, UNIT * level.getWidth() + 100, UNIT * level.getHeight() + 110);
            mainPane.setStyle("-fx-background-color: #cccccc");
        }
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void rightClick(int x, int y) {
        if (allLabels[x][y].getStyle().contains("#9999")) {
            allLabels[x][y].setStyle("-fx-background-color : yellow;-fx-border-color: black");
            game.removeBomb(x, y);
            bombLabel.setText(String.valueOf(game.getRemindedBomb()));
            return;
        }
        if (game.checkBomb(x, y)) {// it should have been repaired!
            alert.setContentText("you are game over\nDo you want to play again?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK)
                reset();
            else
                System.exit(0);
        } else {
            allLabels[x][y].setStyle("-fx-background-color : red;-fx-border-color: black;");
            game.setOn(x,y);
            game.notBomb(x, y);
            ArrayList<Cell> changing = game.getCellArrayList();
            for(Cell thisCell:changing){
                allLabels[thisCell.getX()][thisCell.getY()].setStyle("-fx-background-color : red;-fx-border-color: black;");
                if (game.getNumber(thisCell.getX(), thisCell.getY()) != 0) {
                    allLabels[thisCell.getX()][thisCell.getY()].setAlignment(Pos.CENTER);
                    allLabels[thisCell.getX()][thisCell.getY()].setText(String.valueOf(game.getNumber(thisCell.getX(),
                            thisCell.getY())));
                }
            }

        }
        if (game.checkWin()) {
            alert.setContentText("you win\nDo you want to play again?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK)
                reset();
            else
                System.exit(0);
        }
    }

    private void goBack(){
        try {
            startingStage.start(myStage);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void reset(){
        game = new Game(game.getLevels());
        for (Label[] allLabel : allLabels)
            for (Label label : allLabel) {
                label.setStyle("-fx-background-color : yellow;-fx-border-color: black");
                label.setText("");
            }
        bombLabel.setText(String.valueOf(game.getRemindedBomb()));
    }

    private void leftClick(int x, int y) {
        if (allLabels[x][y].getStyle().contains("red") || game.getRemindedBomb() <= 0)
            return;
        game.addBomb(x, y);
        bombLabel.setText(String.valueOf(game.getRemindedBomb()));
        allLabels[x][y].setStyle("-fx-background-color : #999999;-fx-border-color: black");
    }
}
