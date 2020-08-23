package minesweeper.view;

import javafx.fxml.FXML;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import minesweeper.application.MinesweeperApplication;
import minesweeper.controller.GameController;
import minesweeper.model.GameTile;

public class ViewController {

    private static final double SPRITE_SIZE = 32;
    private static GameTile triggerTile;

    public static GameTile getTriggerTile() {
        return triggerTile;
    }

    public static void setTriggerTile(GameTile newTriggerTile) {
        triggerTile = newTriggerTile;
    }

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
        setTriggerTile(null);

        refreshView();

        // First refresh so need to set the window to the right size
        mainPanel.getScene().getWindow().sizeToScene();
    }

    private void refreshView() {
        boolean hiddenTilesLeft = false;
        // Clear all the blocks on the window and redraw them
        mainPanel.getChildren().clear();

        for (GameTile[] tileRow : MinesweeperApplication.getGameBoard().getTiles()) {
            for (GameTile tile : tileRow) {
                Rectangle square = configureTile(tile);

                // Add it to the view
                square.setX(tile.getX() * SPRITE_SIZE);
                square.setY(tile.getY() * SPRITE_SIZE);
                mainPanel.getChildren().add(square);

                // Keep track of if there are still hidden tiles left on the board (short-circuited)
                hiddenTilesLeft = hiddenTilesLeft || tile.isHidden();
            }
        }

        // If all the tiles have been revealed and game over still hasn't been triggered, then assume you won
        if (!hiddenTilesLeft) {
            gameOver();
        }
    }

    private Rectangle configureTile(GameTile tile) {
        // Creates the square, adds the sprite, and adds the mouse click listener
        Rectangle square = new Rectangle(SPRITE_SIZE, SPRITE_SIZE);

        setSprite(tile, square, false);

        square.setOnMousePressed(mouseEvent -> {
            if (mouseEvent.isMiddleButtonDown() || (mouseEvent.isPrimaryButtonDown() && mouseEvent.isSecondaryButtonDown())) {
                // Middle click or both left and right
                if (GameController.middleClick(tile.getX(), tile.getY())) {
                    gameOver();
                    return;
                }
            } else if (mouseEvent.isPrimaryButtonDown()) {
                // Left click
                if (GameController.leftClick(tile.getX(), tile.getY())) {
                    gameOver();
                    return;
                }
            } else if (mouseEvent.isSecondaryButtonDown()) {
                // Right click
                if (!tile.isVisible()) {
                    tile.toggleFlag();
                }
            }
            refreshView();
        });

        return square;
    }

    private void setSprite(GameTile tile, Rectangle square, boolean isGameOver) {
        // Set to the right sprite
        if (tile.isFlagged() && !isGameOver) {
            square.setFill(Sprites.Flag);
        } else if (tile.isHidden() && !isGameOver) {
            square.setFill(Sprites.Hidden);
        } else {
            // Not flagged nor hidden, get the right number
            switch (tile.getSurroundingMines()) {
                case -1 -> square.setFill(Sprites.Bomb);
                case 0 -> square.setFill(Sprites.Zero);
                case 1 -> square.setFill(Sprites.One);
                case 2 -> square.setFill(Sprites.Two);
                case 3 -> square.setFill(Sprites.Three);
                case 4 -> square.setFill(Sprites.Four);
                case 5 -> square.setFill(Sprites.Five);
                case 6 -> square.setFill(Sprites.Six);
                case 7 -> square.setFill(Sprites.Seven);
                case 8 -> square.setFill(Sprites.Eight);
            }
        }
    }

    private void gameOver() {
        // Clear all the blocks on the window and redraw them all in their revealed form
        mainPanel.getChildren().clear();

        GameTile triggerTile = getTriggerTile();

        for (GameTile[] tileRow : MinesweeperApplication.getGameBoard().getTiles()) {
            for (GameTile tile : tileRow) {
                Rectangle square = new Rectangle(SPRITE_SIZE, SPRITE_SIZE);

                if (tile.equals(triggerTile)) {
                    // If necessary, shows the bomb tile that triggered the game over (that you clicked on)
                    square.setFill(Sprites.BombRed);
                } else {
                    setSprite(tile, square, true);
                }

                // Add it to the view
                square.setX(tile.getX() * SPRITE_SIZE);
                square.setY(tile.getY() * SPRITE_SIZE);
                mainPanel.getChildren().add(square);
            }
        }
    }
}
