package exercise;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * @author 李高丞
 * @version 1.0 Beta
 */
public class ToggleButtons extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Toggle Button Sample");
        primaryStage.setWidth(250);
        primaryStage.setHeight(180);

        HBox hBox = new HBox();
        VBox vBox = new VBox();

        Scene scene = new Scene(new Group(vBox));
        primaryStage.setScene(scene);
        scene.getStylesheets().add("/exercise/ControlStyle.css");

        Rectangle rectangle = new Rectangle();
        rectangle.setHeight(50);
        rectangle.setFill(Color.WHITE);
        rectangle.setStroke(Color.DARKGRAY);
        rectangle.setStrokeWidth(2);
        rectangle.setArcHeight(10);
        rectangle.setArcWidth(10);

        final ToggleGroup group = new ToggleGroup();

        group.selectedToggleProperty().addListener(
                (ObservableValue<? extends Toggle> ov,
                 Toggle toggle, Toggle new_toggle) -> {
                    if (new_toggle == null) {
                        rectangle.setFill(Color.WHITE);
                    } else {
                        rectangle.setFill((Color) group.getSelectedToggle().getUserData());
                    }
                });

        ToggleButton tb1 = new ToggleButton("Minor");
        tb1.setToggleGroup(group);
        tb1.setUserData(Color.LIGHTGREEN);
        tb1.setSelected(true);
        tb1.getStyleClass().add("toggle-button1");

        ToggleButton tb2 = new ToggleButton("Major");
        tb2.setToggleGroup(group);
        tb2.setUserData(Color.LIGHTBLUE);
        tb2.getStyleClass().add("toggle-button2");

        ToggleButton tb3 = new ToggleButton("Critical");
        tb3.setToggleGroup(group);
        tb3.setUserData(Color.SALMON);
        tb3.getStyleClass().add("toggle-button3");



        hBox.getChildren().addAll(tb1, tb2, tb3);

        vBox.getChildren().add(new Label("Priority:"));
        vBox.getChildren().add(hBox);
        vBox.getChildren().add(rectangle);
        vBox.setPadding(new Insets(20, 10, 10, 20));


        primaryStage.show();
        rectangle.setWidth(hBox.getWidth());

    }

    public static void main(String[] args) {
        launch(args);
    }
}
