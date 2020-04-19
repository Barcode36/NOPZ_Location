package Models.Screen;

import Models.Blueprint.Location;
import Models.Utilities.FileWorker;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class MiniGameMapDrawer extends MapDrawer {

    private double lastloop = 0.f;
    private double t;
    private Location player, goal;
    private AnimationTimer animTimer;
    private int point = 0;
    private Label label = new Label("คะแนน: 0");

    public AnimationTimer getAnimTimer() {
        return animTimer;
    }

    public MiniGameMapDrawer(double MAP_HEIGHT, double MAP_WIDTH, double RATIO, double user_x, double user_y) throws IOException {
        super(MAP_HEIGHT, MAP_WIDTH, RATIO, user_x, user_y);
        player = new Location(getUser_x(), getUser_y(), "player");
        goal = FileWorker.readLocationListFromFile().get(0);
        label.setTranslateY(this.getMAP_HEIGHT() - 100);
        label.setTranslateX(this.getMAP_WIDTH() / 2 - 20);
        label.setText("คะแนน: 0");
    }

    @Override
    public Parent getDrawScene() throws Exception {
        Pane pane = (Pane) super.getDrawScene();
        pane.getChildren().add(label);

        return pane;
    }

    @Override
    public Stage getMapStage() throws Exception {
       Scene scene = super.getMapStage().getScene();
       BorderPane bp = new BorderPane();
       bp.setCenter(this.getDrawScene());

       scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
           @Override
           public void handle(KeyEvent keyEvent) {
               if(keyEvent.getCode() == KeyCode.A) {
                   player.setX(getUser_x() - (t - lastloop) * 5000.f);
                   setUser_x(getUser_x() - (t - lastloop) * 5000.f);
               }
               if(keyEvent.getCode() == KeyCode.D) {
                   player.setX(getUser_x() + (t - lastloop) * 5000.f);
                   setUser_x(getUser_x() + (t - lastloop) * 5000.f);
               }
               if(keyEvent.getCode() == KeyCode.W) {
                   player.setY(getUser_y() - (t - lastloop) * 5000.f);
                   setUser_y(getUser_y() - (t - lastloop) * 5000.f);
               }
               if(keyEvent.getCode() == KeyCode.S) {
                   player.setY(getUser_y() + (t - lastloop) * 5000.f);
                   setUser_y(getUser_y() + (t - lastloop) * 5000.f);
               }
           }
       });
       final long startNanoTime = System.nanoTime();

       //Frame
       animTimer = new AnimationTimer() {
           public void handle(long currentNanoTime) {
               lastloop = t;
               t = (currentNanoTime - startNanoTime) / 1000000000.0;
               updateMap();
               ArrayList<Location> locs = player.sortByDistanceWith(getLocs());
               if(player.distanceWith(locs.get(0)) <= 500.f && locs.get(0).getName().compareTo(goal.getName()) == 0) {
                   System.out.println("Oh yeah!");
                   point++;
                   label.setText("คะแนน: " + point);
                   goal = locs.get(1);
               }
           }};
        animTimer.start();

       this.getStage().setScene(scene);
       return this.getStage();
    }
}
