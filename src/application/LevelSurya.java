package application;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;


public class LevelSurya extends Level
    {
        int gridSize = MainSceneController.gridSize;
        ArrayList<GameCharacter> npcs;
        ArrayList<GameCharacter> walls;
        GameTile[][] tiles = new GameTile[gridSize][gridSize];
        public LevelSurya(GridPane gdPane) throws IOException, Exception
        {
            super(gdPane);
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
                gdPane.add(curPane, j, i);
                String path = "resources/surya/" + tiles[i][j].getSpritePath();
                //System.out.printf("Using: %s\n", path);
                FileInputStream stream = new FileInputStream(path);
                Image img = new Image(stream);
                curTile.imgV = new ImageView(img);
                curTile.getPane().getChildren().add(curTile.imgV);

                curTile.imgV.fitWidthProperty().bind(gdPane.widthProperty().divide(gridSize));
                curTile.imgV.fitHeightProperty().bind(gdPane.heightProperty().divide(gridSize));
                stream.close();
                
                if ( i == 2 && j == 2)
                {
                    addBlocker(curTile,walls, "surya/wall.png");
                }
                else if((i==0 &&j ==0) || (i == 1 && j == 1)){
                   addBlocker(curTile,walls, "surya/wall.png");
                    
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
                     c1.imgV.fitWidthProperty().bind(gdPane.widthProperty().divide(gridSize));
                c1.imgV.fitHeightProperty().bind(gdPane.heightProperty().divide(gridSize));
               
                    curTile.getPane().getChildren().add(c1.imgV);
                }

            }

        }
        }
    }
