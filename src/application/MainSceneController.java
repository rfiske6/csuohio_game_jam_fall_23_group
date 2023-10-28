package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
import java.util.logging.Logger;
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
    public GridPane gdPaneGame;

    //public static GridPane gdPaneGameAccess;
    
    @FXML
    Button btnLoad;
    @FXML
    Button btnLoad1;
    @FXML
    Button btnLoad2;

    int number;
    Player player = new Player();
    //ArrayList<GameCharacter> npcs = new ArrayList<>();
    Random globalRand = new Random(1234);
    
    

    
    
    public class Player extends GameCharacter{

        public Player()
        {
            sprite = "player.png";
        }
    }

    //GameTile[][] board = null;
 public final static int gridSize = 10;
       Level curLevel;
    
       @FXML
        public void setLevelDefault(ActionEvent event) throws IOException {
            
                setGrid(new Level(gdPaneGame));
            }
            
    @FXML
        public void setLevelSurya(ActionEvent event) throws IOException, Exception {
            
                setGrid(new LevelSurya(gdPaneGame));
            }
            
            
            
            @FXML
        public void setLevelTovanche(ActionEvent event) throws Exception {
            
                setGrid(new LevelTovanche(gdPaneGame));
            }
            
            
    public void setGrid(Level curLevel) throws IOException {
        //gdPaneGameAccess = gdPaneGame;
        GameTile curTile;
        
        GameTile[][] board = curLevel.tiles;// = new GameTile[gridSize][gridSize];
        
         
        player.tile = board[2][1];
        player.col = 1;
        player.row = 2;
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
        
        
        btnLoad1.setDisable(true);
        btnLoad2.setDisable(true);
        
        
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
        if (curLevel == null) {
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
            if (row == gridSize-1) {
                return;
            }
            newRow++;
        } else if (event.getCode() == KeyCode.LEFT) {
            if (col == 0) {
                return;
            }
            newCol--;
        } else if (event.getCode() == KeyCode.RIGHT) {
            if (col == gridSize-1) {
                return;
            }
            newCol++;
        }
        GameTile swap;
        //curLevel.tiles[0][0].blocker = true;
        
        
        if (curLevel.tiles[newRow][newCol].blocker == false)
        {
            swap = curLevel.tiles[newRow][newCol];
            player.tile.myPane.getChildren().remove(player.imgV);
            swap.myPane.getChildren().add(player.imgV);
            player.tile = swap;
            player.col = newCol;
            player.row = newRow;
        }        
        for (GameCharacter curChar : curLevel.npcs)
        {
            boolean useCol = globalRand.nextBoolean();

            if (curChar.col == player.col || curChar.row == player.row)
            {
                if (Math.abs(player.col - curChar.col) == 1 || Math.abs(player.row - curChar.row) == 1)
                {
                    System.out.println("Attack");
                    continue;
                }
                else if (curChar.col == player.col)
                {
                    useCol = false;
                }
                else if (curChar.row == player.row)
                {
                    //move x
                    useCol = true;
                }
                
            }
            
            col = newCol = curChar.col;
            row = newRow = curChar.row;
            if (useCol)
            {
                if (curChar.col > player.col)
                {
                    newCol--;
                }
                else
                {
                    newCol++;
                }
            }
            else
            {
                if (curChar.row > player.row)
                {
                    newRow--;
                }
                else
                {
                    newRow++;
                }
            }
            try
            {
                if (curLevel.tiles[newRow][newCol].blocker == false)
                {
                    swap = curLevel.tiles[newRow][newCol];
                    curChar.tile.myPane.getChildren().remove(curChar.imgV);
                    swap.myPane.getChildren().add(curChar.imgV);
                    curChar.tile = swap;
                    curChar.col = newCol;
                    curChar.row = newRow;
                }
            }
            catch(Exception e)
            {
                System.out.println("err");
            }
        
        }
    }
}