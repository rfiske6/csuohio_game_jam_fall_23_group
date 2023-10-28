package application;

import application.GameCharacter;
import application.GameTile;
import application.MainSceneController;
import static application.MainSceneController.gridSize;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

  public class Level
    {
      GridPane gdPane;
        ArrayList<GameCharacter> npcs;
        ArrayList<GameCharacter> walls;
        GameTile[][] tiles = new GameTile[gridSize][gridSize];
        public Level(GridPane gdPaneGame) throws IOException
        {
            gdPane = gdPaneGame;
            npcs = new ArrayList<>();
            walls = new ArrayList<>();
            GameTile curTile;
            for (int i = 0; i < gridSize; i++) {

            for (int j = 0; j < gridSize; j++) {
                curTile = tiles[i][j];
                
                if (curTile == null) {
                    tiles[i][j] = curTile = new GameTile();

                } else {
                    System.out.println("yo");
                }
                Pane curPane = tiles[i][j].getPane();
                gdPaneGame.add(curPane, j, i);
                String path = "resources/" + tiles[i][j].getSpritePath();
                //System.out.printf("Using: %s\n", path);
                FileInputStream stream = new FileInputStream(path);
                Image img = new Image(stream);
                curTile.imgV = new ImageView(img);
                curTile.getPane().getChildren().add(curTile.imgV);

                curTile.imgV.fitWidthProperty().bind(gdPaneGame.widthProperty().divide(gridSize));
                curTile.imgV.fitHeightProperty().bind(gdPaneGame.heightProperty().divide(gridSize));
                stream.close();
                
                if (i == 0 && j == 0)
                {
                    try {
                        addBlocker(curTile, walls, "wall.png");
                    } catch (Exception ex) {
                        Logger.getLogger(MainSceneController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                    }
                }
                
                if ((i == 5 && j == 4) || i == 3 && j == 2 )
                {
                    GameCharacter c1 = new GameCharacter();
                    npcs.add(c1);
                    c1.tile = curTile;
                    c1.col = j;
                    c1.row = i;
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
        }
        
           public void addBlocker(GameTile curTile, ArrayList<GameCharacter> walls, String sprite) throws Exception
    {
                      curTile.blocker = true;
                    GameCharacter newChar = new GameCharacter();
                    newChar.sprite = sprite;
                    walls.add(newChar);
                   
                    String path = "resources/" + newChar.sprite;
                    FileInputStream stream = new FileInputStream(path);
                    Image img = new Image(stream);
                    newChar.imgV = new ImageView(img);
                    curTile.getPane().getChildren().add(newChar.imgV);
                    newChar.imgV.fitWidthProperty().bind(
                            gdPane.widthProperty().divide(gridSize));
                    newChar.imgV.fitHeightProperty().bind(
                            gdPane.heightProperty().divide(gridSize));
                    stream.close();
                
    }
 
    }
  