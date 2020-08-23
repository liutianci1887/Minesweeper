package minesweeper.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import minesweeper.model.GameBoard;

public class MinesweeperApplication extends Application {

    private static GameBoard gameBoard;

    public static GameBoard getGameBoard() {
        return gameBoard;
    }

    public static void setGameBoard(GameBoard newGameBoard) {
        gameBoard = newGameBoard;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/Main.fxml"));
        primaryStage.setTitle("Minesweeper v0.2");
        primaryStage.setScene(new Scene(root));
        primaryStage.setAlwaysOnTop(true);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
