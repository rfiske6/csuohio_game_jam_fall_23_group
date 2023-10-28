package application;


import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class GameTile {

        public String sprite;
        public StackPane myPane;
        public ImageView imgV;
        public boolean blocker;

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
    