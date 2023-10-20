package application;

import java.io.FileInputStream;
import javafx.fxml.FXML;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;

public class MainSceneController {

    @FXML
    private Scene scene;
    Stage primarystage;

    @FXML
    GridPane gdPaneGame;

    @FXML
    Button btnLoad;

    int number;
    Player player = new Player();
    ArrayList<GameCharacter> npcs = new ArrayList<>();
    Random globalRand = new Random(1234);
    public class GameCharacter
    {
        GameTile tile;
        String sprite = "debugcharacter.png";
        ImageView imgV;

    }
    
    public class Player extends GameCharacter{

        public Player()
        {
            sprite = "player.png";
        }
    }

    public class GameTile {

        String sprite;
        StackPane myPane;
        ImageView imgV;

        public GameTile() {
            sprite = "debugback.png";
            myPane = new StackPane();
            myPane.setMinSize(0, 0);

        }

        public Pane getPane() {
            return (myPane);
        }

        public String getSpritePath() {
            return (sprite);
        }
    }
    GameTile[][] board = null;
 int gridSize = 10;
       
    @FXML
    public void setGrid(ActionEvent event) throws IOException {
        GameTile curTile;
        board = new GameTile[gridSize][gridSize];
        for (int i = 0; i < gridSize; i++) {

            for (int j = 0; j < gridSize; j++) {
                curTile = board[i][j];

                if (curTile == null) {
                    board[i][j] = curTile = new GameTile();

                } else {
                    System.out.println("yo");
                }
                Pane curPane = board[i][j].getPane();
                gdPaneGame.add(curPane, j, i);
                String path = "resources/" + board[i][j].getSpritePath();
                //System.out.printf("Using: %s\n", path);
                FileInputStream stream = new FileInputStream(path);
                Image img = new Image(stream);
                curTile.imgV = new ImageView(img);
                curTile.getPane().getChildren().add(curTile.imgV);

                curTile.imgV.fitWidthProperty().bind(gdPaneGame.widthProperty().divide(gridSize));
                curTile.imgV.fitHeightProperty().bind(gdPaneGame.heightProperty().divide(gridSize));
                stream.close();
                
                
                if (globalRand.nextInt(0, 100) > 80)
                {
                    GameCharacter c1 = new GameCharacter();
                    npcs.add(c1);
                    c1.tile = curTile;
                    stream = new FileInputStream("resources/" + c1.sprite);
                    img = new Image(stream);
                    c1.imgV = new ImageView(img);
                    stream.close();
                     c1.imgV.fitWidthProperty().bind(gdPaneGame.widthProperty().divide(gridSize));
                c1.imgV.fitHeightProperty().bind(gdPaneGame.heightProperty().divide(gridSize));
               
                    curTile.getPane().getChildren().add(c1.imgV);
                }

            }

        }
        player.tile = board[2][1];
        curTile = player.tile;
        FileInputStream stream = new FileInputStream("resources/player.png");

        Image img = new Image(stream);
        ImageView imgV = new ImageView(img);
        player.imgV = imgV;
        curTile.getPane().getChildren().add(imgV);

        imgV.fitWidthProperty().bind(gdPaneGame.widthProperty().divide(gridSize));
        imgV.fitHeightProperty().bind(gdPaneGame.heightProperty().divide(gridSize));
        stream.close();
        gdPaneGame.requestFocus();
        
        
        
        
        btnLoad.setDisable(true);
    }

    public void buttonFullscreen(ActionEvent event) throws IOException {
        System.out.println("FULLSCREEN");
        Node node = (Node) event.getSource();
        Stage primarystage = (Stage) node.getScene().getWindow();
        primarystage.setFullScreen(true);

    }

    void updateTile(GameTile tile) {
        if (tile == player.tile) {

        }
    }

    @FXML
    void keyPressed(KeyEvent event) {
        if (board == null) {
            return;
        }
        int row = GridPane.getRowIndex(player.tile.getPane());
        int col = GridPane.getColumnIndex(player.tile.getPane());
        int newRow, newCol;
        newRow = row;
        newCol = col;
        if (event.getCode() == KeyCode.UP) {
            if (row == 0) {
                return;
            }
            newRow--;
        } else if (event.getCode() == KeyCode.DOWN) {
            if (row == gridSize) {
                return;
            }
            newRow++;
        } else if (event.getCode() == KeyCode.LEFT) {
            if (col == 0) {
                return;
            }
            newCol--;
        } else if (event.getCode() == KeyCode.RIGHT) {
            if (col == gridSize) {
                return;
            }
            newCol++;
        }
        GameTile swap = board[newRow][newCol];
        player.tile.myPane.getChildren().remove(player.imgV);
        swap.myPane.getChildren().add(player.imgV);
        player.tile = swap;
    }
}
