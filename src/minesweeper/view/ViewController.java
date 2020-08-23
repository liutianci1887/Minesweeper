package minesweeper.view;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import minesweeper.application.MinesweeperApplication;
import minesweeper.controller.GameController;
import minesweeper.model.GameTile;

public class ViewController {

    private final double SPRITE_SIZE = 32;

    @FXML
    private Pane mainPanel;

    @FXML
    private ToggleGroup gameType;

    @FXML
    private void newGame() {
        int dimensionX, dimensionY, mineCount;

        // 0: easy, 1: normal, 2: hard
        int selectedOption = gameType.getToggles().indexOf(gameType.getSelectedToggle());
        switch (selectedOption) {
            case 0:
                dimensionX = 9;
                dimensionY = 9;
                mineCount = 10;
                break;
            case 1:
                dimensionX = 16;
                dimensionY = 16;
                mineCount = 40;
                break;
            case 2:
                dimensionX = 30;
                dimensionY = 16;
                mineCount = 99;
                break;
            default:
                // Invalid option: don't do anything
                return;
        }

        GameController.newBoard(dimensionX, dimensionY, mineCount);

        refreshView();

        // First refresh so need to set the window to the right size
        mainPanel.getScene().getWindow().sizeToScene();
    }

    private void refreshView() {
        // Clear all the blocks on the window and redraw them
        mainPanel.getChildren().clear();

        for (GameTile[] tileRow : MinesweeperApplication.getGameBoard().getTiles()) {
            for (GameTile tile : tileRow) {
                Rectangle square = new Rectangle(SPRITE_SIZE, SPRITE_SIZE);

                // Set to the right sprite
                if (tile.isFlagged()) {
                    square.setFill(Sprites.Flag);
                } else if (tile.isHidden()) {
                    square.setFill(Sprites.Hidden);
                } else {
                    // Not flagged nor hidden, get the right number
                    square.setFill(Sprites.One);
                }

                square.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                            if (GameController.revealTile(tile.getX(), tile.getY())) {
                                gameOver();
                            }
                        } else if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                            tile.setFlag();
                        }

                        refreshView();
                    }
                });

                // Add it to the view
                square.setX(tile.getX() * SPRITE_SIZE);
                square.setY(tile.getY() * SPRITE_SIZE);
                mainPanel.getChildren().add(square);
            }
        }
    }

    private void gameOver() {
        System.out.println("over");
    }

}
